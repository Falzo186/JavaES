import org.antlr.v4.runtime.*;
import java.util.*;

public class ParserErrorListener extends BaseErrorListener {
    private boolean errores = false;

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        errores = true;
        String mensajeEs = traducirMensaje(msg);
        
        System.err.println();
        System.err.println("┌─────────────────────────────────────────────────┐");
        System.err.println("│           ERROR SINTÁCTICO                      │");
        System.err.println("├─────────────────────────────────────────────────┤");
        System.err.printf("│ Línea:       %-38d │\n", line);
        System.err.printf("│ Columna:     %-38d │\n", charPositionInLine + 1);
        System.err.println("├─────────────────────────────────────────────────┤");
        System.err.println("│ Descripción: " + mensajeEs);
        System.err.println("└─────────────────────────────────────────────────┘");
        System.err.println();
    }

    private String traducirMensaje(String msg) {
        // Traducir mensajes de error ANTLR al español
        if (msg.contains("mismatched input")) {
            return msg.replace("mismatched input", "entrada incorrecta")
                      .replace("expecting", "se esperaba");
        }
        if (msg.contains("extraneous input")) {
            return msg.replace("extraneous input", "entrada innecesaria")
                      .replace("expecting", "se esperaba");
        }
        if (msg.contains("no viable alternative")) {
            return "no hay alternativa viable para esta entrada";
        }
        if (msg.contains("missing")) {
            return msg.replace("missing", "falta");
        }
        return msg;
    }

    public boolean tieneErrores() { return errores; }
}
