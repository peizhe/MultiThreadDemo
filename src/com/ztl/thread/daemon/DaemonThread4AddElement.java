package com.ztl.thread.daemon;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztl.consumer.Consumer;
import com.ztl.pojos.Basket;
import com.ztl.producer.Producer;

/**
 * �ػ��߳�,Ϊ����ǿģ�����ʵ�ԣ������Ե���ϵͳ����������߻���������
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
			// ����������,���������İ��ӵ�������<=һ����������ʱ,������������
			if (Producer.producer_all_number < Producer.max_producer_all_number_one_day) {
				producer_new_count++;
				Producer producer_one = new Producer("����������"
						+ producer_new_count, basket,
						this.producer_thread_group);
				producer_one.start();
				logger.info(producer_one.getName() + ",�Ѽ�������!");
				is_add_new_element = true;
			}else {
				logger.info("�����Ѵﵽ�������,���ٲ����µ�������!");
			}

			// ����������,���������а���ʱ,������������0ʱ,��������������
			if (basket.getRemainBaoZiNumber() > 0) {
				consumer_new_count++;
				Consumer consumer_one = new Consumer("����������"
						+ consumer_new_count, basket,
						this.consumer_thread_group);
				consumer_one.start();
				logger.info(consumer_one.getName() + ",�Ѽ�������!");
				is_add_new_element = true;
			}else {
				logger.info("�Ѿ�û�а�����,��������������!");
			}
			if (is_add_new_element) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				logger.info("���������������߻������ߣ����ػ����˳�!");
				isRunning = false;
			}
		}
	}

}
