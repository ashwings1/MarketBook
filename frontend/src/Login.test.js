import { render, screen } from '@testing-library/react';
import LoginForm from './Login';

test('renders login form elements', () => {
    render(<LoginForm />);

    //Checking "Login" heading
    const heading = screen.getByRole('heading', { name: /login/i });
    expect(heading).toBeInTheDocument();

    //Finding inputs by label
    const usernameInput = screen.getByLabelText(/username:/i);
    const passwordInput = screen.getByLabelText(/password:/i);

    //Finding button by role and text
    const submitButton = screen.getByRole('button', { name: /login/i });

    expect(usernameInput).toBeInTheDocument();
    expect(passwordInput).toBeInTheDocument();
    expect(submitButton).toBeInTheDocument();
});