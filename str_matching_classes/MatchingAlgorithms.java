package str_matching_classes;

// import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
// import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;

abstract class MatchingAlgorithms {
	PrintWriter pw;
	protected int noOfComparisons;
	protected int instanceCount;
	protected ArrayList<Integer> startingIndices = new ArrayList<>();
	protected long timeElapsed;
	protected static int keyLength;
 	protected static String keyPattern;
 	protected static long textLength;
	
	public static void main(String[] args) throws IOException{
		MatchingAlgorithms brute = new BruteForceAlgorithm(new PrintWriter("bruteOutput.html"));
		MatchingAlgorithms boyer = new BoyerMooresAlgorithm(new PrintWriter("boyerOutput.html"));
		MatchingAlgorithms horspool = new HorspoolAlgorithm(new PrintWriter("horspoolOutput.html"));
		MatchingAlgorithms algosArray[] = {brute, boyer, horspool};
		
		// text
		String text = Files.readString(Path.of("input.html"));		
		textLength = text.length();
        // key
		keyPattern = Files.readString(Path.of("key.txt"));
        keyLength = keyPattern.length();
        // search
		for(MatchingAlgorithms algo : algosArray){
            algo.search(text.toString());
			System.out.println("    " + algo.getClass().getSimpleName() + "    ");
            System.out.println("Occurrences  : " + algo.instanceCount);
            System.out.println("Comparisons  : " + algo.noOfComparisons);
            System.out.println("Time(ms)    : " + algo.timeElapsed/1000000.0);
            System.out.println();
            if(algo instanceof HorspoolAlgorithm) {
				printBadSymbolTable(HorspoolAlgorithm.badSymbolTable);
            }
            if(algo instanceof BoyerMooresAlgorithm) {
            	printGoodSuffixTable(((BoyerMooresAlgorithm)algo).goodSuffixTable);
				printBadSymbolTable(HorspoolAlgorithm.badSymbolTable);
            }
            StringBuilder copy = new StringBuilder(text.toString()); 
            highlightHtml(algo, copy);
		}
	}

	private static void highlightHtml(MatchingAlgorithms algo, StringBuilder sb){
		ArrayList<Integer> startingIndices = algo.startingIndices;
		String markStart = "<mark>";
		String markEnd = "</mark>";

		for(int i = startingIndices.size() - 1; i >= 0; i--) {
			sb.insert(startingIndices.get(i) + keyLength, markEnd);
			sb.insert(startingIndices.get(i), markStart);
		}
		algo.produceHtmlOutput(sb);
	}

	public static void printGoodSuffixTable(int[] goodTable) {
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
	
	/*TODO 32-126 seems verbose, try to find a way to
	 * print the chars that exist within the key +
	 * 1 more entry with the whole entry*/
	public static void printBadSymbolTable(int[] badTable) {
	    System.out.println("----- Bad Symbol Table -----");
	    System.out.println("Character | Shift");
	    System.out.println("---------------------------");

	    for (int i = 0; i < badTable.length; i++) {
	        if (i >= 32 && i <= 126) {
	            if (badTable[i] != keyLength || keyPattern.indexOf((char) i) != -1) {
	                System.out.printf("    '%c'    |%5d\n", (char) i, badTable[i]);
	            }
	        }
	    }
	    System.out.println("---------------------------");
	}
	
	protected void produceHtmlOutput(StringBuilder sb) {
		this.pw.write(sb.toString());
		this.pw.close();
	}
	
	/*filling in the required fields while searching for instances*/
	abstract protected void search(String text);
}
