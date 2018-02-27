package edu.utsa.tl13;

public class ExpressionPrimeNode {

	SimpleExpressionNode smExpressionNode;
	OpNode opNodesm;
	
	public ExpressionPrimeNode(){
		this.smExpressionNode = null;
		this.opNodesm = null;
	}
	
	public ExpressionPrimeNode(SimpleExpressionNode smexpNode, OpNode op){
		
		this.smExpressionNode = smexpNode;
		this.opNodesm = op;
	}
}
