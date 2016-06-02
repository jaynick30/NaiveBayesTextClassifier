package text.core;

import search.core.Histogram;
import text.learners.FarmNaiveBayes;

public class FarmTestResult {
	private Histogram<String> correctForLabel;
	private Histogram<String> numberForLabel;
	
	public FarmTestResult(FarmTextLearner learner, FarmLabeledWords testSet) {
		try {
			correctForLabel = learner.test(testSet);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		numberForLabel = testSet.allCounts();
		
		System.out.println("TestResult: TotalCounts: " + numberForLabel.getTotalCounts() + " testSet: " + testSet.size());
	}
	
	public Iterable<String> allLabels() {return numberForLabel;}
	
	public int getCorrectFor(String lbl) {
		return correctForLabel.getCountFor(lbl);
	}
	
	public int getTotalFor(String lbl) {
		return numberForLabel.getCountFor(lbl);
	}
	
	public double getRatioFor(String lbl) {
		return (double)getCorrectFor(lbl) / getTotalFor(lbl);
	}
	
	public int getCorrect() {
		return correctForLabel.getTotalCounts();
	}
	
	public int getTotal() {
		return numberForLabel.getTotalCounts();
	}
	
	public double getRatio() {
		return (double)getCorrect() / getTotal();
	}
}
