# Post-Contenido 2 — Pruebas E2E con Selenium, Postman y Newman

**Programación Web — Unidad 10: Pruebas de Software en Aplicaciones Web**  
Ingeniería de Sistemas — UDES 2026

---

## Descripción

Implementación de pruebas de extremo a extremo (E2E) sobre la aplicación Spring Boot de gestión de tareas del Post-Contenido 1, aplicando el patrón Page Object Model con Selenium WebDriver, una colección de pruebas de API REST en Postman con test scripts, y automatización mediante Newman integrado en un pipeline de GitHub Actions.

---

## Requisitos previos

- Java 17+
- Maven 3.9.x (o usar el wrapper `mvnw` incluido)
- Google Chrome (versión estable)
- Node.js 18+ con npm
- Newman 6.x (`npm install -g newman`)
- Postman Desktop v10+ (para editar la colección)

---

## Estructura del Proyecto

```
apellido-post2-u10/
├── .github/
│   └── workflows/
│       └── api-tests.yml
├── postman/
│   ├── ColeccionToDo.json
│   ├── env-local.json
│   └── env-ci.json
├── src/
│   ├── main/java/com/tareas/
│   │   ├── controller/
│   │   │   ├── TareaController.java
│   │   │   └── TareaViewController.java
│   │   ├── entity/
│   │   │   └── Tarea.java
│   │   ├── exception/
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── repository/
│   │   │   └── TareaRepository.java
│   │   └── service/
│   │       └── TareaService.java
│   └── test/java/com/tareas/
│       ├── controller/
│       │   └── TareaControllerTest.java
│       ├── e2e/
│       │   ├── NuevaTareaPage.java
│       │   ├── TareasPage.java
│       │   └── TareasE2ETest.java
│       ├── repository/
│       │   └── TareaRepositoryTest.java
│       └── service/
│           └── TareaServiceTest.java
└── pom.xml
```

---

## Checkpoint 1 — Pruebas E2E con Selenium (Page Object Model)

### Clases implementadas

**`TareasPage.java`** — encapsula los selectores y acciones de la página principal:
- `contarTareas()` — cuenta los elementos con clase `.tarea-item`
- `obtenerTituloPagina()` — retorna el título del navegador
- `obtenerEncabezado()` — retorna el texto del `<h1>`
- `botonNuevaVisible()` — verifica que el botón Nueva Tarea esté visible

**`NuevaTareaPage.java`** — encapsula los selectores del formulario de nueva tarea.

**`TareasE2ETest.java`** — tests ejecutados en modo headless:
- `paginaTareas_cargaCorrectamente` — verifica que el título contiene "Tareas"
- `paginaTareas_botonNuevaVisible` — verifica que el botón Nueva Tarea es visible

### Cómo ejecutar las pruebas Selenium

Asegúrate de tener Google Chrome instalado y la aplicación corriendo en `localhost:8081`.

```bash
# Ejecutar solo los tests E2E
mvn test -Dtest=TareasE2ETest

# Ejecutar todos los tests
mvn test
```

> Los tests corren en modo **headless** (sin ventana de navegador). No se necesita configuración adicional de ChromeDriver gracias a WebDriverManager.

---

## Checkpoint 2 — Colección Postman con Test Scripts

### Entorno

Se usa el entorno **"ToDoApp-Local"** con la variable `baseUrl = http://localhost:8081`.

### Los 5 requests de la colección

| # | Método | Endpoint | Qué verifica |
|---|--------|----------|--------------|
| 1 | POST | `/api/tareas` | Status 201, respuesta tiene `id`, guarda `tareaId` |
| 2 | GET | `/api/tareas/{{tareaId}}` | Status 200, título correcto |
| 3 | PATCH | `/api/tareas/{{tareaId}}/completar` | Status 200, `completada` es `true` |
| 4 | GET | `/api/tareas/{{tareaId}}` | Status 200, verifica `completada: true` |
| 5 | GET | `/api/tareas/99999` | Status 404 Not Found |

### Cómo ejecutar la colección localmente con Newman

```bash
# Instalar Newman (solo la primera vez)
npm install -g newman

# Ejecutar con entorno local
newman run postman/ColeccionToDo.json --environment postman/env-local.json
```

---

## Checkpoint 3 — Newman en GitHub Actions

### Cómo funciona el pipeline

El workflow `.github/workflows/api-tests.yml` se ejecuta automáticamente en cada `push` o `pull_request` y realiza estos pasos:

1. Checkout del código
2. Configura Java 17
3. Compila el proyecto con Maven (`-DskipTests`)
4. Inicia la aplicación en segundo plano
5. Espera que la app responda en el puerto 8081
6. Instala Newman
7. Ejecuta la colección con el entorno CI (`env-ci.json`)

### Cómo ver los resultados

Ve a la pestaña **Actions** del repositorio en GitHub. Cada ejecución muestra el resultado de los tests de Newman en el paso "Ejecutar colección con Newman".

---

## Evidencias

| Evidencia | Descripción |
|-----------|-------------|
| `evidencia/selenium-tests-verde.png` | 2 tests de Selenium en verde |
<img width="1920" height="1032" alt="1" src="https://github.com/user-attachments/assets/a85b9734-9e35-42c7-b9ec-57acc94e33aa" />

| `evidencia/postman-runner-0-failures.png` | Postman Runner con 0 failures |
<img width="1920" height="1032" alt="PostmanGET" src="https://github.com/user-attachments/assets/935ab82c-0659-4f07-adc0-0228eae300c1" />
<img width="1920" height="1032" alt="PostmanPUT" src="https://github.com/user-attachments/assets/95adbd02-04c5-4461-ab75-96183e0cecd5" />
<img width="1920" height="1032" alt="PostmanPOST" src="https://github.com/user-attachments/assets/ba9b98fb-4e71-479a-8ae9-3010a84361f6" />

| `evidencia/github-actions-verde.png` | GitHub Actions con check verde |



---

## Tecnologías usadas

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Spring Boot | 3.4.5 | Framework base |
| Selenium Java | 4.18.1 | Pruebas E2E de interfaz web |
| WebDriverManager | 5.8.0 | Gestión automática de ChromeDriver |
| Postman | v10+ | Diseño de colección de pruebas API |
| Newman | 6.x | Ejecución de colección desde terminal/CI |
| GitHub Actions | — | Pipeline de integración continua |
