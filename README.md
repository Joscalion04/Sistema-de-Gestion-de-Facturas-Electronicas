# Sistema-de-Gestion-de-Facturas-Electronicas
Este proyecto es un sistema web para gestionar facturas electrónicas con renderizado del lado del cliente (CSR). El FrontEnd usa JavaScript y el BackEnd utiliza Java con servicios RESTful y una base de datos MySQL. La comunicación se realiza mediante peticiones asíncronas con APIs como Fetch.

## Características

- **FrontEnd:** Desarrollado con JavaScript y tecnologías relacionadas para renderizado del lado del cliente.
- **BackEnd:** Implementado en Java con servicios RESTful para la lógica de la aplicación.
- **Base de Datos:** Utiliza MySQL para almacenamiento de datos.
- **Comunicación:** Interacción entre FrontEnd y BackEnd mediante peticiones asíncronas con APIs como Fetch.

## Funcionalidades

1. **Registro de Proveedor:** Permite a los proveedores registrarse en el sistema.
2. **Configuración de Perfil:** Los proveedores pueden configurar sus datos, incluyendo información necesaria para su perfil ante Hacienda.
3. **Registro de Clientes:** Posibilidad de registrar clientes habituales por parte de los proveedores.
4. **Registro de Productos/Servicios:** Los proveedores pueden registrar los productos o servicios que ofrecen.
5. **Facturación:** Funcionalidad para que los proveedores puedan generar facturas electrónicas por sus ventas.
6. **Visualización de Facturas:** Acceso para que los proveedores puedan ver sus facturas, incluyendo representaciones en PDF y XML.
7. **Administración:** Los administradores del sistema pueden gestionar las solicitudes de registro de nuevos proveedores, listar los proveedores registrados y desactivar cuentas según sea necesario.

## Instalación
Para ejecutar este proyecto localmente, sigue estos pasos:

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/tu-usuario/tu-repositorio.git
   cd tu-repositorio
   
2. **Abrir el Proyecto en IntelliJ IDEA**
  Abre IntelliJ IDEA.
  Selecciona File > Open.
  Navega hasta la carpeta donde clonaste el repositorio y selecciona el archivo de configuración del proyecto (por ejemplo, pom.xml para proyectos Maven).

3. **Configurar la Base de Datos MySQL**
  Asegúrate de tener MySQL instalado y configurado localmente.
  Actualiza las configuraciones de conexión a la base de datos en el archivo application.properties dentro del proyecto Spring Boot.

4. **Configurar y Ejecutar el Backend**
  Dentro de IntelliJ IDEA, asegúrate de que Maven haya importado todas las dependencias correctamente.
  Configura la ejecución de la aplicación Spring Boot:
    Navega hasta la clase principal de la aplicación (generalmente anotada con @SpringBootApplication).
    Haz clic con el botón derecho y selecciona Run o Debug según prefieras.
   
6. **Acceder a la Aplicación**
  Una vez que el backend y el frontend estén en ejecución, abre tu navegador web.
  Visita http://localhost:3000 para interactuar con la aplicación web.

**Notas**
Asegúrate de tener configurado y ejecutando correctamente tanto el backend como el frontend para el funcionamiento completo de la aplicación.
Verifica y actualiza las configuraciones de conexión a la base de datos y otras dependencias según sea necesario para tu entorno de desarrollo.

## Creditos
Este repositorio fue realizado como proyecto universitario.
Integrantes:
 - Alexander Gutierrez
 - Carlos Chavarria
 - Joseph Leon (Joscalion04)
