import React, { useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import { Link } from 'react-router-dom';
import SellerProductManager from './SellerProductManager';

const Account = () => {
    const { fetchWithAuth, logout, isAuthenticated } = useAuth();
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [activeView, setActiveView] = useState('profile');

    useEffect(() => {
        if (isAuthenticated) {
            fetchUserData();
        }
    }, [isAuthenticated]);

    const fetchUserData = async () => {
        try {
            setLoading(true);
            setError('');

            const response = await fetchWithAuth('http://localhost:8000/account');

            if (response.ok) {
                const userData = await response.json();
                setUser({
                    id: userData.id,
                    firstName: userData.firstName,
                    lastName: userData.lastName,
                    role: userData.role
                });
            } else {
                throw new Error('Failed to fetch user data');
            }
        } catch (error) {
            console.error('Error fetching user data:', error);
            setError('Failed to load account information');
        } finally {
            setLoading(false);
        }
    };

    const handleLogout = () => {
        logout();
    };

    const isSeller = user?.role === 'SELLER';

    const renderProfileView = () => (
        <div className="account-info" style={{
            backgroundColor: '#f8f9fa',
            padding: '20px',
            borderRadius: '8px',
            marginBottom: '20px'
        }}>
            <h2>Account Information</h2>
            <div style={{ marginBottom: '10px' }}>
                <strong>User ID:</strong> {user.id}
            </div>
            <div style={{ marginBottom: '10px' }}>
                <strong>First Name:</strong> {user.firstName}
            </div>
            <div style={{ marginBottom: '10px' }}>
                <strong>Last Name:</strong> {user.lastName}
            </div>
            <div style={{ marginBottom: '10px' }}>
                <strong>Account Type:</strong>
                <span style={{
                    marginLeft: '10px',
                    padding: '4px 8px',
                    borderRadius: '4px',
                    backgroundColor: isSeller ? '#28a745' : '#007bff',
                    color: 'white',
                    fontSize: '12px'
                }}>
                    {user.role || 'BUYER'}
                </span>
            </div>
        </div>
    );

    const renderProductsView = () => {
        return <SellerProductManager user={user} />;
    }

    if (loading) {
        return (
            <div className="account-container">
                <h1>Account Dashboard</h1>
                <div style={{ textAlign: 'center', padding: '20px' }}>
                    <p>Loading account information...</p>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="account-container">
                <h1>Account Dashboard</h1>
                <div style={{
                    color: 'red',
                    padding: '20px',
                    backgroundColor: '#fee',
                    borderRadius: '4px',
                    marginBottom: '20px'
                }}>
                    <p>{error}</p>
                    <button
                        onClick={fetchUserData}
                        style={{
                            padding: '8px 16px',
                            backgroundColor: '#007bff',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px',
                            cursor: 'pointer',
                            marginRight: '10px'
                        }}
                    >
                        Retry
                    </button>
                    <button
                        onClick={handleLogout}
                        style={{
                            padding: '8px 16px',
                            backgroundColor: '#dc3545',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px',
                            cursor: 'pointer'
                        }}
                    >
                        Logout
                    </button>
                </div>
            </div>
        );
    }

    if (!user) {
        return (
            <div className="account-container">
                <h1>Account Dashboard</h1>
                <p>No user data available.</p>
                <button onClick={handleLogout}>Logout</button>
            </div>
        );
    }

    return (
        <div className="account-container">
            <h1>Account Dashboard</h1>
            <div style={{ marginBottom: '20px' }}>
                <p>Welcome, {user.firstName} {user.lastName}!</p>
            </div>

            {/* Navigation Tabs */}
            <div style={{ marginBottom: '20px' }}>
                {activeView !== 'profile' && (
                    <button
                        onClick={() => setActiveView('profile')}
                        style={{
                            display: 'flex',
                            alginItems: 'center',
                            color: '#007bff',
                            background: 'none',
                            border: 'none',
                            cursor: 'pointer',
                            marginBottom: '10px',
                            fontSize: '14px'
                        }}
                    >
                        Back to Profile
                    </button>
                )}

                <div style={{ display: 'flex', gap: '10px', flexWrap: 'wrap' }}>
                    <button
                        onClick={() => setActiveView('profile')}
                        style={{
                            padding: '10px 20px',
                            border: '2px solid #007bff',
                            background: activeView === 'profile' ? '#007bff' : 'white',
                            color: activeView === 'profile' ? 'white' : '#007bff',
                            cursor: 'pointer',
                            borderRadius: '4px',
                            display: 'flex',
                            alginItems: 'center',
                            gap: '5px'
                        }}
                    >
                        Profile
                    </button>

                    {/* Products button for Sellers only */}
                    {isSeller && (
                        <button
                            onClick={() => setActiveView('products')}
                            style={{
                                padding: '10px 20px',
                                border: '2px solid #28a745',
                                background: activeView === 'products' ? '#28a745' : 'white',
                                color: activeView === 'products' ? 'white' : '#28a745',
                                cursor: 'pointer',
                                borderRadius: '4px',
                                display: 'flex',
                                alignItems: 'center',
                                gap: '5px'
                            }}
                        >
                            My Products
                        </button>
                    )}
                </div>
            </div>

            {/* Content */}
            {activeView === 'profile' && renderProfileView()}
            {activeView === 'products' && isSeller && renderProductsView()}

            {/* Action Button */}
            {activeView === 'profile' && (
                <div style={{ marginTop: '20px' }}>
                    <button
                        onClick={fetchUserData}
                        style={{
                            padding: '10px 20px',
                            backgroundColor: '#28a745',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px',
                            cursor: 'pointer',
                            marginRight: '10px'
                        }}
                    >
                        Refresh Data
                    </button>

                    <button
                        onClick={handleLogout}
                        style={{
                            padding: '10px 20px',
                            backgroundColor: '#dc3545',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px',
                            cursor: 'pointer',
                            marginRight: '10px'
                        }}
                    >
                        Logout
                    </button>

                    <button
                        onClick={() => window.location.href = '/'}
                        style={{
                            padding: '10px 20px',
                            backgroundColor: '#007bff',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px',
                            cursor: 'pointer',
                        }}
                    >
                        Back to Home
                    </button>
                </div>
            )}

            {/* 
            <div className="account-info" style={{
                backgroundColor: '#f8f9fa',
                padding: '20px',
                borderRadius: '8px',
                marginBottom: '20px'
            }}>
                <h2>Account Information</h2>
                <div style={{ marginBottom: '10px' }}>
                    <strong>User ID:</strong> {user.id}
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <strong>First Name:</strong> {user.firstName}
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <strong>Last Name:</strong> {user.lastName}
                </div>
            </div>

            <div style={{ marginTop: '20px' }}>
                <button
                    onClick={fetchUserData}
                    style={{
                        padding: '10px 20px',
                        backgroundColor: '#28a745',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer',
                        marginRight: '10px'
                    }}
                >
                    Refresh Data
                </button>

                <button
                    onClick={handleLogout}
                    style={{
                        padding: '10px 20px',
                        backgroundColor: '#dc3545',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer',
                        marginRight: '10px'
                    }}
                >
                    Logout
                </button>

                <Link to="/" style={{ textDecoration: 'none' }}>
                    <button style={{
                        padding: '10px 20px',
                        backgroundColor: '#007bff',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer',
                    }}>
                        Back to Home
                    </button>
                </Link>
            </div>
            {*/}
        </div>
    );
};

export default Account;