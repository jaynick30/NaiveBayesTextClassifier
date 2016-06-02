package text.test;

import java.io.FileNotFoundException;

import text.core.FarmLabeledWords;

public class KNearestFarmTest1 {	
	public static void main(String[] args) throws FileNotFoundException {
		KNearestFarmTester.test(args, FarmLabeledWords::fromSentiment);
	}
}
