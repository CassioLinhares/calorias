# Calorias API

API REST para cadastro, consulta e gerenciamento de alimentos, com calculo automatico de calorias a partir da quantidade de proteinas, carboidratos e gorduras. O projeto tambem possui cadastro de usuarios, autenticacao com JWT e controle de acesso por perfil.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT
- Flyway
- Oracle Database
- Maven
- Docker

## Funcionalidades

- Cadastro e login de usuarios.
- Autenticacao stateless com token JWT.
- Controle de acesso por roles `ADMIN` e `USER`.
- CRUD de alimentos.
- Busca de alimentos por nome, faixa de calorias, gordura maxima e proteina maxima.
- Paginacao e ordenacao nas consultas de alimentos.
- Migracoes de banco com Flyway.

## Requisitos

- Java 21 ou superior
- Maven ou Maven Wrapper incluido no projeto
- Oracle Database acessivel localmente ou via Docker

## Configuracao

O projeto possui arquivos de exemplo em:

- `src/main/resources/application.properties.exemple`
- `src/main/resources/application-docker.properties.exemple`

Crie ou ajuste o arquivo `src/main/resources/application.properties` com suas credenciais:

```properties
spring.application.name=calorias

spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.flyway.baselineOnMigrate=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.web.error.include-stacktrace=never

minha.palavra.secreta=SUA_PALAVRA_SECRETA
```

Para execucao em container, use o arquivo `application-docker.properties` e ajuste a URL do banco, por exemplo:

```properties
spring.datasource.url=jdbc:oracle:thin:@host.docker.internal:1521:XE
```

## Como Executar Localmente

No Windows:

```bash
.\mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

A API ficara disponivel em:

```text
http://localhost:8080
```

## Como Executar com Docker

Gere o pacote da aplicacao:

```bash
.\mvnw.cmd clean package
```

Crie a imagem Docker:

```bash
docker build -t calorias-api .
```

Execute o container:

```bash
docker run -p 8080:8080 calorias-api
```

## Testes

Execute os testes com:

```bash
.\mvnw.cmd test
```

## Autenticacao

Rotas publicas:

- `POST /auth/register`
- `POST /auth/login`

As demais rotas protegidas devem receber o token JWT no header:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

### Cadastro de Usuario

```http
POST /auth/register
Content-Type: application/json
```

```json
{
  "nome": "Admin",
  "email": "admin@email.com",
  "senha": "123456",
  "role": "ADMIN"
}
```

Roles disponiveis:

- `ADMIN`
- `USER`

### Login

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "email": "admin@email.com",
  "senha": "123456"
}
```

Resposta:

```json
{
  "token": "TOKEN_JWT"
}
```

## Endpoints

### Alimentos

| Metodo | Rota | Acesso | Descricao |
| --- | --- | --- | --- |
| `POST` | `/api/alimentos` | `ADMIN` | Cadastra um alimento |
| `GET` | `/api/alimentos` | `ADMIN`, `USER` | Lista alimentos com paginacao |
| `GET` | `/api/alimentos/{alimentoId}` | `ADMIN`, `USER` | Busca alimento por ID |
| `GET` | `/api/alimentos?nome=arroz` | `ADMIN`, `USER` | Busca alimentos por nome |
| `GET` | `/api/alimentos?min=100&max=300` | `ADMIN`, `USER` | Busca por faixa de calorias |
| `GET` | `/api/alimentos?qtdeGorduraMax=10` | `ADMIN`, `USER` | Busca por gordura maxima |
| `GET` | `/api/alimentos?qtdeProteina=20` | `ADMIN`, `USER` | Busca por proteina maxima |
| `PUT` | `/api/alimentos` | `ADMIN` | Atualiza um alimento |
| `DELETE` | `/api/alimentos/{alimentoId}` | `ADMIN` | Remove um alimento |

Exemplo de cadastro de alimento:

```json
{
  "nome": "Arroz branco",
  "porcao": "100g",
  "quantidadeProteina": 2.7,
  "quantidadeCarboidrato": 28.0,
  "quantidadeGorduras": 0.3
}
```

O campo `totalCalorias` e calculado automaticamente pela formula:

```text
(proteinas * 4) + (carboidratos * 4) + (gorduras * 9)
```

### Usuarios

| Metodo | Rota | Descricao |
| --- | --- | --- |
| `POST` | `/api/usuarios` | Cadastra um usuario |
| `GET` | `/api/usuarios` | Lista usuarios |
| `GET` | `/api/usuarios/{id}` | Busca usuario por ID |
| `GET` | `/api/usuarios?email=email@exemplo.com` | Busca usuario por email |
| `PUT` | `/api/usuarios` | Atualiza usuario |
| `DELETE` | `/api/usuarios/{id}` | Remove usuario |

## Paginacao

As consultas de alimentos aceitam parametros de paginacao do Spring:

```text
/api/alimentos?page=0&size=10&sort=nome,asc
```

## Estrutura do Projeto

```text
src/main/java/br/com/fiap/calorias
|-- advice
|-- config/security
|-- controller
|-- dto
|-- Exception
|-- model
|-- repository
`-- service
```

## Migracoes

As migracoes Flyway ficam em:

```text
src/main/resources/db/migration
```

Elas criam e atualizam as tabelas `tbl_usuario` e `tbl_alimento`.
