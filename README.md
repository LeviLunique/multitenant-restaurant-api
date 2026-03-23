# Multitenant Restaurant API

API REST desenvolvida para a fase 2 do Tech Challenge FIAP, com foco em gerenciamento de usuarios, tipos de usuario, restaurantes e itens de cardapio.

O projeto segue Clean Architecture de forma purista, com separacao explicita entre regra de negocio (`core`) e detalhes de infraestrutura (`infra`).

## Stack e requisitos

- Java 21
- Maven 3.9+
- Spring Boot 3.3.2
- PostgreSQL
- Flyway
- Docker e Docker Compose
- JaCoCo
- SonarQube Cloud
- Swagger / OpenAPI

## Arquitetura

### Separacao principal

- `core`: regra de negocio da aplicacao
- `infra`: detalhes externos e tecnologia

### Papel de cada pacote

- `core/domain`: entidades e invariantes de negocio
- `core/usecase`: casos de uso da aplicacao
- `core/controller`: porta de entrada do core, sem dependencia de HTTP
- `core/gateway`: contratos que o core usa para persistencia e saidas externas
- `infra/web`: adaptadores HTTP da API REST
- `infra/database/jpa`: implementacoes concretas de persistencia com JPA
- `infra/config`: composicao das dependencias da aplicacao

Regra importante: o dominio nao depende de Spring, JPA, banco ou controller web.

## Funcionalidades implementadas

- CRUD completo de usuarios
- CRUD completo de tipos de usuario
- associacao de tipo de usuario a usuario existente
- CRUD completo de restaurantes
- CRUD completo de itens de cardapio por restaurante

## Como executar

### Opcao recomendada: Docker Compose

Esta e a forma mais simples de subir a API com PostgreSQL pronto para uso.

#### 1. Criar o arquivo `.env`

Use o arquivo de exemplo como base:

```bash
cp .env.example .env
```

Se quiser, ajuste portas ou credenciais no `.env`. Para o ambiente local padrao, nao e necessario mudar nada.

#### 2. Subir a stack

```bash
docker compose up --build
```

Isso sobe:

- API em `http://localhost:8080`
- Swagger UI em `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON em `http://localhost:8080/v3/api-docs`
- PostgreSQL em `localhost:5432`

#### 3. Derrubar o ambiente

```bash
docker compose down
```

Para remover tambem o volume do banco:

```bash
docker compose down -v
```

### Execucao local com Maven

Se preferir rodar sem Docker para a API:

```bash
mvn spring-boot:run
```

Nesse caso, a aplicacao usa a configuracao padrao local. Para usar PostgreSQL fora do Docker, ajuste as propriedades e variaveis necessarias antes da execucao.

## Variaveis de ambiente principais

O arquivo `.env` concentra as configuracoes usadas no ambiente Docker.

As mais importantes sao:

- `API_PORT`: porta publicada da API
- `POSTGRES_PORT`: porta publicada do PostgreSQL
- `POSTGRES_DB`: nome do banco
- `POSTGRES_USER`: usuario do banco
- `POSTGRES_PASSWORD`: senha do banco
- `SPRING_DATASOURCE_URL`: URL JDBC da aplicacao
- `SPRING_PROFILES_ACTIVE`: profile ativo no container

Observacao importante:

- o `Dockerfile` resolve automaticamente o JAR empacotado pelo Maven
- por isso, o nome versionado do artefato nao precisa ser mantido no `.env`

## Documentacao da API

### Swagger UI

- `http://localhost:8080/swagger-ui/index.html`

### OpenAPI JSON

- `http://localhost:8080/v3/api-docs`

## Endpoints principais

### Usuarios

- `POST /usuarios`
- `GET /usuarios`
- `GET /usuarios/{id}`
- `PUT /usuarios/{id}`
- `DELETE /usuarios/{id}`
- `POST /usuarios/{usuarioId}/tipos-usuario/{tipoUsuarioId}`

### Tipos de usuario

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

### Itens de cardapio

