lexer grammar JavaESLexer;

// Comentarios
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
WS: [ \t\r\n]+ -> skip;

// Palabras reservadas (en español) - DEBEN ir ANTES que IDENTIFICADOR
ENTERO_TIPO: 'entero';
DECIMAL_TIPO: 'decimal';
CADENA_TIPO: 'cadena';
BOOLEANO_TIPO: 'booleano';
VACIO_TIPO: 'vacio';
SI: 'si';
SINO: 'sino';
MIENTRAS: 'mientras';
RETORNAR: 'retornar';
CLASE: 'clase';
VERDADERO: 'verdadero';
FALSO: 'falso';
PUBLICO: 'publico';
PRIVADO: 'privado';

// Literales
fragment ESC: '\\' . ;
CADENA: '"' ( ~["\\\r\n] | ESC )* '"';
ENTERO: [0-9]+;
DECIMAL: [0-9]+ '.' [0-9]+;

// Identificador (incluir guion bajo y letras acentuadas)
IDENTIFICADOR: [a-zA-Z_\u00C0-\u017F][a-zA-Z0-9_\u00C0-\u017F]*;

// Operadores aritméticos
SUMA: '+';
RESTA: '-';
MULTIPLICACION: '*';
DIVISION: '/';
MODULO: '%';

// Operadores relacionales
IGUALDAD: '==';
DESIGUALDAD: '!=';
MAYOR_QUE: '>';
MENOR_QUE: '<';
MAYOR_IGUAL_QUE: '>=';
MENOR_IGUAL_QUE: '<=';
ASIGNACION: '=';

// Delimitadores
PUNTO_Y_COMA: ';';
COMA: ',';
PARENTESIS_ABIERTO: '(';
PARENTESIS_CERRADO: ')';
LLAVE_ABIERTA: '{';
LLAVE_CERRADA: '}';

// Error léxico como token especial para capturarlo
ERROR_LEXICO: . ;
