# cash-flow-service
Proof of concept of a cash flow service

## How build it
```bash
.\mvnw install
```

## How run it
```bash
docker build -t cashflow-service .
```
```bash
docker run -dp 8080:8080 -it cashflow-service
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