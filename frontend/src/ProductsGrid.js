import React, { useState, useEffect } from 'react';
import { useAuth } from './AuthContext';

const ProductsGrid = () => {
    const { fetchWithAuth, isAuthenticated } = useAuth();
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


    return (
        <div style={{ minHeight: '100vh', backgroundColor: '#f9fafb', padding: '16px' }}>
            <div style={{ maxWidth: '1280px', margin: '0 auto' }}>
                <h1 style={{ fontSize: '1.875rem', fontWeight: 'bold', marginBottom: '32px', color: '#111827' }}>
                    Products
                </h1>

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