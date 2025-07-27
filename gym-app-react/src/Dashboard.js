import React from 'react';
import MembershipPlans from './MembershipPlans'; // Import the component to display plans

/**
 * The main container for the authenticated part of the application.
 * It receives data from App.js and passes it to the relevant child components.
 * @param {object} props - The component props.
 * @param {function} props.onLogout - The function to call when the logout button is clicked.
 * @param {object} props.data - The data object containing plans, loading state, and errors.
 */
const Dashboard = ({ onLogout, data }) => {
    return (
        <div className="min-h-screen bg-gray-900 text-white">
            <header className="bg-gray-800 shadow-md">
                <nav className="container mx-auto px-6 py-4 flex justify-between items-center">
                    <div className="text-2xl font-bold text-cyan-400">GYM-SYS Dashboard</div>
                    <button 
                        onClick={onLogout}
                        className="px-4 py-2 font-semibold text-white bg-red-600 rounded-lg hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500"
                    >
                        Logout
                    </button>
                </nav>
            </header>
            <main className="container mx-auto px-6 py-8">
                {/* Instead of a static welcome message, we now render our MembershipPlans component
                  and pass the data down to it as a prop.
                */}
                <MembershipPlans plansData={data} />
            </main>
        </div>
    );
};

export default Dashboard;
