# Movie API
![image](https://github.com/user-attachments/assets/ac346f11-5e6f-4785-818b-9de8b3e8d55f)

MOVIEFLIX

## Descrição

A **Movie API** é uma aplicação construída com Java e Spring Boot que permite o gerenciamento de filmes,
autenticação de usuários, e operações relacionadas a tokens JWT para segurança.
Além disso, a aplicação inclui funcionalidades de recuperação de senha, envio de e-mails e manipulação de arquivos.

Em desenvolvimento Front-End em Angular

## Funcionalidades

- **Autenticação e Autorização:** 
  - Registro de usuários, login e gerenciamento de tokens JWT.
  - Recuperação de senha com envio de e-mails de redefinição.
- **Gerenciamento de Filmes:**
  - CRUD de filmes, com suporte a paginação e exceções personalizadas.
  - Manipulação de arquivos relacionados aos filmes (upload e download).
- **Envio de E-mails:** 
  - Serviço de envio de e-mails utilizando o serviço SMTP do Gmail.

## Tecnologias Utilizadas

- **Java 21 utilizado no projeto mas pode ser 17 ou superior**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **MySQL** (ou outro banco de dados relacional)
- **Hibernate**
- **JWT (JSON Web Tokens)**
- **JavaMailSender (Spring Boot Starter Mail)**
- **Lombok** para reduzir a verbosidade do código

## Estrutura do Projeto

### Pacotes Principais

- **auth:** Contém a lógica de autenticação e segurança da aplicação.
  - **config:** Configurações de segurança e aplicação.
  - **entities:** Entidades relacionadas à autenticação e autorização (Usuários, Tokens, etc).
  - **repositories:** Repositórios JPA para acessar os dados relacionados à autenticação.
  - **services:** Serviços para lidar com lógica de autenticação.
  - **utils:** Classes utilitárias para respostas e requisições de autenticação.

- **controllers:** Controladores REST que expõem os endpoints da aplicação.
  - **AuthController:** Gerencia operações de autenticação (login, registro).
  - **FileController:** Gerencia operações de upload/download de arquivos.
  - **ForgotPasswordController:** Gerencia a recuperação de senha.
  - **MovieController:** Gerencia o CRUD de filmes.

- **dto:** Objetos de transferência de dados (DTO) usados para enviar e receber dados.
  - **MailBody:** Estrutura dos e-mails enviados pela aplicação.
  - **MovieDto:** DTO para operações com filmes.
  - **MoviePageResponse:** DTO para resposta de paginação de filmes.

- **entities:** Entidades principais do domínio da aplicação (Movie).

- **exceptions:** Classes para tratar exceções personalizadas.

- **repositories:** Interfaces JPA para interagir com o banco de dados.
  - **MovieRepository:** Repositório para a entidade Movie.

- **service:** Serviços da aplicação que contêm a lógica de negócios.
  - **EmailService:** Serviço para envio de e-mails.
  - **FileService:** Serviço para manipulação de arquivos.
  - **MovieService:** Serviço para operações de filmes.

- **utils:** Classes utilitárias.

## Configuração do Ambiente

### Pré-requisitos

- **Java 17** ou superior
- **Maven** ou **Gradle**
- **MySQL** ou outro banco de dados relacional
- **Conta de e-mail do Gmail** (para envio de e-mails)

### Passos para Configuração

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/movie-api.git
   cd movie-api
Configure o banco de dados:

Crie um banco de dados MySQL (ou utilize outro banco de dados).
Atualize as configurações de conexão no arquivo application.yml.
Exemplo:
 
 
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
Build e execute a aplicação:

Com Maven:
bash
 
mvn clean install
mvn spring-boot:run
Com Gradle:
bash
 
gradle build
gradle bootRun

*************************************************************************
Endpoints

Autenticação
POST /api/v1/auth/register - Registra um novo usuário.
POST /api/v1/auth/login - Autentica um usuário e retorna o JWT.

Filmes
GET /api/v1/movies - Lista filmes com paginação.
POST /api/v1/movies - Adiciona um novo filme.
PUT /api/v1/movies/{id} - Atualiza um filme existente.
DELETE /api/v1/movies/{id} - Remove um filme.

Recuperação de Senha
POST /api/v1/auth/forgotPassword - Envia um e-mail para recuperação de senha.

Manipulação de Arquivos
POST /api/v1/files/upload - Faz upload de arquivos.
GET /api/v1/files/download/{filename} - Faz download de arquivos.
 
