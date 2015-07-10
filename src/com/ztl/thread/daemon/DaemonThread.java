package com.ztl.thread.daemon;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztl.consumer.Consumer;
import com.ztl.pojos.Basket;
import com.ztl.producer.Producer;

/**
 * 守护线程
 * 
 * @author zel
 * 
 */
public class DaemonThread implements Runnable {
	public static Logger logger = Logger.getLogger(DaemonThread.class);
	private List<ThreadGroup> thread_group_list = null;
	private Basket basket;

	public DaemonThread(Basket baseket) {
		thread_group_list = new ArrayList<ThreadGroup>();
		this.basket = baseket;
	}

	public void addThreadGroup(ThreadGroup threadGroup) {
		this.thread_group_list.add(threadGroup);
	}

	@Override
	public void run() {
		boolean is_active_users_threads = false;
		boolean isRunning=true;
		while (isRunning) {
			is_active_users_threads=false;
			for (ThreadGroup group : thread_group_list) {
				logger.info(group.getName() + ",当前拥有的存活状态,线程数量为   "
						+ group.activeCount() + " 个");
				// logger.info("目前，生产者共生产了" + Producer.producer_all_number +
				// "个");
				// logger.info("目前，消费者共消费了" + Consumer.consumer_all_number +
				// "个");
				logger.info("目前，生产者共生产了" + basket.getPutNumber() + "个");
				logger.info("目前，消费者共消费了" + basket.getTakeNumber() + "个");
				// 剩余多少个很关键，不能出现负数，所以要加销取basket里边的数量为准
				logger.info("目前，剩余了" + basket.getRemainBaoZiNumber() + "个");

				if (group.activeCount() > 0) {
					is_active_users_threads = true;
				}
			}

			if (!is_active_users_threads) {
				logger.info("已没有存活的工作线程,守护线程即将结束,系统亦将结束!");
				isRunning=false;
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
