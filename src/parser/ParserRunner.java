import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParserRunner {
    public static void ejecutarParser(String rutaArchivo) {
        try {
            String texto = new String(Files.readAllBytes(Paths.get(rutaArchivo)), StandardCharsets.UTF_8);
            JavaESLexer lexer = new JavaESLexer(CharStreams.fromString(texto));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaESParser parser = new JavaESParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new ParserErrorListener());
            ParseTree tree = parser.programa();

            System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
            System.out.println("║         ANÁLISIS SINTÁCTICO                                 ║");
            System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

            ParserErrorListener errorListener = (ParserErrorListener) parser.getErrorListeners().get(0);
            if (errorListener.tieneErrores()) {
                System.out.println("❌ Se detectaron errores sintácticos.\n");
            } else {
                System.out.println("✅ Análisis sintáctico completado sin errores.\n");
            }
        } catch (IOException ex) {
            System.err.println("❌ Error leyendo archivo: " + ex.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // CORRECCIÓN (según lo señalado en clase): el árbol sintáctico solo
    // representa una derivación VÁLIDA y COMPLETA de la gramática. Si al
    // programa le faltan piezas (tokens/símbolos) y el analizador tuvo
    // que recuperarse de uno o más errores, el árbol resultante ya no es
    // una construcción fiel de la gramática — es un parche. Por lo tanto
    // NO se debe imprimir un árbol con huecos como si fuera válido.
    // Antes: se imprimía el árbol sin importar si hubo errores.
    // Ahora: solo se construye/imprime el árbol si el análisis terminó
    // sin ningún error sintáctico. Si hubo errores, se informa que el
    // árbol no se puede construir y se detiene ahí, tal como se pidió.
    // ------------------------------------------------------------------
    public static void mostrarArbol(String rutaArchivo) {
        try {
            String texto = new String(Files.readAllBytes(Paths.get(rutaArchivo)), StandardCharsets.UTF_8);
            JavaESLexer lexer = new JavaESLexer(CharStreams.fromString(texto));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaESParser parser = new JavaESParser(tokens);
            parser.removeErrorListeners();
            ParserErrorListener errorListener = new ParserErrorListener();
            parser.addErrorListener(errorListener);
            ParseTree tree = parser.programa();

            if (errorListener.tieneErrores()) {
                System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
                System.out.println("║         ÁRBOL SINTÁCTICO                                 ║");
                System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
                System.out.println("❌ No se puede construir el árbol sintáctico.");
                System.out.println("   El programa tiene " + errorListener.totalErrores()
                    + " error(es) sintáctico(s) — le faltan piezas (tokens/símbolos) a la");
                System.out.println("   derivación, por lo que no existe un árbol válido que representarla.");
                System.out.println("   Corrija los errores reportados arriba y vuelva a analizar.\n");
                return;
            }

            ArbolVisualizer.mostrarArbolJerarquico(tree, parser);
        } catch (IOException ex) {
            System.err.println("❌ Error leyendo archivo: " + ex.getMessage());
        }
    }
}