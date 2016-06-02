package text.core;

import search.core.Histogram;

public interface FarmTextLearner {
	public void train(Sentence words, String lbl);
	public String classify(Sentence words) throws InterruptedException;
	
	default public void train(FarmLabeledWords trainingSet) {
		for (int i = 0; i < trainingSet.size(); i++) {
			train(trainingSet.getWords(i), trainingSet.getLabel(i));
		}
	}
	
	default public Histogram<String> test(FarmLabeledWords testSet) throws InterruptedException {
		Histogram<String> correct = new Histogram<>();
		for (int i = 0; i < testSet.size(); i++) {
			if (classify(testSet.getWords(i)) == testSet.getLabel(i)) {
				correct.bump(testSet.getLabel(i));
			}
		}
		return correct;
	}
}
