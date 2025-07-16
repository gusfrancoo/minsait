# 📋 Processo Seletivo - Resolução de Exercícios

Este projeto contém a resolução dos exercícios propostos no processo seletivo.

## **Parte 1: Exercícios de Java - Algoritmo**

### 1️⃣ Cache LRU  
- **Arquivo:** `CacheLRUDemo.java`  
- **Local:** `src/main/java/com/minsait/user_api/utils/`

---

### 2️⃣ Manipulação de Arquivos  
- **Arquivo:** `FileCleanerDemo.java`  
- **Local:** `src/main/java/com/minsait/user_api/utils/`  
- **Entrada:** `resources/input.txt`  
- **Saída:** `resources/output.txt`

---

### 3️⃣ Multithreading (Banco Simulado)  
- **Arquivo:** `BankTransferDemo.java`  
- **Local:** `src/main/java/com/minsait/user_api/utils/`

---

## **Parte 2: Spring Framework**

### API RESTful de Usuários  
- **Pacotes:** `controller`, `service`, `model`, `repository`  
- **Funcionalidades:** CRUD de usuários (id, nome, email)
- **Documentação:** `api_documentation.md` na raiz do projeto
---

## **Parte 3: Análise e Design**

### 1️⃣ Diagrama de Classes - Sistema de Biblioteca  
- **Arquivo:** `BibliotecaUML.jpeg`  
- **Local:** `src/main/resources/`

---

### 2️⃣ Refatoração do Order (SOLID)  
- **Pacote:** `orderdemo`  
- **Local:** `src/main/java/com/minsait/user_api/utils/orderdemo/`  
- **Arquivos:**  
    - `Item.java`  
    - `Order.java`  
    - `TotalCalculator.java`  
    - `OrderCalculator.java`  
    - `OrderDemoApp.java`

---

## **Parte 4: Frameworks e Ferramentas**

### 1️⃣ Hibernate

O **Hibernate** é um framework de **mapeamento objeto-relacional (ORM)** para Java.  
Ele facilita a persistência de objetos em bancos de dados relacionais, eliminando a necessidade de escrever SQL manualmente para operações básicas.

#### **Principais benefícios do Hibernate:**

- **Abstração do SQL:**  
  O Hibernate converte classes Java em tabelas do banco de dados e atributos em colunas, usando anotações ou XML.

- **Automação do CRUD:**  
  Operações como `save`, `find`, `update`, `delete` são feitas via métodos simples, sem SQL explícito.

- **Portabilidade entre bancos:**  
  Não precisa alterar o código para trocar o banco (MySQL, PostgreSQL, Oracle, etc).

- **Gerenciamento de sessão e transação:**  
  O Hibernate cuida da comunicação e do ciclo de vida dos objetos persistentes.

- **Suporte a lazy loading e caching:**  
  Carregamento sob demanda e otimização de acesso ao banco.

- **Redução de risco de SQL Injection:**  
  Como o Hibernate usa consultas parametrizadas internamente (JPQL/HQL ou Criteria API), ele **minimiza o risco de SQL Injection** ao evitar concatenação direta de strings em comandos SQL.

---


