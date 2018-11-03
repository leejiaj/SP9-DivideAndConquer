/** Sample starter code for SP9.
 *  @author
 */

package lxj171130;
import java.util.Random;

public class SP9 {
    public static Random random = new Random();
    public static int numTrials = 100;
    public static void main(String[] args) {
	int n = 10;  int choice = 1 + random.nextInt(4);
	if(args.length > 0) { n = Integer.parseInt(args[0]); }
	if(args.length > 1) { choice = Integer.parseInt(args[1]); }
        int[] arr = new int[n];
        for(int i=0; i<n; i++) {
	    arr[i] = i;
	}
	Timer timer = new Timer();
	switch(choice) {
	case 1: // Insertion sort
	    Shuffle.shuffle(arr);
	    numTrials = 1;
	    insertionSort(arr);
	    break;
	case 2: // Merge sort (take 1)
	    for(int i=0; i<numTrials; i++) {
		Shuffle.shuffle(arr);
		mergeSort1(arr);
	    }
	    break;
	case 3: // Merge sort (take 2)
	    for(int i=0; i<numTrials; i++) {
		Shuffle.shuffle(arr);
		mergeSort2(arr);
	    }
	    break;
	case 4: // Merge sort (take 3)
	    for(int i=0; i<numTrials; i++) {
		Shuffle.shuffle(arr);
		mergeSort3(arr);
	    }
	    break;
	}
	timer.end();
	timer.scale(numTrials);

	System.out.println("Choice: " + choice + "\n" + timer);
    for(int i=0; i<=arr.length-1; i++) {
        System.out.print(arr[i] + " ");
    }
    System.out.println();
    }

    public static void insertionSort(int[] arr) {
    	insertionSort(arr, 0, arr.length-1);
    }

    private static void insertionSort(int[] arr, int p, int r) {
        for (int i=p+1;i<=r;i++) {
            int tmp = arr[i];
            int j = i-1;
            while (j>=p && tmp<arr[j]) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = tmp;
        }
	}

	public static void mergeSort1(int[] arr) {
	    mergeSort1(arr, 0, arr.length-1);
    }
	
	public static void mergeSort1(int[] arr, int p, int r) {
	    if (p<r) {
	        int q = (p+r)/2;
	        mergeSort1(arr, p, q);
	        mergeSort1(arr, q+1, r);
	        merge1(arr, p, q, r);
	    }
	}

	public static void merge1(int[] arr, int p, int q, int r) {
	    int[] L = new int[q-p+1];
	    int[] R = new int[r-q];
	    System.arraycopy(arr, p, L, 0, q-p+1);
	    System.arraycopy(arr, q+1, R, 0, r-q);
	    int i = 0;
	    int j = 0;
	    for (int k=p; k<=r; k++) {
	        if (j>=R.length || (i<L.length && L[i]<=R[j])) {
	            arr[k] = L[i++];
	        } else {
	            arr[k] = R[j++];
	        }
	    }
	}
    
    public static void mergeSort2(int[] arr) {
    	int[] arrB = new int[arr.length];
    	mergeSort2(arr, arrB, 0, arr.length);
    }
    
    private static void mergeSort2(int[] arr, int[] arrB, int left, int n) {
		if(n < 4) {
			insertionSort(arr, left, left+n-1);
		}
		else {
			int Ln = n / 2;
			mergeSort2(arr, arrB, left, Ln);
			mergeSort2(arr, arrB, left+Ln, n-Ln);
			merge2(arr, arrB, left, left+Ln-1, left+n-1);
		}
	}

	private static void merge2(int[] arr, int[] arrB, int p, int q, int r) {
		// Merge arr[p .. q] and arr[q + 1 ... r] into arr[p .. r], in sorted order
		// Use arrB for temporary storage
		// Pre: arr[p ... q], and arr[q + 1 ... r] are in sorted order
		System.arraycopy(arr, p, arrB, p, r-p+1);
		int i = p;
		int j = q + 1;
		for(int k = p; k<=r; k++) {
			if(j > r || (i <= q && arrB[i] <= arrB[j])) {
				arr[k] = arrB[i++];
			}
			else {
				arr[k] = arrB[j++];
			}
		}
	}

