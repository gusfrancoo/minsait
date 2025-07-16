
## Resposta 1.1 – Como Identificar e Otimizar Consultas Lentas


Para identificar consultas lentas no banco de dados, eu utilizaria:

- **EXPLAIN ANALYZE**  
  Esse comando mostra o plano de execução da query, permitindo verificar se há uso de índices ou se está sendo feito um `Seq Scan` (varredura completa da tabela).

  **Exemplo:**
  ```sql
  EXPLAIN ANALYZE
  SELECT * FROM posts WHERE user_id = 10;
  ```

- **Logs de desempenho**
  Configuração do banco para registrar consultas lentas:
  ```
  log_min_duration_statement = 500  -- Registra queries que demoram mais de 500ms
  ```

- **Extensão pg_stat_statements**
  Permite analisar as consultas mais executadas e as que mais consomem tempo no banco.

---

### Quais índices criar para otimizar?

Os principais índices que ajudariam a melhorar a performance seriam:

- **Posts de um usuário:**
  ```sql
  CREATE INDEX idx_posts_user_id ON posts(user_id);
  ```

- **Número de curtidas em uma postagem:**
  ```sql
  CREATE INDEX idx_likes_post_id ON likes(post_id);
  ```

- **Buscar seguidores de um usuário:**
  ```sql
  CREATE INDEX idx_follows_followed_user_id ON follows(followed_user_id);
  ```

- **Buscar quem o usuário está seguindo:**
  ```sql
  CREATE INDEX idx_follows_following_user_id ON follows(following_user_id);
  ```

- **Posts ordenados por data (timeline):**
  ```sql
  CREATE INDEX idx_posts_user_id_created_at ON posts(user_id, created_at DESC);
  ```
---

### Observação

É importante lembrar que cada índice melhora a leitura, mas pode aumentar o custo de escrita (INSERT/UPDATE). Por isso, devemos criar índices apenas nas consultas mais frequentes e críticas.

---

## Resposta 1.2 – Consultas SQL

### 1. Total de curtidas que cada usuário recebeu em suas postagens

```sql
SELECT 
    u.username,
    COUNT(l.id) AS total_curtidas
FROM 
    users u
LEFT JOIN 
    posts p ON p.user_id = u.id  
LEFT JOIN 
    likes l ON l.post_id = p.id
GROUP BY 
    u.username
ORDER BY 
    total_curtidas DESC;
```

Essa consulta retorna o nome do usuário e o total de curtidas somadas de todas as postagens feitas por ele.

---

### 2. Top 5 usuários com mais postagens nos últimos 30 dias

```sql
SELECT
    u.username,
    COUNT(p.id) AS total_posts
FROM
    users u
LEFT JOIN posts p ON p.user_id = u.id
WHERE 
    p.created_at BETWEEN (CURRENT_DATE - INTERVAL '30 days') AND CURRENT_DATE
GROUP BY 
    u.username
ORDER BY 
    total_posts DESC
LIMIT 5;
```

Essa consulta retorna os 5 usuários que mais postaram nos últimos 30 dias, ordenando pelo número de postagens em ordem decrescente.

--- 
## Resposta 1.3 – Boas práticas para integridade e performance

1. **Integridade dos dados:**
  - Definir **chaves primárias e estrangeiras** corretamente.
  - Usar **constraints** (CHECK, UNIQUE, NOT NULL) para garantir regras de negócio no nível do banco.
  - Criar **triggers ou procedures** apenas quando necessário (preferir integridade declarativa).

2. **Performance:**
  - Criar **índices apropriados** para consultas frequentes.
  - **Monitorar consultas lentas** com ferramentas como `pg_stat_statements` ou logs de slow query.
  - Fazer **manutenção de índices e tabelas** com `VACUUM`, `ANALYZE` e `REINDEX`.
  - **Paginar consultas** e evitar retornos de grandes volumes de dados de uma vez.
  - Utilizar **caches** (Redis, Memcached) para aliviar consultas repetitivas.

