import { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import styles from './Cadastro.module.css';

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

export default function Cadastro() {
  const [searchParams] = useSearchParams();
  const editId = searchParams.get('id');
  const navigate = useNavigate();

  const [nome, setNome] = useState('');
  const [preco, setPreco] = useState('');
  const [quantidade, setQuantidade] = useState(0);
  const [descricao, setDescricao] = useState('');
  const [categoriaId, setCategoriaId] = useState('');
  const [categorias, setCategorias] = useState([]);
  const [isEdit, setIsEdit] = useState(false);
  const [erro, setErro] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!getToken()) {
      navigate('/login');
      return;
    }
    loadCategories();
  }, []);

  async function loadCategories() {
    try {
      const res = await fetch(`${API}/categories`, { headers: getHeaders() });
      if (res.status === 401) { navigate('/login'); return; }
      const data = await res.json();
      setCategorias(data);

      // Só carrega o produto depois que as categorias estão disponíveis
      if (editId) {
        loadProduct(data);
      }
    } catch {
      setErro('Erro ao carregar categorias');
    }
  }

  async function loadProduct(cats) {
    try {
      const res = await fetch(`${API}/products/${editId}`, { headers: getHeaders() });
      const product = await res.json();

      setNome(product.name);
      setPreco(product.price);
      setQuantidade(product.stockQuantity);
      setDescricao(product.description || '');
      if (product.category) setCategoriaId(String(product.category.id));
      setIsEdit(true);
    } catch {
      setErro('Erro ao carregar produto');
    }
  }

  function ajustarQtd(valor) {
    setQuantidade((prev) => Math.max(0, prev + valor));
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setErro('');
    setLoading(true);

    const body = {
      name: nome,
      price: parseFloat(preco),
      stockQuantity: isEdit ? quantidade : parseInt(quantidade),
      description: descricao,
      category: { id: parseInt(categoriaId) },
    };

    const url = editId ? `${API}/products/${editId}` : `${API}/products`;
    const method = editId ? 'PUT' : 'POST';

    try {
      const res = await fetch(url, {
        method,
        headers: getHeaders(),
        body: JSON.stringify(body),
      });

      if (res.ok) {
        navigate('/produtos');
      } else {
        setErro('Erro ao salvar produto');
      }
    } catch {
      setErro('Erro ao conectar com o servidor');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div>
      <header className={styles.header}>
        <h1>{isEdit ? 'Editar Produto' : 'Cadastro de Produtos'}</h1>
      </header>

      <main className={styles.main}>
        <div className={styles.cadastro}>
          <form className={styles.formulario} onSubmit={handleSubmit}>
            <label htmlFor="nome">Nome do Produto:</label>
            <input
              id="nome"
              type="text"
              placeholder="Digite o nome do produto"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              required
            />

            <label htmlFor="preco">Preço:</label>
            <input
              id="preco"
              type="number"
              step="0.01"
              min="0"
              placeholder="R$ 0,00"
              value={preco}
              onChange={(e) => setPreco(e.target.value)}
              required
            />

            <label>Quantidade em estoque:</label>
            {isEdit ? (
              <div className={styles.qtdControle}>
                <button type="button" onClick={() => ajustarQtd(-10)}>-10</button>
                <button type="button" onClick={() => ajustarQtd(-1)}>-1</button>
                <span className={styles.qtdDisplay}>{quantidade}</span>
                <button type="button" onClick={() => ajustarQtd(1)}>+1</button>
                <button type="button" onClick={() => ajustarQtd(10)}>+10</button>
              </div>
            ) : (
              <input
                id="quantidade"
                type="number"
                min="0"
                placeholder="0"
                value={quantidade}
                onChange={(e) => setQuantidade(e.target.value)}
                required
              />
            )}

            <label htmlFor="categoria">Categoria:</label>
            <select
              id="categoria"
              value={categoriaId}
              onChange={(e) => setCategoriaId(e.target.value)}
              required
            >
              <option value="">Selecione uma categoria</option>
              {categorias.map((cat) => (
                <option key={cat.id} value={cat.id}>
                  {cat.name}
                </option>
              ))}
            </select>

            <label htmlFor="descricao">Descrição:</label>
            <textarea
              id="descricao"
              placeholder="Digite a descrição do produto"
              value={descricao}
              onChange={(e) => setDescricao(e.target.value)}
              rows={3}
            />

            {erro && <p className={styles.erro}>{erro}</p>}

            <button className={styles.enviar} type="submit" disabled={loading}>
              {loading ? 'Salvando...' : isEdit ? 'Salvar Alterações' : 'Cadastrar Produto'}
            </button>
            <button
              className={styles.enviarVoltar}
              type="button"
              onClick={() => navigate('/produtos')}
            >
              Voltar
            </button>
          </form>
        </div>
      </main>
    </div>
  );
}
