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
        String alcance = alcanceVariable(ancestros);

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

    private static NodoVisual construirIf(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        hijos.add(nodo("CONDICIÓN: " + resumenHijo(contexto, parser, "expresion"), List.of()));
        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "bloque"));
        return nodo("SI", hijos);
    }

    private static NodoVisual construirWhile(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        hijos.add(nodo("CONDICIÓN: " + resumenHijo(contexto, parser, "expresion"), List.of()));
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
            hijos.add(nodo("CONDICIÓN: " + resumen(expresiones.get(0), parser), List.of()));
        }
        if (expresiones.size() > 1) {
            hijos.add(nodo("ACTUALIZACIÓN: " + resumen(expresiones.get(1), parser), List.of()));
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
        hijos.add(nodo("CONDICIÓN: " + resumenHijo(contexto, parser, "expresion"), List.of()));
        return nodo("HACER ... MIENTRAS", hijos);
    }

    private static NodoVisual construirSwitch(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        hijos.add(nodo("EXPRESIÓN: " + resumenHijo(contexto, parser, "expresion"), List.of()));
        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "casoSwitch", "defectoSwitch"));
        return nodo("CAMBIAR", hijos);
    }

    private static NodoVisual construirCaso(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "instruccion"));
        return nodo("CASO: " + resumenHijo(contexto, parser, "expresion"), hijos);
    }

    private static NodoVisual construirTryCatch(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        List<NodoVisual> hijos = new ArrayList<>();
        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "bloque"));
        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "capturarSecuencias"));
        hijos.addAll(hijosPromovidos(contexto, parser, ancestros, "finalmenteSecuencia"));
        return nodo("INTENTAR", hijos);
    }

    private static NodoVisual construirReturn(ParserRuleContext contexto, Parser parser) {
        String expresion = resumenHijo(contexto, parser, "expresion");
        if (expresion.isEmpty()) {
            return nodo("RETORNAR", List.of());
        }
        return nodo("RETORNAR: " + expresion, List.of());
    }

    private static NodoVisual construirInstruccionSimple(ParserRuleContext contexto, Parser parser, Deque<String> ancestros) {
        NodoVisual bloque = construirHijoDirectoPorRegla(contexto, parser, ancestros, "bloque", "BLOQUE");
        if (bloque != null) {
            return bloque;
        }

        String expresion = resumenHijo(contexto, parser, "expresion");
        return nodo("EXPRESIÓN: " + expresion, List.of());
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
            hijos.add(nodo("ASIGNACIÓN", List.of(construir(expresion, parser, new ArrayDeque<>()))));
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
            hijos.add(nodo("ARGUMENTO", List.of(construir(expresion, parser, new ArrayDeque<>()))));
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

        return nodo("EXPRESIÓN", List.of(construir(asignacion, parser, new ArrayDeque<>())));
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
            return construir(ladoIzquierdo, parser, new ArrayDeque<>());
        }

        List<NodoVisual> hijos = new ArrayList<>();
        if (ladoIzquierdo != null) {
            hijos.add(nodo("DESTINO", List.of(construir(ladoIzquierdo, parser, new ArrayDeque<>()))));
        }
        hijos.add(nodo("OPERADOR: " + resumen(operador, parser), List.of()));
        if (ladoDerecho != null) {
            hijos.add(nodo("VALOR", List.of(construir(ladoDerecho, parser, new ArrayDeque<>()))));
        }
        return nodo("ASIGNACIÓN", hijos);
    }

    private static NodoVisual construirCadenaBinaria(ParserRuleContext contexto, Parser parser, String siguienteRegla, String operadorEsperado) {
        return construirCadenaBinariaMulti(contexto, parser, siguienteRegla, new String[]{operadorEsperado});
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
            return unico == null ? nodo("EXPRESIÓN", List.of()) : construir(unico, parser, new ArrayDeque<>());
        }

        for (ParseTree elemento : elementos) {
            if (elemento instanceof ParserRuleContext) {
                hijos.add(nodo("OPERANDO", List.of(construir((ParserRuleContext) elemento, parser, new ArrayDeque<>()))));
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

    private static NodoVisual construirUnaria(ParserRuleContext contexto, Parser parser) {
        List<NodoVisual> hijos = new ArrayList<>();
        for (int i = 0; i < contexto.getChildCount(); i++) {
            ParseTree hijo = contexto.getChild(i);
            if (hijo instanceof TerminalNode) {
                String texto = hijo.getText();
                if (!texto.isEmpty()) {
                    hijos.add(nodo("OPERADOR: " + texto, List.of()));
                }
            } else if (hijo instanceof ParserRuleContext) {
                hijos.add(nodo("OPERANDO", List.of(construir((ParserRuleContext) hijo, parser, new ArrayDeque<>()))));
            }
        }

        if (hijos.isEmpty()) {
            return nodo("OPERANDO", List.of());
        }

        return nodo("EXPRESIÓN", hijos);
    }

    private static NodoVisual construirPostfijo(ParserRuleContext contexto, Parser parser) {
        ParserRuleContext primaria = hijoDirectoPorRegla(contexto, "primaria");
        NodoVisual base = primaria == null ? nodo("OPERANDO", List.of()) : construirPrimaria(primaria, parser);

        List<ParserRuleContext> sufijos = hijosDirectosPorRegla(contexto, "sufijo");
        if (!sufijos.isEmpty()) {
            ParserRuleContext argumentos = hijoDirectoPorRegla(sufijos.get(0), "argumentos");
            if (argumentos != null) {
                String nombre = extraerNombreInvocable(primaria, parser);
                List<NodoVisual> llamadaHijos = new ArrayList<>();
                llamadaHijos.add(base);
                llamadaHijos.add(construirArgumentos(argumentos, parser));
                return nodo("LLAMADA A MÉTODO: " + nombre, llamadaHijos);
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
            return construir(literal, parser, new ArrayDeque<>());
        }

        ParserRuleContext invocable = hijoDirectoPorRegla(contexto, "invocable");
        if (invocable != null) {
            return construir(invocable, parser, new ArrayDeque<>());
        }

        ParserRuleContext nuevaInstancia = hijoDirectoPorRegla(contexto, "nuevaInstancia");
        if (nuevaInstancia != null) {
            return construirNuevaInstancia(nuevaInstancia, parser);
        }

        ParserRuleContext expresion = hijoDirectoPorRegla(contexto, "expresion");
        if (expresion != null) {
            return nodo("AGRUPACIÓN", List.of(construir(expresion, parser, new ArrayDeque<>())));
        }

        return nodo("OPERANDO", List.of());
    }

    private static NodoVisual construirSufijo(ParserRuleContext contexto, Parser parser) {
        if (hijoDirectoPorRegla(contexto, "argumentos") != null) {
            return nodo("LLAMADA", List.of(construirArgumentos(hijoDirectoPorRegla(contexto, "argumentos"), parser)));
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

    private static String alcanceVariable(Deque<String> ancestros) {
        if (ancestros.contains("claseDecl")) {
            return "ATRIBUTO";
        }
        if (ancestros.contains("bloque") || ancestros.contains("instruccion_for") || ancestros.contains("instruccion_if") || ancestros.contains("instruccion_while") || ancestros.contains("instruccion_doWhile") || ancestros.contains("instruccion_tryCatch") || ancestros.contains("instruccion_switch")) {
            return "VARIABLE LOCAL";
        }
        return "VARIABLE GLOBAL";
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
