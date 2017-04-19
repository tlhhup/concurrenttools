package org.tlh.concurrenttools;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ���ڶ���̵߳���ȴ���֮��������ݵĽ���
 * @author hp
 *
 */
public class ExchangerDemo {

	public static void main(String[] args) {
		Exchanger<String> exchanger=new Exchanger<String>();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(new Runnable() {
			
			public void run() {
				String a=Thread.currentThread().getName()+"�е�����";
				try {
					//�õ�ǰ�̴߳��ڵȴ�״̬���ȴ������̵߳��ｻ���㣬�����ؽ���������
					String exchange = exchanger.exchange(a);//exchangerΪ��ʽ��final
					System.out.println(Thread.currentThread().getName()+"�еõ�������Ϊ��"+exchange);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				String b=Thread.currentThread().getName()+"�е�����";
				try {
					String exchange = exchanger.exchange(b);
					System.out.println(Thread.currentThread().getName()+"�еõ�������Ϊ��"+exchange);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		executorService.shutdown();
	}
	
}
