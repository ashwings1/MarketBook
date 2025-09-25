import React, { useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import { useCart } from './CartContext';
import { useNavigate, Link } from 'react-router-dom';
import AddToCartButton from './AddToCartButton';

const ProductsGrid = () => {
    const { fetchWithAuth, isAuthenticated } = useAuth();
    const { addToCart } = useCart();
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

    const handleAddToCart = async (productId) => {
        if (!isAuthenticated) {
            navigate('/login');
            return;
        }

        const success = await addToCart(productId, 1);
        if (success) {
            console.log('Product added to cart successfully');
        }
    };

    return (
        <div style={{ minHeight: '100vh', padding: '20px' }}>
            <div style={{ marginBottom: '30px' }}>
                <h1 style={{ marginBottom: '10px' }}>Products</h1>
                <Link
                    to="/"
                    style={{
                        color: '#007bff',
                        textDecoration: 'none',
                        fontSize: '16px'
                    }}
                >
                    ← Back to Home
                </Link>
            </div>

            {loading && (
                <div style={{ textAlign: 'center', padding: '40px' }}>
                    <div style={{ fontSize: '18px' }}>Loading products...</div>
                </div>
            )}

            {error && (
                <div style={{
                    textAlign: 'center',
                    padding: '40px',
                    color: 'red',
                    backgroundColor: '#f8d7da',
                    border: '1px solid #f5c6cb',
                    borderRadius: '4px',
                    marginBottom: '20px'
                }}>
                    <div style={{ fontSize: '18px', marginBottom: '10px' }}>Error</div>
                    <div>{error}</div>
                    <button
                        onClick={fetchProducts}
                        style={{
                            marginTop: '10px',
                            padding: '8px 16px',
                            backgroundColor: '#007bff',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px',
                            cursor: 'pointer'
                        }}
                    >
                        Retry
                    </button>
                </div>
            )}

            {!loading && !error && (
                <div style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))',
                    gap: '20px'
                }}>
                    {products.map((product) => (
                        <div
                            key={product.id}
                            style={{
                                border: '1px solid #dee2e6',
                                borderRadius: '8px',
                                padding: '20px',
                                backgroundColor: 'white',
                                boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
                                transition: 'transform 0.2s ease, box-shadow 0.2s ease',
                                cursor: 'pointer'
                            }}
                            onMouseEnter={(e) => {
                                e.target.style.transform = 'translateY(-2px)';
                                e.target.style.boxShadow = '0 4px 8px rgba(0,0,0,0.15)';
                            }}
                            onMouseLeave={(e) => {
                                e.target.style.transform = 'translateY(0)';
                                e.target.style.boxShadow = '0 2px 4px rgba(0,0,0,0.1)';
                            }}
                        >
                            <div
                                onClick={() => handleProductClick(product.id)}
                                style={{ marginBottom: '15px' }}
                            >
                                <div style={{
                                    width: '100%',
                                    height: '200px',
                                    backgroundColor: '#f8f9fa',
                                    borderRadius: '6px',
                                    marginBottom: '15px',
                                    display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'center',
                                    border: '1px solid #dee2e6'
                                }}>
                                    {product.imageUrl ? (
                                        <img
                                            src={product.imageUrl}
                                            alt={product.name}
                                            style={{
                                                width: '100%',
                                                height: '100%',
                                                objectFit: 'cover',
                                                borderRadius: '6px'
                                            }}
                                        />
                                    ) : (
                                        <div style={{ color: '#6c757d', fontSize: '48px' }}>📦</div>
                                    )}
                                </div>

                                <h3 style={{
                                    margin: '0 0 8px 0',
                                    fontSize: '18px',
                                    fontWeight: '500',
                                    color: '#333'
                                }}>
                                    {product.name}
                                </h3>

                                <p style={{
                                    margin: '0 0 12px 0',
                                    color: '#6c757d',
                                    fontSize: '14px',
                                    lineHeight: '1.4'
                                }}>
                                    {product.description}
                                </p>

                                <div style={{
                                    display: 'flex',
                                    justifyContent: 'space-between',
                                    alignItems: 'center',
                                    marginBottom: '15px'
                                }}>
                                    <span style={{
                                        fontSize: '20px',
                                        fontWeight: 'bold',
                                        color: '#007bff'
                                    }}>
                                        ${product.price?.toFixed(2) || '0.00'}
                                    </span>
                                    <span style={{
                                        fontSize: '12px',
                                        color: '#6c757d',
                                        backgroundColor: '#e9ecef',
                                        padding: '4px 8px',
                                        borderRadius: '12px'
                                    }}>
                                        {product.category}
                                    </span>
                                </div>
                            </div>

                            <div style={{ display: 'flex', gap: '10px' }}>
                                <button
                                    onClick={() => handleProductClick(product.id)}
                                    style={{
                                        flex: '1',
                                        padding: '10px',
                                        backgroundColor: '#f8f9fa',
                                        color: '#333',
                                        border: '1px solid #dee2e6',
                                        borderRadius: '6px',
                                        cursor: 'pointer',
                                        fontSize: '14px',
                                        fontWeight: '500'
                                    }}
                                >
                                    View Details
                                </button>

                                <AddToCartButton
                                    productId={product.id}
                                    productName={product.name}
                                    size="small"
                                />
                            </div>
                        </div>
                    ))}
                </div>
            )}

            {!loading && !error && products.length === 0 && (
                <div style={{
                    textAlign: 'center',
                    padding: '60px 20px',
                    backgroundColor: '#f8f9fa',
                    borderRadius: '8px',
                    border: '1px solid #dee2e6'
                }}>
                    <div style={{ fontSize: '48px', marginBottom: '20px' }}>📦</div>
                    <h2 style={{ color: '#6c757d', marginBottom: '10px' }}>No products available</h2>
                    <p style={{ color: '#6c757d' }}>
                        Check back later for new products!
                    </p>
                </div>
            )}
        </div>
    );


    /*
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
                                {//Product Image}
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

                            {//Product Name}
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

                            {//Rating FUTURE}

                            <div cstyle={{ display: 'flex', alignItems: 'center' }}>
                                <span style={{
                                    fontSize: '18px',
                                    fontWeight: '600',
                                    color: '#111827'
                                }}>
                                    ${product.price}
                                </span>
                            </div>

                            {//Add to Cart FUTURE}
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
    */
};

export default ProductsGrid;