parser grammar JavaESParser;
options { tokenVocab=JavaESLexer; }

// Regla principal
programa: elementoGlobal* EOF;

elementoGlobal: paqueteDecl
              | importacionDecl
              | claseDecl
              | declaracionGlobal
              | metodoDecl
              ;

paqueteDecl: PAQUETE rutaCalificada PUNTO_Y_COMA;

importacionDecl: IMPORTAR rutaCalificada PUNTO_Y_COMA;

rutaCalificada: IDENTIFICADOR (PUNTO IDENTIFICADOR)*;

// Declaración de clase
claseDecl: modificadores? CLASE IDENTIFICADOR LLAVE_ABIERTA miembroClase* LLAVE_CERRADA;

miembroClase: declaracionVariable | metodoDecl;

declaracionGlobal: declaracionVariable;

declaracionVariable: modificadores? tipo variablesDeclaradas PUNTO_Y_COMA;

variablesDeclaradas: variableDeclarada (COMA variableDeclarada)*;

variableDeclarada: IDENTIFICADOR (ASIGNACION expresion)?;

// Declaración de método global
metodoDecl: modificadores? tipo IDENTIFICADOR PARENTESIS_ABIERTO parametros? PARENTESIS_CERRADO bloque;

modificadores: modificador+;

modificador: PUBLICO
           | PRIVADO
           | PROTEGIDO
           | ESTATICO
           | FINAL
           ;

parametros: parametro (COMA parametro)*;
parametro: tipo IDENTIFICADOR;

// Tipos
tipo: tipoPrimitivo
    | IDENTIFICADOR
    ;

tipoPrimitivo: ENTERO_TIPO
             | DECIMAL_TIPO
             | CADENA_TIPO
             | BOOLEANO_TIPO
             | VACIO_TIPO
             | CARACTER_TIPO
             ;

// Bloque de código
bloque: LLAVE_ABIERTA instruccion* LLAVE_CERRADA;

instruccion: varLocalDecl
           | instruccion_if
           | instruccion_while
           | instruccion_for
           | instruccion_doWhile
           | instruccion_switch
           | instruccion_tryCatch
           | instruccion_return
           | instruccion_break
           | instruccion_continue
           | instruccion_simple
           ;

varLocalDecl: declaracionVariable;

instruccion_if: SI PARENTESIS_ABIERTO expresion PARENTESIS_CERRADO bloque (SINO bloque)?;

instruccion_while: MIENTRAS PARENTESIS_ABIERTO expresion PARENTESIS_CERRADO bloque;

instruccion_for: PARA PARENTESIS_ABIERTO forInicializacion? PUNTO_Y_COMA expresion? PUNTO_Y_COMA expresion? PARENTESIS_CERRADO bloque;

forInicializacion: declaracionFor
                 | listaExpresiones
                 ;

declaracionFor: tipo variablesDeclaradas;

listaExpresiones: expresion (COMA expresion)*;

instruccion_doWhile: HACER bloque MIENTRAS PARENTESIS_ABIERTO expresion PARENTESIS_CERRADO PUNTO_Y_COMA;

instruccion_switch: CAMBIAR PARENTESIS_ABIERTO expresion PARENTESIS_CERRADO LLAVE_ABIERTA casoSwitch* defectoSwitch? LLAVE_CERRADA;

casoSwitch: CASO expresion DOS_PUNTOS instruccion*;

defectoSwitch: DEFECTO DOS_PUNTOS instruccion*;

instruccion_tryCatch: INTENTAR bloque capturarSecuencias finalmenteSecuencia?;

capturarSecuencias: capturarSecuencia+;

capturarSecuencia: CAPTURAR PARENTESIS_ABIERTO parametro PARENTESIS_CERRADO bloque;

finalmenteSecuencia: FINALMENTE bloque;

instruccion_return: RETORNAR expresion? PUNTO_Y_COMA;

instruccion_break: ROMPER PUNTO_Y_COMA;

instruccion_continue: CONTINUAR PUNTO_Y_COMA;

instruccion_simple: expresion PUNTO_Y_COMA
                  | bloque
                  ;

// Expresiones
expresion: asignacion;

asignacion: logicoOr (operadorAsignacion asignacion)?;

operadorAsignacion: ASIGNACION
                  | SUMA_ASIGNACION
                  | RESTA_ASIGNACION
                  | MULTIPLICACION_ASIGNACION
                  | DIVISION_ASIGNACION
                  | MODULO_ASIGNACION
                  ;

logicoOr: logicoAnd (O_LOGICO logicoAnd)*;

logicoAnd: igualdad (Y_LOGICO igualdad)*;

igualdad: relacion ((IGUALDAD | DESIGUALDAD) relacion)*;

relacion: suma ((MAYOR_QUE | MENOR_QUE | MAYOR_IGUAL_QUE | MENOR_IGUAL_QUE) suma)*;

suma: producto ((SUMA | RESTA) producto)*;

producto: unaria ((MULTIPLICACION | DIVISION | MODULO) unaria)*;

unaria: (NEGACION | SUMA | RESTA | INCREMENTO | DECREMENTO)* postfijo;

postfijo: primaria sufijo*;

sufijo: argumentos
      | PUNTO IDENTIFICADOR
      | INCREMENTO
      | DECREMENTO
      ;

primaria: PARENTESIS_ABIERTO expresion PARENTESIS_CERRADO
        | literal
        | invocable
        | nuevaInstancia
        ;

literal: ENTERO
       | DECIMAL
       | CADENA
       | CARACTER
       | VERDADERO
       | FALSO
       | NULO
       ;

invocable: IDENTIFICADOR
         | IMPRIMIR
         | LEER
         | ESTE
         | SUPER
         ;

nuevaInstancia: NUEVO IDENTIFICADOR argumentos?;

argumentos: PARENTESIS_ABIERTO (expresion (COMA expresion)*)? PARENTESIS_CERRADO;
