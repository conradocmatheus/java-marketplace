# Java Marketplace

This is a Java project for a marketplace system, developed using Spring Boot, as part of the studies in the 4th semester of Software Engineering.

## Technologies
- Java 17
- Spring Boot
- Maven
- MySQL

## Prerequisites
- Java 17
- Maven
- MySQL

## Dependencies
- **Spring Boot Starter Data JPA**: Data manipulation.
- **Spring Boot Starter Web**: Creation of REST APIs.
- **MySQL Connector Java**: Connection to MySQL database.
- **Spring Boot Starter Test**: Automated testing.

## How to Run
1. Clone the repository.
2. Configure the MySQL database locally and create the necessary tables (see the script in the `src/main/resources` folder).
3. Run the project with Maven: `mvn spring-boot:run`.
4. Use Postman to interact with the API.

## Postman
Download the Postman export file [here](back-end/docs/postman.json).

## Features
- Basic CRUD operations for entities such as products, clients, etc.
- Custom queries, such as searching by name or category.
- Beginning implementation of automated tests.
- Planning for front-end development and deployment.

## Next Steps
- Implementation of automated tests.
- Development of the front-end.
- Deployment of the application.

---

# Java Marketplace

Este é um projeto Java para um sistema de marketplace, desenvolvido utilizando Spring Boot, como parte dos estudos do 4º período da faculdade de Engenharia de Software.

## Tecnologias
- Java 17
- Spring Boot
- Maven
- MySQL

## Pré-requisitos
- Java 17
- Maven
- MySQL

## Dependências
- **Spring Boot Starter Data JPA**: Manipulação de dados.
- **Spring Boot Starter Web**: Criação de APIs REST.
- **MySQL Connector Java**: Conexão com banco de dados MySQL.
- **Spring Boot Starter Test**: Testes automatizados.

## Como executar
1. Clone o repositório.
2. Configure o banco de dados MySQL localmente e crie as tabelas necessárias (veja o script na pasta `src/main/resources`).
3. Execute o projeto com Maven: `mvn spring-boot:run`.
4. Utilize o Postman para interagir com a API.

## Postman
Faça o download do arquivo de exportação do Postman [aqui](back-end/docs/postman.json).

## Funcionalidades
- Operações CRUD básicas para entidades como produtos, clientes, etc.
- Consultas personalizadas, como busca por nome ou categoria.
- Início de implementação de testes automatizados.
- Planejamento para desenvolvimento do front-end e deploy.

## Próximos Passos
- Implementação de testes automatizados.
- Desenvolvimento do front-end.
- Deploy da aplicação.
