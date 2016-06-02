package text.test;

import java.io.FileNotFoundException;

import text.core.LabeledWords;

public class KNearestTest1 {	
	public static void main(String[] args) throws FileNotFoundException {
		//BayesTester.test(args, LabeledWords::fromSentiment);
		KNearestTester.test(args, LabeledWords::fromSentiment);
	}
}
