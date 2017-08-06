package netTest;

import SWT.work.Event;
import SWT.work.MyData;
import SWT.work.MyDataListenerAdapter;

public class TestEvent {

	public static MyData data = new MyData();

	public static void main(String[] args) {

		// Java 8方式：
		new Thread(() -> {
			data.registerListener(new MyDataListenerAdapter() {
				@Override
				public void addData(final Event e) {
					System.err.println("addData");
				}
			});
		}).start();



		data.registerListener(new MyDataListenerAdapter() {
			@Override
			public void playData(final Event e) {
				System.err.println("playData");
			}
		});

		data.play();
		data.add(new MyData(1,2,3));
	System.err.println("a");
	while(true){

	}



}

}
