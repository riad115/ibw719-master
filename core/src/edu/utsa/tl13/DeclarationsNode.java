package edu.utsa.tl13;

import java.util.ArrayList;
import java.util.Collections;

public class DeclarationsNode {

	public static DeclarationsNode declNode = new DeclarationsNode();
	public ArrayList<DeclaredVar> declVar;
	public DeclaredVar dVar;
	BNFGrammarAST bgAST; 
	IdentNode idNode;
	public boolean typeOk;
	public DeclarationsNode(){
		this.typeOk=true;
		declVar = new ArrayList<DeclaredVar>();
	}
	
	public void addToDeclarationsNode(DeclaredVar decl){
		declVar.add(decl);
		if(!TypeCheck()){
			this.typeOk =false;
		}
	}
	
	public ArrayList<DeclaredVar> getAllVar(){
		return declNode.declVar;
	}
	
	public static DeclarationsNode getInsatnce(){
		return declNode;
	}
	
	public void drawDecalrationsNode(int seek_index){
		
		
		int graph_first_index;
		bgAST = BNFGrammarAST.getInstance();
		//bgAST.graphAST.append("n"+seek_index + " [label=\""+"decl list"+"\",fillcolor=\"/x11/white\",shape=box]").append(System.getProperty("line.separator"));
		graph_first_index = bgAST.graph_indexAST;
		idNode = new IdentNode("decl list","decl");
		idNode.drawIdentNode(seek_index,"/x11/white","none");
		
		Collections.reverse(declVar);
		for(DeclaredVar d: declVar){
			//bgAST = BNFGrammarAST.getInstance();
			//graph_first_index =bgAST.graph_index;
			//System.out.println(d.ident);
			d.drawDeclaredVar(graph_first_index);
		}
	}
	
	boolean TypeCheck(){
		boolean checkType=true;
		for(DeclaredVar d: declVar){
			checkType =checkType && d.checkDuplicate;
		}
		return checkType;
		
	}
	
}
