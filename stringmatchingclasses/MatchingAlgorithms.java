package stringmatchingclasses;

public class MatchingAlgorithms {
	public static void main(String[] args) {
		MatchingAlgorithms brute = new BruteForceAlgorithm();
		MatchingAlgorithms boyer = new BoyerMooresAlgorithm();
		MatchingAlgorithms horspool = new HorspoolAlgorithm();
	}
}
