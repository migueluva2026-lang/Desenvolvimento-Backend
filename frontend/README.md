# Frontend

Frontend React responsável pela interface principal do sistema.
Consome a API do backend, que por sua vez faz a autenticação e as operações CRUD

---

## Funcionalidades

- Login
- Listagem de Produtos
- Cadastro de Produtos
- Edição de Produtos
- Exclusão de Produtos

---

## Tecnologias

- React
- JavaScript
- CSS

---

## Executando

É recomendado usar o Docker Compose na raiz do projeto

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

## Comunicação

O frontend realiza requisições HTTP para o backend utilizando a API REST. \
OBS: Por sua vez o backend repassa toda operação CRUD para o rabbitMQ

```
Frontend
    v
Backend REST
    v
PostgreSQL
```