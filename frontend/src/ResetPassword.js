import { useState } from 'react';

function ResetPassword() {
    const [username, setUsername] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError('');
        setMessage('');

        /*
        if (newPassword != confirmPassword){
            setError('Passwords do not match');
            return;
        }

        if (newPassword.length < 8){
            setError('Password must be at least 8 characters long');
            return;
        }
        */

        setIsLoading(true);

        try {
            const response = await fetch('http://localhost:8000/api/auth/reset-password', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username,
                    newPassword
                })
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Password reset failed');
            }

            setMessage('Password reset successful!');
            setUsername('');
            setNewPassword('');
            setConfirmPassword('');

            setTimeout(() => {
                window.location.href = '/login';
            }, 2000);
        } catch (error) {
            setError(error.message || 'Password reset failed');
            console.error('Password reset error:', error);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="password-reset-container">
            <h2>Reset Password</h2>
            <p>Enter username and new password to reset account.</p>

            {error && (
                <div className="error-message" style={{
                    color: 'red',
                    merginBottom: '10px'
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
                    <label htmlFor="username">Username:</label>
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
                    <label htmlFor="newPassword">New Password:</label>
                    <input
                        type="password"
                        id="newPassword"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        required
                        minLength="8"
                        placeholder="Enter new password"
                    />
                </div>

                <div className="form-group" style={{ marginBottom: '10px' }}>
                    <label htmlFor="confirmPassword">Confirm Password:</label>
                    <input
                        type="password"
                        id="confirmPassword"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        required
                        minLength="8"
                        placeholder="Confirm new password"
                    />
                </div>

                <button
                    type="submit"
                    disabled={isLoading}
                >
                    {isLoading ? 'Resetting...' : 'Reset Password'}
                </button>
            </form>

            <div style={{ marginTop: '15px' }}>
                <a href="/login">Back to Login</a>
            </div>
        </div>
    );
}

export default ResetPassword;