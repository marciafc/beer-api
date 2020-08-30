# Beer Stock API

RESTful API para o gerenciamento de estoque de cerveja.

## Tecnologias utilizadas

  - [Java 11](https://www.oracle.com/java/)

  - [Spring Boot](https://spring.io/projects/spring-boot)

  - [Spring Data](https://spring.io/projects/spring-data)

  - [Maven](http://maven.apache.org/)

  - [Lombok](https://projectlombok.org)
  
  - [MapStruct](https://mapstruct.org/)
  
  - [Swagger 2](https://springfox.github.io/springfox/)  

  - [JUnit 5](https://junit.org/junit5/)

  - [Mockito](https://site.mockito.org/)

  - [Hamcrest](http://hamcrest.org/JavaHamcrest/)

  - [IntelliJ IDEA Community Edition](https://www.jetbrains.com/pt-br/idea/)


## Pré-requisitos

  - JDK 11 (para executar via Maven)
  - Docker e Docker Compose (para executar via Docker)
    
## Utilizando Maven

    $ ./mvnw spring-boot:run
    
## Utilizando Docker
    
    $ docker-compose up --build -d 

## Executar os testes
    
    $ mvn test
	
## Swagger demonstração

A demonstração está hospedada no Heroku.

Para acessar, clique neste [link](https://mfc-beer-api.herokuapp.com)

## Postman

Para realizar os testes via Postman, importar o arquivo **/postman/Beer_API.postman_collection.json**