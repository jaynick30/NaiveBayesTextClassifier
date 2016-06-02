package text.test;

import java.io.FileNotFoundException;

import text.learners.KNearestNeighbors;
import text.learners.NaiveBayes;

public class KNearestTester {
	public static void test(String[] args, DataSetReader dataReader) throws FileNotFoundException {
		System.out.println("Bayes Tester is a gogogo");
		SimpleTester.test(args, dataReader, (train, test) -> SimpleTester.conductTest("K-Nearest Neighbor", new KNearestNeighbors(), train, test));
	}
}