# cash-flow-service
Proof of concept of a cash flow service 

using: Java 17, Spring Boot 3, flyway, h2 database, Swagger

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
