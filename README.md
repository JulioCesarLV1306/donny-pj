# Asistente de Expedientes - Corte del Santa

## 📋 Descripción del Proyecto

**Asistente de Expedientes** es una aplicación backend desarrollada en Spring Boot que proporciona servicios de gestión de expedientes judiciales para el Poder Judicial. El sistema integra múltiples bases de datos y proporciona APIs REST para la consulta, descarga y gestión de documentos judiciales digitalizados.

---

## 🏗️ Arquitectura del Proyecto

### **Arquitectura General**

El proyecto sigue una **arquitectura de capas (Layered Architecture)** basada en el patrón **MVC (Model-View-Controller)** adaptado para APIs REST. La estructura se organiza en tres módulos principales:

```
┌─────────────────────────────────────────────────────────────┐
│                     Cliente (Frontend)                       │
└─────────────────────┬───────────────────────────────────────┘
                      │ HTTP/REST
┌─────────────────────▼───────────────────────────────────────┐
│              Capa de Controladores (API REST)                │
│  ┌──────────────┬──────────────┬──────────────────────────┐ │
│  │  Asistente   │     SIJ      │         SECE             │ │
│  │  Controllers │  Controllers │      Controllers         │ │
│  └──────────────┴──────────────┴──────────────────────────┘ │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                   Capa de Servicios                          │
│  ┌──────────────┬──────────────┬──────────────────────────┐ │
│  │  Asistente   │     SIJ      │         SECE             │ │
│  │  Services    │  Services    │       Services           │ │
│  └──────────────┴──────────────┴──────────────────────────┘ │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│            Capa de Acceso a Datos (Repository)               │
│  ┌──────────────┬──────────────┬──────────────────────────┐ │
│  │  JPA/JDBC    │  JPA/JDBC    │      JPA/JDBC            │ │
│  │  Repositories│  Repositories│     Repositories         │ │
│  └──────────────┴──────────────┴──────────────────────────┘ │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                    Bases de Datos                            │
│  ┌──────────────┬──────────────┬──────────────────────────┐ │
│  │  PostgreSQL  │  PostgreSQL  │      PostgreSQL          │ │
│  │  (PJ_Bot)    │  (SIJ)       │      (SECE)              │ │
│  └──────────────┴──────────────┴──────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

### **Patrones de Diseño Implementados**

1. **MVC (Model-View-Controller)**: Separación de responsabilidades en capas.
2. **Repository Pattern**: Abstracción del acceso a datos.
3. **Service Layer Pattern**: Lógica de negocio centralizada.
4. **DTO Pattern**: Transferencia de datos entre capas.
5. **Dependency Injection**: Inyección de dependencias con Spring.

---

## 📁 Estructura de Carpetas y Clases

### **Estructura Principal del Proyecto**

```
asistente-expedientes-spring-main/
│
├── src/
│   ├── main/
│   │   ├── java/com/ncpp/asistenteexpedientes/
│   │   │   │
│   │   │   ├── 📄 AsistenteExpedientesApplication.java    ⭐ CORE PRINCIPAL
│   │   │   ├── 📄 ServletInitializer.java
│   │   │   │
│   │   │   ├── 📦 asistente/                              [Módulo: Gestión Interna]
│   │   │   │   ├── controller/                           (API REST)
│   │   │   │   │   ├── BitacoraController.java
│   │   │   │   │   ├── EncuestaController.java
│   │   │   │   │   └── UtilitarioController.java
│   │   │   │   ├── service/                              (Lógica de Negocio)
│   │   │   │   │   ├── BitacoraService.java
│   │   │   │   │   ├── DescargaService.java
│   │   │   │   │   ├── EncuestaService.java
│   │   │   │   │   ├── EstadisticasService.java
│   │   │   │   │   └── impl/
│   │   │   │   ├── entity/                               (Modelos de BD)
│   │   │   │   │   ├── Bitacora.java
│   │   │   │   │   ├── Descarga.java
│   │   │   │   │   ├── Encuesta.java
│   │   │   │   │   ├── Estadisticas.java
│   │   │   │   │   └── Modulo.java
│   │   │   │   ├── database/                             (Conexiones BD)
│   │   │   │   ├── dto/                                  (Data Transfer Objects)
│   │   │   │   └── payload/                              (Request/Response)
│   │   │   │
│   │   │   ├── 📦 sij/                                    [Módulo: Sistema Judicial]
│   │   │   │   ├── controller/                           (API REST)
│   │   │   │   │   ├── ActaController.java
│   │   │   │   │   ├── DocumentoDigitalizadoController.java
│   │   │   │   │   ├── DownloaderController.java
│   │   │   │   │   ├── ExpedienteController.java         ⭐ Principal
│   │   │   │   │   ├── ResolucionController.java
│   │   │   │   │   └── VideoController.java
│   │   │   │   ├── service/                              (Lógica de Negocio)
│   │   │   │   │   ├── impl/
│   │   │   │   │   └── specs/
│   │   │   │   ├── entity/                               (Modelos de BD)
│   │   │   │   │   ├── Archivo.java
│   │   │   │   │   ├── Deposito.java
│   │   │   │   │   ├── Expediente.java
│   │   │   │   │   ├── Fecha.java
│   │   │   │   │   └── ServidorFtp.java
│   │   │   │   ├── database/                             (Conexiones BD)
│   │   │   │   └── payload/                              (Request/Response)
│   │   │   │
│   │   │   ├── 📦 sece/                                   [Módulo: Sistema SECE]
│   │   │   │   ├── controller/                           (API REST)
│   │   │   │   │   └── PersonaController.java
│   │   │   │   ├── service/                              (Lógica de Negocio)
│   │   │   │   │   ├── PersonaService.java
│   │   │   │   │   └── PersonaServiceImpl.java
│   │   │   │   ├── entity/                               (Modelos de BD)
│   │   │   │   │   ├── Persona.java
│   │   │   │   │   └── Tipo.java
│   │   │   │   └── repository/                           (Acceso a Datos)
│   │   │   │
│   │   │   └── 📦 util/                                   [Módulo: Utilidades]
│   │   │       ├── Constants.java                        ⭐ Configuraciones
│   │   │       ├── CORS.java
│   │   │       ├── DepositoWS.java
│   │   │       ├── FTPDownloader.java
│   │   │       ├── InternalServerException.java
│   │   │       ├── LogDony.java
│   │   │       ├── NotFoundException.java
│   │   │       ├── ToPDF.java
│   │   │       └── Util.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties                    ⭐ Configuración Principal
│   │       ├── banner.txt
│   │       └── jasper/
│   │           └── depositos.jasper
│   │
│   └── lib/                                               (Librerías externas)
│       └── jconn4.jar
│
├── 📄 pom.xml                                             ⭐ Dependencias Maven
├── 📄 asistente_db.sql                                    (Script BD)
├── 📄 mvnw & mvnw.cmd                                     (Maven Wrapper)
└── 📄 README.md                                           (Este archivo)
```

---

## ⭐ CORE PRINCIPAL DEL PROYECTO

### **1. AsistenteExpedientesApplication.java**

**Ubicación:** `src/main/java/com/ncpp/asistenteexpedientes/AsistenteExpedientesApplication.java`

Este es el **punto de entrada principal** de la aplicación. Es una clase de configuración de Spring Boot que:

- Inicia el contenedor de Spring Boot
- Configura el auto-escaneo de componentes
- Extiende `SpringBootServletInitializer` para permitir el despliegue como WAR en servidores externos (Tomcat)

```java
@SpringBootApplication
@EnableAutoConfiguration
public class AsistenteExpedientesApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AsistenteExpedientesApplication.class, args);
    }
}
```

---

## 🔧 Tecnologías y Dependencias

### **Framework Principal**
- **Spring Boot 2.2.6** - Framework base
- **Spring Web** - REST APIs
- **Spring Data JPA** - Persistencia de datos
- **Hibernate** - ORM

### **Base de Datos**
- **PostgreSQL 42.5.4** - Base de datos principal
- **jconn4** - Conexión a bases de datos Sybase (legacy)

### **Procesamiento de Documentos**
- **Apache PDFBox 2.0.24** - Manipulación de archivos PDF
- **JasperReports 6.17.0** - Generación de reportes
- **JODConverter 2.2.1** - Conversión de documentos Office
- **OpenOffice Libraries** - Integración con OpenOffice

### **Utilidades**
- **Lombok** - Reducción de código boilerplate
- **Gson 2.10.1** - Serialización/Deserialización JSON
- **Commons Net 3.9.0** - Cliente FTP
- **Commons IO 2.11.0** - Utilidades de I/O
- **Commons Codec 1.15** - Codificación/Decodificación

### **Versiones**
- **Java:** 11
- **Maven:** 3.x (vía wrapper)
- **Empaquetado:** WAR

---

## 🌐 Módulos del Sistema

### **1. Módulo ASISTENTE** (`asistente/`)

**Propósito:** Gestión interna del sistema, bitácoras, encuestas y estadísticas.

**Componentes principales:**
- `BitacoraController` - Registro de actividades del sistema
- `EncuestaController` - Gestión de encuestas
- `UtilitarioController` - Funcionalidades auxiliares

**Endpoints:**
- `/apiv1/bitacora` - Gestión de bitácoras
- `/apiv1/encuesta` - Gestión de encuestas
- `/` - Utilidades generales

---

### **2. Módulo SIJ** (`sij/`) ⭐ **MÓDULO PRINCIPAL**

**Propósito:** Sistema de Información Judicial - Gestión de expedientes, resoluciones, actas y documentos digitalizados.

**Componentes principales:**
- `ExpedienteController` - **Controlador principal** para expedientes judiciales
- `DocumentoDigitalizadoController` - Gestión de documentos digitales
- `ResolucionController` - Consulta de resoluciones judiciales
- `ActaController` - Gestión de actas
- `VideoController` - Gestión de videos de audiencias
- `DownloaderController` - Descarga de archivos desde servidores FTP

**Endpoints:**
- `/apiv1/expediente` - **Endpoint principal** del sistema
- `/apiv1/digitalizados` - Documentos digitalizados
- `/apiv1/resoluciones` - Resoluciones judiciales
- `/apiv1/actas` - Actas judiciales
- `/apiv1/videos` - Videos de audiencias
- `/apiv1/downloader` - Descarga de archivos

**Entidades clave:**
- `Expediente` - Información del expediente judicial
- `Archivo` - Archivos digitales asociados
- `Deposito` - Información de depósitos judiciales
- `ServidorFtp` - Configuración de servidores FTP

---

### **3. Módulo SECE** (`sece/`)

**Propósito:** Sistema Electrónico de Causas Electrónicas - Gestión de personas (abogados, partes procesales).

**Componentes principales:**
- `PersonaController` - Gestión de personas
- `PersonaService` - Lógica de negocio de personas

**Endpoints:**
- `/apiv1/persona` - Gestión de personas

**Entidades:**
- `Persona` - Información de personas (abogados, partes)
- `Tipo` - Tipos de personas

---

### **4. Módulo UTIL** (`util/`)

**Propósito:** Clases de utilidad, configuraciones y servicios transversales.

**Componentes clave:**

| Clase | Función |
|-------|---------|
| `Constants.java` | Constantes globales del sistema (IP, rutas, versión API) |
| `CORS.java` | Configuración de CORS para el API |
| `FTPDownloader.java` | Descarga de archivos desde servidores FTP |
| `ToPDF.java` | Conversión de documentos a PDF |
| `DepositoWS.java` | Cliente de Web Service de depósitos judiciales |
| `LogDony.java` | Sistema de logging personalizado |
| `Util.java` | Funciones auxiliares generales |
| `NotFoundException.java` | Excepción personalizada para recursos no encontrados |
| `InternalServerException.java` | Excepción personalizada para errores internos |

---

## 🔌 Configuración de Bases de Datos

El sistema se conecta a múltiples bases de datos PostgreSQL:

### **Base de Datos Principal (SECE)**
```properties
spring.datasource.url=jdbc:postgresql://172.17.104.247:5432/PJ_Bot
spring.datasource.username=PJBOT_ADM
spring.datasource.password=PJBOT_ADM125689$$#
```

### **Configuraciones Adicionales**
- **SIJ (Sistema de Información Judicial)** - Configurado mediante datasource secundario
- **ASISTENTE** - Base de datos para gestión interna

---

## 📡 API REST

### **Versión del API**
```
Base URL: /apiv1
```

### **Endpoints Principales**

| Módulo | Endpoint | Descripción |
|--------|----------|-------------|
| **SIJ** | `/apiv1/expediente` | Consulta de expedientes judiciales |
| **SIJ** | `/apiv1/digitalizados` | Documentos digitalizados |
| **SIJ** | `/apiv1/resoluciones` | Resoluciones judiciales |
| **SIJ** | `/apiv1/actas` | Actas judiciales |
| **SIJ** | `/apiv1/videos` | Videos de audiencias |
| **SIJ** | `/apiv1/downloader` | Descarga de archivos FTP |
| **SECE** | `/apiv1/persona` | Gestión de personas |
| **ASISTENTE** | `/apiv1/bitacora` | Registro de bitácoras |
| **ASISTENTE** | `/apiv1/encuesta` | Gestión de encuestas |

---

## 🚀 Compilación y Despliegue

### **Requisitos Previos**
- Java 11 JDK
- Maven 3.x (incluido via wrapper)
- Servidor de aplicaciones (Tomcat 9.x o superior)
- PostgreSQL 10+

### **Compilar el Proyecto**

```powershell
# Windows PowerShell
$env:JAVA_HOME="C:\Program Files\Java\jdk-11"
.\mvnw.cmd clean package -DskipTests
```

```bash
# Linux/Mac
export JAVA_HOME=/path/to/jdk-11
./mvnw clean package -DskipTests
```

### **Archivo Generado**
```
target/donybackend.war
```

### **Desplegar en Tomcat**
1. Copiar `target/donybackend.war` al directorio `webapps` de Tomcat
2. Iniciar Tomcat
3. Acceder a: `http://localhost:8080/donybackend/apiv1/`

