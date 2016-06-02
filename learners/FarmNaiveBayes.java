package text.learners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import text.core.FarmTextLearner;
import text.core.Sentence;
import text.core.TextLearner;

public class FarmNaiveBayes implements FarmTextLearner {
	private HashSet<String> vocabulary = new HashSet<String>();
	private HashSet<String> positiveHashSet = new HashSet<String>();
	private HashSet<String> negativeHashSet = new HashSet<String>();
	
	private int positiveWordCount = 0;
	private int negativeWordCount = 0;
	private HashMap<String, Integer> positiveWordMapping = new HashMap<String, Integer>();
	private HashMap<String, Integer> negativeWordMapping = new HashMap<String, Integer>();

	@Override
	public void train(Sentence words, String lbl) {		
		
		Iterator<String> wordIter = words.iterator();
		while(wordIter.hasNext()){
			String nextWord = wordIter.next();
			nextWord = editWord(nextWord);
			vocabulary.add(nextWord);
			if(lbl.equals("POSITIVE")){
				positiveWordCount++;
				positiveHashSet.add(nextWord);
				positiveWordMapping.put(nextWord, positiveWordMapping.getOrDefault(nextWord, 0)+1);
				}
			else if(lbl.equals("NEGATIVE")){
				negativeWordCount++;
				negativeHashSet.add(nextWord);
				negativeWordMapping.put(nextWord, negativeWordMapping.getOrDefault(nextWord, 0)+1);
			}
		}
		
		
		// Count up the relevant values
	}
	
	private String editWord(String word){
		String newWord = word;
		newWord = newWord.replace(".", "");
		newWord = newWord.replace("!", "");
		newWord = newWord.replace(",", "");
		newWord = newWord.replace("(", "");
		newWord = newWord.replace(")", "");
		newWord = newWord.toLowerCase();
		return newWord;
		//return word;
	}
	
	private double getPositiveProbability(String word){
		double wordCount = positiveWordMapping.getOrDefault(word, 0);
		double probability =(wordCount+1.0)/(positiveWordCount + vocabulary.size());
		//System.out.printf("Word count: %f, positiveWordCount: %d, Vocab: %d ", wordCount, positiveWordCount, vocabulary.size());
		//System.out.println("Probability: " + probability);
		
		return probability;
	}
	
	private double getNegativeProbability(String word){
		double wordCount = negativeWordMapping.getOrDefault(word, 0);
		double probability = (wordCount+1.0)/(negativeWordCount + vocabulary.size());
		//System.out.printf("Word count: %f, negativeWordCount: %d, Vocab: %d ", wordCount, negativeWordCount, vocabulary.size());
		//System.out.println("Probability: " + probability); 
		
		return probability;
	}

	@Override
	public String classify(Sentence words) {
		// Use the counted values for classification.
		double positiveTotal = 1.0;
		double negativeTotal = 1.0;
		Iterator<String> wordIter = words.iterator();
		while(wordIter.hasNext()){
			String nextWord = wordIter.next();			
			nextWord = editWord(nextWord);
			vocabulary.add(nextWord);
			positiveTotal *= getPositiveProbability(nextWord);
			
			negativeTotal *= getNegativeProbability(nextWord);
			
		}
		
		if(positiveTotal > negativeTotal){
			this.train(words, "POSITIVE");
			return "POSITIVE";
		}
		else{
			this.train(words, "NEGATIVE");
			return "NEGATIVE";
		}
		/*else{
			System.out.println("Something is wrong:" + positiveTotal + " These should not be the same " + negativeTotal);
			return "IDK";
		}*/
		
	}
}
