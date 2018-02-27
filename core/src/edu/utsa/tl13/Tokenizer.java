package edu.utsa.tl13;
import java.util.ArrayList;
public class Tokenizer {
	
	ArrayList<Token> TokenInfo = new ArrayList<Token>(); 
	
	public Tokenizer(String FileName){
		
		String symbol[] = ReadInputFile.readInputFile(FileName);
		//System.out.println(Arrays.toString(symbol));
		
		for(String lookup:symbol){
			
			if(!RegexMatch.Regexcompare(lookup).equals("")){
				
				if(RegexMatch.Regexcompare(lookup).contains("num") || RegexMatch.Regexcompare(lookup).contains("ident") ||RegexMatch.Regexcompare(lookup).contains("boollit")){
					
					TokenInfo.add(new Token(RegexMatch.Regexcompare(lookup),lookup, RegexMatch.Regexcompare(lookup)+" : "+ lookup));	
				}
				
				else if(RegexMatch.Regexcompare(lookup).contains("OP2") || RegexMatch.Regexcompare(lookup).contains("OP3") ||RegexMatch.Regexcompare(lookup).contains("OP4") || RegexMatch.Regexcompare(lookup).contains("ASGN") || RegexMatch.Regexcompare(lookup).contains("SC") || RegexMatch.Regexcompare(lookup).contains("LP") || RegexMatch.Regexcompare(lookup).contains("RP")){
					
					TokenInfo.add(new Token(RegexMatch.Regexcompare(lookup),lookup,lookup));
				}
				
				else{
					
					TokenInfo.add(new Token(RegexMatch.Regexcompare(lookup),lookup,RegexMatch.Regexcompare(lookup)));
				}
			}
		}
		
		
	}
	
	public ArrayList<Token> myToken(){
		return TokenInfo;
		
	}

}

/*
class Token{
	
	public String type;
	public String value;
	public String stringvalue;
	
	public Token(String type, String value, String stringvalue){
		
		this.type = type;
		this.value = value;
		this.stringvalue = stringvalue;
	}
	
}

*/