package str_matching_classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

abstract class MatchingAlgorithms {
	PrintWriter pw;
	protected int noOfComparisons;
	protected int instanceCount;
	protected ArrayList<Integer> startingIndices = new ArrayList<>();
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
            if(a instanceof HorspoolAlgorithm) {
//            	printBadTable();
            }
            if(a instanceof BoyerMooresAlgorithm) {
//            	printGoodSuffixTable();
            }
            	
            highlightHtml(a, inputFile, text); 
		}
	}
	
	private static void highlightHtml(MatchingAlgorithms algo, File inputFile, StringBuilder sb){
		try(PrintWriter pw = new PrintWriter(inputFile);) {
			ArrayList<Integer> startingIndices = algo.startingIndices;
			String markStart = "<mark>";
			String markEnd = "</mark>";
			for(int i = startingIndices.size() - 1; i >= 0; i--) {
				sb.insert(startingIndices.get(i) + keyLength, markEnd);
				sb.insert(startingIndices.get(i), markStart);
			}
			algo.produceHtmlOutput(sb);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

/*	public static void printGoodSuffixTable(int[] goodTable) {
	    System.out.println("----- Good Suffix Table -----");
	    System.out.println("Length (k) | Matched Suffix | Shift");
	    System.out.println("-------------------------------");

	    for (int i = 0; i < goodTable.length; i++) {
	        int suffixLength = i + 1;
	        String suffix = keyPattern.substring(keyLength - suffixLength);
	        System.out.printf("%10d | %-14s | %5d\n", suffixLength, "\"" + suffix + "\"", goodTable[i]);
	    }
	    System.out.println("-------------------------------");
	}
*/	
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
