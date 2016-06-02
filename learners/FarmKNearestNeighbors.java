package text.learners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.SortedSet;

import search.core.Histogram;
import text.core.FarmTextLearner;
import text.core.Sentence;

public class FarmKNearestNeighbors implements FarmTextLearner{
	
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
		newWord = newWord.replace("ad-", "");
		newWord = newWord.replace("!", "");
		newWord = newWord.replace(",", "");
		newWord = newWord.replace("(", "");
		newWord = newWord.replace(")", "");
		newWord = newWord.toLowerCase();
		return newWord;
	}
	
	

	@Override
	public String classify(Sentence words) {
		Iterator<String> wordIter = words.iterator();
		int sameness = 1;
		int totalSameness = 1;
		while(wordIter.hasNext()){
			String nextWord = wordIter.next();
			nextWord = editWord(nextWord);
			//System.out.println("word is " + nextWord);
			System.out.println();
			System.out.println("positive " + positiveWordMapping);
			System.out.println("negative" + negativeWordMapping);
			System.out.println();
			if(positiveWordMapping.containsKey(nextWord) || negativeWordMapping.containsKey(nextWord)){
				System.out.println("Yay");
				sameness += positiveWordMapping.getOrDefault(nextWord, 0);
				sameness -= negativeWordMapping.getOrDefault(nextWord, 0);
				totalSameness *= sameness;
			}
			else{
				//System.out.println("No idea what to do");
			}
		}
		//System.out.println("Total " + totalSameness);
		if(totalSameness > 0){
			return "POSITIVE";
		}
		else{
			return "NEGATIVE";
		}
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

}
