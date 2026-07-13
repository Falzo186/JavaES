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

            if (tokenType == Token.EOF) {
                return "EOF";
            }

            String symbolicName = parser.getVocabulary().getSymbolicName(tokenType);
            String literalName = parser.getVocabulary().getLiteralName(tokenType);

            if (symbolicName == null) {
                return literalName != null ? literalName.replace("'", "") : text;
            }

            if (symbolicName.equals("IDENTIFICADOR")
                || symbolicName.equals("ENTERO")
                || symbolicName.equals("DECIMAL")
                || symbolicName.equals("CADENA")
                || symbolicName.equals("CARACTER")
                || symbolicName.equals("DECIMAL_CIENTIFICO")
                || symbolicName.equals("ENTERO_HEX")
                || symbolicName.equals("ENTERO_OCTAL")) {
                return symbolicName + " (" + text + ")";
            }

            return symbolicName;
        }
        return "DESCONOCIDO";
    }
}
