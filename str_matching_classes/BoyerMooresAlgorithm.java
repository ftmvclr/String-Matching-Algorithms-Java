package str_matching_classes;

import java.io.PrintWriter;
import java.util.Arrays;


public class BoyerMooresAlgorithm extends MatchingAlgorithms {
	int[] goodSuffixTable;
	int[] badSymbolTable;
	int fullMatchShift;

	public BoyerMooresAlgorithm(PrintWriter pw) {
		this.pw = pw;
	}

	// d = max(d1,d2) d2-> goodTable Result
	// d1 = max(t1(c) - k, 1)
	@Override
	protected void search(String text) {
		goodSuffixTable = goodTable(keyPattern);
		badSymbolTable = HorspoolAlgorithm.badTable(keyPattern);

		fullMatchShift = 1;
		for (int len = keyLength - 1; len > 0; len--) {
			if (keyPattern.substring(0, len).equals(keyPattern.substring(keyLength - len))) {
				fullMatchShift = keyLength - len;
				break;
			}
		}
		// more optimization
		char[] textChars = text.toCharArray();
		char[] patternChars = keyPattern.toCharArray();

		int index = keyLength - 1;
		int textLength = text.length(); // optimization
		while (index < textLength) {
			int keyIndex = keyLength - 1;
			int k = 0;

			for (int i = 0; i < keyLength; i++) {
				this.noOfComparisons++;

				if (textChars[index] == patternChars[keyIndex]){
					k++;
					index--;
					keyIndex--;
					if(k == keyLength){ // key is found
						startingIndices.add(index + 1);
						instanceCount++;
						index += (k + fullMatchShift);
						break;
					}
				} else {
					int d1 = Math.max(badSymbolTable[textChars[index]] - k, 1);
					int d2 = k > 0 ? goodSuffixTable[k - 1] : 0;
					int shift_value = d1 >= d2 ? d1 : d2;
					index += shift_value + k;
					break;
				}
			}
		}
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
				} else {
					fromIndex = index - 1;
				}
			}
		}
		return goodTable;
	}
}
