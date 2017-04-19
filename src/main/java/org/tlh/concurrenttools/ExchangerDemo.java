package org.tlh.concurrenttools;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于多个线程到达等待点之后进行数据的交换
 * @author hp
 *
 */
public class ExchangerDemo {

	public static void main(String[] args) {
		Exchanger<String> exchanger=new Exchanger<String>();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(new Runnable() {
			
			public void run() {
				String a=Thread.currentThread().getName()+"中的数据";
				try {
					//让当前线程处于等待状态，等待其他线程到达交换点，并返回交换的数据
					String exchange = exchanger.exchange(a);//exchanger为隐式的final
					System.out.println(Thread.currentThread().getName()+"中得到的数据为："+exchange);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				String b=Thread.currentThread().getName()+"中的数据";
				try {
					String exchange = exchanger.exchange(b);
					System.out.println(Thread.currentThread().getName()+"中得到的数据为："+exchange);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		executorService.shutdown();
	}
	
}
