# PuntoGrano - Sistema de Gestión de Cafetería + Fidelización de clientes

Proyecto académico EV2 y EV3 CFTSA, que consiste en un punto de ventas + fidelización para cafeterías, desarrollado en Java utilizando patrón MVC. El sistema soluciona el problema que tienen muchas cafeterías que recién están comenzando donde registran todo a mano o en tablas Excel y otorgan a sus clientes tarjetas timbradas de fidelización que estos suelen perder.

---

## Configuración de la Base de Datos

El sistema utiliza persistencia de datos mediante MySQL y JDBC:

- **Motor de Base de Datos:** MySQL Server (puerto `3306`).
- **Nombre de la Base de Datos:** `puntograno`.
- **Script SQL:** El archivo `PuntoGrano.sql` (en la carpeta database del proyecto) contiene las instrucciones para crear la base de datos, tablas, llaves foráneas y datos de prueba.
- **Parámetros de Conexión:** Ubicados en `src/main/java/puntograno/conexion/ConexionBD.java`:
  - **URL:** `jdbc:mysql://localhost:3306/puntograno`
  - **Usuario:** `root`
  - **Contraseña:** `Analista123.` *(clave generica, se debe ajustar según la configuración local si es necesario)*.

---

## Credenciales de Acceso para Pruebas

El sistema cuenta con autenticación simulada y un par de cuentas ficticias para demostrar el polimorfismo, donde los usuarios operadores tienen acceso a funcionalidades más limitadas en comparación con los administradores. Se puede probar con las credenciales de acceso: Administrador/Ad123 y Operador/Op123.

## Enlace de repositorio (control de versiones con Git y Github)
https://github.com/AlejandroBH/Punto-Grano---CFTSA
