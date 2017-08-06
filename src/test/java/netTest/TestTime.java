package netTest;

import java.util.concurrent.TimeUnit;

import SWT.work.MyData;

public class TestTime {

	public static void main(String[] args) throws InterruptedException {

		for(int i = 0;i<60;i++){
			System.err.println(System.currentTimeMillis()%1000000);
					TestEvent.data.add(new MyData(1,2,3));
		TestEvent.data.play();
			TimeUnit.SECONDS.sleep(5);
		}

	}

}
