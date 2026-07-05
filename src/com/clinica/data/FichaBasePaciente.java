package com.clinica.data;

public class FichaBasePaciente {
	
	// 1. Atributos (Variables de Instancia)
	
/*** Clase Modelo (Entidad) que representa la ficha base de un paciente en el sistema.
* Su principal responsabilidad es estructurar y encapsular los datos personales,
* antecedentes y datos demográficos del paciente.
*/

    // 18 Variables
    private String idPaciente;         // Clave única generada por el sistema.
    private String nombreCompleto;    
    private int edad;      
    private double peso;              // Tipo 'int' (entero) para validación de entrada.
    private String sexo;              // Tipo 'double' (decimal) para validación de entrada.
    private String razaEtnia;
    private String ocupacion;
    private String estadoCivil;
    private String ciudadOrigen;
    private String residenciaHabitual;
    private String residenciaOcasional;
    private String telefono;
    private String contactoEmergencia;
    private String seguro;
    private String grupoSanguineo;
    private String alergias;
    private String antecedentesCronicos;   // Almacena el historial de salud crónico.
    private String medicamentosActuales;   // Almacena la medicación que toma actualmente.

    // 2. Constructores 
    
    // Constructor Completo
    public FichaBasePaciente(String idPaciente, String nombreCompleto, int edad, double peso, String sexo, String razaEtnia, String ocupacion, 
                             String estadoCivil, String ciudadOrigen, String residenciaHabitual, String residenciaOcasional, 
                             String telefono, String contactoEmergencia, String seguro, String grupoSanguineo, String alergias, 
                             String antecedentesCronicos, String medicamentosActuales)
    {
        this.idPaciente = idPaciente;
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.peso = peso;
        this.sexo = sexo;
        this.razaEtnia = razaEtnia;
        this.ocupacion = ocupacion;
        this.estadoCivil = estadoCivil;
        this.ciudadOrigen = ciudadOrigen;
        this.residenciaHabitual = residenciaHabitual;
        this.residenciaOcasional = residenciaOcasional;
        this.telefono = telefono;
        this.contactoEmergencia = contactoEmergencia;
        this.seguro = seguro;
        this.grupoSanguineo = grupoSanguineo;
        this.alergias = alergias;
        this.antecedentesCronicos = antecedentesCronicos;
        this.medicamentosActuales = medicamentosActuales;
    }

    // Constructor Vacío
    public FichaBasePaciente() {}

    // 3. Métodos de Persistencia (CSV)
    
    // --- Métodos CSV ---
    public static String getCSVHeader() {
        return "idPaciente,nombreCompleto,edad,peso,sexo, razaEtnia,ocupacion,estadoCivil,ciudadOrigen,residenciaHabitual,residenciaOcasional,telefono,contactoEmergencia,seguro,grupoSanguineo,alergias,antecedentesCronicos,medicamentosActuales";
    }

    public String toCSVRow() {
        return String.format("%s,%s,%d,%.2f,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                idPaciente, nombreCompleto, edad, peso, sexo, razaEtnia, ocupacion, estadoCivil, ciudadOrigen,
                residenciaHabitual, residenciaOcasional, telefono, contactoEmergencia, seguro,
                grupoSanguineo, alergias, antecedentesCronicos, (medicamentosActuales == null ? "": medicamentosActuales))+",";
    }

    // 4. Getters y Setters (Encapsulación)
    
    // --- Getters y Setters ---
    public String getIdPaciente() { return idPaciente; }
    public String getNombreCompleto() { return nombreCompleto; }
    public int getEdad() { return edad; }
    public double getPeso() { return peso; }
    public String getSexo() { return sexo; }
    public String getRazaEtnia() { return razaEtnia; } 
    public String getOcupacion() { return ocupacion; } 
    public String getEstadoCivil() { return estadoCivil; } 
    public String getCiudadOrigen() { return ciudadOrigen; } 
    public String getResidenciaHabitual() { return residenciaHabitual; } 
    public String getResidenciaOcasional() { return residenciaOcasional; }
    public String getTelefono() { return telefono; }
    public String getContactoEmergencia() { return contactoEmergencia; } 
    public String getSeguro() { return seguro; } 
    public String getGrupoSanguineo() { return grupoSanguineo; } 
    public String getAlergias() { return alergias; }
    public String getAntecedentesCronicos() { return antecedentesCronicos; } 
    public String getMedicamentosActuales() { return medicamentosActuales; } 
    
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setPeso(double peso) { this.peso = peso; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public void setRazaEtnia(String razaEtnia) { this.razaEtnia = razaEtnia; }
    public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }
    public void setCiudadOrigen(String ciudadOrigen) { this.ciudadOrigen = ciudadOrigen; }
    public void setResidenciaHabitual(String residenciaHabitual) { this.residenciaHabitual = residenciaHabitual; }
    public void setResidenciaOcasional(String residenciaOcasional) { this.residenciaOcasional = residenciaOcasional; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setContactoEmergencia(String contactoEmergencia) { this.contactoEmergencia = contactoEmergencia; }
    public void setSeguro(String seguro) { this.seguro = seguro; }
    public void setGrupoSanguineo(String grupoSanguineo) { this.grupoSanguineo = grupoSanguineo; }
    public void setAlergias(String alergias) { this.alergias = alergias; }
    public void setAntecedentesCronicos(String antecedentesCronicos) { this.antecedentesCronicos = antecedentesCronicos; }
    public void setMedicamentosActuales(String medicamentosActuales) { this.medicamentosActuales = medicamentosActuales; }

    // ---
    // 5. Método toString
    // ---

    /**
     * Sobreescribe el método toString() para ofrecer una representación legible del objeto.
     * Se usa principalmente para mostrar rápidamente los datos de un paciente en pantalla.
     */

    @Override
    public String toString() {
        return "ID: " + idPaciente + " | Nombre: " + nombreCompleto + " | Edad: " + edad;
    }
}