import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;
import java.util.*;

public class ParserErrorListener extends BaseErrorListener {

    // Si la cantidad de tokens esperados supera este limite, se resume
    // en lugar de listarlos todos (evita mensajes gigantes e ilegibles
    // cuando el parser podria aceptar cualquier inicio de expresion).
    private static final int MAX_TOKENS_LISTADOS = 6;

    private boolean errores = false;
    private int totalErrores = 0;

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        errores = true;
        totalErrores++;
        String mensajeEs = traducirMensaje(recognizer, msg);

        System.err.println();
        System.err.println("+-----------------------------------------------------+");
        System.err.println("|           ERROR SINTACTICO #" + totalErrores);
        System.err.println("+-----------------------------------------------------+");
        System.err.printf("| Linea:       %-38d |\n", line);
        System.err.printf("| Columna:     %-38d |\n", charPositionInLine + 1);
        System.err.println("+-----------------------------------------------------+");
        System.err.println("| Descripcion: " + mensajeEs);
        System.err.println("+-----------------------------------------------------+");
        System.err.println();
    }

    // ------------------------------------------------------------------
    // Antes: se pegaba el fragmento crudo del mensaje de ANTLR (con
    // comillas y llaves sin cerrar de su propia notacion de conjuntos)
    // junto con una segunda lista ya limpia, por lo que el texto final
    // quedaba duplicado y con simbolos sueltos.
    // Ahora: se construye el mensaje SOLO a partir de una categoria en
    // español (que no depende del texto interno de ANTLR) mas la lista
    // de tokens esperados, ya limpia y resumida si es muy larga.
    // ------------------------------------------------------------------
    private String traducirMensaje(Recognizer<?, ?> recognizer, String msg) {
        String categoria = categorizarMensaje(msg);
        String esperado = construirEsperados(recognizer);

        if (esperado.isEmpty()) {
            return categoria;
        }
        return categoria + ". Se esperaba: " + esperado;
    }

    private String categorizarMensaje(String msg) {
        if (msg.contains("mismatched input")) {
            return "entrada incorrecta";
        }
        if (msg.contains("extraneous input")) {
            return "entrada innecesaria (sobra algo aqui)";
        }
        if (msg.contains("no viable alternative")) {
            return "no hay ninguna regla valida para esta entrada";
        }
        if (msg.contains("missing")) {
            return "falta un elemento obligatorio";
        }
        return "error de sintaxis";
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
            String normalizado = normalizarToken(nombre);
            if (!normalizado.isEmpty() && !nombres.contains(normalizado)) {
                nombres.add(normalizado);
            }
        }

        if (nombres.isEmpty()) {
            return "";
        }

        if (nombres.size() > MAX_TOKENS_LISTADOS) {
            List<String> ejemplos = nombres.subList(0, MAX_TOKENS_LISTADOS);
            int restantes = nombres.size() - MAX_TOKENS_LISTADOS;
            return String.join(", ", ejemplos) + " (u otras " + restantes + " opciones mas)";
        }

        return String.join(", ", nombres);
    }

    private String normalizarToken(String nombre) {
        if (nombre == null) {
            return "";
        }

        String limpio = nombre.replace("'", "");
        switch (limpio) {
            case "IDENTIFICADOR": return "un identificador";
            case "ENTERO": return "un entero";
            case "DECIMAL": return "un decimal";
            case "CADENA": return "una cadena";
            case "CARACTER": return "un caracter";
            case "VERDADERO": return "verdadero";
            case "FALSO": return "falso";
            case "NULO": return "nulo";
            case "PUNTO_Y_COMA": return "';'";
            case "COMA": return "','";
            case "PUNTO": return "'.'";
            case "PARENTESIS_ABIERTO": return "'('";
            case "PARENTESIS_CERRADO": return "')'";
            case "LLAVE_ABIERTA": return "'{'";
            case "LLAVE_CERRADA": return "'}'";
            default: return "'" + limpio.toLowerCase() + "'";
        }
    }

    public boolean tieneErrores() { return errores; }
    public int totalErrores() { return totalErrores; }
}