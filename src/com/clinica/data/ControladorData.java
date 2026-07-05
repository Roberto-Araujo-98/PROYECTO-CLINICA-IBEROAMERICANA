package com.clinica.data;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ControladorData {

    // Contraseñas y Roles Fijos
    private static final String PASS_ADMIN = "admin123";
    private static final String ROL_ADMIN = "Administrador";
    private static final String PASS_MEDICO = "doc456";
    private static final String ROL_MEDICO = "Médico";

    // Instancia del Gestor CSV
    private static final GestorCSV gestor = new GestorCSV();

    // ===
    // PUNTO DE ENTRADA (MAIN)
    // ===
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String rolUsuario = null;
        
        System.out.println("=======================================================");
        System.out.println("---   BIEMVENIDO AL SISTEMA | DE GESTIÓN CLÍNICA ---");
        System.out.println("=======================================================");
        System.out.print("Ingrese su contraseña de acceso: ");
        String contrasena = scanner.nextLine();

        // 1. AUTENTICACIÓN
        if (contrasena.equals(PASS_ADMIN)) {
            rolUsuario = ROL_ADMIN;
            System.out.println("\n✅ Acceso concedido. Rol: " + ROL_ADMIN);
        } else	if (contrasena.equals(PASS_MEDICO)) {
            rolUsuario = ROL_MEDICO;
            System.out.println("\n✅ Acceso concedido. Rol: " + ROL_MEDICO);
        } else {
            System.out.println("\n❌ Acceso denegado. Contraseña incorrecta.");
            scanner.close();
            return;
        }

        // 2. EJECUCIÓN DEL MENÚ
        if (ROL_ADMIN.equals(rolUsuario)) {
            mostrarMenuAdministrador(scanner);
        } else if (ROL_MEDICO.equals(rolUsuario)) {
            mostrarMenuMedico(scanner);
        }

        System.out.println("\n--- Sesión terminada. ¡Gracias por usar nuestro sistema ! ¡Hasta pronto! ---");
        scanner.close();
    }

    // ===
    // MENÚ ADMINISTRADOR (Solo Visual)
    // ===
    private static void mostrarMenuAdministrador(Scanner scanner) {
        int opcion = 0;
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Visualizar Pacientes");
            System.out.println("2. Visualizar Historias Clínicas");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1: gestor.verPacientes(); break;
                    case 2: gestor.verHistoriales(); break;
                    case 3: salir = true;
                    break;
                    default: System.out.println("\n⚠️ Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n⚠️ Error de entrada. Ingrese solo números.");
                scanner.nextLine();
            }
        }
    }

    // ===
    // MENÚ MÉDICO (CRUD Completo)
    // ===

    private static void mostrarMenuMedico(Scanner scanner) {
        int opcion = 0;
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ MÉDICO (CRUD) ---");
            System.out.println("--- Pacientes ---");
            System.out.println("1. Registrar Nuevo Paciente");
            System.out.println("2. Ver Pacientes");
            System.out.println("3. Editar Paciente");
            System.out.println("4. Eliminar Paciente");
            System.out.println("--- Historias ---");
            System.out.println("5. Registrar Historia Clínica");
            System.out.println("6. Ver Historias Clínicas");
            System.out.println("7. Editar Historia Clínica");
            System.out.println("8. Eliminar Historia Clínica");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");
     
      //SWITCH-CASE: Estructura de control que dirige 
      //la llamada al meto CRUD
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1: gestor.registrarPaciente(gestor.solicitarDatosPaciente()); break;
                    case 2: gestor.verPacientes(); break;
                    case 3: 
                        System.out.print("\n[EDITAR PACIENTE] Ingrese ID: ");
                        gestor.editarPaciente(scanner.nextLine());
                        break;
                    case 4: 
                        System.out.print("\n[ELIMINAR PACIENTE] Ingrese ID: ");
                        gestor.eliminarPaciente(scanner.nextLine());
                        break;
                    case 5: gestor.registrarHistorial(gestor.solicitarDatosHistorial()); break;
                    case 6: gestor.verHistoriales(); break;
                    case 7: 
                        System.out.print("\n[EDITAR HISTORIA] Ingrese ID: ");
                        gestor.editarHistorial(scanner.nextLine());
                        break;
                    case 8: 
                        System.out.print("\n[ELIMINAR HISTORIA] Ingrese ID: ");
                        gestor.eliminarHistorial(scanner.nextLine());
                        break;
                    case 9: salir = true; break;
                    default: System.out.println("\n⚠️ Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n⚠️ Error de entrada. Ingrese solo números.");
                scanner.nextLine();
            }
        }
    }
}