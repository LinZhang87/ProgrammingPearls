/*
 * Given a sequential file that contains at most four billion 32-bit integers in random order,
 * find a 32-bit integer that isn't in the file. How would you solve this problem with ample 
 * quantities of main memory? How would you solve it if you could use several external "scratch"
 * files but only a few hundred bytes of main memory? 
 * 
 * 
 * 
 */

import java.util.BitSet;
public class BinarySearchMissingNumber {
	//Pseudo definition to represent the input file
	//The actual methods do not work 
	private static class IntegerFile {
		int size;
		
		boolean hasNext() {
			return false;
		}
		
		int getNext() {
			return 0;
		}
		void write(int v) {
			
		}
	}
	//Solution with ample quantities of main memory
	//O(n) runtime, O(n) space 
	public static int findMissingBitMap(IntegerFile file) {
		BitSet bitmap = new BitSet(file.size);
		for(int i = 0; i < bitmap.size(); i++) {
			bitmap.clear(i);
		}
		while(file.hasNext()) {
			int val = file.getNext();
			bitmap.set(val);
		}
		int missing;
		for(missing = 0; missing < bitmap.size(); missing++) {
			if(bitmap.get(missing) == false) {
				break;
			}
		}
		return missing;
	}
	
	//Solution with several scratch files but only a few hundred bytes of main memory
	//Runtime: n + n / 2 + n / 4 + ...... + 1 ~ O(n)
	//Space: O(1) 
	public static int findMissingBinarySearch(IntegerFile file) {
		IntegerFile scratchFile0 = new IntegerFile();
		IntegerFile scratchFile1 = new IntegerFile();
		IntegerFile currFile = file;
		BitSet missingBits = new BitSet(32);
		int i = 1;
		int bitIdx = 0;
		do {
			//split integers into two piles based on integers' bit bitIdx is 0 or 1 
			i = (i << bitIdx);
			while(currFile.hasNext()) {
				int val = currFile.getNext();
				if((val & i) == 0) {
					scratchFile0.write(val);
				}
				else {
					scratchFile1.write(val);
				}
			}
			//repeat the same splitting process on the smaller file using the next higher bit
			//smaller file is guaranteed to have at least one missing integer
			if(scratchFile0.size < scratchFile1.size) {
				currFile = scratchFile0;
				missingBits.clear(31 - bitIdx);
			}
			else {
				currFile = scratchFile1;
				missingBits.set(31 - bitIdx);
			}	
			bitIdx++;
		} while(currFile.size != 0);
		
		//convert binary bits to integer
		int missing = 0;
		for(int j = 31; j >= 0; j--) {
			if(missingBits.get(j)) {
				missing = (missing | (1 << (31 - j)));
			}
		}
		return missing;
	}
}
