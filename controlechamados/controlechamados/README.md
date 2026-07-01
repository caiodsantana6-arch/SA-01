# Sistema de Controle de Chamados Técnicos (controlechamados)

API REST desenvolvida em **Java com Spring Boot** como projeto final da unidade curricular de Programação Orientada a Objetos, com base na Situação de Aprendizagem **SA-01 – Sistema de Controle de Chamados Técnicos**.

## Descrição do Problema

Uma escola técnica precisa organizar melhor os chamados internos relacionados a problemas em computadores, projetores, internet, impressoras e equipamentos de laboratório. Atualmente esses chamados são anotados manualmente, o que dificulta o acompanhamento do status de cada solicitação e a identificação dos setores com mais problemas.

Para resolver isso, o projeto implementa uma API REST capaz de **registrar, consultar, atualizar e finalizar chamados técnicos**. Cada chamado é cadastrado com informações básicas (título, descrição, solicitante e local), é associado a uma **categoria** e acompanhado por um **status** ao longo do seu ciclo de vida. Além disso, cada chamado pode ter um ou mais **técnicos responsáveis** vinculados, permitindo o controle de quem está atuando em cada atendimento.

As principais regras de negócio implementadas garantem a integridade do fluxo de atendimento, entre elas:

- Todo chamado é criado com status `ABERTO`.
- Um chamado `FINALIZADO` não pode ser excluído, alterado, voltar para `ABERTO` nem receber novos técnicos.
- Um chamado só pode mudar para `EM_ANDAMENTO` se possuir pelo menos um técnico vinculado.
- Um técnico `INATIVO` não pode ser vinculado a um chamado.
- Uma categoria não pode ser excluída se já possuir chamados vinculados.
- Um técnico não pode ser excluído se já estiver vinculado a algum chamado.

> **Observação:** O documento de especificação original previa também autenticação básica para as rotas que alteram dados. Essa camada de segurança **não está implementada** na versão atual do código-fonte (não há pacote `config`/`SecurityConfig` no projeto), portanto todas as rotas descritas abaixo são atualmente **públicas**.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 4.0.6** (`spring-boot-starter-parent`)
- **Spring Web (MVC)** — `spring-boot-starter-webmvc`
- **Spring Data JPA** — `spring-boot-starter-data-jpa`
- **Bean Validation** — `spring-boot-starter-validation`
- **PostgreSQL Driver** — `org.postgresql:postgresql`
- **Lombok** (dependência incluída no `pom.xml`, com processador de anotações configurado no build)
- **Maven** (com Maven Wrapper — `mvnw` / `mvnw.cmd`)
- **JUnit / Spring Boot Test** (dependências de teste: `data-jpa-test`, `validation-test`, `webmvc-test`)

> A dependência `spring-boot-starter-security` está declarada no `pom.xml`, mas nenhuma classe de configuração de segurança foi encontrada no código-fonte atual do projeto.

## Como Executar o Projeto

### Pré-requisitos

- JDK 17 ou superior instalado
- PostgreSQL em execução localmente (ou acessível pela rede)
- Maven (opcional — o projeto inclui o Maven Wrapper)

### Passo a passo

1. **Clone o repositório**
   ```bash
   git clone <url-do-repositorio>
   cd SA-01/controlechamados/controlechamados
   ```

