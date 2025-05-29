# CRUD Spring Boot Project
# Descripción
Este es un proyecto de ejemplo para demostrar cómo construir una aplicación CRUD utilizando Spring Boot, Spring Data JPA y PostgreSQL. La aplicación permite manejar operaciones básicas de CRUD (Crear, Leer, Actualizar, Eliminar) sobre un modelo Movimiento.

# Dependencias
Este proyecto utiliza las siguientes dependencias principales:

- Spring Boot Starter Data JPA: Para la integración con JPA y la gestión de la base de datos.
- Spring Boot Starter Web: Para crear servicios web RESTful.
- PostgreSQL Driver: Para la conexión con la base de datos PostgreSQL.
- Spring Boot Starter Test: Para pruebas unitarias y de integración.
- Spring Boot DevTools: Para mejorar la experiencia de desarrollo con características como recarga automática.
- Configuración
- Configuración de la Base de Datos
- Asegúrate de que PostgreSQL esté instalado y en ejecución en tu máquina local. Puedes cambiar la URL, el usuario y la contraseña de la base de datos en el archivo application.properties según tu configuración local.
- se debe tener la misma BD para que funcione


---
# 🏦 Documentacion con Swagger UI 
- primero se debe iniciar el proyecto e ingresar a dicha documentacion
- http://localhost:8080/swagger-ui.html
# 🏦 Documentacion con Swagger UI
- para correr las pruebas unitarias ejecutar el comando 
- ./mvnw test
# 🏦 Sistema Bancario con Spring Boot

Este proyecto implementa una API REST para la gestión de clientes, cuentas bancarias y movimientos financieros (depósitos y retiros), utilizando Spring Boot y buenas prácticas de desarrollo. Se incluye además la generación de reportes por fecha y cliente, así como validaciones de datos robustas.

## ✅ Tecnologías utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Hibernate
- H2 Database (modo embebido para pruebas)
- Lombok
- JUnit 5 + Spring MockMvc
- SLF4J + Logback

## ▶️ Pruebas funcionales en Postman

### Crear Cliente
POST `/api/v1/clientes`
```json
{
  "clienteid": "Joselema123",
  "nombre": "Jose Lema",
  "identificacion": "1234567890",
  "direccion": "Otavalo sn y principal",
  "telefono": "0985245785",
  "contrasena": "Secreto123@",
  "estado": true,
  "edad": 30,
  "genero": "Masculino"
}
```
### Actualizar Cliente
Put`/api/v1/clientes`
{

"nombre": "Whelinc",
"direccion": "Amazonas",
"telefono": "3898037864",
"contrasena": "Contrase$13",
"estado": true
}


### Crear Cuenta
POST `/api/v1/cuentas`
```json
{
  "numeroCuenta": "958978",
  "tipoCuenta": "Ahorro",
  "saldoInicial": 600,
  "estado": true,
  "clienteId": 1
}
```

### Cuenta Duplicada
POST `/api/v1/cuentas`
```json
{
  "numeroCuenta": "478758",
  "tipoCuenta": "Corriente",
  "saldoInicial": 100,
  "estado": true,
  "clienteId": 1
}
```
**Response:**
```json
{
  "error": true,
  "message": "Ya existe una cuenta con ese número"
}
```

### Movimiento - Depósito
POST `/api/v1/movimientos`
```json
{
  "tipoMovimiento": "Deposito",
  "valor": 500,
  "numeroCuenta": "958978"
}
```

### Movimiento - Retiro
POST `/api/v1/movimientos`
```json
{
  "tipoMovimiento": "Retiro",
  "valor": 500,
  "numeroCuenta": "478758"
}
```

### Movimiento - Saldo Insuficiente
POST `/api/v1/movimientos`
```json
{
  "tipoMovimiento": "Retiro",
  "valor": 9999,
  "numeroCuenta": "478758"
}
```
**Response:**
```
Saldo no disponible
```

### Cada Metodo tiene sus propias validaciones 
### Reporte por Cliente y Fechas
http://localhost:8080/api/v1/movimientos/reportes?clienteId=1&fechaDesde=2025-05-29&fechaHasta=2025-05-29

### Filtrar Movimientos por Tipo
GET `/api/v1/movimientos/tipo?tipo=Retiro`

---

## 📁 Estructura de Carpetas

```
src
 └── main
     ├── java
     │   └── com.example.crud.pruebaTec
     │       ├── controller
     │       ├── dto
     │       ├── exeption
     │       ├── mapper
     │       ├── model
     │       ├── repository
     │       ├── service
     │       ├── serviceImpl
     |       |__ validation 
     └── resources
         ├── application.properties
         └── data.sql (opcional)





