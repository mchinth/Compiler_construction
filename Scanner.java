

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.File;

enum Token {
  INT       ("int"),
  VOID      ("void"),
  IF        ("if"),
  WHILE     ("while"),
  RETURN    ("return"),
  READ 	    ("read"),
  WRITE     ("write"),
  PRINT     ("print"),
  CONTINUE  ("continue"),
  BREAK     ("break"),
  BINARY    ("binary"),
  DECIMAL   ("decimal"),
  MAIN      ("main"),

  L_PAREN       ("("),
  R_PAREN       (")"),
  L_BRACE       ("{"),
  R_BRACE       ("}"),
  L_BRACKET     ("["),
  R_BRACKET     ("]"),
  COMMA         (","),
  SEMICOLON     (";"),
  PLUS          ("+"),
  MINUS         ("-"),
  STAR          ("*"),
  SLASH         ("/"),
  EQUAL         ("=="),
  NOT_EQUAL     ("!="),
  GREATER_THAN  (">"),
  GREATER_EQUAL (">="),
  LESS_THAN     ("<"),
  LESSER_EQUAL  ("<="),
  ASSIGN        ("="),
  AND 		("&&"),
  OR		("||"),
  
  IDENTIFIER    (""),
  STRING_LITERAL(""),
  NUMBER        (""),
  SPACE 	(""),
  EOF           (""),
  ERROR         (""),
  COMMENT       (""),
  MACRO         ("");

  public final String defaultLexeme;

  private Token(String defaultLexeme) 
  {
    this.defaultLexeme = defaultLexeme;
  }
}

