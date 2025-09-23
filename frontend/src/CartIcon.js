import React from 'react';
import { Link } from 'react-router-dom';
import { useCart } from './CartContext';
import { ShoppingBag } from 'lucide-react';

const CartIcon = () => {
    const { cart } = useCart();
    const itemCount = cart.summary?.totalItems || 0;

    return (
        <Link
            to="/cart"
            style={{
                position: 'relative',
                display: 'inline-flex',
                alignItems: 'center',
                justifyContent: 'center',
                padding: '8px',
                textDecoration: 'none',
                color: '#333',
                borderRadius: '50%',
                transiiton: 'all 0.2s ease',
                backgroundColor: 'transparent'
            }}
            onMouseEnter={(e) => {
                e.target.style.backgroundColor = '#f8f9fa';
            }}
            onMouseLeave={(e) => {
                e.target.style.backgroundColor = 'transparent';
            }}
        >
            <ShoppingBag size={24} />
            {itemCount > 0 && (
                <span style={{
                    positon: 'absolute',
                    top: '-2px',
                    right: '-2px',
                    backgroundColor: '#dc3545',
                    color: 'white',
                    borderRadius: '50%',
                    minWidth: '20px',
                    height: '20px',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    fontSize: '12px',
                    fontWeight: 'bold',
                    border: '2px solid white',
                    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.2)'
                }}>
                    {itemCount > 99 ? '99+' : itemCount}
                </span>
            )}
        </Link>
    );
};

export default CartIcon;

