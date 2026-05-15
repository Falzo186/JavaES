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
        // Actualizado con token set completo expandido
        switch(tokenId) {
            // Tipos
            case 4: return "ENTERO_TIPO";
            case 5: return "DECIMAL_TIPO";
            case 6: return "CADENA_TIPO";
            case 7: return "BOOLEANO_TIPO";
            case 8: return "VACIO_TIPO";
            case 9: return "CARACTER_TIPO";
            
            // Control de flujo
            case 10: return "SI";
            case 11: return "SINO";
            case 12: return "MIENTRAS";
            case 13: return "PARA";
            case 14: return "HACER";
            case 15: return "CAMBIAR";
            case 16: return "CASO";
            case 17: return "DEFECTO";
            case 18: return "ROMPER";
            case 19: return "CONTINUAR";
            case 20: return "RETORNAR";
            
            // Excepciones
            case 21: return "INTENTAR";
            case 22: return "CAPTURAR";
            case 23: return "FINALMENTE";
            case 24: return "LANZAR";
            
            // Clases y objetos
            case 25: return "CLASE";
            case 26: return "INTERFAZ";
            case 27: return "NUEVO";
            case 28: return "ESTE";
            case 29: return "SUPER";
            
            // Modificadores
            case 30: return "PUBLICO";
            case 31: return "PRIVADO";
            case 32: return "PROTEGIDO";
            case 33: return "ESTATICO";
            case 34: return "FINAL";
            
            // Valores especiales
            case 35: return "VERDADERO";
            case 36: return "FALSO";
            case 37: return "NULO";
            
            // Importación y paquetes
            case 38: return "IMPORTAR";
            case 39: return "PAQUETE";
            
            // Entrada/Salida
            case 40: return "IMPRIMIR";
            case 41: return "LEER";
            
            // Literales
            case 42: return "CADENA";
            case 43: return "CARACTER";
            case 44: return "DECIMAL_CIENTIFICO";
            case 45: return "DECIMAL";
            case 46: return "ENTERO";
            case 47: return "ENTERO_HEX";
            case 48: return "ENTERO_OCTAL";
            
            // Identificador
            case 49: return "IDENTIFICADOR";
            
            // Operadores aritméticos
            case 50: return "INCREMENTO";
            case 51: return "DECREMENTO";
            case 52: return "SUMA";
            case 53: return "RESTA";
            case 54: return "MULTIPLICACION";
            case 55: return "DIVISION";
            case 56: return "MODULO";
            
            // Operadores relacionales
            case 57: return "IGUALDAD";
            case 58: return "DESIGUALDAD";
            case 59: return "MAYOR_IGUAL_QUE";
            case 60: return "MENOR_IGUAL_QUE";
            case 61: return "MAYOR_QUE";
            case 62: return "MENOR_QUE";
            
            // Operadores lógicos
            case 63: return "Y_LOGICO";
            case 64: return "O_LOGICO";
            case 65: return "NEGACION";
            
            // Operadores de asignación
            case 66: return "SUMA_ASIGNACION";
            case 67: return "RESTA_ASIGNACION";
            case 68: return "MULTIPLICACION_ASIGNACION";
            case 69: return "DIVISION_ASIGNACION";
            case 70: return "MODULO_ASIGNACION";
            case 71: return "ASIGNACION";
            
            // Delimitadores
            case 72: return "PARENTESIS_ABIERTO";
            case 73: return "PARENTESIS_CERRADO";
            case 74: return "LLAVE_ABIERTA";
            case 75: return "LLAVE_CERRADA";
            case 76: return "CORCHETE_ABIERTO";
            case 77: return "CORCHETE_CERRADO";
            case 78: return "PUNTO_Y_COMA";
            case 79: return "COMA";
            case 80: return "PUNTO";
            case 81: return "DOS_PUNTOS";
            case 82: return "DOS_PUNTOS_DOBLE";
            case 83: return "FLECHA";
            case 84: return "ARROBA";
            
            // Operadores especiales
            case 85: return "INTERROGACION";
            case 86: return "AMPERSAND";
            case 87: return "PIPE";
            case 88: return "CIRCUNFLEJO";
            case 89: return "TILDE";
            
            // Error
            case 90: return "ERROR_LEXICO";
            case -1: return "EOF";
            default: return null;
        }
    }
}
