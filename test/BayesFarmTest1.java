package text.test;

import java.io.FileNotFoundException;

import text.core.FarmLabeledWords;

public class BayesFarmTest1 {	
	public static void main(String[] args) throws FileNotFoundException {
		//BayesTester.test(args, LabeledWords::fromSentiment);
		BayesFarmTester.test(args, FarmLabeledWords::fromSentiment);
	}
}
