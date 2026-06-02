# demo-dao-jdbc

Projeto Java que implementa o padrão **DAO (Data Access Object)** com **JDBC puro** para acesso a banco de dados MySQL — sem ORM, sem Spring, sem abstração além do necessário.

---

## 📋 O que o projeto faz

Gerencia duas entidades — `Seller` (vendedor) e `Department` (departamento) — com relacionamento N:1, expondo CRUD completo via JDBC com queries SQL escritas manualmente.

Cada entidade tem sua **interface DAO** e sua **implementação concreta** com JDBC, criadas pela `DaoFactory`. Essa separação garante que a camada de aplicação dependa apenas da abstração, não da implementação.

---

## 🏗️ Arquitetura

```
src/
├── application/
│   ├── Main.java               # Testes de Seller (findById, findAll, insert, update, delete)
│   └── Main2.java              # Testes de Department
├── db/
│   ├── DB.java                 # Singleton de conexão — lê db.properties e abre conexão via DriverManager
│   ├── DbException.java        # RuntimeException para erros de SQL
│   └── DbIntegrityException.java  # Para violações de integridade referencial
└── model/
    ├── dao/
    │   ├── SellerDao.java          # Interface: insert, update, deleteById, findById, findAll, findByDepartment
    │   ├── DepartmentDao.java      # Interface: insert, update, deleteById, findById, findAll
    │   ├── DaoFactory.java         # Instancia os DAOs injetando a conexão
    │   └── impl/
    │       ├── SellerDaoJDBC.java       # Implementação com PreparedStatement + ResultSet
    │       └── DepartmentDaoJDBC.java
    └── entities/
        ├── Seller.java             # id, name, email, birthDate, baseSalary, Department
        └── Department.java         # id, name
```

### Fluxo de dados

```
Main → DaoFactory → SellerDaoJDBC → DB (conexão) → MySQL
                         ↑
              PreparedStatement / ResultSet
```

---

## ⚙️ Como executar

**Pré-requisitos:** Java 11+, MySQL local, `mysql-connector-java` no classpath.

**1. Banco de dados** — execute `database.sql` para criar e popular o schema `coursejdbc`.

**2. Configurar conexão** — crie `db.properties` na raiz:

```properties
user=SEU_USUARIO
password=SUA_SENHA
dburl=jdbc:mysql://localhost:3306/coursejdbc?useSSL=false
```

**3. Rodar** — execute `Main.java` ou `Main2.java` pela IDE ou terminal.

---

## 🔍 Destaques da implementação

- **`DB.java`** implementa Singleton para a conexão, lendo as credenciais de `db.properties` via `DriverManager`.
- **`SellerDaoJDBC.findAll()`** usa um `Map<Integer, Department>` para evitar instanciar objetos `Department` duplicados quando múltiplos sellers pertencem ao mesmo departamento.
- **`insert()`** usa `Statement.RETURN_GENERATED_KEYS` para recuperar o id gerado pelo banco e populá-lo no objeto inserido.
- **`DepartmentDaoJDBC`** usa `try-with-resources` para fechar `PreparedStatement` e `ResultSet` automaticamente; `SellerDaoJDBC` faz o fechamento manual via `DB.closeStatement()` / `DB.closeResultSet()` — ambas as abordagens são válidas.

---
## 🛠️ Tecnologias

- Java
- JDBC
- MySQL
- Java 21
- IntelliJ IDEA
- Git e GitHub

- ## 👨🏻‍💻 Autor

  **André Peixoto Cano** — Engenharia de Software · FIAP
