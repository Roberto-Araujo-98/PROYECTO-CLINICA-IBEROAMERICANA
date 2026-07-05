# PROYECTO-CLINICA-IBEROAMERICANA
# 🏥 Sistema de Gestión Clínica - Clinica Iberoamericana

[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-En%20Desarrollo%20/%20Pr%C3%A1ctica-yellow.svg)]()

Este proyecto consiste en un prototipo funcional para un **Sistema de Gestión Clínica** desarrollado en **Java**. El sistema implementa un entorno de consola robusto con autenticación basada en roles (*Administrador* y *Médico*), control de datos maestros de pacientes y un flujo CRUD (*Create, Read, Update, Delete*) para el registro y seguimiento de episodios clínicos. La persistencia se maneja de forma local mediante archivos planos en formato **CSV**.

---

## 🛠️ Estructura del Proyecto

El desarrollo sigue un esquema modularizado nativo de Java. A continuación se detalla la distribución de directorios y archivos principales dentro del repositorio:

```text
Clinica_Iberoamericana/
├── .settings/
├── bin/                        # Archivos binarios compilados (.class)
│   ├── com/
│   └── module-info.class
├── src/                        # Código fuente del sistema
│   └── com\clinica\data\
│       ├── ControladorData.java # Orquestador principal y lógica del Menú UI
│       ├── FichaBasePaciente.java # Entidad y modelo de datos del Paciente
│       ├── GestorCSV.java       # Componente de persistencia y lectura/escritura E/S
│       └── HistorialClinico.java # Entidad y modelo de un Episodio Clínico
├── .classpath
├── .project
├── Episodios_Clinicos.csv      # Base de datos local: Historiales médicos
└── FichaBase_Pacientes.csv     # Base de datos local: Fichas de pacientes
🚀 Características Principales
🔐 1. Autenticación y Control de Accesos por Rol
El sistema restringe el acceso mediante contraseña y asigna vistas operativas diferenciadas:

Rol Administrador (admin123): Vista de solo lectura enfocada en la auditoría general de registros almacenados.

Rol Médico (doc456): Modo operativo completo con facultades transaccionales (CRUD) sobre pacientes e historias clínicas.

📋 2. Módulos Operativos (Menú interactivo)
Gestión de Pacientes: * Registro exhaustivo de datos demográficos y biométricos (18 campos que incluyen: ID autogenerado, antecedentes crónicos, alergias, seguros, etc.).

Búsqueda, edición selectiva de campos en memoria y eliminación lógica/física.

Seguimiento de Episodios Clínicos:

Registro de constantes vitales (temperatura, frecuencia cardíaca, presión arterial).

Vinculación relacional directa verificando la existencia previa del idPaciente.

📂 Formato de Almacenamiento (CSV)
La estructura de las cabeceras de persistencia se modela de la siguiente manera:

Pacientes (FichaBase_Pacientes.csv):

Fragmento de código
idPaciente,nombreCompleto,edad,peso,sexo,razaEtnia,ocupacion,estadoCivil,ciudadOrigen,residenciaHabitual,residenciaOcasional,telefono,contactoEmergencia,seguro,grupoSanguineo,alergias,antecedentesCronicos,medicamentosActuales
Historias Clínicas (Episodios_Clinicos.csv):

Fragmento de código
idHistorial,idPaciente,sintomaPrincipal,temperatura,frecuenciaCardiaca,presionArterial,diagnosticoPrincipal,medicoTratante,fechaIngreso,estadoAlta,notasEvolucion
🚧 Estado del Proyecto e Incidencias Conocidas
⚠️ Nota de Transparencia Técnica / Práctica Académica

Este software fue desarrollado como un proyecto práctico de aprendizaje y entrenamiento de fundamentos de arquitectura de software y manejo de E/S de archivos en Java. Durante las pruebas del entorno se identificó la siguiente limitación técnica:

Incidencia en persistencia selectiva: Tras realizar la edición o actualización en memoria de un registro de paciente existente a través del Menú de Edición del Médico, se han detectado inconsistencias puntuales al serializar los cambios de vuelta sobre la estructura física del archivo FichaBase_Pacientes.csv.

Plan de Mitigación Futuro: Se plantea reestructurar el componente GestorCSV.java en una fase posterior de desarrollo para implementar un volcado de búfer (flush) más eficiente o migrar la lógica relacional directamente hacia una base de datos SQL (como Oracle SQL o MySQL) por medio de JDBC para asegurar la atomicidad de las escrituras.

💻 Ejecución del Sistema
Para compilar y arrancar el módulo principal de la aplicación desde la consola de comandos, asegúrate de utilizar un JDK compatible (versión 21 o superior) y ejecutar desde la raíz del proyecto:

Bash
# Comando de ejecución modularizado
java --module-path bin -m Clinica_Iberoamericana/com.clinica.data.ControladorData
Desarrollado con fines educativos y de práctica en Ingeniería de Software