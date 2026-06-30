import { useEffect, useState } from 'react';
import styles from './Dashboard.module.css';
import { conectarEventos } from '../services/websocket';

function Dashboard() {
  const [eventos, setEventos] = useState([]);

  useEffect(() => {
    const desconectar = conectarEventos((evento) => {
      setEventos((antigos) => [evento, ...antigos]);
    });

    return () => desconectar();
  }, []);

  return (
    <div className={styles.container}>
      <header className={styles.header}>
        <h1>Dashboard Monitor</h1>
      </header>

      <div className={styles.listaEventos}>
        {eventos.length === 0 && (
          <p className={styles.semEventos}>
            Nenhum evento recebido.
          </p>
        )}

        {eventos.map((evento, index) => (
          <div key={index} className={styles.card}>
            <div className={styles.topo}>
              <span className={styles.action}>
                {evento.action}
              </span>

              <span className={styles.entity}>
                {evento.entity}
              </span>
            </div>

            <p className={styles.mensagem}>
              {evento.message}
            </p>

            <span className={styles.data}>
              {new Date(evento.timestamp)
                .toLocaleString('pt-BR')}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Dashboard;