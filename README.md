# Calculator DevOps

A small Spring Boot REST calculator wrapped in a full CI/CD pipeline
(Jenkins, Docker, Docker Compose) for the CSE 816 SPE mini-project.

## Build and test

```bash
./mvnw clean test      # run the 8 JUnit tests
./mvnw spring-boot:run # run locally on port 8080
```

## API

All endpoints are GET under `/api/v1` and return JSON.

| Operation | Example                    | Result         |
|-----------|----------------------------|----------------|
| Add       | `/api/v1/add?a=2&b=3`      | `result: 5.0`  |
| Subtract  | `/api/v1/subtract?a=5&b=2` | `result: 3.0`  |
| Multiply  | `/api/v1/multiply?a=4&b=3` | `result: 12.0` |
| Divide    | `/api/v1/divide?a=6&b=2`   | `result: 3.0`  |
| Health    | `/actuator/health`         | `status: UP`   |

Dividing by zero or passing a missing/non-numeric parameter returns
HTTP 400 with a JSON error body.