---

## 📂 Archivos de Configuración Clave

### **1. application.properties**
Configuración principal de la aplicación (bases de datos, JPA, etc.)

### **2. pom.xml**
Dependencias Maven y configuración de build

### **3. Constants.java**
```java
IP_SERVER = "172.17.104.247"
VERSION_API = "/apiv1"
RUTA_ARCHIVOS_CONF = "\\172.17.104.247\Asistente_Dony"
```

---

## 🔒 Seguridad

- **CORS configurado** mediante la clase `CORS.java`
- **Manejo de excepciones centralizado** con `NotFoundException` e `InternalServerException`
- **Validación de datos** en capa de servicios

---

## 📊 Flujo de Datos Típico

```
1. Cliente → HTTP Request → Controller
2. Controller → llama a → Service
3. Service → lógica de negocio → Repository
4. Repository → JPA/JDBC → PostgreSQL
5. PostgreSQL → datos → Repository
6. Repository → datos → Service
7. Service → DTO → Controller
8. Controller → JSON Response → Cliente
```

---

## 🛠️ Herramientas de Desarrollo

### **Logs**
- Sistema de logging personalizado: `LogDony.java`
- Logs de Hibernate habilitados: `spring.jpa.show-sql=true`

### **Reportes**
- JasperReports configurado en `resources/jasper/`
- Template: `depositos.jasper`

