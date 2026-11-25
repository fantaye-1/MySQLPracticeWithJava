# MySQL Practice With Java

A small Java app that connects to the MySQL [sakila sample database](https://dev.mysql.com/doc/sakila/en/) with plain JDBC, counts the actors and prints the first five.

## Tech

- Java 17, Maven
- JDBC with MySQL Connector/J
- JUnit 5 + H2 (in-memory) for tests

## Configure

Credentials are read from environment variables, never from the code:

| Variable         | Default                               |
|------------------|---------------------------------------|
| `MYSQL_URL`      | `jdbc:mysql://localhost:3306/sakila`  |
| `MYSQL_USER`     | `root`                                |
| `MYSQL_PASSWORD` | required                              |

See `.env.example`.

## Build and test

```bash
mvn verify
```

Tests use an in-memory H2 database, so no MySQL server is needed to run them.

## Run

```bash
export MYSQL_PASSWORD=your-password
mvn -q compile exec:java
```
