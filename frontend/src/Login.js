import { useState } from 'react';

function LoginForm() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();
        //Resetting form fields
        setError('');
        setSuccess('');
        setIsLoading(true);

        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Login failed');
            }

            if (data.success) {
                //Success message to user
                setSuccess('Login successful!');

                //Store user info in session storage/context
                sessionStorage.setItem('isAuthenticated', 'true');
                sessionStorage.setItem('username', username);

                setUsername('');
                setPassword('');

                //Redirect to dashboard or home page
                //window.location.href = '/dashboard';
            } else {
                setError(data.message || 'Authentication failed');
            }
        } catch (err) {
            setError(err.message || 'Invalid username or password');
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
        </div>
    );
}

export default LoginForm;