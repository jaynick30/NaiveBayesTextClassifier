package text.test;

import java.io.FileNotFoundException;

import text.learners.DecisionTree;
import text.learners.KNearestNeighbors;

public class DecisionTreeTester {
	public static void test(String[] args, DataSetReader dataReader) throws FileNotFoundException, InterruptedException {
		System.out.println("Bayes Tester is a go");
		SimpleTester.test(args, dataReader, (train, test) -> SimpleTester.conductTest("Decision Tree", new DecisionTree(), train, test));
	}
}