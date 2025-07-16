# 📋 Resolução Teste Técnico

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

### 2️⃣ Testes Unitários

Os **testes unitários** são fundamentais no desenvolvimento de software porque:

- Garantem que as classes e métodos funcionam corretamente de forma isolada.
- Facilitam a identificação de erros durante o desenvolvimento.
- Permitem a refatoração segura do código, sem medo de quebrar funcionalidades existentes.
- Aumentam a confiabilidade e reduzem o custo de manutenção do sistema.

---

#### **Teste da classe CacheLRU**

Foi implementado um teste unitário para a classe `CacheLRU` utilizando o **JUnit 5**.

##### **Local do teste:**
`src/test/java/com/minsait/user_api/CacheLRUTest.java`

##### **O que é testado?**

| Método de Teste | Descrição |
|----------------|-----------|
| `putAndGet` | Testa inserção e recuperação de valores |
| `evictLRU` | Verifica a remoção do item menos recentemente usado quando a capacidade é excedida |
| `removeItem` | Testa a remoção direta de um item da cache |
| `checkSize` | Verifica o tamanho da cache respeitando o limite máximo |

---
### 3️⃣ **Maven e Gradle**

No ecossistema Java, tanto o **Maven** quanto o **Gradle** são ferramentas amplamente utilizadas para **build e gerenciamento de dependências**. Cada uma possui características específicas e são mais adequadas para diferentes contextos de projeto.

#### **Comparativo entre Maven e Gradle**

Quando penso em Maven e Gradle, costumo analisar alguns pontos práticos do dia a dia de desenvolvimento:

- **Configuração:**  
O Maven usa XML e impõe uma estrutura de projeto bastante rígida e padronizada. Isso facilita a manutenção em projetos grandes e times numerosos, já que todo mundo "fala a mesma língua".  
Já o Gradle usa uma DSL em Groovy ou Kotlin, o que torna o script de build mais limpo, enxuto e fácil de ler, principalmente quando o projeto exige customizações.

- **Curva de aprendizado:**  
O Maven costuma ser mais fácil para quem já trabalha no ecossistema Java tradicional, justamente pela sua simplicidade e previsibilidade.  
O Gradle exige um pouco mais de conhecimento em programação de scripts, mas em troca oferece mais poder de personalização.

- **Performance:**  
Gradle leva vantagem nesse ponto. O suporte a **build incremental** e **cache local** acelera bastante o processo, principalmente em projetos grandes ou com pipelines complexos.

- **Flexibilidade:**  
Se o projeto exige customizações no pipeline de build, integração com outras tecnologias ou tarefas específicas além do ciclo tradicional de build, o Gradle é mais adequado.  
O Maven funciona muito bem quando o projeto se encaixa no fluxo tradicional de build e deploy, mas é mais limitado quando o cenário foge do padrão.

- **Ecossistema e uso:**  
O Maven ainda é muito forte em ambientes corporativos e projetos legados, onde a previsibilidade e o padrão são mais valorizados.  
O Gradle, por outro lado, costuma ser a escolha natural em projetos modernos, microserviços, integração contínua rápida ou desenvolvimento Android.

---

## **Quando usar cada um?**

- **Maven**:  
Quando o projeto exige previsibilidade, padrões de mercado bem estabelecidos e uma curva de aprendizado mais baixa para novos desenvolvedores. É a minha escolha em projetos corporativos grandes, legados ou quando a equipe já está habituada ao ciclo tradicional.

- **Gradle**:  
Quando o foco é performance, flexibilidade ou integração com outras tecnologias. É minha escolha em projetos modernos, com microserviços, integração contínua mais complexa ou quando preciso rodar builds multiplataforma (por exemplo, Android junto com backend Java).

---