---

## Particionamento de Tabelas

Se o volume de dados crescer muito e começar a impactar a performance, eu avaliaria o particionamento.

### Como eu lidaria com isso?

- Usaria **particionamento por data** (ex: `created_at`) se as consultas forem normalmente por períodos (últimos 30 dias, por exemplo).
- Poderia usar **particionamento por região ou tipo de dado** se o sistema trabalhar com dados geográficos ou categorizados.
- No PostgreSQL, usaria **tabelas particionadas nativas**, que facilitam essa operação.

### O que levaria em consideração?

- Se a maioria das consultas filtra pela coluna escolhida para particionar.
- Se o particionamento vai realmente melhorar a performance ou só complicar a estrutura.
- Como será feita a manutenção das partições (arquivar, excluir ou reorganizar dados antigos).

---


## Resposta 1.4 – Experiência prática de otimização de um banco de grande porte

Em minha experiência na **Novo Mundo S/A**, participei de um projeto cujo objetivo era recuperar toda a lista de devedores da empresa para avaliar quais preenchiam os requisitos legais para ter a dívida protestada em cartório.

Os principais desafios envolviam:

- **Volume de dados muito grande**
- **Estrutura complexa das tabelas**
- **Necessidade de manter a precisão e integridade dos dados**

Foi criada uma consulta inicial para esse processo, porém o custo da query era alto e o tempo de execução inviabilizava a operação com a agilidade necessária.

### Estratégias de otimização adotadas:

- **Criação de índices** nas tabelas principais envolvidas.
- **Utilização de Hints de índice no Oracle**, ou seja, adicionamos comentários especiais nas queries orientando o otimizador a forçar o uso de determinados índices. Isso ajudou o Oracle a gerar planos de execução mais eficientes.
- **Melhoria nos relacionamentos da consulta**, reescrevendo alguns subselects complexos e substituindo-os por **views** já otimizadas e pré-processadas.

Essas práticas combinadas permitiram reduzir significativamente o tempo de execução da consulta, atendendo aos requisitos de desempenho do projeto e mantendo a acurácia dos resultados.

---

## Resposta 2.1 – Implementação da Procedure `process_transaction` 

### Procedure `process_transaction` 
```sql
CREATE OR REPLACE PROCEDURE process_transaction (
    p_account_id INT, 
    p_amount DECIMAL(15,2)
)
LANGUAGE plpgsql
AS $$
DECLARE 
    v_old_balance DECIMAL(15,2);
    v_new_balance DECIMAL(15,2);
    v_transaction_id INT; 
BEGIN
    BEGIN
        SELECT balance 
          INTO v_old_balance 
          FROM accounts 
         WHERE account_id = p_account_id 
           FOR UPDATE;

        v_new_balance := v_old_balance + p_amount;
        
        UPDATE accounts 
        SET balance = v_new_balance
        WHERE account_id = p_account_id;

        INSERT INTO transactions (account_id, amount, transaction_date)
        VALUES (p_account_id, p_amount, NOW())
        RETURNING transaction_id INTO v_transaction_id;

        INSERT INTO audit_log (transaction_id, account_id, old_balance, new_balance, change_date)
        VALUES (v_transaction_id, p_account_id, v_old_balance, v_new_balance, NOW());

        COMMIT;
        
    EXCEPTION WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
    END;
END;
$$;
```

### Execução da Procedure

```sql
CALL process_transaction(1, 150.00);
```

---


## Resposta 2.2 – Trigger para registrar alterações na auditoria após inserção de transações

### Função da Trigger

