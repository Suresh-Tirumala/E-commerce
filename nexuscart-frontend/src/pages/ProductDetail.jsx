import { useState } from 'react';
import { useParams, Link } from 'react-router-dom';

const ProductDetail = () => {
  const { id } = useParams();
  const [quantity, setQuantity] = useState(1);

  // Mock product data - will be replaced with API call
  const product = {
    id: parseInt(id),
    name: 'Premium Headphones',
    price: 199.99,
    description: 'Experience crystal-clear audio with our premium headphones. Features active noise cancellation, 30-hour battery life, and ultra-comfortable ear cushions for all-day wear.',
    category: 'Electronics',
    stock: 50,
    images: [
      'https://via.placeholder.com/500',
      'https://via.placeholder.com/500',
      'https://via.placeholder.com/500',
    ],
    features: [
      'Active Noise Cancellation',
      '30-hour battery life',
      'Bluetooth 5.0',
      'Comfortable over-ear design',
      'Built-in microphone',
    ],
  };

  const handleAddToCart = () => {
    console.log(`Added ${quantity} x ${product.name} to cart`);
    alert(`${quantity} x ${product.name} added to cart!`);
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Breadcrumb */}
        <nav className="mb-8">
          <ol className="flex items-center space-x-2 text-sm text-gray-600">
            <li><Link to="/" className="hover:text-primary-600">Home</Link></li>
            <li>/</li>
            <li><Link to="/products" className="hover:text-primary-600">Products</Link></li>
            <li>/</li>
            <li className="text-gray-900">{product.name}</li>
          </ol>
        </nav>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {/* Product Images */}
          <div>
            <img 
              src={product.images[0]} 
              alt={product.name}
              className="w-full h-96 object-cover rounded-lg shadow-md mb-4"
            />
            <div className="grid grid-cols-3 gap-2">
              {product.images.map((img, index) => (
                <img 
                  key={index}
                  src={img} 
                  alt={`${product.name} ${index + 1}`}
                  className="w-full h-24 object-cover rounded-lg cursor-pointer hover:opacity-75 transition"
                />
              ))}
            </div>
          </div>

          {/* Product Info */}
          <div className="card">
            <span className="text-sm bg-primary-100 text-primary-800 px-3 py-1 rounded-full">
              {product.category}
            </span>
            <h1 className="text-3xl font-bold mt-4 mb-2">{product.name}</h1>
            <p className="text-4xl text-primary-600 font-bold mb-4">${product.price}</p>
            
            <p className="text-gray-600 mb-6">{product.description}</p>

            {/* Stock Status */}
            <div className="mb-6">
              {product.stock > 0 ? (
                <p className="text-green-600 font-semibold">✓ In Stock ({product.stock} available)</p>
              ) : (
                <p className="text-red-600 font-semibold">✗ Out of Stock</p>
              )}
            </div>

            {/* Quantity Selector */}
            <div className="mb-6">
              <label className="block text-gray-700 font-semibold mb-2">Quantity:</label>
              <div className="flex items-center space-x-4">
                <button 
                  onClick={() => setQuantity(Math.max(1, quantity - 1))}
                  className="w-10 h-10 bg-gray-200 rounded-lg hover:bg-gray-300 transition flex items-center justify-center text-xl"
                >
                  -
                </button>
                <span className="text-xl font-semibold w-12 text-center">{quantity}</span>
                <button 
                  onClick={() => setQuantity(Math.min(product.stock, quantity + 1))}
                  className="w-10 h-10 bg-gray-200 rounded-lg hover:bg-gray-300 transition flex items-center justify-center text-xl"
                >
                  +
                </button>
              </div>
            </div>

            {/* Add to Cart Button */}
            <button 
              onClick={handleAddToCart}
              disabled={product.stock === 0}
              className={`w-full py-3 rounded-lg font-semibold text-lg transition ${
                product.stock > 0
                  ? 'btn-primary'
                  : 'bg-gray-400 text-gray-200 cursor-not-allowed'
              }`}
            >
              {product.stock > 0 ? 'Add to Cart' : 'Out of Stock'}
            </button>

            {/* Features */}
            <div className="mt-8">
              <h3 className="text-lg font-semibold mb-3">Key Features:</h3>
              <ul className="space-y-2">
                {product.features.map((feature, index) => (
                  <li key={index} className="flex items-center text-gray-600">
                    <span className="text-green-500 mr-2">✓</span>
                    {feature}
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;
