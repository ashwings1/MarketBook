import React, { useState } from 'react';
import { useCart } from './CartContext';
import { ShoppingCart, Plus, Check } from 'lucide-react';

const AddToCartButton = ({ productId, productName, disabled = false, size = 'medium' }) => {
    const { addToCart, loading } = useCart();
    const [isAdding, setIsAdding] = useState(false);
    const [added, setAdded] = useState(false);

    const handleAddToCart = async () => {
        if (disabled || isAdding) return;

        setIsAdding(true);
        const success = await addToCart(productId, 1);

        if (success) {
            setAdded(true);
            setTimeout(() => setAdded(false), 2000);
        }
        setIsAdding(false);
    };

    const buttonSizes = {
        small: {
            padding: '6px 12px',
            fontSize: '14px',
            iconSize: 16
        },
        medium: {
            padding: '8px 16px',
            fontSize: '16px',
            iconSize: 18
        },
        large: {
            padding: '12px 24px',
            fontSize: '18px',
            iconSize: 20
        }
    };

    const currentSize = buttonSizes[size];

    return (
        <button
            onClick={handleAddToCart}
            disabled={disabled || isAdding || loading}
            style={{
                display: 'flex',
                alignItems: 'center',
                gap: '8px',
                padding: currentSize.padding,
                backgroundColor: added ? '#28a745' : '#007bff',
                color: 'white',
                border: 'none',
                borderRadius: '6px',
                cursor: (disabled || isAdding || loading) ? 'not-allowed' : 'pointer',
                fontSize: currentSize.fontSize,
                fontWeight: '500',
                transition: 'all 0.2s ease',
                opacity: (disabled || isAdding || loading) ? 0.6 : 1,
                transform: added ? 'scale(1.05)' : 'scale(1)',
                boxShadow: added ? '0 4px 8px rgba(40, 167, 69, 0.3)' : '0 2px 4px rgba(0, 123, 255, 0.2)'
            }}
            onMouseEnter={(e) => {
                if (!disabled && !isAdding && !loading) {
                    e.target.style.transfrom = 'translateY(-1px)';
                    e.target.style.boxShadow = added
                        ? '0 6px 12px rgba(40, 167, 69, 0.4)'
                        : '0 4px 8px rgba(0, 123, 255, 0.3)';
                }
            }}
            onMouseLeave={(e) => {
                if (!disabled && !isAdding && !loading) {
                    e.target.style.transform = added ? 'scale(1.05)' : 'scale(1)';
                    e.target.style.boxShadow = added
                        ? '0 4px 8px rgba(40, 167, 69, 0.3)'
                        : '0 2px 4px rgba(0, 123, 255, 0.2)';
                }
            }}
        >
            {added ? (
                <>
                    <Check size={currentSize.iconSize} />
                    Added!
                </>
            ) : isAdding ? (
                <>
                    <div style={{
                        width: currentSize.iconSize,
                        height: currentSize.iconSize,
                        border: '2px solid transparent',
                        borderTop: '2px solid white',
                        borderRadius: '50%',
                        animation: 'spin 1s linear infinite'
                    }} />
                    Adding...
                </>
            ) : (
                <>
                    <ShoppingCart size={currentSize.iconSize} />
                    Add to Cart
                </>
            )}
        </button>
    );
};

export default AddToCartButton;