2. **Configure o banco de dados** (veja a seção [Configuração do Banco de Dados](#configuração-do-banco-de-dados) abaixo).

3. **Compile o projeto**
   ```bash
   ./mvnw clean install
   ```
   No Windows:
   ```bash
   mvnw.cmd clean install
   ```

4. **Execute a aplicação**
   ```bash
   ./mvnw spring-boot:run
   ```
   No Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

5. A API estará disponível em:
   ```
   http://localhost:8080
   ```

## Configuração do Banco de Dados

O projeto utiliza **PostgreSQL** e a persistência é gerenciada pelo Spring Data JPA/Hibernate. A configuração padrão está em `src/main/resources/application.properties`:

```properties
spring.application.name=controlechamados
spring.datasource.url=jdbc:postgresql://localhost:5432/chamados_db
spring.datasource.username=postgres
spring.datasource.password=

spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

**Antes de rodar a aplicação:**

1. Crie o banco de dados `chamados_db` no PostgreSQL:
   ```sql
   CREATE DATABASE chamados_db;
   ```
2. Ajuste `spring.datasource.username` e `spring.datasource.password` conforme as credenciais do seu ambiente local.
3. As tabelas e relacionamentos (incluindo a tabela de junção `chamado_tecnico`, referente ao relacionamento `@ManyToMany`) são criados/atualizados automaticamente pelo Hibernate, graças a `spring.jpa.hibernate.ddl-auto=update`.

## Usuário e Senha para Testar as Rotas Protegidas

O código-fonte atual **não implementa autenticação/autorização** (não há classe `SecurityConfig` nem filtros de segurança configurados no projeto, apesar da dependência `spring-boot-starter-security` constar no `pom.xml`). Dessa forma:

- **Não existem credenciais de teste**, pois nenhuma rota exige login.
- Todos os endpoints listados abaixo podem ser acessados livremente, sem cabeçalho de autenticação.

Caso a autenticação básica venha a ser adicionada futuramente, este README deverá ser atualizado com o usuário e a senha de teste correspondentes.

## Lista de Endpoints

### Chamados (`/chamados`)

| Método | Rota                    | Descrição                                                              |
|--------|-------------------------|--------------------------------------------------------------------------|
| GET    | `/chamados`             | Lista todos os chamados cadastrados                                     |
| GET    | `/chamados/{id}`        | Busca um chamado específico pelo ID                                     |
| POST   | `/chamados`              | Cadastra um novo chamado                                                 |
| PUT    | `/chamados/{id}`        | Atualiza todos os dados de um chamado existente                         |
| PATCH  | `/chamados/{id}/status`  | Atualiza apenas o status do chamado (ação específica)                   |
| PATCH  | `/chamados/{id}/tecnico` | Vincula (substitui) a lista de técnicos responsáveis pelo chamado       |
| DELETE | `/chamados/{id}`        | Exclui um chamado (não permitido se estiver `FINALIZADO`)               |

### Categorias (`/categorias`)

| Método | Rota                | Descrição                                                |
|--------|---------------------|-------------------------------------------------------------|
| GET    | `/categorias`       | Lista todas as categorias                                   |
| GET    | `/categorias/{id}`  | Busca uma categoria pelo ID                                  |
| POST   | `/categorias`       | Cadastra uma nova categoria                                  |
| PUT    | `/categorias/{id}`  | Atualiza os dados de uma categoria existente                 |
| DELETE | `/categorias/{id}`  | Exclui uma categoria (não permitido se houver chamados vinculados) |

### Técnicos (`/tecnicos`)

| Método | Rota              | Descrição                                                       |
|--------|-------------------|----------------------------------------------------------------|
| GET    | `/tecnicos`       | Lista todos os técnicos                                         |
| GET    | `/tecnicos/{id}`  | Busca um técnico pelo ID                                        |
| POST   | `/tecnicos`       | Cadastra um novo técnico                                        |
| PUT    | `/tecnicos/{id}`  | Atualiza os dados de um técnico existente                       |
| DELETE | `/tecnicos/{id}`  | Exclui um técnico (não permitido se estiver vinculado a chamados) |

## Exemplos de Requisições JSON

### `POST /categorias`
```json
{
  "nome": "Rede",
  "descricao": "Problemas relacionados à internet e conectividade"
}
```

### `POST /tecnicos`
```json
{
  "nome": "João Silva",
  "email": "joao.silva@escola.com",
  "especialidade": "Redes",
  "ativo": "ATIVO"
}
```

### `POST /chamados`
```json
{
  "titulo": "Projetor não liga na sala 12",
  "descricao": "O projetor da sala 12 não liga desde ontem",
  "solicitante": "Maria Souza",
  "local": "Sala 12",
  "prioridade": "ALTA",
  "categoriaId": 1,
  "tecnicosIds": [1, 2]
}
```

### `PUT /chamados/{id}`
```json
{
  "titulo": "Projetor não liga na sala 12",
  "descricao": "O projetor da sala 12 não liga desde ontem, verificado cabo de força",
  "solicitante": "Maria Souza",
  "local": "Sala 12",
  "prioridade": "MEDIA",
  "categoriaId": 1,
  "tecnicosIds": [1]
}
```

### `PATCH /chamados/{id}/status`
```json
{
  "status": "EM_ANDAMENTO"
}
```

### `PATCH /chamados/{id}/tecnico`
```json
{
  "tecnicosIds": [1, 2]
}
```

## Exemplos de Respostas JSON

### `201 Created` — resposta de `POST /chamados`
```json
{
  "id": 1,
  "titulo": "Projetor não liga na sala 12",
  "descricao": "O projetor da sala 12 não liga desde ontem",
  "solicitante": "Maria Souza",
  "local": "Sala 12",
  "prioridade": "ALTA",
  "status": "ABERTO",
  "dataAbertura": "2026-07-01",
  "dataFinalizacao": null,
  "categoria": {
    "id": 1,
    "nome": "Rede",
    "descricao": "Problemas relacionados à internet e conectividade"
  },
  "tecnicos": [
    {
      "id": 1,
      "nome": "João Silva",
      "email": "joao.silva@escola.com",
      "especialidade": "Redes",
      "ativo": "ATIVO"
    },
    {
      "id": 2,
      "nome": "Carlos Pereira",
      "email": "carlos.pereira@escola.com",
      "especialidade": "Hardware",
      "ativo": "ATIVO"
    }
  ]
}
```

### `200 OK` — resposta de `GET /categorias/{id}`
```json
{
  "id": 1,
  "nome": "Rede",
  "descricao": "Problemas relacionados à internet e conectividade"
}
```

### `200 OK` — resposta de `GET /tecnicos`
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao.silva@escola.com",
    "especialidade": "Redes",
    "ativo": "ATIVO"
  },
  {
    "id": 2,
    "nome": "Carlos Pereira",
    "email": "carlos.pereira@escola.com",
    "especialidade": "Hardware",
    "ativo": "INATIVO"
  }
]
```

### `204 No Content` — resposta de `DELETE /chamados/{id}`
Sem corpo de resposta.

## Exemplos de Erros

O tratamento global de erros é feito pela classe `GlobalExceptionHandler` (`@RestControllerAdvice`), que padroniza todas as respostas de erro no seguinte formato JSON, definido pela classe `ErrorResponse`:

```json
{
  "status": 0,
  "erro": "string",
  "mensagem": "string"
}
```

### `404 Not Found` — `RecursoNaoEncontradoException`
Ocorre, por exemplo, ao buscar um chamado, categoria ou técnico com ID inexistente.
```json
{
  "status": 404,
  "erro": "Recurso não encontrado",
  "mensagem": "Chamado não encontrado com o ID: 99"
}
```

### `400 Bad Request` — `RegraNegocioException`
Ocorre quando uma regra de negócio é violada.

Exemplo — tentar iniciar um chamado sem técnico vinculado:
```json
{
  "status": 400,
  "erro": "Regra de negócio violada",
  "mensagem": "Um chamado só poderá ser alterado para EM_ANDAMENTO se possuir pelo menos um técnico vinculado."
}
```

Exemplo — tentar excluir uma categoria vinculada a chamados:
```json
{
  "status": 400,
  "erro": "Regra de negócio violada",
  "mensagem": "Não é possível excluir uma categoria vinculada a chamados."
}
```

Exemplo — tentar vincular um técnico inativo:
```json
{
  "status": 400,
  "erro": "Regra de negócio violada",
  "mensagem": "O técnico Carlos Pereira está inativo e não pode ser vinculado."
}
```

Exemplo — tentar excluir ou alterar um chamado já finalizado:
```json
{
  "status": 400,
  "erro": "Regra de negócio violada",
  "mensagem": "Um chamado finalizado não poderá ser excluído."
}
```

### `500 Internal Server Error` — erro genérico
Capturado pelo handler genérico (`Exception.class`) para qualquer falha não mapeada explicitamente.
```json
{
  "status": 500,
  "erro": "Erro interno",
  "mensagem": "<mensagem da exceção original>"
}
```

---

## Estrutura do Projeto

```
src/main/java/br/com/senai/controlechamados
├── controller       → recebe as requisições HTTP (Chamado, Categoria, Tecnico)
├── service           → contém as regras de negócio
├── repository        → interfaces JpaRepository de acesso ao banco
├── entity            → entidades JPA (Chamado, Categoria, Tecnico)
├── dto                → objetos de entrada/saída (Request/Response)
├── enums              → StatusChamado, Prioridade, Ativo
└── exception          → exceções personalizadas e tratamento global de erros
```

## Relacionamentos entre Entidades

- **Chamado → Categoria**: `@ManyToOne` (um chamado pertence a uma categoria; uma categoria pode ter vários chamados).
- **Chamado ↔ Técnico**: `@ManyToMany`, via tabela de junção `chamado_tecnico` (um chamado pode ter vários técnicos responsáveis; um técnico pode atuar em vários chamados).
