package GestionClientes;

import java.io.*;
import java.util.*;

public class GestionClientes4 {

    static class Cliente {
        private String id;
        private String nombre;
        private String email;
        private String categoria;

        public Cliente(String id, String nombre, String email, String categoria) {
            this.id = id;
            this.nombre = nombre;
            this.email = email;
            this.categoria = categoria;
        }

        public String getId() {
            return id;
        }

        public String getCategoria() {
            return categoria;
        }

        public String toFileString() {
            return id + ";" + nombre + ";" + email;
        }

        public static Cliente fromFileString(String linea, String categoria) {
            String[] datos = linea.split(";");
            if (datos.length == 3) {
                return new Cliente(datos[0], datos[1], datos[2], categoria);
            }
            return null;
        }
   
        public String toString() {
            return "ID: " + id + " | Nombre: " + nombre + " | Email: " + email + " | Categoría: " + categoria;
        }
     
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cliente)) return false;
            Cliente cliente = (Cliente) o;
            return Objects.equals(id, cliente.id);
        }

        public int hashCode() {
            return Objects.hash(id);
        }
    }

    private static final String[] CATEGORIAS = {
            "Regular",
            "Premium",
            "Empresarial"
    };

    private static Map<String, List<Cliente>> clientesPorCategoria = new HashMap<>();

    public static void main(String[] args) {

        inicializarCategorias();
        cargarClientes();

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println(" MENÚ PRINCIPAL");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Listar clientes por categoría");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    agregarCliente(sc);
                    break;

                case 2:
                    listarClientes(sc);
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        sc.close();
    }

    private static void inicializarCategorias() {
        for (String categoria : CATEGORIAS) {
            clientesPorCategoria.put(categoria, new ArrayList<>());
        }
    }

    private static File obtenerArchivoCategoria(String categoria) throws IOException {
        File archivo = new File(categoria + ".txt");

        if (!archivo.exists()) {
            archivo.createNewFile();
            System.out.println("Archivo creado automáticamente: " + archivo.getName());
        }

        return archivo;
    }

    private static void cargarClientes() {

        for (String categoria : CATEGORIAS) {
            try {
                File archivo = obtenerArchivoCategoria(categoria);

                FileReader fr = new FileReader(archivo);
                BufferedReader br = new BufferedReader(fr);

                String linea;

                while ((linea = br.readLine()) != null) {
                    Cliente cliente = Cliente.fromFileString(linea, categoria);
                    if (cliente != null) {
                        clientesPorCategoria.get(categoria).add(cliente);
                    }
                }

                br.close();
                System.out.println("Archivo cargado correctamente: " + categoria + ".txt");

            } catch (IOException e) {
                System.out.println("Error al leer archivo de categoría: " + categoria);
            }
        }
    }

    private static void agregarCliente(Scanner sc) {

        System.out.print("ID: ");
        String id = sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.println("Seleccione categoría:");
        for (int i = 0; i < CATEGORIAS.length; i++) {
            System.out.println((i + 1) + ". " + CATEGORIAS[i]);
        }

        int opcionCategoria = sc.nextInt();
        sc.nextLine();

        if (opcionCategoria < 1 || opcionCategoria > CATEGORIAS.length) {
            System.out.println("Categoría inválida.");
            return;
        }

        String categoria = CATEGORIAS[opcionCategoria - 1];
        Cliente nuevoCliente = new Cliente(id, nombre, email, categoria);

        if (clientesPorCategoria.get(categoria).contains(nuevoCliente)) {
            System.out.println("Error: Cliente con ese ID ya existe en la categoría.");
            return;
        }

        try {
            File archivo = obtenerArchivoCategoria(categoria);

            FileWriter fw = new FileWriter(archivo, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(nuevoCliente.toFileString());
            bw.newLine();
            bw.close();

            clientesPorCategoria.get(categoria).add(nuevoCliente);

            System.out.println("Cliente guardado correctamente en categoría: " + categoria);

        } catch (IOException e) {
            System.out.println("Error al guardar cliente.");
        }
    }

    private static void listarClientes(Scanner sc) {

        System.out.println("Seleccione categoría:");
        for (int i = 0; i < CATEGORIAS.length; i++) {
            System.out.println((i + 1) + ". " + CATEGORIAS[i]);
        }

        int opcionCategoria = sc.nextInt();
        sc.nextLine();

        if (opcionCategoria < 1 || opcionCategoria > CATEGORIAS.length) {
            System.out.println("Categoría inválida.");
            return;
        }

        String categoria = CATEGORIAS[opcionCategoria - 1];
        List<Cliente> lista = clientesPorCategoria.get(categoria);

        if (lista.isEmpty()) {
            System.out.println("No hay clientes en esta categoría.");
            return;
        }

        System.out.println("\nClientes categoría: " + categoria);
        for (Cliente cliente : lista) {
            System.out.println(cliente);
        }
    }
}
