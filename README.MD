# BatchDate – Controle de Validade

## Sobre o projeto

O BatchDate é um sistema desenvolvido para auxiliar no controle de produtos e lotes, permitindo acompanhar quantidades em estoque e datas de validade.

O objetivo é facilitar a identificação de produtos próximos do vencimento, ajudando a reduzir perdas por produtos vencidos.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- HTML
- CSS
- JavaScript
- Maven

## Funcionalidades

- Cadastro e listagem de produtos.
- Cadastro e consulta de lotes.
- Associação de lotes aos produtos.
- Registro de entrada de mercadorias, atualizando a quantidade do lote.
- Consulta de lotes vencidos e próximos do vencimento.
- Alertas de validade com nome do produto, código do lote, quantidade e data de validade.
- Exportação dos dados de um produto e seus lotes em JSON.
- Validação de dados e tratamento de erros.

## Configuração do banco de dados

O projeto utiliza MySQL.

1. Inicie o MySQL pelo XAMPP.
2. Crie um banco de dados chamado `controle_validade`.
3. Confira as configurações no arquivo `application.properties`.

Configuração utilizada:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/controle_validade
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Como executar

1. Abra o projeto no IntelliJ IDEA.
2. Inicie o MySQL pelo XAMPP.
3. Execute a aplicação Spring Boot.
4. Aguarde a inicialização do servidor.

Acesse o sistema pelo navegador:

`http://localhost:8080`

## Estrutura do projeto

- `controller`: recebe as requisições HTTP.
- `service`: contém as regras de negócio.
- `repository`: realiza o acesso ao banco de dados.
- `model`: contém as entidades Produto e Lote.
- `dto`: contém objetos utilizados para transferência de dados.
- `exception`: realiza o tratamento de erros.
- `static`: contém os arquivos HTML, CSS e JavaScript da interface.

## Autor

Heberson Duarte de Souza

Projeto acadêmico desenvolvido para a disciplina de Desenvolvimento Back-end II.