package edu.utsa.tl13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BNFGrammar {
	public static int current_index;
	public static int graph_index;
	public static int pre_graph_index;
	Token sym ;
	static StringBuffer graph;
	public ArrayList<Token> tokens;
	
	String OutFileName;
	String InputFileName;
	
	
	
	public void BNFGrammarStructure(String inputFileName, String outFileName){
		
		graph = new StringBuffer();
		graph.append("digraph parseTree {").append(System.getProperty("line.separator"));
		graph.append("\t ordering=out;").append(System.getProperty("line.separator"));
		graph.append(" node [shape = box, style = filled];").append(System.getProperty("line.separator"));
		
		current_index =0;
		graph_index=1;
		pre_graph_index =1;
		Tokenizer tokenizer = new Tokenizer(inputFileName);
		tokens = tokenizer.myToken();
		for(Token tk: tokens){
			 //System.out.println(tk.type+" "+tk.value+ " " + tk.stringvalue);
			}
		if(program()){
		
			try {
				WriteToFIle.writeToFile(outFileName, graph);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("File created Succesfully");
		}
		
		else{
			System.out.println("Syntex error found");
			System.out.println("File not created");
		}
	}
	
	public String peek(){
		sym = tokens.get(current_index);
		if(!sym.value.equals(null)){
			
			return sym.type;
		}
		return null;
	}
	
	public boolean program(){
		int graph_first_index =graph_index;
		boolean checkProgram;
		if(peek().equals("PROGRAM")){
			
			graph.append("n"+graph_index + " [label=\""+"program"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator")) ;
			graph_index++;
		}
		
		else{
			
			System.out.println("Syntex Error in Program");
			return false;
		}
		
		//int graph_first_index =graph_index;
		
		checkProgram = next("PROGRAM", graph_first_index) && declarations(graph_first_index) && next("BEGIN",graph_first_index) && statementSequence(graph_first_index)  && next("END", graph_first_index); 
		
		if(checkProgram){
			return true;
		}
		
		else{
			
			return false;
		}
	}
	
	public boolean declarations(int seek_index){
		boolean checkProgram;
		int declarations_graph_index;
		int graph_first_index =graph_index;
        if(peek().equals("BEGIN")){
			
			graph.append("n"+graph_index + " [label=\""+"declarations"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
			graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
	    	graph_index++;
	    	declarations_graph_index=graph_index-1;
	    	graph.append("n"+graph_index + " [label=\""+"&#949;"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator")); //Empty string rule
			graph.append("n"+declarations_graph_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
	    	graph_index++;
	    	pre_graph_index=graph_index-1;
	    	
	    	return true;
		}
        
        graph.append("n"+graph_index + " [label=\""+"declarations"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	checkProgram = next("VAR", graph_first_index) && next("ident", graph_first_index) && next("AS", graph_first_index) && type(graph_first_index) && next("SC", graph_first_index) && declarations(graph_first_index);
        if(checkProgram){
        	
        	return true;
        }
        
        else{
        	System.out.println("Syntex Error in declartions rule");
        	return false;
        }
	}
	
	public boolean statementSequence(int seek_index){
		boolean checkProgram;
		int declarations_graph_index;
		int graph_first_index =graph_index;
		/*if(!(peek().equals("ELSE")) || (peek().equals("END"))){
			
			graph.append("n"+graph_index + " [label=\""+"statementSequence"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
	    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
	    	graph_index++;
	    	pre_graph_index=graph_index-1;
		}*/
		
	    

        if((peek().equals("ELSE")) || (peek().equals("END"))){
			
    	    graph.append("n"+graph_index + " [label=\""+"statementSequence"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
			graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
	    	graph_index++;
	    	declarations_graph_index=graph_index-1;
	    	graph.append("n"+graph_index + " [label=\""+"&#949;"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator")); //Empty string rule
			graph.append("n"+declarations_graph_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
	    	graph_index++;
	    	pre_graph_index=graph_index-1;
	    	
	    	return true;
		}
		
        graph.append("n"+graph_index + " [label=\""+"statementSequence"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
        
    	checkProgram= statement(graph_first_index) && next("SC", graph_first_index) && statementSequence(graph_first_index); 
    	
       if(checkProgram){
    	   
    	   return true;
       }
       else{
    	   System.out.println("Syntex Error in statementSequence rule"); 
    	   return false;
       }
	}
	
	public boolean type(int seek_index){
		boolean checkInt;
		boolean checkBool;
		//int declarations_graph_index;
		int graph_first_index =graph_index;
		
		graph.append("n"+graph_index + " [label=\""+"type"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	checkInt = peek().equals("INT") && next("INT",graph_first_index);
    	checkBool = peek().equals("BOOL") && next("BOOL",graph_first_index);
    	if(checkInt){
    		
    			return true;
    	}
    	
    	else if(checkBool){
    		
    			return true;
    	}
   
    	else{
    		
    		System.out.println("Syntex Error in type rule");
    		return false;
    	}
		
	}
	
	public boolean statement(int seek_index){
		//boolean checkProgram;
		//int declarations_graph_index;
		int graph_first_index =graph_index;
		
		graph.append("n"+graph_index + " [label=\""+"statement"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	if(peek().equals("ident")){
    		return assignment(graph_first_index);
    	}
    	
    	else if(peek().equals("IF")){
    		return ifStatement(graph_first_index);
    	}
    	
    	else if(peek().equals("WHILE")){
    		return whileStatement(graph_first_index);
    	}
    	
    	else if(peek().equals("WRITEINT")){
    		return writeInt(graph_first_index);
    	}
    	
    	else{
    		System.out.println("Syntex Error in statement rule");
      		return false;
    	}
	}
	
	public boolean assignment(int seek_index){
		boolean checkAssignment;
		//int declarations_graph_index;
		int graph_first_index =graph_index;
		
		graph.append("n"+graph_index + " [label=\""+"assignment"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	checkAssignment = next("ident", graph_first_index) && next("ASGN", graph_first_index) && assignmentPrime(graph_first_index);
		if(checkAssignment){
			return true;
		}
		else{
    	
    		System.out.println("Syntex Error in assignment rule");
			
			return false;
		}
	}
	
	public boolean assignmentPrime(int seek_index){
		boolean checkAssignmentPrime;
		//int declarations_graph_index;
		int graph_first_index =seek_index;
		checkAssignmentPrime = peek().equals("READINT") && next("READINT", graph_first_index);
		
		if(peek().equals("ident") || peek().equals("num") || peek().equals("boollit") || peek().equals("LP")){
			return expression(graph_first_index);
		}
		
		else if(checkAssignmentPrime){
			return true;
		}
		
		else{
    		System.out.println("Syntex Error in assignmentPrime rule");
			
			return false;
		}
	}
	
	

	
	public boolean ifStatement(int seek_index){
		boolean checkifStatement;
		//int declarations_graph_index;
		int graph_first_index =graph_index;
		
		graph.append("n"+graph_index + " [label=\""+"ifStatement"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	checkifStatement = next("IF",graph_first_index) && expression(graph_first_index) && next("THEN", graph_first_index) && statementSequence(graph_first_index) && elseClause(graph_first_index) && next("END", graph_first_index);
    	if(checkifStatement){
    		return true;
    	}
    	
    	else{
    		System.out.println("Syntex Error in ifStatement rule");
    		
    		return false;
    	}
	}
	
	
	public boolean elseClause(int seek_index){
		boolean checkelseClause;
		int elseClause_graph_index;
		int graph_first_index =graph_index;
		
		graph.append("n"+graph_index + " [label=\""+"elseClause"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	checkelseClause = next("ELSE", graph_first_index) && statementSequence(graph_first_index);
    	
    	if(peek().equals("END")){
    		graph.append("n"+graph_index + " [label=\""+"elseClause"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
			graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
	    	graph_index++;
	    	elseClause_graph_index=graph_index-1;
	    	graph.append("n"+graph_index + " [label=\""+"&#949;"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator")); //Empty string rule
			graph.append("n"+elseClause_graph_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
	    	graph_index++;
	    	pre_graph_index=graph_index-1;
	    	return true;
    	}
    	
    	else if(checkelseClause){
    		return true;
    	}
    	else{
    		System.out.println("Syntex Error in elseClause rule");
		
    		return false;
    	}
	}
	
	public boolean whileStatement(int seek_index){
		boolean checkwhileStatement;
		//int elseClause_graph_index;
		int graph_first_index =graph_index;
		
		graph.append("n"+graph_index + " [label=\""+"whileStatement"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	checkwhileStatement = next("WHILE", graph_first_index) && expression(graph_first_index) && next("DO", graph_first_index) && statementSequence(graph_first_index) && next("END", graph_first_index);
		if(checkwhileStatement){
			return true;
		}
		else{
    		System.out.println("Syntex Error in whileStatement rule");
    	
			return false;
		}
	}
	
	public boolean writeInt(int seek_index){
		boolean checkwriteInt;
		//int elseClause_graph_index;
		int graph_first_index =graph_index;
		
		graph.append("n"+graph_index + " [label=\""+"writeInt"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	checkwriteInt = next("WRITEINT", graph_first_index) && expression(graph_first_index);
        if(checkwriteInt){
        	return true;
        }
        else{
    		System.out.println("Syntex Error in writeInt rule");
    	
        	return false;
        }
	}
	
	
	public boolean expression(int seek_index){
		boolean checkexpression;
		//int elseClause_graph_index;
		int graph_first_index =graph_index;
		
		graph.append("n"+graph_index + " [label=\""+"expression"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	checkexpression = simpleExpression(graph_first_index) && expressionPrime(graph_first_index);
        if(checkexpression){
        	return true;
        }
        
        else{
    		System.out.println("Syntex Error in expression rule");    	
        	return false;
        }
	}
	
	public boolean expressionPrime(int seek_index){
		boolean checkexpressionPrime;
		//int elseClause_graph_index;
		int graph_first_index =seek_index;
		
		checkexpressionPrime = next("OP4", graph_first_index) && simpleExpression(graph_first_index);
		
		if(peek().equals("THEN") || peek().equals("DO") || peek().equals("SC") || peek().equals("RP")){
			return true;
		}
		
		else if(checkexpressionPrime){
			return true;
		}
		
		else{

			System.out.println("Syntex Error in expressionPrime rule");    	

			return false;
		}
	}
	
	public boolean simpleExpression(int seek_index){
		boolean checksimpleExpression;
		//int elseClause_graph_index;
		int graph_first_index =graph_index;
		
		graph.append("n"+graph_index + " [label=\""+"simpleExpression"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	checksimpleExpression = term(graph_first_index) && simpleExpressionPrime(graph_first_index);
    	if(checksimpleExpression){
    		return true;
    	}
    	else{
			System.out.println("Syntex Error in simpleExpression rule");    	
		
    		return false;
    	}
	}
	
	public boolean simpleExpressionPrime(int seek_index){
		boolean checksimpleExpressionPrime;
		//int elseClause_graph_index;
		int graph_first_index =seek_index;
		
		checksimpleExpressionPrime = next("OP3", graph_first_index) && term(graph_first_index);
		if(peek().equals("THEN") || peek().equals("DO") || peek().equals("SC") || peek().equals("RP") || peek().equals("OP4")){
			return true;
		}
		
		else if(checksimpleExpressionPrime){
			return true;
		}
		
		else{
			System.out.println("Syntex Error in simpleExpressionPrime rule");
			return false;
		}
	}
	
	public boolean term(int seek_index){
		boolean checkterm;
		//int elseClause_graph_index;
		int graph_first_index =graph_index;
		
		graph.append("n"+graph_index + " [label=\""+"term"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	checkterm = factor(graph_first_index) && termPrime(graph_first_index);
    	if(checkterm){
    		return true;
    	}
    	
    	else{
    		System.out.println("Syntex Error in term rule");
    		return false;
    	}
	}
	
	public boolean termPrime(int seek_index){
		boolean checktermPrime;
		//int elseClause_graph_index;
		int graph_first_index =seek_index;
		
		checktermPrime = next("OP2",graph_first_index) && factor(graph_first_index);
		if(peek().equals("THEN") || peek().equals("DO") || peek().equals("SC") || peek().equals("RP") || peek().equals("OP4") || peek().equals("OP3")){
		
		         
		   return true;
		}
		
		else if(checktermPrime){
			return true;
		}
		
		else{
			System.out.println("Syntex Error in termPrime rule");
			return false;
		}
	}
	
	public boolean factor(int seek_index){
		boolean checkfactor;
		//int elseClause_graph_index;
		int graph_first_index =graph_index;
		System.out.println(tokens.get(current_index).value);
		graph.append("n"+graph_index + " [label=\""+"factor"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
    	graph_index++;
    	pre_graph_index=graph_index-1;
    	
    	///checkfactor = next("LP",graph_first_index) && expression(graph_first_index) && next("RP", graph_first_index);
    	if(peek().equals("LP") && next("LP",graph_first_index) && expression(graph_first_index) && next("RP", graph_first_index)){
    		
    			return true;
    	}
    	
    	else if(peek().equals("ident") && next("ident", graph_first_index)){
    		return true;
    	}
    	
    	else if(peek().equals("num") && next("num", graph_first_index)){
    		return true;
    	}
    	
    	else if(peek().equals("boollit") && next("boollit", graph_first_index)){
    		return true;
    	}
    	
    	else{
    		System.out.println("Syntex Error in factor rule");
    		return false;
    	}
		//return false;
	}
	public boolean next(String symbol, int seek_index){
	    sym = tokens.get(current_index);
	    //System.out.println(sym.type);
	    if(sym.type.equals(symbol)){
	    	current_index++;
	    	graph.append("n"+graph_index + " [label=\""+sym.stringvalue+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
	    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
	    	graph_index++;
	    	pre_graph_index=graph_index-1;
	    	return true;
	    }
	    
	    else{
	    	//System.out.println("Syntex error found for "+ symbol);
	    	return false;
	    }
	}

}
