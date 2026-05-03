package str_matching_classes;

import java.io.FileWriter;
// import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
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
 	static int cc;
	
	public static void main(String[] args) throws IOException{
		MatchingAlgorithms brute = new BruteForceAlgorithm(new PrintWriter("bruteOutput.html"));
		MatchingAlgorithms boyer = new BoyerMooresAlgorithm(new PrintWriter("boyerOutput.html"));
		MatchingAlgorithms horspool = new HorspoolAlgorithm(new PrintWriter("horspoolOutput.html"));
		MatchingAlgorithms algosArray[] = {brute, boyer, horspool};
		
		Scanner filenameinput = new Scanner(System.in);
		String filenamestring = filenameinput.nextLine();
		filenameinput.close();
		// text
		String text = Files.readString(Path.of(filenamestring));		
		textLength = text.length();
        // key
		keyPattern = Files.readString(Path.of("key.txt"));
        keyLength = keyPattern.length();
        // search
		for(MatchingAlgorithms algo : algosArray){
			long start = System.nanoTime();
            algo.search(text);
            long end = System.nanoTime();
            algo.timeElapsed = end - start;
            
            if(algo instanceof HorspoolAlgorithm) {
            	printStatistics(algo, "Report.txt", filenamestring);
				printBadSymbolTable(HorspoolAlgorithm.badSymbolTable, "Report.txt");
            }
            else if(algo instanceof BoyerMooresAlgorithm) {
            	printStatistics(algo, "Report.txt", filenamestring);
            	printGoodSuffixTable(((BoyerMooresAlgorithm)algo).goodSuffixTable, "Report.txt");
            	printBadSymbolTable(HorspoolAlgorithm.badSymbolTable, "Report.txt");
            }
            else
            	printStatistics(algo, "Report.txt", filenamestring);
            StringBuilder copy = new StringBuilder(text); 
            highlightHtml(algo, copy);
		}
	}
	private static void highlightHtml(MatchingAlgorithms algo, StringBuilder sb){
		ArrayList<Integer> startingIndices = algo.startingIndices;
		String markStart = "<mark>";
		String markEnd = "</mark>";

		for(int i = startingIndices.size() - 1; i >= 0; i--) {
			sb.insert(startingIndices.get(i) + keyLength, markEnd);
			for(;i != 0 && startingIndices.get(i) - startingIndices.get(i - 1) < keyLength;) { 
				i--; // loop until the beginning of the overlap
			}
			sb.insert(startingIndices.get(i), markStart);
			
		}
		algo.produceHtmlOutput(sb);
	}
	public static void printGoodSuffixTable(int[] goodTable, String filename) {
		// append? true
	    try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
	        writer.println("----- Good Suffix Table -----");
	        writer.println("Length (k) | Matched Suffix | Shift");
	        writer.println("-------------------------------");

	        for (int i = 0; i < goodTable.length; i++) {
	            int suffixLength = i + 1;
	            String suffix = keyPattern.substring(keyLength - suffixLength);
	            writer.printf("%10d | %-14s | %5d\n", suffixLength, "\"" + suffix + "\"", goodTable[i]);
	        }
	        writer.println("-------------------------------");
	        
	    } catch (IOException e) {
	    	System.err.println("exiting");
	    }
	}
	
	public static void printBadSymbolTable(int[] badTable, String fileName) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) { // append? true
			writer.println("----- Bad Symbol Table -----");
			writer.println(" Character |  Shift");
			writer.println("---------------------------");
			// traverse printables, print if the char is contained
			for (int i = 32; i <= 126; i++) { 
				if (badTable[i] != keyLength || keyPattern.indexOf((char) i) != -1) { 
					writer.printf("    '%c'    |%5d\n", (char) i, badTable[i]);
	            }
	        }
			writer.printf("    '%c'    |%5d\n", '*', keyLength);
	        writer.println("---------------------------");
	        
	    } catch (IOException e) {
	    	System.err.println("exiting");
	    }
	}
	
	public static void printStatistics(MatchingAlgorithms algo, String outFileName, String inFileName) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(outFileName, true))) { // append? true
			if(cc++ == 0)
				writer.printf("Searched Key: %s in the file: %s\n", keyPattern, inFileName);
			writer.println("    " + algo.getClass().getSimpleName() + "    ");
			writer.println("  Occurrences  : " + algo.instanceCount);
			writer.println("  Comparisons  : " + algo.noOfComparisons);
			writer.println("  Time(ms)     : " + algo.timeElapsed/1000000.0);
			writer.println("---------------------------");
		} catch (IOException e) {
	    	System.err.println("exiting");
		}
	}
	protected void produceHtmlOutput(StringBuilder sb) {
		pw.write(sb.toString());
		pw.close();
	}
	
	/*filling in the required fields while searching for instances*/
	abstract protected void search(String text);
}
