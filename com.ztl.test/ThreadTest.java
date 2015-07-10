public class ThreadTest {
	/**
	 * һ�������
	 */
	public static void normalSyn() {
		// һ�������
		Object obj = new Object();
		synchronized (obj) {
			System.out.println("obj is syn!");
		}
	}

	/**
	 * �Ǿ�̬������
	 */
	public synchronized void methodSyn() {
		System.out.println("method syn");
	}

	/**
	 * ��̬������,�൱��ֱ����סThreadTest.class,����ǰ����ֽ����ļ�
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
