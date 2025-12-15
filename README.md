# Tarjeta de Fidelidad Gamificada

Programa en CLI de Java que gestiona un sistema de fidelidad para una cadena de tiendas de conveniencia. Permite administrar clientes, registrar compras, acumular puntos y calcular niveles de fidelidad.

El proyecto fue desarrollado siguiendo principios de **TDD (Test Driven Development)**, incorporando pruebas unitarias con **JUnit 5** y medición de cobertura de código.

---

## 🧩 Funcionalidades

### Gestión de Clientes
- Crear clientes con:
  - id
  - nombre
  - correo (validado)
  - puntos (inicial 0)
  - nivel (Bronce)
  - streak de compras
- Listar clientes
- Actualizar cliente
- Eliminar cliente

### Registro de Compras
- Registrar compras con:
  - id de compra
  - id de cliente
  - monto
  - fecha
- Cálculo de puntos:
  - Cada $100 ⇒ 1 punto (redondeo hacia abajo)
  - Multiplicador por nivel:
    - Bronce ×1
    - Plata ×1.2
    - Oro ×1.5
    - Platino ×2
- **Bonus**:
  - 3 compras en el mismo día ⇒ +10 puntos
  - El bonus se reinicia cada día
- Histórico de compras:
  - Listar
  - Actualizar
  - Eliminar

### Niveles de Fidelidad
| Nivel     | Puntos Totales | Beneficio |
|----------|----------------|-----------|
| Bronce   | 0 – 499        | —         |
| Plata    | 500 – 1499     | +20%      |
| Oro      | 1500 – 2999    | +50%      |
| Platino | 3000 +         | +100%     |

El nivel del cliente se recalcula automáticamente tras cada compra.

---

## 🖥️ Interfaz
La aplicación se ejecuta completamente por **consola**, mediante un menú por texto:

- Gestión de clientes
- Gestión de compras
- Mostrar puntos y nivel de un cliente
- Salir

---

## 🏗️ Diseño y Arquitectura

El proyecto está organizado en capas:
> domain → Entidades y lógica de negocio
> 
> repository → Persistencia en memoria
> 
> service → Casos de uso y reglas de negocio
> 
> ui → Menú por consola (CLI)

- **Persistencia:** en memoria (repositorios `InMemory`)
- **Separación de responsabilidades:** cada capa cumple un rol claro
- **UI desacoplada** de la lógica de negocio

#### Diagrama UML:

<img width="467" height="241" alt="UML drawio" src="https://github.com/user-attachments/assets/e128e5d1-183a-4ea8-925d-9ab1446a40d0" />

---

## 🧪 Pruebas Unitarias

- Framework: **JUnit 5**
- Se implementaron pruebas para:
  - Creación y validación de clientes
  - Reglas de negocio de compras
  - Cálculo de puntos y bonus
  - Cálculo de niveles
  - Operaciones CRUD
  - Casos de error (cliente inexistente, correo inválido, monto inválido, etc.)

Las pruebas cubren tanto casos factibles como casos infactibles (de error), siguiendo una aproximación de **TDD**.

## Evidencia de TDD (Red → Green → Refactor)

Durante el desarrollo se aplicó un flujo **TDD**, implementando primero pruebas unitarias (JUnit 5), observando fallos controlados (“Red”), luego agregando la implementación mínima para hacerlas pasar (“Green”), y finalmente refinando el diseño.

### 1) Red: primer ciclo (fallos iniciales)
En la siguiente evidencia se observa un ciclo “Red” en el que las pruebas no pueden ejecutarse correctamente debido a dependencias/clases aún no implementadas o inconsistencias iniciales del diseño.

![TDD Red - compilación y dependencias](tarjetagamificada/docs/img/tdd-red-compilacion.png)

*Figura 1 — Estado “Red”: pruebas fallando por implementación incompleta / errores de compilación.*

### 2) Green: creación de cliente y estado inicial correcto
Luego de implementar el caso de uso mínimo, el test pasa y se valida la creación del cliente con valores iniciales esperados (puntos = 0, nivel Bronce, etc.).

![TDD Green - creación de cliente](tarjetagamificada/docs/img/tdd-green-cliente.png)

*Figura 2 — Estado “Green”: pruebas pasando para creación y validaciones básicas del cliente.*

---

## Reglas de niveles y multiplicadores

El nivel del cliente se determina por umbrales de puntos y define un multiplicador para el cálculo de puntos.

![Enum Nivel con multiplicador y umbrales](tarjetagamificada/docs/img/nivel-enum.png)

*Figura 3 — Implementación de `Nivel`: multiplicadores y cálculo por umbrales.*

### 3) Red/Green: corrección de cálculo de nivel
Se agregó un test que exige que el nivel cambie según puntos acumulados. Primero falla (“Red”) y luego se corrige la implementación (“Green”).

![TDD Red - nivel incorrecto](tarjetagamificada/docs/img/tdd-red-nivel.png)

*Figura 4 — Estado “Red”: el test detecta un nivel incorrecto (ej. esperaba PLATA y obtuvo BRONCE).*

![TDD Green - nivel corregido](tarjetagamificada/docs/img/tdd-green-nivel.png)

*Figura 5 — Estado “Green”: el cálculo de nivel queda consistente con los umbrales.*

---

## Bonus por compras en un mismo día

Regla implementada: al registrar **3 compras el mismo día**, se otorga un **bonus adicional** (reiniciado por día). Esta lógica se validó mediante pruebas unitarias.

![Test de bonus por 3 compras](tarjetagamificada/docs/img/test-bonus.png)

*Figura 6 — Test que valida bonus al completar 3 compras en el mismo día.*

![TDD Green - bonus y reinicio diario](tarjetagamificada/docs/img/tdd-green-bonus.png)

