# Dashboard Monitor

Frontend em React secundário responsável pelo monitoramento dos eventos publicados pelo backend através do RabbitMQ.
O objetivo aqui é acompanhar em tempo real as ações executadas pelos usuários no sistema.

---

## Eventos monitorados

- Login
- Cadastro
- Atualização
- Exclusão

---

## Funcionamento

O backend publica eventos no RabbitMQ.

O Dashboard Monitor abre uma conexão Websocket com Stomp e se inscreve no topico de produtos para acompanhar os eventos feitos

---

## Tecnologias

- React
- RabbitMQ
- JavaScript

---

## Executando

É recomendado usar o Docker Compose na raiz do projeto.

```bash
docker compose up --build
```

---

### Execução local

Instale as dependências:

```bash
npm install
```

Execute:

```bash
npm run dev
```

---

## Porta padrão

Depende caso rode esse frontend primeiro ou depois do dashboard-monitor, logo:
```
5173 ou 5174
```

---

## Fluxo

```
Backend
    v
RabbitMQ
    v
Dashboard Monitor
```