import { useEffect, useState } from 'react';
import api from '../Services/api';

function MaterialManager() {
  const [materials, setMaterials] = useState([]);
  const [form, setForm] = useState({ name: '', stockQuantity: '' });
  const [editingId, setEditingId] = useState(null);

  // Carrega os dados assim que o componente aparece na tela

  useEffect(() => {
    fetchMaterials();
  }, []);

  const fetchMaterials = () => {
    api.get('/raw-materials')
      .then(res => setMaterials(res.data))
      .catch(err => console.error("Erro ao buscar materiais", err));
  };

  const handleSave = () => {
    const request = editingId 
      ? api.put(`/raw-materials/${editingId}`, form) 
      : api.post('/raw-materials', form);

    request.then(() => {
      alert(editingId ? 'Material atualizado!' : 'Material criado!');
      setForm({ name: '', stockQuantity: '' });
      setEditingId(null);
      fetchMaterials();
    }).catch(err => alert("Erro na operação"));
  };

  const handleDelete = (id) => {
    if(!window.confirm("Excluir este material?")) return;
    api.delete(`/raw-materials/${id}`)
      .then(() => fetchMaterials())
      .catch(err => alert("Erro ao excluir. Pode estar em uso."));
  };

  return (
    <div>
      {/* Formulário */}
      <div className={`card mb-4 ${editingId ? 'border-warning' : 'bg-light'}`}>
        <div className="card-body">
          <h5>{editingId ? 'Editando Material' : 'Adicionar Matéria-Prima'}</h5>
          <div className="row g-2">
            <div className="col-md-5">
              <input type="text" className="form-control" placeholder="Nome do Material" 
                value={form.name} onChange={e => setForm({...form, name: e.target.value})} />
            </div>
            <div className="col-md-3">
              <input type="number" className="form-control" placeholder="Qtd em Estoque" 
                value={form.stockQuantity} onChange={e => setForm({...form, stockQuantity: e.target.value})} />
            </div>
            <div className="col-md-4">
              <button className={`btn w-100 ${editingId ? 'btn-warning' : 'btn-success'}`} onClick={handleSave}>
                {editingId ? 'Atualizar' : 'Salvar'}
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* Tabela de Listagem */}
      <table className="table table-hover shadow-sm">
        <thead className="table-dark">
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Estoque</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {materials.map(m => (
            <tr key={m.id}>
              <td>{m.id}</td>
              <td>{m.name}</td>
              <td>{m.stockQuantity}</td>
              <td>
                <button className="btn btn-warning btn-sm me-2" 
                  onClick={() => { setEditingId(m.id); setForm(m); }}>Editar</button>
                <button className="btn btn-danger btn-sm" 
                  onClick={() => handleDelete(m.id)}>Excluir</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default MaterialManager;