package com.pp.column2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/*
 * Given a dictionary of English words, find all sets of anagrams. For instance,
 * pots, stop and tops are all anagrams of one another because each can be formed 
 * by permuting the letters of the others.
 * 
 * You can assume that only lower case letters are used and there are no duplicated words.
 * The result list should be in sorted order using words' canonical forms.
 */
public class Anagrams {
	
	//Solution 1, using hash map
	//O(n * (k + k * logk + k + c) + n + n * logn + n) ~ O(n * (k * logk + logn)) runtime
	//O(n * k + k + n * k) ~ O(n * k) space, n is the number of words, k is the average length
	//of all words
	public static List<List<String>> getAllAnagramsHashMap(HashSet<String> words) {
		List<List<String>> anagrams = new ArrayList<>();
		if(words == null || words.size() == 0) {
			return anagrams;
		}
		HashMap<String, List<String>> map = new HashMap<>();
		for(String word : words) {			
			char[] charArray = word.toCharArray();
			Arrays.sort(charArray);
			String key = String.valueOf(charArray);
			if(!map.containsKey(key)) {
				List<String> list = new ArrayList<>();
				list.add(word);
				map.put(key, list);
			}
			else {
				map.get(key).add(word);
			}
		}
		List<String> keys = new ArrayList<>(map.keySet());
		Collections.sort(keys);
		for(int i = 0; i < keys.size(); i++) {
			anagrams.add(map.get(keys.get(i)));
		}
		return anagrams;
	}
	
	//Solution 2, using tree map
	//O(n * (k + k * logk + k + logn) + n) ~ O(n * (k * logk + logn)) runtime
	//O(n * k + k) ~ O(n * k) space
	public static List<List<String>> getAllAnagramsTreeMap(HashSet<String> words) {
		List<List<String>> anagrams = new ArrayList<>();
		if(words == null || words.size() == 0) {
			return anagrams;
		}
		TreeMap<String, List<String>> map = new TreeMap<>();
		for(String word : words) {			
			char[] charArray = word.toCharArray();
			Arrays.sort(charArray);
			String key = String.valueOf(charArray);
			if(!map.containsKey(key)) {
				List<String> list = new ArrayList<>();
				list.add(word);
				map.put(key, list);
			}
			else {
				map.get(key).add(word);
			}
		}
		Iterator<String> iterator = map.navigableKeySet().iterator();
		while(iterator.hasNext()) {
			anagrams.add(map.get(iterator.next()));
		}
		return anagrams;		
	}
	
	//Solution 3, using custom data type
	private static class WordWithKey implements Comparable<WordWithKey>{
		String key;
		String word;
		WordWithKey(String key, String word) {
			this.key = key;
			this.word = word;
		}
		public int compareTo(WordWithKey w) {
			return key.compareTo(w.key);
		}
	}
	public static List<List<String>> getAllAnagrams(HashSet<String> words) {
		List<List<String>> anagrams = new ArrayList<>();
		if(words == null || words.size() == 0) {
			return anagrams;
		}
		List<WordWithKey> wordsWithKeys = new ArrayList<WordWithKey>();
		for(String word : words) {
			char[] charArray = word.toCharArray();
			Arrays.sort(charArray);
			String key = String.valueOf(charArray);
			wordsWithKeys.add(new WordWithKey(key, word));
		}
		Collections.sort(wordsWithKeys);
		List<String> list = new ArrayList<>();
		list.add(wordsWithKeys.get(0).word);
		for(int i = 1; i < wordsWithKeys.size(); i++) {
			WordWithKey curr_wk = wordsWithKeys.get(i);
			WordWithKey prev_wk = wordsWithKeys.get(i - 1);
			if(!curr_wk.key.equals(prev_wk.key)) {
				anagrams.add(list);
				list = new ArrayList<String>();
			}
			list.add(curr_wk.word);
		}
		anagrams.add(list);
		return anagrams;
	}
}
