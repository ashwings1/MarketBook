import React, { useEffect } from 'react';
import { useCart } from './CartContext';
import { useAuth } from './AuthContext';
import { Link } from 'react-router-dom';
import CartItem from './CartItem';
import { ShoppingBag, ArrowLeft, Trash2 } from 'lucide-react';

const ShoppingCart = () => {
    const { cart, loading, error, clearCart } = useCart();
    const { isAuthenticated } = useAuth();

    useEffect(() => {
        if (!isAuthenticated) {
            window.location.href = '/login';
        }
    }, [isAuthenticated]);

    const handleClearCart = async () => {
        if (window.confirm('Are you sure you want to clear your cart?')) {
            await clearCart();
        }
    };

    if (!isAuthenticated) {
        return (
            <div style={{ padding: '20px', textAlign: 'center' }}>
                <p>Please log in to view your cart.</p>
                <Link to="/login">Go to Login</Link>
            </div>
        );
    }

    if (loading) {
        return (
            <div style={{ padding: '20px', textAlign: 'center' }}>
                <div style={{ fontSize: '18px' }}>Loading your cart...</div>
            </div>
        );
    }

    if (error) {
        return (
            <div style={{ padding: '20px', textAlign: 'center' }}>
                <div style={{ color: 'red', marginBottom: '10px' }}>Error: {error}</div>
                <button onClick={() => window.location.reload()}>Retry</button>
            </div>
        );
    }

    return (
        <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '20px ' }}>
            <div style={{ marginBottom: '20px' }}>
                <Link
                    to="/products"
                    style={{
                        display: 'inline-flex',
                        alignItems: 'center',
                        textDecoration: 'none',
                        color: '#007bff',
                        marginBottom: '20px'
                    }}
                >
                    <ArrowLeft size={20} style={{ marginRight: '8px' }} />
                    Back to Products
                </Link>
                <h1 style={{ display: 'flex', alignItems: 'center', margin: '0' }}>
                    <ShoppingBag size={32} style={{ marginRight: '12px' }} />
                    Shopping Cart
                </h1>
            </div>

            {cart.items && cart.items.length === 0 ? (
                <div style={{
                    textAlign: 'center',
                    padding: '60px 20px',
                    backgroundColor: '#f8f9fa',
                    borderRadius: '8px',
                    border: '1px solid #dee2e6'
                }}>
                    <ShoppingBag size={64} style={{ color: '#6c757d', marginBottom: '20px' }} />
                    <h2 style={{ color: '#6c757d', marginBottom: '10px' }}>Your cart is empty</h2>
                    <p style={{ color: '#6c757d', marginBottom: '20px' }}>
                        Add some products to get started!
                    </p>
                    <Link
                        to="/products"
                        style={{
                            display: 'inline-block',
                            padding: '12px 24px',
                            backgroundColor: '#007bff',
                            color: white,
                            textDecoration: 'none',
                            borderRadius: '6px',
                            fontWeight: '500'
                        }}
                    >
                        Browse Products
                    </Link>
                </div>
            ) : (
                <div style={{ display: 'flex', gap: '20px', flexWrap: 'wrap' }}>
                    <div style={{ flex: '2', minWidth: '300px' }}>
                        <div style={{
                            display: 'flex',
                            justifyContent: 'space-between',
                            alignItems: 'center',
                            marginBottom: '20px',
                            padding: '15px',
                            backgroundColor: '#f8f9fa',
                            borderRadius: '6px'
                        }}>
                            <h2 style={{ margin: '0' }}>
                                Items ({cart.summary.uniqueItems})
                            </h2>
                            {cart.items && cart.items.length > 0 && (
                                <button
                                    onClick={handleClearCart}
                                    style={{
                                        display: 'flex',
                                        alignItems: 'center',
                                        padding: '8px 12px',
                                        backgroundColor: '#dc3545',
                                        color: 'white',
                                        border: 'none',
                                        borderRadius: '4px',
                                        cursor: 'pointer',
                                        fontSize: '14px'
                                    }}
                                >
                                    <Trash2 size={16} style={{ marginRight: '6px' }} />
                                    Clear Cart
                                </button>
                            )}
                        </div>

                        <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
                            {cart.items && cart.items.map((item) => (
                                <CartItem key={item.id} item={item} />
                            ))}
                        </div>
                    </div>

                    <div style={{ flex: '1', minWidth: '300px' }}>
                        <div style={{
                            backgroundColor: '#f8f9fa',
                            padding: '20px',
                            borderRadius: '8px',
                            border: '1px solid #dee2e6',
                            position: 'sticky',
                            top: '20px'
                        }}>
                            <h3 style={{ marginTop: '0', marginBottom: '20px' }}>Order Summary</h3>

                            <div style={{ marginBottom: '15px' }}>
                                <div style={{
                                    display: 'flex',
                                    justifyContent: 'space-between',
                                    marginBottom: '8px'
                                }}>
                                    <span>Items ({cart.summary.totalItems}):</span>
                                    <span>${cart.summary.totalAmount?.toFixed(2) || '0.00'}</span>
                                </div>

                                <div style={{
                                    display: 'flex',
                                    justifyContent: 'space-between',
                                    marginBottom: '8px'
                                }}>
                                    <span>Shipping:</span>
                                    <span style={{ color: '#28a745' }}>Free</span>
                                </div>

                                <hr style={{ margin: '15px 0' }} />

                                <div style={{
                                    display: 'flex',
                                    justifyContent: 'space-between',
                                    fontSize: '18px',
                                    fontWeight: 'bold'
                                }}>
                                    <span>Total:</span>
                                    <span>${cart.summary.totalAmount?.toFixed(2) || '0.00'}</span>
                                </div>
                            </div>

                            <button
                                style={{
                                    width: '100%',
                                    padding: '12px',
                                    backgroundColor: '#28a745',
                                    color: 'white',
                                    border: 'none',
                                    borderRadius: '6px',
                                    fontSize: '16px',
                                    fontWeight: '500',
                                    cursor: 'pointer',
                                    marginBottom: '10px'
                                }}
                                disabled={cart.items && cart.items.length === 0}
                            >
                                Proceed to Checkout
                            </button>

                            <p style={{
                                fontSize: '12px',
                                color: '#6c757d',
                                textAlign: 'center',
                                margin: '0'
                            }}>
                                Checkout functionality coming soon
                            </p>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ShoppingCart;