parser grammar JavaESParser;
options { tokenVocab=JavaESLexer; }

// Regla principal
programa: declaracionGlobal* EOF;

declaracionGlobal: claseDecl
                 | metodoDecl
                 | varGlobalDecl
                 ;

// Declaración de clase
claseDecl: CLASE IDENTIFICADOR LLAVE_ABIERTA miembroClase* LLAVE_CERRADA;

miembroClase: varClaseDecl | metodoClase;

varClaseDecl: tipo IDENTIFICADOR PUNTO_Y_COMA;

metodoClase: tipo IDENTIFICADOR PARENTESIS_ABIERTO parametros? PARENTESIS_CERRADO bloque;

// Declaración de método global
metodoDecl: tipo IDENTIFICADOR PARENTESIS_ABIERTO parametros? PARENTESIS_CERRADO bloque;

parametros: parametro (COMA parametro)*;
parametro: tipo IDENTIFICADOR;

// Variable global
varGlobalDecl: tipo IDENTIFICADOR (ASIGNACION expresion)? PUNTO_Y_COMA;

// Tipos
tipo: ENTERO_TIPO
    | DECIMAL_TIPO
    | CADENA_TIPO
    | BOOLEANO_TIPO
    | VACIO_TIPO
    ;

// Bloque de código
bloque: LLAVE_ABIERTA instruccion* LLAVE_CERRADA;

instruccion: varLocalDecl
           | instruccion_if
           | instruccion_while
           | instruccion_return
           | instruccion_simple
           ;

varLocalDecl: tipo IDENTIFICADOR (ASIGNACION expresion)? PUNTO_Y_COMA;

instruccion_if: SI PARENTESIS_ABIERTO expresion PARENTESIS_CERRADO bloque (SINO bloque)?;

instruccion_while: MIENTRAS PARENTESIS_ABIERTO expresion PARENTESIS_CERRADO bloque;

instruccion_return: RETORNAR expresion? PUNTO_Y_COMA;

instruccion_simple: expresion PUNTO_Y_COMA
                  | bloque
                  ;

// Expresiones
expresion: asignacion;

asignacion: comparacion (ASIGNACION asignacion)?;

comparacion: suma ((IGUALDAD | DESIGUALDAD | MAYOR_QUE | MENOR_QUE | MAYOR_IGUAL_QUE | MENOR_IGUAL_QUE) suma)*;

suma: producto ((SUMA | RESTA) producto)*;

producto: atom ((MULTIPLICACION | DIVISION | MODULO) atom)*;

atom: PARENTESIS_ABIERTO expresion PARENTESIS_CERRADO
    | ENTERO
    | DECIMAL
    | CADENA
    | VERDADERO
    | FALSO
    | IDENTIFICADOR (PARENTESIS_ABIERTO (expresion (COMA expresion)*)? PARENTESIS_CERRADO)?
    ;
