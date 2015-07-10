package com.ztl.thread.daemon;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztl.consumer.Consumer;
import com.ztl.pojos.Basket;
import com.ztl.producer.Producer;

/**
 * 守护线程,为了增强模拟的真实性，周期性的向系统中添加生产者或者消费者
 * 
 * @author zel
 * 
 */
public class DaemonThread4AddElement implements Runnable {
	public static Logger logger = Logger
			.getLogger(DaemonThread4AddElement.class);
	private ThreadGroup producer_thread_group;
	private ThreadGroup consumer_thread_group;
	private Basket basket;

	// private ThreadGroup consumer_thread_group;
	public DaemonThread4AddElement(ThreadGroup producer_thread_group,
			Basket basket, ThreadGroup consumer_thread_group) {
		this.producer_thread_group = producer_thread_group;
		this.basket = basket;
		this.consumer_thread_group = consumer_thread_group;
	}

	@Override
	public void run() {
		int producer_new_count = 0;
		int consumer_new_count = 0;
		boolean is_add_new_element = false;
		boolean isRunning = true;
		while (isRunning) {
			is_add_new_element = false;
			// 新增生产者,当已生产的包子的总数量<=一天的最大数量时,则新增生产者
			if (Producer.producer_all_number < Producer.max_producer_all_number_one_day) {
				producer_new_count++;
				Producer producer_one = new Producer("新增生产者"
						+ producer_new_count, basket,
						this.producer_thread_group);
				producer_one.start();
				logger.info(producer_one.getName() + ",已加入生产!");
				is_add_new_element = true;
			}else {
				logger.info("生产已达到最大数量,不再产生新的生产者!");
			}

			// 新增消费者,当篮子中有包子时,即数量不等于0时,即可新增消费者
			if (basket.getRemainBaoZiNumber() > 0) {
				consumer_new_count++;
				Consumer consumer_one = new Consumer("新增消费者"
						+ consumer_new_count, basket,
						this.consumer_thread_group);
				consumer_one.start();
				logger.info(consumer_one.getName() + ",已加入消费!");
				is_add_new_element = true;
			}else {
				logger.info("已经没有包子了,不再新增消费者!");
			}
			if (is_add_new_element) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				logger.info("不会再新增生产者或消费者，该守护线退出!");
				isRunning = false;
			}
		}
	}

}
