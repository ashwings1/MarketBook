import React, { useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import { useNavigate, Link } from 'react-router-dom';

const ProductsGrid = () => {
    const { fetchWithAuth, isAuthenticated } = useAuth();
    const navigate = useNavigate();
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        if (isAuthenticated) {
            fetchProducts();
        }
    }, [isAuthenticated]);

    const fetchProducts = async () => {
        try {
            setLoading(true);
            setError('');

            const response = await fetchWithAuth('http://localhost:8000/products');

            const data = await response.json();

            console.log('Parsed JSON data:', data);
            console.log('Data type:', typeof data);
            console.log('Is array?', Array.isArray(data));

            if (Array.isArray(data)) {
                setProducts(data);
            } else {
                console.error('Expected array but got:', data);
                setError('Invalid data format received from server');
            }

        } catch (error) {
            console.error('Error fetching products:', error);
            setError('Failed to fetch products');
        } finally {
            setLoading(false);
        }
    };

    const handleProductClick = (productId) => {
        navigate(`/products/${productId}`);
    };

    return (
        <div style={{ minHeight: '100vh', backgroundColor: '#f9fafb', padding: '16px' }}>
            <div style={{ maxWidth: '1280px', margin: '0 auto' }}>
                <div style={{
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    marginBottom: '32px',
                    paddingBottom: '16px',
                    borderBottom: '1px solid #e5e7eb'
                }}>
                    <h1 style={{ fontSize: '1.875rem', fontWeight: 'bold', color: '#111827', margin: 0 }}>
                        Products
                    </h1>

                    <div style={{ display: 'flex', gap: '16px', alignItems: 'center' }}>
                        <Link
                            to="/"
                            style={{
                                padding: '8px 16px',
                                color: '#6b7280',
                                textDecoration: 'none',
                                fontSize: '14px',
                                fontWeight: '500',
                                borderRadius: '6px',
                                transition: 'background-color 0.2s'
                            }}
                            onMouseEnter={(e) => {
                                e.target.style.backgroundColor = '#f3f4f6';
                            }}
                            onMouseLeave={(e) => {
                                e.target.style.backgroundColor = 'transparent';
                            }}
                        >
                            Home
                        </Link>

                        <Link
                            to="/account"
                            style={{
                                padding: '10px 20px',
                                backgroundColor: '#3b82f6',
                                color: 'white',
                                textDecoration: 'none',
                                fontSize: '14px',
                                fontWeight: '600',
                                borderRadius: '8px',
                                transition: 'background-color 0.2s',
                                boxShadow: '0 1px 3px 0 rgba(0, 0, 0, 0.1)'
                            }}
                            onMouseEnter={(e) => {
                                e.target.style.backgroundColor = '#2563eb';
                            }}
                            onMouseLeave={(e) => {
                                e.target.style.backgroundColor = '#3b82f6';
                            }}
                        >
                            Account
                        </Link>
                    </div>
                </div>

                <div style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fill, minmax(250px, 1fr))',
                    gap: '24px'
                }}>
                    {products.map((product) => (
                        <div
                            key={product.id}
                            style={{
                                backgroundColor: 'white',
                                borderRadius: '8px',
                                boxShadow: '0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06)',
                                border: '1px solid #e5e7eb',
                                cursor: 'pointer',
                                transition: 'box-shadow 0.2s',
                            }}
                            onClick={() => handleProductClick(product.id)}
                            onMouseEnter={(e) => {
                                e.target.style.boxShadow = '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)';
                            }}
                            onMouseLeave={(e) => {
                                e.target.style.boxShadow = '0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06)';
                            }}
                        >
                            <div style={{ padding: '16px' }}>
                                {/*Product Image*/}
                                <div style={{
                                    width: '100%',
                                    height: '192px',
                                    background: 'linear-gradient(135deg, #dbeafe 0%, #bfdbfe 100%)',
                                    display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'center',
                                    transition: 'background 0.2s'
                                }}>
                                    <span style={{
                                        color: '#2563eb',
                                        fontWeight: '600',
                                        fontSize: '18px'
                                    }}>
                                        {product.name.charAt(0)}
                                    </span>
                                </div>
                            </div>

                            {/*Product Name*/}
                            <h3 style={{
                                fontSize: '14px',
                                fontWeight: '500',
                                color: '#111827',
                                marginBottom: '8px',
                                lineHeight: '1.25',
                                display: '-webkit-box',
                                WebkitLineClamp: 2,
                                WebkitBoxOrient: 'vertical',
                                overflow: 'hidden'
                            }}>
                                {product.name}
                            </h3>

                            {/*Rating FUTURE*/}

                            <div cstyle={{ display: 'flex', alignItems: 'center' }}>
                                <span style={{
                                    fontSize: '18px',
                                    fontWeight: '600',
                                    color: '#111827'
                                }}>
                                    ${product.price}
                                </span>
                            </div>

                            {/*Add to Cart FUTURE*/}
                            <button style={{
                                width: '100%',
                                marginTop: '12px',
                                backgroundColor: '#3b82f6',
                                color: 'white',
                                fontSize: '14px',
                                fontWeight: '500',
                                padding: '8px 16px',
                                borderRadius: '4px',
                                border: 'none',
                                cursor: 'pointer',
                                transition: 'background-color 0.2s'
                            }}
                                onMouseEnter={(e) => {
                                    e.target.style.backgroundColor = '#2563eb';
                                }}
                                onMouseLeave={(e) => {
                                    e.target.style.backgroundColor = '#3b82f6';
                                }}
                                onClick={(e) => {
                                    e.stopPropagation();
                                    //Add to cart login in future
                                }}>
                                Add to Cart
                            </button>
                        </div>
                    ))}
                </div>
            </div>
        </div >
    );
};

export default ProductsGrid;