*Figura 7 — Estado “Green”: pruebas pasando para bonus y reinicio por día.*

---

## Tests de validación / errores

Se incluyen pruebas para casos inválidos (por ejemplo, cliente inexistente, monto inválido, etc.). La evidencia muestra el estado “Red” previo a implementar las validaciones faltantes.

![TDD Red - validaciones](tarjetagamificada/docs/img/tdd-red-validaciones.png)

*Figura 8 — Estado “Red”: pruebas de validación detectan reglas aún no implementadas (luego corregidas en el desarrollo).*

### Ejecución de tests
## 📊 Cobertura de Código

Herramienta utilizada: EclEmma (JaCoCo)

Tipo de cobertura medida:
  - Cobertura de líneas
  - Cobertura de ramas
  - Criterio de cobertura

La medición se centró en el código productivo relevante, específicamente en los módulos (o paquetes):
  - domain
  - repository
  - service

* La capa ui (menú por consola) no se cubre con pruebas unitarias, ya que corresponde a lógica de entrada/salida (IO), la cual se valida manualmente.\*
* Este criterio permite una cobertura alta y representativa de la lógica de negocio, evitando tests artificiales sobre código trivial o de interfaz.\*

## 🚀 Compilación y Ejecución

### Compilar el proyecto
```
mvn clean compile
```

### Ejecutar la aplicación
```
en tarjetagamificada/tarjetagamificada/src/main/java usar
javac Main.java
Finalmente java Main
```

## 📂 Estructura del Repositorio
```
src/main/java
 ├── domain
 ├── repository
 ├── service
 └── ui

src/test/java
 ├── domain
 ├── repository
 └── service
```

## 📊 Evidencia de Cobertura por Suite de Tests

La cobertura de código fue analizada utilizando **EclEmma (JaCoCo)** ejecutando **cada suite de pruebas de forma independiente**.  
Este enfoque permite evaluar correctamente la cobertura de cada capa del sistema según su responsabilidad, evitando mezclar resultados no representativos.

A continuación se presentan las evidencias de cobertura obtenidas.

---

### FidelidadServiceTest
![Cobertura ClienteRepositoryTest](tarjetagamificada/docs/img/Imagen10.png)

Esta suite de pruebas cubre principalmente la **capa `service`**, donde se implementan las reglas de negocio principales:
- cálculo de puntos
- aplicación de multiplicadores por nivel
- bonus por compras
- validaciones de reglas

El porcentaje global mostrado es menor porque, en esta ejecución, no se instrumentan pruebas sobre otras capas (domain, repository, ui).  
La cobertura del paquete `service` es alta y consistente con su rol central en la lógica del sistema.

---

### ClienteRepositoryTest
![Cobertura CompraRepositoryTest](tarjetagamificada/docs/img/Imagen11.png)

Esta prueba valida el comportamiento del **repositorio de clientes en memoria**, incluyendo:
- creación
- búsqueda
- listado
- eliminación

La cobertura se concentra exclusivamente en la capa `repository`, lo cual explica el bajo porcentaje global observado en esta ejecución específica.

---

### CompraRepositoryTest
![Cobertura ClienteTest](tarjetagamificada/docs/img/Imagen12.png)

Esta suite cubre el **repositorio de compras**, verificando:
- almacenamiento de compras
- filtrado por cliente
- actualización
- eliminación

Al igual que en los casos anteriores, la cobertura global refleja únicamente las clases ejecutadas durante esta suite de pruebas.

---

### ClienteTest (Domain)
![Cobertura FidelidadServiceTest](tarjetagamificada/docs/img/Imagen9.png)

Este test se enfoca en la **entidad de dominio `Cliente`**, validando:
- creación correcta del objeto
- valores iniciales (puntos, nivel, streak)
- validación de correo
- reglas básicas del dominio

La cobertura del paquete `domain` es alta, lo cual demuestra que las entidades fueron probadas de forma aislada y correcta.

---

## 🧠 Interpretación de la Cobertura

El porcentaje de cobertura global varía dependiendo de la suite de pruebas ejecutada, ya que cada una instrumenta únicamente la capa que le corresponde.

Para una evaluación adecuada, la cobertura debe analizarse **por paquete**, destacando que:

- `domain`: alta cobertura
- `repository`: alta cobertura
- `service`: alta cobertura
- `ui`: no cubierta por pruebas unitarias

La capa `ui` corresponde a lógica de entrada/salida por consola, la cual se valida manualmente y no forma parte del núcleo de reglas de negocio.

---

## ❓ ¿Qué tipo de cobertura se midió y por qué?

Se midió **cobertura de líneas e instrucciones** utilizando **EclEmma (JaCoCo)**.

La medición se centró en el **código productivo relevante** (`domain`, `repository`, `service`), ya que estas capas contienen la lógica de negocio del sistema.

No se forzó cobertura sobre la interfaz por consola (`ui`) ni sobre código trivial, con el objetivo de mantener pruebas significativas y alineadas con buenas prácticas de testing.

## ⚖️ Declaración de Uso de Herramientas de IA (Uso Ético)

Durante el desarrollo de este proyecto se utilizó **ChatGPT (OpenAI)** como herramienta de apoyo, principalmente para:

- Apoyo en la **redacción y estructuración del archivo README**.
- Orientación en la **organización de evidencias** (imágenes de cobertura y pruebas).
- Asistencia en la **implementación de la interfaz por consola (UI)** para acelerar el desarrollo del menú y flujos de interacción.
- Creación de pom.xml

El **diseño del sistema**, la **implementación de la lógica de negocio**, la **definición de reglas**, la **escritura de pruebas unitarias**, la **interpretación de resultados** y la **resolución de errores** fueron realizadas y validadas por el autor del proyecto.
