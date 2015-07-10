/**
 * 死锁举例
 * 
 * @author zel
 * 
 */
public class DeadLockDemo extends Thread {
	// 该两个对象是要竞争的资源对象
	public static Object a_obj = new Object();
	public static Object b_obj = new Object();
	
	private int threadNumber;
	private String threadName;

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public int getThreadNumber() {
		return threadNumber;
	}

	public void setThreadNumber(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	public DeadLockDemo(int threadNumber, String threadName) {
		this.threadNumber = threadNumber;
		this.threadName = threadName;
	}

	@Override
	public void run() {
		if (this.threadNumber % 2 == 0) {// 这样的序号线程，先拿a_obj的锁，再拿b_obj的锁
			synchronized (a_obj) {
				System.out
						.println(this.getThreadName() + ",拿到了a_obj,即将去拿b_obj");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (b_obj) {
					System.out.println(this.getThreadName() + ",拿到了b_obj!");
				}
			}
		} else {
			synchronized (b_obj) {
				System.out
						.println(this.getThreadName() + ",拿到了b_obj,即将去拿a_obj");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (a_obj) {
					System.out.println(this.getThreadName() + ",拿到了a_obj!");
				}
			}
		}
	}

	public static void main(String[] args) {
		DeadLockDemo t1 = new DeadLockDemo(1, "大哥_A");
		DeadLockDemo t2 = new DeadLockDemo(2, "大哥_B");

		t1.start();
		t2.start();

	}
}
