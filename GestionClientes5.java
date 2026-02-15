package GestionClientes;

import java.io.*;
import java.util.*;

public class GestionClientes5 {

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

        int opcion = 0;

        do {
            try {
                System.out.println(" SISTEMA DE CLIENTES ");
                System.out.println("1. Registrar cliente");
                System.out.println("2. Listar clientes");
                System.out.println("3. Editar cliente");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");

                opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1:
                        registrarCliente();
                        break;
                    case 2:
                        listarClientes();
                        break;
                    case 3:
                        editarCliente();
                        break;
                    case 4:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println(" Opción inválida.");
                }

            } catch (InputMismatchException e) {
                System.out.println(" Error: Debe ingresar un número válido.");
                scanner.nextLine();
            }

        } while (opcion != 4);

        scanner.close();
    }

    public static void registrarCliente() {

        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            if (nombre == null || nombre.trim().isEmpty()) {
                System.out.println(" El nombre es obligatorio.");
                return;
            }

            System.out.print("Cédula: ");
            String cedula = scanner.nextLine();

            if (!cedula.matches("\\d{8,10}")) {
                System.out.println(" Cédula inválida. Solo números (8-10 dígitos).");
                return;
            }

            System.out.print("Correo: ");
            String correo = scanner.nextLine();

            if (!correo.contains("@") || !correo.contains(".")) {
                System.out.println(" Correo inválido.");
                return;
            }

            Cliente cliente = new Cliente(nombre, cedula, correo);
            guardarEnArchivo(cliente);

            System.out.println(" Cliente registrado correctamente.");

        } catch (Exception e) {
            System.out.println(" Error inesperado: " + e.getMessage());
        }
    }

    public static void guardarEnArchivo(Cliente cliente) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(cliente.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println(" Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public static void listarClientes() {

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {

            String linea;
            while ((linea = br.readLine()) != null) {

                try {
                    String[] datos = linea.split(",");

                    if (datos.length != 3) {
                        throw new IllegalArgumentException();
                    }

                    System.out.println("Nombre: " + datos[0] +
                                       "  Cédula: " + datos[1] +
                                       "  Correo: " + datos[2]);

                } catch (Exception e) {
                    System.out.println(" Error en formato de línea: " + linea);
                }
            }

        } catch (IOException e) {
            System.out.println(" Error al leer el archivo: " + e.getMessage());
        }
    }

    public static void editarCliente() {

        try {
            System.out.print("Ingrese la cédula del cliente a editar: ");
            String cedulaBuscar = scanner.nextLine();

            if (!cedulaBuscar.matches("\\d{8,10}")) {
                System.out.println(" Cédula inválida.");
                return;
            }

            List<String> lineas = new ArrayList<>();
            boolean encontrado = false;

            try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {

                String linea;

                while ((linea = br.readLine()) != null) {

                    String[] datos = linea.split(",");

                    if (datos.length == 3 && datos[1].equals(cedulaBuscar)) {

                        encontrado = true;

                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();

                        if (nuevoNombre.trim().isEmpty()) {
                            System.out.println(" Nombre inválido.");
                            return;
                        }

                        System.out.print("Nuevo correo: ");
                        String nuevoCorreo = scanner.nextLine();

                        if (!nuevoCorreo.contains("@") || !nuevoCorreo.contains(".")) {
                            System.out.println(" Correo inválido.");
                            return;
                        }

                        linea = nuevoNombre + "," + cedulaBuscar + "," + nuevoCorreo;
                    }

                    lineas.add(linea);
                }

            } catch (IOException e) {
                System.out.println(" Error al leer el archivo.");
                return;
            }

            if (!encontrado) {
                System.out.println(" Cliente no encontrado.");
                return;
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
                for (String l : lineas) {
                    bw.write(l);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println(" Error al actualizar el archivo.");
                return;
            }

            System.out.println(" Cliente actualizado correctamente.");

        } catch (Exception e) {
            System.out.println(" Error inesperado: " + e.getMessage());
        }
    }
}
