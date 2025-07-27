/**
 * A centralized helper for making API calls.
 * It automatically adds the Authorization header if a token is available.
 */
const api = {
  call: async (endpoint, method = 'GET', body = null) => {
    // Retrieve the token from localStorage for every call
    const token = localStorage.getItem('gym_auth_token');
    
    const headers = {
      'Content-Type': 'application/json',
    };
    // If a token exists, add it to the Authorization header
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    const options = {
      method,
      headers,
    };
    // If the call includes a body (for POST/PUT), stringify and add it
    if (body) {
      options.body = JSON.stringify(body);
    }
    
    // Make the actual network request. The '/api' prefix works because
    // of the "proxy" setting in your package.json.
    const response = await fetch(`/api${endpoint}`, options);

    // If the response is not successful (e.g., 404, 403, 500)
    if (!response.ok) {
        let errorData;
        try {
            // Try to parse a structured error message from our Java GlobalExceptionHandler
            errorData = await response.json();
        } catch (e) {
            // If the error isn't JSON, fall back to plain text
            const errorText = await response.text();
            errorData = { message: errorText || `Request failed with status ${response.status}` };
        }
        // Throw an error that can be caught by our components
        throw new Error(errorData.message || `An unknown error occurred.`);
    }
    
    // Handle successful requests that have no content (like a DELETE request)
    if (response.status === 204) {
        return null;
    }
    
    // For successful requests with content, parse and return the JSON body
    return response.json();
  },
};

export default api;
