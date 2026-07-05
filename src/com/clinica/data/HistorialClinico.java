package com.clinica.data;

/**
 * Clase Modelo (Entidad) que representa un registro médico o episodio clínico.
 * Almacena los signos vitales, diagnóstico y evolución de una consulta específica.
 * Es la clave para el seguimiento longitudinal de la salud del paciente.
 */

public class HistorialClinico {
    
	// 1. Atributos (Variables de Instancia)
    // 11 Variables (Atributos)
    private String idHistorial;
    private String idPaciente;
    private String sintomaPrincipal;
    private double temperatura;
    private int frecuenciaCardiaca;
    private String presionArterial;
    private String diagnosticoPrincipal;
    private String medicoTratante;
    private String fechaIngreso;
    private String estadoAlta;
    private String notasEvolucion;
    
    // 2. Constructores
    // Constructor Completo
    public HistorialClinico(String idHistorial, String idPaciente, String sintomaPrincipal, double temperatura, int frecuenciaCardiaca, 
                            String presionArterial, String diagnosticoPrincipal, String medicoTratante, 
                            String fechaIngreso, String estadoAlta, String notasEvolucion) {
        this.idHistorial = idHistorial;
        this.idPaciente = idPaciente;
        this.sintomaPrincipal = sintomaPrincipal;
        this.temperatura = temperatura;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.presionArterial = presionArterial;
        this.diagnosticoPrincipal = diagnosticoPrincipal;
        this.medicoTratante = medicoTratante;
        this.fechaIngreso = fechaIngreso;
        this.estadoAlta = estadoAlta;
        this.notasEvolucion = notasEvolucion;
    }
    
    // Constructor Vacío
    public HistorialClinico() {}
    
    // 3. Métodos de Persistencia (CSV)
    // --- Métodos CSV ---
    public static String getCSVHeader() {
        return "idHistorial,idPaciente,sintomaPrincipal,temperatura,frecuenciaCardiaca,presionArterial,diagnosticoPrincipal,medicoTratante,fechaIngreso,estadoAlta,notasEvolucion";
    }

    public String toCSVRow() {
        return String.format("%s,%s,%s,%.2f,%d,%s,%s,%s,%s,%s,%s",
                idHistorial, idPaciente, sintomaPrincipal, temperatura, frecuenciaCardiaca, presionArterial,
                diagnosticoPrincipal, medicoTratante, fechaIngreso, estadoAlta, notasEvolucion);
    }
    // 4. Getters y Setters
    
    // --- Getters y Setters ---
    public String getIdHistorial() { return idHistorial; }
    public String getIdPaciente() { return idPaciente; }
    public double getTemperatura() { return temperatura; }
    public String getSintomaPrincipal() {return sintomaPrincipal;    }
    public int getFrecuenciaCardiaca() { return frecuenciaCardiaca; } 
    public String getPresionArterial() { return presionArterial; } 
    public String getDiagnosticoPrincipal() { return diagnosticoPrincipal; } 
    public String getMedicoTratante() { return medicoTratante; }
    public String getFechaIngreso() { return fechaIngreso; } 
    public String getEstadoAlta() { return estadoAlta; } 
    public String getNotasEvolucion() { return notasEvolucion; }
    
    public void setIdHistorial(String idHistorial) { this.idHistorial = idHistorial; }
    public void setFechaIngreso(String fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }
    public void setSintomaPrincipal(String sintomaPrincipal) { this.sintomaPrincipal = sintomaPrincipal; }
    public void setTemperatura(double temperatura) { this.temperatura = temperatura; }
    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) { this.frecuenciaCardiaca = frecuenciaCardiaca; }
    public void setPresionArterial(String presionArterial) { this.presionArterial = presionArterial; }
    public void setDiagnosticoPrincipal(String diagnosticoPrincipal) { this.diagnosticoPrincipal = diagnosticoPrincipal; }
    public void setMedicoTratante(String medicoTratante) { this.medicoTratante = medicoTratante; }
    public void setEstadoAlta(String estadoAlta) { this.estadoAlta = estadoAlta; }
    public void setNotasEvolucion(String notasEvolucion) { this.notasEvolucion = notasEvolucion; }

 // 5. Método toString
    @Override
    public String toString() {
        return "ID Historial: " + idHistorial + " | Paciente ID: " + idPaciente;
    }
}