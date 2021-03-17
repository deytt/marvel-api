# Marvel Web API

CRUD em API Rest utilizando Spring Boot, H2, Maven, JPA e documentação com Swagger.

Essa é uma API semelhante a [API da Marvel](https://developer.marvel.com/docs#!/public).
Utilizando esta API é possível inserir, alterar, excluir e listar entidades como Character / Comics / Events / Series / Stories

# Como executar
 
 - Depois de clonar o projeto e importa-lo no Intellij, execute o main localizado em *MarvelApiApplication.kt* para subir a aplicação.
 
 - É possível também subir a aplicação rodando o comando *"java -jar marvel-api-0.0.1-SNAPSHOT"* dentro de *"~\marvel-api\target"* após buildar o projeto utilizando o comando *"mvn clean install"*.
 
 - Para visualizar os endpoints disponíveis abra o navegador e digite "http://localhost:8080/api-docs", ou "http://localhost:8080/api-docs.yaml" para fazer download do mesmo. 

 - Para consulta e persistência de dados foi utilizado o banco H2. Para visualizar as tabelas e realizar comandos e queries basta acessar "http://localhost:8080/h2-console". Confira os parâmetros Driver Class *"org.h2.Driver"* e JDBC URL *"jdbc:h2:mem:testdb"*, e depois connect. 
 
 - Para testar todos os endpoints disponíveis basta acessar "http://localhost:8080/swagger-ui.html" e passar os parâmetros exigidos para cada requisição.