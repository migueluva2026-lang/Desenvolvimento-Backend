import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Produtos.module.css';

const API = 'http://localhost:8080/api';

function getToken() {
  return localStorage.getItem('token');
}

function getHeaders() {
  return {
    Authorization: `Bearer ${getToken()}`,
    'Content-Type': 'application/json',
  };
}

export default function Produtos() {
  const [produtos, setProdutos] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [categoriaSelecionada, setCategoriaSelecionada] = useState('todos');
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    if (!getToken()) {
      navigate('/login');
      return;
    }
    loadFilters();
    loadProducts();
  }, []);

  async function loadFilters() {
    try {
      const res = await fetch(`${API}/categories`, { headers: getHeaders() });
      if (res.status === 401) { navigate('/login'); return; }
      const data = await res.json();
      setCategorias(data);
    } catch {
      console.error('Erro ao carregar categorias');
    }
  }

  async function loadProducts() {
    setLoading(true);
    try {
      const res = await fetch(`${API}/products`, { headers: getHeaders() });
      if (res.status === 401) { navigate('/login'); return; }
      const data = await res.json();
      setProdutos(data);
    } catch {
      console.error('Erro ao carregar produtos');
    } finally {
      setLoading(false);
    }
  }

  async function deleteProduct(id) {
    if (!confirm('Remover este produto?')) return;
    await fetch(`${API}/products/${id}`, { method: 'DELETE', headers: getHeaders() });
    loadProducts();
  }

  function handleLogout() {
    localStorage.removeItem('token');
    navigate('/login');
  }

  const produtosFiltrados =
    categoriaSelecionada === 'todos'
      ? produtos
      : produtos.filter((p) => p.category && String(p.category.id) === String(categoriaSelecionada));

  return (
    <div>
      <header className={styles.header}>
        <h1>Tabela de Produtos</h1>
        <div className={styles.headerAcoes}>
          <button className={styles.btnNovo} onClick={() => navigate('/cadastro')}>
            + Novo Produto
          </button>
          <button className={styles.btnLogout} onClick={handleLogout}>
            Sair
          </button>
        </div>
      </header>

      <div className={styles.filtros}>
        <button
          className={`${styles.filtroBtn} ${categoriaSelecionada === 'todos' ? styles.ativo : ''}`}
          onClick={() => setCategoriaSelecionada('todos')}
        >
          Todos
        </button>
        {categorias.map((cat) => (
          <button
            key={cat.id}
            className={`${styles.filtroBtn} ${String(categoriaSelecionada) === String(cat.id) ? styles.ativo : ''}`}
            onClick={() => setCategoriaSelecionada(String(cat.id))}
          >
            {cat.name}
          </button>
        ))}
      </div>

      {loading ? (
        <p className={styles.loadingMsg}>Carregando produtos...</p>
      ) : (
        <main className={styles.container}>
          {produtosFiltrados.length === 0 && (
            <p className={styles.vazio}>Nenhum produto encontrado.</p>
          )}
          {produtosFiltrados.map((product) => (
            <div key={product.id} className={styles.card}>
              <h2>{product.name}</h2>
              <p className={styles.descricao}>{product.description || ''}</p>
              <p className={styles.preco}>
                R$ {product.price.toFixed(2).replace('.', ',')}
              </p>
              <p className={styles.estoque}>Estoque: {product.stockQuantity}</p>
              <div className={styles.cardAcoes}>
                <button
                  className={styles.btnEditar}
                  onClick={() => navigate(`/cadastro?id=${product.id}`)}
                >
                  Editar
                </button>
                <button
                  className={styles.btnDeletar}
                  onClick={() => deleteProduct(product.id)}
                >
                  Remover
                </button>
              </div>
            </div>
          ))}
        </main>
      )}
    </div>
  );
}
