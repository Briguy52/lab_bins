import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

// small change

/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins {
	public static final String DATA_FILE = "example.txt";

	/**
	 * Reads list of integer data from the given input.
	 *
	 * @param input tied to an input source that contains space separated numbers
	 * @return list of the numbers in the order they were read
	 */
	public List<Integer> readData (Scanner input) {
		List<Integer> results = new ArrayList<Integer>();
		while (input.hasNext()) {
			results.add(input.nextInt());
		}
		return results;
	}
	
	// Add to collection of disks
	public PriorityQueue<Disk> addDisk(List<Integer> myData) {
		PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
		pq.add(new Disk(0));

		int diskId = 1;
		for (Integer size : myData) {
			Disk d = pq.peek();
			if (d.freeSpace() >= size) {
				pq.poll();
				d.add(size);
				pq.add(d);
			} else {
				Disk d2 = new Disk(diskId);
				diskId++;
				d2.add(size);
				pq.add(d2);
			}
		}
		return pq; 
	}
	
	// Returns total data size of files
	public int getTotalData(List<Integer> dataIn) {
		int total = 0;
		for (Integer elem: dataIn) {
			total += elem; 
		}
		return total; 
	}
	
	// Print statistics
	public void printStats(PriorityQueue<Disk> myPQ) {
		System.out.println();
		System.out.println("worst-fit method");
		System.out.println("number of pq used: " + myPQ.size());
		while (!myPQ.isEmpty()) {
			System.out.println(myPQ.poll());
		}
		System.out.println();
	}

	/**
	 * The main program.
	 */
	public static void main (String args[]) {
		Bins b = new Bins();
		Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
		List<Integer> data = b.readData(input);
		System.out.println("total size = " + b.getTotalData(data) / 1000000.0 + "GB\n");
		// In-order
		b.printStats(b.addDisk(data)); 
		// Decreasing order
		Collections.sort(data, Collections.reverseOrder());
		b.printStats(b.addDisk(data)); 
	}
}
