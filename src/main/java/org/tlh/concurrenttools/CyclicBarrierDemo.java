package org.tlh.concurrenttools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier:�����̵߳ȴ����γ�һ���ȴ��㣬�������̶߳�����õȴ����ʱ���������ִ��
 * 1.�����CountDownLatch��ӿ������ø��� <br>
 * 2.������ȴ���֮����Դ����¼�
 * 
 * @author hp
 *
 */
public class CyclicBarrierDemo {

	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(3,new Runnable() {
			
			public void run() {
				System.out.println("���е�����ִ�����");
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
				cyclicBarrier.await();//���̴߳��ڵȴ�״̬��������ͷ���Դ,LockSupport
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

	}

}
