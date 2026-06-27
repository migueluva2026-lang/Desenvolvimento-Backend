import { useEffect, useState } from 'react';
import styles from './Notificacoes.module.css';
import { buscarEventos } from '../utils/eventosSistema';

export default function Notificacoes() {
  const [eventos, setEventos] = useState([]);

  useEffect(() => {
    function carregarEventos() {
      setEventos(buscarEventos());
    }

    carregarEventos();

    window.addEventListener('eventosAtualizados', carregarEventos);
    window.addEventListener('storage', carregarEventos);

    return () => {
      window.removeEventListener('eventosAtualizados', carregarEventos);
      window.removeEventListener('storage', carregarEventos);
    };
  }, []);

  return (
    <div>
      <header className={styles.header}>
        <h1>Notificações de Eventos</h1>
      </header>

      <main className={styles.main}>
        <section className={styles.lista}>
          {eventos.length === 0 && (
            <p className={styles.vazio}>Nenhum evento registrado.</p>
          )}

          {eventos.map((evento) => (
            <div key={evento.id} className={styles.card}>
              <div>
                <h2>{evento.tipo}</h2>
                <p>{evento.mensagem}</p>
              </div>
              <span>{evento.data}</span>
            </div>
          ))}
        </section>
      </main>
    </div>
  );
}