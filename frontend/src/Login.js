import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from './AuthContext';
import { trackRequest } from './Metrics';

function LoginForm() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();
    const { login } = useAuth();

    const from = location.state?.from?.pathname || '/account'

    const handleSubmit = async (event) => {
        event.preventDefault();
        //Resetting form fields
        setError('');
        setSuccess('');
        setIsLoading(true);

        try {
            const response = await fetch('http://localhost:8000/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            const data = await response.json();

            if (!response.ok) {
                //Tracking failed login
                //trackRequest('POST', '/login/error');
                throw new Error(data.message || 'Login failed');
            }
            if (data.accessToken) {
                setSuccess('Login Successful');

                //sessionStorage.setItem('accessToken', data.accessToken);
                //sessionStorage.setItem('refreshToken', data.refreshToken);
                //sessionStorage.setItem('accessTokenExpiration', data.accessTokenExpiration);
                //sessionStorage.setItem('refreshTokenExpiration', data.refreshTokenExpiration);
                //sessionStorage.setItem('tokenType', data.tokenType);

                login(data);

                setUsername('');
                setPassword('');

                navigate(from, { replace: true });
            } else {
                //Tracking failed authentication
                //trackRequest('POST', '/login/error');
                setError(data.message || 'Authentication failed');
            }
        } catch (error) {
            //Tracking general errors
            //trackRequest('POST', '/login/error');
            setError(error.message || 'Invalid username or password');
            console.error('Login Error:', error);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            {error &&
                <div className="error-message" style={{
                    color: 'red',
                    marginBottom: '10px'
                }}>
                    {error}
                </div>
            }

            {success &&
                <div className="success-message" style={{
                    color: 'green',
                    marginBottom: '10px'
                }}>
                    {success}
                </div>
            }

            <form onSubmit={handleSubmit}>
                <div className="form-group" style={{ marginBottom: '10px' }}>
                    <label htmlFor="username">Username: </label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group" style={{ marginBottom: '10px' }}>
                    <label htmlFor="password">Password: </label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button
                    type="submit"
                    disabled={isLoading}
                >
                    {isLoading ? 'Logging in...' : 'Login'}
                </button>
            </form>
            <div style={{ marginTop: '15px' }}>
                <a href="/reset-password">Reset Password</a>
            </div>
        </div>
    );
}

export default LoginForm;