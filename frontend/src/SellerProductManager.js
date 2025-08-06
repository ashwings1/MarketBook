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
    });

    const isSeller = user?.role === 'SELLER';

    if (!isSeller) {
        return (
            <div className="not-seller-container" style={{
                borderColor: '#FF0000',
                textColor: '#FF0000',
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

    /* IMPLEMENT INDIVIDUAL SELLER TABLE THAT HAS INFORMATION OF EACH SELLERS PRODUCTS
    const fetchSellerProducts = async () => {
        setLoading(true);
        try {
           // const response = await fetchWithAuth('http://localhost:8000/')
        }
    }
    */

}