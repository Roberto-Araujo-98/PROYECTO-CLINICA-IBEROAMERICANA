package com.clinica.data;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

/**
 * Clase GestorCSV: Encargada de la persistencia de datos y la lógica CRUD.
 * Maneja la lectura y escritura de los archivos CSV y la conversión de
 * objetos Modelo (Paciente/Historial) a filas de texto y viceversa.
 */
public class GestorCSV {
	// 1. CONSTANTES Y ATRIBUTOS
private static final String PACIENTES_FILE = "FichaBase_Pacientes.csv";
private static final String HISTORIALES_FILE = "Episodios_Clinicos.csv";
private static final String CSV_DELIMITER = ",";
   //El Scanner debe ser un atributo de instancia (final) para ser reutilizado.
private final Scanner sc = new Scanner(System.in);
  //Formateador para registrar fechas de ingreso de forma consistente (YYYY-MM-DD HH:MM).
private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  //En GestorCSV.java (Método auxiliar)
private boolean confirmarCambio() {
 System.out.print("¿Desea cambiar este valor? (s/n): ");
 String respuesta = this.sc.nextLine().trim().toLowerCase(); // Usando this.sc
 
 while (!respuesta.equals("s") && !respuesta.equals("n")) {
     System.out.println("❌ Respuesta no válida. Use 's' para Sí o 'n' para No.");
     System.out.print("¿Desea cambiar este valor? (s/n): ");
     respuesta = this.sc.nextLine().trim().toLowerCase(); // Usando this.sc
 }
 return respuesta.equals("s");
}
public GestorCSV() {
	// ----------------------------------------------------
    // 2. CONSTRUCTOR
    // ----------------------------------------------------
    asegurarArchivoExiste(PACIENTES_FILE, FichaBasePaciente.getCSVHeader());
    asegurarArchivoExiste(HISTORIALES_FILE, HistorialClinico.getCSVHeader());
}

   // =================================================================
   // 3. MÉTODOS PÚBLICOS (CRUD)
   // =================================================================

   // --- CREATE ---
   /**
   * Registra un nuevo objeto FichaBasePaciente en el archivo CSV.
   * Llama al método toCSVRow() del objeto para obtener el formato de línea.
   */
public void registrarPaciente(FichaBasePaciente p) {
	// Generación de IDs automáticos para el historial y la fecha (ID Historial y Fecha Ingreso).
escribirFila(PACIENTES_FILE, p.toCSVRow());
System.out.println("✅ Paciente registrado en: " + PACIENTES_FILE);
}

public void registrarHistorial(HistorialClinico h) {
h.setIdHistorial("H" + System.currentTimeMillis()); 
h.setFechaIngreso(LocalDateTime.now().format(formatter)); 
escribirFila(HISTORIALES_FILE, h.toCSVRow());
System.out.println("✅ Historial registrado en: " + HISTORIALES_FILE);
}

    // --- READ (Visualizar) ---
public List<FichaBasePaciente> verPacientes() {
return leerYMostrarTodos(PACIENTES_FILE, "Pacientes");
}

public List<HistorialClinico> verHistoriales() {
return leerYMostrarTodos(HISTORIALES_FILE, "Historiales Clínicos");
}

   // --- READ (Búsqueda específica) ---
   /**
   * Busca un paciente en la lista cargada en memoria.
   * @param id ID del paciente a buscar.
   * @return El objeto FichaBasePaciente si se encuentra, o null si no existe.
   */
public FichaBasePaciente buscarPacientePorId(String id) {
List<FichaBasePaciente> pacientes = cargarTodosPacientes();
for (FichaBasePaciente p : pacientes) {
if (p.getIdPaciente().equalsIgnoreCase(id)) {
return p;
}
}
return null; // No encontrado
}

