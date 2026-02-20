# Electro Store System

Backend de e-commerce desarrollado bajo una arquitectura de microservicios utilizando Spring Boot y Spring Cloud.

El sistema implementa API Gateway, balanceo de carga, mecanismos de resiliencia y una configuración centralizada para garantizar una comunicación confiable entre servicios.

Simula una tienda online de electrodomésticos donde los microservicios de productos, carritos y ventas interactúan de manera desacoplada mediante OpenFeign.

## Tecnologías Utilizadas
* Java 17
* Spring Boot
* Spring Cloud
  - Eureka Server (Service Discovery)
  - Spring Cloud Gateway (API Gateway)
  - Spring Cloud LoadBalancer
  - Spring Cloud Config Server
  - OpenFeign
  - Resilience4J
* Maven
* JPA / Hibernate
* Docker

## Arquitectura

El sistema está compuesto por los siguientes microservicios:

### products-service

- Gestiona los productos a través de un CRUD
- Incluye lógica de actualización de stock
- Persiste los productos en una base de datos

### cart-service 

- Gestiona los carritos de compra a través de un CRUD
- Crea el detalle de los carritos, calculando dinámicamente totales y subtotales 
- Se comunica con products-service mediante OpenFeign para obtener productos y actualizar su stock al crear, editar o eliminar un carrito
- Implementa mecanismos de resiliencia ante fallas en la comunicación
- Permite múltiples instancias para balancear la carga
- Persiste los carritos en una base de datos

### sale-service 

- Gestiona las ventas a través de un CRUD
- Asocia cada venta a un carrito, consumiendo la api cart-service mediante OpenFeign y la de products-service a través de la anterior
- Implementa mecanismos de resiliencia ante fallas en la comunicación
- Permite múltiples instancias para balancear la carga
- Persiste las ventas en una base de datos

### eureka-sv

- Registra todos los microservicios
- Permite una resolución dinámica de los servicios
- Facilita la comunicación sin necesidad de direcciones estáticas

### api-gateway

- Es un punto único de entrada para clientes externos
- Redirige las solicitudes al microservicio correspondiente
- Está integrado con Eureka para ruteo dinámico

### config-server

- Centraliza la configuración de todos los microservicios
- Permite configuración por entorno

## Aspectos Destacados del Proyecto

* Implementa Spring Cloud LoadBalancer: se pueden ejecutar múltiples instancias de algunos servicios para balancear la carga
* Implementación de Circuit Breaker (Resilience4J):
  - Prevención de fallas en cascada
  - Implementación de métodos fallback para gestionar fallos repetidos de comunicación entre servicios
  - Implementación de reintentos de comunicación automáticos
* Comunicación fluida entre servicios mediante OpenFeign
* Aplicación de buenas prácticas de diseño y separación de responsabilidades
* Arquitectura en capas y orientada a servicios
* Además del CRUD, cada servicio incluye los métodos de lógica de negocio necesarios para su correcto funcionamiento
* Soporte con Docker: cada microservicio incluye su Dockerfile, integración dentro de un docker-compose y red compartida entre contenedores
