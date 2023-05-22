# cash-flow-service
Cash Flow Service is a proof of concept service that allows users to 
manage their financial transactions and view a daily summary of their revenues and expenses. 
The service is built in Java 17 and Spring Boot 3, and uses Flyway to manage database 
migrations and H2 to store data. Swagger is used to document the service's API.
<table>
  <tr>
    <td>
      :chart_with_upwards_trend: <a href=".github/img/componentDiagram.png">
        Service component diagram
      </a>
    </td>
    <td>
      :chart_with_upwards_trend: <a href=".github/img/flowDiagram.png">
        Flow diagram
      </a>
    </td>
    <td>
      :chart_with_upwards_trend: <a href=".github/img/sequenceDiagram.png">
       Sequence diagram
      </a>
    </td>
  </tr>
</table>

## Build & Run with Docker
```bash
./mvnw install
```
```bash
docker build -t cashflow-service .
```
```bash
docker run -dp 8080:8080 -it cashflow-service
```

## Build & Run without Docker
```bash
./mvnw spring-boot:run
```

## How use it
To use access swagger on http://localhost:8080/swagger-ui/index.html

or

GET http://localhost:8080/api/daily-summary

POST http://localhost:8080/api/transactions

Body:
```json
{
"amount": 3101.50,
"type": "CREDIT",
"date": "2023-05-12"
}
```

## How load example data
Just move file 
```bash 
src/main/resources/V2__ExampleData.sql 
```
to folder
```bash
src/main/resources/db/migration/
```

#### Can do this with this command in root folder of project:

Linux:
```bash
mv src/main/resources/V2__ExampleData.sql src/main/resources/db/migration/
```

Windows:
```bash
move src\main\resources\V2__ExampleData.sql src\main\resources\db\migration\
```
