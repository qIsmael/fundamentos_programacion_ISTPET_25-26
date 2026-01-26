package GestionClientes;

import java.util.Scanner;

public class GestionClientes2 {

	static Scanner scanner = new Scanner(System.in);

    static String[] nombres = new String[10];
    static String[] cedulas = new String[10];
    static String[] correos = new String[10];

    static int contador = 0;

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    buscarCliente();
                    break;
                case 3:
                    editarCliente();
                    break;
                case 4:
                    eliminarCliente();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public static void mostrarMenu() {
        System.out.println(" MENÚ DE CLIENTES ");
        System.out.println("1. Agregar cliente");
        System.out.println("2. Buscar cliente");
        System.out.println("3. Editar cliente");
        System.out.println("4. Eliminar cliente");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public static void agregarCliente() {
        if (contador >= nombres.length) {
            System.out.println("No se pueden agregar más clientes.");
            return;
        }

        System.out.print("Ingrese nombre: ");
        nombres[contador] = scanner.nextLine();

        System.out.print("Ingrese cédula: ");
        cedulas[contador] = scanner.nextLine();

        System.out.print("Ingrese correo electrónico: ");
        correos[contador] = scanner.nextLine();

        contador++;
        System.out.println("Cliente agregado correctamente.");
    }

    public static void buscarCliente() {
        System.out.println("Buscar por:");
        System.out.println("1. Cédula");
        System.out.println("2. Nombre");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        int posicion = -1;

        if (opcion == 1) {
            System.out.print("Ingrese la cédula: ");
            String cedulaBuscar = scanner.nextLine();

            posicion = buscarPorCedula(cedulaBuscar);

        } else if (opcion == 2) {
            System.out.print("Ingrese el nombre: ");
            String nombreBuscar = scanner.nextLine();

            posicion = buscarPorNombre(nombreBuscar);
        }

        if (posicion != -1) {
            System.out.println("Cliente encontrado:");
            mostrarCliente(posicion);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    public static int buscarPorCedula(String cedula) {
        for (int i = 0; i < contador; i++) {
            if (cedulas[i].equalsIgnoreCase(cedula)) {
                return i;
            }
        }
        return -1;
    }

    public static int buscarPorNombre(String nombre) {
        for (int i = 0; i < contador; i++) {
            if (nombres[i].equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1;
    }

    public static void editarCliente() {
        System.out.print("Ingrese la cédula del cliente a editar: ");
        String cedula = scanner.nextLine();

        int posicion = buscarPorCedula(cedula);

        if (posicion == -1) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.print("Nuevo nombre: ");
        nombres[posicion] = scanner.nextLine();

        System.out.print("Nueva cédula: ");
        cedulas[posicion] = scanner.nextLine();

        System.out.print("Nuevo correo electrónico: ");
        correos[posicion] = scanner.nextLine();

        System.out.println("Datos actualizados correctamente.");
    }

    public static void eliminarCliente() {
        System.out.print("Ingrese la cédula del cliente a eliminar: ");
        String cedula = scanner.nextLine();

        int posicion = buscarPorCedula(cedula);

        if (posicion == -1) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        for (int i = posicion; i < contador - 1; i++) {
            nombres[i] = nombres[i + 1];
            cedulas[i] = cedulas[i + 1];
            correos[i] = correos[i + 1];
        }

        contador--;
        System.out.println("Cliente eliminado con éxito.");
    }

    public static void mostrarCliente(int i) {
        System.out.println("Nombre: " + nombres[i]);
        System.out.println("Cédula: " + cedulas[i]);
        System.out.println("Correo: " + correos[i]);
    }
}