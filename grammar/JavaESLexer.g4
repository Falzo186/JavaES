lexer grammar JavaESLexer;

// =====================================================
// COMENTARIOS Y ESPACIOS EN BLANCO
// =====================================================

LINE_COMMENT    : '//' ~[\r\n]* -> skip;
BLOCK_COMMENT   : '/*' .*? '*/' -> skip;
WS              : [ \t\r\n]+ -> skip;

// =====================================================
// PALABRAS RESERVADAS - TIPOS
// =====================================================

ENTERO_TIPO     : 'entero';
DECIMAL_TIPO    : 'decimal';
CADENA_TIPO     : 'cadena';
BOOLEANO_TIPO   : 'booleano';
VACIO_TIPO      : 'vacio';
CARACTER_TIPO   : 'caracter';

// =====================================================
// PALABRAS RESERVADAS - CONTROL DE FLUJO
// =====================================================

SI              : 'si';
SINO            : 'sino';
MIENTRAS        : 'mientras';
PARA            : 'para';
HACER           : 'hacer';
CAMBIAR         : 'cambiar';
CASO            : 'caso';
DEFECTO         : 'defecto';
ROMPER          : 'romper';
CONTINUAR       : 'continuar';
RETORNAR        : 'retornar';

// =====================================================
// PALABRAS RESERVADAS - EXCEPCIONES
// =====================================================

INTENTAR        : 'intentar';
CAPTURAR        : 'capturar';
FINALMENTE      : 'finalmente';
LANZAR          : 'lanzar';

// =====================================================
// PALABRAS RESERVADAS - CLASES Y OBJETOS
// =====================================================

CLASE           : 'clase';
INTERFAZ        : 'interfaz';
NUEVO           : 'nuevo';
ESTE            : 'este';
SUPER           : 'super';

// =====================================================
// PALABRAS RESERVADAS - MODIFICADORES
// =====================================================

PUBLICO         : 'publico';
PRIVADO         : 'privado';
PROTEGIDO       : 'protegido';
ESTATICO        : 'estatico';
FINAL           : 'final';

// =====================================================
// PALABRAS RESERVADAS - VALORES ESPECIALES
// =====================================================

VERDADERO       : 'verdadero';
FALSO           : 'falso';
NULO            : 'nulo';

// =====================================================
// PALABRAS RESERVADAS - IMPORTACION Y PAQUETES
// =====================================================

IMPORTAR        : 'importar';
PAQUETE         : 'paquete';

// =====================================================
// PALABRAS RESERVADAS - ENTRADA SALIDA
// =====================================================

IMPRIMIR        : 'imprimir';
LEER            : 'leer';

// =====================================================
// LITERALES - CADENAS Y CARACTERES
// =====================================================

fragment ESC    : '\\' . ;
CADENA          : '"' ( ~["\\\r\n] | ESC )* '"';
CARACTER        : '\'' ( ~['\\\r\n] | ESC ) '\'';

// =====================================================
// LITERALES - NUMEROS
// =====================================================

DECIMAL_CIENTIFICO : [0-9]+ '.' [0-9]+ ('e'|'E') ('+'|'-')? [0-9]+;
DECIMAL         : [0-9]+ '.' [0-9]+;
ENTERO          : [0-9]+;
ENTERO_HEX      : '0x' [0-9a-fA-F]+;
ENTERO_OCTAL    : '0' [0-7]+;

// =====================================================
// IDENTIFICADORES
// =====================================================

IDENTIFICADOR   : [a-zA-Z_\u00C0-\u017F][a-zA-Z0-9_\u00C0-\u017F]*;

// =====================================================
// OPERADORES ARITMETICOS
// =====================================================

INCREMENTO      : '++';
DECREMENTO      : '--';
SUMA            : '+';
RESTA           : '-';
MULTIPLICACION  : '*';
DIVISION        : '/';
MODULO          : '%';

// =====================================================
// OPERADORES RELACIONALES
// =====================================================

IGUALDAD        : '==';
DESIGUALDAD     : '!=';
MAYOR_IGUAL_QUE : '>=';
MENOR_IGUAL_QUE : '<=';
MAYOR_QUE       : '>';
MENOR_QUE       : '<';

// =====================================================
// OPERADORES LOGICOS
// =====================================================

Y_LOGICO        : '&&';
O_LOGICO        : '||';
NEGACION        : '!';

// =====================================================
// OPERADORES DE ASIGNACION
// =====================================================

SUMA_ASIGNACION              : '+=';
RESTA_ASIGNACION             : '-=';
MULTIPLICACION_ASIGNACION    : '*=';
DIVISION_ASIGNACION          : '/=';
MODULO_ASIGNACION            : '%=';
ASIGNACION                   : '=';

// =====================================================
// DELIMITADORES - PARENTESIS Y LLAVES
// =====================================================

PARENTESIS_ABIERTO   : '(';
PARENTESIS_CERRADO   : ')';
LLAVE_ABIERTA        : '{';
LLAVE_CERRADA        : '}';
CORCHETE_ABIERTO     : '[';
CORCHETE_CERRADO     : ']';

// =====================================================
// DELIMITADORES - PUNTUACION
// =====================================================

PUNTO_Y_COMA    : ';';
COMA            : ',';
PUNTO           : '.';
DOS_PUNTOS      : ':';
DOS_PUNTOS_DOBLE : '::';
FLECHA          : '->';
ARROBA          : '@';

// =====================================================
// OPERADORES ESPECIALES
// =====================================================

INTERROGACION   : '?';
AMPERSAND       : '&';
PIPE            : '|';
CIRCUNFLEJO     : '^';
TILDE           : '~';

// =====================================================
// ERROR LEXICO
// =====================================================

ERROR_LEXICO    : . ;
