import { useEffect, useState } from 'react';
import api from '../Services/api';

function ProductionPlan() {
  const [plan, setPlan] = useState([]);

  // Busca as sugestões de produção do Back-end
  const fetchPlan = () => {
    api.get('/production-plan')
      .then(res => setPlan(res.data))
      .catch(err => console.error("Erro ao carregar o plano:", err));
  };

  // Carrega os dados na primeira vez que a tela abre
  useEffect(() => { 
    fetchPlan(); 
  }, []);

  // Remove o item apenas da visualização atual da tabela
  const removeItem = (productName) => {
    const newPlan = plan.filter(item => item.productName !== productName);
    setPlan(newPlan);
  };

  return (
    <div className="card p-3 shadow-sm">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h5>Production Suggestion (Priority: Highest Value)</h5>
        <button className="btn btn-outline-primary btn-sm" onClick={fetchPlan}>Recalculate</button>
      </div>

      <table className="table table-hover border">
        <thead className="table-light">
          <tr>
            <th>Product</th>
            <th className="text-center">Unit Price</th>
            <th className="text-center">Qty to Produce</th>
            <th className="text-end">Total Value</th>
            <th className="text-center">Actions</th>
          </tr>
        </thead>
        <tbody>
          {plan.map((item, i) => (
            /* Se houver uma mensagem de aviso, a linha fica vermelha (table-danger) */
            <tr key={i} className={item.warningMessage ? "table-danger" : ""}>
              <td>
                <strong>{item.productName}</strong>
              </td>
              
              {/* Se houver erro de estoque, mostramos o aviso em vez dos números normais */}
              {item.warningMessage ? (
                <td colSpan="3" className="text-center text-danger fw-bold">
                  ⚠️ {item.warningMessage}
                </td>
              ) : (
                <>
                  <td className="text-center">
                    ${item.unitPrice ? item.unitPrice.toFixed(2) : '0.00'}
                  </td>

                  <td className="text-center fw-bold">
                    {item.quantityToProduce} units
                  </td>

                  <td className="text-end text-success fw-bold">
                    ${item.totalValue ? item.totalValue.toLocaleString('en-US', { minimumFractionDigits: 2 }) : '0.00'}
                  </td>
                </>
              )}

              {/* Botão para remover a linha da visão do usuário */}
              <td className="text-center">
                <button 
                  className="btn btn-outline-secondary btn-sm" 
                  onClick={() => removeItem(item.productName)}
                >
                  Hide
                </button>
              </td>
            </tr>
          ))}
          
          {plan.length === 0 && (
            <tr>
              <td colSpan="5" className="text-center text-muted py-4">
                No production suggestions available.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default ProductionPlan;