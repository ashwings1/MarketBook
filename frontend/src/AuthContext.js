import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};

export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [loading, setLoading] = useState(true);

    //If user authenticated on component mount
    useEffect(() => {
        checkAuthStatus();
    }, []);

    //Token expiration check
    useEffect(() => {
        if (isAuthenticated) {
            const interval = setInterval(() => {
                checkTokenExpiration();
            }, 60000); //Every minute check

            return () => clearInterval(interval);
        }
    }, [isAuthenticated]);

    const checkAuthStatus = () => {
        const accessToken = sessionStorage.getItem('accessToken');
        const accessTokenExpiration = sessionStorage.getItem('accessTokenExpiration');

        if (accessToken && accessTokenExpiration) {
            const now = new Date().getTime();
            const expiration = new Date(accessTokenExpiration).getTime();

            if (now < expiration) {
                setIsAuthenticated(true);
            } else {
                logout();
            }
        }
        setLoading(false);
    };

    const checkTokenExpiration = () => {
        const accessTokenExpiration = sessionStorage.getItem('accessTokenExpiration');

        if (accessTokenExpiration) {
            const now = new Date().getTime();
            const expiration = new Date(accessTokenExpiration).getTime();

            if (now >= expiration) {
                logout();
                //Placeholder to refresh token
            }
        }
    };

    const login = (tokenData) => {
        sessionStorage.setItem('accessToken', tokenData.accessToken);
        sessionStorage.setItem('refreshToken', tokenData.refreshToken);
        sessionStorage.setItem('accessTokenExpiration', tokenData.accessTokenExpiration);
        sessionStorage.setItem('refreshTokenExpiration', tokenData.refreshTokenExpiration);
        sessionStorage.setItem('tokenType', tokenData.tokenType);

        setIsAuthenticated(true);
    };

    const logout = () => {
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('refreshToken');
        sessionStorage.removeItem('accessTokenExpiration');
        sessionStorage.removeItem('refreshTokenExpiration');
        sessionStorage.removeItem('tokenType');

        setIsAuthenticated(false);
    };

    const fetchWithAuth = async (url, options = {}) => {
        const accessToken = sessionStorage.getItem('accessToken');

        if (!accessToken) {
            throw new Error('No access token available');
        }

        const response = await fetch(url, {
            ...options,
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json',
                ...options.headers,
            },
        });

        if (response.status === 401) {
            //Expired token or invalid
            logout();
            throw new Error('Authentication expired');
        }

        return response;
    };

    const value = {
        isAuthenticated,
        login,
        logout,
        loading,
        fetchWithAuth
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};