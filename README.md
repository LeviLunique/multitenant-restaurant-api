# Multitenant Restaurant API

API REST desenvolvida em Java 21 com Spring Boot, estruturada com Clean Architecture purista e separação explícita entre `core` e `infra`.

## Arquitetura

- `core/domain`: entidades e invariantes de negócio
- `core/usecase`: casos de uso e regras de aplicação
- `core/controller`: orquestração de entrada do core
- `core/gateway`: contratos de saída
- `infra/web`: adapters HTTP
- `infra/database`: adapters de persistência
- `infra/config`: composição de dependências e configuração externa

O domínio não depende de Spring, JPA ou detalhes de infraestrutura.

## Requisitos

- Java 21
- Maven 3.9+

## Executar localmente

```bash
mvn spring-boot:run
```

## Executar com Docker Compose

```bash
docker compose up --build
```

O arquivo `.env` na raiz do projeto concentra todas as variáveis usadas pelo `Dockerfile` e pelo `docker-compose.yml`. Se precisar recriar esse arquivo, use `./.env.example` como base.

Serviços expostos:

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- PostgreSQL: `localhost:5432`

Credenciais padrão do banco no ambiente Docker:

- database: `restaurant_hub`
- user: `restaurant_hub`
- password: `restaurant_hub`

Variáveis mais importantes no `.env`:

- `APP_JAR_FILE`: nome do JAR gerado pelo Maven e copiado no `Dockerfile`
- `API_PORT`: porta publicada da API no host
- `POSTGRES_PORT`: porta publicada do PostgreSQL no host
- `SPRING_DATASOURCE_URL`: URL JDBC usada pela aplicação no profile `docker`

Para derrubar o ambiente:

```bash
docker compose down
```

Para remover também o volume do banco:

```bash
docker compose down -v
```

## Executar testes e cobertura

```bash
mvn verify
```

O build falha se a cobertura JaCoCo ficar abaixo de `80%`.

## Documentação OpenAPI

- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Endpoints

### Usuários

- `POST /usuarios`
- `GET /usuarios`
- `GET /usuarios/{id}`
- `PUT /usuarios/{id}`
- `DELETE /usuarios/{id}`
- `POST /usuarios/{usuarioId}/tipos-usuario/{tipoUsuarioId}`

### Tipos de usuário

- `POST /tipos-usuario`
- `GET /tipos-usuario`
- `GET /tipos-usuario/{id}`
- `PUT /tipos-usuario/{id}`
- `DELETE /tipos-usuario/{id}`

### Restaurantes

- `POST /restaurantes`
- `GET /restaurantes`
- `GET /restaurantes/{id}`
- `PUT /restaurantes/{id}`
- `DELETE /restaurantes/{id}`

### Itens de cardápio

- `POST /restaurantes/{restauranteId}/itens-cardapio`
- `GET /restaurantes/{restauranteId}/itens-cardapio`
- `GET /restaurantes/{restauranteId}/itens-cardapio/{id}`
- `PUT /restaurantes/{restauranteId}/itens-cardapio/{id}`
- `DELETE /restaurantes/{restauranteId}/itens-cardapio/{id}`

## Padrão de erro

Todas as falhas retornam um contrato padronizado:

```json
{
  "code": "RESOURCE_NOT_FOUND",
  "message": "Resource not found.",
  "timestamp": "2026-03-22T22:00:00Z"
}
```
