package netTest;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.IOUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import wuzi.dialog.GameOverDialog;
import wuzi.dialog.HtmlVersionDialog;
import wuzi.dialog.NetStartDialog;

public class Wuzi {
	private Shell shell;
	private Composite composite_1;
	private Boolean blackFlag = false;// 黑子下棋标志
	private Boolean startFlag = false;// 开始标志
	private Boolean netFlag;// 网络对战标志
	private Canvas canvas;// 布局
	private GC gc;// 画图
	private Timer t;// 计时器线程
	static ServerSocket ss;

	// 记录黑白棋子的坐标
	private List<Integer> blackList;
	private List<Integer> whiteList;

	private Text timeMesText;
	private Text timeMes;
	private Text chessMes;
	private Text chessMesText;
	private Text chessStatus;
	private Text chessStatusText;
	final String PATH = System.getProperty("user.dir") + IOUtils.DIR_SEPARATOR + "result" + IOUtils.DIR_SEPARATOR;
	final int MAX = 169;
	private Display display;
	private Point point;
	private ServerSocket server;

	//全局客户端IO
	private PrintWriter clientout;
	private BufferedReader clientoutin;

	//服务器端IO
	private PrintWriter oos;
	private BufferedReader ois;

	public void createContents() {
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		// blackImage = new Image(, null);
		shell.setLayout(new FillLayout());
		shell.setSize(1100, 850);

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem startMenu = new MenuItem(menu, SWT.CASCADE);
		startMenu.setText("开始");

		Menu menu_1 = new Menu(startMenu);
		startMenu.setMenu(menu_1);

		MenuItem mntmNewSubmenu_3 = new MenuItem(menu_1, SWT.CASCADE);
		mntmNewSubmenu_3.setText("New SubMenu");

		Menu menu_7 = new Menu(mntmNewSubmenu_3);
		mntmNewSubmenu_3.setMenu(menu_7);

		MenuItem mntmNewSubmenu_4 = new MenuItem(menu_7, SWT.CASCADE);
		mntmNewSubmenu_4.setText("New SubMenu");

		Menu menu_8 = new Menu(mntmNewSubmenu_4);
		mntmNewSubmenu_4.setMenu(menu_8);

		MenuItem mntmNewSubmenu_5 = new MenuItem(menu_7, SWT.CASCADE);
		mntmNewSubmenu_5.setText("New SubMenu");

		Menu menu_9 = new Menu(mntmNewSubmenu_5);
		mntmNewSubmenu_5.setMenu(menu_9);

		MenuItem mntmNewSubmenu_6 = new MenuItem(menu_1, SWT.CASCADE);
		mntmNewSubmenu_6.setText("New SubMenu");

		Menu menu_10 = new Menu(mntmNewSubmenu_6);
		mntmNewSubmenu_6.setMenu(menu_10);

		MenuItem mntmNewSubmenu_7 = new MenuItem(menu_10, SWT.CASCADE);
		mntmNewSubmenu_7.setText("New SubMenu");

		Menu menu_11 = new Menu(mntmNewSubmenu_7);
		mntmNewSubmenu_7.setMenu(menu_11);

		MenuItem mntmNewSubmenu_8 = new MenuItem(menu_10, SWT.CASCADE);
		mntmNewSubmenu_8.setText("New SubMenu");

		Menu menu_12 = new Menu(mntmNewSubmenu_8);
		mntmNewSubmenu_8.setMenu(menu_12);

		MenuItem mntmNewItem_1 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_1.setText("开启网络服务器");
		mntmNewItem_1.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// 设置按钮是否可用
				try {
					ss = new ServerSocket(4567);
					// 监听端口5555
					new Thread() {


						public void run() {
							while (!blackFlag) {
								Socket s;
								try {
									s = ss.accept();
									// 接受客户端连接

									try {
										oos = new PrintWriter(s.getOutputStream());
										ois = new BufferedReader(new InputStreamReader(s.getInputStream()));
										// 以上顺利不可倒,必须先out,再in,否则可能出现死锁,服务器跟客户端都等待对方发送数据
										String md = ois.readLine();
										// 得到客户端发送的数据
										System.out.println("md" + md);
										if ("1110".equals(md)) {
											display.asyncExec(new Runnable() {

												@Override
												public void run() {
													// TODO Auto-generated method stub
													reStart();
												}
											});
											netFlag = true;
											// 客戶端後手
											blackFlag = true;

											startFlag = true;
										}
										System.err.println("服务器启动完成");
										oos.println("0110");
										oos.flush();
										// 发回给客户端
										oos.close();
										ois.close();
										// 关闭流
									} catch (Exception e) {

									}

								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}.start();

				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}

			}
		});

		MenuItem mntmNewItem_2 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_2.setText("连接网络服务器");
		mntmNewItem_2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

			}
		});

		//客户端
		MenuItem netStart = new MenuItem(menu_1, SWT.NONE);
		netStart.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				NetStartDialog nsd = new NetStartDialog(shell, SWT.CLOSE);
				nsd.open();
				String address = nsd.result;

				String ss = null;
				try {
					Socket client = new Socket(address, 4567);
					clientout = new PrintWriter(client.getOutputStream());
					clientout.println("1110");
					clientout.flush();
					clientoutin = new BufferedReader(new InputStreamReader(client.getInputStream()));
					ss = clientoutin.readLine();

					if (ss.startsWith("0110")) {
						reStart();
						netFlag = true;
						// 客戶端後手
						blackFlag = false;
						startFlag = true;
					}
					System.err.println("客户端开启");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.err.println("address=" + address);
			}
		});
		netStart.setText("开启网络对战");

		MenuItem reStartSubMenu = new MenuItem(menu_1, SWT.NONE);
		reStartSubMenu.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				reStart();
			}
		});
		reStartSubMenu.setText("重新开始");

		MenuItem overGameSubMenu = new MenuItem(menu_1, SWT.NONE);
		overGameSubMenu.setText("结束对局");

		MenuItem featureMenu = new MenuItem(menu, SWT.CASCADE);
		featureMenu.setText("菜单");

		Menu menu_2 = new Menu(featureMenu);
		featureMenu.setMenu(menu_2);

		MenuItem saveGameSubMenu = new MenuItem(menu_2, SWT.NONE);
		saveGameSubMenu.setText("保存对局");

		MenuItem mntmNewSubmenu = new MenuItem(menu_2, SWT.CASCADE);
		mntmNewSubmenu.setText("New SubMenu");

		Menu menu_4 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_4);

		MenuItem mntmNewSubmenu_1 = new MenuItem(menu_4, SWT.CASCADE);
		mntmNewSubmenu_1.setText("New SubMenu");

		Menu menu_5 = new Menu(mntmNewSubmenu_1);
		mntmNewSubmenu_1.setMenu(menu_5);

		MenuItem mntmNewSubmenu_2 = new MenuItem(menu_4, SWT.CASCADE);
		mntmNewSubmenu_2.setText("New SubMenu");

		Menu menu_6 = new Menu(mntmNewSubmenu_2);
		mntmNewSubmenu_2.setMenu(menu_6);

		MenuItem loadGameSubMenu = new MenuItem(menu_2, SWT.NONE);
		loadGameSubMenu.setText("加载对局");

		MenuItem mntmNewItem = new MenuItem(menu_2, SWT.NONE);
		mntmNewItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				undoChess();
			}
		});
		mntmNewItem.setText("悔棋");

		MenuItem helpMenu = new MenuItem(menu, SWT.CASCADE);
		helpMenu.setText("帮助");

		Menu menu_3 = new Menu(helpMenu);
		helpMenu.setMenu(menu_3);

		MenuItem introGameSubMenu = new MenuItem(menu_3, SWT.NONE);
		introGameSubMenu.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				System.err.println("aaa");
				HtmlVersionDialog d = new HtmlVersionDialog(shell, SWT.CLOSE);
				d.open(1);
			}
		});
		introGameSubMenu.setText("切换游戏");

		MenuItem themeGameSubMenu = new MenuItem(menu_3, SWT.NONE);
		themeGameSubMenu.setText("软件介绍");
		themeGameSubMenu.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				System.err.println("aaa");
				HtmlVersionDialog d = new HtmlVersionDialog(shell, SWT.CLOSE);
				d.open(2);
			}
		});

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = 3;
		formLayout.marginHeight = 3;
		composite.setLayout(formLayout);

		composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new FillLayout());
		FormData fd_TFolder = new FormData();
		fd_TFolder.left = new FormAttachment(0, 50);
		fd_TFolder.top = new FormAttachment(0, 50);
		fd_TFolder.right = new FormAttachment(0, 750);
		fd_TFolder.bottom = new FormAttachment(100, -50);
		composite_1.setLayoutData(fd_TFolder);

		canvas = new Canvas(composite_1, SWT.NONE);
		gc = new GC(canvas);
		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));

		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (!startFlag) {
					return;
				}
				if (netFlag != null && netFlag) {

					getChessPointForNetWork(e);
					reDrawChess();
					WuziUtils.sound(1);

					if (blackFlag == false) {// 由于在getChessPointForSingle()方法中flag会切换，所以此时是blackFlag==false表示黑棋已经下完
						chessMesText.append("黑方:" + blackList.get((blackList.size() - 1)) / 100 + ":"
								+ blackList.get((blackList.size() - 1)) % 100 + chessMesText.getLineDelimiter());
						chessStatusText.setText("白方:正在执子..." + chessMesText.getLineDelimiter() + "黑方:等待执子...");
						if (WuziUtils.getWin(blackList)) {
							actionEventForWin(true);
						}
					} else {
						chessMesText.append("白方:" + whiteList.get((whiteList.size() - 1)) / 100 + ":"
								+ whiteList.get((whiteList.size() - 1)) % 100 + chessMesText.getLineDelimiter());
						chessStatusText.setText("黑方:正在执子..." + chessMesText.getLineDelimiter() + "白方:等待执子...");
						if (WuziUtils.getWin(whiteList)) {
							actionEventForWin(false);
						}
					}

					if (blackList.size() + whiteList.size() == MAX) {
						actionEventForWin(null);
					}
				} else {
					getChessPointForSingle(e);
					reDrawChess();
					WuziUtils.sound(1);

					if (blackFlag == false) {// 由于在getChessPointForSingle()方法中flag会切换，所以此时是blackFlag==false表示黑棋已经下完
						chessMesText.append("黑方:" + blackList.get((blackList.size() - 1)) / 100 + ":"
								+ blackList.get((blackList.size() - 1)) % 100 + chessMesText.getLineDelimiter());
						chessStatusText.setText("白方:正在执子..." + chessMesText.getLineDelimiter() + "黑方:等待执子...");
						if (WuziUtils.getWin(blackList)) {
							actionEventForWin(true);
						}
					} else {
						chessMesText.append("白方:" + whiteList.get((whiteList.size() - 1)) / 100 + ":"
								+ whiteList.get((whiteList.size() - 1)) % 100 + chessMesText.getLineDelimiter());
						chessStatusText.setText("黑方:正在执子..." + chessMesText.getLineDelimiter() + "白方:等待执子...");
						if (WuziUtils.getWin(whiteList)) {
							actionEventForWin(false);
						}
					}

					if (blackList.size() + whiteList.size() == MAX) {
						actionEventForWin(null);
					}
				}

			}
		});

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				drawChessBoard();
				reDrawChess();
			}
		});

		Label label = new Label(composite, SWT.SEPARATOR | SWT.VERTICAL);

		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(0, 800);
		fd_label.top = new FormAttachment(0, 0);
		fd_label.right = new FormAttachment(0, 810);
		fd_label.bottom = new FormAttachment(100, 0);
		label.setLayoutData(fd_label);

		timeMesText = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		timeMesText.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
		timeMesText.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		FormData fd_timeMesText = new FormData();
		fd_timeMesText.bottom = new FormAttachment(composite_1, 86);
		fd_timeMesText.top = new FormAttachment(0, 98);
		fd_timeMesText.left = new FormAttachment(label, 33);
		fd_timeMesText.right = new FormAttachment(100, -10);
		timeMesText.setLayoutData(fd_timeMesText);

		timeMes = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		timeMes.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		timeMes.setText("对战时间");
		FormData fd_timeMes = new FormData();
		fd_timeMes.right = new FormAttachment(timeMesText, 0, SWT.RIGHT);
		fd_timeMes.bottom = new FormAttachment(timeMesText, -24);
		fd_timeMes.left = new FormAttachment(timeMesText, 3, SWT.LEFT);
		timeMes.setLayoutData(fd_timeMes);

		chessMes = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		chessMes.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		chessMes.setText("对战信息");
		FormData fd_chessMes = new FormData();
		fd_chessMes.left = new FormAttachment(timeMesText, 0, SWT.LEFT);
		fd_chessMes.top = new FormAttachment(timeMesText, 27);
		fd_chessMes.right = new FormAttachment(timeMesText, 0, SWT.RIGHT);
		chessMes.setLayoutData(fd_chessMes);

		chessMesText = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL);
		chessMesText.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		FormData fd_chessMesText = new FormData();
		fd_chessMesText.bottom = new FormAttachment(chessMes, 408, SWT.BOTTOM);
		fd_chessMesText.right = new FormAttachment(timeMesText, 0, SWT.RIGHT);
		fd_chessMesText.top = new FormAttachment(chessMes, 26);
		fd_chessMesText.left = new FormAttachment(timeMesText, 0, SWT.LEFT);
		chessMesText.setLayoutData(fd_chessMesText);

		chessStatus = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		chessStatus.setText("对战状态");
		FormData fd_chessStatus = new FormData();
		fd_chessStatus.top = new FormAttachment(chessMesText, 32);
		fd_chessStatus.right = new FormAttachment(timeMesText, -3, SWT.RIGHT);
		fd_chessStatus.left = new FormAttachment(timeMesText, 0, SWT.LEFT);
		chessStatus.setLayoutData(fd_chessStatus);

		chessStatusText = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.MULTI);
		FormData fd_chessStatusText = new FormData();
		fd_chessStatusText.bottom = new FormAttachment(composite_1, 0, SWT.BOTTOM);
		fd_chessStatusText.right = new FormAttachment(timeMesText, 0, SWT.RIGHT);
		fd_chessStatusText.top = new FormAttachment(chessStatus, 18);
		fd_chessStatusText.left = new FormAttachment(timeMesText, 0, SWT.LEFT);
		chessStatusText.setLayoutData(fd_chessStatusText);

	}

	// 提供服务
	public void service() {
		try {// 建立服务器连接
			server = new ServerSocket(3394);
			// 等待客户连接
			Socket socket = server.accept();

			try {
				// 读取客户端传过来信息的DataInputStream
				DataInputStream in = new DataInputStream(socket.getInputStream());
				// 向客户端发送信息的DataOutputStream
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				// 获取控制台输入的Scanner
				Scanner scanner = new Scanner(GoBangSystem.in);
				while (true) {
					// 读取来自客户端的信息
					String accpet = in.readUTF();
					System.out.println(accpet);
					String send = scanner.nextLine();
					System.out.println("服务器：" + send);
					// 把服务器端的输入发给客户端
					out.writeUTF("服务器：" + send);
				}
			} finally {// 建立连接失败的话不会执行socket.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopservice() {

	}

	/**
	 * Launch the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Wuzi window = new Wuzi();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		point = new Point();
		createContents();
		shell.open();
		shell.layout();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// 定时器时间功能
	void init() {
		blackFlag = true;
		startFlag = true;
		netFlag = false;
		blackList = new ArrayList<Integer>();
		whiteList = new ArrayList<Integer>();
		if (gc == null || gc.isDisposed()) {
			gc = new GC(canvas);
		}
		canvas.setData(null);
		canvas.redraw();
		drawChessBoard();
		startTimer();
	}

	void dispose() {
		blackList = null;
		whiteList = null;
		gc = null;
		chessMesText.setText("");
		chessStatusText.setText("");
	}

	// 棋盘显示功能
	void drawChessBoard() {
		if (gc == null || gc.isDisposed()) {
			gc = new GC(canvas);
		}
		gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		for (int i = 1; i < 14; i++) {
			gc.drawLine(i * 50, 50, i * 50, 650);// 竖线
			gc.drawLine(50, i * 50, 650, i * 50);// 横线
		}
		gc.fillOval(345, 345, 10, 10);
		gc.fillOval(245, 245, 10, 10);
		gc.fillOval(445, 245, 10, 10);
		gc.fillOval(245, 445, 10, 10);
		gc.fillOval(445, 445, 10, 10);

	}

	// 棋子重绘功能
	void reDrawChess() {
		if (blackList != null) {
			if (gc == null || gc.isDisposed()) {
				gc = new GC(canvas);
			}
			int x = 0;
			int y = 0;
			for (int i = 0; i < blackList.size(); i++) {
				x = blackList.get(i) % 100;
				y = blackList.get(i) / 100;
				point.drawPoint(display, gc, x * 50 - 15, y * 50 - 15, Point.COLOR_BLACK);
			}
			for (int i = 0; i < whiteList.size(); i++) {
				x = whiteList.get(i) % 100;
				y = whiteList.get(i) / 100;
				point.drawPoint(display, gc, x * 50 - 15, y * 50 - 15, Point.COLOR_WHITWE);
			}
		}
	}

	// 悔棋功能
	void undoChess() {
		if (!startFlag) {
			return;
		}
		if (blackFlag) {
			if (whiteList == null || whiteList.size() <= 0) {
				return;
			}
			whiteList.remove(whiteList.size() - 1);
			blackFlag = !blackFlag;
		} else {
			if (blackList == null || blackList.size() <= 0) {
				return;
			}
			blackList.remove(blackList.size() - 1);
			blackFlag = !blackFlag;
		}

		canvas.setData(null);
		canvas.redraw();
		reDrawChess();
		chessMesText.setText(chessMesText.getText().substring(0, chessMesText.getText().lastIndexOf("方") - 1));
	}

	// 重新开始功能
	void reStart() {
		dispose();
		init();
	}

	// 单机上获取棋子的方法
	void getChessPointForSingle(MouseEvent e) {
		if (!startFlag) {
			return;
		}

		int x = e.x / 50 + (e.x % 50 > 25 ? 1 : 0);
		int y = e.y / 50 + (e.y % 50 > 25 ? 1 : 0);
		if (x == 0 || x == 14 || y == 0 || y == 14) {
			return;
		}

		if (blackFlag == true) {

			if (blackList.contains(x + y * 100)) {
				return;
			}
			if (whiteList.contains(x + y * 100)) {
				return;
			}
			blackList.add(x + y * 100);
		} else {
			if (blackList.contains(x + y * 100)) {
				return;
			}
			if (whiteList.contains(x + y * 100)) {
				return;
			}
			whiteList.add(x + y * 100);
		}
		blackFlag = !blackFlag;
	}

	void actionEventForWin(Boolean boo) {
		startFlag = false;
		t.cancel();
		WuziUtils.sound(2);
		GameOverDialog gd = new GameOverDialog(shell, SWT.CLOSE);
		gd.open(boo);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		try {
			IOUtils.write(PATH + blackList.toString() + "\r\n" + whiteList.toString(),
					new FileOutputStream(new File(PATH + df.format(new Date()) + ".txt")), "UTF-8");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	// 网络版获取棋子的方法
	void getChessPointForNetWork(MouseEvent e) {
		int x = e.x / 50 + (e.x % 50 > 25 ? 1 : 0);
		int y = e.y / 50 + (e.y % 50 > 25 ? 1 : 0);
		if (x == 0 || x == 14 || y == 0 || y == 14) {
			return;
		}

		if(blackFlag){

			blackList.add(x+y*100);
			//服务器端IO
			oos.println("A"+x+y*100+"B");
			oos.flush();
		}else{
			whiteList.add(x+y*100);
			clientout.println("B"+x+y*100+"B");
			clientout.flush();
		}

	}

	// 启动计时器
	void startTimer() {
		if (t != null) {
			t.cancel();
		}
		t = new Timer("name", true);
		t.schedule(new TimerTask() {
			String H = "";
			int h = 0;
			int m = 0;
			int s = 0;
			String M = "";
			String S = "";
			int Counter = 0;

			@Override
			public void run() {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						++Counter;
						h = Counter / 3600;
						m = (Counter - h * 3600) / 60;
						s = (Counter - h * 3600 - 60 * m);
						if (h >= 10) {
							H = "" + h;
						} else {
							H = "0" + h;
						}
						if (m >= 10) {
							M = "" + m;
						} else {
							M = "0" + m;
						}
						if (s >= 10) {
							S = "" + s;
						} else {
							S = "0" + s;
						}
						timeMesText.setText(H + ":" + M + ":" + S);
					}
				});
			}
		}, 0, 1000);
	}



}
