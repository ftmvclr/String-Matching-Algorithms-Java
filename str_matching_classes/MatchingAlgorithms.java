package str_matching_classes;

import java.util.Scanner;

abstract class MatchingAlgorithms {
	protected int noOfComparisons;
	protected int instanceCount;
	protected int startingIndices[] = new int[128];
	
	public static void main(String[] args){
		Scanner inputScanner = new Scanner();
		MatchingAlgorithms brute = new BruteForceAlgorithm();
		MatchingAlgorithms boyer = new BoyerMooresAlgorithm();
		MatchingAlgorithms horspool = new HorspoolAlgorithm();
		MatchingAlgorithms array[] = {brute, boyer, horspool};
		for(MatchingAlgorithms a : array ) {
			a.search();
			highlightHtml(a.startingIndices);
		}
	}
	private static void highlightHtml(int indices[]){
		
	}
	
	public abstract void search(); // this needs to be overridden by the sub classes
	// while searching it will adjust its own fields appropriately
	// could also fill the startingIndices array
}
