🐾 Trabajo Final Integrador – Programación II (UTN a Distancia)
Gestión de Mascotas y Microchips — Java + JDBC + MySQL + DAO + Transacciones

📌 Integrantes: Santiago Báez | Macarena Marinoni | Marianela Valletto

---

📝 1. Dominio elegido y justificación
El sistema modela una veterinaria donde se gestionan mascotas y los microchips que se implantan en ellas.
Presenta una relación clara y didáctica 1 → 1, ideal para aplicar:
- CRUD completo
- Relaciones entre entidades
- Patrón DAO con JDBC
- Arquitectura por capas
- Operaciones transaccionales
- Baja lógica
- Validaciones y reglas de negocio

Motivos del dominio
- Una mascota puede tener a lo sumo un microchip.
- Un microchip depende de la existencia de la mascota (integridad referencial).
- Se implementa baja lógica mediante campo eliminado.

---

💻 2. Requisitos del entorno
- JDK: 17+ (desarrollado con JDK 24)
- IDE recomendado: Apache NetBeans
- Base de Datos: MySQL 8.x
- Conector: MySQL JDBC Driver
- Archivo de configuración: config.properties

Ejemplo:
- db.url=jdbc:mysql://localhost:3306/vetdb?useSSL=false&serverTimezone=UTC
- db.user=root
- db.password=1234

---

🗂️ 3. Scripts SQL y creación de la base

Dentro de la carpeta /SQL/ se incluyen:

01_create_database.sql
- Crea la base de datos
- Tabla mascota
- Tabla microchip
- Relación 1 → 1 mediante FK
- Campos con baja lógica

02_insert_data.sql
- Carga datos de prueba listos para usar

▶️ Pasos para crear la base
1. Abrir MySQL Workbench (o cliente preferido).
2. Ejecutar 01_create_database.sql.
3. Ejecutar 02_insert_data.sql.
4. Verificar que existan mascota y microchip.
5. Configurar config.properties con tus credenciales.
6. Ejecutar el proyecto desde NetBeans.

---

🧱 4. Arquitectura del proyecto
La aplicación está organizada en capas siguiendo buenas prácticas:

📦 prog2int.config
- DatabaseConnection
  Centraliza el acceso a la base mediante config.properties.

📦 prog2int.entities
- BaseEntity → clase base (id, eliminado)
- Mascota → entidad principal (A)
- Microchip → entidad secundaria (B) con relación 1 → 1

📦 prog2int.dao
- GenericDao<T> → CRUD genérico + baja lógica
- MascotaDao, MicrochipDao → interfaces específicas
- MascotaDaoJdbc, MicrochipDaoJdbc → implementación JDBC:
  - SQL parametrizado
  - PreparedStatement & ResultSet
  - Manejo de errores
  - Conversión tabla ↔ entidad

📦 prog2int.service
- Lógica de negocio y validaciones
- Coordinación entre DAOs
- Manejo de excepciones

✔ Incluye operación transaccional completa:
  Registrar Mascota + Microchip en un solo bloque con:

📦 prog2int.main
- AppMenu
  - Menú principal
  - Submenús
  - Búsquedas avanzadas
- Punto de entrada de la aplicación

---

🎮 5. Flujo de uso de la aplicación (AppMenu)
Menú principal
1️⃣ Gestionar Mascotas
2️⃣ Gestionar Microchips
3️⃣ Búsqueda avanzada
0️⃣ Salir

Mascotas – Funcionalidades
- Alta sin microchip
- Alta con microchip (transacción)
- Listado
- Actualización
- Baja lógica
- Validaciones de entrada

Microchips – Funcionalidades
- Listado
- Lectura por ID
- Actualización
- Baja lógica
- Búsqueda por código
- Búsquedas avanzadas
- Buscar por nombre
- Buscar por dueño
- Buscar por código de microchip

📌 Todas las entradas relevantes se normalizan a mayúsculas.

---

📐 6. UML del proyecto
La carpeta /UML/ contiene versiones PNG del diagrama con:
- Entidades
- Relación 1 → 1
- Herencia
- DAOs y Services
- Flujo básico de interacción

---

📄 7. Informe (PDF)
Dentro del repositorio se incluye la carpeta /informe/ con el informe final, que contiene:
- Justificación del dominio
- Base de datos + SQL
- UML completo
- Arquitectura por capas
- Explicación detallada de CRUD y transacciones

---

🚀 8. Cómo compilar y ejecutar
1. Clonar el repositorio >> git clone https://github.com/MaruValletto/TFI_Prog2_MascotaMicrochip.git
2. Abrir el proyecto en NetBeans. Se encuentra en: TFI_Prog2_MascotaMicrochip
3. Configurar el archivo config.properties: Con la URL, usuario y contraseña de tu base.
4. Ejecutar scripts SQL >> En la carpeta /sql/ correr: 01_create_database.sql | 02_insert_data.sql
5. Ejecutar la aplicación: En NetBeans → clic derecho → Run
   O ejecutar: prog2int.main.AppMenu
6. Probar funciones
- Alta de mascota
- Alta con microchip (transacción)
- CRUD de microchips
- Búsquedas
- Baja lógica
- Validaciones

---

🎥 9. Video de presentación
📌 Enlace al video (cuando esté listo):
👉 [Ver video de presentación](URL_DEL_VIDEO)
