# Gestión de Frutix 🍎🍌🍇

¡Bienvenido/a al sistema de Gestión de Frutix! Este proyecto es una simulación de una base de datos para la gestión de una tienda de frutas, implementada en Java con enfoque en POO, polimorfismo y listas.

## Objetivo 🎯

El objetivo de este proyecto es permitir la gestión de una tienda de frutas. Los usuarios podrán iniciar sesión, cerrar sesión, y según su rol, realizar diversas operaciones.

## Características ✨

- Inicio de Sesión: Permite a los usuarios iniciar sesión en el sistema.
- Cierre de Sesión: Permite a los usuarios cerrar sesión en el sistema.
- Gestión de Frutas: Los usuarios con rol administrador pueden agregar, actualizar, eliminar y ver frutas y usuarios en el sistema.
- Gestión de Ventas: Los usuarios con rol vendedor pueden realizar ventas de frutas.
- La cantidad de frutas vendidas depende de la cantidad disponible. Solo se puede vender una fruta si su cantidad es igual o menor a la cantidad disponible y si la fruta está disponible.

## Requisitos 🛠️

- Java Development Kit (JDK) 8 o superior.
- NetBeans IDE (opcional, pero recomendado para facilidad de desarrollo).
- MySQL

## Instrucciones de Uso 📖

1. Clona o descarga el repositorio del proyecto desde GitHub.
2. Abre el proyecto en NetBeans o tu IDE preferido.
3. Sigue las instrucciones en pantalla para interactuar con el sistema y gestionar la tienda de frutas.

> [!TIP]
> ```bash
> git clone https://github.com/Minkaspr/Frutix.git
> ```

## Estructura del Proyecto 🏗️
El proyecto está organizado en los siguientes paquetes y clases:

- frutix: Paquete principal del proyecto.
- dao: Contiene las interfaces y clases para el acceso a la base de datos.
- dto: Contiene clases para el transporte de datos específicos de las entidades como FrutaDTO y UsuarioDTO.
- entity: Contiene las clases que representan las entidades de la base de datos.
- util: Contiene las clases de utilidad, como la conexión a la base de datos.
- web: Contiene el servlet y el validador.

## Base de Datos 🗄️
La base de datos consta de las siguientes tablas:
- Usuario: Tabla principal con campos como id_usuario, correo, clave, rol, estado.
- Fruta: Tabla con campos id_fruta, nombre, descripcion, cantidadKilogramos, precioPorKilogramo, disponible.
- Venta: Tabla con campos id_venta, usuario, fechaVenta, precioTotal.
- DetalleVenta: Tabla con campos id_detalleVenta, venta, fruta, precioVenta, cantidad.

## Tecnologías Utilizadas 🚀
- Java
- NetBeans IDE
- MySQL
- Apache Tomcat
- Servlets
- HTML
