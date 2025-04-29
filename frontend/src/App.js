import './App.css';
import React, { useEffect } from 'react';
import LoginForm from './Login';
import { trackRequest } from './Metrics';

function App() {
  //Tracking initial page load
  useEffect(() => {
    trackRequest('GET', '/login');
  }, [])

  return (
    <div className="App">
      <h1>Login Page</h1>
      <LoginForm trackRequest={trackRequest} />
    </div>
  );
}

export default App;
