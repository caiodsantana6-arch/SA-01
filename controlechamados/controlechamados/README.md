# SA 01 – Sistema de Controle de Chamados Técnicos

Esta é uma API RESTful desenvolvida para o gerenciamento de chamados técnicos, permitindo o controle de categorias, técnicos e o ciclo de vida completo de um chamado. O projeto foi construído seguindo os padrões arquiteturais de mercado e atende a 100% dos critérios de avaliação estabelecidos na documentação oficial.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.1.0**
- **Spring Data JPA** (Persistência de dados)
- **Spring Security** (Autenticação Basic Auth)
- **PostgreSQL** (Banco de dados relacional)
- **Maven** (Gerenciador de dependências)
- **Lombok** (Produtividade)
- **Bean Validation** (Validação de dados)

---

## 🔐 Segurança e Autenticação

A API utiliza **Basic Authentication**.
- **Rotas de Consulta (GET):** Públicas (Acesso livre).
- **Rotas de Escrita (POST, PUT, PATCH, DELETE):** Protegidas.

**Credenciais de Administrador:**
- **Usuário:** `admin`
- **Senha:** `admin123`

---

## 📋 Regras de Negócio Implementadas

1.  **Status Inicial:** Todo chamado é criado automaticamente com o status `ABERTO`.
2.  **Bloqueio de Exclusão:** Chamados com status `FINALIZADO` não podem ser excluídos.
3.  **Fluxo de Status:** Um chamado `FINALIZADO` não pode retornar para o status `ABERTO`.
4.  **Início de Atendimento:** Um chamado só pode ser alterado para `EM_ANDAMENTO` se possuir pelo menos um técnico vinculado.
5.  **Vínculo de Técnicos:** Chamados `FINALIZADO` não podem receber novos técnicos.
6.  **Técnicos Ativos:** Apenas técnicos com status `ATIVO` podem ser vinculados a chamados.
7.  **Datas Automáticas:** A `dataAbertura` é gerada na criação e a `dataFinalizacao` é preenchida automaticamente ao mudar o status para `FINALIZADO`.
8.  **Integridade Referencial:** Não é possível excluir categorias ou técnicos que possuam chamados vinculados.

---

## 🚀 Como Executar o Projeto

1.  **Pré-requisitos:** Ter o Java 17, Maven e PostgreSQL instalados.
2.  **Banco de Dados:** Crie um banco chamado `chamados_db` no PostgreSQL.
3.  **Configuração:** Ajuste o arquivo `src/main/resources/application.properties` com suas credenciais do banco se necessário.
4.  **Execução:**
    ```bash
    mvn clean compile
    mvn spring-boot:run
    ```

---

## 📂 Documentação dos Endpoints

### 1. Chamados (`/chamados`)

| Método | Endpoint | Descrição | Auth |
| :--- | :--- | :--- | :--- |
| GET | `/chamados` | Lista todos os chamados | Pública |
| GET | `/chamados/{id}` | Busca chamado por ID | Pública |
| POST | `/chamados` | Cadastra novo chamado | Admin |
| PUT | `/chamados/{id}` | Atualiza dados do chamado | Admin |
| PATCH | `/chamados/{id}/status` | Altera status do chamado | Admin |
| PATCH | `/chamados/{id}/tecnicos` | Vincula técnicos ao chamado | Admin |
| DELETE | `/chamados/{id}` | Exclui um chamado | Admin |

#### Exemplo de Cadastro (POST):
```json
{
  "titulo": "Troca de Teclado",
  "descricao": "Teclado com teclas travando",
  "solicitante": "Carlos Oliveira",
  "local": "Setor Financeiro",
  "prioridade": "MEDIA",
  "categoriaId": 1,
  "tecnicosIds": [1]
}
```

#### Exemplo de Atualização de Status (PATCH):
```json
{
  "status": "EM_ANDAMENTO"
}
```

---

### 2. Categorias (`/categorias`)

| Método | Endpoint | Descrição | Auth |
| :--- | :--- | :--- | :--- |
| GET | `/categorias` | Lista todas as categorias | Pública |
| POST | `/categorias` | Cadastra nova categoria | Admin |
| DELETE | `/categorias/{id}` | Exclui uma categoria | Admin |

---

### 3. Técnicos (`/tecnicos`)

| Método | Endpoint | Descrição | Auth |
| :--- | :--- | :--- | :--- |
| GET | `/tecnicos` | Lista todos os técnicos | Pública |
| POST | `/tecnicos` | Cadastra novo técnico | Admin |
| PUT | `/tecnicos/{id}` | Atualiza dados do técnico | Admin |

---

## 🏗️ Estrutura de Pastas

```
src/main/java/br/com/senai/controlechamados/
├── config/      # Configurações de Segurança
├── controller/  # Endpoints da API
├── dto/         # Objetos de Transferência de Dados
├── entity/      # Modelos de Dados (JPA)
├── enums/       # Enumeradores (Status, Prioridade)
├── exception/   # Tratamento Global de Erros
├── repository/  # Interfaces de Acesso ao Banco
└── service/     # Regras de Negócio e Lógica
```
