# ChâTop
## Installation Requirements

- Java 17 
- Spring Boot
- Maven
- MySQL
- Node.js and npm

## Backend Setup

1. Configure MySQL:

Download [MySQL](https://dev.mysql.com/downloads/mysql/)

 ```bash
  CREATE DATABASE rentalDB;
  USE rentalDB;
  SOURCE absolutePathFile/.../client/ressource/sql/script.sql;
  CREATE USER 'user_name'@'localhost' IDENTIFIED BY 'password';
  GRANT ALL PRIVILEGES ON rentalDB.* TO 'user_name'@'localhost';
  FLUSH PRIVILEGES;
  ```

2. Add credentials as Windows environment variables : 

After creating MySQL user, save the credentials you chose in your system environment variables. Open PowerShell and run : 
```bash
  setx P3DB_USER "your_mysql_username"
  setx P3DB_PASSWORD "your_mysql_password"
  ```

**Important** : After setting the variables, restart your terminal or IDE so they are available for the Spring Boot application.

3. Start Spring Boot API:
  ```bash
  cd api
  mvn spring-boot:run
  ```
  Server runs on `http://localhost:8080`

## Frontend Setup

1. Install Angular dependencies:
  ```bash
  cd client
  npm install
  ```

2. Start Angular server:
  ```bash
  npm run start
  ```
  Application runs on `http://localhost:4200`

## Development

Both servers must be running:
- Backend API on port 8080
- Frontend on port 4200

Access the application at `http://localhost:4200`

Access Swagger API documentation at `http://http://localhost:8080/api/swagger-ui/index.html`

## Additional Resources

### Mockoon Environment
- Download [Mockoon](https://mockoon.com/download/)
- Load environment from `client/ressources/mockoon/rental-oc.json`
- Import via File > Open environment
- Start server using play button

### Postman Collection
- Import collection from `client/ressources/postman/rental.postman_collection.json`
- Follow [Postman import guide](https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-data-into-postman)
