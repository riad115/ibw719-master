package edu.utsa.tl13;

public class BoollitNode {

	public boolean boolVal;
	String boolType;
	BNFGrammarAST bgAST;
	public boolean typeOk;
	public BoollitNode(Boolean val){
		this.boolVal=val;
		boolType ="BOOL";
		this.typeOk = true;
	}
	
	public void drawBoollitNode(int seek_index){
		String fillcolor= "/pastel13/2";
		String shape = "box";
		bgAST = BNFGrammarAST.getInstance();
		int graph_first_index = bgAST.graph_indexAST;
		bgAST.graphAST.append("n"+graph_first_index+ " [label=\""+String.valueOf(boolVal)+"\",fillcolor=\""+fillcolor+"\",shape="+shape+"]").append(System.getProperty("line.separator"));
		//int graph_first_index = bgAST.graph_indexAST;
		bgAST.graph_indexAST++;
		bgAST.graphAST.append("n"+seek_index+" -> n"+graph_first_index).append(System.getProperty("line.separator"));
	}
}
