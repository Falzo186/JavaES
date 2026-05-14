import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ArbolVisualizer {
    
    public static void mostrarArbolJerarquico(ParseTree tree, Parser parser) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         ÁRBOL SINTÁCTICO                                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        mostrarNodo(tree, parser, "", "");
        System.out.println();
    }
    
    private static void mostrarNodo(ParseTree node, Parser parser, String prefijo, String extension) {
        String nombreNodo = obtenerNombreNodo(node, parser);
        
        System.out.print(prefijo);
        System.out.println(extension + nombreNodo);
        
        for (int i = 0; i < node.getChildCount(); i++) {
            ParseTree hijo = node.getChild(i);
            boolean esUltimo = i == node.getChildCount() - 1;
            
            String nuevoExtension = esUltimo ? "└── " : "├── ";
            String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
            
            mostrarNodo(hijo, parser, nuevoPrefijo, nuevoExtension);
        }
    }
    
    private static String obtenerNombreNodo(ParseTree node, Parser parser) {
        if (node instanceof ParserRuleContext) {
            ParserRuleContext ruleNode = (ParserRuleContext) node;
            int ruleIndex = ruleNode.getRuleIndex();
            if (ruleIndex >= 0 && ruleIndex < parser.getRuleNames().length) {
                String ruleName = parser.getRuleNames()[ruleIndex];
                return ruleName.toUpperCase();
            }
        } else if (node instanceof TerminalNode) {
            TerminalNode terminalNode = (TerminalNode) node;
            Token token = terminalNode.getSymbol();
            int tokenType = token.getType();
            String text = token.getText();
            
            // Mapeo de IDs de tokens a nombres en español
            String tokenName = mapearTokenId(tokenType);
            
            // Si el token es una palabra clave o identificador, mostrar el valor
            if (tokenName != null && (tokenName.equals("IDENTIFICADOR") || 
                tokenName.equals("ENTERO") || tokenName.equals("DECIMAL") || 
                tokenName.equals("CADENA"))) {
                return tokenName + " (" + text + ")";
            } else if (tokenName != null) {
                return tokenName;
            } else {
                return text;
            }
        }
        return "DESCONOCIDO";
    }
    
    private static String mapearTokenId(int tokenId) {
        // Mapeo directo de IDs a nombres en español academico (basado en JavaESLexer.tokens)
        switch(tokenId) {
            case 4: return "ENTERO_TIPO";
            case 5: return "DECIMAL_TIPO";
            case 6: return "CADENA_TIPO";
            case 7: return "BOOLEANO_TIPO";
            case 8: return "VACIO_TIPO";
            case 9: return "SI";
            case 10: return "SINO";
            case 11: return "MIENTRAS";
            case 12: return "RETORNAR";
            case 13: return "CLASE";
            case 14: return "VERDADERO";
            case 15: return "FALSO";
            case 16: return "PUBLICO";
            case 17: return "PRIVADO";
            case 18: return "CADENA";
            case 19: return "ENTERO";
            case 20: return "DECIMAL";
            case 21: return "IDENTIFICADOR";
            case 22: return "SUMA";
            case 23: return "RESTA";
            case 24: return "MULTIPLICACION";
            case 25: return "DIVISION";
            case 26: return "MODULO";
            case 27: return "IGUALDAD";
            case 28: return "DESIGUALDAD";
            case 29: return "MAYOR_QUE";
            case 30: return "MENOR_QUE";
            case 31: return "MAYOR_IGUAL_QUE";
            case 32: return "MENOR_IGUAL_QUE";
            case 33: return "ASIGNACION";
            case 34: return "PUNTO_Y_COMA";
            case 35: return "COMA";
            case 36: return "PARENTESIS_ABIERTO";
            case 37: return "PARENTESIS_CERRADO";
            case 38: return "LLAVE_ABIERTA";
            case 39: return "LLAVE_CERRADA";
            case 40: return "ERROR_LEXICO";
            case -1: return "EOF";
            default: return null;
        }
    }
}
