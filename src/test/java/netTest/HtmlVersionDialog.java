package netTest;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class HtmlVersionDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text txtbleethnie;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public HtmlVersionDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open(int id) {
		createContents(id);
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
	private void createContents(int id) {
		shell = new Shell(getParent(), getStyle());
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		shell.setSize(521, 238);
		shell.setText("五子棋对话框");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
				if(id==1){
			        try {
			        	URI uri = new URI("www.baidu.com");  
						Desktop.getDesktop().browse(uri);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
				}
			}
		});
		btnNewButton.setBounds(104, 149, 80, 27);
		btnNewButton.setText("确认");
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnNewButton_1.setBounds(326, 149, 80, 27);
		btnNewButton_1.setText("取消");
		
		txtbleethnie = new Text(shell, SWT.BORDER | SWT.MULTI);
		txtbleethnie.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
		if(id==1){
			txtbleethnie.setBounds(26, 38, 464, 72);
			txtbleethnie.setText("即将打开浏览器切换到网络版本...");
		}else{
			txtbleethnie.setBounds(26, 20, 464, 123);
			txtbleethnie.setText("开发作者：BleethNie\r\n"
								+ "开发版本：0.9.1\r\n"
								+ "开发时间：2017-07-08 13:12:24\r\n"
								+ "作者邮箱：xiao5406710@foxmail.com");
		}
		txtbleethnie.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		//txtbleethnie.setBounds(26, 38, 464, 72);
	}
	
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		URI uri = new URI("www.baidu.com");  
        Desktop.getDesktop().browse(uri);  
	}

}
