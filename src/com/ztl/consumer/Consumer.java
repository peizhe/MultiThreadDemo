package com.ztl.consumer;

import org.apache.log4j.Logger;

import com.ztl.pojos.BaoZi;
import com.ztl.pojos.Basket;
import com.ztl.producer.Producer;

/**
 * �������߳���
 * 
 * @author zel
 * 
 */
public class Consumer extends Thread {
	public static Logger logger = Logger.getLogger(Consumer.class);

	public static Class<Object> my_class = Object.class;
	public static int max_consumer_number = 3;
	private int count = 0;
	// �������Ŀ������ȴ��Ĵ�����һ��������ֵ�����������˳�
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
					logger.info(this.getConsumer_name() + ",�����ȴ������Ѵﵽ"
							+ max_consumer_number + "�Σ���������������!");
					break;
				}
				this.count++;
				if (this.count == max_consumer_number) {
					logger.info(consumer_name + ",���Ѵﵽ���ֵ"
							+ this.max_consumer_number + "����ͷ���Ա��ˣ����ٳ���!");
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
			// ����ʧ��һ�������ܣ�������������Ŀ��Ķ��ԺͿ��������׳̶�
			while (baozi == null) {
				try {
					if (sleep_time_continue_count >= max_sleep_time_continue) {
						return null;
					}
					sleep_time_continue_count++;
					logger.info(this.getConsumer_name()
							+ ",û���õ����ӣ����ȴ�һ��,��ǰ�����ȴ�����Ϊ"
							+ this.sleep_time_continue_count);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				baozi = this.basket.getOne();
			}
			logger.info(this.consumer_name + ",�Ե���һ�����ӣ����Ϊ"
					+ baozi.getIndex());
			this.sleep_time_continue_count = 0;
			consumer_all_number++;
		}
		return baozi;
	}

}
