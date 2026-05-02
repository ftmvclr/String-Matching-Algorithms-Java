package str_matching_classes;

import java.io.PrintWriter;

public class HorspoolAlgorithm extends MatchingAlgorithms {
	static int[] badSymbolTable;
    public HorspoolAlgorithm(PrintWriter pw) {
        this.pw = pw;
    }

    public static int[] badTable(String pattern) {
        badSymbolTable = new int[256];
        int p=pattern.length();
        for(int i=0; i<256; i++) {
            badSymbolTable[i]=p;
        }
        for(int i=0; i<p-1; i++) {
            badSymbolTable[pattern.charAt(i)] = p-1-i;
        }
        return badSymbolTable;
    }

    @Override
    protected void search(String text) {
        String pattern=keyPattern;
        int m=pattern.length();
        int n=text.length();
        int[] table=badTable(pattern);
        int i=m-1;
        while(i<n){
            int k=0;
            while(k<m){
                char patt= pattern.charAt(m-1-k);
                char tex= text.charAt(i-k);
                noOfComparisons++;
                if(patt!=tex){
                    break;
                }
                k++; 
            }
                if (k==m){
                    startingIndices.add(i-m+1);
                    instanceCount++;
                }
            i+=table[text.charAt(i)];
        }
    }
}