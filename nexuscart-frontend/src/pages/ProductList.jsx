import { useState } from 'react';
import { Link } from 'react-router-dom';

const ProductList = () => {
  const [selectedCategory, setSelectedCategory] = useState('all');
  
  const categories = ['all', 'Electronics', 'Clothing', 'Books', 'Home & Garden'];
  
  const products = [
    { id: 1, name: 'Premium Headphones', price: 199.99, category: 'Electronics', image: 'https://via.placeholder.com/300', stock: 50 },
    { id: 2, name: 'Smart Watch', price: 299.99, category: 'Electronics', image: 'https://via.placeholder.com/300', stock: 30 },
    { id: 3, name: 'Wireless Earbuds', price: 149.99, category: 'Electronics', image: 'https://via.placeholder.com/300', stock: 100 },
    { id: 4, name: 'Laptop Stand', price: 79.99, category: 'Electronics', image: 'https://via.placeholder.com/300', stock: 25 },
    { id: 5, name: 'Cotton T-Shirt', price: 29.99, category: 'Clothing', image: 'https://via.placeholder.com/300', stock: 200 },
    { id: 6, name: 'Denim Jeans', price: 89.99, category: 'Clothing', image: 'https://via.placeholder.com/300', stock: 150 },
    { id: 7, name: 'Programming Book', price: 49.99, category: 'Books', image: 'https://via.placeholder.com/300', stock: 75 },
    { id: 8, name: 'Desk Lamp', price: 59.99, category: 'Home & Garden', image: 'https://via.placeholder.com/300', stock: 40 },
  ];

  const filteredProducts = selectedCategory === 'all' 
    ? products 
    : products.filter(p => p.category === selectedCategory);

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h1 className="text-3xl font-bold mb-8">All Products</h1>
        
        {/* Category Filter */}
        <div className="mb-8 flex flex-wrap gap-2">
          {categories.map((category) => (
            <button
              key={category}
              onClick={() => setSelectedCategory(category)}
              className={`px-4 py-2 rounded-lg font-semibold transition ${
                selectedCategory === category
                  ? 'bg-primary-600 text-white'
                  : 'bg-white text-gray-700 hover:bg-gray-100'
              }`}
            >
              {category.charAt(0).toUpperCase() + category.slice(1)}
            </button>
          ))}
        </div>

        {/* Products Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {filteredProducts.map((product) => (
            <div key={product.id} className="card hover:shadow-lg transition">
              <img 
                src={product.image} 
                alt={product.name}
                className="w-full h-48 object-cover rounded-lg mb-4"
              />
              <span className="text-xs bg-gray-200 px-2 py-1 rounded-full text-gray-600">
                {product.category}
              </span>
              <h3 className="text-lg font-semibold mt-2 mb-1">{product.name}</h3>
              <p className="text-primary-600 font-bold text-xl mb-2">${product.price}</p>
              <p className="text-sm text-gray-500 mb-4">
                {product.stock > 0 ? `${product.stock} in stock` : 'Out of stock'}
              </p>
              <Link 
                to={`/products/${product.id}`} 
                className="btn-primary w-full block text-center"
              >
                View Details
              </Link>
            </div>
          ))}
        </div>

        {filteredProducts.length === 0 && (
          <div className="text-center py-12">
            <p className="text-gray-500 text-lg">No products found in this category.</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default ProductList;
