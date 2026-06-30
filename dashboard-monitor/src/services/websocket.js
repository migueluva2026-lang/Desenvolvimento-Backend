import { Client } from "@stomp/stompjs";

let client = null;

export function conectarEventos(onEventoRecebido)
{
    client = new Client({
        brokerURL: "ws://localhost:8080/eventos",

        reconnectDelay: 5000,

        onConnect: () => {
          console.log("WebSocket conectado.");

          client.subscribe("/topic/eventos", (mensagem) => {
            const evento = JSON.parse(mensagem.body);

            console.log("Evento recebido:", evento);

            onEventoRecebido(evento);
          });
        },

        onDisconnect: () => {
          console.log("WebSocket desconectado.");
        },

        onStompError: (frame) => {
          console.error("Erro STOMP:", frame);
        },

        onWebSocketError: (erro) => {
          console.error("Erro WebSocket:", erro);
        },
    });

    client.activate();
    return () => {
      client.deactivate();
      console.log('WebSocket desconectado.');
    };
}

export function desconectarEventos() {
  client?.deactivate();
}