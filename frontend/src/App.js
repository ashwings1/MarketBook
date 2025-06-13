import './App.css';
import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import LoginForm from './Login';
import ResetPassword from './ResetPassword';
import RegisterUser from './Register';
import Account from './Account';
import { AuthProvider, useAuth } from './AuthContext';
import { ProtectedRoute } from './ProtectedRoute';
import { trackRequest } from './Metrics';

function Home() {
  const { isAuthenticated, logout } = useAuth();

  return (
    <div>
      <h1>Home Page</h1>
      {isAuthenticated && (
        <div style={{ marginBottom: '15px', padding: '10px', backgroundColor: '#e7feff', borderRadius: '4px' }}>
          <p>You are logged in! Visit your <Link to="/account">Account</Link> to view your profile.</p>
        </div>
      )}
      <nav style={{ margin: '20px 0' }}>
        {isAuthenticated ? (
          <>
            <Link to="/account" style={{ marginRight: '20px' }}>Account</Link>
            <button
              onClick={logout}
              style={{
                marginRight: '20px',
                padding: '5px 10px',
                backgroundColor: '#dc3545',
                color: 'white',
                border: 'none',
                borderRadius: '4px',
                cursor: 'pointer'
              }}
            >
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login" style={{ marginRight: '20px' }}>Login</Link>
            <Link to="/register" style={{ marginRight: '20px' }}>Register</Link>
            <Link to="/reset-password">Reset Password</Link>
          </>
        )}
      </nav>
    </div>
  );
}

function App() {
  //Tracking initial page load
  useEffect(() => {
    trackRequest('GET', '/login');
  }, [])

  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Routes>
            <Route
              path="/"
              element={<Home />}
            />
            <Route
              path="/login"
              element={
                <div>
                  <h1>Login Page</h1>
                  <LoginForm trackRequest={trackRequest} />
                  <Link to="/">Back to Home</Link>
                </div>
              }
            />
            <Route
              path="/reset-password"
              element={
                <div>
                  <h1>Reset Password</h1>
                  <ResetPassword />
                  <Link to="/">Back to Home</Link>
                </div>
              }
            />
            <Route
              path="/register"
              element={
                <div>
                  <h1>Register</h1>
                  <RegisterUser />
                  <Link to="/">Back to Home</Link>
                </div>
              }
            />
            <Route
              path="/account"
              element={
                <ProtectedRoute>
                  <Account />
                </ProtectedRoute>
              }
            />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
