package com.bleeth.dialog;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class WinDialog   {

	protected Object result;
	protected Shell shell;


	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open(String str) {
		createContents( str);
		shell.open();
		shell.layout();
		Display display = Display.getDefault();
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
	private void createContents(String str) {
		shell = new Shell();
		shell.setSize(450, 250);
		shell.setText("");

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setBounds(78, 98, 299, 17);
		lblNewLabel.setText("恭喜    "+  str  +"    获得胜利");

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnNewButton.setBounds(183, 154, 80, 27);
		btnNewButton.setText("确认");

	}
}
