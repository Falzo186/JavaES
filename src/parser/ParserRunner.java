import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParserRunner {
    public static void ejecutarParser(String rutaArchivo) {
        try {
            String texto = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
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

    public static void mostrarArbol(String rutaArchivo) {
        try {
            String texto = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            JavaESLexer lexer = new JavaESLexer(CharStreams.fromString(texto));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaESParser parser = new JavaESParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new ParserErrorListener());
            ParseTree tree = parser.programa();
            
            ArbolVisualizer.mostrarArbolJerarquico(tree, parser);
        } catch (IOException ex) {
            System.err.println("❌ Error leyendo archivo: " + ex.getMessage());
        }
    }
}
