/**
 * ��������
 * 
 * @author zel
 * 
 */
public class DeadLockDemo extends Thread {
	// ������������Ҫ��������Դ����
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
		if (this.threadNumber % 2 == 0) {// ����������̣߳�����a_obj����������b_obj����
			synchronized (a_obj) {
				System.out
						.println(this.getThreadName() + ",�õ���a_obj,����ȥ��b_obj");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (b_obj) {
					System.out.println(this.getThreadName() + ",�õ���b_obj!");
				}
			}
		} else {
			synchronized (b_obj) {
				System.out
						.println(this.getThreadName() + ",�õ���b_obj,����ȥ��a_obj");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (a_obj) {
					System.out.println(this.getThreadName() + ",�õ���a_obj!");
				}
			}
		}
	}

	public static void main(String[] args) {
		DeadLockDemo t1 = new DeadLockDemo(1, "���_A");
		DeadLockDemo t2 = new DeadLockDemo(2, "���_B");

		t1.start();
		t2.start();

	}
}
