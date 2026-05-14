import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("================================");
            System.out.println("JAVAES - Analizador Sintáctico");
            System.out.println("==============================");
            System.out.println("1. Ejecutar análisis léxico");
            System.out.println("2. Ejecutar análisis sintáctico");
            System.out.println("3. Mostrar árbol sintáctico");
            System.out.println("4. Ejecutar ejemplo válido");
            System.out.println("5. Ejecutar ejemplo con error");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = sc.nextLine().trim();
            switch (opcion) {
                case "1":
                    System.out.print("Ruta de archivo o 0 para ejemplo: ");
                    String rutaLex = sc.nextLine().trim();
                    if (rutaLex.equals("0")) rutaLex = "resources/examples/Validos/ejemplo_basico.javaes";
                    rutaLex = resolverRuta(rutaLex);
                    LexerRunner.ejecutarLexico(rutaLex);
                    System.out.print("\n¿Desea crear el árbol sintáctico? (1=sí, 2=no): ");
                    String opcionArbol = sc.nextLine().trim();
                    if (opcionArbol.equals("1")) {
                        ParserRunner.mostrarArbol(rutaLex);
                    }
                    break;
                case "2":
                    System.out.print("Ruta de archivo o 0 para ejemplo: ");
                    String rutaSin = sc.nextLine().trim();
                    if (rutaSin.equals("0")) rutaSin = "resources/examples/Validos/ejemplo_basico.javaes";
                    rutaSin = resolverRuta(rutaSin);
                    ParserRunner.ejecutarParser(rutaSin);
                    break;
                case "3":
                    System.out.print("Ruta de archivo o 0 para ejemplo: ");
                    String rutaArb = sc.nextLine().trim();
                    if (rutaArb.equals("0")) rutaArb = "resources/examples/Validos/ejemplo_basico.javaes";
                    rutaArb = resolverRuta(rutaArb);
                    ParserRunner.mostrarArbol(rutaArb);
                    break;
                case "4":
                    mostrarSubmenEjemplos("Validos", sc);
                    break;
                case "5":
                    mostrarSubmenEjemplos("Invalidos", sc);
                    break;
                case "6":
                    System.out.println("Saliendo...");
                    sc.close();
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
            System.out.println();
        }
    }

    private static String resolverRuta(String ruta) {
        File f = new File(ruta);
        if (f.exists()) return ruta;
        File f2 = new File(".." + File.separator + ruta);
        if (f2.exists()) return f2.getPath();
        File f3 = new File(".." + File.separator + ".." + File.separator + ruta);
        if (f3.exists()) return f3.getPath();
        return ruta; // devolver tal cual, el lector mostrará error si no existe
    }

    private static void mostrarSubmenEjemplos(String tipo, Scanner sc) throws IOException {
        String dirEjemplos = resolverRuta("resources/examples/" + tipo);
        File dir = new File(dirEjemplos);
        
        if (!dir.exists()) {
            System.out.println("Directorio no encontrado: " + dirEjemplos);
            return;
        }
        
        File[] archivos = dir.listFiles((d, name) -> name.endsWith(".javaes"));
        if (archivos == null || archivos.length == 0) {
            System.out.println("No hay ejemplos disponibles en " + tipo);
            return;
        }
        
        while (true) {
            System.out.println("\n================================");
            System.out.println("Ejemplos " + tipo);
            System.out.println("================================");
            for (int i = 0; i < archivos.length; i++) {
                System.out.println((i + 1) + ". " + archivos[i].getName());
            }
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione un ejemplo: ");
            
            String seleccion = sc.nextLine().trim();
            try {
                int num = Integer.parseInt(seleccion);
                if (num == 0) {
                    break; // Volver al menú principal
                }
                if (num > 0 && num <= archivos.length) {
                    String rutaArchivo = archivos[num - 1].getAbsolutePath();
                    System.out.println("\n--- Ejemplo: " + archivos[num - 1].getName() + " ---");
                    
                    // Ejecutar análisis léxico
                    LexerRunner.ejecutarLexico(rutaArchivo);
                    
                    // Preguntar por árbol sintáctico
                    System.out.print("¿Desea crear el árbol sintáctico? (1=sí, 2=no): ");
                    String opcionArbol = sc.nextLine().trim();
                    if (opcionArbol.equals("1")) {
                        ParserRunner.mostrarArbol(rutaArchivo);
                    }
                    System.out.println();
                } else {
                    System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida.");
            }
        }
    }
}
