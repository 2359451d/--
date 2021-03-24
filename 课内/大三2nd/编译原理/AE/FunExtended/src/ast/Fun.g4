//////////////////////////////////////////////////////////////
//
// Specification of the Fun grammar.
//
//  Name: Yao Du
//  Date: 22/03/21
//
//////////////////////////////////////////////////////////////


grammar Fun;

// This specifies the Fun grammar, defining the syntax of Fun.

@header{
	package ast;
}

//////// Programs

program
	:	var_decl* proc_decl+ EOF  # prog
	;


//////// Declarations

proc_decl
	:	PROC ID
		  LPAR formal_decl_seq? RPAR COLON
		  var_decl* seq_com DOT   # proc

	|	FUNC type ID
		  LPAR formal_decl_seq? RPAR COLON
		  var_decl* seq_com
		  RETURN expr DOT         # func
	;

// multiple paras, seperated by comma ','
formal_decl_seq
	:	formal_decl (COMMA formal_decl)* # formalseq
	;

formal_decl
	:	type ID                # formal
	;

var_decl
	:	type ID ASSN expr         # var
	;

type
	:	BOOL                      # bool
	|	INT                       # int
	;


//////// Commands

com
	:	ID ASSN expr              # assn
	|	ID LPAR actual_seq? RPAR       # proccall
							 
	|	IF expr COLON c1=seq_com
		  ( DOT              
		  | ELSE COLON c2=seq_com DOT   
		  )                       # if

	|	WHILE expr COLON          
		  seq_com DOT             # while

// EXTENSION:  'for' ident = expr1(int) 'to' expr2(int): seq-com .
	|   FOR ID ASSN expr1=sec_expr TO expr2=sec_expr COLON
	      seq_com DOT             # for

// EXTENSION:  'switch' expr(int) : (case guard:seq-com)* default:seq-com .
    |   SWITCH expr COLON
            (case_com)*
            de_case DOT           # switch
	;

case_com
    : CASE g=guard COLON
        seq_com                             # casecom
    ;

de_case
    : DEFAULT COLON
        seq_com                             # defaultcase
    ;

guard
    :   NUM
    |   TRUE
    |   FALSE
    |   NUM RANGE NUM
    ;
////////////// EXTENSION

seq_com
	:	com*                      # seq
	;


//////// Expressions

expr
	:	e1=sec_expr
		  ( op=(EQ | LT | GT) e2=sec_expr )?
	;

sec_expr
	:	e1=prim_expr
		  ( op=(PLUS | MINUS | TIMES | DIV) e2=sec_expr )?
	;

prim_expr
	:	FALSE                  # false        
	|	TRUE                   # true
	|	NUM                    # num
	|	ID                     # id
	|	ID LPAR actual_seq? RPAR    # funccall
	|	NOT prim_expr          # not
	|	LPAR expr RPAR         # parens
	;

actual_seq
    :   expr (COMMA expr)*  # actualseq
    ;


//////// Lexicon

BOOL	:	'bool' ;
ELSE	:	'else' ;
FALSE	:	'false' ;
FUNC	:	'func' ;
IF		:	'if' ;
INT		:	'int' ;
PROC	:	'proc' ;
RETURN 	:	'return' ;
TRUE	:	'true' ;
WHILE	:	'while' ;
// EXTENSION - for lexicon
TO      :   'to'    ;
FOR     :   'for'   ;
// EXTENSION - switch lexicon
SWITCH  :   'switch' ;
CASE    :   'case' ;
DEFAULT :   'default' ;
///////////////EXTENSION

EQ		:	'==' ;
LT		:	'<' ;
GT		:	'>' ;
PLUS	:	'+' ;
MINUS	:	'-' ;
TIMES	:	'*' ;
DIV		:	'/' ;
NOT		:	'not' ;

ASSN	:	'=' ;

LPAR	:	'(' ;
RPAR	:	')' ;
COLON	:	':' ;
DOT		:	'.' ;
COMMA   :	',' ;
//EXTENSION - switch command lexicon
RANGE   :   '..' ;
////////EXTENSION
NUM		:	DIGIT+ ;

ID		:	LETTER (LETTER | DIGIT)* ;



SPACE	:	(' ' | '\t')+   -> skip ;
EOL		:	'\r'? '\n'          -> skip ;
COMMENT :	'#' ~('\r' | '\n')* '\r'? '\n'  -> skip ;

fragment LETTER : 'a'..'z' | 'A'..'Z' ;
fragment DIGIT  : '0'..'9' ;
