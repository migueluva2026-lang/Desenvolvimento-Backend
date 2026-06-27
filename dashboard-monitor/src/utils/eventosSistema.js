export function registrarEvento(tipo, mensagem) {
  const eventosAtuais = JSON.parse(localStorage.getItem('eventosSistema')) || [];

  const novoEvento = {
    id: Date.now(),
    tipo,
    mensagem,
    data: new Date().toLocaleString('pt-BR'),
  };

  const eventosAtualizados = [novoEvento, ...eventosAtuais];

  localStorage.setItem('eventosSistema', JSON.stringify(eventosAtualizados));
  window.dispatchEvent(new Event('eventosAtualizados'));
}

export function buscarEventos() {
  return JSON.parse(localStorage.getItem('eventosSistema')) || [];
}