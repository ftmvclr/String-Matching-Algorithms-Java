package stringmatchingclasses;

abstract class MatchingAlgorithms {
	protected int noOfComparisons;
	protected int instanceCount;
	protected int startingIndices[] = new int[128];
	
	public static void main(String[] args) {
		MatchingAlgorithms brute = new BruteForceAlgorithm();
		MatchingAlgorithms boyer = new BoyerMooresAlgorithm();
		MatchingAlgorithms horspool = new HorspoolAlgorithm();
		MatchingAlgorithms array[] = new MatchingAlgorithms[3];
		for(MatchingAlgorithms a : array ) {
			a.search();
		}
	}
	
	public abstract void search(); // this needs to be overridden by the sub classes
}
