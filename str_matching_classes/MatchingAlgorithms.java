package str_matching_classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

abstract class MatchingAlgorithms {
	PrintWriter pw;
	protected int noOfComparisons;
	protected int instanceCount;
	protected int startingIndices[] = new int[1024];
	protected long timeElapsed;
	protected static int keyLength;
 	protected String keyPattern;
	
	public static void main(String[] args) throws FileNotFoundException{
		File inputFile = new File("input.html");
		MatchingAlgorithms brute = new BruteForceAlgorithm(new PrintWriter("bruteOutput.html"));
		MatchingAlgorithms boyer = new BoyerMooresAlgorithm(new PrintWriter("bruteOutput.html"));
		MatchingAlgorithms horspool = new HorspoolAlgorithm(new PrintWriter("bruteOutput.html"));
		MatchingAlgorithms algosArray[] = {brute, boyer, horspool};
		
		Scanner inputScanner = new Scanner(inputFile);
		StringBuilder sb = new StringBuilder();
		while (inputScanner.hasNextLine()) {
			sb.append(inputScanner.nextLine());
			sb.append("\n"); // TODO check if \r\n is a requirement 
			for(MatchingAlgorithms a : algosArray){
	            a.search(sb.toString());
	            highlightHtml(a, inputFile, sb); 
	            inputScanner.close();
			}
		}
	}
	// needs key length and a for loop that goes from 0 to instance count
	// loop this backward for indices' not to move
	private static void highlightHtml(MatchingAlgorithms algo, File inputFile, StringBuilder sb){
		try(PrintWriter pw = new PrintWriter(inputFile);) {
			int[] startingIndices = algo.startingIndices;
			String markStart = "<mark>";
			String markEnd = "</mark>";
			for(int i = algo.instanceCount - 1; i >= 0; i--) { // as long as instanceCount isnt > 1024
				sb.insert(startingIndices[i] + keyLength, markEnd);
				sb.insert(startingIndices[i], markStart);
			}
			algo.produceHtmlOutput(sb);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected void produceHtmlOutput(StringBuilder sb) {
		this.pw.write(sb.toString());
	}
	
	abstract protected void search(String text); // this needs to be overridden by the sub classes
	// while searching it will adjust its own fields appropriately
	// could also fill the startingIndices array
}
