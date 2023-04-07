# LoginPublic
Api Restfull Springboot, JPA, JWT, SpringSerurity, Junit4.


Documentacion de los endPonint
https://documenter.getpostman.com/view/4963699/2s93XsWkFS

En la rama principal del repositorio solo esta el proyecto basico con la disponibilizacion de los endpoint con autorizacion basica de usuario y clave para su Uso.

En Necesario dejar la intancion en la base de datos antes 

create database sistema_login_user4

Luego correr la aplicación de springboot para que cree todas las tablas necesarias para funcionar.

realice esta insert para que puede utilizar los endpoint criticos
use sistema_login_user3;

INSERT INTO `sistema_login_user3`.`roles` (`id`, `nombre`) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `sistema_login_user3`.`roles` (`id`, `nombre`) VALUES ('2', 'ROLE_USER');

En la rama nttDataSctruct se encuentra el proyecto desarrollado con Junit y JWT
por lo tanto, para que funcione correctamente debes agregar un usuario 

Por medio del endponit http://localhost:8080/api/auth/registrar en la documentacion de postman esta la estructuta del Json 
Finalmente debe iniciar sesion en endponit http://localhost:8080/api/auth/iniciarSesion endponit las credenciales recien credas y la estructura Json ya comentada
para obtener el Token y por medio de Authorization type: Bearer Token podras utilizar los desmas endpoint 


