import { useState } from 'react';

const AdminProducts = () => {
  const [products, setProducts] = useState([
    { id: 1, name: 'Premium Headphones', category: 'Electronics', price: 199.99, stock: 50, status: 'Active' },
    { id: 2, name: 'Smart Watch', category: 'Electronics', price: 299.99, stock: 30, status: 'Active' },
    { id: 3, name: 'Wireless Earbuds', category: 'Electronics', price: 149.99, stock: 100, status: 'Active' },
    { id: 4, name: 'Cotton T-Shirt', category: 'Clothing', price: 29.99, stock: 200, status: 'Active' },
    { id: 5, name: 'Denim Jeans', category: 'Clothing', price: 89.99, stock: 0, status: 'Out of Stock' },
  ]);

  const getStatusColor = (status) => {
    switch (status) {
      case 'Active': return 'bg-green-100 text-green-800';
      case 'Inactive': return 'bg-gray-100 text-gray-800';
      case 'Out of Stock': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center mb-8">
          <div>
            <h1 className="text-3xl font-bold">Product Management</h1>
            <p className="text-gray-600 mt-2">Manage your product catalog</p>
          </div>
          <button className="btn-primary flex items-center gap-2">
            <span>+</span> Add Product
          </button>
        </div>

        {/* Filters */}
        <div className="card mb-6">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <input
              type="text"
              placeholder="Search products..."
              className="input-field"
            />
            <select className="input-field">
              <option value="">All Categories</option>
              <option value="electronics">Electronics</option>
              <option value="clothing">Clothing</option>
              <option value="books">Books</option>
            </select>
            <select className="input-field">
              <option value="">All Status</option>
              <option value="active">Active</option>
              <option value="inactive">Inactive</option>
              <option value="outofstock">Out of Stock</option>
            </select>
            <button className="btn-secondary">Filter</button>
          </div>
        </div>

        {/* Products Table */}
        <div className="card overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-gray-50">
                <tr>
                  <th className="text-left py-3 px-4 font-semibold text-gray-700">Product</th>
                  <th className="text-left py-3 px-4 font-semibold text-gray-700">Category</th>
                  <th className="text-left py-3 px-4 font-semibold text-gray-700">Price</th>
                  <th className="text-left py-3 px-4 font-semibold text-gray-700">Stock</th>
                  <th className="text-left py-3 px-4 font-semibold text-gray-700">Status</th>
                  <th className="text-left py-3 px-4 font-semibold text-gray-700">Actions</th>
                </tr>
              </thead>
              <tbody>
                {products.map((product) => (
                  <tr key={product.id} className="border-b hover:bg-gray-50">
                    <td className="py-3 px-4">
                      <div className="font-medium">{product.name}</div>
                      <div className="text-sm text-gray-500">ID: {product.id}</div>
                    </td>
                    <td className="py-3 px-4">{product.category}</td>
                    <td className="py-3 px-4 font-semibold">${product.price.toFixed(2)}</td>
                    <td className="py-3 px-4">
                      <span className={product.stock === 0 ? 'text-red-600 font-semibold' : ''}>
                        {product.stock}
                      </span>
                    </td>
                    <td className="py-3 px-4">
                      <span className={`px-3 py-1 rounded-full text-xs font-semibold ${getStatusColor(product.status)}`}>
                        {product.status}
                      </span>
                    </td>
                    <td className="py-3 px-4">
                      <div className="flex space-x-2">
                        <button className="text-blue-600 hover:text-blue-800 text-sm font-semibold">
                          Edit
                        </button>
                        <button className="text-red-600 hover:text-red-800 text-sm font-semibold">
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminProducts;