class Scanner {
	 public StringBuilder lexeme ;
	 public Token token;
	 public String lex;
	 public int nextchar;
	 public PushbackReader in;
	 public static FileWriter writer;
	 public Scanner(String inputfilename) throws IOException{
		try{
		   in = new PushbackReader(new FileReader(inputfilename)); 
		}catch(IOException ex){
			System.err.println(" java.io.FileNotFoundException");
			System.exit(1);
		}
		nextchar =0;
	 }
	public void readchar(){
		try{		
			nextchar = in.read();
		}catch(IOException ex){
			System.err.println(" java.io.IOException");
			System.exit(1);
		}
		if (nextchar == 65535){
			System.err.println("File Doesn't contain an EOF");
			nextchar = -1;
		}else if ( nextchar == -1){
			nextchar = -1;
		}else if(nextchar == ' ' || nextchar == '\t' || nextchar == '\r'){
			lex +=(char)nextchar; 
		}else if ( nextchar == '\n'){
			lex += " ";
		}else{
			lex += (char) nextchar;
		}
	}
	public void unreadchar(){
		lex = (String)lex.substring(0,lex.length()-1);
		try{
			in.unread(nextchar);
		}catch(IOException ex){
            System.err.println(" java.io.IOException");
            System.exit(1);
        }
	}
	public void generateLexeme(){
		String temp = "";
		for (int i = 0; i < lex.length(); i++){
			temp += lex.charAt(i);
		}
		lexeme = new StringBuilder(temp);
	}
	public Token next(){
		lex = "";
		readchar();
		while (nextchar != -1 )
		{
		   switch((char)nextchar){
			case ' ' :
			case '\t':
			case '\r':
			case '\n':
				return next();
			//	generateLexeme();
				//return Token.SPACE;
			case 'i' : return check_i(); /*int*/
			case 'v' : return check_v(); /* void */
			case 'r' : return check_r(); /*return, read */
			case 'w' : return check_w(); /*while ,write*/
			case 'b' : return check_b(); /*break, binary */
			case 'd' : return check_d(); /*decimal*/
			case 'c' : return check_c(); /*continue*/
			case 'p' : return check_p();  /*print*/
			case 'm' : return check_m(); /* main */
			case '0':
			case '1': 
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				return numeric_value();  /*number */
			case '"':
			    readchar();
			    while(nextchar != '"'){
					try{
						nextchar = in.read();
					}catch(IOException ex){
						System.err.println(" java.io.IOException");
                        System.exit(1);
        		    }
				lex +=(char) nextchar;
				}
				generateLexeme();
				return Token.STRING_LITERAL;
			case '#':
			    readchar();
				while((nextchar != '\n' ||  nextchar != -1) ){
					readchar();
					if(nextchar == '\n'){
						generateLexeme();
						break;
					}
				}
				if(nextchar == '\n' || nextchar != -1){
					generateLexeme();
				}
				return next();
			case '(':
				generateLexeme();
				return Token.L_PAREN;
			case ')': 
				generateLexeme(); 
				return Token.R_PAREN;
			case '{':
				generateLexeme();
				return Token.L_BRACE;
			case '}': 
				generateLexeme(); 
				return Token.R_BRACE;
			case '[':
				generateLexeme();
				return Token.L_BRACKET;
			case ']':
				generateLexeme();
				return Token.R_BRACKET;
			case ',': 
				generateLexeme(); 
				return Token.COMMA;
			case ';':
				generateLexeme(); 
				return Token.SEMICOLON;
			case '+': 
				generateLexeme(); 
				return Token.PLUS;
			case '-':
				generateLexeme();
				return Token.MINUS;
			case '*': 
				generateLexeme(); 
				return Token.STAR;
			case '/':
				readchar();
				if(nextchar == '/'){
					readchar();
					while((nextchar != '\n' ||  nextchar != -1) ){
						readchar();
						if(nextchar == '\n'){
							generateLexeme();
							break;
						}
					}
					generateLexeme();
					return Token.COMMENT;
				}else{
					unreadchar();
					generateLexeme();
					return Token.SLASH;
				}
			case '=': 
				readchar();
				if(nextchar == '='){
					generateLexeme();
					return Token.EQUAL;
				}else{
					unreadchar();
					generateLexeme();
					return Token.ASSIGN;
				}
			case '!':
				readchar();
				if(nextchar == '='){
					generateLexeme();
					return Token.NOT_EQUAL;
				}else{
					generateLexeme();
					return Token.ERROR;
				}
			case '>': 
				readchar();
				if( nextchar == '='){
					generateLexeme();
					return Token.GREATER_EQUAL;
				}else{
					unreadchar();
					generateLexeme();
					return Token.GREATER_THAN;
				}
			case '<':
				readchar();
				if( nextchar == '='){
					generateLexeme();
					return Token.LESSER_EQUAL;
				}else{
					unreadchar();
					generateLexeme();
					return Token.LESS_THAN;
				}
			case '&': 
				readchar();
				if(nextchar == '&'){
					generateLexeme();
					return Token.AND;
				}else{
					generateLexeme();
					return Token.ERROR;
				}
			case '|':
			readchar();
				if(nextchar == '|'){
					generateLexeme();
					return Token.OR;
				}else{
					generateLexeme();
					return Token.ERROR;
				}
			default:
				if(token.IDENTIFIER == identifier() && lex.length() !=0){
					return Token.IDENTIFIER;
				}
				try{
                    nextchar = in.read();
		       	}catch(IOException ex){
                    System.err.println(" java.io.IOException");
                    System.exit(1);
        		}
				lex +=(char) nextchar;
				generateLexeme();
				return Token.ERROR;
			}
	    }
	   if (nextchar == -1){
			generateLexeme();
			return Token.EOF;
		}
		return Token.ERROR;
	}	
	public Token  check_i(){
		readchar();
		if(nextchar == 'n'){
			readchar();
			if(nextchar == 't'){
				readchar();
				if(Character.isWhitespace(nextchar)){
					unreadchar();			
					generateLexeme();
					return Token.INT;
				}else{
					if(nextchar == ')'){
						unreadchar();			
						generateLexeme();
						return Token.INT;
					}
					return identifier();
				}
			}else{
				return identifier();
			}
		}else if(nextchar == 'f'){
			readchar();
			if(Character.isWhitespace(nextchar)){
				unreadchar();
				generateLexeme();
				return Token.IF;
			}else if (nextchar == '('){
				unreadchar();
				generateLexeme();
				return Token.IF;
			}else{
				return identifier();
			}
		}else {
			return identifier();
		}
	}
	public Token check_b(){
		readchar();
		if(nextchar == 'r'){
			readchar();
			if(nextchar == 'e'){
				readchar();
				if(nextchar == 'a'){
					readchar();
					if(nextchar == 'k'){
						readchar();
						if(Character.isWhitespace(nextchar)){
							unreadchar();
							generateLexeme();
							return Token.BREAK;
						}else if (!Character.isLetterOrDigit(nextchar)){
							unreadchar();
							generateLexeme();
							return Token.BREAK;
						}else {
							return identifier();
						}
					}else {
						return identifier();
					}
				}else {
					return identifier();
				}
			}else{
				return identifier();
			}
		}else if (nextchar == 'i'){
			readchar();
			if(nextchar == 'n'){
				readchar();
				if(nextchar == 'a'){
					readchar();
					if(nextchar == 'r'){
						readchar();
						if(nextchar == 'y'){
							readchar();
							if(Character.isWhitespace(nextchar)){
								unreadchar();
								generateLexeme();
								return Token.BINARY;
							}else if( !Character.isLetterOrDigit(nextchar)){
								unreadchar();
								generateLexeme();
								return Token.BINARY;
							}else {
								return identifier();
							}
						}else{
							return identifier();
						}
					}else{
						return identifier();
					}
				}else{
					return identifier();
				}
			}else{
				return identifier();
			}
		}else{
			return identifier();
		}
	}
	public Token check_m(){
		readchar();
		if(nextchar == 'a'){
			readchar();
			if(nextchar == 'i'){
				readchar();
				if(nextchar == 'n'){
					readchar();
					if(Character.isWhitespace(nextchar)){
						unreadchar();
						generateLexeme();
						return Token.IDENTIFIER;
					}else if(nextchar == '('){
						unreadchar();
						generateLexeme();
						return Token.IDENTIFIER;
					}else if(!Character.isLetterOrDigit(nextchar) ){
						unreadchar();
						generateLexeme();
						return Token.IDENTIFIER;
					}else{
						return identifier();
					}
				}else{
					return identifier();
				}
			}else{
				return identifier();
			}
		}else{
			return identifier();
		}
	}
	public Token  check_w(){
		readchar();
		if(nextchar == 'h'){
			readchar();
			if(nextchar == 'i'){
				readchar();
				if(nextchar == 'l'){
					readchar();
					if(nextchar == 'e'){
						readchar();
						if(Character.isWhitespace(nextchar)){
							unreadchar();
							generateLexeme();
							return Token.WHILE;
						}else if (!Character.isLetterOrDigit(nextchar)){
							unreadchar();
							generateLexeme();
							return Token.WHILE;
						}else {
							return identifier();
						}
					}else {
						return identifier();
					}
				}else {
					return identifier();
				}
			}else{
				return identifier();
			}
		}else if( nextchar == 'r'){
			readchar();
			if(nextchar == 'i'){
				readchar();
				if(nextchar == 't'){
					readchar();
					if(nextchar == 'e'){
						readchar();
						if(Character.isWhitespace(nextchar)){
							unreadchar();
							generateLexeme();
							return Token.WRITE;
						}else if ( nextchar == '('){
							unreadchar();
							generateLexeme();
							return Token.WRITE;
						}else {
							return identifier();
						}
					}else{
						return identifier();
					}
				}else{
					return identifier();
				}
			}else{
				return identifier();
			}
		}else{
			return identifier();
		}
	}
	public Token check_p(){
		readchar();
		if(nextchar == 'r'){
			readchar();
			if(nextchar == 'i'){
				readchar();
				if(nextchar == 'n'){
					readchar();
					if(nextchar == 't'){
						readchar();
						if(Character.isWhitespace(nextchar)){
							unreadchar();
							generateLexeme();
							return Token.PRINT;
						}else if( nextchar == '('){
                            unreadchar();
                            generateLexeme();
                            return Token.PRINT;
						}else {
							return identifier();
						}
					}else {
						return identifier();
					}
				}else {
					return identifier();
				}
			}else{
				return identifier();
			}
		}else{
			return identifier();
		}
	}
	public Token check_r(){
		readchar();
		if(nextchar == 'e'){
			readchar();
			if(nextchar == 'a'){
				readchar();
				if(nextchar == 'd'){
					readchar();
					if(Character.isWhitespace(nextchar)){
						unreadchar();
						generateLexeme();
						return Token.READ;
					}else if(nextchar == '('){
						unreadchar();
						generateLexeme();
						return Token.READ;	
					}else {
						return identifier();
					}
				}else {
					return identifier();
				}
			}else if(nextchar == 't'){
				readchar();
				if(nextchar == 'u'){
					readchar();
					if(nextchar == 'r'){
						readchar();
						if(nextchar == 'n'){
							readchar();
							if(Character.isWhitespace(nextchar)){
								unreadchar();
								generateLexeme();
								return Token.RETURN;
							}else if(nextchar == ';' || nextchar == '('){
								unreadchar();
								generateLexeme();
								return Token.RETURN;	
							}else{
								return identifier();
							}
						}else{
							return identifier();
						}
					}else{
						return identifier();
					}
				}else{
					return identifier();
				}
			}else {
				return identifier();
			}
		}else{
			return identifier();
		}
	}
	public Token  check_d(){
		readchar();
		if(nextchar == 'e'){
			readchar();
			if(nextchar == 'c'){
				readchar();
				if(nextchar == 'i'){
					readchar();
					if(nextchar == 'm'){
						readchar();
						if(nextchar == 'a'){
							readchar();
							if(nextchar == 'l'){
								readchar();
								if(Character.isWhitespace(nextchar)){
									unreadchar();
									generateLexeme();
									return Token.DECIMAL;
								}else if(nextchar == '(' || nextchar == ';'){
									unreadchar();
									generateLexeme();
									return Token.READ;	
								}
								else{
									return identifier();
								}
							}else{
								return identifier();
							}
						}else{
							return identifier();
						}
					}else{
						return identifier();
					}
				}else{
					return identifier();
				}
			}else{
				return identifier();
			}
		}else{
			return identifier();
		}
	}
	public Token check_v(){
		readchar();
		if(nextchar == 'o'){
			readchar();
			if(nextchar == 'i'){
				readchar();
				if(nextchar == 'd'){
					readchar();
					if(Character.isWhitespace(nextchar)){
						unreadchar();
						generateLexeme();
						return Token.VOID;
					}else if (!Character.isLetterOrDigit(nextchar)){
						unreadchar();
						generateLexeme();
						return Token.VOID;
					}else {
						return identifier();
					}
				}else {
					return identifier();
				}
			}else{
				return identifier();
			}
		}else{
			return identifier();
		}
	}
	public Token  check_c(){
		readchar();
		if(nextchar == 'o'){
			readchar();
			if(nextchar == 'n'){
				readchar();
				if(nextchar == 't'){
					readchar();
					if(nextchar == 'i'){
						readchar();
						if(nextchar == 'n'){
							readchar();
							if(nextchar == 'u'){
								readchar();
								if(nextchar == 'e'){
									readchar();
									if(Character.isWhitespace(nextchar)){
										unreadchar();
										generateLexeme();
										return Token.CONTINUE;
									}else if ( !Character.isLetterOrDigit(nextchar)){
										unreadchar();
										generateLexeme();
										return Token.CONTINUE;
									}else{
										return identifier();
									}
								}else{
									return identifier();
								}
							}else{
								return identifier();
							}
						}else{
							return identifier();
						}
					}else{
						return identifier();
					}
				}else{
					return identifier();
				}
			}else{
				return identifier();
			}
		}else{
			return identifier();
        }
	}
	public Token identifier(){
		while((Character.isLetterOrDigit(nextchar)  || nextchar == '_' ) && nextchar != '\n' ){
			readchar();
		}
		 if ((!Character.isLetterOrDigit(lex.charAt(lex.length() - 1))) || !(lex.charAt(lex.length() - 1) == '_'))
         {
            unreadchar();
         }
		generateLexeme();
		return Token.IDENTIFIER;
	}
	public Token numeric_value(){
		while(Character.isDigit(nextchar)){
			readchar();
		}
		if(Character.isDigit(lex.charAt(lex.length() - 1))){
			generateLexeme();
			return Token.NUMBER;
		}
		else{
			unreadchar();
			generateLexeme();
			return Token.NUMBER;
		}
	}
    public String getLexeme()
	{
		return lexeme.toString();
	}
	public String scannedToken(Token token)
	{

		if (token.defaultLexeme.length() == 0) {
			if( token == token.IDENTIFIER){
				return "cs512" + this.lexeme.toString();
			}else if ( token == Token.MACRO || token == Token.COMMENT){
				return this.lexeme.toString() + '\n';
			}
			return  this.lexeme.toString();
		}else return token.defaultLexeme;
	}
	public String getTokens() throws IOException
	{
		StringBuilder buff = new StringBuilder();
		Token token = next();
		while (token != Token.EOF && token != Token.ERROR)
		{	
			buff.append(scannedToken(token));
			token = next();
		} 
		if(token == Token.ERROR){
			return  "Unexpected Char found --->   " + this.lexeme.toString() + " Error in Scanning \n ";
		}
		return buff.toString();
	}
	/*public static void main(String[] args) throws IOException
	{
		if (args.length != 1){
			System.err.println("Usage: java Scanner <sourceFile>");
			System.exit(1);
		}
		Scanner scanner = new Scanner(args[0]);
		String result = scanner.getTokens();
		writer.write(result);
		writer.flush();
		writer.close();
	}*/
}