### **FTP**
- Descarga automática de archivos via `FTPDownloader.java`
- Configuración de servidores FTP en entidad `ServidorFtp`

---

## 👥 Información del Proyecto

- **Nombre:** Asistente de Expedientes
- **Versión:** 0.11
- **Organización:** NCPP (Consejo del Poder Judicial)
- **Descripción:** Asistente Corte del Santa
- **Artifact ID:** `asistente-expedientes`
- **Group ID:** `com.ncpp`
- **Empaquetado:** WAR

---

## 📝 Notas Importantes

1. **El proyecto está diseñado para ser desplegado como WAR** en un servidor de aplicaciones externo.

2. **Múltiples fuentes de datos** configuradas para integrar diferentes sistemas del Poder Judicial.

3. **El módulo SIJ es el core funcional** del sistema, manejando expedientes y documentos judiciales.

4. **Integración con sistemas legacy** mediante jconn4 para bases de datos Sybase.

5. **Procesamiento de documentos** incluye conversión a PDF y generación de reportes.

6. **Sistema de descarga distribuido** con integración FTP para archivos judiciales.

---

## 🔗 Recursos Adicionales

- **Script de Base de Datos:** `asistente_db.sql`
- **Scripts de Despliegue:** `copy_files.bat`, `run_copy.bat`

---

## 📞 Soporte

Para soporte técnico o consultas sobre el proyecto, contactar al equipo de desarrollo del Poder Judicial - Corte del Santa.

---

**Última actualización:** Diciembre 2025  
**Versión del documento:** 1.0
