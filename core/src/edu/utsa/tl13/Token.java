package edu.utsa.tl13;

import java.util.ArrayList;

public class Token {
	public String type;
	public String value;
	public String stringvalue;
	public ArrayList<Token> tokenPeek;
	static int searchIndex; 

	public Token(String type, String value, String stringvalue){
		
		this.type = type;
		this.value = value;
		this.stringvalue = stringvalue;
	}
	
	
	/*Test functions for peeking Symbol for type*/
	public String TokenPeek(String FileName){
		searchIndex=0;
		Tokenizer tokenizer = new Tokenizer(FileName);
		tokenPeek = tokenizer.myToken();
		Token sym = tokenPeek.get(searchIndex);
		if(!sym.value.equals(null)){
			searchIndex++;
			return sym.type;
		}
		return null;
	}
	
	
	/*Test functions for peeking Symbol for Value*/
	public String TokenPeekValue(String FileName){
		searchIndex=0;
		Tokenizer tokenizer = new Tokenizer(FileName);
		tokenPeek = tokenizer.myToken();
		Token sym = tokenPeek.get(searchIndex);
		if(!sym.value.equals(null)){
			searchIndex++;
			return sym.stringvalue;
		}
		return null;
	}
}
