function EventCard({ evento }) {
  return (
    <div className="event-card">
      <div className="event-time">
        {new Date(evento.timestamp).toLocaleString('pt-BR')}
      </div>

      <div className="event-title">
        {evento.action} - {evento.entity}
      </div>

      <div className="event-message">
        {evento.message}
      </div>
    </div>
  );
}

export default EventCard;