	public static void mergeSort3(int[] arr) {
		int[] arrB = new int[arr.length];
		System.arraycopy(arr, 0, arrB, 0, arr.length);
		mergeSort3(arr, arrB, 0, arr.length);
    }


    private static void mergeSort3(int[] arr, int[] arrB, int left, int n) {
		//Sort arr[left..left+n-1] or arrB[left .. left+n-1]
		// into arr[left .. left+n-1]
		//Pre: arr[left .. left+n-1], arrB[left .. left+n-1]
		// have the same elements
		if(n < 4) {
			insertionSort(arr, left, left+n-1);
		}
		else {
			int Ln = n / 2;
			// sort into arrB and merge into arr
			mergeSort3(arrB, arr, left, Ln);
			mergeSort3(arrB, arr, left+Ln, n-Ln);
			merge3(arr, arrB, left, left+Ln-1, left+n-1);
		}
	}


    private static void merge3(int[] arr, int[] arrB, int p, int q, int r) {
		//Merge arrB[p .. q] and arrB[q + 1 .. r] into arr[p .. r], in sorted order
		//Pre: arrB[p .. q], and arrB[q + 1 .. r] are in sorted order
		int i = p, j = q + 1, k = p;
		while(i <= q && j <= r) {
			if(arrB[ i ] <= arrB[ j ]) {
				arr[k++] = arrB[i++];
			}
			else {
				arr[k++] = arrB[j++];
			}
		}
		while(i <= q) {
			arr[k++] = arrB[i++];
		}
		while(j <= r) {
			arr[k++] = arrB[j++];
		}
	}


/** Timer class for roughly calculating running time of programs
     *  @author rbk
     *  Usage:  Timer timer = new Timer();
     *          timer.start();
     *          timer.end();
     *          System.out.println(timer);  // output statistics
     */

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() { if(!ready) { end(); }  return elapsedTime; }

        public long memory()   { if(!ready) { end(); }  return memUsed; }

	public void scale(int num) { elapsedTime /= num; }
	
        public String toString() {
            if(!ready) { end(); }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }
    }
    
    /** @author rbk : based on algorithm described in a book
     */


    /* Shuffle the elements of an array arr[from..to] randomly */
    public static class Shuffle {
	
	public static void shuffle(int[] arr) {
	    shuffle(arr, 0, arr.length-1);
	}

	public static<T> void shuffle(T[] arr) {
	    shuffle(arr, 0, arr.length-1);
	}

	public static void shuffle(int[] arr, int from, int to) {
	    int n = to - from  + 1;
	    for(int i=1; i<n; i++) {
		int j = random.nextInt(i);
		swap(arr, i+from, j+from);
	    }
	}

	public static<T> void shuffle(T[] arr, int from, int to) {
	    int n = to - from  + 1;
	    Random random = new Random();
	    for(int i=1; i<n; i++) {
		int j = random.nextInt(i);
		swap(arr, i+from, j+from);
	    }
	}

	static void swap(int[] arr, int x, int y) {
	    int tmp = arr[x];
	    arr[x] = arr[y];
	    arr[y] = tmp;
	}
	
	static<T> void swap(T[] arr, int x, int y) {
	    T tmp = arr[x];
	    arr[x] = arr[y];
	    arr[y] = tmp;
	}

	public static<T> void printArray(T[] arr, String message) {
	    printArray(arr, 0, arr.length-1, message);
	}

	public static<T> void printArray(T[] arr, int from, int to, String message) {
	    System.out.print(message);
	    for(int i=from; i<=to; i++) {
		System.out.print(" " + arr[i]);
	    }
	    System.out.println();
	}
    }
}

