import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Parser{
	public Token currentToken;
	private static FileOutputStream parserOutFile;
	private static PrintStream parserPrintData;
	private static String currentIdent;
	public  NonTerminal nonTerminal;
	public Scanner sc ;
	static int num_var; 
	static int num_func;
	static int num_statement; 
	
	public Parser(String filename) throws IOException{
		sc = new Scanner(filename);
		num_var =0;
		num_func =0;
		num_statement =0;
		try{
			currentToken = sc.next();
		}catch(Exception e){
			System.out.println("Error in finding Token" +e);
		}
	}
	
	private boolean accept(Token token) throws IOException{
		try{
			if (currentToken == token){
				if (currentToken == Token.IDENTIFIER){
					currentIdent = sc.getLexeme();
				}
				currentToken = sc.next();
				if(currentToken == Token.ERROR){
					printError(Token.ERROR);
				}
				return true;
			}
			return false;
		}
		catch (Exception e){
			System.out.println("Caught an error " + e);
			return false;
		}
	}
	
	private boolean expect(Token token) throws IOException{
		if (accept(token)) { 
			return true;
		}
		return false;
	}
	
	private void printError(Token token) throws IOException{
		System.out.println("FAIL @" + token + "--> " + sc.getLexeme());
		System.exit(-1);
	}
	
	public static void main(String args[]) throws IOException{
		if (args.length != 1){
			System.err.println("Usage: java Parser <sourceFile>");
			System.exit(1);
		}
		Parser p = new Parser(args[0]);
		try{
			p.program();
			System.out.println("num of vars = "+num_var);
			System.out.println("num of funcs ="+ num_func);
			System.out.println("num of statements ="+ num_statement);
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-2);
		}catch(Exception e){
			System.exit(-1);
		}
	}
	
	public void program() throws IOException{
		if(NonTerminal.PROGRAM.firstSet.contains(currentToken)){
			if(NonTerminal.TYPE_NAME.firstSet.contains(currentToken)){
				type_name();
				expect(Token.IDENTIFIER);
				program_lf2();	
			}
			else if (currentToken == Token.EOF){
				System.out.println("PASS " + Token.EOF + "  ");
			}
		}
		else {
			printError(currentToken);
		}
	}
	
	public void type_name()throws IOException{
		if (NonTerminal.TYPE_NAME.firstSet.contains(currentToken)){
			if( accept(Token.INT) || accept(Token.VOID) || accept(Token.BINARY)||accept(Token.DECIMAL) ){
				
			}
			else{
				
			}
		}
		else{
				printError(currentToken);
			}
	}
	
	public void program_lf2()throws IOException{
		if (NonTerminal.PROGRAM_LF2.firstSet.contains(currentToken)){
			if(NonTerminal.ID_LF1.firstSet.contains(currentToken)){
				id_lf1();
				id_list_dash();
				accept(Token.SEMICOLON);
				num_var++;
				program();
			}
			else if(currentToken == Token.L_PAREN){
				accept(Token.L_PAREN);
				func_decl_lf1();
				func_lf1();
				func_list_lf1();
			}
		}
		else {
			printError(currentToken);
		}
	}
		
	public void func_list()throws IOException{
		if(NonTerminal.FUNC_LIST.firstSet.contains(currentToken)){
			func();
			func_list_lf1();
		}
		else{
			printError(currentToken);
		}
	}
	
	public void func_list_lf1() throws IOException{
		if(NonTerminal.FUNC_LIST_LF1.firstSet.contains(currentToken)){
			if(NonTerminal.FUNC_LIST.firstSet.contains(currentToken)){
				func_list();
			}
			else if (currentToken == Token.EOF){
			System.out.println("PASS " + Token.EOF + "  ");	
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void func()throws IOException{
		if(NonTerminal.FUNC.firstSet.contains(currentToken)){
			func_decl();
			func_lf1();
		}
		else{
			printError(currentToken);
		}
	}
	
	public void func_lf1() throws IOException{
		if(NonTerminal.FUNC_LF1.firstSet.contains(currentToken)){
			if(currentToken == Token.SEMICOLON){
				accept(Token.SEMICOLON);
			}
			else if(currentToken== Token.L_BRACE){
				accept(Token.L_BRACE);
				num_func++;
				func_lf2();
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void func_lf2()throws IOException{
		if(NonTerminal.FUNC_LF2.firstSet.contains(currentToken)){
			if(currentToken == Token.R_BRACE){
				accept(Token.R_BRACE);
			}
			else if(NonTerminal.STATEMENT_SEQUENCE.firstSet.contains(currentToken)){
				statement_sequence();
				expect(Token.R_BRACE);
			}
			else if(NonTerminal.DATA_DECLS.firstSet.contains(currentToken)){
				data_decls();
				func_lf3();
			}			
		}
		else{
			printError(currentToken);
		}
	}
	
	public void func_lf3() throws IOException {
		if(NonTerminal.FUNC_LF3.firstSet.contains(currentToken)){
			if(currentToken == Token.R_BRACE){
				accept(Token.R_BRACE);
			}
			else if(NonTerminal.STATEMENT_SEQUENCE.firstSet.contains(currentToken)){
				statement_sequence();
				expect(Token.R_BRACE);
			}
		}
		else{
			printError(currentToken);
		}
		
	}
	
	public void func_decl() throws IOException{
		if(NonTerminal.FUNC_DECL.firstSet.contains(currentToken)){
			type_name();
			expect(Token.IDENTIFIER);
			expect(Token.L_PAREN);
			func_decl_lf1();
		}
		else{
			printError(currentToken);
		}
		
	}
	
	public void func_decl_lf1() throws IOException{
		if(NonTerminal.FUNC_DECL_LF1.firstSet.contains(currentToken)){
			if(currentToken == Token.R_PAREN){
				accept(Token.R_PAREN);
			}
			else if(NonTerminal.PARAM_LIST.firstSet.contains(currentToken)){
				param_list();
				expect(Token.R_PAREN);	
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void param_list() throws IOException{
		if(NonTerminal.PARAM_LIST.firstSet.contains(currentToken)){
			if(currentToken == Token.VOID){
				accept(Token.VOID);
				param_list_lf1();
			}
			else if(accept( Token.INT)
			||accept( Token.BINARY)
			||accept( Token.DECIMAL)){
				expect (Token.IDENTIFIER);
				non_empty_list_dash();
			}
		}
		else{
			printError(currentToken);
		}	
	}
	
	public void param_list_lf1() throws IOException{
		if(NonTerminal.PARAM_LIST_LF1.firstSet.contains(currentToken)){
			if(currentToken == Token.IDENTIFIER ){
				expect(Token.IDENTIFIER);
				non_empty_list_dash();
			}
		}
		else{
			printError(currentToken); 
		}
		
	}
	
	public void non_empty_list_dash() throws IOException{
		if(NonTerminal.NON_EMPTY_LIST_DASH.firstSet.contains(currentToken)){
			if(currentToken == Token.COMMA){
				expect(Token.COMMA);
				type_name();
				expect(Token.IDENTIFIER);
				non_empty_list_dash();
			}
			else{
				
			}
		}
		else{
			printError(currentToken);
		}
		
	}
	
	public void data_decls()throws IOException{
		if(NonTerminal.DATA_DECLS.firstSet.contains(currentToken)){
			num_var++;
			type_name();
			id_list();
			expect(Token.SEMICOLON);
			data_decls_lf1();
		}
		else{
			printError(currentToken);
		}
	}
	
	public void data_decls_lf1() throws IOException{
		if(NonTerminal.DATA_DECLS_LF1.firstSet.contains(currentToken)){
			if(NonTerminal.DATA_DECLS.firstSet.contains(currentToken)){
				data_decls();
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void id_list() throws IOException{
		if(NonTerminal.ID_LIST.firstSet.contains(currentToken)){
			id();
			id_list_dash();
		}
		else{
			printError(currentToken);
		}
	}
	
	public void id_list_dash() throws IOException{
		if(NonTerminal.ID_LIST_DASH.firstSet.contains(currentToken)){
			if(currentToken == Token.COMMA){
				expect(Token.COMMA);
				num_var++;
				id();
				id_list_dash();	
			}		
		}
		else{
			printError(currentToken);
		}
	}
	
	public void id() throws IOException{
		if(NonTerminal.ID.firstSet.contains(currentToken)){
			expect(Token.IDENTIFIER);
			id_lf1();
		}
		else{
			printError(currentToken);
		}
	}
	
	public void id_lf1() throws IOException{
		if(NonTerminal.ID_LF1.firstSet.contains(currentToken)){
			if(currentToken == Token.L_BRACKET){
			expect(Token.L_BRACKET);
			expression();
			expect(Token.R_BRACKET);
			}
			else{
				
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void block_statements() throws IOException{
		if(NonTerminal.BLOCK_STATEMENTS.firstSet.contains(currentToken)){
			expect(Token.L_BRACE);
			block_statements_lf1();			
		}
		else{
			printError(currentToken);
		}
	}
	
	public void block_statements_lf1() throws IOException{
		if(NonTerminal.BLOCK_STATEMENTS_LF1.firstSet.contains(currentToken)){
			if(currentToken == Token.R_BRACE){
				accept(Token.R_BRACE);
			}
			else {
			statement_sequence();
			expect(Token.R_BRACE);
			}
		}
		else{
			printError(currentToken);
		}	
	}
	
	public void statement_sequence() throws IOException{
		if(NonTerminal.STATEMENT_SEQUENCE.firstSet.contains(currentToken)){
			statement();
			statement_sequence_lf1();
		}
		else{
			printError(currentToken);
		}
		
	}
	
	public void statement_sequence_lf1() throws IOException{
		if(NonTerminal.STATEMENT_SEQUENCE_LF1.firstSet.contains(currentToken)){
			if(NonTerminal.STATEMENT_SEQUENCE.firstSet.contains(currentToken)){
				statement_sequence();
			}
			else{}
		}
		else{
			printError(currentToken);
		}
	}

	public void statement() throws IOException{
		if(NonTerminal.STATEMENT.firstSet.contains(currentToken)){
			num_statement++;
			if(currentToken == Token.IDENTIFIER){
				accept(Token.IDENTIFIER);
				statement_lf1();
			}
			else if(currentToken == Token.READ){
				accept(Token.READ);
				expect(Token.L_PAREN);
				expect(Token.IDENTIFIER);
				expect(Token.R_PAREN);
				expect(Token.SEMICOLON);
			}
			else if(currentToken == Token.WRITE){
				accept(Token.WRITE);
				expect(Token.L_PAREN);
				expression();
				expect(Token.R_PAREN);
				expect(Token.SEMICOLON);
			}
			else if(currentToken == Token.PRINT){
				accept(Token.PRINT);
				expect(Token.L_PAREN);
				expect(Token.STRING_LITERAL);
				expect(Token.R_PAREN);
				expect(Token.SEMICOLON);
			}
			else if(NonTerminal.IF_STATEMENT.firstSet.contains(currentToken)){
				if_statement();
			}
			else if(NonTerminal.WHILE_STATEMENT.firstSet.contains(currentToken)){
				while_statement();
			}
			else if(NonTerminal.RETURN_STATEMENT.firstSet.contains(currentToken)){
				return_statement();
			}
			else if(NonTerminal.BREAK_STATEMENT.firstSet.contains(currentToken)){
				break_statement();
			}
			else if(NonTerminal.CONTINUE_STATEMENT.firstSet.contains(currentToken)){
				continue_statement();
			}
		}
		else {
			printError(currentToken);
		}
	}
	
	public void statement_lf1() throws IOException{
		if(NonTerminal.STATEMENT_LF1.firstSet.contains(currentToken)){
			if(currentToken == Token.L_PAREN ){
				accept(Token.L_PAREN);
				func_call_lf1();
			}
			else if(NonTerminal.ID_LF1.firstSet.contains(currentToken)){
				id_lf1();
				expect(Token.ASSIGN);
				expression();
				expect(Token.SEMICOLON);
			}
		}
		else {
			printError(currentToken);
		}
	}
		
	public void func_call_lf1() throws IOException{
		if(NonTerminal.FUNC_CALL_LF1.firstSet.contains(currentToken)){
			if(NonTerminal.EXPR_LIST.firstSet.contains(currentToken)){
				expr_list();
				expect(Token.R_PAREN);
				expect(Token.SEMICOLON);
			}
			else if(currentToken == Token.R_PAREN){
				accept(Token.R_PAREN);
				expect(Token.SEMICOLON);
			}
		}else {
			printError(currentToken);
		}
		
	}
	
	public void expr_list()throws IOException{ 
		if(NonTerminal.EXPR_LIST.firstSet.contains(currentToken)){
			non_empty_expr_list();
		}
		else {
			printError(currentToken);
		}
	}
	
	public void non_empty_expr_list() throws IOException{
		if(NonTerminal.NON_EMPTY_EXPR_LIST.firstSet.contains(currentToken)){
			expression();
			non_empty_expr_list_dash();
		}
		else {
			printError(currentToken);
		}
	} 
	
	public void non_empty_expr_list_dash() throws IOException{
		if(NonTerminal.NON_EMPTY_EXPR_LIST_DASH.firstSet.contains(currentToken)){
			if(currentToken == Token.COMMA){
				expect(Token.COMMA);
				expression();
				non_empty_expr_list_dash();
			}
			else{
				
			}
		}	
		else{
		printError(currentToken);
		}
	}
	
	public void if_statement()throws IOException{
		if(NonTerminal.IF_STATEMENT.firstSet.contains(currentToken)){
			expect(Token.IF);
			expect(Token.L_PAREN);
			condition_expr();
			expect(Token.R_PAREN);
			block_statements();
		}
		else{
			printError(currentToken);
		}
	}
	
	public void condition_expr()throws IOException{
		if(NonTerminal.CONDITION_EXPRESSION.firstSet.contains(currentToken)){
			condition();
			condition_expr_lf1();
		}
		else{
			printError(currentToken);
		}
	}
	
	public void condition_expr_lf1()throws IOException{
		if(NonTerminal.CONDITION_EXPRESSION_LF1.firstSet.contains(currentToken)){
			if(NonTerminal.CONDITION_OP.firstSet.contains(currentToken)){
				condition_op();
				condition();
			}
			else{
				
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void condition_op() throws IOException{
		if(NonTerminal.CONDITION_OP.firstSet.contains(currentToken)){
			if(accept(Token.AND) 
				|| accept(Token.OR)){
				}
			else{
			}		
		}
		else{
			printError(currentToken);
		}
	}
	
	public void condition()throws IOException{
		if(NonTerminal.CONDITION.firstSet.contains(currentToken)){
			expression();
			comparison_op();
			expression();
		}
		else{
			printError(currentToken);
		}
	 }
	
	public void comparison_op()throws IOException{
		if(NonTerminal.COMPARISON_OP.firstSet.contains(currentToken)){
			if(accept(Token.EQUAL) 
				|| accept(Token.LESS_THAN) 
				|| accept(Token.GREATER_THAN)
				|| accept(Token.GREATER_EQUAL)
				|| accept(Token.LESSER_EQUAL)
				|| accept(Token.NOT_EQUAL)){
				}
			else{
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void while_statement()throws IOException{ 
		if(NonTerminal.WHILE_STATEMENT.firstSet.contains(currentToken)){
			accept(Token.WHILE);
			expect(Token.L_PAREN);
			condition_expr();
			expect(Token.R_PAREN);
			block_statements();	
		}
		else{
			printError(currentToken);
		}
	}
	
	public void return_statement()throws IOException{
		if(NonTerminal.RETURN_STATEMENT.firstSet.contains(currentToken)){
			expect(Token.RETURN);
			return_statement_lf1();
		}
		else{
			printError(currentToken);
		}
	}
	
	public void return_statement_lf1()throws IOException{
		if(NonTerminal.RETURN_STATEMENT_LF1.firstSet.contains(currentToken)){
			if(currentToken == Token.SEMICOLON){
				accept(Token.SEMICOLON);
			}
			else {
				expression();
				expect(Token.SEMICOLON);
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void break_statement()throws IOException{
		if(NonTerminal.BREAK_STATEMENT.firstSet.contains(currentToken)){
			expect(Token.BREAK);
			expect(Token.SEMICOLON);
		}
		else{
			printError(currentToken);
		}
	}
	
	public void continue_statement()throws IOException{
		if(NonTerminal.CONTINUE_STATEMENT.firstSet.contains(currentToken)){
			expect(Token.CONTINUE);
			expect(Token.SEMICOLON);
		}
		else{
			printError(currentToken);
		}
	}
	
	public void expression()throws IOException{
		if(NonTerminal.EXPRESSION.firstSet.contains(currentToken)){
			term();
			expression_dash();
		}
		else{
			printError(currentToken);
		}
	}
	
	public void expression_dash()throws IOException{
		if(NonTerminal.EXPRESSION_DASH.firstSet.contains(currentToken)){
			if(currentToken == Token.PLUS || currentToken == Token.MINUS){
				addop();
				term();
				expression_dash();
			}
			else{
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void addop()throws IOException{
		if(NonTerminal.ADDOP.firstSet.contains(currentToken)){
			if(accept(Token.PLUS)|| accept(Token.MINUS)){
			}
			else{
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void term()throws IOException{
		if(NonTerminal.TERM.firstSet.contains(currentToken)){
			if(NonTerminal.FACTOR.firstSet.contains(currentToken)){
			factor();
			term_dash();
			}
			else{
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void term_dash()throws IOException{
		if(NonTerminal.TERM_DASH.firstSet.contains(currentToken)){
			if(NonTerminal.MULOP.firstSet.contains(currentToken)){
				mulop();
				factor();
				term_dash();
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void mulop()throws IOException{
		if(NonTerminal.MULOP.firstSet.contains(currentToken)){
			if(accept(Token.STAR)|| accept(Token.SLASH)){
				
			}
		}
		else{
			printError(currentToken);
		}
	}
	
	public void factor()throws IOException{
		if(NonTerminal.FACTOR.firstSet.contains(currentToken)){
			if(currentToken == Token.IDENTIFIER){
				accept(Token.IDENTIFIER);
				factor_lf1();
			}
			else if (currentToken == Token.L_PAREN){
				accept(Token.L_PAREN);
				expression();
				expect(Token.R_PAREN);
			}
			else if(currentToken == Token.MINUS){
				accept(Token.MINUS);
				expect(Token.NUMBER);
			}
			else if (currentToken == Token.NUMBER){
				accept(Token.NUMBER);
			}
		}	
		else{
			printError(currentToken);
		}
	}
	
	public void factor_lf1()throws IOException{
		if(NonTerminal.FACTOR_LF1.firstSet.contains(currentToken)){
			if(currentToken == Token.L_BRACKET){
				accept(Token.L_BRACKET);
				expression();
				expect(Token.R_BRACKET);
			}
			else if (currentToken == Token.L_PAREN){
				accept(Token.L_PAREN);
				factor_lf2();
			}
			else{}	
		}	
		else{
			printError(currentToken);
		}
	}
	
	public void factor_lf2()throws IOException{
		if(NonTerminal.FACTOR_LF2.firstSet.contains(currentToken)){
			if(currentToken == Token.R_PAREN){
				accept(Token.R_PAREN);
			}
			else {
				expr_list();
				expect(Token.R_PAREN);
			}
		}	
		else{
			printError(currentToken);
		}
	}
}
