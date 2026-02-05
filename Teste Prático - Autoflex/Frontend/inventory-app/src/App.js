import axios from 'axios';
import { useEffect, useState } from 'react';
import './App.css'; // Certifique-se de que este arquivo existe, mesmo que vazio

function App() {
  // --- ESTADOS (Memória do componente) ---
  const [activeTab, setActiveTab] = useState('plan'); // 'plan', 'products', 'materials'
  
  // Listas de dados vindos do Banco
  const [productionPlan, setProductionPlan] = useState([]);
  const [products, setProducts] = useState([]);
  const [materials, setMaterials] = useState([]);

  // Controle de Edição
  const [editingId, setEditingId] = useState(null); // Se tiver um ID aqui, estamos editando. Se null, criando.

  // Dados dos Formulários
  const [formProduct, setFormProduct] = useState({ name: '', value: '' });
  const [formMaterial, setFormMaterial] = useState({ name: '', stockQuantity: '' });

  // URL da API Java
  const API_URL = "http://localhost:8080/api";

  // --- EFEITO INICIAL (Carrega dados ao abrir) ---
  useEffect(() => {
    fetchProductionPlan();
  }, []);

  // --- FUNÇÕES DE BUSCA (GET) ---
  const fetchProductionPlan = () => {
    axios.get(`${API_URL}/production-plan`)
      .then(res => setProductionPlan(res.data))
      .catch(err => console.error(err));
  };

  const fetchProducts = () => {
    axios.get(`${API_URL}/products`)
      .then(res => setProducts(res.data))
      .catch(err => console.error(err));
  };

  const fetchMaterials = () => {
    axios.get(`${API_URL}/raw-materials`)
      .then(res => setMaterials(res.data))
      .catch(err => console.error(err));
  };

  // --- NAVEGAÇÃO ENTRE ABAS ---
  const handleTabChange = (tabName) => {
    setActiveTab(tabName);
    setEditingId(null); // Cancela edição se trocar de aba
    setFormProduct({ name: '', value: '' });
    setFormMaterial({ name: '', stockQuantity: '' });

    // Carrega os dados da aba selecionada
    if (tabName === 'plan') fetchProductionPlan();
    if (tabName === 'products') fetchProducts();
    if (tabName === 'materials') fetchMaterials();
  };


  // Botão "Edit" da lista chama isso:
  const handleEditProductClick = (product) => {
    setEditingId(product.id); // Entra em modo edição
    setFormProduct({ name: product.name, value: product.value }); // Preenche o form
    window.scrollTo(0, 0); // Sobe a tela para ver o form
  };

  // Botão "Save/Update" do form chama isso:
  const handleSaveProduct = () => {
    if (editingId) {
      // MODO ATUALIZAR (PUT)
      axios.put(`${API_URL}/products/${editingId}`, formProduct)
        .then(() => {
          alert('Product Updated Successfully!');
          setEditingId(null); // Sai do modo edição
          setFormProduct({ name: '', value: '' });
          fetchProducts();
        })
        .catch(err => alert('Error updating product.'));
    } else {
      // MODO CRIAR (POST)
      axios.post(`${API_URL}/products`, formProduct)
        .then(() => {
          alert('Product Created!');
          setFormProduct({ name: '', value: '' });
          fetchProducts();
        })
        .catch(err => alert('Error creating product.'));
    }
  };

  const handleDeleteProduct = (id) => {
    if(!window.confirm("Delete this product?")) return;
    axios.delete(`${API_URL}/products/${id}`)
      .then(() => fetchProducts())
      .catch(err => alert('Error deleting product.'));
  };

  // ==========================================
  // LÓGICA DE MATÉRIAS-PRIMAS (Create, Update, Delete)
  // ==========================================

  const handleEditMaterialClick = (material) => {
    setEditingId(material.id);
    setFormMaterial({ name: material.name, stockQuantity: material.stockQuantity });
    window.scrollTo(0, 0);
  };

  const handleSaveMaterial = () => {
    if (editingId) {
      // MODO ATUALIZAR (PUT)
      axios.put(`${API_URL}/raw-materials/${editingId}`, formMaterial)
        .then(() => {
          alert('Material Updated Successfully!');
          setEditingId(null);
          setFormMaterial({ name: '', stockQuantity: '' });
          fetchMaterials();
        })
        .catch(err => alert('Error updating material.'));
    } else {
      // MODO CRIAR (POST)
      axios.post(`${API_URL}/raw-materials`, formMaterial)
        .then(() => {
          alert('Material Created!');
          setFormMaterial({ name: '', stockQuantity: '' });
          fetchMaterials();
        })
        .catch(err => alert('Error creating material.'));
    }
  };

  const handleDeleteMaterial = (id) => {
    if(!window.confirm("Delete this material?")) return;
    axios.delete(`${API_URL}/raw-materials/${id}`)
      .then(() => fetchMaterials())
      .catch(err => alert('Error deleting! This material might be used in a product.'));
  };

  // ==========================================
  // RENDERIZAÇÃO (O HTML da tela)
  // ==========================================
  return (
    <div className="container mt-4 mb-5">
      <h1 className="text-center mb-4">Industrial Inventory System</h1>

      {/* Navegação (Abas) */}
      <ul className="nav nav-tabs mb-4">
        <li className="nav-item">
          <button className={`nav-link ${activeTab === 'plan' ? 'active' : ''}`} onClick={() => handleTabChange('plan')}>
            Production Plan
          </button>
        </li>
        <li className="nav-item">
          <button className={`nav-link ${activeTab === 'products' ? 'active' : ''}`} onClick={() => handleTabChange('products')}>
            Products
          </button>
        </li>
        <li className="nav-item">
          <button className={`nav-link ${activeTab === 'materials' ? 'active' : ''}`} onClick={() => handleTabChange('materials')}>
            Raw Materials
          </button>
        </li>
      </ul>

      {/* --- ABA 1: PLANO DE PRODUÇÃO --- */}
      {activeTab === 'plan' && (
        <div className="card shadow-sm">
          <div className="card-header bg-primary text-white">
            Based on stock, you should produce:
          </div>
          <div className="card-body">
            {productionPlan.length === 0 ? (
              <p className="text-muted">No suggestions available. Add stock or products.</p>
            ) : (
              <table className="table table-hover">
                <thead className="table-light">
                  <tr>
                    <th>Product Name</th>
                    <th>Quantity to Produce</th>
                    <th>Estimated Value</th>
                  </tr>
                </thead>
                <tbody>
                  {productionPlan.map((item, index) => (
                    <tr key={index}>
                      <td>{item.productName}</td>
                      <td className="fw-bold fs-5">{item.quantityToProduce}</td>
                      <td className="text-success fw-bold">R$ {item.totalValue}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
            <button className="btn btn-outline-primary" onClick={fetchProductionPlan}>
              Refresh Calculation
            </button>
          </div>
        </div>
      )}

      {/* --- ABA 2: GERENCIAR PRODUTOS --- */}
      {activeTab === 'products' && (
        <div>
          {/* Formulário de Produto */}
          <div className={`card mb-4 ${editingId ? 'border-warning' : 'border-light bg-light'}`}>
            <div className="card-body">
              <h5 className={editingId ? 'text-warning' : 'text-dark'}>
                {editingId ? `Editing Product (ID: ${editingId})` : 'Add New Product'}
              </h5>
              <div className="row g-2">
                <div className="col-md-5">
                  <input type="text" className="form-control" placeholder="Product Name" 
                    value={formProduct.name} 
                    onChange={e => setFormProduct({...formProduct, name: e.target.value})} 
                  />
                </div>
                <div className="col-md-3">
                  <input type="number" className="form-control" placeholder="Price (Value)" 
                    value={formProduct.value} 
                    onChange={e => setFormProduct({...formProduct, value: e.target.value})} 
                  />
                </div>
                <div className="col-md-4">
                  {/* O BOTÃO MUDA DE COR E TEXTO SE ESTIVER EDITANDO */}
                  <button 
                    className={`btn w-100 ${editingId ? 'btn-warning' : 'btn-success'}`} 
                    onClick={handleSaveProduct}>
                    {editingId ? 'Update Product' : 'Save Product'}
                  </button>
                  
                  {editingId && (
                    <button className="btn btn-secondary w-100 mt-1" onClick={() => {
                      setEditingId(null); 
                      setFormProduct({name:'', value:''});
                    }}>Cancel Edit</button>
                  )}
                </div>
              </div>
            </div>
          </div>

          {/* Lista de Produtos */}
          <h3>Product List</h3>
          <ul className="list-group">
            {products.map(p => (
              <li key={p.id} className="list-group-item d-flex justify-content-between align-items-center">
                <span>
                  <strong>{p.name}</strong> <small className="text-muted"> (R$ {p.value})</small>
                </span>
                <div>
                  <button 
                    className="btn btn-warning btn-sm me-2" 
                    onClick={() => handleEditProductClick(p)}>
                    Edit
                  </button>
                  <button 
                    className="btn btn-danger btn-sm" 
                    onClick={() => handleDeleteProduct(p.id)}>
                    Delete
                  </button>
                </div>
              </li>
            ))}
          </ul>
        </div>
      )}

      {/* --- ABA 3: GERENCIAR MATÉRIAS-PRIMAS --- */}
      {activeTab === 'materials' && (
        <div>
           {/* Formulário de Matéria-Prima */}
           <div className={`card mb-4 ${editingId ? 'border-warning' : 'border-light bg-light'}`}>
            <div className="card-body">
              <h5 className={editingId ? 'text-warning' : 'text-dark'}>
                {editingId ? `Editing Material (ID: ${editingId})` : 'Add Raw Material'}
              </h5>
              <div className="row g-2">
                <div className="col-md-5">
                  <input type="text" className="form-control" placeholder="Material Name" 
                     value={formMaterial.name} 
                     onChange={e => setFormMaterial({...formMaterial, name: e.target.value})} 
                  />
                </div>
                <div className="col-md-3">
                  <input type="number" className="form-control" placeholder="Stock Qty" 
                     value={formMaterial.stockQuantity} 
                     onChange={e => setFormMaterial({...formMaterial, stockQuantity: e.target.value})} 
                  />
                </div>
                <div className="col-md-4">
                  <button 
                    className={`btn w-100 ${editingId ? 'btn-warning' : 'btn-success'}`} 
                    onClick={handleSaveMaterial}>
                    {editingId ? 'Update Material' : 'Save Material'}
                  </button>
                  {editingId && (
                    <button className="btn btn-secondary w-100 mt-1" onClick={() => {
                      setEditingId(null); 
                      setFormMaterial({name:'', stockQuantity:''});
                    }}>Cancel Edit</button>
                  )}
                </div>
              </div>
            </div>
          </div>

          {/* Lista de Matérias-Primas */}
          <h3>Stock Inventory</h3>
          <table className="table table-bordered table-striped">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Stock</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {materials.map(m => (
                <tr key={m.id}>
                  <td>{m.id}</td>
                  <td>{m.name}</td>
                  <td>{m.stockQuantity}</td>
                  <td>
                    <button 
                      className="btn btn-warning btn-sm me-2" 
                      onClick={() => handleEditMaterialClick(m)}>
                      Edit
                    </button>
                    <button 
                      className="btn btn-danger btn-sm" 
                      onClick={() => handleDeleteMaterial(m.id)}>
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

    </div>
  );
}

export default App;