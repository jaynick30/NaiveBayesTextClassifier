package text.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import search.core.Duple;
import search.core.Funcs;
import search.core.Histogram;

public class FarmLabeledWords implements Iterable<String> {
	private ArrayList<Duple<String,Sentence>> sentences = new ArrayList<>();
	private Histogram<String> countsFor = new Histogram<>();
	
	public FarmLabeledWords() {}
	
	public static FarmLabeledWords fromSentiment(File src) throws FileNotFoundException {
		FarmLabeledWords set = new FarmLabeledWords();
		String text = Funcs.fromFile(src);
		text.replaceAll(".", "");
		for (String line: text.split("\\n")) {
			String[] info = line.split("\\t");
			//System.out.println("Before info " + info[0]);
			//info[0] = editWord(info[0]);
			//System.out.println("New info " + info[0]);
			double rand = Math.random();
			int neg = -1;
			if(info[0].charAt(0) == String.valueOf(neg).charAt(0)){
				if(rand >= 0.5){
					info = randomizeText(info);
				}
			}
			if(rand >= 0.5){
				info = randomizeText(info);
			}
			if (info.length >= 1 && info[0].charAt(0) != '-') {
				String lbl = info[0].charAt(0) == '1' ? "POSITIVE" : "NEGATIVE";
				String sent = info.toString().substring(2);
				set.add(new Sentence(sent), lbl);
			}
			else{
				info[0] = info[0].toString().substring(2);
			}
		}
		return set;
	}
	
	private static String editWord(String word){
		String newWord = word;
		newWord = newWord.replace(".", "");
		newWord = newWord.replace("ad-", "");
		newWord = newWord.replace("!", "");
		newWord = newWord.replace(",", "");
		newWord = newWord.toLowerCase();
		return newWord;
	}
	
	private static String[] randomizeText(String[] info){
		info[0] = info.toString().replace(info[0].charAt(0), '-');		
		
		return info;
	}
	
	public ArrayList<Sentence> allWith(String lbl) {
		ArrayList<Sentence> result = new ArrayList<>();
		for (Duple<String,Sentence> dup: sentences) {
			if (dup.getFirst().equals(lbl)) {
				result.add(dup.getSecond());
			}
		}
		return result;
	}
	
	public void add(Sentence words, String lbl) {
		sentences.add(new Duple<>(lbl, words));
		countsFor.bump(lbl);
	}
	
	public String getLabel(int i) {return sentences.get(i).getFirst();}
	
	public Sentence getWords(int i) {return sentences.get(i).getSecond();}
	
	public int size() {return sentences.size();}
	
	public int numWith(String lbl) {
		return countsFor.getCountFor(lbl);
	}
	
	public Histogram<String> allCounts() {return new Histogram<>(countsFor);}

	public Duple<FarmLabeledWords,FarmLabeledWords> split() {
		FarmLabeledWords even = new FarmLabeledWords();
		FarmLabeledWords odd = new FarmLabeledWords();
		for (String lbl: countsFor) {
			ArrayList<Sentence> sentences = allWith(lbl);
			for (int i = 0; i < sentences.size(); i++) {
				FarmLabeledWords target = i % 2 == 0 ? even : odd;
				target.add(sentences.get(i), lbl);
			}
		}
		return new Duple<>(even, odd);
	}

	@Override
	public Iterator<String> iterator() {
		return countsFor.iterator();
	}
}
