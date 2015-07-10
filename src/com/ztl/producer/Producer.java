package com.ztl.producer;

import org.apache.log4j.Logger;

import com.ztl.pojos.BaoZi;
import com.ztl.pojos.Basket;

/**
 * 生产者线程类
 * 
 * @author zel
 * 
 */
public class Producer extends Thread {
	public static Logger logger = Logger.getLogger(Producer.class);

	private static Object synObj = new Object();
	public static int max_producer_number = 5;
	public static int producer_all_number=0;
	private int count = 0;
	public static int max_producer_all_number_one_day=35;
    
	public Producer(String producer_name, Basket basket) {
		this.producer_name = producer_name;
		this.basket = basket;
	}

	private ThreadGroup threadGroup;

	public Producer(String producer_name, Basket basket, ThreadGroup threadGroup) {
		super(threadGroup,producer_name);
		this.producer_name = producer_name;
		this.basket = basket;
		this.threadGroup = threadGroup;
	}

	// 生产者的名称
	private String producer_name;

	public String getProducer_name() {
		return producer_name;
	}

	public void setProducer_name(String producerName) {
		producer_name = producerName;
	}

	// 生产者包的包子的序号,要求唯一来标识一个包子
	public static int product_seq = 1;
	private Basket basket;

	public int getProduct_seq() {
		return product_seq;
	}

	public void setProduct_seq(int productSeq) {
		product_seq = productSeq;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	@Override
	public void run() {
		while (true) {
			BaoZi baozi = this.produce();
			if(baozi==null){
				break;
			}
			try {
				this.count++;
				if (this.count == max_producer_number) {
					logger.info(producer_name + ",生产达到最大值"
							+ this.max_producer_number + "个馒头，不再生产了!");
					break;
				}
				// 每生产一个包子，休息3秒
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private BaoZi produce() {
		BaoZi baozi = null;
		synchronized (synObj) {
			if(this.producer_all_number==max_producer_all_number_one_day){
				logger.info(producer_name + ",一天的生产已经完成,线程将退出!");
				return null;
			}
			baozi = new BaoZi(producer_name + "_" + product_seq);
			logger.info(this.getProducer_name() + ",生产了" + baozi.getIndex());
			this.basket.addOne(baozi);
			product_seq++;
			producer_all_number++;
		}
		return baozi;
	}
}
