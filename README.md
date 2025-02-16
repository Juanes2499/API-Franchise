# Franchise API

## Requisitos previos
Para desplegar la API **Franchise**, asegúrese de tener instalado lo siguiente:

- **Java 17** o una versión superior.
- **Gradle 8.8** o una versión superior.

## Despliegue
Siga estos pasos para ejecutar la API:

1. Clone el repositorio:
   ```sh
   git clone <URL_DEL_REPOSITORIO>
   cd franchise-api
   ```

2. Compile y construya el proyecto con Gradle:
   ```sh
   ./gradlew build
   ```
   *(En Windows, use `gradlew.bat build`)*

3. Ejecute la aplicación:
   ```sh
   ./gradlew bootRun
   ```

4. La API estará disponible en:
   ```
   http://localhost:8080
   ```

## Configuración
Se debe tener en cuenta las siguientes variables de configuración, que deben ubicarse en el archivo `application.yaml` en la ruta:
```
applications/app-service/src/main/resources/application.yaml
```
En el atributo `adapters.r2dbc`:

```yaml
adapters:
  r2dbc:
    host: "franchisesdatabase.cf82ye4wwyn5.us-east-1.rds.amazonaws.com"
    port: 5432
    database: "franchisesDataBase"
    schema: "franchisesSchema"
    username: "postgres"
    password: "${DB_PASSWORD}"
```

**Nota:** `DB_PASSWORD` debe configurarse como una variable de entorno para mayor seguridad.

## Despliegue de la Base de Datos
Para desplegar la base de datos, ejecute el siguiente script SQL:

```sql
CREATE DATABASE "franchisesDataBase";

CREATE SCHEMA IF NOT EXISTS "franchisesSchema";

CREATE TABLE "franchisesSchema"."franchises" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(30) NOT NULL,
    "createdAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "franchisesSchema"."branches" (
    "id" SERIAL PRIMARY KEY,
    "franchiseId" INT NOT NULL,
    "name" VARCHAR(30) NOT NULL,
    "address" VARCHAR(100) NOT NULL,
    "phoneNumber" VARCHAR(14) NOT NULL,
    "createdAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_branches_franchises FOREIGN KEY ("franchiseId") REFERENCES "franchisesSchema".franchises("id") ON DELETE CASCADE
);

CREATE TABLE "franchisesSchema"."products" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(30) NOT NULL,
    "description" VARCHAR(100) NOT NULL,
    "price" NUMERIC(10,2) NOT NULL,
    "createdAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "franchisesSchema"."branches_products" (
    "id" SERIAL PRIMARY KEY,
    "branchId" INT NOT NULL,
    "productId" INT NOT NULL,
    "stock" INT NOT NULL,
    "createdAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_branches FOREIGN KEY ("branchId") REFERENCES "franchisesSchema".branches("id") ON DELETE CASCADE,
    CONSTRAINT fk_products FOREIGN KEY ("productId") REFERENCES "franchisesSchema".products("id") ON DELETE CASCADE
);

-- Trigger para actualizar `updated_at` automáticamente
CREATE FUNCTION "franchisesSchema".update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW."updatedAt" = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_branches
BEFORE UPDATE ON "franchisesSchema".branches
FOR EACH ROW
EXECUTE FUNCTION "franchisesSchema".update_updated_at_column();

CREATE TRIGGER trigger_update_products
BEFORE UPDATE ON "franchisesSchema".products
FOR EACH ROW
EXECUTE FUNCTION "franchisesSchema".update_updated_at_column();

CREATE TRIGGER trigger_update_branches_products
BEFORE UPDATE ON "franchisesSchema".branches_products
FOR EACH ROW
EXECUTE FUNCTION "franchisesSchema".update_updated_at_column();
```

## Endpoints
### Health Check
Para verificar el estado de la API, use el siguiente endpoint:
```
GET http://localhost:8080/api/health
```

### Crear una Franquicia
Para crear una nueva franquicia, realice una solicitud `POST` al siguiente endpoint:
```
POST http://localhost:8080/api/franchise
```
**Cuerpo de la petición (`JSON`):**
```json
{
    "name": "Franchise name"
}
```

