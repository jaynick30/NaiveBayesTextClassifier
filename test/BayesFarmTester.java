package text.test;

import java.io.FileNotFoundException;

import text.learners.FarmNaiveBayes;

public class BayesFarmTester {
	public static void test(String[] args, FarmDataSetReader dataReader) throws FileNotFoundException {
		System.out.println("Bayes Tester is a go");
		FarmTester.test(args, dataReader, (train, test) -> FarmTester.conductTest("Farm Naive Bayes", new FarmNaiveBayes(), train, test));
	}
}
