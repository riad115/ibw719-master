package edu.utsa.tl13;

public class TermPrimeNode {

	public FactorNode factNodePTerm;
	public OpNode opNodePTerm;
	
	public TermPrimeNode(){
		this.opNodePTerm=null;
		this.factNodePTerm =null;
	}
	
	public TermPrimeNode(OpNode op, FactorNode fNode){
	
		this.opNodePTerm = op;
		this.factNodePTerm = fNode;
	}
}
