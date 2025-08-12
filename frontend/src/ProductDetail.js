import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { useAuth } from './AuthContext';

const ProductDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const { fetchWithAuth, isAuthenticated } = useAuth();
    const [product, setProduct] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    console.log('ProductDetail component rendered with id:', id);
    console.log('isAuthenticated:', isAuthenticated);

    useEffect(() => {
        console.log('useEffect triggered - id:', id, 'isAuthenticated:', isAuthenticated);
        if (isAuthenticated) {
            fetchProductDetail();
        }
    }, [id, isAuthenticated]);

    const fetchProductDetail = async () => {
        console.log('fetchProductDetail called with id:', id);
        try {
            setLoading(true);
            setError('');

            const response = await fetchWithAuth(`http://localhost:8000/products/${id}`);
            console.log('API response:', response);

            if (!response.ok) {
                if (response.status === 404) {
                    setError('Product not found');
                } else {
                    setError('Failed to fetch product details');
                }
                return;
            }

            const data = await response.json();
            console.log('Product data received:', data);
            setProduct(data);
        } catch (error) {
            console.error('Error fetching product details:', error);
            setError('Failed to fetch product details');
        } finally {
            setLoading(false);
        }
    };

    const handleAddToCart = () => {
        //Future requirement, placeholder for now
        console.log('Adding to cart:', product.id);
    };

    if (loading) {
        return (
            <div style={{
                minHeight: '100vh',
                backgroundColor: '#f9fafb',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center'
            }}>
                <div style={{ fontSize: '18px', color: '#6b7280' }}>Loading...</div>
            </div>
        );
    }

    if (error) {
        return (
            <div style={{
                minHeight: '100vh',
                backgroundColor: '#f9fafb',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                flexDirection: 'column',
                gap: '16px'
            }}>
                <div style={{ fontSize: '18px', color: '#dc2626' }}>{error}</div>
                <button
                    onClick={() => navigate('/products')}
                    style={{
                        padding: '8px 16px',
                        backgroundColor: '#3b82f6',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer'
                    }}
                >
                    Back to Products
                </button>
            </div>
        );
    }

    if (!product) {
        return null;
    }

    return (
        <div style={{ minHeight: '100vh', backgroundColor: '#f9fafb', padding: '16px' }}>
            <div style={{ maxWidth: '1200px', margin: '0 auto' }}>
                <div style={{
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    marginBottom: '32px',
                    paddingBottom: '16px',
                    borderBottom: '1px solid #e5e7eb'
                }}>
                    <h1 style={{ fontSize: '1.875rem', fontWeight: 'bold', color: '#111827', margin: 0 }}>
                        Product Details
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
                            to="/products"
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
                            Products
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

                {/* Product Detail Container */}
                <div style={{
                    backgroundColor: 'white',
                    borderRadius: '12px',
                    boxShadow: '0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06)',
                    overflow: 'hidden'
                }}>
                    <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '48px' }}>
                        <div style={{ padding: '48px' }}>
                            <div style={{
                                width: '100%',
                                height: '400px',
                                background: 'linear-gradient(135deg, #dbeafe 0%, #bfdbfe 100%)',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                borderRadius: '8px',
                                border: '1px solid #e5e7eb'
                            }}>
                                <span style={{
                                    color: '#2563eb',
                                    fontWeight: '600',
                                    fontSize: '48px'
                                }}>
                                    {product.name.charAt(0)}
                                </span>
                            </div>
                        </div>

                        <div style={{ padding: '48px', display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
                            <h1 style={{
                                fontSize: '2.25rem',
                                fontWeight: 'bold',
                                color: '#111827',
                                marginBottom: '16px',
                                lineHeight: '1.2'
                            }}>
                                {product.name}
                            </h1>

                            <div style={{ marginBottom: '24px' }}>
                                <span style={{
                                    fontSize: '2rem',
                                    fontWeight: 'bold',
                                    color: '#059669'
                                }}>
                                    ${product.price}
                                </span>
                            </div>

                            {product.category && (
                                <div style={{ marginBottom: '24px' }}>
                                    <span style={{
                                        fontSize: '14px',
                                        color: '#6b7280',
                                        backgroundColor: '#f3f4f6',
                                        padding: '4px 12px',
                                        borderRadius: '16px'
                                    }}>
                                        {product.category}
                                    </span>
                                </div>
                            )}

                            {product.description && (
                                <div style={{ marginBottom: '32px' }}>
                                    <h3 style={{
                                        fontSize: '18px',
                                        fontWeight: '600',
                                        color: '#6b7280',
                                        marginBottom: '12px'
                                    }}>
                                        Description
                                    </h3>
                                    <p style={{
                                        fontSize: '16px',
                                        color: '#4b5563',
                                        lineHeight: '1.6'
                                    }}>
                                        {product.description}
                                    </p>
                                </div>
                            )}

                            <button
                                onClick={handleAddToCart}
                                style={{
                                    width: '100%',
                                    backgroundColor: '#3b82f6',
                                    color: 'white',
                                    fontSize: '18px',
                                    padding: '16px 24px',
                                    borderRadius: '8px',
                                    border: 'none',
                                    cursor: 'pointer',
                                    transition: 'background-color 0.2s',
                                    marginBottom: '16px'
                                }}
                                onMouseEnter={(e) => {
                                    e.target.style.backgroundColor = '#2563eb';
                                }}
                                onMouseLeave={(e) => {
                                    e.target.style.backgroundColor = '#3b82f6'
                                }}
                            >
                                Add to cart
                            </button>

                            {/* Product Metadata */}
                            <div style={{
                                borderTop: '1px solid #e5e7eb',
                                paddingTop: '24px',
                                fontSize: '14px',
                                color: '#6b7280'
                            }}>
                                <div style={{ marginBottom: '8px ' }}>
                                    Product ID: {product.id}
                                </div>
                                {product.createdAt && (
                                    <div style={{ marginBottom: '8px' }}>
                                        Listed: {new Date(product.createdAt).toLocaleDateString()}
                                    </div>
                                )}
                                {product.updatedAt && (
                                    <div>
                                        Last updated: {new Date(product.updatedAt).toLocaleDateString()}
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductDetail;