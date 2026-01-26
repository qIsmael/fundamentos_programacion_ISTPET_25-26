package Arreglos;

import java.util.Scanner;

public class ArreglosNumeros {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("¿Cuántos números desea ingresar? ");
        int cantidad = scanner.nextInt();

        int[] numeros = new int[cantidad];

        for (int i = 0; i < cantidad; i++) {
            System.out.print("Ingrese el número " + (i + 1) + ": ");
            numeros[i] = scanner.nextInt();
        }

        System.out.println("\nValores ingresados:");
        for (int i = 0; i < cantidad; i++) {
            System.out.print(numeros[i] + " ");
        }

        int mayor = numeros[0];
        int menor = numeros[0];

        for (int i = 1; i < cantidad; i++) {
            if (numeros[i] > mayor) {
                mayor = numeros[i];
            }
            if (numeros[i] < menor) {
                menor = numeros[i];
            }
        }

        System.out.println("\n\nNúmero mayor: " + mayor);
        System.out.println("Número menor: " + menor);

        scanner.close();
    }
}