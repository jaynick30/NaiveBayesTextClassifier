package text.test;

import java.io.FileNotFoundException;

import text.core.LabeledWords;

public class DecisionTreeTest1 {
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		DecisionTreeTester.test(args, LabeledWords::fromSentiment);
	}
}
