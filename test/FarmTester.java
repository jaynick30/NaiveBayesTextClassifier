package text.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.function.BiConsumer;

import search.core.Duple;
import text.core.FarmLabeledWords;
import text.core.FarmTestResult;
import text.core.FarmTextLearner;
import text.learners.FarmNaiveBayes;

public class FarmTester {
	public static void test(String[] args, FarmDataSetReader dataReader, BiConsumer<FarmLabeledWords,FarmLabeledWords> testDriver) throws FileNotFoundException {
		if (args.length == 0) {
			System.out.println("Usage: SimpleTester [filenames]");
			System.exit(1);
		}
		
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
			File input = new File(args[i]);
			Duple<FarmLabeledWords, FarmLabeledWords> data = dataReader.read(input).split();
			FarmLabeledWords train = data.getFirst();
			FarmLabeledWords test = data.getSecond();

			System.out.println("Training size: " + train.size());
			System.out.println("Test size: " + test.size());

			testDriver.accept(train, test);
		}
	}
	
	public static void conductTest(String title, FarmTextLearner learner, FarmLabeledWords train, FarmLabeledWords test) {
		System.out.println("Conduncting Test");
		learner.train(train);
		FarmTestResult result = new FarmTestResult(learner, test);
		show(title, result.getCorrect(), result.getTotal());
		for (String lbl: result.allLabels()) {
			show(lbl.toString(), result.getCorrectFor(lbl), result.getTotalFor(lbl));
		}
	}

	public static void show(String label, int correct, int total) {
		double ratio = 100.0 * correct / total;
		System.out.printf("%s: %d/%d (%4.2f%%)\n", label, correct, total, ratio);
	}
}
