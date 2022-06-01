# Projeto Aegro - Backend

## Descrição

O projeto consiste na construção de uma aplicação cujo domínio é composto por fazendas que podem ter de 0 a N talhões (marcação de uma subárea da fazenda), cada. Os talhões devem conter um atributo numérico de área (em hectares) e deve ser possível fazer N registros de produção (em KG) para cada talhão. A aplicação deve ser capaz de calcular a produtividade de cada fazenda e de cada talhão, sendo a produtividade a quantidade de produção (em KG) por área (em hectares).

## Backlog

* Cadastro, edição e remoção de uma fazenda;
* Cadastro, edição e remoção de um talhão (área da fazenda);
* Cadastro, edição e remoção da produção de um talhão;
* Calcular produtividade de uma fazenda;
* Calcular produtividade de um talhão;
* A API deve ser construída em Java com Spring;
* Os dados devem ser persistidos em um banco de dados local;
* A aplicação deve possuir um coverage maior do que 50%;

## Tecnologias

* [Java 11](https://www.java.com/pt-BR/download/ie_manual.jsp?locale=pt_BR)
* [Spring Boot 2.6.6](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html)
* [Gradle](https://gradle.org/install/)
* [MongoDB](https://www.mongodb.com/docs/manual/installation/)
