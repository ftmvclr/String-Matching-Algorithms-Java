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
	
	public static void main(String[] args) throws FileNotFoundException{
		File inputFile = new File("input.html");
		MatchingAlgorithms brute = new BruteForceAlgorithm(new PrintWriter("bruteOutput.html"));
		MatchingAlgorithms boyer = new BoyerMooresAlgorithm(new PrintWriter("bruteOutput.html"));
		MatchingAlgorithms horspool = new HorspoolAlgorithm(new PrintWriter("bruteOutput.html"));
		MatchingAlgorithms algosArray[] = {brute, boyer, horspool};
		
		for(MatchingAlgorithms a : algosArray){
			Scanner inputScanner = new Scanner(inputFile);
	            a.search(inputScanner);
	            highlightHtml(a, inputFile); 
	            inputScanner.close();
		}
	}
	// needs key length and a for loop that goes from 0 to instance count
	// loop this backward for indices' not to move
	private static void highlightHtml(MatchingAlgorithms algo, File inputFile){
		try(PrintWriter pw = new PrintWriter(inputFile);) {
			int[] startingIndices = algo.startingIndices;
			String markStart = "<mark>";
			String markEnd = "</mark>";
			StringBuilder sb = new StringBuilder();
			Scanner scanner = new Scanner(inputFile);
			while (scanner.hasNextLine()) {
		        sb.append(scanner.nextLine());
		        sb.append("\n"); // TODO check if \r\n is a requirement 
		    }
			for(int i = algo.instanceCount - 1; i >= 0; i--) { // as long as instanceCount isnt > 1024
				sb.insert(startingIndices[i] + keyLength, markEnd);
				sb.insert(startingIndices[i], markStart);
			}
			scanner.close();
			algo.produceHtmlOutput(sb);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected void produceHtmlOutput(StringBuilder sb) {
		this.pw.write(sb.toString());
	}
	
	protected abstract void search(Scanner scanner); // this needs to be overridden by the sub classes
	// while searching it will adjust its own fields appropriately
	// could also fill the startingIndices array
}
