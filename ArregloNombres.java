package Arreglos;

import java.util.Scanner;

public class ArregloNombres {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] nombres = crearArregloNombres(scanner);

        buscarNombre(nombres, scanner);

        scanner.close();
    }

    public static String[] crearArregloNombres(Scanner scanner) {
        System.out.print("¿Cuántos nombres deseas ingresar?: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine(); 
        String[] nombres = new String[cantidad];

        for (int i = 0; i < nombres.length; i++) {
            System.out.print("Ingresa el nombre " + (i + 1) + ": ");
            nombres[i] = scanner.nextLine();
        }

        return nombres;
    }

    public static void buscarNombre(String[] nombres, Scanner scanner) {
        System.out.print("Ingresa el nombre que deseas buscar: ");
        String nombreBuscado = scanner.nextLine();

        boolean encontrado = false;

        for (int i = 0; i < nombres.length; i++) {
            if (nombres[i].equalsIgnoreCase(nombreBuscado)) {
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            System.out.println("El nombre \"" + nombreBuscado + "\" fue encontrado en el arreglo.");
        } else {
            System.out.println("El nombre \"" + nombreBuscado + "\" NO fue encontrado en el arreglo.");
        }
    }
}