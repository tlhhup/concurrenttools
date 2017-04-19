package org.tlh.concurrenttools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier:用于线程等待，形成一个等待点，当所有线程都到达该等待点的时候继续往下执行
 * 1.相对与CountDownLatch添加可以重置复用 <br>
 * 2.当到达等待点之后可以触发事件
 * 
 * @author hp
 *
 */
public class CyclicBarrierDemo {

	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(3,new Runnable() {
			
			public void run() {
				System.out.println("所有的任务执行完毕");
			}
		});
		for (int i = 0; i < 3; i++) {
			new CyclicBarrierThread(cyclicBarrier).start();
		}
	}

	private static class CyclicBarrierThread extends Thread {

		CyclicBarrier cyclicBarrier;

		public CyclicBarrierThread(CyclicBarrier cyclicBarrier) {
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
			try {
				cyclicBarrier.await();//将线程处于等待状态，相对于释放资源,LockSupport
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

	}

}
