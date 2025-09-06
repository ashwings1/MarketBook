import React, { useState } from 'react';
import { useCart } from './CartContext';
import { Minus, Plus, Trash2 } from 'lucide-react';

const CartItem = ({ item }) => {
    const { updateQuantity, removeItem } = useCart();
    const [loading, setLoading] = useState(false);
    const [quantity, setQuantity] = useState(item.quantity);

    const handleQuantityChange = async (newQuantity) => {
        if (newQuantity < 1) return;

        setLoading(true);
        setQuantity(newQuantity);

        const success = await updateQuantity(item.productId, newQuantity);
        if (!success) {
            setQuantity(item.quantity);
        }
        setLoading(false);
    };

    const handleRemove = async () => {
        if (window.confirm('Remove this item from your cart?')) {
            setLoading(true);
            await removeItem(item.productId);
            setLoading(false);
        }
    };

    const handleIncrement = () => {
        handleQuantityChange(quantity + 1);
    };

    const handleDecrement = () => {
        if (quantity > 1) {
            handleQuantityChange(quantity - 1);
        }
    };

    const subtotal = (item.productPrice || 0) * quantity;

    return (
        <div style={{
            display: 'flex',
            padding: '20px',
            backgroundColor: 'white',
            borderRadius: '8px',
            border: '1px solid #dee2e6',
            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
            opacity: loading ? 0.6 : 1,
            transition: 'opacity 0.2s'
        }}>
            <div style={{ marginRight: '20px', flexShrink: 0 }}>
                <div style={{
                    width: '100px',
                    height: '100px',
                    backgroundColor: '#f8f9fa',
                    borderRadius: '6px',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    border: '1px solid #dee2e6'
                }}>
                    {item.productImage ? (
                        <img
                            src={item.productImage}
                            alt={item.productName}
                            style={{
                                width: '100%',
                                height: '100%',
                                objectFit: 'cover',
                                borderRadius: '6px'
                            }}
                        />
                    ) : (
                        <div style={{ color: '#6c757d', fontSize: '24px' }}>📦</div>
                    )}
                </div>
            </div>

            <div style={{ flex: '1', minWidth: '0' }}>
                <h3 style={{
                    margin: '0 0 8px 0',
                    fontSize: '18px',
                    fontWeight: '500',
                    color: '#333'
                }}>
                    {item.productName || 'Product Name'}
                </h3>

                <p style={{
                    margin: '0 0 12px 0',
                    color: '#6c757d',
                    fontSize: '14px'
                }}>
                    Product ID: {item.productId}
                </p>

                <div style={{
                    display: 'flex',
                    alignItems: 'center',
                    gap: '20px',
                    flexWrap: 'wrap'
                }}>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '8px ' }}>
                        <span style={{ fontSize: '14px', fontWeight: '500' }}>Quantity:</span>
                        <div style={{
                            display: 'flex',
                            alignItems: 'center',
                            border: '1px solid #dee2e6',
                            borderRadius: '4px',
                            overflow: 'hidden'
                        }}>
                            <button
                                onClick={handleDecrement}
                                disabled={loading || quantity <= 1}
                                style={{
                                    padding: '8px 12px',
                                    border: 'none',
                                    backgroundColor: quantity <= 1 ? '#f8f9fa' : '#fff',
                                    color: quantity <= 1 ? '#6c757d' : '#333',
                                    cursor: quantity <= 1 ? 'not-allowed' : 'pointer',
                                    display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'center'
                                }}
                            >
                                <Minus size={16} />
                            </button>

                            <span style={{
                                padding: '8px 16px',
                                minWidth: '50px',
                                textAlign: 'center',
                                backgroundColor: '#f8f9fa',
                                borderLeft: '1px solid #dee2e6',
                                borderRight: '1px solid #dee2e6',
                                fontWeight: '500'
                            }}>
                                {quantity}
                            </span>

                            <button
                                onClick={handleIncrement}
                                disabled={loading}
                                style={{
                                    padding: '8px 12px',
                                    border: 'none',
                                    backgroundColor: '#fff',
                                    color: '#333',
                                    cursor: loading ? 'not-allowed' : 'pointer',
                                    display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'center'
                                }}
                            >
                                <Plus size={16} />
                            </button>
                        </div>
                    </div>

                    <button
                        onClick={handleRemove}
                        disabled={loading}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            padding: '8px 12px',
                            backgroundColor: '#dc3545',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px',
                            cursor: loading ? 'not-allowed' : 'pointer',
                            fontSize: '14px'
                        }}
                    >
                        <Trash2 size={16} style={{ maringRight: '6px' }} />
                        Remove
                    </button>
                </div>
            </div>

            <div style={{
                textAlign: 'right',
                minWidth: '120px',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between'
            }}>
                <div>
                    <div style={{
                        fontSize: '16px',
                        fontWeight: '500',
                        color: '#333',
                        marginBottom: '4px'
                    }}>
                        ${item.productPrice?.toFixed(2) || '0.00'}
                    </div>
                    <div style={{
                        fontSize: '12px',
                        color: '#6c757d'
                    }}>
                        each
                    </div>
                </div>

                <div style={{
                    fontSize: '18px',
                    fontWeight: 'bold',
                    color: '#007bff',
                    marginTop: '8px'
                }}>
                    ${subtotal.toFixed(2)}
                </div>
                <div style={{
                    fontSize: '12px',
                    color: '#6c757d'
                }}>
                    subtotal
                </div>
            </div>
        </div>
    );
};

export default CartItem;