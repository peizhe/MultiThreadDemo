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
		ThreadGroup producer_thread_group = new ThreadGroup("生产者线程组");
		ThreadGroup consumer_thread_group = new ThreadGroup("消费者线程组");

		Basket basket = new Basket(1);
		// Producer producer_A = new Producer("生产者A", basket);
		Producer producer_A = new Producer("生产者A", basket,
				producer_thread_group);
		// Producer producer_B = new Producer("生产者B", basket,
		// producer_thread_group);
		// Producer producer_C = new Producer("生产者C", basket);
		// Producer producer_D = new Producer("生产者D", basket);
		// Producer producer_E = new Producer("生产者E", basket);
		// Producer producer_F = new Producer("生产者F", basket);

		Consumer consumerA = new Consumer("消费者A", basket, consumer_thread_group);
		Consumer consumerB = new Consumer("消费者B", basket, consumer_thread_group);
		Consumer consumerC = new Consumer("消费者C", basket, consumer_thread_group);

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
		 * 启动守护线程
		 */
		DaemonThread daemonThread = new DaemonThread(basket);
		daemonThread.addThreadGroup(producer_thread_group);
		daemonThread.addThreadGroup(consumer_thread_group);
		new Thread(daemonThread).start();
		
		/**
		 * 启动添加元素的线程
		 */
		DaemonThread4AddElement daemonThread4AddElement=new DaemonThread4AddElement(producer_thread_group,basket,consumer_thread_group);
		new Thread(daemonThread4AddElement).start();/**/
		
	}
}
