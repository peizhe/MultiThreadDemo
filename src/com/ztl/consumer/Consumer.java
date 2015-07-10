package com.ztl.consumer;

import org.apache.log4j.Logger;

import com.ztl.pojos.BaoZi;
import com.ztl.pojos.Basket;
import com.ztl.producer.Producer;

/**
 * 消费者线程类
 * 
 * @author zel
 * 
 */
public class Consumer extends Thread {
	public static Logger logger = Logger.getLogger(Consumer.class);

	public static Class<Object> my_class = Object.class;
	public static int max_consumer_number = 3;
	private int count = 0;
	// 设置最大的可连续等待的次数，一旦超过该值，则消费者退出
	private static int max_sleep_time_continue = 3;
//	private static int max_consumer_all_number_one_day=Producer.max_producer_all_number_one_day;
	
	public static int consumer_all_number=0;
	private int sleep_time_continue_count = 0;

	public Consumer(String consumer_name, Basket basket) {
		this.consumer_name = consumer_name;
		this.basket = basket;
	}
	private ThreadGroup threadGroup;
	public Consumer(String consumer_name, Basket basket,ThreadGroup threadGroup) {
		super(threadGroup,consumer_name);
		this.consumer_name = consumer_name;
		this.basket = basket;
		this.threadGroup=threadGroup;
	}

	@Override
	public void run() {
		while (true) {
			BaoZi baozi = this.getOne();
			try {
				if (baozi == null) {
					logger.info(this.getConsumer_name() + ",连续等待次数已达到"
							+ max_consumer_number + "次，消费者生气而走!");
					break;
				}
				this.count++;
				if (this.count == max_consumer_number) {
					logger.info(consumer_name + ",消费达到最大值"
							+ this.max_consumer_number + "个馒头，吃饱了，不再吃了!");
					break;
				}
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String consumer_name;
	private Basket basket;

	public String getConsumer_name() {
		return consumer_name;
	}

	public void setConsumer_name(String consumerName) {
		consumer_name = consumerName;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public BaoZi getOne() {
		BaoZi baozi = null;
		// synchronized (Consumer.class) {
		synchronized (my_class) {
			baozi = this.basket.getOne();
			// 会损失掉一部分性能，但会提升程序的可阅读性和开发的难易程度
			while (baozi == null) {
				try {
					if (sleep_time_continue_count >= max_sleep_time_continue) {
						return null;
					}
					sleep_time_continue_count++;
					logger.info(this.getConsumer_name()
							+ ",没有拿到包子，将等待一次,当前连续等待计数为"
							+ this.sleep_time_continue_count);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				baozi = this.basket.getOne();
			}
			logger.info(this.consumer_name + ",吃掉了一个包子，编号为"
					+ baozi.getIndex());
			this.sleep_time_continue_count = 0;
			consumer_all_number++;
		}
		return baozi;
	}

}
