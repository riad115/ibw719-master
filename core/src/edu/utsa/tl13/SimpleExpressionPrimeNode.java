package edu.utsa.tl13;

public class SimpleExpressionPrimeNode {

	//public SimpleExpressionPrimeNode smExpTerm;
	public OpNode opTerm;
	public TermNode termNode;
	
	public SimpleExpressionPrimeNode(){
		this.opTerm =null;
		this.termNode = null;
	}
	
	public SimpleExpressionPrimeNode(OpNode op, TermNode tNode){
		this.opTerm =op;
		this.termNode =tNode;
	}
}
