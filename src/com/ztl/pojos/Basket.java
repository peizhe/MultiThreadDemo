package com.ztl.pojos;

import java.util.LinkedList;
import java.util.List;

import com.ztl.consumer.Consumer;

/**
 * 包子的容器对象，负责包子放入和拿出
 * 
 * @author zel
 * 
 */
public class Basket {
	private int take_number = 0;
	private int put_number = 0;

	public Basket(int index) {
		this.index = index;
		this.baoziList = new LinkedList<BaoZi>();
	}

	private int index;
	private LinkedList<BaoZi> baoziList;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<BaoZi> getBaoziList() {
		return baoziList;
	}

	public void setBaoziList(LinkedList<BaoZi> baoziList) {
		this.baoziList = baoziList;
	}

	public int getRemainBaoZiNumber() {
		synchronized (this) {
			return this.getBaoziList().size();
		}
	}

	public int getTakeNumber() {
		synchronized (this) {
			return this.take_number;
		}
	}
	public int getPutNumber(){
		synchronized (this) {
			return this.put_number;
		}
	}

	public void addOne(BaoZi baozi) {
		synchronized (this) {
			this.baoziList.add(baozi);
			// this.notifyAll();
			put_number++;
		}
	}

	public BaoZi getOne() {
		BaoZi baozi = null;
		synchronized (this) {
			baozi = this.baoziList.pollLast();
			// while (baozi == null) {
			// System.out.println("消费者在等待中!");
			// try {
			// this.wait();
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// baozi = this.baoziList.peekLast();
			// }
			if (baozi != null) {
				this.take_number++;
			}
		}
		return baozi;
	}

}
