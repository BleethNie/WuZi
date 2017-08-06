package netTest;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class GameOverDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GameOverDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open(Boolean blackFlag) {
		createContents(blackFlag);
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
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
	private void createContents(Boolean blackFlag) {
		shell = new Shell(getParent(), getStyle());
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		shell.setSize(482, 223);
		shell.setText("游戏结束");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.isDisposed();
				shell.close();
				//shell.getDisplay().dispose();
			}
		});
		btnNewButton.setBounds(196, 142, 80, 27);
		btnNewButton.setText("确认");
		
		text = new Text(shell, SWT.BORDER| SWT.CENTER);
		text.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		text.setBounds(27, 25, 418, 89);
		if(blackFlag==null){
			text.setText("游戏结束,红黑双方打成平局...");
		}else{
			if(blackFlag){
				text.setText("游戏结束,恭喜黑方获得胜利...");
			}else{
				text.setText("游戏结束,恭喜白方获得胜利...");
			}
		}
		

	}
}
