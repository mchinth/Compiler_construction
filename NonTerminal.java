import java.util.HashSet;

public enum NonTerminal
{

	PROGRAM_LF2		("program_lf2", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.SEMICOLON);
		add(Token.L_PAREN);
		add(Token.L_BRACKET);
		add(Token.COMMA);
		}
	}), 
	
	TYPE_NAME("type_name", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.INT);
		add(Token.VOID);
		add(Token.BINARY);
		add(Token.DECIMAL);
		}
	}),
	
	PROGRAM 	("program", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(TYPE_NAME.firstSet);
		add(Token.EOF);
		}
	}),
	
	FUNC_LIST("func_list", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(TYPE_NAME.firstSet);
		}
	}),
	
	FUNC_LIST_LF1("func_list_lf1", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(TYPE_NAME.firstSet);
		add(Token.EOF);
		}

	}), 
	
	FUNC("func", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(TYPE_NAME.firstSet);
		}
	}),
	
	FUNC_LF1("func_lf1", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.SEMICOLON);
		add(Token.L_BRACE);
		}
	}),
	
	FUNC_LF2("func_lf2", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			add(Token.R_BRACE);
			add(Token.IDENTIFIER);
			add(Token.READ);
			add(Token.WRITE);
			add(Token.PRINT);
			add(Token.IF);
			add(Token.WHILE);
			add(Token.RETURN);
			add(Token.BREAK);
			add(Token.CONTINUE);
			addAll(TYPE_NAME.firstSet);
		}
	}),
	
	FUNC_LF3("func_lf3", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			add(Token.R_BRACE);
			add(Token.IDENTIFIER);
			add(Token.READ);
			add(Token.WRITE);
			add(Token.PRINT);
			add(Token.IF);
			add(Token.WHILE);
			add(Token.RETURN);
			add(Token.BREAK);
			add(Token.CONTINUE);
		}
	}),
	
	FUNC_DECL("func_decl", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			
		addAll(TYPE_NAME.firstSet);
		}
	}),
	
	FUNC_DECL_LF1("func_decl_lf1", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(TYPE_NAME.firstSet);
		add(Token.R_PAREN);
		}
	}),
	
	PARAM_LIST("param_list", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(TYPE_NAME.firstSet);
		}
	}),
	
	PARAM_LIST_LF1("param_list_lf1",  new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.IDENTIFIER);
		add(Token.R_PAREN);
		}
	}),
	
	NON_EMPTY_LIST_DASH("non_empty_list_dash",  new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.COMMA);
		add(Token.R_PAREN);
		}
	}),
	
	DATA_DECLS("data_decls", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(TYPE_NAME.firstSet);
		}
	}), 
	
	DATA_DECLS_LF1("data_decls_lf1", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			addAll(TYPE_NAME.firstSet);
			add(Token.R_BRACE);
			add(Token.IDENTIFIER);
			add(Token.READ);
			add(Token.WRITE);
			add(Token.PRINT);
			add(Token.IF);
			add(Token.WHILE);
			add(Token.RETURN);
			add(Token.BREAK);
			add(Token.CONTINUE);
		}
	}),
	
	ID_LIST("id_list", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.IDENTIFIER);
		}
	}), 
	
	ID_LIST_DASH("id_list_dash", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.COMMA);
		add(Token.SEMICOLON);
		}
	}),
	
	ID("id", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.IDENTIFIER);
		}
	}),
	
	ID_LF1("id_lf1", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			add(Token.L_BRACKET);
			add(Token.R_PAREN);

		add(Token.ASSIGN);
		add(Token.SEMICOLON);
		add(Token.COMMA);
		}
	}),
		
	BLOCK_STATEMENTS("block_statements", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.L_BRACE);
		}
	}),
		
	STATEMENT_LF1("statement_lf1", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.ASSIGN);
		add(Token.L_PAREN);
		add(Token.L_BRACKET);
		}
	}),
		
	FUNC_CALL_LF1("func_call_lf1", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.IDENTIFIER);
		add(Token.R_PAREN);
		add(Token.NUMBER);
		add(Token.MINUS);
		add(Token.L_PAREN);
		}
	}),
	
	EXPR_LIST("expr_list",new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			add(Token.NUMBER);
			add(Token.IDENTIFIER);
			add(Token.MINUS);
			add(Token.L_PAREN);
		}
	}), 
	
	NON_EMPTY_EXPR_LIST("non_empty_expr_list",new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			add(Token.NUMBER);
			add(Token.IDENTIFIER);
			add(Token.MINUS);
			add(Token.L_PAREN);
		}
	}), 
	
	NON_EMPTY_EXPR_LIST_DASH("non_empty_expr_list_dash",new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			add(Token.COMMA);
		
			add(Token.R_PAREN);
		}
	}), 
	
	IF_STATEMENT("if_statement", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.IF);
		}
	}), 
	
	CONDITION_EXPRESSION("condition_expr",new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.L_PAREN);
		add(Token.IDENTIFIER);
		add(Token.NUMBER);
		add(Token.MINUS);
		}
	}),
	
	CONDITION_OP("condition_op", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.AND);
		add(Token.OR);
		}
	}),
	
	CONDITION_EXPRESSION_LF1("condition_expr_lf1",new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(CONDITION_OP.firstSet);
		add(Token.R_PAREN);
		}
	}),
	
	CONDITION("condition", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.L_PAREN);
		add(Token.IDENTIFIER);
		add(Token.NUMBER);
		add(Token.MINUS);
		}
	}),
	
	COMPARISON_OP("comparison_op", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.EQUAL);
		add(Token.NOT_EQUAL);
		add(Token.GREATER_THAN);
		add(Token.GREATER_EQUAL);
		add(Token.LESS_THAN);
		add(Token.LESSER_EQUAL);
		}
	}),
	
	WHILE_STATEMENT("whileStatement", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			add(Token.WHILE);
		}
	}),
	
	RETURN_STATEMENT("return_statement", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.RETURN);
		}
	}), 
	
	RETURN_STATEMENT_LF1("return_statement_lf1", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.SEMICOLON);
		add(Token.IDENTIFIER);
		add(Token.NUMBER);
		add(Token.MINUS);
		add(Token.L_PAREN);
		}
	}),
	
	BREAK_STATEMENT("break_statement", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.BREAK);
		}
	}),
	
	CONTINUE_STATEMENT("continue_statement", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.CONTINUE);
		}
	}),
	
	EXPRESSION("expression", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.L_PAREN);
		add(Token.IDENTIFIER);
		add(Token.NUMBER);
		add(Token.MINUS);
		}
	}),

	ADDOP("addop", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			add(Token.PLUS);
			add(Token.MINUS);
		}
	}),
	
	MULOP("mulop", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			add(Token.STAR);
			add(Token.SLASH);
		}
	}),
	
	EXPRESSION_DASH("expression_dash", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(ADDOP.firstSet);
		addAll(CONDITION_OP.firstSet);
		addAll(COMPARISON_OP.firstSet);
		add(Token.COMMA);
		add(Token.SEMICOLON);
		add(Token.R_PAREN);
		add(Token.R_BRACKET);
		}
	}),
		
	TERM_DASH("term_dash", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(MULOP.firstSet);
		//new
		addAll(ADDOP.firstSet);
		addAll(CONDITION_OP.firstSet);
		addAll(COMPARISON_OP.firstSet);
		add(Token.COMMA);
		add(Token.SEMICOLON);
		add(Token.R_PAREN);
		add(Token.R_BRACKET);
		}
	}),
	
	FACTOR("factor", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			add(Token.NUMBER);
			add(Token.IDENTIFIER);
			add(Token.MINUS);
			add(Token.L_PAREN);
		}
	}), 
	
	TERM("term", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
			addAll(FACTOR.firstSet);
		}
	}), 
	
	FACTOR_LF1("factor_lf1", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.L_BRACKET);
		add(Token.L_PAREN);
		addAll(MULOP.firstSet);
		addAll(ADDOP.firstSet);
		addAll(COMPARISON_OP.firstSet);
		addAll(CONDITION_OP.firstSet);
		add(Token.COMMA);
		add(Token.SEMICOLON);
		add(Token.R_PAREN);
		add(Token.R_BRACKET);
		}
	}),
	
	FACTOR_LF2("factor_lf2", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.R_PAREN);
		addAll(FACTOR.firstSet);
		}
	}),

	STATEMENT("statement", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.READ);
		add(Token.WRITE);
		add(Token.PRINT);
		addAll(IF_STATEMENT.firstSet);
		addAll(WHILE_STATEMENT.firstSet);
		addAll(RETURN_STATEMENT.firstSet);
		addAll(BREAK_STATEMENT.firstSet);
		addAll(CONTINUE_STATEMENT.firstSet);
		add(Token.IDENTIFIER);
		}
	}),
			
	BLOCK_STATEMENTS_LF1("block_statements_lf1", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		add(Token.R_BRACE);
		addAll(STATEMENT.firstSet);
		}
	}),
	
	STATEMENT_SEQUENCE("statement_sequence", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(STATEMENT.firstSet);
		}
	}),
	
	STATEMENT_SEQUENCE_LF1("statement_sequence_lf1", new HashSet<Token>(){
		private static final long serialVersionUID = 1L;
		{
		addAll(STATEMENT.firstSet);
		add(Token.R_BRACE);
		}
	});

	public final String lexeme;
	public final HashSet<Token> firstSet = new HashSet<Token>();

 NonTerminal(String lexeme) 
  {
    this.lexeme = lexeme;
  }
	NonTerminal(String lexeme, HashSet<Token> t){

		this.lexeme = lexeme;
		firstSet.addAll(t);
	}
}