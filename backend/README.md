# Backend Spring MVC

Backend desenvolvido com Spring Boot responsável pela lógica de negócio, autenticação, persistência dos dados e comunicação com o RabbitMQ.\
Usa a arquitetura arquitetura MVC e cria uma API REST, passando todos os eventos para o RabbitMQ

---

## Funcionalidades

- Login
- Autenticação via JWT
- CRUD de Usuários
- CRUD de Produtos
- CRUD de Categorias
- Fazer alterações no banco de dados
- Publicação de eventos no RabbitMQ

---

## Stack

- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- JWT
- Hibernate
- PostgreSQL
- RabbitMQ
- Maven
- Docker

---

## Estrutura

```
src/main/java/com/example/TrabalhoDenis

|-- config
|-- controller
|-- messaging
|-- model
|-- repository
|-- security
|-- service
|-- TrabalhoDenisApplication
```

---

## Executando

É recomendado usar o Docker Compose disponível na raiz do projeto.

```bash
docker compose up --build
```

Isso vai resolver as dependencias automaticamente, como o ecossistema spring e tecnologias terceiras

- PostgreSQL
- RabbitMQ

---

### Execução local

Pré-requisitos:

- Java 17
- Maven
- PostgreSQL
- RabbitMQ

Execute:

```bash
mvn spring-boot:run
```

---

## Porta padrão

```
8080
```

---

## Variáveis de ambiente

Você pode alterar esses valores aqui desde que altere nas tecnologias (Passar como arg ao dockar caso altere do padrão)


```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=PostgreSQL

app.jwt.secret=segredo-super-seguro

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=secret
```