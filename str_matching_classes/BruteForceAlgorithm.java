package str_matching_classes;

import java.io.PrintWriter;
//import java.util.Scanner;

public class BruteForceAlgorithm extends MatchingAlgorithms{
	PrintWriter pw;
	
	public BruteForceAlgorithm(PrintWriter pw) {
		this.pw = pw;
	}
	
	@Override
	public void search(String text) {
		long start = System.nanoTime();
		int j = 0; // text index, i will have shorter span
		int i;
		int matchLength = 0;
		do {
			for(i = 0, matchLength = 0; i < keyLength;) {
				if(keyPattern.charAt(i) == text.charAt(j)) {
					noOfComparisons++;
					matchLength++;
					i++; j++;
					if(matchLength == keyLength) {
						startingIndices.add(j - keyLength);
						instanceCount++;
						j = j - keyLength + 1; // for overlap cases
						break; 
					}
				}
				else {
					noOfComparisons++; // mismatch is still counted
					j = j - i + 1; // to shift window by 1 only
					break;
				}
			}
		} while(j <= textLength - keyLength);
		
		long end = System.nanoTime();
		timeElapsed = end - start;
	}
}
