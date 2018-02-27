package edu.utsa.tl13;

public class OpNode {

	public String opName;
	public String opType;
	BNFGrammarAST bgAST;
	ExpressionNode expNode;
	
	public OpNode(String n, String t){
		this.opName=n;
		this.opType=t;
	}
	
	public void drawOpNode(int seek_index){
		String fillcolor= "/pastel13/2";
		//if(!expNode.typeOk){
			//fillcolor= "/pastel13/2";
		//}
		String shape = "box";
		bgAST = BNFGrammarAST.getInstance();
		int graph_first_index = bgAST.graph_indexAST;
		bgAST.graphAST.append("n"+graph_first_index+ " [label=\""+opName+"\",fillcolor=\""+fillcolor+"\",shape="+shape+"]").append(System.getProperty("line.separator"));
		//int graph_first_index = bgAST.graph_indexAST;
		bgAST.graph_indexAST++;
		bgAST.graphAST.append("n"+seek_index+" -> n"+graph_first_index).append(System.getProperty("line.separator"));
	}
	
	public void drawOpNode(int seek_index, String color){
		String fillcolor= color;
		//if(!expNode.typeOk){
			//fillcolor= "/pastel13/2";
		//}
		String shape = "box";
		bgAST = BNFGrammarAST.getInstance();
		int graph_first_index = bgAST.graph_indexAST;
		bgAST.graphAST.append("n"+graph_first_index+ " [label=\""+opName+"\",fillcolor=\""+fillcolor+"\",shape="+shape+"]").append(System.getProperty("line.separator"));
		//int graph_first_index = bgAST.graph_indexAST;
		bgAST.graph_indexAST++;
		bgAST.graphAST.append("n"+seek_index+" -> n"+graph_first_index).append(System.getProperty("line.separator"));
	}
}
