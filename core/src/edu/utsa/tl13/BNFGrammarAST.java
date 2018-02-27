package edu.utsa.tl13;

import java.io.IOException;
import java.util.ArrayList;

public class BNFGrammarAST {

	public static int current_index;
	public static int graph_index;
	public static int pre_graph_index;
	public static int current_indexAST;
	public static int graph_indexAST;
	public static int pre_graph_indexAST;
	public static int current_blockNum=0;
	public static int ilocRegister;
	public static String listType=null;
	private static BNFGrammarAST bnfAST =null;
	
	Token sym ;
	static StringBuffer graph;
	static StringBuffer graphAST;
	static StringBuffer graphILOC;
	static StringBuffer graphMIPS;
	public ArrayList<Token> tokens;
	
	String OutFileName;
	String InputFileName;
	
	public static BNFGrammarAST getInstance(){
		if(bnfAST==null){
			bnfAST = new BNFGrammarAST();
		}
		
		return bnfAST;
	}
	
	public void BNFGrammarASTStructure(String inputFileName, String outFileName){
		
		graph = new StringBuffer();
		graphAST = new StringBuffer();
		graphILOC = new StringBuffer();
		graphMIPS = new StringBuffer();
		graphAST.append("digraph astTree {").append(System.getProperty("line.separator"));
		graphAST.append("\t ordering=out;").append(System.getProperty("line.separator"));
		graphAST.append(" node [shape = box, style = filled];").append(System.getProperty("line.separator"));
		graphILOC.append("digraph ilocTree {").append(System.getProperty("line.separator"));
		graphILOC.append("node [shape = none];").append(System.getProperty("line.separator"));
		graphILOC.append("edge [tailport = s];").append(System.getProperty("line.separator"));
		graphILOC.append("entry").append(System.getProperty("line.separator"));
		graphILOC.append("subgraph cluster {").append(System.getProperty("line.separator"));
		graphILOC.append("color=\"/x11/white\"").append(System.getProperty("line.separator"));
		graphMIPS.append(".data").append(System.getProperty("line.separator"));
		//graphMIPS.append("newline:	.asciiz \"\\n\" ").append(System.getProperty("line.separator"));
		//graphMIPS.append(".text").append(System.getProperty("line.separator"));
		//graphMIPS.append(".globl main").append(System.getProperty("line.separator"));
		//graphMIPS.append("main:").append(System.getProperty("line.separator"));
		//graphMIPS.append("li $fp, 0x7ffffffc").append(System.getProperty("line.separator"));
		
		
		
		ProgramNode pr;
		BlockNode block = new BlockNode();
		current_index =0;
		graph_index=1;
		pre_graph_index =1;
		graph_indexAST=1;
		pre_graph_indexAST=1;
		//current_blockNum = 0;
		ilocRegister = -1;
		Tokenizer tokenizer = new Tokenizer(inputFileName);
		tokens = tokenizer.myToken();
		for(Token tk: tokens){
			System.out.println(tk.type+" "+tk.value+ " " + tk.stringvalue);
			}
		if((pr=program())!=null){
		
			try {
				pr.drawProgramNode(pre_graph_index);
				WriteToFileAST.writeToFileAST(inputFileName, graphAST);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("File created Succesfully");
			
			graphMIPS.append("newline:	.asciiz \"\\n\" ").append(System.getProperty("line.separator"));
			graphMIPS.append(".text").append(System.getProperty("line.separator"));
			graphMIPS.append(".globl main").append(System.getProperty("line.separator"));
			graphMIPS.append("main:").append(System.getProperty("line.separator"));
			graphMIPS.append("li $fp, 0x7ffffffc").append(System.getProperty("line.separator"));
			
			block = BuildILOC.ILOCDeclarationsNode(block, pr.declarations);
			//System.out.println(pr.statementSequence);
			BlockNode exit = BuildILOC.ILOCStatementSequence(block, pr.statementSequence);
			try {
				BNFGrammarAST.graphILOC.append("<tr><td align=\"left\">exit</td><td align=\"left\"></td><td align=\"left\"></td></tr>");
				BNFGrammarAST.graphILOC.append("</table>>,fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
				graphILOC.append("}").append(System.getProperty("line.separator"));
				graphILOC.append("entry -> n"+block.blkNum).append(System.getProperty("line.separator"));
				graphILOC.append("n"+exit.blkNum+"-> exit").append(System.getProperty("line.separator"));
				
				WriteToFileILOCCFG.writeToFileILOCCFG(inputFileName, graphILOC);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				graphMIPS.append("# exit ").append(System.getProperty("line.separator"));
				graphMIPS.append("exit: ").append(System.getProperty("line.separator")); //added for exit
				exit.addMipsInstruction(new MIPSInstructionFromat("li", "10", null, "$v0"));
				exit.addMipsInstruction(new MIPSInstructionFromat("syscall", null, null, null));
				graphMIPS.append("li $v0, 10").append(System.getProperty("line.separator"));
				graphMIPS.append("syscall").append(System.getProperty("line.separator"));
				WriteToFileMIPS.writeToFileMIPS(inputFileName, graphMIPS);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else{
			System.out.println("Syntex error found");
			System.out.println("File not created");
		}
		
		if(pr.typeOk){
			System.out.println("No type error");
		}
		
		else{
			System.out.println("TYPE ERROR");
		}
		
		
	}
	
	public String peek(){
		sym = tokens.get(current_index);
		if(!sym.value.equals("")){
			
			return sym.type;
		}
		return "";
	}
	
	public boolean peekValue(String symbol){
		sym = tokens.get(current_index);
		if(sym.type.equals(symbol)){
			current_index++;
			return true;
		}
		//System.out.println("Syntex error found" +  sym.type+"for "+ symbol);
		return false;
	}
	
	public ProgramNode program(){
		
		DeclarationsNode decl;
		StatementSequenceNode stmt_seq;
		int graph_first_index =graph_index;
		boolean checkProgram;
		
		
		//int graph_first_index =graph_index;
		
		if(peekValue("PROGRAM") && ((decl=declarations())!=null) && peekValue("BEGIN") && ((stmt_seq=statementSequence())!=null)  && peekValue("END")){
			
			return new ProgramNode(decl, stmt_seq);
		}
		
		
		
		else{
			System.out.println("Syntex Error in program rule");
			return null;
		}
	}
	
	public DeclarationsNode declarations(){
		TypeNode typeNode;
		boolean checkProgram;
		int declarations_graph_index;
		int graph_first_index =graph_index;
		String idName;
		IdentNode idNode;
        if(peek().equals("BEGIN")){
			
			return DeclarationsNode.getInsatnce();
		}

        //!(idName=nextValue("ident")).equals("")
    	 
        if(peekValue("VAR") && ((idNode=ident())!=null) && peekValue("AS") && ((typeNode=type())!=null) && peekValue("SC") && (declarations()!=null)){
        	
        	// idNode = new IdentNode(idName, typeNode.typeNode);
        	if(!(idNode.idType.equals("ARRAY"))){
        		idNode.idType = typeNode.typeNode;
        	}
        	DeclaredVar de_var =new DeclaredVar(idNode, typeNode);
        	DeclarationsNode.getInsatnce().addToDeclarationsNode(de_var);
        	return DeclarationsNode.getInsatnce();
        }
        
        else{
        	System.out.println("Syntex Error in declartions rule");
        	return null;
        }
	}
	
	public StatementSequenceNode statementSequence(){
		boolean checkProgram;
		int declarations_graph_index;
		int graph_first_index =graph_index;
		StatementSequenceNode stmtSeq =null;
		StatementNode stmt = null;
		//System.out.println(tokens.get(current_index).value);
		/*if(!(peek().equals("ELSE")) || (peek().equals("END"))){
			
			graph.append("n"+graph_index + " [label=\""+"statementSequence"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
	    	graph.append("n"+seek_index+" -> n"+graph_index).append(System.getProperty("line.separator"));
	    	graph_index++;
	    	pre_graph_index=graph_index-1;
		}*/
		
	    

        if((peek().equals("ELSE")) || (peek().equals("END"))){
			
    	   return new StatementSequenceNode();
		}
		
        //stmt =statement();
        //stmtSeq =statementSequence();
        
    	//checkProgram= ((stmt=statement()) !=null) && peekValue("SC") && ((stmtSeq=statementSequence())!=null); 
    	
        else if(((stmt=statement()) !=null) && peekValue("SC") && ((stmtSeq=statementSequence())!=null)){
    	   stmtSeq.addToStatementSequence(stmt);
    	   return stmtSeq;
       }
       else{
    	   System.out.println("Syntex Error in statementSequence rule"); 
    	   return null;
       }
	}
	
	public TypeNode type(){
		boolean checkInt;
		boolean checkBool;
		//int declarations_graph_index;
		//int graph_first_index =graph_index;
		
		//System.out.println(tokens.get(current_index).stringvalue);
    	
    	checkInt = peek().equals("INT") && peekValue("INT");
    	checkBool = peek().equals("BOOL") && peekValue("BOOL");
    	if(checkInt){
    			
    			return new TypeNode("INT");
    	}
    	
    	else if(checkBool){
    		
    			return new TypeNode("BOOL");
    	}
   
    	else{
    		
    		System.out.println("Syntex Error in type rule");
    		return null;
    	}
		
	}
	
	public StatementNode statement(){
		//boolean checkProgram;
		//int declarations_graph_index;
		//int graph_first_index =graph_index;

		//System.out.println(tokens.get(current_index).value);
    	if(peek().equals("ident")){
    		return assignment();
    	}
    	
    	else if(peek().equals("IF")){
    		return ifStatement();
    	}
    	
    	else if(peek().equals("WHILE")){
    		return whileStatement();
    	}
    	
    	else if(peek().equals("WRITEINT")){
    		return writeInt();
    	}
    	
    	else if(peek().equals("EXIT")){
    		return exit();
    	}
    	
    	else{
    		System.out.println("Syntex Error in statement rule");
      		return null;
    	}
	}
	
	public ExitStatementNode exit(){
		ExitStatementNode exitNode =null;
		if(peekValue("EXIT")){
			
			exitNode = new ExitStatementNode();
			return exitNode;
		}
		
		return exitNode;
	}
	
	public StatementNode assignment(){
		boolean checkAssignment;
		//int declarations_graph_index;
		
		String idName;
		String asgn = null ;
		ASTtree stmt = null;
		String ident;
		
		IdentNode idNode;
		AssignmentNode asgnNode = null;
		OpNode op;
		//!((idName = nextValue("ident")).equals(""))
    	checkAssignment = ((idNode=ident())!=null) && (!((asgn = nextValue("ASGN")).equals(""))) && ((stmt = assignmentPrime())!=null);
    	op = new OpNode(asgn, "ASGN");
    	idNode.idType = HashTableforSymbol.getInsatnce().getIdentName(idNode.idName);
    	//idNode = new IdentNode(idName, ident);
    	//asgnNode = new AssignmentNode(idNode);
    	
		if(checkAssignment){
			//System.out.println(stmt.getClass().getName().toString());
			if(stmt.getClass().getName().equals("edu.utsa.tl13.ExpressionNode")){
				ExpressionNode exp = (ExpressionNode)stmt;
				asgnNode = new AssignmentNode(idNode, op, exp);
				//return asgnNode;
			}
			if(stmt.getClass().getName().equals("edu.utsa.tl13.IdentNode")){
				IdentNode identifier = (IdentNode)stmt;
				asgnNode = new AssignmentNode(idNode, op, identifier);
				//return asgnNode;
			}
			
			return asgnNode;
		}
		else{
    	
    		System.out.println("Syntex Error in assignment rule");
			
			return null;
		}
	}
	
	public ASTtree assignmentPrime(){
		boolean checkAssignmentPrime;
		//int declarations_graph_index;
		//int graph_first_index =seek_index;
		IdentNode idNode = null;
		//checkAssignmentPrime = peek().equals("READINT") && peekValue("READINT");
		
		if(peek().equals("ident") || peek().equals("num") || peek().equals("boollit") || peek().equals("LP")){
			return expression();
		}
		
		else if(peek().equals("READINT") && peekValue("READINT")){
			idNode = new IdentNode("READINT", "INT");
			return idNode;
		}
		
		else{
    		System.out.println("Syntex Error in assignmentPrime rule");
			
			return null;
		}
	}
	
	

	
	public IfStatementNode ifStatement(){
		boolean checkifStatement;
		//int declarations_graph_index;
		//int graph_first_index =graph_index;
        ExpressionNode expNode = null; 
        StatementSequenceNode stmtSeq = null;
        StatementSequenceNode elsestmtSeq = null;
    	IfStatementNode ifstmtNode = null;
    	checkifStatement = peekValue("IF") && ((expNode=expression())!=null) && peekValue("THEN") && ((stmtSeq=statementSequence())!=null) && ((elsestmtSeq=elseClause())!=null) && peekValue("END");
    	if(checkifStatement){
    		ifstmtNode = new IfStatementNode(expNode, stmtSeq, elsestmtSeq);
    		return ifstmtNode;
    	}
    	
    	else{
    		System.out.println("Syntex Error in ifStatement rule");
    		
    		return null;
    	}
	}
	
	
	public StatementSequenceNode elseClause(){
		boolean checkelseClause;
		int elseClause_graph_index;
		int graph_first_index =graph_index;
		
		StatementSequenceNode stmtSeq = null;
		StatementSequenceNode stmt =null;
    	
    	//checkelseClause = peekValue("ELSE") && ((stmtSeq=statementSequence())!=null);
    	
    	if(peek().equals("END")){
    		stmt = new StatementSequenceNode();
    		return stmt;
    	}
    	
    	else if(peekValue("ELSE") && ((stmtSeq=statementSequence())!=null)){
    		return stmtSeq;
    	}
    	else{
    		System.out.println("Syntex Error in elseClause rule");
		
    		return null;
    	}
	}
	
	public WhileStatementNode whileStatement(){
		boolean checkwhileStatement;
		//int elseClause_graph_index;
		int graph_first_index =graph_index;
		ExpressionNode expNode = null;
		StatementSequenceNode stmtSeqNode = null;
		WhileStatementNode whilestmtNode =null;
		
		checkwhileStatement = peekValue("WHILE") && ((expNode=expression())!=null) && peekValue("DO") && ((stmtSeqNode=statementSequence())!=null) && peekValue("END");
		if(checkwhileStatement){
			whilestmtNode = new WhileStatementNode(expNode, stmtSeqNode);
			return whilestmtNode;
		}
		else{
    		System.out.println("Syntex Error in whileStatement rule");
    	
			return null;
		}
	}
	
	public WriteIntNode writeInt(){
		boolean checkwriteInt;
		//int elseClause_graph_index;
		//System.out.println(tokens.get(current_index).value);
		int graph_first_index =graph_index;
		ExpressionNode expNode = null;
    	WriteIntNode writeInt=null;
    	checkwriteInt = peekValue("WRITEINT") && ((expNode=expression())!=null);
        if(checkwriteInt){
        	writeInt = new WriteIntNode(expNode);
        	return writeInt;
        }
        else{
    		System.out.println("Syntex Error in writeInt rule");
    	
        	return null;
        }
	}
	
	
	public ExpressionNode expression(){
		boolean checkexpression;
		//int elseClause_graph_index;
		int graph_first_index =graph_index;
		
		//System.out.println(tokens.get(current_index).value);
		SimpleExpressionNode smExpNode = null;
		ExpressionPrimeNode exPrimeNode = null;
		ExpressionNode expNode =null;
    	
    	checkexpression = ((smExpNode = simpleExpression())!=null) && ((exPrimeNode = expressionPrime())!=null);
        if(checkexpression){
        	//if(exPrimeNode.opNodesm==null){
        		//expNode = new ExpressionNode(smExpNode);
        	//}
        	
        	//else{
        	expNode = new ExpressionNode(smExpNode, exPrimeNode.opNodesm, exPrimeNode.smExpressionNode);
        	//}
        	return expNode;
        }
        
        else{
    		System.out.println("Syntex Error in expression rule");    	
        	return null;
        }
	}
	
	public ExpressionPrimeNode expressionPrime(){
		boolean checkexpressionPrime;
		//int elseClause_graph_index;
		//int graph_first_index =seek_index;
		SimpleExpressionNode smexpNode = null;
		String opNode;   //have to change
		ExpressionPrimeNode exPrimeNode = null;
		OpNode op;
		//checkexpressionPrime = (!((opNode = nextValue("OP4")).equals(""))) && ((smexpNode =simpleExpression())!=null);
		
		if(peek().equals("THEN") || peek().equals("DO") || peek().equals("SC") || peek().equals("RP") || peek().equals("arrayEnd")){
			exPrimeNode =new ExpressionPrimeNode();
			return exPrimeNode;
			
		}
		
		else if((!((opNode = nextValue("OP4")).equals(""))) && ((smexpNode =simpleExpression())!=null)){
			op =new OpNode(opNode, "OP4");
			exPrimeNode = new ExpressionPrimeNode(smexpNode, op);
			return exPrimeNode;
		}
		
		else{

			System.out.println("Syntex Error in expressionPrime rule");    	

			return null;
		}
	}
	
	public SimpleExpressionNode simpleExpression(){
		boolean checksimpleExpression;
		//int elseClause_graph_index;
		int graph_first_index =graph_index;
		//System.out.println(tokens.get(current_index).value);
		TermNode termNode = null;
    	SimpleExpressionPrimeNode smExpPrimeNode =null;
    	SimpleExpressionNode smExpNode =null;
    	
    	checksimpleExpression = ((termNode = term())!=null) && ((smExpPrimeNode = simpleExpressionPrime())!=null);
    	if(checksimpleExpression){
    		
    		//if(smExpPrimeNode.opTerm==null){
        		//smExpNode = new SimpleExpressionNode(termNode);
        	//}
        	
        	//else{
        	   smExpNode = new SimpleExpressionNode(termNode, smExpPrimeNode.opTerm, smExpPrimeNode.termNode);
        	//}
        	return smExpNode;
    		
    	}
    	else{
			System.out.println("Syntex Error in simpleExpression rule");    	
		
    		return null;
    	}
	}
	
	public SimpleExpressionPrimeNode simpleExpressionPrime(){
		boolean checksimpleExpressionPrime;
		//int elseClause_graph_index;
		//int graph_first_index =seek_index;
		//System.out.println(tokens.get(current_index).value);
		String op;
		TermNode termNode = null;
		SimpleExpressionPrimeNode smExpNode = null;
		OpNode opNode = null;
		
		//checksimpleExpressionPrime = (!((op = nextValue("OP3")).equals(""))) && ((termNode = term())!=null);
		if(peek().equals("THEN") || peek().equals("DO") || peek().equals("SC") || peek().equals("RP") || peek().equals("OP4") || peek().equals("arrayEnd")){
			smExpNode = new SimpleExpressionPrimeNode();
			return smExpNode;
		}
		
		else if((!((op = nextValue("OP3")).equals(""))) && ((termNode = term())!=null)){
			opNode = new OpNode(op,"OP3");
			smExpNode = new SimpleExpressionPrimeNode(opNode, termNode);
			return smExpNode;
		}
		
		else{
			System.out.println("Syntex Error in simpleExpressionPrime rule");
			return null;
		}
	}
	
	public TermNode term(){
		boolean checkterm;
		//int elseClause_graph_index;
		//int graph_first_index =graph_index;
		//System.out.println(tokens.get(current_index).value);
        FactorNode factNode =null;
        TermPrimeNode termPNode = null;
        TermNode termNode = null;
    	
    	checkterm = ((factNode =factor())!=null) && ((termPNode = termPrime())!=null);
    	if(checkterm){
    		//if(termPNode.opNodePTerm==null){
    			//termNode = new TermNode(factNode);
        	//}
        	
        	//else{
        		termNode = new TermNode(factNode, termPNode.opNodePTerm, termPNode.factNodePTerm);
        	//}
        	return termNode;
    	}
    	
    	else{
    		System.out.println("Syntex Error in term rule");
    		return null;
    	}
	}
	
	public TermPrimeNode termPrime(){
		boolean checktermPrime;
		//int elseClause_graph_index;
		//int graph_first_index =seek_index;
		//System.out.println(tokens.get(current_index).value);
		String op;
		FactorNode factNode = null;
		TermPrimeNode termPNode =null;
		OpNode opNode = null;
		
		//checktermPrime = (!((op = nextValue("OP2")).equals(""))) && ((factNode = factor())!=null);
		if(peek().equals("THEN") || peek().equals("DO") || peek().equals("SC") || peek().equals("RP") || peek().equals("OP4") || peek().equals("OP3") || peek().equals("arrayEnd")){
		
		   termPNode = new TermPrimeNode();
		   return termPNode;
		}
		
		else if((!((op = nextValue("OP2")).equals(""))) && ((factNode = factor())!=null)){
			opNode = new OpNode(op,"OP2");
			termPNode = new TermPrimeNode(opNode, factNode);
			return termPNode;
		}
		
		else{
			System.out.println("Syntex Error in termPrime rule");
			return null;
		}
	}
	
	public FactorNode factor(){
		boolean checkfactor;
		//int elseClause_graph_index;
		int graph_first_index =graph_index;
		
        ExpressionNode expNode;
        FactorNode factNode =null;
        IdentNode idNode=null;
        NumNode numNode =null;
        BoollitNode boolNode =null;
        String idName, idType;
        //System.out.println(tokens.get(current_index).value);
    	///checkfactor = next("LP",graph_first_index) && expression(graph_first_index) && next("RP", graph_first_index);
    	if(peek().equals("LP") && peekValue("LP") && ((expNode=expression())!=null) && peekValue("RP")){
    		
    			factNode = new FactorNode(expNode);
    			return factNode;
    	}
    	
    	//else if(peek().equals("ident") && (!(idName=nextValue("ident")).equals(""))){
    	else if(peek().equals("ident")){	
    		//idType = HashTableforSymbol.getInsatnce().getIdentName(idName);
    		//idNode = new IdentNode(idName, idType);
    		idNode = ident();
    		factNode = new FactorNode(idNode);
    		return factNode;
    	}
    	
    	else if(peek().equals("num") && (!(idName=nextValue("num")).equals(""))){
    		//idType = HashTableforSymbol.getInsatnce().getIdentName(idName);
    		numNode = new NumNode(Long.parseLong(idName)); 
    		factNode = new FactorNode(numNode);
    		return factNode;
    	}
    	
    	else if(peek().equals("boollit") && (!(idName=nextValue("boollit")).equals(""))){
    		boolNode = new BoollitNode(Boolean.parseBoolean(idName)); 
    		factNode = new FactorNode(boolNode);
    		return factNode;
    	}
    	
    	else{
    		System.out.println("Syntex Error in factor rule");
    		return null;
    	}
		//return false;
	}
	
	public IdentNode ident(){
		String idName = nextValue("ident");
		IdentNode ident;
		//System.out.println(idName);
		//System.out.println(tokens.get(current_index).stringvalue);
		if (peek().equals("arrayStart")){
			nextValue("arrayStart");
			ExpressionNode expNode = expression();
			nextValue("arrayEnd");
			//System.out.println(tokens.get(current_index).stringvalue);
			ident = new IdentNode(idName,"ident",expNode);
			return ident;
		}
		
		else if(peek().equals("LISTTYPE")){
			nextValue("LISTTYPE");
			//ExpressionNode expNode = expression();
			nextValue("LIST");
			System.out.println(tokens.get(current_index).stringvalue);
			ident = new IdentNode(idName,"ident", null,"LIST");
			return ident;
		}
		
		else if(peek().equals("LP")){
			nextValue("LP");
			ExpressionNode expNode = expression();
			nextValue("RP");
			//System.out.println(tokens.get(current_index).stringvalue);
			ident = new IdentNode(idName,"ident",expNode,"LIST");
			return ident;
		}
		
		else{
			ident = new IdentNode(idName,"ident");
			return ident;
		}
		
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
	
	public String nextValue(String symbol){
	    sym = tokens.get(current_index);
	    //System.out.println(sym.type);
	    if(sym.type.equals(symbol)){
	    	current_index++;
	    	return sym.value;
	    }
	    
	    else{
	    	//System.out.println("Syntex error found" +  sym.type+"for "+ symbol);
	    	return "";
	    }
	}

}
