/*
 * Consider the same anagrams problem with a given input word. 
 * How would you solve this problem given only the word and the dictionary? 
 * What if you could spend some time and space to process the dictionary before answering any queries?
 */

package com.pp.column2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AnagramForAWord {
	private HashMap<String, List<String>> dictionary;
	
	public AnagramForAWord(HashSet<String> dict) {
		dictionary = new HashMap<>();
		for(String word : dict) {			
			char[] charArray = word.toCharArray();
			Arrays.sort(charArray);
			String key = String.valueOf(charArray);
			if(!dictionary.containsKey(key)) {
				List<String> list = new ArrayList<>();
				list.add(word);
				dictionary.put(key, list);
			}
			else {
				dictionary.get(key).add(word);
			}
		}			
	}
	//Solution 1. simple solution without preprocessing the dictionary 
	//O(n * k) runtime, n is the number of words in the dictionary, k is the average length of each word
	//O(1) space 
	public List<String> getAllAnagramsOf1(HashSet<String> dict, String word) {
		List<String> anagrams = new ArrayList<String>();
		for(String s : dict) {
			if(isAnagrams(word, s)) {
				anagrams.add(s);
			}
		}
		return anagrams;
	}
	private boolean isAnagrams(String s1, String s2) {
		if(s1.length() != s2.length()) {
			return false;
		}
		int[] appearances = new int[26];
		for(char c : s1.toCharArray()) {
			appearances[c - 'a']++;
		}
		for(char c : s2.toCharArray()) {
			int i = c - 'a';
			appearances[i]--;
			if(appearances[i] < 0) {
				return false;
			}
		}
		return true;
	}
	
	//Solution 2, preprocess the dictionary.
	//The one time preprocessing takes O(n * k * logk) time, O(n * k) extra space
	//After preprocessing, each query takes O(k * logk) time, O(k) extra space
	//Clearly, if the given dictionary does not change and there will be many queries,
	//this solution is much better.
	public List<String> getAllAnagramsOf2(HashSet<String> dict, String word) {
		char[] charArray = word.toCharArray();
		Arrays.sort(charArray);
		String key = String.valueOf(charArray);
		List<String> anagrams = dictionary.get(key);	
		return anagrams == null ? new ArrayList<String>() : anagrams;
	}
}
