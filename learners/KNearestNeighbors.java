package text.learners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.SortedSet;

import search.core.Histogram;
import text.core.Sentence;
import text.core.TextLearner;

public class KNearestNeighbors implements TextLearner{
	
	private Histogram<String> bagOfWords = new Histogram<String>();
	private HashSet<String> vocabulary = new HashSet<String>();
	private HashMap<Sentence, String> positiveSentenceMap = new HashMap<Sentence, String>();
	private HashMap<Sentence, String> negativeSentenceMap = new HashMap<Sentence, String>();
	private SortedSet<Integer> kNeighborSet;
	private HashMap<String, Integer> positiveWordMapping = new HashMap<String, Integer>();
	private HashMap<String, Integer> negativeWordMapping = new HashMap<String, Integer>();

	@Override
	public void train(Sentence words, String lbl) {
		if(lbl.equals("POSITIVE")){
			positiveSentenceMap.put(words, lbl);
		}
		else{
			negativeSentenceMap.put(words, lbl);
		}
		Iterator<String> wordIter = words.iterator();
		while(wordIter.hasNext()){
			String nextWord = wordIter.next();
			vocabulary.add(nextWord);
			bagOfWords.bump(nextWord);
			if(lbl.equals("POSITIVE")){
				if(positiveWordMapping.containsKey(nextWord)){
					positiveWordMapping.put(nextWord, positiveWordMapping.get(nextWord)+1);
				}
				else{
					positiveWordMapping.put(nextWord, 1);
				}
			}
			else if(lbl.equals("NEGATIVE")){
				if(negativeWordMapping.containsKey(nextWord)){
					negativeWordMapping.put(nextWord, negativeWordMapping.get(nextWord)+1);
				}
				else{
					negativeWordMapping.put(nextWord, 1);
				} 
			}
			else{
				System.out.println("SOMETHING ISN'T RIGHT HERE");
				
			}
		}
		
	}
	
	

	@Override
	public String classify(Sentence words) {
		Iterator<String> wordIter = words.iterator();
		int sameness = 0;
		int totalSameness = 0;
		while(wordIter.hasNext()){
			String nextWord = wordIter.next();
			sameness += positiveWordMapping.getOrDefault(nextWord, 0);
			sameness -= negativeWordMapping.getOrDefault(nextWord, 0);
			totalSameness += sameness;
		}
		if(totalSameness > 0){
			return "POSITIVE";
		}
		else{
			return "NEGATIVE";
		}
	}

}
