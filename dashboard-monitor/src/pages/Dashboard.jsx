import { useEffect, useState } from 'react';
import styles from './Dashboard.module.css';
import { buscarEventos } from '../utils/eventosSistema';

const API = 'http://localhost:8080/api';

function getHeaders() {
  return {
    Authorization: `Bearer ${localStorage.getItem('token')}`,
    'Content-Type': 'application/json',
  };
}

export default function Dashboard() {
  const [totalProdutos, setTotalProdutos] = useState(0);
  const [totalEventos, setTotalEventos] = useState(0);

  async function carregarDashboard() {
    try {
      const res = await fetch(`${API}/products`, { headers: getHeaders() });
      const produtos = await res.json();

      setTotalProdutos(produtos.length);
      setTotalEventos(buscarEventos().length);
    } catch {
      setTotalProdutos(0);
      setTotalEventos(buscarEventos().length);
    }
  }

  useEffect(() => {
    carregarDashboard();

    window.addEventListener('eventosAtualizados', carregarDashboard);
    window.addEventListener('storage', carregarDashboard);

    return () => {
      window.removeEventListener('eventosAtualizados', carregarDashboard);
      window.removeEventListener('storage', carregarDashboard);
    };
  }, []);

  const dados = [
    { titulo: 'Produtos cadastrados', valor: totalProdutos },
    { titulo: 'Eventos registrados', valor: totalEventos },
    { titulo: 'Status do sistema', valor: 'Online' },
  ];

  return (
    <div>
      <header className={styles.header}>
        <h1>Dashboard</h1>
      </header>

      <main className={styles.main}>
        <section className={styles.container}>
          {dados.map((item, index) => (
            <div key={index} className={styles.card}>
              <h2>{item.valor}</h2>
              <p>{item.titulo}</p>
            </div>
          ))}
        </section>
      </main>
    </div>
  );
}