```sql
CREATE OR REPLACE FUNCTION trg_transactions_audit()
RETURNS TRIGGER AS $$
DECLARE
    v_old_balance DECIMAL(15,2);
    v_new_balance DECIMAL(15,2);
BEGIN
    SELECT balance + (-NEW.amount) 
    INTO v_old_balance
    FROM accounts
    WHERE account_id = NEW.account_id;

    SELECT balance 
    INTO v_new_balance
    FROM accounts
    WHERE account_id = NEW.account_id;

    INSERT INTO audit_log (transaction_id, account_id, old_balance, new_balance, change_date)
    VALUES (NEW.transaction_id, NEW.account_id, v_old_balance, v_new_balance, NOW());

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
```

### Trigger associada

```sql
CREATE TRIGGER after_transaction_insert
AFTER INSERT ON transactions
FOR EACH ROW
EXECUTE FUNCTION trg_transactions_audit();
```

### Observação

Após a criação da trigger, você pode **comentar o trecho da procedure `process_transaction` responsável pela inserção direta na `audit_log`**, já que isso agora será feito automaticamente pela trigger.

#### Trecho que pode ser comentado na procedure:

```sql
-- INSERT INTO audit_log (transaction_id, account_id, old_balance, new_balance, change_date)
-- VALUES (v_transaction_id, p_account_id, v_old_balance, v_new_balance, NOW());
```

---

### 2.3 – Como o uso de transactions ajuda a manter a integridade dos dados?

O uso de **`BEGIN`, `COMMIT` e `ROLLBACK`** garante que um conjunto de operações aconteça de forma **atômica**.

Se tudo der certo, damos `COMMIT` e as alterações são salvas.  
Se houver qualquer erro, fazemos `ROLLBACK` e tudo volta ao estado original.

Isso evita situações como:

- Saldo da conta atualizado mas transação não registrada.
- Transação registrada mas saldo não atualizado.


---

### 2.4 – Importância dos triggers na integridade e segurança dos dados

Os **triggers** ajudam a manter regras de negócio no próprio banco, sem depender da aplicação.

Vantagens:

- Garante que ações automáticas sempre aconteçam (ex: auditoria, logs).
- Evita esquecimento ou erro humano em rotinas críticas.
- Melhora a segurança porque centraliza a lógica sensível no banco, não na aplicação.

---

### 2.5 – Procedure e Trigger criadas

#### Procedure `process_transaction`

- Atualiza o saldo da conta.
- Insere uma transação na tabela `transactions`.
- No modelo com trigger, **não precisa mais inserir na `audit_log` diretamente**, pois a trigger cuida disso.

#### Trigger `after_transaction_insert`

- É acionada automaticamente após cada inserção em `transactions`.
- Calcula o saldo antigo e o saldo novo e insere essas informações na tabela `audit_log`.

---

### 2.6 – Como evitar vulnerabilidades de segurança

- **Evitar SQL Injection:**  
  As consultas foram feitas com **variáveis vinculadas e comandos SQL estruturados em PL/pgSQL**, não com concatenação de strings. Isso protege contra SQL Injection.

- **Garantir validação de dados:**  
  Antes de rodar a procedure em produção, é importante validar se os valores passados fazem sentido (ex: contas válidas, saldo não ficando negativo se for regra do negócio).

---

## Resposta 2.7 – Experiência prática

Em projetos anteriores, uma prática comum que utilizei muito foi a criação de **triggers para gerar IDs automaticamente utilizando sequences**.  
Isso foi especialmente útil em sistemas onde o banco não gerava IDs nativamente (por exemplo, quando não havia `SERIAL` ou `IDENTITY` em algumas tabelas legadas).  
A trigger buscava o próximo valor da sequence e atribuía automaticamente ao campo de ID, garantindo a **integridade das chaves primárias e evitando duplicidade**.

Além disso, também tive boas experiências utilizando **procedures para encapsular regras de negócio e auditoria**, centralizando a lógica no banco de dados para garantir consistência, principalmente em operações financeiras e registros sensíveis.

---

## Resposta 3.1 – Normalização até a 3ª Forma Normal (3NF)


