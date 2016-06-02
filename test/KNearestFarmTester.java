package text.test;

import java.io.FileNotFoundException;

import text.learners.FarmKNearestNeighbors;

public class KNearestFarmTester {
	public static void test(String[] args, FarmDataSetReader dataReader) throws FileNotFoundException {
		FarmTester.test(args, dataReader, (train, test) -> FarmTester.conductTest("Farm K-Nearest", new FarmKNearestNeighbors(), train, test));
	}
}