   // --- UPDATE (Edición) ---
   /**
   * Busca un paciente por ID, solicita los datos actualizados y reescribe el archivo.
   */
public void editarPaciente(String id) { 
List<FichaBasePaciente> pacientes = cargarTodosPacientes(); 
FichaBasePaciente pacienteAEditar = null;

for (FichaBasePaciente p : pacientes) {
if (p.getIdPaciente().equalsIgnoreCase(id)) {
pacienteAEditar = p;
break;
}
}

if (pacienteAEditar != null) {
System.out.println("✅ Paciente ID " + id + " encontrado.");
solicitarDatosActualizadosPaciente(pacienteAEditar); 
reescribirArchivo(PACIENTES_FILE, FichaBasePaciente.getCSVHeader(), pacientes);
System.out.println("\n💾 Edición completa. Paciente ID " + id + " actualizado.");
} else {
System.out.println("❌ Error: No se encontró el paciente con ID: " + id);
}
}

//En GestorCSV.java (Método principal)

public void editarHistorial(String id) {
 
 // 1. Cargar todos los historiales y buscar el índice
 List<HistorialClinico> historiales = cargarTodosHistoriales();
 HistorialClinico historialAEditar = null;
 int indice = -1;

 for (int i = 0; i < historiales.size(); i++) {
     if (historiales.get(i).getIdHistorial().equalsIgnoreCase(id)) {
         historialAEditar = historiales.get(i);
         indice = i;
         break;
     }
 }

 // 2. Verificar si se encontró
 if (historialAEditar == null) {
     System.out.println("❌ Error: No se encontró el Historial Clínico con ID: " + id);
     return;
 }

 System.out.println("\n--- ✍️ EDITANDO Historial ID: " + historialAEditar.getIdHistorial() + " ---");
 
 // --- EDICIÓN CAMPO POR CAMPO (11 Variables del Historial Clínico) ---
 
 // 1. Síntoma Principal (String)
 System.out.println("\nValor actual [Síntoma Principal]: " + historialAEditar.getSintomaPrincipal());
 if (confirmarCambio()) { // Llamada limpia
     System.out.print("Nuevo Síntoma Principal: ");
     String nuevoSintoma = this.sc.nextLine();
     if (!nuevoSintoma.trim().isEmpty()) {
         historialAEditar.setSintomaPrincipal(nuevoSintoma);
     }
 }
 
 // 2. Temperatura (double)
 System.out.println("\nValor actual [Temperatura (°C)]: " + historialAEditar.getTemperatura());
 if (confirmarCambio()) {
     System.out.print("Nueva Temperatura (ej: 36.8): ");
     String tempInput = this.sc.nextLine();
     if (!tempInput.trim().isEmpty()) {
         try {
             double nuevaTemperatura = Double.parseDouble(tempInput);
             historialAEditar.setTemperatura(nuevaTemperatura);
         } catch (NumberFormatException e) {
             System.out.println("❌ ADVERTENCIA: La temperatura no es un número válido. Campo no modificado.");
         }
     }
 }
 
 // 3. Frecuencia Cardiaca (int)
 System.out.println("\nValor actual [Frecuencia Cardiaca]: " + historialAEditar.getFrecuenciaCardiaca());
 if (confirmarCambio()) {
     System.out.print("Nueva Frecuencia Cardiaca (ej: 75): ");
     String fcInput = this.sc.nextLine();
     if (!fcInput.trim().isEmpty()) {
         try {
             int nuevaFC = Integer.parseInt(fcInput);
             historialAEditar.setFrecuenciaCardiaca(nuevaFC);
         } catch (NumberFormatException e) {
             System.out.println("❌ ADVERTENCIA: La frecuencia no es un número entero válido. Campo no modificado.");
         }
     }
 }
 
 // 4. Presión Arterial (String)
 System.out.println("\nValor actual [Presión Arterial]: " + historialAEditar.getPresionArterial());
 if (confirmarCambio()) {
     System.out.print("Nueva Presión Arterial: ");
     String nuevaPA = this.sc.nextLine();
     if (!nuevaPA.trim().isEmpty()) {
         historialAEditar.setPresionArterial(nuevaPA);
     }
 }

 // 5. Diagnóstico Principal (String)
 System.out.println("\nValor actual [Diagnóstico Principal]: " + historialAEditar.getDiagnosticoPrincipal());
 if (confirmarCambio()) {
     System.out.print("Nuevo Diagnóstico Principal: ");
     String nuevoDiagnostico = this.sc.nextLine();
     if (!nuevoDiagnostico.trim().isEmpty()) {
         historialAEditar.setDiagnosticoPrincipal(nuevoDiagnostico);
     }
 }
 
 // 6. Médico Tratante (String)
 System.out.println("\nValor actual [Médico Tratante]: " + historialAEditar.getMedicoTratante());
 if (confirmarCambio()) {
     System.out.print("Nuevo Médico Tratante: ");
     String nuevoMedico = this.sc.nextLine();
     if (!nuevoMedico.trim().isEmpty()) {
         historialAEditar.setMedicoTratante(nuevoMedico);
     }
 }
 
 // 7. Estado de Alta (String)
 System.out.println("\nValor actual [Estado de Alta]: " + historialAEditar.getEstadoAlta());
 if (confirmarCambio()) {
     System.out.print("Nuevo Estado de Alta (ej: 'Activo', 'Dado de Alta'): ");
     String nuevoEstadoAlta = this.sc.nextLine();
     if (!nuevoEstadoAlta.trim().isEmpty()) {
         historialAEditar.setEstadoAlta(nuevoEstadoAlta);
     }
 }
 
 // 8. Notas de Evolución (String)
 System.out.println("\nValor actual [Notas de Evolución]: " + historialAEditar.getNotasEvolucion());
 if (confirmarCambio()) {
     System.out.print("Nuevas Notas de Evolución: ");
     String nuevasNotasEvolucion = this.sc.nextLine();
     if (!nuevasNotasEvolucion.trim().isEmpty()) {
         historialAEditar.setNotasEvolucion(nuevasNotasEvolucion);
     }
 }
 
 // 3. Actualizar la lista y reescribir el archivo
 historiales.set(indice, historialAEditar);
 reescribirArchivo(HISTORIALES_FILE, HistorialClinico.getCSVHeader(), historiales);
 
 System.out.println("\n✅ Historial Clínico ID " + id + " actualizado y guardado correctamente.");
}
// --- DELETE (Eliminación) ---
public void eliminarPaciente(String id) {
List<FichaBasePaciente> pacientes = cargarTodosPacientes(); 

boolean removido = pacientes.removeIf(p -> p.getIdPaciente().equalsIgnoreCase(id));

if (removido) {
reescribirArchivo(PACIENTES_FILE, FichaBasePaciente.getCSVHeader(), pacientes);
System.out.println("🗑️ Paciente ID " + id + " eliminado correctamente del CSV.");
} else {
System.out.println("❌ Error: No se encontró el paciente con ID: " + id);
}
}

public void eliminarHistorial(String id) {
List<HistorialClinico> historiales = cargarTodosHistoriales(); 

boolean removido = historiales.removeIf(h -> h.getIdHistorial().equalsIgnoreCase(id));

if (removido) {
reescribirArchivo(HISTORIALES_FILE, HistorialClinico.getCSVHeader(), historiales);
System.out.println("🗑️ Historial ID " + id + " eliminado correctamente del CSV.");
} else {
System.out.println("❌ Error: No se encontró el historial con ID: " + id);
}
}

// =================================================================
// 4. MÉTODOS PÚBLICOS (UTILIDAD: SOLICITUD DE DATOS)
// =================================================================

public FichaBasePaciente solicitarDatosPaciente() {
System.out.println("\n--- Recolección de Datos del Paciente ---");

// Generación de ID Automático para evitar duplicación
String idPaciente = generarIdUnico(); 
System.out.println("ID Paciente generado: " + idPaciente);

System.out.print("Nombre completo: ");
String nombreCompleto = this.sc.nextLine();

//--- Bloque de EDAD ---
int edad = 0;
while (true) {
 System.out.print("Edad: ");
 String input = this.sc.nextLine(); // LEER COMO STRING
 try {
     edad = Integer.parseInt(input); // PARSEAR A INT
     if (edad <= 0) {
          System.out.println("❌ Error: La edad debe ser un valor positivo.");
          continue;
     }
     break;
 } catch (NumberFormatException e) {
     System.out.println("❌ Error: Ingresa un número entero válido para la edad.");
 }
}
//--- FIN Bloque de EDAD ---
//--- Bloque de PESO ---
double peso = 0.0;
while (true) {
 System.out.print("Peso (kg) (ej: 75.5): ");
 String input = this.sc.nextLine(); // LEER COMO STRING
 try {
     peso = Double.parseDouble(input); // PARSEAR A DOUBLE
     if (peso <= 0.0) {
         System.out.println("❌ Error: El peso debe ser un valor positivo.");
         continue;
     }
     break;
 } catch (NumberFormatException e) {
     System.out.println("❌ Error: Ingresa un número decimal o entero válido para el peso.");
 }
} 
//--- FIN Bloque de PESO ---
System.out.print("Sexo: ");
String sexo = this.sc.nextLine();

System.out.print("Raza / Etnia: ");
String razaEtnia = this.sc.nextLine();

System.out.print("Ocupación: ");
String ocupacion = this.sc.nextLine();

System.out.print("Estado civil: ");
String estadoCivil = this.sc.nextLine();

System.out.print("Ciudad de origen: ");
String ciudadOrigen = this.sc.nextLine();

System.out.print("Residencia habitual: ");
String residenciaHabitual = this.sc.nextLine();

System.out.print("Residencia ocasional: ");
String residenciaOcasional = this.sc.nextLine();

System.out.print("Teléfono: ");
String telefono = this.sc.nextLine();

System.out.print("Contacto de emergencia: ");
String contactoEmergencia = this.sc.nextLine();

System.out.print("Seguro médico: ");
String seguro = this.sc.nextLine();

System.out.print("Grupo sanguíneo: ");
String grupoSanguineo = this.sc.nextLine();

System.out.print("Alergias: ");
String alergias = this.sc.nextLine();

System.out.print("Antecedentes crónicos: ");
String antecedentesCronicos = this.sc.nextLine();

System.out.print("Medicamentos actuales: ");
String medicamentosActuales = this.sc.nextLine();

// Retornamos el objeto con todos los datos
return new FichaBasePaciente(idPaciente, nombreCompleto, edad, peso, sexo, razaEtnia,
ocupacion, estadoCivil, ciudadOrigen, residenciaHabitual,
residenciaOcasional, telefono, contactoEmergencia, seguro,
grupoSanguineo, alergias, antecedentesCronicos, medicamentosActuales
);

}

public HistorialClinico solicitarDatosHistorial() {
System.out.println("\n--- Recolección de Datos de la Historia Clínica (11 Campos) ---");

String idPaciente;
// Validación del ID de Paciente (Integridad referencial)
while (true) {
System.out.print("ID del Paciente (a enlazar - debe existir): ");
idPaciente = this.sc.nextLine();
if (buscarPacientePorId(idPaciente) != null) {
break;
} else {
System.err.println("❌ ERROR: El ID de Paciente " + idPaciente + " no existe. Intente de nuevo.");
}
}

System.out.print("Síntoma Principal: ");
String sintoma = this.sc.nextLine();

double temperatura = 0.0;
int frecuenciaCardiaca = 0;
String presionArterial = "";
String diagnosticoPrincipal = "";
String medicoTratante = "";
String estadoAlta = "";
String notasEvolucion = "";

try {
// CAMPOS NUMÉRICOS
System.out.print("Temperatura (ej: 37.5): ");
temperatura = this.sc.nextDouble();

System.out.print("Frecuencia Cardiaca: ");
frecuenciaCardiaca = this.sc.nextInt();
sc.nextLine(); // Limpia buffer después de los números

// CAMPOS DE TEXTO
System.out.print("Presión Arterial (ej: 120/80): ");
presionArterial = this.sc.nextLine();

System.out.print("Diagnóstico Principal: ");
diagnosticoPrincipal = this.sc.nextLine();

System.out.print("Médico Tratante: ");
medicoTratante = this.sc.nextLine();

System.out.print("Estado de Alta (e.g., Activo, Dado de Alta): ");
estadoAlta = this.sc.nextLine();

System.out.print("Notas de Evolución: ");
notasEvolucion = this.sc.nextLine();

// Retorna el objeto con todos los datos
return new HistorialClinico(
"", // ID Historial (Generado después en registrarHistorial)
idPaciente,
sintoma,
temperatura,
frecuenciaCardiaca,
presionArterial,
diagnosticoPrincipal,
medicoTratante,
"", // Fecha Ingreso (Generado después en registrarHistorial)
estadoAlta,
notasEvolucion
);

} catch (InputMismatchException e) {
System.err.println("❌ Error: Por favor, ingrese un valor numérico válido para Temperatura/Frecuencia.");
sc.nextLine(); // Limpia buffer
return new HistorialClinico(); // Retorna un objeto vacío
}
}

// =================================================================
// 5. MÉTODOS PRIVADOS AUXILIARES (IMPLEMENTACIÓN DE PARSING Y ARCHIVOS)
// =================================================================

// --- Lógica de IDs ---
private String generarIdUnico() {
// Genera un ID basado en el timestamp, prefijado con 'P' (Paciente)
return "P" + System.currentTimeMillis();
}

/**
* Auxiliar para editar: permite al usuario modificar solo algunos campos de FichaBasePaciente.
*/
//En GestorCSV.java (Método privado)

private void solicitarDatosActualizadosPaciente(FichaBasePaciente paciente) {
 System.out.println("\n--- Editando Paciente ID: " + paciente.getIdPaciente() + " ---");
 System.out.println("Use 's' para cambiar el valor o 'n' para mantenerlo.");

 // 1. idPaciente (No editable, solo se muestra)
 System.out.println("ID Paciente: " + paciente.getIdPaciente() + " (No editable)");

 // 2. Nombre Completo (String)
 System.out.println("\nValor actual [Nombre Completo]: " + paciente.getNombreCompleto());
 if (confirmarCambio()) { // <-- Usando la lógica s/n
     System.out.print("Nuevo Nombre Completo: ");
     String nuevoNombre = this.sc.nextLine();
     if (!nuevoNombre.trim().isEmpty()) {
         paciente.setNombreCompleto(nuevoNombre);
     }
 }

 // 3. Edad (int)
 System.out.println("\nValor actual [Edad]: " + paciente.getEdad());
 if (confirmarCambio()) { // <-- Usando la lógica s/n
     System.out.print("Nueva Edad: ");
     String nuevaEdadStr = this.sc.nextLine();
     if (!nuevaEdadStr.trim().isEmpty()) {
         try {
             paciente.setEdad(Integer.parseInt(nuevaEdadStr));
         } catch (NumberFormatException e) {
             System.err.println("⚠️ Entrada inválida (debe ser número entero). Edad no modificada.");
         }
     }
 }

 // 4. PESO (double)
 System.out.println("\nValor actual [Peso]: " + paciente.getPeso());
 if (confirmarCambio()) { // <-- Usando la lógica s/n
     System.out.print("Nuevo Peso (kg) (ej: 75.5): ");
     String nuevoPesoStr = this.sc.nextLine();
     if (!nuevoPesoStr.trim().isEmpty()) {
         try {
             paciente.setPeso(Double.parseDouble(nuevoPesoStr)); 
         } catch (NumberFormatException e) {
             System.err.println("⚠️ Entrada inválida (debe ser número decimal/entero). Peso no modificado.");
         }
     }
 }
 
 // ... DEBES REPLICAR ESTE PATRÓN (get-confirmar-set) PARA LOS DEMÁS 13 CAMPOS ...

 // 5. Sexo (String)
 System.out.println("\nValor actual [Sexo]: " + paciente.getSexo());
 if (confirmarCambio()) {
     System.out.print("Nuevo Sexo: ");
     String nuevoSexo = this.sc.nextLine();
     if (!nuevoSexo.trim().isEmpty()) {
         paciente.setSexo(nuevoSexo);
     }
 }
 
 // 6. Raza Etnia (String)
 System.out.println("\nValor actual [Raza Etnia]: " + paciente.getRazaEtnia());
 if (confirmarCambio()) {
     System.out.print("Nueva Raza/Etnia: ");
     String nuevaRazaEtnia = this.sc.nextLine();
     if (!nuevaRazaEtnia.trim().isEmpty()) {
         paciente.setRazaEtnia(nuevaRazaEtnia);
     }
 }

 // 7. Ocupacion (String)
 System.out.println("\nValor actual [Ocupación]: " + paciente.getOcupacion());
 if (confirmarCambio()) {
     System.out.print("Nueva Ocupación: ");
     String nuevaOcupacion = this.sc.nextLine();
     if (!nuevaOcupacion.trim().isEmpty()) {
         paciente.setOcupacion(nuevaOcupacion);
     }
 }

 // 8. Estado Civil (String)
 System.out.println("\nValor actual [Estado Civil]: " + paciente.getEstadoCivil());
 if (confirmarCambio()) {
     System.out.print("Nuevo Estado Civil: ");
     String nuevoEstadoCivil = this.sc.nextLine();
     if (!nuevoEstadoCivil.trim().isEmpty()){
         paciente.setEstadoCivil(nuevoEstadoCivil);
     }
 }

 // 9. Ciudad Origen (String)
 System.out.println("\nValor actual [Ciudad Origen]: " + paciente.getCiudadOrigen());
 if (confirmarCambio()) {
     System.out.print("Nueva Ciudad de Origen: ");
     String nuevaCiudadOrigen = this.sc.nextLine();
     if (!nuevaCiudadOrigen.trim().isEmpty()) {
         paciente.setCiudadOrigen(nuevaCiudadOrigen);
     }
 }

 // 10. Residencia Habitual (String)
 System.out.println("\nValor actual [Residencia Habitual]: " + paciente.getResidenciaHabitual());
 if (confirmarCambio()) {
     System.out.print("Nueva Residencia Habitual: ");
     String nuevaResidenciaHabitual = this.sc.nextLine();
     if (!nuevaResidenciaHabitual.trim().isEmpty()) {
         paciente.setResidenciaHabitual(nuevaResidenciaHabitual);
     }
 }

 // 11. Residencia Ocasional (String)
 System.out.println("\nValor actual [Residencia Ocasional]: " + paciente.getResidenciaOcasional());
 if (confirmarCambio()) {
     System.out.print("Nueva Residencia Ocasional: ");
     String nuevaResidenciaOcasional = this.sc.nextLine();
     if (!nuevaResidenciaOcasional.trim().isEmpty()) {
         paciente.setResidenciaOcasional(nuevaResidenciaOcasional);
     }
 }

 // 12. Telefono (String)
 System.out.println("\nValor actual [Teléfono]: " + paciente.getTelefono());
 if (confirmarCambio()) {
     System.out.print("Nuevo Teléfono: ");
     String nuevoTelefono = this.sc.nextLine();
     if (!nuevoTelefono.trim().isEmpty()) {
         paciente.setTelefono(nuevoTelefono);
     }
 }

 // 13. Contacto Emergencia (String)
 System.out.println("\nValor actual [Contacto Emergencia]: " + paciente.getContactoEmergencia());
 if (confirmarCambio()) {
     System.out.print("Nuevo Contacto de Emergencia: ");
     String nuevoContactoEmergencia = this.sc.nextLine();
     if (!nuevoContactoEmergencia.trim().isEmpty()) {
         paciente.setContactoEmergencia(nuevoContactoEmergencia);
     }
 }

 // 14. Seguro (String)
 System.out.println("\nValor actual [Seguro]: " + paciente.getSeguro());
 if (confirmarCambio()) {
     System.out.print("Nuevo Seguro: ");
     String nuevoSeguro = this.sc.nextLine();
     if (!nuevoSeguro.trim().isEmpty()) {
         paciente.setSeguro(nuevoSeguro);
     }
 }

 // 15. Grupo Sanguineo (String)
 System.out.println("\nValor actual [Grupo Sanguíneo]: " + paciente.getGrupoSanguineo());
 if (confirmarCambio()) {
     System.out.print("Nuevo Grupo Sanguíneo: ");
     String nuevoGrupoSanguineo = this.sc.nextLine();
     if (!nuevoGrupoSanguineo.trim().isEmpty()) {
         paciente.setGrupoSanguineo(nuevoGrupoSanguineo);
     }
 }

 // 16. Alergias (String)
 System.out.println("\nValor actual [Alergias]: " + paciente.getAlergias());
 if (confirmarCambio()) {
     System.out.print("Nuevas Alergias: ");
     String nuevasAlergias = this.sc.nextLine();
     if (!nuevasAlergias.trim().isEmpty()) {
         paciente.setAlergias(nuevasAlergias);
     }
 }

 // 17. Antecedentes Crónicos (String)
 System.out.println("\nValor actual [Antecedentes Crónicos]: " + paciente.getAntecedentesCronicos());
 if (confirmarCambio()) {
     System.out.print("Nuevos Antecedentes Crónicos: ");
     String nuevosAntecedentesCronicos = this.sc.nextLine();
     if (!nuevosAntecedentesCronicos.trim().isEmpty()) {
         paciente.setAntecedentesCronicos(nuevosAntecedentesCronicos);
     }
 }

 // 18. Medicamentos Actuales (String)
 System.out.println("\nValor actual [Medicamentos Actuales]: " + paciente.getMedicamentosActuales());
 if (confirmarCambio()) {
     System.out.print("Nuevos Medicamentos Actuales: ");
     String nuevosMedicamentosActuales = this.sc.nextLine();
     if (!nuevosMedicamentosActuales.trim().isEmpty()) {
         paciente.setMedicamentosActuales(nuevosMedicamentosActuales);
     }
 }

 System.out.println("\n✅ Datos del paciente actualizados en memoria.");
}
/**
* Carga todos los pacientes del archivo CSV y devuelve una lista de objetos.
*/
//En GestorCSV.java

public List<FichaBasePaciente> cargarTodosPacientes() {
 List<FichaBasePaciente> pacientes = new ArrayList<>();
 // Asegúrate de que tengas una variable para el delimitador (ej: CSV_DELIMITER)
 final String CSV_DELIMITER = ","; 
 
 try (BufferedReader br = new BufferedReader(new FileReader(PACIENTES_FILE))) {
     String line;
     
     // 1. Saltar la cabecera (HEADER)
     if ((line = br.readLine()) == null) return pacientes; 
     
     // 2. Leer y procesar las líneas de datos
     while ((line = br.readLine()) != null) {
         
         // Ignorar líneas que solo contienen espacios
         if (line.trim().isEmpty()) continue; 
         
         String[] data = line.split(CSV_DELIMITER);

         // Intentar procesar la línea de datos (requiere 18 campos)
         if (data.length >= 18) { 
             try {
                 FichaBasePaciente p = new FichaBasePaciente();
                 
                 // Asignación de Campos (¡Asegúrate que el orden es correcto!)
                 p.setIdPaciente(data[0].trim()); // Importante el .trim()
                 p.setNombreCompleto(data[1]);
                 p.setEdad(Integer.parseInt(data[2].trim())); // Conversión
                 p.setPeso(Double.parseDouble(data[3].trim())); // Conversión
                 p.setSexo(data[4]);
                 p.setRazaEtnia(data[5]);
                 p.setOcupacion(data[6]);
                 p.setEstadoCivil(data[7]);
                 p.setCiudadOrigen(data[8]);
                 p.setResidenciaHabitual(data[9]);
                 p.setResidenciaOcasional(data[10]);
                 p.setTelefono(data[11]);
                 p.setContactoEmergencia(data[12]);
                 p.setSeguro(data[13]);
                 p.setGrupoSanguineo(data[14]);
                 p.setAlergias(data[15]);
                 p.setAntecedentesCronicos(data[16]);
                 p.setMedicamentosActuales(data[17]);
                 
                 pacientes.add(p);
                 
             } catch (NumberFormatException e) {
                 // ⬅️ DIAGNÓSTICO: FALLA EN CONVERSIÓN DE NÚMERO
                 System.err.println("\n❌ ERROR DE FORMATO EN CSV (FichaBase_Pacientes.csv):");
                 System.err.println("❌ Causa: Valor no numérico en campo esperado (Edad o Peso).");
                 System.err.println("❌ LÍNEA DEFECTUOSA (NO CARGADA): " + line);
                 System.err.println("❌ DETALLE: " + e.getMessage());
             }
         } else {
              // ⬅️ DIAGNÓSTICO: FALLA POR NÚMERO INCORRECTO DE CAMPOS
             System.err.println("\n❌ ERROR DE FORMATO EN CSV (FichaBase_Pacientes.csv):");
             System.err.println("❌ Causa: Faltan campos. Esperado: 18. Encontrado: " + data.length);
             System.err.println("❌ LÍNEA DEFECTUOSA (NO CARGADA): " + line);
         }
     }
     
 } catch (IOException e) {
     System.err.println("❌ Error al leer el archivo de pacientes: " + e.getMessage());
 }
 
 // Opcional: Esto te dirá si al final la lista se cargó o quedó vacía
 System.out.println("\nDEBUG - Pacientes Cargados exitosamente: " + pacientes.size() + " registros.");

 return pacientes;
}
/**
* Carga todos los historiales del archivo CSV y devuelve una lista de objetos.
*/
private List<HistorialClinico> cargarTodosHistoriales() {
List<HistorialClinico> historiales = new ArrayList<>();
try (BufferedReader br = new BufferedReader(new FileReader(HISTORIALES_FILE))) {
br.readLine(); // Saltar la cabecera
String line;
while ((line = br.readLine()) != null) {
String[] data = line.split(CSV_DELIMITER);

if (data.length >= 11) {
HistorialClinico h = new HistorialClinico();

// Asignación (parsing) de los 11 campos
h.setIdHistorial(data[0]);
h.setIdPaciente(data[1]);
h.setSintomaPrincipal(data[2]);
h.setTemperatura(Double.parseDouble(data[3])); // <-- Usa double
h.setFrecuenciaCardiaca(Integer.parseInt(data[4])); // <-- Usa int
h.setPresionArterial(data[5]);
h.setDiagnosticoPrincipal(data[6]);
h.setMedicoTratante(data[7]);
h.setFechaIngreso(data[8]);
h.setEstadoAlta(data[9]);
h.setNotasEvolucion(data[10]);

historiales.add(h);
}
}
} catch (IOException | NumberFormatException e) {
System.err.println("❌ Error al cargar historiales desde CSV: " + e.getMessage());
}
return historiales;
}

/**
* Reescribe completamente el archivo con los datos de la lista (Usado para Editar/Eliminar).
*/
private <T> void reescribirArchivo(String fileName, String header, List<T> data) {
try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, false))) { // false = SOBREESCRIBIR
pw.println(header);
for (T item : data) {
if (item instanceof FichaBasePaciente) {
pw.println(((FichaBasePaciente) item).toCSVRow());
} else if (item instanceof HistorialClinico) {
pw.println(((HistorialClinico) item).toCSVRow());
}
}
} catch (IOException e) {
System.err.println("❌ Error al reescribir el archivo " + fileName + ": " + e.getMessage());
}
}

