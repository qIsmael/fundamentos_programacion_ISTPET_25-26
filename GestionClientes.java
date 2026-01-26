package GestionClientes;

import java.util.Scanner;

public class GestionClientes {

    static final int MAX_CLIENTES = 5;

    static String[] nombres = new String[MAX_CLIENTES];
    static String[] cedulas = new String[MAX_CLIENTES];
    static String[] correos = new String[MAX_CLIENTES];

    static int totalClientes = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
         
            System.out.println("\n--- Sistema de Gestión de Clientes ---");
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Listar todos los clientes");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); 
            switch (opcion) {
                case 1:
                    agregarCliente(scanner);
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 3);

        scanner.close();
    }


    public static void agregarCliente(Scanner scanner) {
        if (totalClientes >= MAX_CLIENTES) {
            System.out.println("No se pueden registrar más clientes. Arreglo lleno.");
            return;
        }

        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese la cédula del cliente: ");
        String cedula = scanner.nextLine();

        System.out.print("Ingrese el correo electrónico del cliente: ");
        String correo = scanner.nextLine();

        nombres[totalClientes] = nombre;
        cedulas[totalClientes] = cedula;
        correos[totalClientes] = correo;

        totalClientes++;

        System.out.println("Cliente registrado correctamente.");
    }

    public static void listarClientes() {
        if (totalClientes == 0) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println(" Lista de Clientes ");
        for (int i = 0; i < totalClientes; i++) {
            System.out.println("Cliente #" + (i + 1));
            System.out.println("Nombre: " + nombres[i]);
            System.out.println("Cédula: " + cedulas[i]);
            System.out.println("Correo: " + correos[i]);
            System.out.println("---------------------------");
        }
    }
}
