# Moushaifah Bank - Java Servlet Web App

A complete themed Java Web Application for the OOSD homework. It includes Login, Register, Forgot Password, Dashboard, Account Summary, Recent Transactions, dummy data, SQLite3 database, Hibernate ORM, BCrypt password hashing, responsive UI, and documentation.

## Demo Login
- Email: demo@moushaifahbank.com
- Password: Password123
- Security answer: blue

## Requirements
- Java JDK 11 or newer
- Apache Maven 3.8+

## Run Commands
```bash
cd moushaifah-bank-webapp
mvn clean package
mvn jetty:run
```
Open:
```text
http://localhost:8080/moushaifah-bank
```

The SQLite database file `moushaifah_bank.db` is created automatically in the project root on first run.

## Build WAR
```bash
mvn clean package
```
The WAR file will be generated at:
```text
target/moushaifah-bank.war
```

## Main Pages
- `/` - Home page
- `/login` - Login page
- `/register` - Registration page
- `/forgot-password` - Password reset page
- `/dashboard` - Authenticated user dashboard
- `/logout` - Logout
