// src/components/ProductManager.js
import { useEffect, useState } from 'react';
import api from '../Services/api';

function ProductManager() {
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState({ name: '', value: '' });
  const [editingId, setEditingId] = useState(null);

  useEffect(() => { fetchProducts(); }, []);

  const fetchProducts = () => {
    api.get('/products').then(res => setProducts(res.data));
  };

  const handleSave = () => {
    const request = editingId 
      ? api.put(`/products/${editingId}`, form) 
      : api.post('/products', form);

    request.then(() => {
      alert('Sucesso!');
      setForm({ name: '', value: '' });
      setEditingId(null);
      fetchProducts();
    });
  };

  const handleDelete = (id) => {
    if (window.confirm("Deseja excluir?")) {
      api.delete(`/products/${id}`).then(() => fetchProducts());
    }
  };

  return (
    <div>
      <div className="card mb-4 p-3 bg-light">
        <h5>{editingId ? 'Editar Produto' : 'Novo Produto'}</h5>
        <div className="row g-2">
          <div className="col-md-5">
            <input className="form-control" placeholder="Nome" value={form.name} 
              onChange={e => setForm({...form, name: e.target.value})} />
          </div>
          <div className="col-md-3">
            <input type="number" className="form-control" placeholder="Preço" value={form.value} 
              onChange={e => setForm({...form, value: e.target.value})} />
          </div>
          <div className="col-md-4">
            <button className={`btn w-100 ${editingId ? 'btn-warning' : 'btn-success'}`} onClick={handleSave}>
              {editingId ? 'Atualizar' : 'Salvar'}
            </button>
          </div>
        </div>
      </div>
      <ul className="list-group">
        {products.map(p => (
          <li key={p.id} className="list-group-item d-flex justify-content-between">
            {p.name} (R$ {p.value})
            <div>
              <button className="btn btn-sm btn-warning me-2" onClick={() => {setEditingId(p.id); setForm(p);}}>Edit</button>
              <button className="btn btn-sm btn-danger" onClick={() => handleDelete(p.id)}>Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ProductManager;