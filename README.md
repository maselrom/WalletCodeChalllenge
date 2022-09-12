## Interview assignment for Wallet app
</br></br>

## Assumptions
1. Auth is not implemented for this interview assignment, can be added later
2. Currency support can be added
</br></br>

## Database
H2 in-memory id DB for app. Persistent state is saving in file. console available by url
* [h2 console](http://localhost:8080/h2-console)

## Build the Project
```
mvnw clean package
```
## Run unit tests
```
mvnw test
```

## Run all tests
```
mvnw verify
```

## Run the Project
```
mvnw spring-boot:run
```
## Build and run project using docker
```
docker build --platform linux/amd64 -t wallet .
docker run -p 8080:8080 -t wallet
```

## Swagger UI for testing available by url
* [swagger](http://localhost:8080/swagger-ui/)