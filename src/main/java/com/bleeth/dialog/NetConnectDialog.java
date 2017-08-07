package com.bleeth.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.bleeth.system.ASystem;
import com.bleeth.system.NetSystem;
import com.bleeth.test.NetWorkScanner;

public class NetConnectDialog {

	protected Object result;
	private Display display;
	protected Shell shell;
	private Text text;
	private List list;

	public static void main(String[] args) {
		try {
			NetConnectDialog window = new NetConnectDialog();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the dialog.
	 *
	 * @return the result
	 */
	public Object open() {
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell();
		shell.setSize(557, 300);
		shell.setText("AA");

		text = new Text(shell, SWT.BORDER);
		text.setBounds(136, 170, 230, 23);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(22, 173, 61, 17);
		lblNewLabel.setText("指定IP");

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 发送

				String txt = text.getText();

				if (!ipCheck(txt) ^ ipCheck("192.168.1." + txt) ^ ipCheck("192.168." + txt)) {
					btnNewButton.setToolTipText("IP地址不合法");
					return;
				}

				ASystem net = new NetSystem("192.168." + txt);
				shell.close();
				BattleDialog bd = new BattleDialog();
				bd.open(net);

			}
		});
		btnNewButton.setBounds(422, 168, 80, 27);
		btnNewButton.setText("发送");

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(22, 78, 61, 17);
		lblNewLabel_1.setText("IP自动扫描");

		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 自动扫描
				// Java 8方式：
				Thread th = new Thread(() -> {
					try {
						NetWorkScanner b = new NetWorkScanner();
						b.scan();
						b.getIP(8899);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				});
				th.start();

				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						try {
							for (int i = 0; i < NetWorkScanner.goodIP.size(); i++) {

								list.add(NetWorkScanner.goodIP.take());

							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

			}

		});
		btnNewButton_1.setBounds(422, 73, 80, 27);
		btnNewButton_1.setText("开始扫描");

		ProgressBar progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setBounds(136, 234, 368, 17);

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(22, 234, 61, 17);
		lblNewLabel_2.setText("扫描进度条");

		list = new List(shell, SWT.BORDER | SWT.H_SCROLL);
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				int selectedItems = list.getSelectionIndex();
				text.setText(list.getItem(selectedItems));
				System.err.println(list.getItem(selectedItems));
			}
		});
		list.setBounds(133, 27, 233, 104);

	}

	/**
	 * 判断IP地址的合法性，这里采用了正则表达式的方法来判断 return true，合法
	 */
	public static boolean ipCheck(String text) {
		if (text != null && !text.isEmpty()) {
			// 定义正则表达式
			String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
					+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
					+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
			// 判断ip地址是否与正则表达式匹配
			if (text.matches(regex)) {
				// 返回判断信息
				return true;
			} else {
				// 返回判断信息
				return false;
			}
		}
		return false;
	}

}
