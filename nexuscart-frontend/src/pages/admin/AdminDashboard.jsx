import { Link } from 'react-router-dom';

const AdminDashboard = () => {
  const stats = [
    { label: 'Total Sales', value: '$12,345.67', change: '+12%', icon: '💰' },
    { label: 'Total Orders', value: '234', change: '+8%', icon: '📦' },
    { label: 'Total Products', value: '1,234', change: '+5%', icon: '🛍️' },
    { label: 'Total Customers', value: '567', change: '+15%', icon: '👥' },
  ];

  const recentOrders = [
    { id: 'ORD-001', customer: 'John Doe', total: '$199.99', status: 'Pending', date: '2024-01-15' },
    { id: 'ORD-002', customer: 'Jane Smith', total: '$299.99', status: 'Shipped', date: '2024-01-14' },
    { id: 'ORD-003', customer: 'Bob Johnson', total: '$149.99', status: 'Delivered', date: '2024-01-13' },
    { id: 'ORD-004', customer: 'Alice Brown', total: '$89.99', status: 'Pending', date: '2024-01-12' },
  ];

  const getStatusColor = (status) => {
    switch (status) {
      case 'Pending': return 'bg-yellow-100 text-yellow-800';
      case 'Shipped': return 'bg-blue-100 text-blue-800';
      case 'Delivered': return 'bg-green-100 text-green-800';
      case 'Cancelled': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold">Admin Dashboard</h1>
          <p className="text-gray-600 mt-2">Welcome back! Here's what's happening with your store today.</p>
        </div>

        {/* Stats Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          {stats.map((stat, index) => (
            <div key={index} className="card">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-gray-600 text-sm">{stat.label}</p>
                  <p className="text-2xl font-bold mt-1">{stat.value}</p>
                  <p className="text-green-600 text-sm mt-1">{stat.change} from last month</p>
                </div>
                <div className="text-4xl">{stat.icon}</div>
              </div>
            </div>
          ))}
        </div>

        {/* Quick Actions */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <Link to="/admin/products" className="card hover:shadow-lg transition block">
            <div className="text-center">
              <div className="text-4xl mb-3">🛍️</div>
              <h3 className="text-lg font-semibold">Manage Products</h3>
              <p className="text-gray-600 text-sm mt-1">Add, edit, or remove products</p>
            </div>
          </Link>
          <Link to="/admin/orders" className="card hover:shadow-lg transition block">
            <div className="text-center">
              <div className="text-4xl mb-3">📦</div>
              <h3 className="text-lg font-semibold">Manage Orders</h3>
              <p className="text-gray-600 text-sm mt-1">View and process orders</p>
            </div>
          </Link>
          <Link to="/admin/customers" className="card hover:shadow-lg transition block">
            <div className="text-center">
              <div className="text-4xl mb-3">👥</div>
              <h3 className="text-lg font-semibold">Manage Customers</h3>
              <p className="text-gray-600 text-sm mt-1">View customer information</p>
            </div>
          </Link>
        </div>

        {/* Recent Orders */}
        <div className="card">
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-xl font-bold">Recent Orders</h2>
            <Link to="/admin/orders" className="text-primary-600 hover:text-primary-700 text-sm font-semibold">
              View All →
            </Link>
          </div>
          
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b">
                  <th className="text-left py-3 px-4 font-semibold text-gray-700">Order ID</th>
                  <th className="text-left py-3 px-4 font-semibold text-gray-700">Customer</th>
                  <th className="text-left py-3 px-4 font-semibold text-gray-700">Date</th>
                  <th className="text-left py-3 px-4 font-semibold text-gray-700">Total</th>
                  <th className="text-left py-3 px-4 font-semibold text-gray-700">Status</th>
                </tr>
              </thead>
              <tbody>
                {recentOrders.map((order) => (
                  <tr key={order.id} className="border-b hover:bg-gray-50">
                    <td className="py-3 px-4 font-medium">{order.id}</td>
                    <td className="py-3 px-4">{order.customer}</td>
                    <td className="py-3 px-4 text-gray-600">{order.date}</td>
                    <td className="py-3 px-4 font-semibold">{order.total}</td>
                    <td className="py-3 px-4">
                      <span className={`px-3 py-1 rounded-full text-xs font-semibold ${getStatusColor(order.status)}`}>
                        {order.status}
                      </span>
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

export default AdminDashboard;
