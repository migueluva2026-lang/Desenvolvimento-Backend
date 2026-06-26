# Sistema Cadastral - Desenvolvimento Backend

![Java](https://img.shields.io/badge/Java-21-red)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F)
![React](https://img.shields.io/badge/React-19-61DAFB)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-336791)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Message_Broker-FF6600)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED)
![License](https://img.shields.io/badge/License-MIT-blue)

Sistema cadastral desenvolvido como trabalho da disciplina **Desenvolvimento Backend**, foi aplicada a arquitetura MVC, serviços REST, autenticação, mensageria e Docker.

---

# Instruções do Trabalho

"Crie um aplicativo cadastral com pelo menos 3 entidades, usando arquitetura MVC e serviços REST, dividindo entre backend e frontend.

Para a tecnologia de backend utilize Spring.

O frontend deve ser SPA (Single Page App), como React ou Angular.

Também deverá ser feita a autenticação do usuário.

Deverá ser utilizada mensageria, como o Rabbit MQ, para monitoramento de ações e criado um segundo frontend de acompanhamento, em qualquer plataforma.

Deverá, obrigatoriamente, ser utilizado o Docker."

---

# Tecnologias Utilizadas

## Frontend

* React

## Backend

* Spring Framework:
* Spring Boot
* Spring MVC
* Spring Data JPA
* Spring Security
* Lombok
* JWT
* Hibernate
* Maven

## Banco de Dados

* PostgreSQL

## Mensageria

* RabbitMQ

## Containerização

* Docker
* Docker Compose

---

# Arquitetura

O projeto foi desenvolvido seguindo o padrão **MVC (Model-View-Controller)**.

```
Frontend
    | (fetch)
Backend
    |
Controller
    |
Service
    |
Repository
    |
PostgreSQL
    |
    ---- RabbitMQ ----- Dashboard Monitor
```

Os eventos importantes do sistema são enviados para o RabbitMQ, e podem ser visualizados pelo dashboard-monitor.

---

# Funcionalidades

## Autenticação

* Login
* Proteção de rotas
* Autenticação via JWT

---

## CRUD

O sistema possui cadastro completo das seguintes entidades:

* Usuário
* Produto
* Categoria

Cada entidade permite:

* Criar
* Ler
* Atualizar
* Deletar

---

# RabbitMQ

É o message broker usado para monitorar eventos do sistema.\
As seguintes ações são publicadas no RabbitMQ:

* Cadastro feito
* Atualização de registros
* Exclusão de registros
* Login de usuário

Esses eventos podem ser acompanhados através do Dashboard Monitor.

---

# Dashboard Monitor

É um segundo frontend responsável por:

* Exibir eventos recebidos pelo RabbitMQ
* Monitorar ações do sistema

---

# Estrutura do Projeto

```
backend/
├── src/main/java/.com.example.TrabalhoDenis
├──    config/
├──    controller/
├──    messaging/
├──    model/
├──    repository/
├──    security/
├──    service/
├──    TrabalhoDenisApplication
├── Dockerfile
└── pom.xml

frontend/
├── src/
├── Dockerfile
├── package.json
└── ...

dashboard-monitor/
│
├── src/
├── Dockerfile
└── ...

docker-compose.yml
README.md
```

---

# Como executar

## Pré-requisitos

* Docker
* Docker Compose

ou

* Java 21
* Maven
* Node.js
* PostgreSQL
* RabbitMQ

---

## Executando com Docker

Clone o repositório:

```bash
git clone <https://github.com/migueluva2026-lang/Desenvolvimento-Backend.git>
```

Entre na pasta:

```bash
cd TrabalhoDenis (Ou Desenvolvimento-Backend-master)
```

Execute:

```bash
docker compose up --build
```

---

# Portas

| Serviço             | Porta |
|---------------------| ----: |
| Backend             |  8080 |
| Frontend            |  3000 |
| Dashboard-Monitor   |  3001 |
| RabbitMQ            |  5672 |
| RabbitMQ Management | 15672 |
| PostgreSQL          |  5432 |

---

# Endpoints

## Autenticação

| Método | Endpoint       |
| ------ |----------------|
| POST   | /api/auth      |

---

## Produtos

| Método | Endpoint           |
| ------ |--------------------|
| GET    | /api/products      |
| GET    | /api/products/{id} |
| POST   | /api/products      |
| PUT    | /api/products/{id} |
| DELETE | /api/products/{id} |

## Usuários

| Método | Endpoint        |
| ------ |-----------------|
| GET    | /api/users      |
| GET    | /api/users/{id} |
| POST   | /api/users      |
| PUT    | /api/users/{id} |
| DELETE | /api/users/{id} |

## Categorias

| Método | Endpoint             |
| ------ |----------------------|
| GET    | /api/categories      |
| GET    | /api/categories/{id} |
| POST   | /api/categories      |
| PUT    | /api/categories/{id} |
| DELETE | /api/categories/{id} |

# Variáveis de Ambiente
OBS: Caso queira, pode substituir os valores pelas suas próprias keys
```
spring.application.name=TrabalhoDenis
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=PostgreSQL

app.jwt.secret=segredo-super-seguro-123456789101112

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=secret
```
---

# Docker

Todos os serviços da aplicação podem ser iniciados utilizando Docker Compose.

Containers utilizados:

* Backend
* Frontend
* Dashboard-monitor
* PostgreSQL
* RabbitMQ

---