// --- MÉTODOS DE ARCHIVO BÁSICOS ---

private void asegurarArchivoExiste(String fileName, String header) {
File file = new File(fileName);
if (!file.exists()) {
try (PrintWriter pw = new PrintWriter(new FileWriter(file, false))) {
pw.println(header);
System.out.println("💾 Archivo creado: " + fileName);
} catch (IOException e) {
System.err.println("❌ Error al crear archivo inicial " + fileName + ": " + e.getMessage());
}
}
}

private void escribirFila(String fileName, String dataRow) {
try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) { 
pw.println(dataRow);
} catch (IOException e) {
System.err.println("❌ Error al escribir en " + fileName + ": " + e.getMessage());
}
}

private <T> List<T> leerYMostrarTodos(String fileName, String nombreRegistro) {
System.out.println("--- Contenido de " + nombreRegistro + " (" + fileName + ") ---");

try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
String headerLine = br.readLine(); // Leer la cabecera
if (headerLine != null) {
System.out.println("HEADER: " + headerLine);
}

String line;
int count = 0;
while ((line = br.readLine()) != null) {
System.out.println("    > " + line);
count++;
}
System.out.println(count == 0 ? "    (Archivo vacío. No hay registros.)" : "    Total de registros: " + count);
} catch (IOException e) {
System.err.println("❌ Error de lectura: " + e.getMessage());
}

return new ArrayList<>(); 
}
}