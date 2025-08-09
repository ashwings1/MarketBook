import React, { useState, useEffect } from 'react';
import { Plus, Edit, Trash2, Save, X } from 'lucide-react';
import { useAuth } from './AuthContext';

const SellerProductManager = ({ user }) => {
    const { fetchWithAuth } = useAuth();
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [showCreateForm, setShowCreateForm] = useState(false);
    const [editingProduct, setEditingProduct] = useState(null);
    const [error, setError] = useState(null);

    const [formData, setFormData] = useState({
        name: '',
        description: '',
        price: '',
        category: '',
        imageUrl: ''
    });

    const isSeller = user?.role === 'SELLER';

    if (!isSeller) {
        return (
            <div className="not-seller-container" style={{
                borderColor: '#FF0000',
                color: '#FF0000',
                borderRadius: '25px'
            }}>
                <h3 className="denied" style={{ fontWeight: 'bold' }}>Access Denied</h3>
                <p>You need Seller privileges to access this section.</p>
            </div>
        );
    }

    useEffect(() => {
        fetchSellerProducts();
    }, []);

    const fetchSellerProducts = async () => {
        setLoading(true);
        try {
            const response = await fetchWithAuth(`http://localhost:8000/products/seller/${user.id}`);

            if (response.ok) {
                const data = await response.json();
                setProducts(data);
            } else if (response.status === 403) {
                setError('Access denied. Seller privileges required.');
            } else {
                setError('Failed to fetch products');
            }
        } catch (error) {
            setError('Error fetching products');
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const resetForm = () => {
        setFormData({
            name: '',
            description: '',
            price: '',
            category: '',
            imageUrl: ''
        });
        setShowCreateForm(false);
        setEditingProduct(null);
        setError(null);
    };

    const handleCreate = async () => {
        try {
            const response = await fetchWithAuth('http://localhost:8000/product/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    ...formData,
                    price: parseFloat(formData.price)
                })
            });

            if (response.ok) {
                const newProduct = await response.json();
                setProducts(prev => [...prev, newProduct]);
                resetForm();
                setError(null);
            } else if (response.status === 403) {
                setError('Access denied. Seller privileges required.');
            } else {
                setError('Failed to create product');
            }
        } catch (error) {
            setError('Error creating product');
            console.error(error);
        }
    };

    const handleUpdate = async () => {
        try {
            const response = await fetchWithAuth(`http://localhost:8000/product/${editingProduct.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    ...formData,
                    price: parseFloat(formData.price)
                })
            });

            if (response.ok) {
                const updatedProduct = await response.json();
                setProducts(prev => prev.map(p => p.id === updatedProduct.id ? updatedProduct : p));
                resetForm();
                setError(null);
            } else if (response.status === 403) {
                setError('Access denied. You can only update your own products.');
            } else {
                setError('Failed to update product');
            }
        } catch (error) {
            setError('Error updating product');
            console.error(error);
        }
    };

    const handleDelete = async (productId) => {
        if (!window.confirm('Are you sure you want to delete this product?')) {
            return;
        }

        try {
            const response = await fetchWithAuth(`http://localhost:8000/product/${productId}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                setProducts(prev => prev.filter(p => p.id !== productId));
                setError(null);
            } else if (response.status === 403) {
                setError('Access denied. You can only delete your own products.');
            } else {
                setError('Failed to delete product');
            }
        } catch (error) {
            setError('Error deleting product');
            console.error(error);
        }
    };

    const startEdit = (product) => {
        setEditingProduct(product);
        setFormData({
            name: product.name,
            description: product.description,
            price: product.price.toString(),
            category: product.category,
            imageUrl: product.imageUrl || ''
        });
        setShowCreateForm(false);
    };

    const startCreate = () => {
        resetForm();
        setShowCreateForm(true);
    };

    const spinKeyframes = `
    @keyframes spin {
        from {
            transform: rotate(0deg);
        }
        to {
            transform: rotate(360deg);
        }
     }
    `;

    if (typeof document !== 'undefined') {
        const style = document.createElement('style');
        style.textContent = spinKeyframes;
        document.head.appendChild(style);
    }

    if (loading) {
        return (
            <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                padding: '2rem 0'
            }}>
                <div style={{
                    animation: 'spin 1s linear infinite',
                    borderRadius: '50%',
                    height: '2rem',
                    width: '2rem',
                    border: '2px solid transparent',
                    borderBottom: '2px solid #2563eb'
                }}></div>
            </div>
        );
    }

    return (
        <div style={{
            maxWidth: '72rem',
            margin: '0 auto',
            padding: '1.5rem'
        }}>
            <div style={{
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                marginBottom: '1.5rem'
            }}>
                <h2 style={{
                    fontSize: '1.5rem',
                    fontWeight: 'bold',
                    color: '#1f2937'
                }}>My Products</h2>
                <button
                    onClick={startCreate}
                    style={{
                        backgroundColor: '#2563eb',
                        color: 'white',
                        padding: '0.5rem 1rem',
                        borderRadius: '0.5rem',
                        display: 'flex',
                        alignItems: 'center',
                        gap: '0.5rem',
                        border: 'none',
                        cursor: 'pointer',
                        transition: 'background-color 0.3s'
                    }}
                    onMouseOver={(e) => e.target.style.backgroundColor = '#1d4ed8'}
                    onMouseOut={(e) => e.target.style.backgroundColor = '#2563eb'}
                >
                    <Plus size={20} />
                    Add Product
                </button>
            </div>

            {error && (
                <div style={{
                    backgroundColor: '#f3f2f2',
                    border: '1px solid #fca5a5',
                    color: '#b91c1c',
                    padding: '0.75rem 1rem',
                    borderRadius: '0.25rem',
                    marginBottom: '1rem'
                }}>
                    {error}
                </div>
            )}

            {/* Create/Edit form */}
            {(showCreateForm || editingProduct) && (
                <div style={{
                    backgroundColor: 'white',
                    border: '1px solid #e5e7eb',
                    borderRadius: '0.5rem',
                    padding: '1.5rem',
                    marginBottom: '1.5rem',
                    boxShadow: '0 1px 2px 0 rgba(0, 0, 0, 0.05)'
                }}>
                    <h3 style={{
                        fontSize: '1.125rem',
                        fontWeight: '600',
                        marginBottom: '1rem'
                    }}>
                        {editingProduct ? 'Edit Product' : 'Create New Product'}
                    </h3>
                    <div style={{
                        display: 'flex',
                        flexDirection: 'column',
                        gap: '1rem'
                    }}>
                        <div style={{
                            display: 'grid',
                            gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
                            gap: '1rem'
                        }}>
                            <div>
                                <label style={{
                                    display: 'block',
                                    fontSize: '0.875rem',
                                    fontWeight: '500',
                                    color: '#374151',
                                    marginBottom: '0.25rem'
                                }}>
                                    Product Name *
                                </label>
                                <input
                                    type="text"
                                    name="name"
                                    value={formData.name}
                                    onChange={handleInputChange}
                                    required
                                    style={{
                                        width: '100%',
                                        padding: '0.5rem 0.75rem',
                                        border: '1px solid #d1d5db',
                                        borderRadius: '0.375rem',
                                        outline: 'none',
                                        boxSizing: 'border-box'
                                    }}
                                    onFocus={(e) => e.target.style.outline = '2px solid #3b82f6'}
                                    onBlur={(e) => e.target.style.outline = 'none'}
                                />
                            </div>
                            <div>
                                <label style={{
                                    display: 'block',
                                    fontSize: '0.875rem',
                                    fontWeight: '500',
                                    color: '#374151',
                                    marginBottom: '0.25rem'
                                }}>
                                    Description
                                </label>
                                <textarea
                                    name="description"
                                    value={formData.description}
                                    onChange={handleInputChange}
                                    rows="3"
                                    style={{
                                        width: '100%',
                                        padding: '0.5rem 0.75rem',
                                        border: '1px solid #d1d5db',
                                        borderRadius: '0.375rem',
                                        outline: 'none',
                                        boxSizing: 'border-box',
                                        resize: 'vertical'
                                    }}
                                    onFocus={(e) => e.target.style.outline = '2px solid #3b82f6'}
                                    onBlur={(e) => e.target.style.outline = 'none'}
                                />
                            </div>
                        </div>
                        <div style={{
                            display: 'grid',
                            gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
                            gap: '1rem'
                        }}>
                            <div>
                                <label style={{
                                    display: 'block',
                                    fontSize: '0.875rem',
                                    fontWeight: '500',
                                    color: '#374151',
                                    marginBottom: '0.25rem'
                                }}>
                                    Price *
                                </label>
                                <input
                                    type="number"
                                    name="price"
                                    value={formData.price}
                                    onChange={handleInputChange}
                                    step="0.01"
                                    min="0"
                                    required
                                    style={{
                                        width: '100%',
                                        padding: '0.5rem 0.75rem',
                                        border: '1px solid #d1d5db',
                                        borderRadius: '0.375rem',
                                        outline: 'none',
                                        boxSizing: 'border-box'
                                    }}
                                    onFocus={(e) => e.target.style.outline = '2px solid #3b82f6'}
                                    onBlur={(e) => e.target.style.outline = 'none'}
                                />
                            </div>
                            <div>
                                <label style={{
                                    display: 'block',
                                    fontSize: '0.875rem',
                                    fontWeight: '500',
                                    color: '#374151',
                                    marginBottom: '0.25rem'
                                }}>
                                    Category
                                </label>
                                <input
                                    type="text"
                                    name="category"
                                    value={formData.category}
                                    onChange={handleInputChange}
                                    style={{
                                        width: '100%',
                                        padding: '0.5rem 0.75rem',
                                        border: '1px solid #d1d5db',
                                        borderRadius: '0.375rem',
                                        outline: 'none',
                                        boxSizing: 'border-box'
                                    }}
                                    onFocus={(e) => e.target.style.outline = '2px solid #3b82f6'}
                                    onBlur={(e) => e.target.style.outline = 'none'}
                                />
                            </div>
                        </div>
                        <div>
                            <label style={{
                                display: 'block',
                                fontSize: '0.875rem',
                                fontWeight: '500',
                                color: '#374151',
                                marginBottom: '0.25rem'
                            }}>
                                Image URL
                            </label>
                            <input
                                type="url"
                                name="imageUrl"
                                value={formData.imageUrl}
                                onChange={handleInputChange}
                                style={{
                                    width: '100%',
                                    padding: '0.5rem 0.75rem',
                                    border: '1px solid #d1d5db',
                                    borderRadius: '0.375rem',
                                    outline: 'none',
                                    boxSizing: 'border-box'
                                }}
                                onFocus={(e) => e.target.style.outline = '2px solid #3b82f6'}
                                onBlur={(e) => e.target.style.outline = 'none'}
                            />
                        </div>
                        <div style={{
                            display: 'flex',
                            gap: '0.5rem'
                        }}>
                            <button
                                onClick={editingProduct ? handleUpdate : handleCreate}
                                style={{
                                    backgroundColor: '#16a34a',
                                    color: 'white',
                                    padding: '0.5rem 1rem',
                                    borderRadius: '0.375rem',
                                    display: 'flex',
                                    alignItems: 'center',
                                    gap: '0.5rem',
                                    border: 'none',
                                    cursor: 'pointer',
                                    transition: 'background-color 0.3s'
                                }}
                                onMouseOver={(e) => e.target.style.backgroundColor = '#15803d'}
                                onMouseOut={(e) => e.target.style.backgroundColor = '#16a34a'}
                            >
                                <Save size={16} />
                                {editingProduct ? 'Update' : 'Create'}
                            </button>
                            <button
                                onClick={resetForm}
                                style={{
                                    backgroundColor: '#6b7280',
                                    color: 'white',
                                    padding: '0.5rem 1rem',
                                    borderRadius: '0.375rem',
                                    display: 'flex',
                                    alignItems: 'center',
                                    gap: '0.5rem',
                                    border: 'none',
                                    cursor: 'pointer',
                                    transition: 'background-color 0.3s'
                                }}
                                onMouseOver={(e) => e.target.style.backgroundColor = '#4b5563'}
                                onMouseOut={(e) => e.target.style.backgroundColor = '#6b7280'}
                            >
                                <X size={16} />
                                Cancel
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {/* Products List */}
            {products.length === 0 ? (
                <div style={{
                    textAlign: 'center',
                    padding: '2rem 0',
                    color: '#6b7280'
                }}>
                    <p>No products found. Create your first product to get started!</p>
                </div>
            ) : (
                <div style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
                    gap: '1.5rem'
                }}>
                    {products.map((product) => (
                        <div key={product.id} style={{
                            backgroundColor: 'white',
                            border: '1px solid #e5e7eb',
                            borderRadius: '0.5rem',
                            overflow: 'hidden',
                            boxShadow: '0 1px 2px 0 rgba(0, 0, 0, 0.05)',
                            transition: 'box-shadow 0.3s'
                        }}
                            onMouseOver={(e) => e.currentTarget.style.boxShadow = '0 4px 6px -1px rgba(0, 0, 0, 0.1)'}
                            onMouseOut={(e) => e.currentTarget.style.boxShadow = '0 1px 2px 0 rgba(0, 0, 0, 0.05)'}
                        >
                            {product.imageUrl && (
                                <img
                                    src={product.imageUrl}
                                    alt={product.name}
                                    style={{
                                        width: '100%',
                                        height: '12rem',
                                        objectFit: 'cover'
                                    }}
                                />
                            )}
                            <div style={{
                                padding: '1rem'
                            }}>
                                <h3 style={{
                                    fontWeight: '600',
                                    fontSize: '1.125rem',
                                    color: '#1f2937',
                                    marginBottom: '0.5rem'
                                }}>{product.name}</h3>
                                <p style={{
                                    color: '#4b5563',
                                    fontSize: '0.875rem',
                                    marginBottom: '0.5rem',
                                    display: '-webkit-box',
                                    WebkitLineClamp: 2,
                                    WebkitBoxOrient: 'vertical',
                                    overflow: 'hidden'
                                }}>{product.description}</p>
                                <div style={{
                                    display: 'flex',
                                    justifyContent: 'space-between',
                                    alignItems: 'center',
                                    marginBottom: '0.75rem'
                                }}>
                                    <span style={{
                                        fontSize: '1.5rem',
                                        fontWeight: 'bold',
                                        color: '#16a34a'
                                    }}>
                                        ${product.price?.toFixed(2)}
                                    </span>
                                    {product.category && (
                                        <span style={{
                                            backgroundColor: '#f3f4f6',
                                            color: '#374151',
                                            fontSize: '0.75rem',
                                            padding: '0.25rem 0.5rem',
                                            borderRadius: '0.25rem'
                                        }}>
                                            {product.category}
                                        </span>
                                    )}
                                </div>
                                <div style={{
                                    display: 'flex',
                                    gap: '0.5rem'
                                }}>
                                    <button
                                        onClick={() => startEdit(product)}
                                        style={{
                                            flex: 1,
                                            backgroundColor: '#2563eb',
                                            color: 'white',
                                            padding: '0.5rem 0.75rem',
                                            borderRadius: '0.375rem',
                                            fontSize: '0.875rem',
                                            display: 'flex',
                                            alignItems: 'center',
                                            justifyContent: 'center',
                                            gap: '0.25rem',
                                            border: 'none',
                                            cursor: 'pointer',
                                            transition: 'background-color 0.3s'
                                        }}
                                        onMouseOver={(e) => e.target.style.backgroundColor = '#1d4ed8'}
                                        onMouseOut={(e) => e.target.style.backgroundColor = '#2563eb'}
                                    >
                                        <Edit size={16} />
                                        Edit
                                    </button>
                                    <button
                                        onClick={() => handleDelete(product.id)}
                                        style={{
                                            flex: 1,
                                            backgroundColor: '#dc2626',
                                            color: 'white',
                                            padding: '0.5rem 0.75rem',
                                            borderRadius: '0.375rem',
                                            fontSize: '0.875rem',
                                            display: 'flex',
                                            alignItems: 'center',
                                            justifyContent: 'center',
                                            gap: '0.25rem',
                                            border: 'none',
                                            cursor: 'pointer',
                                            transition: 'background-color 0.3s'
                                        }}
                                        onMouseOver={(e) => e.target.style.backgroundColor = '#b91c1c'}
                                        onMouseOut={(e) => e.target.style.backgroundColor = '#dc2626'}
                                    >
                                        <Trash2 size={16} />
                                        Delete
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default SellerProductManager;




