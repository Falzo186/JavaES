import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArbolVisualizer {

    private static final Set<String> REGLAS_OCULTAS = new HashSet<>(Arrays.asList(
        "elementoGlobal",
        "rutaCalificada",
        "declaracionGlobal",
        "miembroClase",
        "variablesDeclaradas",
        "modificadores",
        "modificador",
        "tipo",
        "tipoPrimitivo",
        "instruccion",
        "varLocalDecl",
        "forInicializacion",
        "declaracionFor",
        "listaExpresiones",
        "capturarSecuencias",
        "finalmenteSecuencia",
        "operadorAsignacion",
        "expresion",
        "asignacion",
        "logicoOr",
        "logicoAnd",
        "igualdad",
        "relacion",
        "suma",
        "producto",
        "unaria",
        "postfijo",
        "primaria",
        "literal",
        "invocable",
        "sufijo",
        "argumentos"
    ));

    public static void mostrarArbolJerarquico(ParseTree tree, Parser parser) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         ÁRBOL SINTÁCTICO                                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        NodoVisual raiz = construir(tree, parser, new ArrayDeque<>());
        imprimir(raiz, "", true, true);
        System.out.println();
    }

    private static NodoVisual construir(ParseTree node, Parser parser, Deque<String> ancestros) {
        if (node instanceof TerminalNode) {
            return construirTerminal((TerminalNode) node, parser);
        }

        ParserRuleContext contexto = (ParserRuleContext) node;
        String regla = nombreRegla(contexto, parser);
        ancestros.push(regla);
        try {
            switch (regla) {
                case "programa":
                    List<NodoVisual> programaHijos = hijosPromovidos(contexto, parser, ancestros);
                    programaHijos.add(nodo("FIN", List.of()));
                    return nodo("PROGRAMA", programaHijos);
                case "paqueteDecl":
                    return nodo("PAQUETE " + textoHijo(contexto, parser, "rutaCalificada"), List.of());
                case "importacionDecl":
                    return nodo("IMPORTACIÓN " + textoHijo(contexto, parser, "rutaCalificada"), List.of());
                case "claseDecl":
                    return construirClase(contexto, parser, ancestros);
                case "declaracionVariable":
                    return construirDeclaracionVariable(contexto, parser, ancestros);
                case "variableDeclarada":
                    return construirVariableDeclarada(contexto, parser);
                case "metodoDecl":
                    return construirMetodo(contexto, parser, ancestros);
                case "parametros":
                    return nodo("PARÁMETROS", hijosPromovidos(contexto, parser, ancestros));
                case "parametro":
                    return construirParametro(contexto, parser);
                case "bloque":
                    return nodo("BLOQUE", hijosPromovidos(contexto, parser, ancestros));
                case "instruccion_if":
                    return construirIf(contexto, parser, ancestros);
                case "instruccion_while":
                    return construirWhile(contexto, parser, ancestros);
                case "instruccion_for":
                    return construirFor(contexto, parser, ancestros);
                case "instruccion_doWhile":
                    return construirDoWhile(contexto, parser, ancestros);
                case "instruccion_switch":
                    return construirSwitch(contexto, parser, ancestros);
                case "casoSwitch":
                    return construirCaso(contexto, parser, ancestros);
                case "defectoSwitch":
                    return nodo("DEFECTO", hijosPromovidos(contexto, parser, ancestros));
                case "instruccion_tryCatch":
                    return construirTryCatch(contexto, parser, ancestros);
                case "capturarSecuencia":
                    return construirCapturarSecuencia(contexto, parser);
                case "finalmenteSecuencia":
                    return construirFinalmenteSecuencia(contexto, parser);
                case "instruccion_return":
                    return construirReturn(contexto, parser);
                case "instruccion_break":
                    return nodo("ROMPER", List.of());
                case "instruccion_continue":
                    return nodo("CONTINUAR", List.of());
                case "instruccion_simple":
                    return construirInstruccionSimple(contexto, parser, ancestros);
                case "nuevaInstancia":
                    return construirNuevaInstancia(contexto, parser);
                case "argumentos":
                    return construirArgumentos(contexto, parser);
                case "literal":
                    return construirLiteral(contexto, parser);
                case "invocable":
                    return construirInvocable(contexto, parser);
                case "expresion":
                    return construirExpresion(contexto, parser);
                case "asignacion":
                    return construirAsignacion(contexto, parser);
                case "operadorAsignacion":
                    return nodo("OPERADOR ASIGNACIÓN: " + resumen(contexto, parser), List.of());
                case "logicoOr":
                    return construirCadenaBinaria(contexto, parser, "logicoAnd", "||");
                case "logicoAnd":
                    return construirCadenaBinaria(contexto, parser, "igualdad", "&&");
                case "igualdad":
                    return construirCadenaBinariaMulti(contexto, parser, "relacion", new String[]{"==", "!="});
                case "relacion":
                    return construirCadenaBinariaMulti(contexto, parser, "suma", new String[]{">", "<", ">=", "<="});
                case "suma":
                    return construirCadenaBinariaMulti(contexto, parser, "producto", new String[]{"+", "-"});
                case "producto":
                    return construirCadenaBinariaMulti(contexto, parser, "unaria", new String[]{"*", "/", "%"});
                case "unaria":
                    return construirUnaria(contexto, parser);
                case "postfijo":
                    return construirPostfijo(contexto, parser);
                case "primaria":
                    return construirPrimaria(contexto, parser);
                case "sufijo":
                    return construirSufijo(contexto, parser);
                default:
                    return nodo(regla.toUpperCase(), hijosPromovidos(contexto, parser, ancestros));
            }
        } finally {
            ancestros.pop();
        }
    }

    private static NodoVisual construirClase(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        String nombre = textoTerminalDirecto(contexto, parser, JavaESParser.IDENTIFICADOR);
        List<NodoVisual> hijos = new ArrayList<>();

        NodoVisual modificadores = construirModificadores(contexto, parser);
        if (modificadores != null) {
            hijos.add(modificadores);
        }

        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "miembroClase"));
        return nodo("CLASE " + nombre, hijos);
    }

    private static NodoVisual construirDeclaracionVariable(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        String tipo = textoHijo(contexto, parser, "tipo");
        String alcance = alcanceVariable(contexto, ancestros);

        List<NodoVisual> hijos = new ArrayList<>();
        NodoVisual modificadores = construirModificadores(contexto, parser);
        if (modificadores != null) {
            hijos.add(modificadores);
        }

        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "variablesDeclaradas"));
        return nodo(alcance + ": " + tipo, hijos);
    }

    private static NodoVisual construirMetodo(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        String nombre = textoTerminalDirecto(contexto, parser, JavaESParser.IDENTIFICADOR);
        String retorno = textoHijo(contexto, parser, "tipo");
        String alcance = ancestros.contains("claseDecl") ? "MÉTODO" : "MÉTODO GLOBAL";

        List<NodoVisual> hijos = new ArrayList<>();
        NodoVisual modificadores = construirModificadores(contexto, parser);
        if (modificadores != null) {
            hijos.add(modificadores);
        }

        hijos.add(nodo("RETORNO: " + retorno, List.of()));

        NodoVisual parametros = construirHijoDirectoPorRegla(contexto, parser, ancestros, "parametros", "PARÁMETROS");
        if (parametros != null) {
            hijos.add(parametros);
        }

        NodoVisual bloque = construirHijoDirectoPorRegla(contexto, parser, ancestros, "bloque", "BLOQUE");
        if (bloque != null) {
            hijos.add(bloque);
        }

        return nodo(alcance + ": " + nombre, hijos);
    }

    // ------------------------------------------------------------------
    // CORRECCIÓN: antes estas condiciones se imprimían como texto plano
    // (resumenHijo/resumen), rompiendo la consistencia visual con el
    // resto del árbol (RETORNAR, ASIGNACIÓN, ARGUMENTO), que siempre
    // anida un subárbol real construido con construirExpresionCompacta.
    // Ahora TODAS las expresiones de control (SI, MIENTRAS, PARA,
    // HACER..MIENTRAS, CAMBIAR, CASO) se construyen igual: se anida el
    // subárbol real de la expresión bajo una etiqueta, en vez de
    // aplanar el texto con resumen().
    // ------------------------------------------------------------------
    private static NodoVisual construirNodoConExpresion(String etiqueta, ParserRuleContext expresion, Parser parser) {
        if (expresion == null) {
            return nodo(etiqueta, List.of());
        }
        return nodo(etiqueta, List.of(construirExpresionCompacta(expresion, parser)));
    }

    private static NodoVisual construirIf(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        hijos.add(construirNodoConExpresion("CONDICIÓN", hijoDirectoPorRegla(contexto, "expresion"), parser));
        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "bloque"));
        return nodo("SI", hijos);
    }

    private static NodoVisual construirWhile(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        hijos.add(construirNodoConExpresion("CONDICIÓN", hijoDirectoPorRegla(contexto, "expresion"), parser));
        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "bloque"));
        return nodo("MIENTRAS", hijos);
    }

    private static NodoVisual construirFor(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        NodoVisual inicializacion = construirHijoDirectoPorRegla(contexto, parser, ancestros, "forInicializacion", "INICIALIZACIÓN");
        if (inicializacion != null) {
            hijos.add(inicializacion);
        }

        List<ParserRuleContext> expresiones = hijosDirectosPorRegla(contexto, "expresion");
        if (!expresiones.isEmpty()) {
            hijos.add(construirNodoConExpresion("CONDICIÓN", expresiones.get(0), parser));
        }
        if (expresiones.size() > 1) {
            hijos.add(construirNodoConExpresion("ACTUALIZACIÓN", expresiones.get(1), parser));
        }

        NodoVisual bloque = construirHijoDirectoPorRegla(contexto, parser, ancestros, "bloque", "BLOQUE");
        if (bloque != null) {
            hijos.add(bloque);
        }

        return nodo("PARA", hijos);
    }

    private static NodoVisual construirDoWhile(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "bloque"));
        hijos.add(construirNodoConExpresion("CONDICIÓN", hijoDirectoPorRegla(contexto, "expresion"), parser));
        return nodo("HACER ... MIENTRAS", hijos);
    }

    private static NodoVisual construirSwitch(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        hijos.add(construirNodoConExpresion("EXPRESIÓN", hijoDirectoPorRegla(contexto, "expresion"), parser));
        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "casoSwitch", "defectoSwitch"));
        return nodo("CAMBIAR", hijos);
    }

    private static NodoVisual construirCaso(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        hijos.add(construirNodoConExpresion("VALOR", hijoDirectoPorRegla(contexto, "expresion"), parser));
        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "instruccion"));
        return nodo("CASO", hijos);
    }

    private static NodoVisual construirTryCatch(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        ParserRuleContext bloque = hijoDirectoPorRegla(contexto, "bloque");
        if (bloque != null) {
            hijos.add(nodo("BLOQUE INTENTO", contenidoBloque(bloque, parser, ancestros)));
        }

        ParserRuleContext capturas = hijoDirectoPorRegla(contexto, "capturarSecuencias");
        if (capturas != null) {
            for (ParserRuleContext captura : hijosDirectosPorRegla(capturas, "capturarSecuencia")) {
                hijos.add(construirCapturarSecuencia(captura, parser));
            }
        }

        ParserRuleContext finalmente = hijoDirectoPorRegla(contexto, "finalmenteSecuencia");
        if (finallyExiste(finalmente)) {
            hijos.add(construirFinalmenteSecuencia(finalmente, parser));
        }

        return nodo("INTENTAR", hijos);
    }

    private static NodoVisual construirReturn(ParserRuleContext contexto, Parser parser) {
        ParserRuleContext expresion = hijoDirectoPorRegla(contexto, "expresion");
        if (expresion == null) {
            return nodo("RETORNAR", List.of());
        }
        return nodo("RETORNAR", List.of(construirExpresionCompacta(expresion, parser)));
    }

    private static NodoVisual construirInstruccionSimple(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        NodoVisual bloque = construirHijoDirectoPorRegla(contexto, parser, ancestros, "bloque", "BLOQUE");
        if (bloque != null) {
            return bloque;
        }

        ParserRuleContext expresion = hijoDirectoPorRegla(contexto, "expresion");
        if (expresion != null) {
            return construirExpresionCompacta(expresion, parser);
        }

        return nodo("INSTRUCCIÓN", List.of());
    }

    private static NodoVisual construirVariableDeclarada(ParserRuleContext contexto, Parser parser) {
        String tipo = textoHijo(contexto, parser, "tipo");
        String nombre = textoTerminalDirecto(contexto, parser, JavaESParser.IDENTIFICADOR);

        List<NodoVisual> hijos = new ArrayList<>();
        if (!tipo.isEmpty()) {
            hijos.add(nodo("TIPO: " + tipo, List.of()));
        }
        if (!nombre.isEmpty()) {
            hijos.add(nodo("NOMBRE: " + nombre, List.of()));
        }

        ParserRuleContext expresion = hijoDirectoPorRegla(contexto, "expresion");
        if (expresion != null) {
            hijos.add(nodo("ASIGNACIÓN", List.of(construirExpresionCompacta(expresion, parser))));
        }

        return nodo("VARIABLE", hijos);
    }

    private static NodoVisual construirParametro(ParserRuleContext contexto, Parser parser) {
        String tipo = textoHijo(contexto, parser, "tipo");
        String nombre = textoTerminalDirecto(contexto, parser, JavaESParser.IDENTIFICADOR);

        List<NodoVisual> hijos = new ArrayList<>();
        if (!tipo.isEmpty()) {
            hijos.add(nodo("TIPO: " + tipo, List.of()));
        }
        if (!nombre.isEmpty()) {
            hijos.add(nodo("NOMBRE: " + nombre, List.of()));
        }

        return nodo("PARÁMETRO", hijos);
    }

    private static NodoVisual construirNuevaInstancia(ParserRuleContext contexto, Parser parser) {
        String tipo = textoTerminalDirecto(contexto, parser, JavaESParser.IDENTIFICADOR);
        List<NodoVisual> hijos = new ArrayList<>();

        ParserRuleContext argumentos = hijoDirectoPorRegla(contexto, "argumentos");
        if (argumentos != null) {
            hijos.add(construirArgumentos(argumentos, parser));
        }

        return nodo("NUEVA INSTANCIA: " + tipo, hijos);
    }

    private static NodoVisual construirArgumentos(ParserRuleContext contexto, Parser parser) {
        List<NodoVisual> hijos = new ArrayList<>();
        for (ParserRuleContext expresion : hijosDirectosPorRegla(contexto, "expresion")) {
            hijos.add(nodo("ARGUMENTO", List.of(construirExpresionCompacta(expresion, parser))));
        }
        return nodo("ARGUMENTOS", hijos);
    }

    private static NodoVisual construirLiteral(ParserRuleContext contexto, Parser parser) {
        if (contexto.getChildCount() == 0) {
            return nodo("OPERANDO", List.of());
        }

        Token token = ((TerminalNode) contexto.getChild(0)).getSymbol();
        String simbolo = parser.getVocabulary().getSymbolicName(token.getType());
        if (simbolo == null) {
            simbolo = token.getText();
        }
        return nodo("OPERANDO: " + simbolo + " (" + token.getText() + ")", List.of());
    }

    private static NodoVisual construirInvocable(ParserRuleContext contexto, Parser parser) {
        String texto = resumen(contexto, parser);
        if (texto.isEmpty()) {
            texto = textoTerminalDirecto(contexto, parser, JavaESParser.IDENTIFICADOR);
        }
        if (texto.isEmpty()) {
            texto = resumen(contexto, parser);
        }

        return nodo("OPERANDO: " + texto, List.of());
    }

    private static NodoVisual construirExpresion(ParserRuleContext contexto, Parser parser) {
        ParserRuleContext asignacion = hijoDirectoPorRegla(contexto, "asignacion");
        if (asignacion == null) {
            return nodo("EXPRESIÓN", List.of());
        }

        return construirExpresionCompacta(asignacion, parser);
    }

    private static NodoVisual construirAsignacion(ParserRuleContext contexto, Parser parser) {
        ParserRuleContext ladoIzquierdo = hijoDirectoPorRegla(contexto, "logicoOr");
        ParserRuleContext operador = hijoDirectoPorRegla(contexto, "operadorAsignacion");
        ParserRuleContext ladoDerecho = null;

        for (int i = 0; i < contexto.getChildCount(); i++) {
            ParseTree hijo = contexto.getChild(i);
            if (hijo instanceof ParserRuleContext && nombreRegla((ParserRuleContext) hijo, parser).equals("asignacion") && hijo != contexto) {
                ladoDerecho = (ParserRuleContext) hijo;
                break;
            }
        }

        if (operador == null) {
            if (ladoIzquierdo == null) {
                return nodo("ASIGNACIÓN", List.of());
            }
            return construirCadenaBinariaCompacta(ladoIzquierdo, parser);
        }

        List<NodoVisual> hijos = new ArrayList<>();
        if (ladoIzquierdo != null) {
            hijos.add(nodo("DESTINO", List.of(construirCadenaBinariaCompacta(ladoIzquierdo, parser))));
        }
        hijos.add(nodo("OPERADOR: " + resumen(operador, parser), List.of()));
        if (ladoDerecho != null) {
            hijos.add(nodo("VALOR", List.of(construirExpresionCompacta(ladoDerecho, parser))));
        }
        return nodo("ASIGNACIÓN", hijos);
    }

    private static NodoVisual construirCadenaBinaria(ParserRuleContext contexto, Parser parser, String siguienteRegla, String operadorEsperado) {
        return construirCadenaBinariaMulti(contexto, parser, siguienteRegla, new String[]{operadorEsperado});
    }

    private static NodoVisual construirCadenaBinariaCompacta(ParserRuleContext contexto, Parser parser) {
        ParserRuleContext actual = contexto;
        while (actual != null) {
            String regla = nombreRegla(actual, parser);
            if ("logicoOr".equals(regla) || "logicoAnd".equals(regla) || "igualdad".equals(regla)
                || "relacion".equals(regla) || "suma".equals(regla) || "producto".equals(regla)
                || "unaria".equals(regla) || "postfijo".equals(regla) || "primaria".equals(regla)
                || "literal".equals(regla) || "invocable".equals(regla) || "nuevaInstancia".equals(regla)) {
                return construir(actual, parser, new ArrayDeque<>());
            }

            ParserRuleContext siguiente = primerHijoRegla(actual, "logicoOr", "logicoAnd", "igualdad", "relacion", "suma", "producto", "unaria", "postfijo", "primaria", "literal", "invocable", "nuevaInstancia");
            if (siguiente == null) {
                break;
            }
            actual = siguiente;
        }

        return construir(contexto, parser, new ArrayDeque<>());
    }

    private static NodoVisual construirCadenaBinariaMulti(ParserRuleContext contexto, Parser parser, String siguienteRegla, String[] operadores) {
        List<NodoVisual> hijos = new ArrayList<>();
        List<ParseTree> elementos = new ArrayList<>();
        for (int i = 0; i < contexto.getChildCount(); i++) {
            ParseTree hijo = contexto.getChild(i);
            if (hijo instanceof ParserRuleContext || hijo instanceof TerminalNode) {
                elementos.add(hijo);
            }
        }

        if (elementos.size() <= 1) {
            ParserRuleContext unico = hijoDirectoPorRegla(contexto, siguienteRegla);
            return unico == null ? nodo("OPERANDO", List.of()) : construirSinEnvolver(unico, parser);
        }

        for (ParseTree elemento : elementos) {
            if (elemento instanceof ParserRuleContext) {
                hijos.add(comoOperando(construirSinEnvolver((ParserRuleContext) elemento, parser)));
            } else {
                String texto = elemento.getText();
                if (texto.isEmpty()) {
                    texto = operadores.length == 0 ? "?" : operadores[0];
                }
                hijos.add(nodo("OPERADOR: " + texto, List.of()));
            }
        }

        return nodo("EXPRESIÓN", hijos);
    }

    private static NodoVisual comoOperando(NodoVisual candidato) {
        return candidato.etiqueta.startsWith("OPERANDO")
            ? candidato
            : nodo("OPERANDO", List.of(candidato));
    }

    private static NodoVisual construirUnaria(ParserRuleContext contexto, Parser parser) {
        List<NodoVisual> hijos = new ArrayList<>();
        boolean tieneOperador = false;
        for (int i = 0; i < contexto.getChildCount(); i++) {
            ParseTree hijo = contexto.getChild(i);
            if (hijo instanceof TerminalNode) {
                String texto = hijo.getText();
                if (!texto.isEmpty()) {
                    tieneOperador = true;
                    hijos.add(nodo("OPERADOR: " + texto, List.of()));
                }
            } else if (hijo instanceof ParserRuleContext) {
                hijos.add(nodo("OPERANDO", List.of(construirSinEnvolver((ParserRuleContext) hijo, parser))));
            }
        }

        if (hijos.isEmpty()) {
            return nodo("OPERANDO", List.of());
        }

        if (!tieneOperador && hijos.size() == 1 && contexto.getChild(0) instanceof ParserRuleContext) {
            return construirSinEnvolver((ParserRuleContext) contexto.getChild(0), parser);
        }

        return nodo("EXPRESIÓN", hijos);
    }

    private static NodoVisual construirPostfijo(ParserRuleContext contexto, Parser parser) {
        ParserRuleContext primaria = hijoDirectoPorRegla(contexto, "primaria");
        NodoVisual base = primaria == null ? nodo("OPERANDO", List.of()) : construirSinEnvolver(primaria, parser);

        List<ParserRuleContext> sufijos = hijosDirectosPorRegla(contexto, "sufijo");
        if (!sufijos.isEmpty()) {
            ParserRuleContext argumentos = hijoDirectoPorRegla(sufijos.get(0), "argumentos");
            if (argumentos != null) {
                String nombre = extraerNombreInvocable(primaria, parser);
                List<NodoVisual> llamadaHijos = new ArrayList<>();
                llamadaHijos.add(nodo("IDENTIFICADOR: " + nombre, List.of()));
                llamadaHijos.add(construirArgumentos(argumentos, parser));
                return nodo("LLAMADA A MÉTODO", llamadaHijos);
            }

            List<NodoVisual> cadena = new ArrayList<>();
            cadena.add(base);
            for (ParserRuleContext sufijo : sufijos) {
                cadena.add(construirSufijo(sufijo, parser));
            }
            return nodo("EXPRESIÓN", cadena);
        }

        return base;
    }

    private static NodoVisual construirPrimaria(ParserRuleContext contexto, Parser parser) {
        ParserRuleContext literal = hijoDirectoPorRegla(contexto, "literal");
        if (literal != null) {
            return construirSinEnvolver(literal, parser);
        }

        ParserRuleContext invocable = hijoDirectoPorRegla(contexto, "invocable");
        if (invocable != null) {
            return construirSinEnvolver(invocable, parser);
        }

        ParserRuleContext nuevaInstancia = hijoDirectoPorRegla(contexto, "nuevaInstancia");
        if (nuevaInstancia != null) {
            return construirNuevaInstancia(nuevaInstancia, parser);
        }

        ParserRuleContext expresion = hijoDirectoPorRegla(contexto, "expresion");
        if (expresion != null) {
            return nodo("AGRUPACIÓN", List.of(construirSinEnvolver(expresion, parser)));
        }

        return nodo("OPERANDO", List.of());
    }

    private static NodoVisual construirSufijo(ParserRuleContext contexto, Parser parser) {
        ParserRuleContext argumentos = hijoDirectoPorRegla(contexto, "argumentos");
        if (argumentos != null) {
            return nodo("ARGUMENTOS", construirArgumentos(argumentos, parser).hijos);
        }

        String texto = resumen(contexto, parser);
        if (texto.isEmpty()) {
            texto = contexto.getText();
        }
        return nodo("SUFIJO: " + texto, List.of());
    }

    private static String extraerNombreInvocable(ParserRuleContext primaria, Parser parser) {
        if (primaria == null) {
            return "";
        }

        ParserRuleContext invocable = hijoDirectoPorRegla(primaria, "invocable");
        if (invocable != null) {
            String nombre = textoTerminalDirecto(invocable, parser, JavaESParser.IDENTIFICADOR);
            if (!nombre.isEmpty()) {
                return nombre;
            }
            return resumen(invocable, parser);
        }

        return resumen(primaria, parser);
    }

    private static NodoVisual construirExpresionCompacta(ParserRuleContext contexto, Parser parser) {
        if (contexto == null) {
            return nodo("EXPRESIÓN", List.of());
        }

        String regla = nombreRegla(contexto, parser);
        switch (regla) {
            case "expresion": {
                ParserRuleContext asignacion = hijoDirectoPorRegla(contexto, "asignacion");
                return asignacion == null ? nodo("EXPRESIÓN", List.of()) : construirExpresionCompacta(asignacion, parser);
            }
            case "asignacion":
                return construirAsignacion(contexto, parser);
            case "logicoOr":
                return construirCadenaBinariaMulti(contexto, parser, "logicoAnd", new String[]{"||"});
            case "logicoAnd":
                return construirCadenaBinariaMulti(contexto, parser, "igualdad", new String[]{"&&"});
            case "igualdad":
                return construirCadenaBinariaMulti(contexto, parser, "relacion", new String[]{"==", "!="});
            case "relacion":
                return construirCadenaBinariaMulti(contexto, parser, "suma", new String[]{">", "<", ">=", "<="});
            case "suma":
                return construirCadenaBinariaMulti(contexto, parser, "producto", new String[]{"+", "-"});
            case "producto":
                return construirCadenaBinariaMulti(contexto, parser, "unaria", new String[]{"*", "/", "%"});
            case "unaria":
                return construirUnaria(contexto, parser);
            case "postfijo":
                return construirPostfijo(contexto, parser);
            case "primaria":
                return construirPrimaria(contexto, parser);
            case "literal":
                return construirLiteral(contexto, parser);
            case "invocable":
                return construirInvocable(contexto, parser);
            case "nuevaInstancia":
                return construirNuevaInstancia(contexto, parser);
            default:
                return construir(contexto, parser, new ArrayDeque<>());
        }
    }

    private static NodoVisual construirCapturarSecuencia(ParserRuleContext contexto, Parser parser) {
        List<NodoVisual> hijos = new ArrayList<>();
        ParserRuleContext parametro = hijoDirectoPorRegla(contexto, "parametro");
        if (parametro != null) {
            hijos.add(construirParametro(parametro, parser));
        }

        ParserRuleContext bloque = hijoDirectoPorRegla(contexto, "bloque");
        if (bloque != null) {
            hijos.add(nodo("BLOQUE CAPTURA", contenidoBloque(bloque, parser, new ArrayDeque<>())));
        }

        return nodo("CAPTURA", hijos);
    }

    private static NodoVisual construirFinalmenteSecuencia(ParserRuleContext contexto, Parser parser) {
        ParserRuleContext bloque = hijoDirectoPorRegla(contexto, "bloque");
        if (bloque == null) {
            return nodo("FINALLY", List.of());
        }

        return nodo("FINALLY", List.of(nodo("BLOQUE FINAL", contenidoBloque(bloque, parser, new ArrayDeque<>()))));
    }

    private static NodoVisual construirSinEnvolver(ParserRuleContext contexto, Parser parser) {
        if (contexto == null) {
            return nodo("OPERANDO", List.of());
        }

        String regla = nombreRegla(contexto, parser);
        switch (regla) {
            case "expresion":
                return construirExpresionCompacta(contexto, parser);
            case "asignacion":
                return construirAsignacion(contexto, parser);
            case "logicoOr":
            case "logicoAnd":
            case "igualdad":
            case "relacion":
            case "suma":
            case "producto":
                return construirExpresionCompacta(contexto, parser);
            case "unaria":
                return construirUnaria(contexto, parser);
            case "postfijo":
                return construirPostfijo(contexto, parser);
            case "primaria":
                return construirPrimaria(contexto, parser);
            case "literal":
                return construirLiteral(contexto, parser);
            case "invocable":
                return construirInvocable(contexto, parser);
            case "nuevaInstancia":
                return construirNuevaInstancia(contexto, parser);
            default:
                return construir(contexto, parser, new ArrayDeque<>());
        }
    }

    private static boolean finallyExiste(ParserRuleContext contexto) {
        return contexto != null;
    }

    private static List<NodoVisual> contenidoBloque(ParserRuleContext bloque, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        for (int i = 0; i < bloque.getChildCount(); i++) {
            ParseTree hijo = bloque.getChild(i);
            if (!(hijo instanceof ParserRuleContext)) {
                continue;
            }

            ParserRuleContext contextoHijo = (ParserRuleContext) hijo;
            if ("instruccion".equals(nombreRegla(contextoHijo, parser))) {
                hijos.addAll(construirHijosPromovidos(contextoHijo, parser, ancestros));
            } else {
                hijos.add(construir(contextoHijo, parser, ancestros));
            }
        }
        return hijos;
    }

    private static ParserRuleContext primerHijoRegla(ParserRuleContext contexto, String... reglas) {
        for (String regla : reglas) {
            ParserRuleContext hijo = hijoDirectoPorRegla(contexto, regla);
            if (hijo != null) {
                return hijo;
            }
        }
        return null;
    }

    private static NodoVisual construirModificadores(ParserRuleContext contexto, Parser parser) {
        ParserRuleContext modificadores = hijoDirectoPorRegla(contexto, "modificadores");
        if (modificadores == null) {
            return null;
        }

        List<String> nombres = new ArrayList<>();
        for (ParserRuleContext modificador : hijosDirectosPorRegla(modificadores, "modificador")) {
            String texto = resumen(modificador, parser);
            if (!texto.isEmpty()) {
                nombres.add(texto);
            }
        }

        if (nombres.isEmpty()) {
            String resumen = resumen(modificadores, parser);
            if (!resumen.isEmpty()) {
                nombres.add(resumen);
            }
        }

        return nombres.isEmpty() ? null : nodo("MODIFICADORES: " + String.join(" ", nombres), List.of());
    }

    private static NodoVisual construirHijoDirectoPorRegla(ParserRuleContext contexto, Parser parser, Deque<String> ancestros, String regla, String etiqueta) {
        ParserRuleContext hijo = hijoDirectoPorRegla(contexto, regla);
        if (hijo == null) {
            return null;
        }

        List<NodoVisual> hijos = construirHijosPromovidos(hijo, parser, ancestros);
        if ("BLOQUE".equals(etiqueta) || "PARÁMETROS".equals(etiqueta)) {
            return nodo(etiqueta, hijos);
        }
        return nodo(etiqueta + ": " + resumen(hijo, parser), hijos);
    }

    private static NodoVisual nodoConHijosPromovidos(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        return nodo(nombreRegla(contexto, parser).toUpperCase(), construirHijosPromovidos(contexto, parser, ancestros));
    }

    private static List<NodoVisual> hijosPromovidos(ParseTree nodo, Parser parser, Deque<String> ancestros, String... reglasVisibles) {
        Set<String> reglas = new HashSet<>(Arrays.asList(reglasVisibles));
        List<NodoVisual> hijos = new ArrayList<>();

        if (!(nodo instanceof ParserRuleContext)) {
            return hijos;
        }

        ParserRuleContext contexto = (ParserRuleContext) nodo;
        for (int i = 0; i < contexto.getChildCount(); i++) {
            ParseTree hijo = contexto.getChild(i);
            if (hijo instanceof ParserRuleContext) {
                ParserRuleContext reglaHijo = (ParserRuleContext) hijo;
                String nombre = nombreRegla(reglaHijo, parser);
                if (REGLAS_OCULTAS.contains(nombre)) {
                    hijos.addAll(construirHijosPromovidos(reglaHijo, parser, ancestros));
                } else if (reglas.isEmpty() || reglas.contains(nombre)) {
                    hijos.add(construir(reglaHijo, parser, ancestros));
                } else {
                    hijos.add(construir(reglaHijo, parser, ancestros));
                }
            }
        }

        return hijos;
    }

    private static List<NodoVisual> construirHijosPromovidos(ParseTree nodo, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        if (!(nodo instanceof ParserRuleContext)) {
            return hijos;
        }

        ParserRuleContext contexto = (ParserRuleContext) nodo;
        for (int i = 0; i < contexto.getChildCount(); i++) {
            ParseTree hijo = contexto.getChild(i);
            if (hijo instanceof ParserRuleContext) {
                ParserRuleContext reglaHijo = (ParserRuleContext) hijo;
                String nombreHijo = nombreRegla(reglaHijo, parser);
                if (REGLAS_OCULTAS.contains(nombreHijo)) {
                    hijos.addAll(construirHijosPromovidos(reglaHijo, parser, ancestros));
                } else {
                    hijos.add(construir(reglaHijo, parser, ancestros));
                }
            }
        }

        return hijos;
    }

    private static ParserRuleContext hijoDirectoPorRegla(ParserRuleContext contexto, String regla) {
        for (int i = 0; i < contexto.getChildCount(); i++) {
            ParseTree hijo = contexto.getChild(i);
            if (hijo instanceof ParserRuleContext && nombreRegla((ParserRuleContext) hijo, null).equals(regla)) {
                return (ParserRuleContext) hijo;
            }
        }
        return null;
    }

    private static List<ParserRuleContext> hijosDirectosPorRegla(ParserRuleContext contexto, String regla) {
        List<ParserRuleContext> hijos = new ArrayList<>();
        for (int i = 0; i < contexto.getChildCount(); i++) {
            ParseTree hijo = contexto.getChild(i);
            if (hijo instanceof ParserRuleContext && nombreRegla((ParserRuleContext) hijo, null).equals(regla)) {
                hijos.add((ParserRuleContext) hijo);
            }
        }
        return hijos;
    }

    private static String nombreRegla(ParserRuleContext contexto, Parser parser) {
        if (parser == null) {
            String simple = contexto.getClass().getSimpleName().replace("Context", "");
            if (simple.isEmpty()) {
                return simple;
            }
            return Character.toLowerCase(simple.charAt(0)) + simple.substring(1);
        }

        int indice = contexto.getRuleIndex();
        if (indice >= 0 && indice < parser.getRuleNames().length) {
            return parser.getRuleNames()[indice];
        }
        return contexto.getClass().getSimpleName().replace("Context", "");
    }

    private static NodoVisual construirTerminal(TerminalNode nodoTerminal, Parser parser) {
        Token token = nodoTerminal.getSymbol();
        if (token.getType() == Token.EOF) {
            return nodo("FIN", List.of());
        }

        String texto = token.getText();
        String simbolo = parser.getVocabulary().getSymbolicName(token.getType());

        if (simbolo == null) {
            String literal = parser.getVocabulary().getLiteralName(token.getType());
            if (literal != null) {
                simbolo = literal.replace("'", "");
            } else {
                simbolo = texto;
            }
        }

        if (simbolo.equals("IDENTIFICADOR")
            || simbolo.equals("ENTERO")
            || simbolo.equals("DECIMAL")
            || simbolo.equals("CADENA")
            || simbolo.equals("CARACTER")
            || simbolo.equals("VERDADERO")
            || simbolo.equals("FALSO")
            || simbolo.equals("NULO")) {
            return nodo(simbolo + " (" + texto + ")", List.of());
        }

        return nodo(simbolo, List.of());
    }

    private static String alcanceVariable(ParserRuleContext contexto, Deque<String> ancestros) {
        String padre = contexto.getParent() instanceof ParserRuleContext
            ? nombreRegla((ParserRuleContext) contexto.getParent(), null)
            : "";

        if ("miembroClase".equals(padre)) {
            return "ATRIBUTO";
        }
        if ("declaracionGlobal".equals(padre)) {
            return "VARIABLE GLOBAL";
        }
        return "VARIABLE LOCAL";
    }

    private static String textoTerminalDirecto(ParserRuleContext contexto, Parser parser, int tokenType) {
        for (int i = 0; i < contexto.getChildCount(); i++) {
            ParseTree hijo = contexto.getChild(i);
            if (hijo instanceof TerminalNode) {
                Token token = ((TerminalNode) hijo).getSymbol();
                if (token.getType() == tokenType) {
                    return token.getText();
                }
            }
        }
        return "";
    }

    private static String textoHijo(ParserRuleContext contexto, Parser parser, String regla) {
        ParserRuleContext hijo = hijoDirectoPorRegla(contexto, regla);
        return hijo == null ? "" : resumen(hijo, parser);
    }

    private static String resumenHijo(ParserRuleContext contexto, Parser parser, String regla) {
        ParserRuleContext hijo = hijoDirectoPorRegla(contexto, regla);
        return hijo == null ? "" : resumen(hijo, parser);
    }

    private static String resumen(ParseTree nodo, Parser parser) {
        List<String> partes = new ArrayList<>();
        recolectarTexto(nodo, parser, partes);
        return normalizarTexto(String.join(" ", partes));
    }

    private static void recolectarTexto(ParseTree nodo, Parser parser, List<String> partes) {
        if (nodo instanceof TerminalNode) {
            Token token = ((TerminalNode) nodo).getSymbol();
            if (token.getType() != Token.EOF) {
                partes.add(token.getText());
            }
            return;
        }

        for (int i = 0; i < nodo.getChildCount(); i++) {
            recolectarTexto(nodo.getChild(i), parser, partes);
        }
    }

    private static String normalizarTexto(String texto) {
        String limpio = texto.replaceAll("\\s+", " ").trim();
        limpio = limpio.replaceAll("\\s+([,;:)\\]\\}])", "$1");
        limpio = limpio.replaceAll("([\\(\\[\\{])\\s+", "$1");
        limpio = limpio.replaceAll("\\s*\\.\\s*", ".");
        limpio = limpio.replaceAll("\\s*::\\s*", "::");
        limpio = limpio.replaceAll("\\s*->\\s*", "->");
        return limpio;
    }

    private static void imprimir(NodoVisual nodo, String prefijo, boolean esUltimo, boolean esRaiz) {
        if (esRaiz) {
            System.out.println(nodo.etiqueta);
        } else {
            System.out.println(prefijo + (esUltimo ? "└── " : "├── ") + nodo.etiqueta);
        }

        for (int i = 0; i < nodo.hijos.size(); i++) {
            NodoVisual hijo = nodo.hijos.get(i);
            boolean ultimo = i == nodo.hijos.size() - 1;
            String nuevoPrefijo = esRaiz ? "" : prefijo + (esUltimo ? "    " : "│   ");
            imprimir(hijo, nuevoPrefijo, ultimo, false);
        }
    }

    private static NodoVisual nodo(String etiqueta, List<NodoVisual> hijos) {
        return new NodoVisual(etiqueta, hijos);
    }

    private static final class NodoVisual {
        private final String etiqueta;
        private final List<NodoVisual> hijos;

        private NodoVisual(String etiqueta, List<NodoVisual> hijos) {
            this.etiqueta = etiqueta;
            this.hijos = hijos;
        }
    }
}