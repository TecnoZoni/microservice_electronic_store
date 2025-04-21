# 🛒 Tienda de Electrodomésticos - Arquitectura de Microservicios

Este repositorio contiene el desarrollo de un sistema distribuido para una tienda online de electrodomésticos, basado en una arquitectura de **microservicios**. El sistema está compuesto por tres servicios principales: **Productos**, **Carrito de Compras** y **Ventas**, cada uno encargado de gestionar una parte específica del dominio del negocio. La comunicación entre ellos se realiza a través de HTTP mediante REST APIs, y se complementa con herramientas de Spring Cloud para tolerancia a fallos, descubrimiento de servicios, balanceo de carga y gateway.

---

## 📐 Arquitectura General

```
                         ┌─────────────────────────────┐
                         │       API Gateway           │
                         │    (Spring Cloud Gateway)   │
                         └────────────┬────────────────┘
                                      │
       ┌──────────────────────────────┼──────────────────────────────┐
       │                              │                              │
┌──────▼───────┐             ┌────────▼────────┐            ┌────────▼────────┐
│  Productos   │             │   Carrito       │            │     Ventas      │
│  Service     │             │   Service       │            │     Service     │
│ (CRUD de     │◄───────────►│ (Agregar/Quitar │◄──────────►│  (Registrar      │
│ productos)   │             │ productos)      │            │  ventas)        │
└──────────────┘             └─────────────────┘            └─────────────────┘

Todos los servicios están registrados en:
┌─────────────────────────────┐
│      Eureka Server          │
│  (Descubrimiento de servicios)│
└─────────────────────────────┘
```

---

## 🧩 Microservicios

### 🧰 1. Servicio de Productos

- Encargado de gestionar los electrodomésticos disponibles en la plataforma.
- Expone endpoints para:
  - Listar productos
  - Consultar un producto por ID
  - Crear, actualizar y eliminar productos
- Atributos:
  - `id`, `codigo`, `nombre`, `marca`, `precio`

### 🛒 2. Servicio de Carrito de Compras

- Permite a los usuarios agregar o quitar productos del carrito.
- Atributos del carrito:
  - `id`, `listaProductos`, `precioTotal`
- Funcionalidades:
  - Agregar producto al carrito
  - Eliminar producto del carrito
  - Calcular precio total automáticamente
- Consume el microservicio de Productos para obtener los detalles de cada producto.

### 💳 3. Servicio de Ventas

- Registra una venta final con:
  - `id`, `fecha`, `carritoAsociado`
- Consulta al servicio de Carrito para obtener los productos y el precio total.
- Permite obtener el detalle completo de una venta.

---

## ⚙️ Tecnologías Utilizadas

- Java 21
- Spring Boot
- Spring Cloud
  - Eureka Server
  - Spring Cloud Gateway
  - Load Balancer
  - Resilience4J
- Maven
- RESTful APIs
- Postman (testing)
- Lombok
- MySQL (según configuración)

---

## 📌 Requisitos

- JDK 21+
- Maven 3.4+
- Postman
---

## 🗂️ Estructura del Proyecto

```
/discovery_server
    └── EurekaServiceApplication.java
/productos_service
    └── ProductoController.java
    └── ProductoService.java
/carrito_service
    └── CarritoController.java
    └── CarritoService.java
/ventas_service
    └── VentaController.java
    └── VentaService.java
/api_gateway
    └── ApiGatewayApplication.java
```

---

## 🧪 Testing

Usar Postman para probar:
- Registro y consulta de productos
- Agregar productos a un carrito y consultar el total
- Crear una venta y verificar el monto total y productos asociados

---

## 🔁 Load Balancer y Tolerancia a Fallos

- El microservicio de Productos tiene múltiples instancias configuradas en diferentes puertos (`8081`, `8082`, etc.).
- Se usa `Spring Cloud Load Balancer` para distribuir las peticiones automáticamente.
- `Resilience4J` maneja:
  - Circuit Breaker (para prevenir fallos en cascada)
  - Retry (para reintentos automáticos)
  - Fallback methods (respuestas alternativas ante errores)

---

## 🔐 Eureka Server

- Todos los microservicios están registrados en el **Servidor de Eureka**.
- Cada microservicio tiene un nombre único para identificación en el `application.properties`.

---

## 🌐 API Gateway

- Un único punto de entrada para los clientes.
- Redirige las peticiones a los microservicios correspondientes.
- Facilita el control de rutas, filtros y seguridad.

---

## 📝 Configuraciones Importantes (`application.properties`)

### Productos Service
```properties
spring.application.name=productos-service
server.port=8081
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

### Carrito Service
```properties
spring.application.name=carrito-service
server.port=8083
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

### Ventas Service
```properties
spring.application.name=ventas-service
server.port=8084
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

### Gateway
```properties
spring.application.name=gateway-service
server.port=443
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

---

## 📎 TODOs

- [ ] Crear servicios base
- [ ] Configurar Eureka y Gateway
- [ ] Implementar CRUDs
- [ ] Configurar Load Balancer con múltiples instancias
- [ ] Agregar Circuit Breaker y Retry
- [ ] Agregar autenticación y autorización (opcional)
- [ ] Dockerizar los servicios (opcional)

---

## 👨‍💻 Autor

**Agustín Minzoni**  
Desarrollador Backend Java  
🌐 [agustinminzoni.com](https://agustinminzoni.com)  
🐙 [github.com/TecnoZoni](https://github.com/TecnoZoni)
