import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFunner {
	public ThreadFunner() {
		// blocking queues
		// Java Collections Framework provides ArrayBlockingQueue, LinkedBlockingQueue, and PriorityBlockingQueue for supporting blocking queues.

		blockingQueueTest();

		// semaphores
		// Semaphores can be used to restrict the number of threads that access a shared resource.
		semaphoreTest();

		// resource-ordering
		/* 
		 * Recall the dining philosophers problem: https://en.wikipedia.org/wiki/Dining_philosophers_problem
		 * Deadlock involves a cycle of locks held and locks awaited.
		 * To make such cycles impossible, we can strictly order resources and make sure that we obtain them in a given order.
		 * For example, number the chopsticks. 
		 * Then, have each philosopher first pick up the closest chopstick with the minimum number (or await it).
		 * Finally, have each philosopher pick up the closest other chopstick (or await it).
		 * No more starving philosophers!
		 */

		// thread lifecycle
		// See figure 30.35 (10th edition) or 32.35 (11th edition).
		/*
		 * The five thread states: New, Ready, Running, Blocked, or Finished
		 * New - after construction, before start; start() -> Ready state
		 * Ready - ready to run, but not running yet; run() -> Running state
		 * Running - being executed by the CPU
		 *   - run() completed -> Finished state
		 *   - yield() or CPU scheduler decides time out -> Ready state
		 *   - join() -> Blocked state waiting for target thread to finish
		 *   - sleep() -> Blocked state waiting for sleep timeout to finish
		 *   - wait() -> Blocked state waiting to be signaled (i.e. notified)
		 * Blocked - waiting condition met -> Ready state
		 * isAlive() - true iff in Ready, Running, or Blocked states
		 * interrupt()
		 *   - Ready/Running state -> interrupted flag is set (isInterrupted() returns true)
		 *   - Blocked state -> Ready state and InterruptedException is thrown
		 */

		// synchronized collections
		// Java Collections Framework provides synchronized collections for lists, sets, and maps.
		
		/* Important author note:
		 * The methods in java.util.Vector, java.util.Stack, and java.util.Hashtable are already synchronized.
		 * These are old classes introduced in JDK 1.0. Starting with JDK 1.5, you should use 
		 * java.util.ArrayList to replace Vector, java.util.LinkedList to replace Stack, and 
		 * java.util.Map to replace Hashtable. If synchronization is needed, use a synchronization wrapper.
		 */
		synchListTest(); // We'll look at sets and maps more in Chapter 21.

		// fork/join parallelism
		// The Fork/Join Framework is used for parallel programming in Java.
		forkJoinTest();
	}

	boolean doneAdding = false;

	private void blockingQueueTest() {
		// create a new buffer of fixed size 3
		ArrayBlockingQueue<Integer> sleepBuffer = new ArrayBlockingQueue<>(3);
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(() -> {
			doneAdding = false;
			for (int i = 0 ; i < 15; i++) // put 15 random sleep times < 1 sec into queue
				try {
					int ms = (int) (1000 * Math.random());
					System.out.printf("Thread added sleep time %d to queue.\n", ms);
					sleepBuffer.put(ms); // Note: put not add
				} catch (Exception e) {
					e.printStackTrace();
				}
			doneAdding = true;
			System.out.println("Thread is done adding all sleep times to queue.");
		});
		for (int i = 0; i < 5; i++) { // set 5 threads to task of sleeping those times
			final int iFinal = i;
			executor.execute(() -> {
				while (!(sleepBuffer.isEmpty() && doneAdding)) {
					int ms;
					try {
						ms = sleepBuffer.take(); // Note: take not remove
						System.out.printf("Thread %d sleeping %d ms...\n", iFinal, ms);
						Thread.sleep(ms);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				System.out.printf("Thread %d observes empty queue and terminates.\n", iFinal);
			});
		}
		executor.shutdown();
		try {
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("blockingQueueTest complete.\n");
	}

	private void semaphoreTest() {
		Semaphore threeAtOnce = new Semaphore(3, true); // three at once with fairness set to true *
		AtomicInteger threadsWithSemaphore = new AtomicInteger();
		for (int i = 0; i < 10; i++) {
			final int threadNum = i;
			new Thread(() -> {
				try {
					threeAtOnce.acquire();
					System.out.printf("Thread %d acquires semaphore for total of %d threads ...\n", threadNum, threadsWithSemaphore.incrementAndGet());
					Thread.sleep(100);
					System.out.printf("Thread %d releases semaphore for total of %d threads ...\n", threadNum, threadsWithSemaphore.decrementAndGet());
					// Note that the print order of the statements isn't guaranteed to be consistent.
					// * We would need to fairly queue print requests, but we can see that the OS doesn't allow true fairness
					threeAtOnce.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
		while (threadsWithSemaphore.get() > 0)
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("semaphoreTest complete.\n");
	}

	private void synchListTest() {
		// Comment out one or the other of the following lines to observe the behavior with or without a synchronized collection.
		List<Integer> intList = Collections.synchronizedList(new ArrayList<Integer>());
//		List<Integer> intList = new ArrayList<Integer>();

		// 10 threads concurrently add and remove integers 0-9 in random orders, which should result in an empty list.
		for (int i = 0; i < 10; i++) 
			new Thread(() -> {
				ArrayList<Integer> values = new ArrayList<Integer>();
				for (int j = 0; j < 10; j++)
					values.add(j);
				Collections.shuffle(values);
				for (Integer value : values)
					intList.add(value);
				Collections.shuffle(values);
				for (Integer value : values)
					intList.remove(value);
			}).start();
	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Should be empty list: " +  intList);
		System.out.println("synchListTest complete.\n");
	}

	static final int SIZE = 100000000;

	private void forkJoinTest() {
		// Parallel maximum of an unsorted array
		
		// Create data
		double[] data = new double[SIZE];
		for (int i = 0; i < SIZE; i++)
			data[i] = Math.random();
		
		// Sequential maximum
		long startTime = System.currentTimeMillis();
		double max = Double.MIN_VALUE;
		for (int i = 0; i < SIZE; i++)
			if (data[i] > max)
				max = data[i];
		long duration = System.currentTimeMillis() - startTime;
		System.out.println("Maximum " + max + " after " + duration + " ms with a single processor");
		
		// Parallel maximum
		startTime = System.currentTimeMillis();
		RecursiveTask<Double> maxTask = new MaxTask(0, SIZE, data);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(maxTask);
		max = maxTask.join();
		duration = System.currentTimeMillis() - startTime;
		System.out.println("Maximum " + maxTask.getRawResult() + " after " + duration + " ms with " + Runtime.getRuntime().availableProcessors() + " processors");		
		// Note that the fork/join overhead is very significant for such a simple iterative task.
		
		System.out.println("forkJoinTest complete.\n");
	}

	@SuppressWarnings("serial")
	static class MaxTask extends RecursiveTask<Double> {
		final int SPLIT_THRESHOLD = SIZE / Runtime.getRuntime().availableProcessors();
		int index1, index2;
		double[] data;

		public MaxTask(int index1, int index2, double[] data) {
			super();
			this.index1 = index1;
			this.index2 = index2;
			this.data = data;
		}

		@Override
		protected Double compute() {
			if (index2 - index1 < SPLIT_THRESHOLD) {
				// Sequential computation for smaller blocks
				double max = Double.MIN_VALUE;
				for (int i = index1; i < index2; i++)
					if (data[i] > max)
						max = data[i];
				return max;
			}
			else {
				int indexMid = (index1 + index2) / 2;
				MaxTask firstHalf = new MaxTask(index1, indexMid, data);
				MaxTask secondHalf = new MaxTask(indexMid, index2, data);
				invokeAll(firstHalf, secondHalf);
				double max1 = firstHalf.join();
				double max2 = secondHalf.join();
				return max1 > max2 ? max1 : max2;
			}
		}
	}
	
	public static void main(String[] args) {
		new ThreadFunner();
	}

}
