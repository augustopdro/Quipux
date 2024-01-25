# Quipux API

Uma API sofisticada para a resolução do desafio da Quipux.


# Recursos Principais

## Spring Framework
Aproveitando o poder do Spring Framework, a API oferece injeção de dependência robusta, inversão de controle e capacidades de programação orientada a aspectos. Ele promove um código modular e escalável, permitindo um desenvolvimento e manutenção rápidos.

## Spring Data
Ao utilizar o Spring Data, a API se integra perfeitamente a vários bancos de dados, garantindo uma persistência e gerenciamento eficientes de dados. Ele simplifica a implementação de camadas de acesso a dados, aprimorando o desempenho e a confiabilidade do sistema como um todo.

## Spring Security
A API incorpora o Spring Security, um framework de segurança altamente personalizável e confiável, para proteger contas de usuário e dados sensíveis. A implementação de autenticação baseada em JWT aumenta a segurança, proporcionando uma experiência de login perfeita para os usuários.

## Spring HATEOAS
A API adota os princípios de Hypermedia as the Engine of Application State (HATEOAS) usando o Spring HATEOAS. Ele enriquece as respostas da API com links de hipermídia, permitindo que os clientes naveguem na API com facilidade e descubram os recursos disponíveis.

---

## Endpoints
- Usuário
  - [Cadastrar](#cadastrar)
  - [Login](#login)
- Lista
  - [Criar](#criar)
  - [Adicionar Musica](#adicionar-musica)
  - [Deletar](#deletar)
  - [Recuperar Listas](#recuperar-listas)
  - [Recuperar Uma Lista](#recuperar-uma-lista)
- Musica
  - [Criar Musica](#criar-musica)

---

## Cadastrar
`POST` /api/usuario/cadastrar

| Campo | Tipo | Obrigatório | Descrição
|:-------:|:------:|:-------------:|--
| email | string | sim | é o email do usuário, deve respeitar o ReGex(^[A-Za-z0-9+_.-]+@(.+)$)
| senha | string | sim | é a senha do usuário, deve ter no mínimo 8 caracteres


**Exemplo de corpo do request**
```js
{
	"email": "pedro.email@gmail.com",
	"senha": "Senha123"
}
```

**Exemplo de corpo de response**
```js
{
    "id": 1,
    "email": "pedro@email.com.br",
    "senha": "$2a$10$bJmpWjryo4sD15KImtSFguE6p9DpXpLjuFSJsz4xgzUDm5S90a4WW",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/usuario/cadastrar"
        },
        "logar": {
            "href": "http://localhost:8080/api/usuario/login"
        }
    }
}
```

**Códigos de Resposta**
| Código | Descrição
|:-:|-
| 201 | Usuario cadastrado com sucesso
| 400 | Erro na requisição

---

---

## Login
`POST` /api/usuario/login

| Campo | Tipo | Obrigatório | Descrição
|:-------:|:------:|:-------------:|--
| email | string | sim | é o email cadastrado pelo usuário
| senha | string | sim | é a senha cadastrada pelo usuário

**Exemplo de corpo do request**
```js
{
	"email": "usuario@email.com",
	"senha": "senha123"
}
```

**Exemplo de corpo do response**

| Campo | Tipo | Descrição
|:-------:|:------:|-------------
|token | string | token do usuario que identifica o usuário no sistema

```js
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJwZWRyb0BlbWFpbC5jb20uYnIiLCJleHAiOjE2ODQ3ODk5NTcsImlzcyI6IkRyZWFtQ29udHJvbCJ9.dii4kCQsnJEpl4ycu8Z2Mh687_0234MkyNIh_sZPPcQ",
    "type": "JWT",
    "prefix": "Bearer",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/usuario/login"
        },
        "cadastrar": {
            "href": "http://localhost:8080/api/usuario/cadastrar"
        }
    }
}
```

**Códigos de Resposta**
| Código | Descrição
|:-:|-
| 200 | Usuario validado com sucesso
| 401 | Usuário ou Senha incorreto

---

---

## Criar
`POST` /api/lista/{userId}/lists

| Campo | Tipo | Obrigatório | Descrição
|:-------:|:------:|:-------------:|--
| nome | String | sim | é o nome da lista
| descricao | String | não | é a descrição da lista


**Exemplo de corpo do request**
```js
{
	"nome": "Lista1",
  "descricao": "Lista de musicas do spotify"
}
```

**Códigos de Resposta**
| Código | Descrição
|:-:|-
| 201 | Lista cadastrada com sucesso
| 400 | Erro na requisição
| 404 | Usuario não encontrado
| 422 | Erro ao processar a requisição

---
---

## Adicionar Musica
`POST` /api/lista/{userId}/lists

| Campo | Tipo | Obrigatório | Descrição
|:-------:|:------:|:-------------:|--
| nomeDaMusica | String | sim | é o nome da musica
| nomeDaLista | String | sim | é o nome da lista


**Exemplo de corpo do request**
```js
{
  "nomeDaMusica": "The End",
	"nomeDaLista": "Lista1"
}
```

**Códigos de Resposta**
| Código | Descrição
|:-:|-
| 201 | Musica adicionada com sucesso
| 400 | Erro na requisição
| 404 | Usuario não encontrado
| 422 | Erro ao processar a requisição

---
---

## Recuperar Listas
`GET` /api/lista/{userId}/lists/

**Exemplo de corpo do response**

| Campo | Tipo | Descrição
|:-------:|:------:|-------------
| nome | String | é o nome da lista
| descricao | String | é a descrição da lista

```js
{
    "content": [
        {
            "id": 1,
            "nome": "Lista1",
            "descricao": "Lista de musicas do spotify",
            "musicas": []
        }
    ],
    "number": 0,
    "totalElements": 1,
    "totalPages": 1,
    "first": true,
    "last": true,
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/lista/1/lists"
        }
    }
}
```

**Códigos de Resposta**
| Código | Descrição
|:-:|-
| 200 | Lista recuperada com sucesso
| 404 | Usuario não encontrado
| 404 | Lista não encontrada
| 400 | Erro na requisição

---

---

## Recuperar Uma Lista
`GET` /api/lista/{userId}/lists/{listName}

**Exemplo de corpo do response**

| Campo | Tipo | Descrição
|:-------:|:------:|-------------
| nome | String | é o nome da lista
| descricao | String | é a descrição da lista

```js
{
    "id": 1,
    "nome": "Lista1",
    "descricao": "Lista de musicas do spotify",
    "musicas": [],
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/lista/1/lists/Lista1"
        },
        "criar": {
            "href": "http://localhost:8080/api/lista/1/lists"
        },
        "deletar": {
            "href": "http://localhost:8080/api/lista/1/lists/Lista1"
        }
    }
}
```

**Códigos de Resposta**
| Código | Descrição
|:-:|-
| 200 | Lista recuperada com sucesso
| 404 | Usuario não encontrado
| 404 | Lista não encontrada
| 400 | Erro na requisição

---

---

## Criar Musica
`POST` /api/lista/{userId}/musica

| Campo | Tipo | Obrigatório | Descrição
|:-------:|:------:|:-------------:|--
| titulo | String | sim | é o nome da musica
| artista | String | sim | é o nome do artista
| album | String | sim | é o album da musica
| ano | String | sim | é o ano em que a musica foi lançada
| genero | String | sim | é o genero da musica

**Exemplo de corpo do request**
```js
{
	"titulo": "The End",
  "artista": "The Doors",
  "album": "The Doors",
  "ano": "1967",
  "genero": "Rock"
}
```

**Códigos de Resposta**
| Código | Descrição
|:-:|-
| 201 | Musica cadastrada com sucesso
| 400 | Erro na requisição
| 404 | Usuario não encontrado

---

---

## Deletar Registro
`DELETE` /api/lista/{userId}/lists/{listName}

**Códigos de Resposta**
| Código | Descrição
|:-:|-
| 204 | Objeto deletado com sucesso
| 404 | Usuario não encontrado
| 404 | Objeto não encontrado

---

