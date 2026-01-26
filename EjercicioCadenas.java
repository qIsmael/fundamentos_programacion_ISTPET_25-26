package Arreglos;

import java.util.Scanner;

public class EjercicioCadenas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(" MANIPULACIÓN DE CADENAS ");
        System.out.print("Ingrese una cadena de texto: ");
        String texto = scanner.nextLine();

        mostrarLongitud(texto);
        mostrarMayusculasMinusculas(texto);
        contarVocales(texto);

        scanner.close();
    }


    public static void mostrarLongitud(String texto) {
        System.out.println("Longitud de la cadena: " + texto.length());
    }


    public static void mostrarMayusculasMinusculas(String texto) {
        System.out.println("Texto en mayúsculas: " + texto.toUpperCase());
        System.out.println("Texto en minúsculas: " + texto.toLowerCase());
    }

    public static void contarVocales(String texto) {
        int contadorVocales = 0;
        texto = texto.toLowerCase();

        for (int i = 0; i < texto.length(); i++) {
            char letra = texto.charAt(i);

            if (letra == 'a' || letra == 'e' || letra == 'i' ||
                letra == 'o' || letra == 'u') {
                contadorVocales++;
            }
        }

        System.out.println("Número de vocales: " + contadorVocales);
    }
}