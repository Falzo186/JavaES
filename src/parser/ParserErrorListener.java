import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;
import java.util.*;

public class ParserErrorListener extends BaseErrorListener {
    private boolean errores = false;

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        errores = true;
        String mensajeEs = traducirMensaje(recognizer, msg);
        
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

    private String traducirMensaje(Recognizer<?, ?> recognizer, String msg) {
        String esperado = construirEsperados(recognizer);
        String esperadoLimpio = esperado.isEmpty() ? "" : ". Se esperaba: " + esperado;

        // Traducir mensajes de error ANTLR al español
        if (msg.contains("mismatched input")) {
            return extraerDescripcion(msg, "entrada incorrecta") + esperadoLimpio;
        }
        if (msg.contains("extraneous input")) {
            return extraerDescripcion(msg, "entrada innecesaria") + esperadoLimpio;
        }
        if (msg.contains("no viable alternative")) {
            return esperado.isEmpty()
                ? "no hay alternativa viable para esta entrada"
                : "no hay alternativa viable para esta entrada. Se esperaba: " + esperado;
        }
        if (msg.contains("missing")) {
            return extraerDescripcion(msg, "falta") + esperadoLimpio;
        }
        return msg;
    }

    private String extraerDescripcion(String msg, String reemplazo) {
        int inicio = msg.indexOf(' ');
        if (inicio < 0) {
            return reemplazo;
        }
        int separador = msg.indexOf(',', inicio);
        String resto = separador >= 0 ? msg.substring(separador) : "";
        return reemplazo + resto.replace("expecting", "se esperaba");
    }

    private String construirEsperados(Recognizer<?, ?> recognizer) {
        if (!(recognizer instanceof Parser)) {
            return "";
        }

        Parser parser = (Parser) recognizer;
        IntervalSet expected = parser.getExpectedTokens();
        List<String> nombres = new ArrayList<>();

        for (int tokenType : expected.toList()) {
            String nombre = parser.getVocabulary().getDisplayName(tokenType);
            if (nombre == null) {
                continue;
            }
            nombres.add(normalizarToken(nombre));
        }

        if (nombres.isEmpty()) {
            return "";
        }

        return String.join(", ", nombres);
    }

    private String normalizarToken(String nombre) {
        if (nombre == null) {
            return "";
        }

        String limpio = nombre.replace("'", "");
        switch (limpio) {
            case "IDENTIFICADOR": return "identificador";
            case "ENTERO": return "entero";
            case "DECIMAL": return "decimal";
            case "CADENA": return "cadena";
            case "CARACTER": return "caracter";
            case "VERDADERO": return "verdadero";
            case "FALSO": return "falso";
            case "NULO": return "nulo";
            case "PUNTO_Y_COMA": return ";";
            case "COMA": return ",";
            case "PUNTO": return ".";
            case "PARENTESIS_ABIERTO": return "(";
            case "PARENTESIS_CERRADO": return ")";
            case "LLAVE_ABIERTA": return "{";
            case "LLAVE_CERRADA": return "}";
            default: return limpio.toLowerCase();
        }
    }

    public boolean tieneErrores() { return errores; }
}