- **1ª Forma Normal (1NF):**  
  Garante que os dados estejam organizados em tabelas com valores atômicos, sem listas ou repetições em uma mesma coluna.

- **2ª Forma Normal (2NF):**  
  Elimina dependências parciais em tabelas com chave primária composta, garantindo que todos os atributos dependam da chave completa.

- **3ª Forma Normal (3NF):**  
  Elimina dependências transitivas, ou seja, não permite que atributos dependam de outros atributos que não sejam a chave primária.


### O modelo apresentado já atende até a **3ª Forma Normal (3NF)**, pois:

- Não há campos multivalorados ou repetidos → **(1NF)**
- As tabelas dependem corretamente da chave primária → **(2NF)**
- Não há dependências transitivas entre colunas → **(3NF)**

---


### 3.2 – Quando considerar desnormalização?

A desnormalização pode ser útil quando:

- O sistema tem **consultas muito pesadas com muitas junções** e o custo de `JOIN` é alto.
- Precisamos de **relatórios rápidos ou dashboards** e podemos aceitar alguma redundância.
- Existem **leituras muito frequentes e poucas escritas**, como em sistemas de recomendação ou cache.
- Para **evitar consultas recursivas ou joins complexos** em dados históricos ou agregados.

---

### 3.3 – Consulta otimizada para listar pedidos com detalhes

```sql
SELECT 
    o.order_id,
    o.order_date,
    o.total_amount,
    c.customer_name,
    c.email,
    oi.product_id,
    p.product_name,
    oi.quantity,
    oi.price
FROM 
    orders o
JOIN customers c ON o.customer_id = c.customer_id
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id
ORDER BY o.order_date DESC;
```

---

### 3.4 – Como identificar e resolver problemas de performance em consultas com múltiplas tabelas

- Usaria **`EXPLAIN ANALYZE`** para ver o plano de execução e identificar gargalos.
- Verificaria se as **chaves estrangeiras e colunas de filtro estão indexadas**.
- Ajustaria as queries para evitar subconsultas desnecessárias ou joins redundantes.
- Poderia usar **views materializadas** ou **caches de leitura** se o problema for leitura repetitiva.

---

### 3.5 – Práticas recomendadas de backup e recuperação em e-commerce

- **Backups frequentes e automatizados** (diários ou até horários dependendo do impacto).
- Utilizar **backup incremental ou diferencial** para não sobrecarregar o sistema.
- Fazer **testes regulares de restauração** para garantir que o backup realmente funciona.
- Ter **replicação em tempo real (hot standby)** para alta disponibilidade.
- Implementar **pontos de recuperação (PITR)** usando logs de transação para restaurar o banco em um ponto específico.

---


### 3.6 – Backup incremental e recuperação ponto a ponto

- Faria um **backup completo (full)** semanal e **backups incrementais** diários, salvando apenas as alterações.
- Ativaria o **WAL archiving** no PostgreSQL para registrar continuamente as mudanças no banco.
- Em caso de falha, restauraria o backup completo e aplicaria os **WALs** até o ponto desejado (PITR - Point In Time Recovery).
- Isso garante recuperação precisa até o momento de interesse, minimizando perda de dados.

---
### 3.7 – Experiência prática de otimização em banco de dados de e-commerce

Trabalhando com **VTEX**, já tive experiências com reestruturação de tabelas que não seguiam corretamente as boas práticas de normalização.  
Haviam situações onde uma **única tabela armazenava tanto os dados de cabeçalho do pedido quanto os itens, causando duplicidade de informações e dificultando a manutenção**.

Nesse caso, aplicamos as regras de normalização, separando as informações em tabelas distintas de **pedido (cabeçalho)** e **itens do pedido**.  
Com isso, garantimos a **integridade dos dados, reduzimos o risco de inconsistências e melhoramos a performance das consultas**, já que as tabelas ficaram mais leves e eficientes.
