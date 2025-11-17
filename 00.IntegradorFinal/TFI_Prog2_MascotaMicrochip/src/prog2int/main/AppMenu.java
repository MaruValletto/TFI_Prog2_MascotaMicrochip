/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog2int.main;

import prog2int.entities.Mascota;
import prog2int.entities.Microchip;
import prog2int.service.MascotaService;
import prog2int.service.MicrochipService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author vallett
 */
public class AppMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final MascotaService mascotaService = new MascotaService();
    private final MicrochipService microchipService = new MicrochipService();

    public static void main(String[] args) {
        AppMenu app = new AppMenu();
        app.iniciar();
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> menuMascotas();
                case 2 -> menuMicrochips();
                case 3 -> menuBusquedaAvanzada();
                case 0 -> System.out.println("Saliendo de la aplicación...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    // ================= MENÚS =================

    private void mostrarMenuPrincipal() {
        System.out.println("\n===== GESTIÓN DE MASCOTAS Y MICROCHIPS =====");
        System.out.println("1. Gestionar Mascotas");
        System.out.println("2. Gestionar Microchips");
        System.out.println("3. Búsqueda avanzada");
        System.out.println("0. Salir");
    }

    private void menuMascotas() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ MASCOTAS ---");
            System.out.println("1. Alta de mascota (sin microchip)");
            System.out.println("2. Alta de mascota con microchip");
            System.out.println("3. Listar mascotas");
            System.out.println("4. Actualizar mascota");
            System.out.println("5. Baja lógica de mascota");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> altaMascotaSimple();
                    case 2 -> altaMascotaConMicrochip();
                    case 3 -> listarMascotas();
                    case 4 -> actualizarMascota();
                    case 5 -> bajaLogicaMascota();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error al procesar la operación: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void menuMicrochips() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ MICROCHIPS ---");
            System.out.println("1. Listar microchips");
            System.out.println("2. Ver detalle de microchip por ID");
            System.out.println("3. Actualizar datos de microchip");
            System.out.println("4. Baja lógica de microchip");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> listarMicrochips();
                    case 2 -> verMicrochipPorId();
                    case 3 -> actualizarMicrochip();
                    case 4 -> bajaLogicaMicrochip();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error al procesar la operación: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void menuBusquedaAvanzada() {
        int opcion;
        do {
            System.out.println("\n--- BÚSQUEDA AVANZADA ---");
            System.out.println("1. Buscar mascota por nombre");
            System.out.println("2. Buscar mascota por dueño");
            System.out.println("3. Buscar microchip por código");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> buscarMascotaPorNombre();
                    case 2 -> buscarMascotaPorDuenio();
                    case 3 -> buscarMicrochipPorCodigo();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error durante la búsqueda: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // ============ OPERACIONES MASCOTA ============

    private void altaMascotaSimple() throws Exception {
        System.out.println("\n>>> Alta de Mascota (sin microchip)");

        String nombre = leerTextoObligatorio("Nombre: ");
        String especie = leerTextoObligatorio("Especie: ").toUpperCase();   
        String raza = leerTexto("Raza (opcional): ").toUpperCase();              
        LocalDate fechaNac = leerFechaOpcional("Fecha de nacimiento (AAAA-MM-DD, vacío si no se sabe): ");
        String duenio = leerTextoObligatorio("Nombre del dueño: ").toUpperCase(); 

        Mascota mascota = new Mascota(nombre, especie, raza, fechaNac, duenio, null);
        Mascota creada = mascotaService.insertar(mascota);

        System.out.println("Mascota creada con éxito: " + creada);
    }

    private void altaMascotaConMicrochip() throws Exception {
        System.out.println("\n>>> Alta de Mascota con Microchip (operación transaccional)");

        String nombre = leerTextoObligatorio("Nombre: ");
        String especie = leerTextoObligatorio("Especie: ").toUpperCase();   
        String raza = leerTexto("Raza (opcional): ").toUpperCase(); 
        LocalDate fechaNac = leerFechaOpcional("Fecha de nacimiento (AAAA-MM-DD, vacío si no se sabe): ");
        String duenio = leerTextoObligatorio("Nombre del dueño: ").toUpperCase();

        String codigoChip = leerTextoObligatorio("Código de microchip: ").toUpperCase(); 
        LocalDate fechaImplantacion = leerFechaOpcional("Fecha de implantación (AAAA-MM-DD, vacío si no se sabe): ");
        String veterinaria = leerTexto("Veterinaria (opcional): ").toUpperCase();       
        String observaciones = leerTexto("Observaciones (opcional): ");            

        Mascota mascota = new Mascota(nombre, especie, raza, fechaNac, duenio, null);
        Microchip microchip = new Microchip(codigoChip, fechaImplantacion, veterinaria, observaciones);

        Mascota creada = mascotaService.registrarMascotaConMicrochip(mascota, microchip);

        System.out.println("Mascota y microchip registrados correctamente: " + creada);
    }

    private void listarMascotas() throws Exception {
        System.out.println("\n>>> Listado de Mascotas");
        List<Mascota> mascotas = mascotaService.getAll();
        if (mascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
        } else {
            mascotas.forEach(System.out::println);
        }
    }

    private void actualizarMascota() throws Exception {
        System.out.println("\n>>> Actualizar datos de Mascota");
        Long id = (long) leerEntero("Ingrese el ID de la mascota a actualizar: ");
        Mascota existente = mascotaService.getById(id);
        if (existente == null) {
            System.out.println("No se encontró una mascota con ese ID.");
            return;
        }

        System.out.println("Deje el campo vacío para mantener el valor actual.");

        String nombre = leerTexto("Nombre (" + existente.getNombre() + "): ");
        if (!nombre.isBlank()) existente.setNombre(nombre);

        String especie = leerTexto("Especie (" + existente.getEspecie() + "): ");
        if (!especie.isBlank()) existente.setEspecie(especie);

        String raza = leerTexto("Raza (" + existente.getRaza() + "): ");
        if (!raza.isBlank()) existente.setRaza(raza);

        LocalDate fechaNac = leerFechaOpcional("Fecha de nacimiento (" + existente.getFechaNacimiento() + "): ");
        if (fechaNac != null) existente.setFechaNacimiento(fechaNac);

        String duenio = leerTexto("Dueño (" + existente.getDuenio() + "): ");
        if (!duenio.isBlank()) existente.setDuenio(duenio);

        mascotaService.actualizar(existente);
        System.out.println("Mascota actualizada correctamente.");
    }

    private void bajaLogicaMascota() throws Exception {
        System.out.println("\n>>> Baja lógica de Mascota");
        Long id = (long) leerEntero("Ingrese el ID de la mascota a dar de baja: ");
        mascotaService.eliminar(id);
        System.out.println("Mascota marcada como eliminada (baja lógica).");
    }

    // ============ OPERACIONES MICROCHIP ============

    private void listarMicrochips() throws Exception {
        System.out.println("\n>>> Listado de Microchips");
        List<Microchip> microchips = microchipService.getAll();
        if (microchips.isEmpty()) {
            System.out.println("No hay microchips registrados.");
        } else {
            microchips.forEach(System.out::println);
        }
    }

    private void verMicrochipPorId() throws Exception {
        System.out.println("\n>>> Ver detalle de Microchip");
        Long id = (long) leerEntero("Ingrese el ID del microchip: ");
        Microchip mc = microchipService.getById(id);
        if (mc == null) {
            System.out.println("No se encontró un microchip con ese ID.");
        } else {
            System.out.println(mc);
        }
    }

    private void actualizarMicrochip() throws Exception {
        System.out.println("\n>>> Actualizar Microchip");
        Long id = (long) leerEntero("Ingrese el ID del microchip a actualizar: ");
        Microchip existente = microchipService.getById(id);
        if (existente == null) {
            System.out.println("No se encontró un microchip con ese ID.");
            return;
        }

        System.out.println("Deje el campo vacío para mantener el valor actual.");

        String codigo = leerTexto("Código (" + existente.getCodigo() + "): ");
        if (!codigo.isBlank()) existente.setCodigo(codigo);

        LocalDate fechaImpl = leerFechaOpcional("Fecha de implantación (" + existente.getFechaImplantacion() + "): ");
        if (fechaImpl != null) existente.setFechaImplantacion(fechaImpl);

        String vet = leerTexto("Veterinaria (" + existente.getVeterinaria() + "): ");
        if (!vet.isBlank()) existente.setVeterinaria(vet);

        String obs = leerTexto("Observaciones (" + existente.getObservaciones() + "): ");
        if (!obs.isBlank()) existente.setObservaciones(obs);

        microchipService.actualizar(existente);
        System.out.println("Microchip actualizado correctamente.");
    }

    private void bajaLogicaMicrochip() throws Exception {
        System.out.println("\n>>> Baja lógica de Microchip");
        Long id = (long) leerEntero("Ingrese el ID del microchip a dar de baja: ");
        microchipService.eliminar(id);
        System.out.println("Microchip marcado como eliminado (baja lógica).");
    }

    // ============ BÚSQUEDAS ============

    private void buscarMascotaPorNombre() throws Exception {
        String nombre = leerTextoObligatorio("Nombre (o parte): ");
        List<Mascota> mascotas = mascotaService.buscarPorNombre(nombre);
        if (mascotas.isEmpty()) {
            System.out.println("No se encontraron mascotas con ese nombre.");
        } else {
            mascotas.forEach(System.out::println);
        }
    }

    private void buscarMascotaPorDuenio() throws Exception {
        String duenio = leerTextoObligatorio("Nombre del dueño (o parte): ");
        List<Mascota> mascotas = mascotaService.buscarPorDuenio(duenio);
        if (mascotas.isEmpty()) {
            System.out.println("No se encontraron mascotas para ese dueño.");
        } else {
            mascotas.forEach(System.out::println);
        }
    }

    private void buscarMicrochipPorCodigo() throws Exception {
        String codigo = leerTextoObligatorio("Código de microchip: ");
        Microchip microchip = microchipService.getAll()
                .stream()
                .filter(mc -> codigo.equalsIgnoreCase(mc.getCodigo()))
                .findFirst()
                .orElse(null);

        if (microchip == null) {
            System.out.println("No se encontró microchip con ese código.");
        } else {
            System.out.println(microchip);
        }
    }

    // ============ HELPERS DE ENTRADA ============

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine();
            try {
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private String leerTextoObligatorio(String mensaje) {
        String valor;
        do {
            System.out.print(mensaje);
            valor = scanner.nextLine();
            if (valor == null || valor.trim().isEmpty()) {
                System.out.println("Este campo es obligatorio.");
            }
        } while (valor == null || valor.trim().isEmpty());
        return valor.trim();
    }

    private LocalDate leerFechaOpcional(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(valor);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use AAAA-MM-DD o deje vacío.");
            }
        }
    }
}