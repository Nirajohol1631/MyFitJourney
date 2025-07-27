import React, { useState, useEffect } from 'react';
import LoginPage from './LoginPage';
import Dashboard from './Dashboard';
import api from './api'; // We will still use the api helper

export default function App() {
  const [token, setToken] = useState(null);
  const [authError, setAuthError] = useState('');
  const [loginIsLoading, setLoginIsLoading] = useState(false);

  // --- NEW STATE FOR DASHBOARD DATA ---
  const [dashboardData, setDashboardData] = useState({
    plans: [],
    isLoading: true,
    error: '',
  });

  // This effect runs when the component first loads to check for a stored token
  useEffect(() => {
    const storedToken = localStorage.getItem('gym_auth_token');
    if (storedToken) {
      setToken(storedToken);
    }
  }, []);

  // --- NEW EFFECT TO FETCH DATA AFTER LOGIN ---
  // This effect runs whenever the `token` state changes (i.e., after a user logs in)
  useEffect(() => {
    // Only fetch data if there is a token
    if (token) {
      const fetchPlans = async () => {
        // Set loading state for the dashboard
        setDashboardData({ plans: [], isLoading: true, error: '' });
        try {
          // Use our api helper to make the authenticated call
          const fetchedPlans = await api.call('/membership-plans');
          setDashboardData({ plans: fetchedPlans, isLoading: false, error: '' });
        } catch (err) {
          // If fetching fails, set an error message for the dashboard
          setDashboardData({ plans: [], isLoading: false, error: err.message });
        }
      };
      fetchPlans();
    }
  }, [token]); // The dependency array ensures this runs only when `token` changes

  const handleLogin = async (username, password) => {
    setLoginIsLoading(true);
    setAuthError('');
    try {
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      });
      const responseBody = await response.text();
      if (!response.ok) {
        throw new Error(responseBody || 'Login failed');
      }
      setToken(responseBody);
      localStorage.setItem('gym_auth_token', responseBody);
    } catch (error) {
      setAuthError(error.message || 'An unknown error occurred.');
    } finally {
      setLoginIsLoading(false);
    }
  };

  const handleLogout = () => {
    setToken(null);
    localStorage.removeItem('gym_auth_token');
  };

  if (!token) {
    return <LoginPage onLogin={handleLogin} error={authError} isLoading={loginIsLoading} />;
  }

  // Pass the dashboard data down to the Dashboard component as props
  return <Dashboard onLogout={handleLogout} data={dashboardData} />;
}
