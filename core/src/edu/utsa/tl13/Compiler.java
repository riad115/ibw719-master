package edu.utsa.tl13;

import java.io.IOException;


public class Compiler {
    public static void main (String[] args) throws IOException {
        String inputFileName = args[0];
        int baseNameOffset = inputFileName.length() - 5;

        String baseName;
        if (inputFileName.substring(baseNameOffset).equals(".tl13"))
            baseName = inputFileName.substring(0,baseNameOffset);
        else
            throw new RuntimeException("inputFileName does not end in .tl13");

        String parseOutName = baseName + ".ast.dot";
        String ilocOutName = baseName + ".iloc.cfg.dot";
        String mipsOutName = baseName + ".s";

	System.out.println("Input file: " + inputFileName);
	System.out.println("AST Output file: " + parseOutName);
	System.out.println("ILOC Output file: " + ilocOutName);
	System.out.println("MIPS Output file: " + mipsOutName);
	//FileReader tl12In = new FileReader(inputFileName);
	//OutputStream parseOut = new FileOutputStream(parseOutName);
	
	Parser parser = new Parser();
	parser.parser(inputFileName, parseOutName);
	//BNFGrammar.BNFGrammarStructure(parseOutName);

    }
}