- `POST /restaurantes/{restauranteId}/itens-cardapio`
- `GET /restaurantes/{restauranteId}/itens-cardapio`
- `GET /restaurantes/{restauranteId}/itens-cardapio/{id}`
- `PUT /restaurantes/{restauranteId}/itens-cardapio/{id}`
- `DELETE /restaurantes/{restauranteId}/itens-cardapio/{id}`

## Postman

O repositorio ja possui uma collection versionada para validar os fluxos da fase 2.

### Arquivos

- [postman/multitenant-restaurant-api.postman_collection.json](/Users/levilunique/Workspace/Java/FIAP/TechChallenge/challenge2/multitenant-restaurant-api/postman/multitenant-restaurant-api.postman_collection.json)
- [postman/multitenant-restaurant-api.environment.json](/Users/levilunique/Workspace/Java/FIAP/TechChallenge/challenge2/multitenant-restaurant-api/postman/multitenant-restaurant-api.environment.json)

### Como importar no Postman

1. Abra o Postman.
2. Clique em `Import`.
3. Selecione a collection e o environment.
4. Depois da importacao, selecione o environment `Multitenant Restaurant API (local)`.

### Como executar manualmente

Execute as pastas na ordem em que estao organizadas:

1. `Usuarios`
2. `Tipos de Usuario`
3. `Restaurantes`
4. `Itens de Cardapio`
5. `Encerramento`

A collection usa variaveis para reaproveitar os IDs criados durante os testes.

## Newman

Existe um script para rodar a collection via Docker com Newman:

```bash
./scripts/run-postman.sh
```

Esse script:

- usa a collection versionada do repositorio
- usa o environment local
- tenta detectar automaticamente a rede do container da API
- permite sobrescrever a URL com `BASE_URL`

Exemplo com outra URL:

```bash
BASE_URL=http://localhost:8081 ./scripts/run-postman.sh
```

## Testes e cobertura

Para executar testes e cobertura:

```bash
mvn verify
```

O build falha se a cobertura de linhas ficar abaixo de `80%`.

Essa regra esta configurada no `pom.xml` com JaCoCo, na fase `verify`.

Relatorio HTML de cobertura:

- `target/site/jacoco/index.html`

## CI e qualidade

O projeto possui pipeline com GitHub Actions para:

- build
- testes
- cobertura
- analise Sonar

O workflow principal fica em:

- [.github/workflows/ci.yml](/Users/levilunique/Workspace/Java/FIAP/TechChallenge/challenge2/multitenant-restaurant-api/.github/workflows/ci.yml)

## Contrato de erro

As falhas da API retornam um payload padronizado.

Exemplo:

```json
{
  "code": "RESOURCE_NOT_FOUND",
  "message": "Resource not found.",
  "timestamp": "2026-03-22T22:00:00Z"
}
```

## Troubleshooting

### O Docker falhou ao subir por causa do nome do JAR

O `Dockerfile` ja foi ajustado para localizar o JAR automaticamente. Se ainda houver problema, rode novamente:

```bash
docker compose down
docker compose up --build
```

### A collection nao consegue chamar a API

Confira:

- se a API esta de pe em `http://localhost:8080`
- se o `base_url` do environment esta correto
- se a porta foi alterada no `.env`

### Quero recriar o `.env`

Basta gerar novamente a partir do exemplo:

```bash
cp .env.example .env
```

## Estrutura util do repositorio

- `src/main/java/.../core`: regra de negocio
- `src/main/java/.../infra`: adaptadores e framework
- `src/main/resources/db/migration`: migrations Flyway
- `postman/`: collection e environment
- `scripts/`: scripts utilitarios
- `.env.example`: modelo de variaveis locais

## Resumo rapido de uso

Se voce quiser o caminho mais curto para testar tudo:

```bash
cp .env.example .env
docker compose up --build
```

Depois abra:

- Swagger: `http://localhost:8080/swagger-ui/index.html`

Ou rode:

```bash
./scripts/run-postman.sh
```
