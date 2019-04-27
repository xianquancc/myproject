public class Test04 implements Runnable {
	
	private static int count=0;
	
	
	public void run() {
		while(true) {
			if(Thread.currentThread().getName().startsWith("add")) {
				count++;
			}else {
				count--;
			}
			System.out.println(count);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test04 t = new Test04();
		
		Thread tt = new Thread(t,"add01");
		Thread tt2 = new Thread(t,"add02");
		Thread tt3 = new Thread(t,"mul03");
		Thread tt4 = new Thread(t,"mul04");
//		System.out.print("线程1：");
		tt.start();
		tt2.start();
		tt3.start();
		tt4.start();
		
 
	}
}
 