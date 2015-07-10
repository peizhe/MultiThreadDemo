public class ThreadTest {
	/**
	 * 一般对象锁
	 */
	public static void normalSyn() {
		// 一般对象销
		Object obj = new Object();
		synchronized (obj) {
			System.out.println("obj is syn!");
		}
	}

	/**
	 * 非静态方法锁
	 */
	public synchronized void methodSyn() {
		System.out.println("method syn");
	}

	/**
	 * 静态方法锁,相当于直接锁住ThreadTest.class,即当前类的字节码文件
	 */
	public synchronized static void staticMethodSyn() {
		System.out.println("static method syn");
	}

	static class ClassByteSynThread extends Thread {

		@Override
		public void run() {
			synchronized (ThreadTest.class) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("class byte syn");
			}
		}

	}

	public static void main(String[] args) throws Exception {
		Thread tt = new ClassByteSynThread();
		tt.start();

		Thread.sleep(2000);
		for (int i = 0; i < 5; i++) {
			synchronized (ThreadTest.class) {
				new ThreadTest();
				System.out.println("new ThreadTest is successfuly");
			}
		}

	}
}
