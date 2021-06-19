package com.bleeth.dialog;

import com.bleeth.chess.Point;
import com.bleeth.system.SingleSystem;
import com.bleeth.system.WuZiSystem;
import com.bleeth.util.ResourceUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;


public class SingleBattleDialog {

    protected Shell singleBattleShell;

    private Display display;

    private  boolean isRun ;

    private WuZiSystem wuZiSystem;

    private Text text;

    private Text text_1;


    public static void main(String[] args) {
        SingleBattleDialog dialog = new SingleBattleDialog();
        dialog.open();
    }

    /**
     * Open the window.
     */
    public void open( ) {
        display = Display.getDefault();
        wuZiSystem = new SingleSystem();
        createContents();
        singleBattleShell.open();
        singleBattleShell.layout();
        wuZiSystem.init();

        while (!singleBattleShell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        singleBattleShell = new Shell(SWT.CLOSE | SWT.MIN);
        singleBattleShell.setSize(1050, 670);
        singleBattleShell.setText("WuZi");
        singleBattleShell.setImage(ResourceUtil.getImage(ResourceUtil.RESOURCE_PATH + "/image/chess.png"));

        Point point = new Point();
        Composite composite = new Composite(singleBattleShell, SWT.NONE);
        GC gc = new GC(composite);
        composite.setBackgroundImage(ResourceUtil.getImage(ResourceUtil.RESOURCE_PATH + "/image/棋盘.png"));
        composite.setBounds(0, 0, 600, 600);

        Composite composite_1 = new Composite(singleBattleShell, SWT.NONE);
        composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
        composite_1.setBackgroundImage(ResourceUtil.getImage(ResourceUtil.RESOURCE_PATH + "/image/控制面板.png"));
        composite_1.setBounds(600, 0, 430, 300);



        ToolBar toolBar = new ToolBar(composite_1, SWT.FLAT | SWT.RIGHT);
        toolBar.setBackground(SWTResourceManager.getColor(173, 216, 230));
        toolBar.setBounds(0, 0, 430, 27);

        ToolItem startToolItem = new ToolItem(toolBar, SWT.NONE);
        startToolItem.setToolTipText("开始");
        startToolItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                isRun = true;
                System.err.println("start。。。isRun:"+isRun);
            }
        });
        startToolItem.setHotImage(null);
        startToolItem.setImage(ResourceUtil.getImage(ResourceUtil.RESOURCE_PATH + "/icon/icons8-开始-20.png"));

        ToolItem tltmNewItem_1 = new ToolItem(toolBar, SWT.NONE);
        tltmNewItem_1.setToolTipText("暂停");
        tltmNewItem_1.setImage(ResourceUtil.getImage(ResourceUtil.RESOURCE_PATH + "/icon/icons8-暂停-20.png"));

        ToolItem tltmNewItem_2 = new ToolItem(toolBar, SWT.NONE);
        tltmNewItem_2.setToolTipText("撤销");
        tltmNewItem_2.setImage(ResourceUtil.getImage(ResourceUtil.RESOURCE_PATH + "/icon/icons8-撤销-20.png"));
        tltmNewItem_2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });

        ToolItem tltmNewItem_3 = new ToolItem(toolBar, SWT.NONE);
        tltmNewItem_3.setToolTipText("重新开始");
        tltmNewItem_3.setImage(ResourceUtil.getImage(ResourceUtil.RESOURCE_PATH + "/icon/icons8-重启-20.png"));

        ToolItem tltmNewItem_4 = new ToolItem(toolBar, SWT.NONE);
        tltmNewItem_4.setToolTipText("退出");
        tltmNewItem_4.setImage(ResourceUtil.getImage(ResourceUtil.RESOURCE_PATH + "/icon/icons8-关闭-20.png"));

        ToolItem tltmNewItem_9 = new ToolItem(toolBar, SWT.NONE);
        tltmNewItem_9.setImage(ResourceUtil.getImage(ResourceUtil.RESOURCE_PATH + "/icon/icons8-Shuffle-20.png"));

        Composite composite_2 = new Composite(singleBattleShell, SWT.NONE);
        composite_2.setBackgroundImage(ResourceUtil.getImage(ResourceUtil.RESOURCE_PATH + "/image/控制面板.png"));
        composite_2.setBounds(600, 300, 430, 300);


        Label lblNewLabel = new Label(composite_1, SWT.NONE);
        lblNewLabel.setBounds(29, 145, 391, 53);
        lblNewLabel.setText("计时器");


        text = new Text(composite_2, SWT.BORDER);
        text.setBounds(24, 55, 385, 147);

        text_1 = new Text(composite_2, SWT.BORDER);
        text_1.setBounds(24, 208, 385, 49);

        Button btnNewButton_3 = new Button(composite_2, SWT.NONE);
        btnNewButton_3.setBounds(24, 263, 80, 27);
        btnNewButton_3.setText("发送");

        Button btnNewButton_4 = new Button(composite_2, SWT.NONE);
        btnNewButton_4.setBounds(329, 263, 80, 27);
        btnNewButton_4.setText("撤销");

        Composite composite_3 = new Composite(singleBattleShell, SWT.NONE);
        composite_3.setBounds(0, 601, 1030, 30);

        Label lblNewLabel_1 = new Label(composite_3, SWT.NONE);
        lblNewLabel_1.setLocation(154, 0);
        lblNewLabel_1.setSize(137, 27);
        lblNewLabel_1.setText("aa");

        Label label = new Label(composite_3, SWT.NONE);
        label.setText("aa");
        label.setBounds(0, 0, 137, 27);
        composite.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(MouseEvent e) {
                Point p = changePoint(e, point, wuZiSystem);
                if (p == null) {
                    return;
                }

                wuZiSystem.playChess(display, gc, p);
                if (wuZiSystem.getWinMessage() == 1) {
                    WinDialog wd = new WinDialog();
                    wd.open("黑方");
                    isRun = !isRun;
                } else if (wuZiSystem.getWinMessage() == 2) {
                    WinDialog wd = new WinDialog();
                    wd.open("白方");
                    isRun = !isRun;
                } else {
                    System.err.println("未出结果");
                }

            }
        });

    }


    /**
     * 棋子边缘检测，防止棋子落入棋盘外，和非格子中
     * @param e
     * @param p
     * @param system
     * @return
     */
    public Point changePoint(MouseEvent e, Point p, WuZiSystem system) {
        System.out.println("changePoint");
        if (!isRun) {
            return null;
        }
        int x, y;
        if (e.x <= 30 || e.x >= 540 || e.y <= 30 || e.y >= 540) {
            return null;
        }
        if ((e.x - 40) <= 0) {
            x = 1;
        } else {
            x = e.x - 40;
        }
        if ((e.y - 40) <= 0) {
            y = 1;
        } else {
            y = e.y - 40;
        }

        x = x / 35 + (x % 35 > 10 ? 1 : 0);
        y = y / 35 + (y % 35 > 10 ? 1 : 0);
        if (!system.isRight(x, y)) {
            return null;
        }

        System.out.println("isRun = " + isRun + "  " + "x = " + x + "  " + "y = " + y);
        return p.setPoint(x, y);

    }
}
