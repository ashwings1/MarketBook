import './App.css';
import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginForm from './Login';
import ResetPassword from './ResetPassword';
import { trackRequest } from './Metrics';

function App() {
  //Tracking initial page load
  useEffect(() => {
    trackRequest('GET', '/login');
  }, [])

  return (
    /*
    <div className="App">
      <h1>Login Page</h1>
      <LoginForm trackRequest={trackRequest} />
    </div>
    */
    <Router>
      <div className="App">
        <Routes>
          <Route
            path="/"
            element={
              <div>
                <h1>Login Page</h1>
                <LoginForm trackRequest={trackRequest} />
              </div>
            }
          />
          <Route
            path="/login"
            element={
              <div>
                <h1>Login Page</h1>
                <LoginForm trackRequest={trackRequest} />
              </div>
            }
          />
          <Route
            path="/reset-password"
            element={
              <div>
                <h1>Reset Password</h1>
                <ResetPassword />
              </div>
            }
          />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
