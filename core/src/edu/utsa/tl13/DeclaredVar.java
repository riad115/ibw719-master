package edu.utsa.tl13;

public class DeclaredVar {

	public IdentNode ident;
	public TypeNode type;
	
	BNFGrammarAST bgAST;
	IdentNode idNode;
	TypeNode typeNode;
	public boolean checkDuplicate;
	HashTableforSymbol sym;

	//public boolean duplicate = false;
	
	public DeclaredVar(IdentNode id, TypeNode t){
		this.ident =id;
		this.type = t;
		this.checkDuplicate =true;
		
		
		if(addToSymbolHashTable(id.idName, id.idType)){
			this.checkDuplicate =false;
			//System.out.println("Variable declared twice");
		}
		else{
			this.checkDuplicate =true;
		}
	}
	
	public boolean addToSymbolHashTable(String id, String type){
		
		HashTableforSymbol symTable = HashTableforSymbol.getInsatnce();
		
		if(symTable.getIdentName(id)==null){
			if(ident.expNode!=null){
				int arraySize = (int) ident.expNode.seNode1.smExpTerm1.fNode1.numNodeF.numVal;
				if(arraySize>0){
					BNFGrammarAST.graphMIPS.append(id+":\t .space "+ (arraySize*4)).append(System.getProperty("line.separator"));
					symTable.addToHashTableforSymbol(id, type);
				}
			}
			
			else if((ident.listType!=null) && (ident.listType.equals("LIST"))){
				//int arraySize = (int) ident.expNode.seNode1.smExpTerm1.fNode1.numNodeF.numVal;
				//if(arraySize>0){
					BNFGrammarAST.graphMIPS.append(id+":\t .space "+ 256).append(System.getProperty("line.separator"));
					symTable.addToHashTableforSymbol(id, type);
				//}
			}
			else{
				symTable.addToHashTableforSymbol(id, type);
			}
			return false;
		}
		
		else{
			return true;
		}
	}
	
	
	public void drawDeclaredVar(int seek_index){
		sym = HashTableforSymbol.getInsatnce();
		typeNode =new TypeNode(type.typeNode);
		String fillcolor = "/x11/lightgrey";
		if(!this.checkDuplicate){
			fillcolor = "/pastel13/1";
		}
		else{
		if(type.typeNode==null){
			fillcolor = "/x11/lightgrey";
		}
		else if(type.typeNode.equals("BOOL")){
			fillcolor = "/pastel13/2";
		}
		
		else if(type.typeNode.equals("INT")){
			fillcolor = "/pastel13/3";
		}
		}
		//if(type.typeNode.equals("INT"))
		bgAST = BNFGrammarAST.getInstance();
		int graph_first_index = bgAST.graph_indexAST;
		idNode = new IdentNode("decl: "+"'"+ident.idName+"'", "decl");
		idNode.drawIdentNode(seek_index, fillcolor, "box");
		//typeNode =new TypeNode(type.typeNode);
		typeNode.drawTypeNode(graph_first_index);
		
		
		
	}
}
