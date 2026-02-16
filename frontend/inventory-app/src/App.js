import { useState } from 'react';
import MaterialManager from './components/MaterialManager';
import ProductForm from './components/ProductForm';
import ProductionPlan from './components/ProductionPlan';

function App() {
  const [activeTab, setActiveTab] = useState('plan');

  return (
    <div className="container mt-4 mb-5">
      <h2 className="text-center mb-4">Industrial Inventory System</h2>

      <ul className="nav nav-pills nav-fill mb-4 shadow-sm p-1 bg-white rounded border">
        <li className="nav-item">
          <button className={`nav-link ${activeTab === 'plan' ? 'active' : ''}`} onClick={() => setActiveTab('plan')}>
            📋 Production Plan
          </button>
        </li>
        <li className="nav-item">
          <button className={`nav-link ${activeTab === 'products' ? 'active' : ''}`} onClick={() => setActiveTab('products')}>
            📦 Products & Composition
          </button>
        </li>
        <li className="nav-item">
          <button className={`nav-link ${activeTab === 'materials' ? 'active' : ''}`} onClick={() => setActiveTab('materials')}>
            🏗️ Raw Materials
          </button>
        </li>
      </ul>

      <div className="p-3 border rounded bg-white shadow-sm">
        {activeTab === 'plan' && <ProductionPlan />}
        {activeTab === 'products' && <ProductForm />}
        {activeTab === 'materials' && <MaterialManager />}
      </div>
    </div>
  );
}

export default App;