package edu.utsa.tl13;

public class IdentNode implements ASTtree{
	public String idName;
	public String idType;
	BNFGrammarAST bgAST;
	HashTableforSymbol sym;
	public boolean typeOk;
	ExpressionNode expNode;
	public String listType;
	
	public IdentNode(String n, String t){
		this.idName =n;
		this.idType =t;
		this.expNode = null;
		this.typeOk=true;
		this.listType=null;
	}
	
	public IdentNode(String n, String t, ExpressionNode expNode){
		this.idName =n;
		this.idType =t;
		this.expNode = expNode;
		this.typeOk=true;
		this.listType=null;
	}
	
	public IdentNode(String n, String t, ExpressionNode expNode,String type){
		this.idName =n;
		this.idType =t;
		this.expNode = expNode;
		this.typeOk=true;
		this.listType=type;
	}
	
	public void drawIdentNode(int seek_index, String fillcolor, String shape){
		bgAST = BNFGrammarAST.getInstance();
		int graph_first_index = bgAST.graph_indexAST;
		bgAST.graphAST.append("n"+graph_first_index+ " [label=\""+idName+"\",fillcolor=\""+fillcolor+"\",shape="+shape+"]").append(System.getProperty("line.separator"));
		bgAST.graph_indexAST++;
    	bgAST.graphAST.append("n"+seek_index+" -> n"+graph_first_index).append(System.getProperty("line.separator"));

	}
	
	 public void drawReadIntNode(int seek_index){
		bgAST = BNFGrammarAST.getInstance();
		sym = HashTableforSymbol.getInsatnce();
		String fillcolor = "/x11/lightgrey";
		
		if(sym.getIdentName(idName)==null){
			fillcolor = "/x11/lightgrey";
		}
		else if(sym.getIdentName(idName).equals("BOOL")){
			fillcolor = "/pastel13/2";
		}
		
		else if(sym.getIdentName(idName).equals("INT")){
			fillcolor = "/pastel13/3";
		}
		
		String shape = "box";
		int graph_first_index = bgAST.graph_indexAST;
		bgAST.graphAST.append("n"+graph_first_index+ " [label=\""+idName+"\",fillcolor=\""+fillcolor+"\",shape="+shape+"]").append(System.getProperty("line.separator"));
		bgAST.graph_indexAST++;
    	bgAST.graphAST.append("n"+seek_index+" -> n"+graph_first_index).append(System.getProperty("line.separator"));

	}
}
