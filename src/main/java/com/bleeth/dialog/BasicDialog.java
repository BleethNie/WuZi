package com.bleeth.dialog;

import com.bleeth.system.ASystem;
import com.bleeth.system.SingleSystem;
import com.bleeth.util.ImageUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BasicDialog {



    protected Shell shell;
    private Composite composite;
    private Button button_one;
    private Button button_two;
    private Button button_three;
    private Button helpButton;
    private Button exitButton;

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            BasicDialog window = new BasicDialog();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell(SWT.CLOSE | SWT.MIN);
        shell.setSize(700, 400);
        shell.setText("五子棋大战");
        shell.setImage(ImageUtil.getImage(ImageUtil.RESOURCE_PATH + "/image/chess.png"));


        composite = new Composite(shell, SWT.NONE);
        composite.setBackgroundImage(ImageUtil.getImage(ImageUtil.RESOURCE_PATH + "/image/bg_2.jpg"));
        composite.setBounds(0, 0, 680, 360);

        button_one = new Button(composite, SWT.NONE);
        button_one.setImage(ImageUtil.getImage(ImageUtil.RESOURCE_PATH + "/image/单人对战_1.jpg"));
        button_one.setBounds(267, 111, 160, 30);
        button_one.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                //单人对战
                shell.close();
                BattleDialog bd = new BattleDialog();
                ASystem net = new SingleSystem();
                bd.open(net);
            }
        });


        button_two = new Button(composite, SWT.NONE);
        button_two.setImage(ImageUtil.getImage(ImageUtil.RESOURCE_PATH + "/image/双人对战.jpg"));
        button_two.setBounds(267, 174, 160, 30);







        button_three = new Button(composite, SWT.NONE);
        button_three.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                shell.close();
                NetConnectDialog bd = new NetConnectDialog();
                bd.open();
            }
        });
        button_three.setImage(ImageUtil.getImage(ImageUtil.RESOURCE_PATH + "/image/网络对战.jpg"));
        button_three.setBounds(267, 232, 160, 30);

        helpButton = new Button(composite, SWT.NONE);
        helpButton.setImage(ImageUtil.getImage(ImageUtil.RESOURCE_PATH + "/image/帮助.jpg"));
        helpButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

            }
        });
        helpButton.setBounds(162, 301, 100, 30);

        exitButton = new Button(composite, SWT.NONE);
        exitButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.close();
            }
        });
        exitButton.setImage(ImageUtil.getImage(ImageUtil.RESOURCE_PATH + "/image/退出.jpg"));
        exitButton.setBounds(431, 301, 100, 30);
    }
}
