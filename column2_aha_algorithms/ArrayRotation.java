package com.pp.column2;
/*
 * Rotate a one-dimensional vector of n elements left by i positions.
 * For instance, with n = 8 and i = 3, the vector abcdefgh is rotated to defghabc. 
 * Simple code uses an n element intermediate vector to do the jobs in n steps.
 * Can you rotate the vector in time proportional to n using O(1) space?
 */
public class ArrayRotation {
    private static void reverse(int[] bits, int startIdx, int endIdx) {
    	while(startIdx < endIdx) {
    		bits[startIdx] ^= bits[endIdx];
    		bits[endIdx] ^= bits[startIdx];
    		bits[startIdx] ^= bits[endIdx];
    		startIdx++;
    		endIdx--;
    	}
    }
    //approach 1, O(n) time, O(1) space 
    public static void leftRotateByMUsingReverse(int[] arr, int m) {
    	m = m % arr.length;
    	if(m != 0) {
        	reverse(arr, 0, m - 1);
        	reverse(arr, m, arr.length - 1);
        	reverse(arr, 0, arr.length - 1);    		
    	}
    }
    
    //approach 2, O(n) time, O(1) space 
	public static void leftRotateByM(int[] arr, int m) {
		m = m % arr.length;
		if(m != 0) {
			int count = 0;
			int startIdx = 0;   				
			while(count < arr.length) {
				int currIdx = startIdx;
				int nextIdx = (currIdx + m) % arr.length;
				int temp = arr[startIdx];
				while(nextIdx != startIdx) {
					arr[currIdx] = arr[nextIdx];
					currIdx = nextIdx;
					nextIdx = (nextIdx + m) % arr.length;
					count++;
				}
				arr[currIdx] = temp;
				startIdx++;
				count++;
			}
		}
	}	
}
