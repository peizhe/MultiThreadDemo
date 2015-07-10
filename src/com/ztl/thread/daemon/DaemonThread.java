package com.ztl.thread.daemon;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztl.consumer.Consumer;
import com.ztl.pojos.Basket;
import com.ztl.producer.Producer;

/**
 * �ػ��߳�
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
				logger.info(group.getName() + ",��ǰӵ�еĴ��״̬,�߳�����Ϊ   "
						+ group.activeCount() + " ��");
				// logger.info("Ŀǰ�������߹�������" + Producer.producer_all_number +
				// "��");
				// logger.info("Ŀǰ�������߹�������" + Consumer.consumer_all_number +
				// "��");
				logger.info("Ŀǰ�������߹�������" + basket.getPutNumber() + "��");
				logger.info("Ŀǰ�������߹�������" + basket.getTakeNumber() + "��");
				// ʣ����ٸ��ܹؼ������ܳ��ָ���������Ҫ����ȡbasket��ߵ�����Ϊ׼
				logger.info("Ŀǰ��ʣ����" + basket.getRemainBaoZiNumber() + "��");

				if (group.activeCount() > 0) {
					is_active_users_threads = true;
				}
			}

			if (!is_active_users_threads) {
				logger.info("��û�д��Ĺ����߳�,�ػ��̼߳�������,ϵͳ�ཫ����!");
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
