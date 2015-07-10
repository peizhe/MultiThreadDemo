package com.ztl.producer;

import org.apache.log4j.Logger;

import com.ztl.pojos.BaoZi;
import com.ztl.pojos.Basket;

/**
 * �������߳���
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

	// �����ߵ�����
	private String producer_name;

	public String getProducer_name() {
		return producer_name;
	}

	public void setProducer_name(String producerName) {
		producer_name = producerName;
	}

	// �����߰��İ��ӵ����,Ҫ��Ψһ����ʶһ������
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
					logger.info(producer_name + ",�����ﵽ���ֵ"
							+ this.max_producer_number + "����ͷ������������!");
					break;
				}
				// ÿ����һ�����ӣ���Ϣ3��
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
				logger.info(producer_name + ",һ��������Ѿ����,�߳̽��˳�!");
				return null;
			}
			baozi = new BaoZi(producer_name + "_" + product_seq);
			logger.info(this.getProducer_name() + ",������" + baozi.getIndex());
			this.basket.addOne(baozi);
			product_seq++;
			producer_all_number++;
		}
		return baozi;
	}
}
