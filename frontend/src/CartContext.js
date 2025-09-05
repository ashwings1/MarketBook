import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import ProductsGrid from './ProductsGrid';

const CartContext = createContext();

export const useCart = () => {
    const context = useContext(CartContext);
    if (!context) {
        throw new Error('useCart must be used within a CartProvider');
    }

    return context;
};

export const CartProvider = ({ children }) => {
    const { fetchWithAuth, isAuthenticated } = useAuth();
    const [cart, setCart] = useState({
        items: [],
        summary: {
            totalItems: 0,
            totalAmount: 0,
            uniqueItems: 0
        }
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (isAuthenticated) {
            fetchCart();
        } else {
            setCart({
                items: [],
                summary: {
                    totalItems: 0,
                    totalAmount: 0,
                    uniqueItems: 0
                }
            });
        }
    }, [isAuthenticated]);

    const fetchCart = async () => {
        try {
            setLoading(true);
            setError(null);
            const response = await fetchWithAuth('http://localhost:8000/cart');

            if (response.ok) {
                const cartData = await response.json();
                setCart(cartData);
            } else {
                throw new Error('Failed to fetch cart');
            }
        } catch (error) {
            console.error('Error fetching cart:', error);
            setError('Failed to load cart');
        } finally {
            setLoading(false);
        }
    };

    const fetchCartSummary = async () => {
        try {
            const response = await fetchWithAuth('http://localhost:8000/cart/summary');
            if (response.ok) {
                const summary = await response.json();
                setCart(prev => ({
                    ...prev,
                    summary
                }));
            }
        } catch (error) {
            console.error('Error fetching cart summary:', error);
        }
    };

    const addToCart = async (productId, quantity = 1) => {
        try {
            setLoading(true);
            setError(null);

            for (let i = 0; i < quantity; i++) {
                const response = await fetchWithAuth(`http://localhost:8000/cart/${productId}`, {
                    method: 'POST'
                });

                if (!response.ok) {
                    throw new Error('Failed to add items to cart');
                }
            }

            await fetchCart();
            return true;
        } catch (error) {
            console.error('Error adding to cart:', error);
            setError('Failed to add item to cart')
            return false;
        } finally {
            setLoading(false);
        }
    };

    const updateQuantity = async (productId, quantity) => {
        try {
            setLoading(true);
            setError(null);

            const response = await fetchWithAuth(`http://localhost:8000/cart/${productId}`, {
                method: 'PUT',
                body: JSON.stringify({ quantity })
            });

            if (response.ok) {
                await fetchCart();
                return true;
            } else {
                throw new Error('Failed to update quantity');
            }
        } catch (error) {
            console.error('Error updating quantity:', error);
            setError('Failed to update quantity');
            return false;
        } finally {
            setLoading(false);
        }
    };

    const removeItem = async (productId) => {
        try {
            setLoading(true);
            setError(null);

            const response = await fetchWithAuth(`http://localhost:8000/cart/${productId}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                await fetchCart();
                return true;
            } else {
                throw new Error('Failed to remove item');
            }
        } catch (error) {
            console.error('Error removing item:', error);
            setError('Failed to remove item');
            return false;
        } finally {
            setLoading(false);
        }
    };

    const clearCart = async () => {
        try {
            setLoading(true);
            setError(null);

            const response = await fetchWithAuth('http://localhost:8000/cart/clear', {
                method: 'DELETE'
            });

            if (response.ok) {
                setCart({
                    items: [],
                    summary: {
                        totalItems: 0,
                        totalAmount: 0,
                        uniqueItems: 0
                    }
                });

                return true;
            } else {
                throw new Error('Failed to clear cart');
            }
        } catch (error) {
            console.error('Error clearing cart:', error);
            setError('Failed to clear cart');
            return false;
        } finally {
            setLoading(false);
        }
    };

    const value = {
        cart,
        loading,
        error,
        addToCart,
        updateQuantity,
        removeItem,
        clearCart,
        fetchCart
    };

    return (
        <CartContext.Provider value={value}>
            {children}
        </CartContext.Provider>
    );
};