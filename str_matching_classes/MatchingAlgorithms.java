package str_matching_classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

abstract class MatchingAlgorithms {
	protected int noOfComparisons;
	protected int instanceCount;
	protected int startingIndices[] = new int[128];
	
	public static void main(String[] args){
		File inputFile = new File("input.html");
		
		MatchingAlgorithms brute = new BruteForceAlgorithm();
		MatchingAlgorithms boyer = new BoyerMooresAlgorithm();
		MatchingAlgorithms horspool = new HorspoolAlgorithm();
		MatchingAlgorithms array[] = {brute, boyer, horspool};
		
		for(MatchingAlgorithms a : array){
			try (Scanner inputScanner = new Scanner(inputFile)){
	            a.search(inputScanner);
	            highlightHtml(a.startingIndices, inputFile);   
	        } 
			catch (FileNotFoundException e){
	            System.err.println("Error: Could not find the file!");
	            e.printStackTrace();
	            System.exit(1);
	        }
		}
	}
	private static void highlightHtml(int indices[], File inputFile){
		try(PrintWriter pw = new PrintWriter(inputFile);) {
			// needs key length and a for loop that goes from 0 to instance count
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void search(Scanner scanner); // this needs to be overridden by the sub classes
	// while searching it will adjust its own fields appropriately
	// could also fill the startingIndices array
}
