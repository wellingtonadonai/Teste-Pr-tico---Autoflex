import { useEffect, useState } from 'react';
import api from '../Services/api';

function ProductForm() {
  const [products, setProducts] = useState([]);
  const [materials, setMaterials] = useState([]);
  
  const [productName, setProductName] = useState('');
  const [productValue, setProductValue] = useState('');
  const [productComponents, setProductComponents] = useState([]);

  const [selectedMaterialId, setSelectedMaterialId] = useState('');
  const [quantityNeeded, setQuantityNeeded] = useState('');

  useEffect(() => {
    fetchProducts();
    fetchMaterials();
  }, []);

  const fetchProducts = () => api.get('/products').then(res => setProducts(res.data));
  const fetchMaterials = () => api.get('/raw-materials').then(res => setMaterials(res.data));

  const addMaterialToProduct = () => {
    if (!selectedMaterialId || !quantityNeeded) return;
    
    const material = materials.find(m => m.id === parseInt(selectedMaterialId));
    
    // AJUSTE AQUI: O objeto deve seguir a estrutura da Entity ProductComposition.java
    const newComponent = {
      rawMaterial: { id: material.id, name: material.name }, // Envia o objeto da matéria-prima
      quantityRequired: parseInt(quantityNeeded) // O Java espera "quantityRequired" (Integer)
    };

    setProductComponents([...productComponents, newComponent]);
    setSelectedMaterialId('');
    setQuantityNeeded('');
  };

  const handleSaveProduct = () => {
    // Validação básica para evitar o erro de valor nulo no Java
    if (!productName || !productValue) {
      alert("Please fill in the Name and Value");
      return;
    }

    const payload = {
      name: productName,
      value: parseFloat(productValue), // Garante que é um número para o BigDecimal do Java
      compositions: productComponents // No Java o nome da lista é "compositions" e não "components"
    };

    api.post('/products', payload)
      .then(() => {
        alert("Product saved successfully!");
        setProductName('');
        setProductValue('');
        setProductComponents([]);
        fetchProducts();
      })
      .catch(err => {
        console.error("Error detail:", err.response?.data);
        alert("Error saving product. Check the Java console.");
      });
  };

  return (
    <div className="row">
      <div className="col-md-5">
        <div className="card p-3 shadow-sm">
          <h5>New Product & Composition</h5>
          <input className="form-control mb-2" placeholder="Product Name" value={productName} onChange={e => setProductName(e.target.value)} />
          <input className="form-control mb-3" placeholder="Price (Value)" type="number" value={productValue} onChange={e => setProductValue(e.target.value)} />
          
          <h6 className="mt-2">Add Materials</h6>
          <div className="d-flex gap-2 mb-2">
            <select className="form-select" value={selectedMaterialId} onChange={e => setSelectedMaterialId(e.target.value)}>
              <option value="">Select Material...</option>
              {materials.map(m => <option key={m.id} value={m.id}>{m.name}</option>)}
            </select>
            <input className="form-control" style={{width: '80px'}} placeholder="Qty" type="number" value={quantityNeeded} onChange={e => setQuantityNeeded(e.target.value)} />
            <button className="btn btn-dark" onClick={addMaterialToProduct}>+</button>
          </div>

          <ul className="list-group mb-3">
            {productComponents.map((c, i) => (
              <li key={i} className="list-group-item d-flex justify-content-between small">
                {c.rawMaterial.name} - {c.quantityRequired} units
              </li>
            ))}
          </ul>

          <button className="btn btn-primary w-100" onClick={handleSaveProduct}>Save Full Product</button>
        </div>
      </div>

      <div className="col-md-7">
        <h5>Registered Products</h5>
        <table className="table table-striped border">
          <thead>
            <tr><th>Name</th><th>Value</th><th>Materials</th></tr>
          </thead>
          <tbody>
            {products.map(p => (
              <tr key={p.id}>
                <td>{p.name}</td>
                <td>${p.value}</td>
                {/* Ajustado para ler "compositions" que vem do Java */}
                <td>{p.compositions?.length || 0} items</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default ProductForm;