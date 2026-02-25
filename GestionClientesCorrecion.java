package GestionClientes;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class GestionClientesCorrecion {

    private static final String ARCHIVO = "clientes.txt";
    private static Scanner scanner = new Scanner(System.in);

    static class Cliente {
        String nombre;
        String cedula;
        String correo;

        public Cliente(String nombre, String cedula, String correo) {
            this.nombre = nombre;
            this.cedula = cedula;
            this.correo = correo;
        }

        public String toString() {
            return nombre + "," + cedula + "," + correo;
        }
    }

    public static void main(String[] args) {
        crearArchivoSiNoExiste();
        int opcion;

        do {
            System.out.println("\nSISTEMA DE CLIENTES");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente");
            System.out.println("4. Editar cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1: registrarCliente(); break;
                    case 2: listarClientes(); break;
                    case 3: buscarCliente(); break;
                    case 4: editarCliente(); break;
                    case 5: eliminarCliente(); break;
                    case 6: System.out.println("Saliendo..."); break;
                    default: System.out.println("Opción inválida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
                opcion = 0;
            }

        } while (opcion != 6);
    }

    // ================= VALIDACIONES =================

    private static boolean validarCedula(String cedula) {
        return cedula.matches("\\d{8,10}");
    }

    private static boolean validarCorreo(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regex, correo);
    }

    private static void crearArchivoSiNoExiste() {
        try {
            File file = new File(ARCHIVO);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error al crear archivo.");
        }
    }

    // ================= REGISTRAR =================

    public static void registrarCliente() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("Nombre obligatorio.");
            return;
        }

        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();

        if (!validarCedula(cedula)) {
            System.out.println("Cédula inválida.");
            return;
        }

        if (existeCedula(cedula)) {
            System.out.println("Ya existe un cliente con esa cédula.");
            return;
        }

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        if (!validarCorreo(correo)) {
            System.out.println("Correo inválido.");
            return;
        }

        guardarLinea(nombre + "," + cedula + "," + correo);
        System.out.println("Cliente registrado correctamente.");
    }

    // ================= LISTAR =================

    public static void listarClientes() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    System.out.println("Nombre: " + datos[0] +
                            " | Cédula: " + datos[1] +
                            " | Correo: " + datos[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo.");
        }
    }

    // ================= BUSCAR =================

    public static void buscarCliente() {
        System.out.print("Ingrese cédula: ");
        String cedula = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3 && datos[1].equals(cedula)) {
                    System.out.println("Cliente encontrado:");
                    System.out.println(linea);
                    return;
                }
            }
            System.out.println("Cliente no encontrado.");
        } catch (IOException e) {
            System.out.println("Error al leer archivo.");
        }
    }

    // ================= EDITAR =================

    public static void editarCliente() {
        System.out.print("Ingrese cédula: ");
        String cedula = scanner.nextLine();

        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 3 && datos[1].equals(cedula)) {
                    encontrado = true;

                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();

                    System.out.print("Nuevo correo: ");
                    String nuevoCorreo = scanner.nextLine();

                    if (!nuevoNombre.trim().isEmpty() && validarCorreo(nuevoCorreo)) {
                        linea = nuevoNombre + "," + cedula + "," + nuevoCorreo;
                    } else {
                        System.out.println("Datos inválidos. No se actualizó.");
                    }
                }

                lineas.add(linea);
            }

        } catch (IOException e) {
            System.out.println("Error al leer archivo.");
            return;
        }

        if (!encontrado) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        reescribirArchivo(lineas);
        System.out.println("Cliente actualizado.");
    }

    // ================= ELIMINAR =================

    public static void eliminarCliente() {
        System.out.print("Ingrese cédula: ");
        String cedula = scanner.nextLine();

        List<String> lineas = new ArrayList<>();
        boolean eliminado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 3 && datos[1].equals(cedula)) {
                    eliminado = true;
                    continue;
                }
                lineas.add(linea);
            }

        } catch (IOException e) {
            System.out.println("Error al leer archivo.");
            return;
        }

        if (!eliminado) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        reescribirArchivo(lineas);
        System.out.println("Cliente eliminado correctamente.");
    }

    // ================= MÉTODOS AUXILIARES =================

    private static boolean existeCedula(String cedula) {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.split(",")[1].equals(cedula)) {
                    return true;
                }
            }
        } catch (IOException ignored) {}
        return false;
    }

    private static void guardarLinea(String linea) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar.");
        }
    }

    private static void reescribirArchivo(List<String> lineas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (String l : lineas) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar archivo.");
        }
    }
}