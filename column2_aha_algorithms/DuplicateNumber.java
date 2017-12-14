/*
 * Q: Given a sequential file containing 4,300,000,000 32-bit integers, how can you find one that
 * appears at least twice? 
 * 
 * A: If we have ample main memory(17.2 Gigabyte), then a simple solution is to read all integers 
 * into memory, then sort it and do a linear scan to find one duplicated integer. 
 * 
 * With enough main memory, a better solution is to use the bitmap algorithm.
 * Create a bitmap bits[i] of size 2^32 / 8 bytes, each bit at index i represents whether an integer
 * exists in the input file or not. 
 * 
 * If all integers are unsigned, then the indices of the bitmap are the integers. If integers can be
 * signed, then an offset value is needed for each integer, bits[0] represents the existence of -2^31.
 * 
 * Scan the input file and set each integer's bitmap representation. If an integer's bit map value is
 * already set, we just found one duplicated integer.
 * 
 * However, if we do not have that much memory, but have some scratch files to work with, then we can
 * apply the same principle of finding the missing number, constructing one duplicated integer bit 
 * by bit. 
 * 
 * Algorithm:
 * 1. scan all integers in the file, put integers whose MSB is 0 in one file, integers whose MSB is 1
 * in another file.The file with more integers in it must have at least one duplicated integers.
 * 
 * 2. pick the bigger file then repeat the same process for the 2nd MSB, repeat this process until  
 * after processing LSB, pick the bigger file and get any one of the integers(they are all the same)
 * and return it as one duplicated integers
 * 
 * Performance analysis:
 * Unlike finding a missing number, where we always pick the smaller size file to process next and have
 * a guarantee of n/2 upper bound at each iteration, the above binary search algorithm does not provide 
 * a n/2 upper bound as we pick the bigger size file. The worst case will be O(n) integers are scanned 
 * at each iteration, there are logn passes for all bits, so the runtime is O(n * logn), not O(n)
 * 
 * Optimization:
 * Each time we partition all integers into two piles, we are putting them into two equal sized range, 
 * as long as we have more integers than the whole range, we are guaranteed to have at least one duplicate,
 * so for each one of the two piles, we can safely stop accepting new numbers once its size is the range + 1.
 * This modification halves the input file size each step, thus gives the O(n) runtime.
 */

package com.pp.column2;

public class DuplicateNumber {
	//Pseudo definition to represent the input file
	//The actual methods do not work 
	private static final long RANGE = 4294967296L;
	private static class IntegerFile {
		long size;
		
		boolean hasNext() {
			return false;
		}
		
		int getNext() {
			return 0;
		}
		void write(int v) {
			
		}
	}	
	
	public static int FindOneDuplicateInteger(IntegerFile file) {
		IntegerFile file0 = new IntegerFile(); 
		IntegerFile file1 = new IntegerFile();
		IntegerFile currFile = file;
		int i = 1;
		int bitIdx = 0;
		
		while(bitIdx < 32) {
			i = (i << bitIdx);
			while(currFile.hasNext()) {
				int val = currFile.getNext();
				if((val & i) == 0) {
					file0.write(val);
					if(file0.size > (RANGE / (2 * i))) {
						currFile = file0;
						break;
					}
				}
				else {
					file1.write(val);
					if(file1.size > (RANGE / (2 * i))) {
						currFile = file1;
						break;
					}
				}
			}
			bitIdx++;
		}
		return currFile.getNext();
	}
	
}
