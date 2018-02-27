package edu.utsa.tl13;

import java.util.*;

public class Parser {
	
	static String filename;
	public ArrayList<Token> currenttokens;
	//BNFGrammar bnf = new BNFGrammar();
	BNFGrammarAST bnfAST = new BNFGrammarAST();
	
	public void parser(String InputFileName, String OutputFileName){
		filename = InputFileName;
		
		System.out.println(filename);
		
		Tokenizer tokenizer = new Tokenizer(InputFileName);
		currenttokens = tokenizer.myToken(); 	
		for(Token tk: currenttokens){
			 //System.out.println(tk.type+" "+tk.value+ " " + tk.stringvalue);
			}
		
		//bnf.BNFGrammarStructure(InputFileName,OutputFileName);
		bnfAST.BNFGrammarASTStructure(InputFileName,OutputFileName);
	}

}
