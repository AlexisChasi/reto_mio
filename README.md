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
  
- spring.application.name=crud
- spring.datasource.url=jdbc:postgresql://localhost:5432/crud
- spring.datasource.username=postgres
- spring.datasource.password=123456
- spring.jpa.hibernate.ddl-auto=create-drop
- spring.jpa.show-sql=true
- spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
- spring.jpa.properties.hibernate.format_sql=true


# Crear Cliente-cuenta- movimiento
- CLIENTE

{
"nombre":"ACH",
"genero":"M",
"edad":20,
"identificacion":"17234567489",
"direccion":"Torres del castilo",
"telefono":"096999999",
"clienteid":"2905022",
"contrasena":"password123",
"estado":true
}
------
- CUENTA

{
"numeroCuenta":"123456788",
"tipoCuenta":"Ahorros",
"saldoInicial": 500.00,
"estado": true,
"cliente":{
"id":1
}
}
___________
- MOVIMIENTO

{
"tipoMovimiento":"DEPOSITO",
"valor":800,
"cuenta":{
"numeroCuenta":"123456788"
}
}




