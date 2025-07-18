
# 📡 API - Usuários

1️⃣ **Pré-requisitos:**

- [Docker](https://www.docker.com/products/docker-desktop) instalado
- [Docker Compose](https://docs.docker.com/compose/install/) (se não estiver incluso no Docker Desktop)

2️⃣ **Rodar o projeto:**

```bash
  docker-compose up --build
```

> **Obs:**  
O Docker irá:
- **Construir a imagem da API e executar o build do Maven automaticamente (multi-stage build)**
- **Subir o banco de dados PostgreSQL com a estrutura pronta**
- **Iniciar a aplicação na porta `8080`**

3️⃣ **Acessar a API:**

- **Base URL:** [http://localhost:8080](http://localhost:8080)

---

## 🧾 Modelo de Dados - User

| Campo  | Tipo   | Descrição                | Validação                   |
|---------|--------|--------------------------|----------------------------|
| `id`    | Long   | Identificador único       | Gerado automaticamente     |
| `nome`  | String | Nome do usuário           | Obrigatório (`@NotBlank`)  |
| `email` | String | E-mail do usuário         | Obrigatório e válido (`@Email`) |

---

## 🔗 Endpoints

### ➕ Criar Usuário

**Método:** `POST`  
**Endpoint:** `/users`

#### Corpo da Requisição:

```json
{
  "nome": "João da Silva",
  "email": "joao@email.com"
}
```

#### Resposta:

**200 OK**

```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao@email.com"
}
```

---

### 📃 Listar Todos os Usuários

**Método:** `GET`  
**Endpoint:** `/users`

#### Resposta:

**200 OK**

```json
[
  {
    "id": 1,
    "nome": "João da Silva",
    "email": "joao@email.com"
  },
  {
    "id": 2,
    "nome": "Maria Souza",
    "email": "maria@email.com"
  }
]
```

---

### 🔍 Buscar Usuário por ID

**Método:** `GET`  
**Endpoint:** `/users/{id}`

#### Exemplo de requisição:

`GET /users/1`

#### Resposta:

**200 OK**

```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao@email.com"
}
```

**404 Not Found**

```text
Usuário não encontrado
```

---

### ✏️ Atualizar Usuário

**Método:** `PUT`  
**Endpoint:** `/users/{id}`

#### Corpo da Requisição:

```json
{
  "nome": "João Atualizado",
  "email": "joao.atualizado@email.com"
}
```

#### Resposta:

**200 OK**

```json
{
  "id": 1,
  "nome": "João Atualizado",
  "email": "joao.atualizado@email.com"
}
```

**404 Not Found**

```text
Usuário não encontrado
```

---

### 🗑️ Deletar Usuário

**Método:** `DELETE`  
**Endpoint:** `/users/{id}`

#### Resposta:


**404 Not Found**

```text
Usuário não encontrado
```

---

## ⚠️ Tratamento de Erros

### Erros de Validação - **400 Bad Request**

```json
{
  "nome": "O nome é obrigatório",
  "email": "E-mail inválido"
}
```

### Usuário Não Encontrado - **404 Not Found**

```text
Usuário não encontrado
```

---

## 📦 Resumo dos Endpoints

| Método | Endpoint        | Descrição           |
|---------|----------------|---------------------|
| POST    | `/users`        | Criar usuário       |
| GET     | `/users`        | Listar usuários     |
| GET     | `/users/{id}`   | Buscar por ID       |
| PUT     | `/users/{id}`   | Atualizar usuário   |
| DELETE  | `/users/{id}`   | Deletar usuário     |

---

## 🔧 Formato das Requisições

- **Content-Type:** `application/json`
- **Retorno:** JSON
