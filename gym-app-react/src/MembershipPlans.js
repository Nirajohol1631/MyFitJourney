import React from 'react';
// Note: We no longer need useState, useEffect, or the api helper in this file.

/**
 * A component that displays a list of membership plans.
 * It receives its data as props and handles rendering the loading, error, and success states.
 * @param {object} props - The component props.
 * @param {object} props.plansData - The data object from App.js.
 * @param {Array} props.plansData.plans - The array of membership plans.
 * @param {boolean} props.plansData.isLoading - The loading state.
 * @param {string} props.plansData.error - The error message, if any.
 */
const MembershipPlans = ({ plansData }) => {
  // Destructure the data from the prop for easier access
  const { plans, isLoading, error } = plansData;

  // 1. Show a loading message while data is being fetched
  if (isLoading) {
    return <div className="text-center p-8 text-lg text-gray-400">Loading Membership Plans...</div>;
  }

  // 2. Show an error message if the API call failed
  if (error) {
    return <div className="text-center p-8 text-lg text-red-400">Error: {error}</div>;
  }

  // 3. If loading is done and there's no error, display the data in a table
  return (
    <div className="bg-gray-800 p-6 rounded-lg shadow-xl">
      <h2 className="text-2xl font-bold mb-6 text-cyan-300">Manage Membership Plans</h2>
      <div className="overflow-x-auto">
        <table className="min-w-full bg-gray-700 rounded-lg">
          <thead>
            <tr className="bg-gray-600 text-left text-sm font-semibold text-gray-300 uppercase tracking-wider">
              <th className="p-4">Plan Name</th>
              <th className="p-4">Description</th>
              <th className="p-4">Cost</th>
              <th className="p-4">Duration</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-600">
            {plans.map((plan) => (
              <tr key={plan.id} className="hover:bg-gray-600/50 transition-colors duration-200">
                <td className="p-4 whitespace-nowrap font-medium">{plan.name}</td>
                <td className="p-4">{plan.description}</td>
                <td className="p-4">${plan.cost.toFixed(2)}</td>
                <td className="p-4">{plan.durationMonths} Months</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default MembershipPlans;
