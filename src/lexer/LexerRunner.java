import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LexerRunner {
    public static void ejecutarLexico(String rutaArchivo) {
        try {
            String texto = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            JavaESLexer lexer = new JavaESLexer(CharStreams.fromString(texto));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new LexerErrorListener());
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            tokens.fill();
            
            System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
            System.out.println("║         ANÁLISIS LÉXICO - TOKENS DETECTADOS                 ║");
            System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
            
            System.out.println("┌──────────────────────────┬──────────────────┬────────┬────────┐");
            System.out.println("│ TOKEN                    │ LEXEMA           │ LÍNEA  │ COLUMNA│");
            System.out.println("├──────────────────────────┼──────────────────┼────────┼────────┤");
            
            boolean hayErrorLexico = false;
            StringBuilder reporteErrores = new StringBuilder();

            for (Token t : tokens.getTokens()) {
                if (t.getType() == -1) break; // EOF
                String nombreToken = JavaESLexer.VOCABULARY.getSymbolicName(t.getType());
                String lexema = t.getText();
                int linea = t.getLine();
                int columna = t.getCharPositionInLine() + 1;

                if (nombreToken == null) nombreToken = "DESCONOCIDO";

                if (t.getType() == JavaESLexer.ERROR_LEXICO) {
                    hayErrorLexico = true;
                    reporteErrores.append(String.format("Error léxico: carácter inválido '%s' en línea %d, columna %d.%n", lexema, linea, columna));
                }

                // Limitar lexema a 16 caracteres
                if (lexema.length() > 16) {
                    lexema = lexema.substring(0, 13) + "...";
                }

                System.out.printf("│ %-24s │ %-16s │ %6d │ %6d │\n", 
                    nombreToken, lexema, linea, columna);
            }
            System.out.println("└──────────────────────────┴──────────────────┴────────┴────────┘\n");

            if (hayErrorLexico) {
                System.err.println("┌─────────────────────────────────────────────────┐");
                System.err.println("│               ERROR LÉXICO DETECTADO             │");
                System.err.println("├─────────────────────────────────────────────────┤");
                for (String lineaError : reporteErrores.toString().split("\n")) {
                    if (lineaError.isEmpty()) continue;
                    System.err.printf("│ %-47s │%n", lineaError);
                }
                System.err.println("└─────────────────────────────────────────────────┘");
            }
            
        } catch (IOException ex) {
            System.err.println("❌ Error leyendo archivo: " + ex.getMessage());
        }
    }
}
