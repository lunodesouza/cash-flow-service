# cash-flow-service
Proof of concept of a cash flow service

## How build it
.\mvnw install

## How run it

docker build -t cashflow-service .

docker run -dp 8080:8080 -it cashflow-service

## How use it
To use access swagger on http://localhost:8080/swagger-ui/index.html