### Actualizar Nombre de una Franquicia
Para actualizar el nombre de una franquicia existente, realice una solicitud `PUT` al siguiente endpoint:
```
PUT http://localhost:8080/api/franchise/name
```
**Cuerpo de la petición (`JSON`):**
```json
{
    "id": #Franchise  ID#,
    "name": "New franchise name"
}
```

### Crear una Sucursal
Para crear una nueva sucursal asociada a una franquicia, realice una solicitud `POST` al siguiente endpoint:
```
POST http://localhost:8080/api/branch
```
**Cuerpo de la petición (`JSON`):**
```json
{
    "franchiseId": #Franchise ID#,
    "name": "Branch name",
    "address": "Branch address",
    "phoneNumber": "Branch phone number"
}
```

### Consultar Productos de una Sucursal
Para consultar los productos de una sucursal, realice una solicitud `GET` al siguiente endpoint:
```
GET http://localhost:8080/api/branchesProducts
```

### Eliminar un Producto de una Sucursal
Para eliminar un producto de una sucursal, realice una solicitud `DELETE` al siguiente endpoint:
```
DELETE http://localhost:8080/api/branchesProducts
```
**Cuerpo de la petición (`JSON`):**
```json
{
    "branchId": #Branch ID#,
    "productId": #Product ID#
}
```

### Actualizar el Stock de un Producto de una Sucursal
Para actualizar el stock de un producto en una sucursal, realice una solicitud `PUT` al siguiente endpoint:
```
PUT http://localhost:8080/api/branchesProducts/stock
```

**Cuerpo de la petición (`JSON`):**
```json
{
    "branchId": #Branch ID#,
    "productId": #Product ID#,
    "stock": #New stock#
}
```

### Mostrar el Producto con Más Stock por Sucursal para una Franquicia Puntual
Para consultar el producto con más stock por sucursal para una franquicia específica, realice una solicitud `GET` al siguiente endpoint:
```
GET http://localhost:8080/api/franchise/1/maxStock
```

### Implementación de CI/CD con GitHub Actions y Docker

Actualmente, se está utilizando **GitHub Actions** para automatizar el proceso de **compilación de la imagen** y **subida al repositorio de Docker Hub**. El flujo de trabajo de CI/CD está compuesto por dos etapas principales:

1. **Create environment files**: Esta etapa prepara los archivos de entorno necesarios para la compilación.
2. **Build and push**: En esta etapa, se compila la imagen y se sube al repositorio de Docker Hub.

El archivo de configuración de este flujo de trabajo de GitHub Actions se encuentra en el siguiente enlace:

- [GitHub Actions Pipeline](https://github.com/Juanes2499/API-Franchise/blob/main/.github/workflows/apifranchise_github_action_gradle.yaml)

#### Repositorio Docker Hub
La imagen compilada se sube al siguiente repositorio de Docker Hub:

- [Docker Hub Repository](https://hub.docker.com/repository/docker/juanes2499/nequi-api-franchise/tags)

#### Proceso para Ejecutar un Pipeline

Para ejecutar un pipeline, sigue estos pasos:

1. **Crear una rama** a partir de la rama `main`.
2. Realiza las modificaciones necesarias en tu código.
3. **Sube los cambios** a la rama creada.
4. **Crea un pull request** contra la rama `main`.
5. **Completa el pull request** y realiza el merge.
6. **Crea un nuevo release** a partir de un nuevo tag.
7. Finalmente, **publica el release**.

Este flujo asegura que cualquier cambio se automatice, compile y se suba correctamente a Docker Hub.

# Acerca de como esta implementado el proyecto:

## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por último el inicio y configuración de la aplicación.

Lee el artículo [Clean Architecture - Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el módulo más interno de la arquitectura, pertenece a la capa del dominio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este módulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define lógica de aplicación y reacciona a las invocaciones desde el módulo de entry points, orquestando los flujos hacia el módulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no están arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
genéricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patrón de diseño [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio.

## Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma automática, inyectando en éstos instancias concretas de las dependencias declaradas. Además inicia la aplicación (es el único módulo del proyecto donde encontraremos la función public static void main(String[] args).

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**
