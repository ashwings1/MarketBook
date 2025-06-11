import { useState } from 'react';

function RegisterUser() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [role, setRole] = useState('BUYER');
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError('');
        setMessage('');
        setIsLoading(true);

        try {
            const response = await fetch('http://localhost:8000/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username,
                    password,
                    firstName,
                    lastName,
                    role
                })
            });


            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || 'Registration failed at errorText');
            }

            const responseText = await response.text();

            if (responseText == "Success") {
                setMessage('Registration successful');
                setUsername('');
                setPassword('');
                setFirstName('');
                setLastName('');
                setRole('BUYER');

                setTimeout(() => {
                    //Change to /dashboard later
                    window.location.href = '/login';
                }, 2000);
            } else {
                throw new Error('Registration failed at else');
            }
        } catch (error) {
            setError(error.message || 'Registration failed at catch');
            console.error('Registration error:', error);
        } finally {
            setIsLoading(false);
        }

    };

    return (
        <div className="register-container">
            <h2>Register</h2>
            <p>Enter details to register an account.</p>

            {error && (
                <div className="error-message" style={{
                    color: 'red',
                    marginBottom: '10px'
                }}>
                    {error}
                </div>
            )}

            {message && (
                <div className="success-message" style={{
                    color: 'green',
                    marginBottom: '10px'
                }}>
                    {message}
                </div>
            )}

            <form onSubmit={handleSubmit}>
                <div className="form-group" style={{ marginBottom: '10px' }}>
                    <label htmlFor="username">Username: </label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                        placeholder="Enter your username"
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
                        placeholder="Enter your password"
                    />
                </div>

                <div className="form-group" style={{ marginBottom: '10px' }}>
                    <label htmlFor="firstName">First Name: </label>
                    <input
                        type="text"
                        id="firstName"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                        placeholder="Enter your first name"
                    />
                </div>

                <div className="form-group" style={{ marginBottom: '10px' }}>
                    <label htmlFor="lastName">Last Name: </label>
                    <input
                        type="text"
                        id="lastName"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                        placeholder="Enter your lastName"
                    />
                </div>

                <div className="form-group" style={{ marginBottom: '10px' }}>
                    <label>Account Type: </label>
                    <div style={{ display: 'flex', gap: '10px', marginTop: '5px' }}>
                        <button
                            type="button"
                            onClick={() => setRole('BUYER')}
                            style={{
                                padding: '10px 20px',
                                border: '2px solid #007bff',
                                background: role === 'BUYER' ? '#007bff' : 'white',
                                color: role === 'BUYER' ? 'white' : '#007bff',
                                cursor: 'pointer',
                                borderRadius: '4px'
                            }}
                        >
                            Buyer
                        </button>

                        <button
                            type="button"
                            onClick={() => setRole('SELLER')}
                            style={{
                                padding: '10px 20px',
                                border: '2px solid #007bff',
                                background: role === 'SELLER' ? '#007bff' : 'white',
                                color: role === 'SELLER' ? 'white' : '#007bff',
                                cursor: 'pointer',
                                borderRadius: '4px'
                            }}
                        >
                            Seller
                        </button>
                    </div>
                </div>

                <button
                    type="submit"
                    disabled={isLoading}
                >
                    {isLoading ? 'Registering...' : 'Register'}
                </button>
            </form>
            <div style={{ marginTop: '15px' }}>
                /* Change to /dashboard later */
                <a href="/login">Back to Login</a>
            </div>
        </div>
    );

}

export default RegisterUser;