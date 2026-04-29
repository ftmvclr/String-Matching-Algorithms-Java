package str_matching_classes;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class BoyerMooresAlgorithm extends MatchingAlgorithms {
	PrintWriter pw;
	
	public BoyerMooresAlgorithm(PrintWriter pw) {
		pw = this.pw;
	}
	@Override
	public void search(Scanner scanner) {
		
	}

	public int[] goodTable(String key) {
		char previousLetter;
		int keyLength = key.length();
		String subString;
		int[] goodTable = new int[keyLength - 1];
		Arrays.fill(goodTable, keyLength);
		for (int i = 1; i < keyLength; i++) {
			subString = key.substring(keyLength - i, keyLength);
			previousLetter = key.charAt(keyLength - i -1);
			int fromIndex = keyLength - i - 1;

			while(true) {
				int index = key.lastIndexOf(subString, fromIndex);

				if(index == -1) {
					int subStringLength = subString.length();
					int count = 0;
					for(int len = subStringLength - 1; len > 0; len--) {
						if (key.substring(0,len).equals(subString.substring(subStringLength - len, subStringLength))) {
							count = len;
							break;
						}
					}
					goodTable[i - 1] = keyLength - count;
					break;
				}
				if(index == 0) {
					goodTable[i - 1] = keyLength - i;
					break;
				}
				if(previousLetter != key.charAt(index - 1)) {
					goodTable[i - 1] = keyLength - index - i;
					break;
				} else  {
					fromIndex = index - 1;
				}
			}
		}
		return goodTable;
	}

}
