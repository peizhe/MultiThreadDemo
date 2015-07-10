package com.ztl.controler;

import org.apache.log4j.Logger;

import com.ztl.consumer.Consumer;
import com.ztl.pojos.Basket;
import com.ztl.producer.Producer;
import com.ztl.thread.daemon.DaemonThread;
import com.ztl.thread.daemon.DaemonThread4AddElement;

public class SystemControler {
	public static Logger logger = Logger.getLogger(SystemControler.class);

	public static void main(String[] args) {
		ThreadGroup producer_thread_group = new ThreadGroup("�������߳���");
		ThreadGroup consumer_thread_group = new ThreadGroup("�������߳���");

		Basket basket = new Basket(1);
		// Producer producer_A = new Producer("������A", basket);
		Producer producer_A = new Producer("������A", basket,
				producer_thread_group);
		// Producer producer_B = new Producer("������B", basket,
		// producer_thread_group);
		// Producer producer_C = new Producer("������C", basket);
		// Producer producer_D = new Producer("������D", basket);
		// Producer producer_E = new Producer("������E", basket);
		// Producer producer_F = new Producer("������F", basket);

		Consumer consumerA = new Consumer("������A", basket, consumer_thread_group);
		Consumer consumerB = new Consumer("������B", basket, consumer_thread_group);
		Consumer consumerC = new Consumer("������C", basket, consumer_thread_group);

		producer_A.start();
		// producer_B.start();
		// producer_C.start();
		// producer_D.start();
		// producer_E.start();
		// producer_F.start();

		consumerA.start();
		consumerB.start();
		consumerC.start();

		/**
		 * �����ػ��߳�
		 */
		DaemonThread daemonThread = new DaemonThread(basket);
		daemonThread.addThreadGroup(producer_thread_group);
		daemonThread.addThreadGroup(consumer_thread_group);
		new Thread(daemonThread).start();
		
		/**
		 * �������Ԫ�ص��߳�
		 */
		DaemonThread4AddElement daemonThread4AddElement=new DaemonThread4AddElement(producer_thread_group,basket,consumer_thread_group);
		new Thread(daemonThread4AddElement).start();/**/
		
	}
}
