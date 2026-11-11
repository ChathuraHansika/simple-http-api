# simple-http-api

## Project Overview

A small Spring Boot API with one endpoint that greets a user by name.

```
GET /hello-world?name={name}
```

- If the name starts with a letter from A to M, it returns `200 OK`:
  ```json
  { "message": "Hello Alice" }
  ```
- Otherwise it returns `400 Bad Request`:
  ```json
  { "error": "Invalid Input" }
  ```

## How to Run

You need Java 17 or later. Maven is not required because the project includes the Maven wrapper.

```bash
./mvnw spring-boot:run
```

The app starts on port 8080:

```bash
curl "http://localhost:8080/hello-world?name=alice"
```

On Windows, use `mvnw.cmd` instead of `./mvnw`.

## How to Run Tests

```bash
./mvnw test
```

## Assumptions

- Names are checked without regard to case, so `alice` and `Alice` are both accepted.
- Only English letters count. Names starting with a digit, a symbol or an accented letter (like `é`) are rejected.
- Spaces at the start and end of the name are ignored.
- Only the first letter is capitalised. The rest of the name keeps its original casing (`mcDONALD` becomes `Hello McDONALD`).
- A missing or blank name is treated as invalid input and returns `400`.
- Unexpected server errors return a plain `500` with no error details.
