The submission contains below files : 
      Scanner.java , Parser.java, NonTerminal.java 
 Below are the instructions to compile and Run the parser . 
To compile : 
      javac Parser.java Scanner.java NonTerminal.java
To run : 
      java Parser <input file>
      
      
LL(1) Grammar 
1)<program> --> <type name> ID <program lf2>
2)		|empty
3)<program lf2> --> <id lf1> <id list dash> semicolon <program>
4)		|left_parenthesis <func decl lf1> < func lf1> < func list lf1>
5)<func list> --> <func> <func list lf1>
6)<func list lf1> --> <func list> 
7)		|empty
8)<func> --> <func decl> <func lf1>
9)<func lf1> --> semicolon 
10)		| left_brace <func lf2>
11)<func lf2> --> <statements> right_brace 
12)		|right_brace
13)		|<data decls> <func lf3>
14)<func lf3> --> <statements> right_brace
16)		|right_brace
17)<func decl> --> <type name> ID left_parenthesis <func decl lf1>
18)<func decl lf1> --> <parameter list> right_parenthesis 
19)		| right_parenthesis
20)<type name> --> int | void | binary | decimal 
21)<parameter list> --> void <parameter list lf1> 
22)			| int ID <non-empty list dash> 
23)			| binary ID <non-empty list dash> 
24)			|decimal ID <non-empty list dash> 
25)<parameter list lf1>  --> ID <non-empty list dash>
26)		| empty
27)<non-empty list> --> <type name> ID <non-empty list dash> 
28)<non-empty list dash> --> comma <type name> ID <non-empty list dash> 
29)		| empty
30)<data decls> --> <type name> <id list> semicolon <data decls lf1>
32)<data decls lf1> --> <data decls>
33)		|empty 
		 
34)<id list> --> <id> <id list dash>
35)<id list dash> --> comma <id> <id list dash>
36)		|empty 
37)<id> --> ID <id lf1>
38)<id lf1>  --> empty
39)		| left_bracket <expression> right_bracket
40)<block statements> --> left_brace <block statements	lf1>
41)<block statements lf1> --> <statements> right_brace 
42)			| right_brace
43)<statements> -->  <statement> <statements lf1> 
44)<statements lf1> -->  <statements> 
45)		| empty
46)<statement> --> ID	<statement lf1>
47)			| <if statement> 
48)			| <while statement> 
49)			| <return statement> 
50)			| <break statement> 
51)			| <continue statement> 
52)			| read left_parenthesis  ID right_parenthesis semicolon 
53)			| write left_parenthesis <expression> right_parenthesis semicolon 
54)			| print left_parenthesis  STRING right_parenthesis semicolon 
55)<statement lf1> --> <id lf1> equal_sign <expression> semicolon 
56)			|  left_parenthesis <func call lf1>  
57)<func call lf1> --> <expr list> right_parenthesis semicolon
58)			|right_parenthesis semicolon 
59)<expr list> --> <non-empty expr list>
60)<non-empty expr list> --> <expression> <non-empty expr list dash>
61)<non-empty expr list dash> --> comma <expression> <non-empty expr list dash>
62)			| empty
63)<if statement> --> if left_parenthesis <condition expression> right_parenthesis <block statements> 
64)<condition expression> -->  <condition> <condition expression lf1>
65)<condition expression lf1> --> <condition op> <condition> 
66)			| empty 
67)<condition op> --> double_and_sign 
68)			| double_or_sign
69)<condition> --> <expression> <comparison op> <expression> 
70)<comparison op> --> == | != | > | >= | < | <=
71)<while statement> --> while left_parenthesis <condition expression> right_parenthesis <block statements> 
72)<return statement> --> return <return statement lf1>
73)<return statement lf1> --> 	<expression> semicolon 
74)			| semicolon
75)<break statement> ---> break semicolon 
76)<continue statement> ---> continue semicolon
77)<expression> --> <term> <expression dash>
78)<expression dash> -->  <addop> <term> <expression dash>
79)			| empty
80)<addop> --> plus_sign 
81)			| minus_sign 
82)<term> --> <factor> <term dash>
83)<term dash> --> <mulop> <factor> <term dash>
84)			| empty
85)<mulop> --> star_sign 
86)			| forward_slash 
87)<factor> --> ID <factor lf1>
88)			| NUMBER 
89)			| minus_sign NUMBER 
90)			| left_parenthesis <expression> right_parenthesis
91)<factor lf1> --> left_bracket <expression> right_bracket
92)			|left_parenthesis <factor lf2>
93)			|empty
94)<factor lf2> --> <expr list> right_parenthesis
95)			| right_parenthesis	
		
		
		
Number of Production Rules = 95 and 
Number of Non-terminal’s  = 51 

		
		
		
		
		
		
