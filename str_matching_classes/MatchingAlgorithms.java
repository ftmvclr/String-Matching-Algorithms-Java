package str_matching_classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

abstract class MatchingAlgorithms {
	PrintWriter pw;
	protected int noOfComparisons;
	protected int instanceCount;
	protected int startingIndices[] = new int[1024]; // should we use array lists instead?
	protected long timeElapsed;
	protected static int keyLength;
 	protected static String keyPattern;
 	protected static long textLength;
	
	public static void main(String[] args) throws FileNotFoundException{
		File inputFile = new File("input.html");
		File patternFile = new File("key.txt");
		MatchingAlgorithms brute = new BruteForceAlgorithm(new PrintWriter("bruteOutput.html"));
		MatchingAlgorithms boyer = new BoyerMooresAlgorithm(new PrintWriter("boyerOutput.html"));
		MatchingAlgorithms horspool = new HorspoolAlgorithm(new PrintWriter("horspoolOutput.html"));
		MatchingAlgorithms algosArray[] = {brute, boyer, horspool};
		
		Scanner inputScanner = new Scanner(inputFile);
		Scanner patternScanner = new Scanner(patternFile);
		// text
		StringBuilder text = stringBuilderBuilder(inputScanner);
		textLength = text.toString().length();
        // key
        StringBuilder key = stringBuilderBuilder(patternScanner);
        keyPattern = key.toString();
        keyLength = key.length();
        // search
		for(MatchingAlgorithms a : algosArray){
            a.search(text.toString());
            highlightHtml(a, inputFile, text); 
		}
	}
	
	private static void highlightHtml(MatchingAlgorithms algo, File inputFile, StringBuilder sb){
		try(PrintWriter pw = new PrintWriter(inputFile);) {
			int[] startingIndices = algo.startingIndices;
			String markStart = "<mark>";
			String markEnd = "</mark>";
			for(int i = algo.instanceCount - 1; i >= 0; i--) { // as long as instanceCount isn't > 1024
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
	
	private static StringBuilder stringBuilderBuilder(Scanner inputScanner) {
		StringBuilder sb = new StringBuilder();
		while (inputScanner.hasNextLine()) {
			sb.append(inputScanner.nextLine());
			sb.append("\n"); // TODO check if \r\n is a requirement 
		}
		inputScanner.close();
		return sb;
	}
	/*filling in the required fields while searching for instances*/
	abstract protected void search(String text);
}
