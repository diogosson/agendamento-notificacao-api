
# Agendador de Notificações

Esse projeto foi desenvolvido para um desafio técnico visando apresentar habilidades de desenvolvedor java backend e tem como escopo o cadastro de notificações para posterior envio.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- PostgreSQL
- Docker
- Docker Compose
- Maven
- JUnit 5
- Mockito

## Pré-requisitos

- Docker
- Docker Compose

## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/diogosson/agendamento-notificacao-api.git
```

Entre no diretório do projeto

```bash
  docker-compose up --build
```

Swagger

```bash
  http://localhost:8080/swagger-ui/index.html
```

## Documentação da API

#### Cadastra notificações pendentes

```http
  POST /agendamento
```

| Body   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `emailDestinatario` | `string` | **Obrigatório**. Email do destinatário da notificação |
| `telefoneDestinatario` | `string` | **Obrigatório**. Telefone do destinatário da notificação |
| `mensagem` | `string` | **Obrigatório**. Mensagem da notificação |
| `dataHoraEvento` | `LocalDateTime` | **Obrigatório**. Data hora do evento no formado dd-MM-yyyy HH:mm:ss |

#### Retorna uma notificação por id

```http
  GET /agendamento/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório**. O ID do item que você quer buscar |

#### Cancela uma notificação por id

```http
  DELETE /agendamento/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório**. O ID do item que você quer cancelar |


## Rodando os testes

Para rodar os testes, rode o seguinte comando

```bash
  mvn test
```