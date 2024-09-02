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
- Planejamento para desenvolvimento de front-end e deploy.

## Próximos Passos
- Implementação de testes automatizados.
- Desenvolvimento do front-end.
- Deploy da aplicação.
