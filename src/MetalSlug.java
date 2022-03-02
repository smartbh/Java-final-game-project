import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.awt.*;

public class MetalSlug extends JFrame {
	private MainScreen p = new MainScreen();
	private StageSelect ss = new StageSelect();
	private GameRule grule = new GameRule();
	private Container c = new Container();
	public int count = 0;
	// 스테이지 xml
	public XMLReader xml = new XMLReader("C:\\Temp\\slug\\stage1.xml");
	public XMLReader xml2 = new XMLReader("C:\\Temp\\slug\\stage2.xml");

	private ImageIcon bulletIcon = new ImageIcon("C:\\Temp\\slug\\images\\bullet.png"); // 총알
																						// 이미지
	private Image bulletImg = bulletIcon.getImage();

	public JLabel bulletLabel = new JLabel(bulletIcon);

	public Character realPlayer[] = new Character[1]; // 플레이어
	public Character realPlayer1[] = new Character[1];

	public Vector<Bullet> bulletList = new Vector<Bullet>();
	public Vector<Block> blockList = new Vector<Block>();

	private Clip clip;

	public MetalSlug() {
		setTitle("Mortal Slug");
		createMenu();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램 안전 종료 코드
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		c = getContentPane();

		c.add(p); // 시작화면 띄우기

		int f_xpos = (int) (screen.getWidth() / 2 - 800 / 2);
		int f_ypos = (int) (screen.getHeight() / 2 - 1000 / 2);
		setLocation(f_xpos, f_ypos);

		File bgm;
		AudioInputStream stream;
		AudioFormat format;
		DataLine.Info info;

		bgm = new File("C:\\Temp\\slug\\KissInTheDark.wav");

		Clip clip;

		try {
			stream = AudioSystem.getAudioInputStream(bgm);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.loop(clip.LOOP_CONTINUOUSLY);

		} catch (Exception e) {
			System.out.println("err : " + e);
		}

		setSize(800, 1000);
		setResizable(false);
		setVisible(true);
	}

	private void createMenu() {

		JMenuBar mb = new JMenuBar();
		JMenuItem[] menuItem = new JMenuItem[2];
		String[] itemTitle = { "환경설정", "종료" };
		JMenu option = new JMenu("옵션");

		MenuActionListener listener = new MenuActionListener();
		for (int i = 0; i < menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(listener);
			option.add(menuItem[i]);
		}
		mb.add(option);
		setJMenuBar(mb);
	}

	class MenuActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			switch (cmd) {
			case "환경설정":
				// 여기 뭐라 써야할지 모르겠다
				break;
			case "종료":
				System.exit(0);
				break;
			}
		}
	}

	/////////////// 메인 스크린 패널

	class MainScreen extends JPanel { // 시작 화면 게임 패널

		private ImageIcon logoIcon = new ImageIcon("C:\\Temp\\slug\\images\\logo.png");
		private Image logoImg = logoIcon.getImage();
		private ImageIcon icon = new ImageIcon("C:\\Temp\\slug\\images\\space.png"); // 이미지
																						// 출력할
																						// 레이블
		private Image img = icon.getImage();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (count >= 1) {
				c.removeAll();
				c.repaint();
				c.add(p);

				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
				g.drawImage(logoImg, 200, 60, 400, 260, this);

				this.setLayout(null);

				JButton start, gameRule, exit;

				start = new JButton("게임 시작");
				gameRule = new JButton("게임 방법");
				exit = new JButton("게임 종료");
				start.setBounds(280, 400, 230, 100);
				gameRule.setBounds(280, 580, 230, 100);
				exit.setBounds(280, 760, 230, 100);

				this.add(start);
				this.add(gameRule);
				this.add(exit);

				start.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						c.removeAll();
						c.add(ss);
						c.revalidate();
					}
				});

				gameRule.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						c.removeAll();
						c.add(grule); // 게임 방법 설명 레이블을 넣어야한다.
						c.revalidate();
					}

				});

				exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
			}

			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			g.drawImage(logoImg, 200, 60, 400, 260, this);

			this.setLayout(null);

			JButton start, gameRule, exit;

			start = new JButton("게임 시작");
			gameRule = new JButton("게임 방법");
			exit = new JButton("게임 종료");
			start.setBounds(280, 400, 230, 100);
			gameRule.setBounds(280, 580, 230, 100);
			exit.setBounds(280, 760, 230, 100);

			this.add(start);
			this.add(gameRule);
			this.add(exit);

			start.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c.removeAll();
					c.add(ss);
					c.revalidate();
				}
			});

			gameRule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c.removeAll();
					c.add(grule); // 게임 방법 설명 레이블을 넣어야한다.
					c.revalidate();
				}

			});

			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});

		}

	}

	//////////////// 스테이지 고르는 패널

	class StageSelect extends JPanel {
		private ImageIcon icon = new ImageIcon("C:\\Temp\\slug\\images\\space.png"); // 이미지
																						// 출력할
		// 레이블
		private Image img = icon.getImage();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			g.setColor(Color.white);
			Font f = new Font("돋움체", Font.ITALIC, 80);
			g.setFont(f);
			g.drawString("  스테이지를", 150, 100);
			g.drawString("선택해 주세요", 160, 240);

			JButton stage1, stage2, goBack;
			stage1 = new JButton("스테이지 1");
			stage2 = new JButton("스테이지 2");
			goBack = new JButton();

			stage1.setBounds(280, 400, 230, 100);
			stage2.setBounds(280, 700, 230, 100);
			goBack.setBounds(740, 30, 30, 30);

			this.setLayout(null);
			this.add(stage1);
			this.add(stage2);
			this.add(goBack);

			Node blockGameNode = xml.getBlockGameElement();
			Node sizeNode = xml.getNode(blockGameNode, xml.E_SIZE);
			GameStage1 gstage1 = new GameStage1(xml.getGamePanelElement());
			GameStage2 gstage2 = new GameStage2(xml2.getGamePanelElement());
			String w = xml.getAttr(sizeNode, "w");
			String h = xml.getAttr(sizeNode, "h");

			stage1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c.removeAll();
					c.add(gstage1);
					c.revalidate();

					gstage1.setFocusable(true);
					gstage1.requestFocus();
				}
			});

			stage2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c.removeAll();
					c.add(gstage2);
					c.revalidate();

					gstage2.setFocusable(true);
					gstage2.requestFocus();
				}
			});

			goBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c.removeAll();
					c.add(p);
					c.repaint();
					count++;
				}
			});
		}
	}

	/////////////////// 게임 방법 패널
	class GameRule extends JPanel {
		private ImageIcon icon = new ImageIcon("C:\\Temp\\slug\\images\\space.png"); // 이미지
																						// 출력할
		// 레이블
		private Image img = icon.getImage();
		private JLabel label = new JLabel();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // 배경 그리기

			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			g.setColor(Color.white);
			Font f = new Font("돋움체", Font.ITALIC, 70);
			g.setFont(f);
			g.drawString("How to", 300, 160);
			g.drawString("Play", 330, 250);

			g.setColor(Color.GRAY);
			g.fillRoundRect(100, 320, 600, 480, 20, 20);

			g.setColor(Color.white);
			g.fillRoundRect(110, 330, 580, 460, 20, 20);

			Font ef = new Font("돋움체", Font.ITALIC, 40);
			g.setFont(ef);
			g.setColor(Color.BLACK);
			g.drawString("게임 플레이 방법", 120, 370);
			g.drawString("내려오는 UFO를 격추하세요!", 120, 420);
			g.drawString("1.Z키를 누르면 발싸", 120, 480);
			g.drawString("2.X키를 누르면 폭탄사용", 120, 540);
			g.drawString("3.이동은 방향키로", 120, 600);

			this.setLayout(null);
			JButton goBack = new JButton();
			goBack.setBounds(740, 30, 30, 30);
			this.add(goBack);

			goBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c.removeAll();
					c.add(p);
					p.repaint();
					count++;
				}
			});
		}
	}

	// 블럭
	class Block extends JLabel implements Runnable {
		private Image img = null;
		int x, y;

		public Block(int x, int y, int w, int h, ImageIcon icon) {
			this.setBounds(x, y, w, h);
			if (icon != null)
				img = icon.getImage();

			new Thread(this).start();

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (img != null)
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			else {
				g.setColor(Color.YELLOW);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
					this.setLocation(this.getX(), this.getY() + 10);

				} catch (InterruptedException e) {
					remove(this);
					this.getParent().repaint();
				}
			}
		}
	}

	// 플레이어 그림
	class Character extends JLabel {
		private Image img = null;

		public Character(int x, int y, int w, int h, ImageIcon icon) {
			this.setBounds(x, y, w, h);

			if (icon != null)
				img = icon.getImage();

		}

		public void paintComponent(Graphics g) {
			if (img != null)
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			else {
				g.setColor(Color.YELLOW);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		}

	}

	class Bullet extends JLabel implements Runnable {
		private ImageIcon bulletIcon = new ImageIcon("C:\\Temp\\slug\\images\\bullet.png"); // 총알
																							// 이미지
		private Image bulletImg = bulletIcon.getImage();

		public Bullet(int x, int y, int w, int h) {
			this.setBounds(x, y, w, h);

			new Thread(this).start();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (bulletImg != null)
				g.drawImage(bulletImg, 0, 0, this.getWidth(), this.getHeight(), this);
			else {
				g.setColor(Color.YELLOW);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		}

		public void run() {
			while (true) {
				/*
				 * if(hit()) { bulletList.get(0).interrupt();
				 * bullet.setLocation(bullet.getParent().getWidth()/2 - 5,
				 * bullet.getParent().getHeight()-50); return; }
				 */
				try {
					Thread.sleep(80);
					this.setLocation(this.getX(), this.getY() - 20);

				} catch (InterruptedException e) {
					remove(this);
					this.getParent().repaint();
				}
			}
		}

	}

	// 스테이지1
	class GameStage1 extends JPanel {
		ImageIcon bgImg;
		Image bullet;

		public void shoot() {
			bulletList.add(new Bullet(realPlayer[0].getX(), realPlayer[0].getY(), 50, 30));
			add(bulletList.get(bulletList.size() - 1));

			if (bulletList.get(0).getY() < 100) {
				remove(bulletList.get(3));
				bulletList.remove(3);
			}
		}

		public GameStage1(Node gamePanelNode) {
			setLayout(null);

			JButton goBack;
			goBack = new JButton();
			goBack.setBounds(740, 30, 30, 30);
			this.add(goBack);

			goBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c.removeAll();
					c.add(p);
					c.repaint();
					count++;
				}
			});

			Node bgNode = XMLReader.getNode(gamePanelNode, XMLReader.E_BG);
			String filePath = bgNode.getTextContent();
			bgImg = new ImageIcon(filePath);

			// read <Fish><Obj>s from the XML parse tree, make Food objects, and
			// add them to the FishBowl panel.
			// 블락 노드들
			Node blockNode = XMLReader.getNode(gamePanelNode, XMLReader.E_BLOCK);
			NodeList blnodeList = blockNode.getChildNodes();

			for (int i = 0; i < blnodeList.getLength(); i++) {
				Node blnode = blnodeList.item(i);
				if (blnode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				// found!!, <Obj> tag
				if (blnode.getNodeName().equals(XMLReader.E_OBJ)) {
					int x = Integer.parseInt(XMLReader.getAttr(blnode, "x"));
					int y = Integer.parseInt(XMLReader.getAttr(blnode, "y"));
					int w = Integer.parseInt(XMLReader.getAttr(blnode, "w"));
					int h = Integer.parseInt(XMLReader.getAttr(blnode, "h"));

					ImageIcon icon = null;

					String imgFilePath = XMLReader.getAttr(blnode, "img");

					if (imgFilePath != null)
						icon = new ImageIcon(imgFilePath);

					Block block = new Block(x, y, w, h, icon);
					add(block);
					blockList.add(block);

				}
			}

			// blockArray[0].setLocation(this.getX(), this.getY()+10);

			// 플레이어 노드
			Node charNode = XMLReader.getNode(gamePanelNode, XMLReader.E_CHARCTER);
			NodeList charnodeList = charNode.getChildNodes();

			for (int i = 0; i < charnodeList.getLength(); i++) {
				Node chnode = charnodeList.item(i);
				if (chnode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				// found!!, <Obj> tag
				if (chnode.getNodeName().equals(XMLReader.E_CHAROBJ)) {
					int x = Integer.parseInt(XMLReader.getAttr(chnode, "x"));
					int y = Integer.parseInt(XMLReader.getAttr(chnode, "y"));
					int w = Integer.parseInt(XMLReader.getAttr(chnode, "w"));
					int h = Integer.parseInt(XMLReader.getAttr(chnode, "h"));

					ImageIcon icon = null;

					String charimgFilePath = XMLReader.getAttr(chnode, "img");

					if (charimgFilePath != null)
						icon = new ImageIcon(charimgFilePath);

					Character player = new Character(x, y, w, h, icon);
					add(player);
					realPlayer[0] = player;
				}
			}

			this.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					switch (key) {
					case KeyEvent.VK_LEFT:
						realPlayer[0].setLocation(realPlayer[0].getX() - 10, realPlayer[0].getY());
						break;
					case KeyEvent.VK_RIGHT:
						realPlayer[0].setLocation(realPlayer[0].getX() + 10, realPlayer[0].getY());
						break;

					case KeyEvent.VK_UP:
						realPlayer[0].setLocation(realPlayer[0].getX(), realPlayer[0].getY() - 10);
						break;

					case KeyEvent.VK_DOWN:
						realPlayer[0].setLocation(realPlayer[0].getX(), realPlayer[0].getY() + 10);
						break;

					case KeyEvent.VK_Z:
						shoot();

						break;

					case KeyEvent.VK_X: // 폭탄

						break;

					}
				}
			});

		}

		public void paintComponent(Graphics g) {
			g.drawImage(bgImg.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	// 스테이지2
	class GameStage2 extends JPanel {
		ImageIcon bgImg;
		Image bullet;

		public void shoot() {
			bulletList.add(new Bullet(realPlayer1[0].getX(), realPlayer1[0].getY(), 50, 30));
			add(bulletList.get(bulletList.size() - 1));

			if (bulletList.get(0).getY() < 100) {
				remove(bulletList.get(3));
				bulletList.remove(3);
			}
		}

		public GameStage2(Node gamePanelNode) {
			setLayout(null);
			
			
			
			

			Node bgNode = XMLReader.getNode(gamePanelNode, XMLReader.E_BG);
			String filePath = bgNode.getTextContent();
			bgImg = new ImageIcon(filePath);

			// read <Fish><Obj>s from the XML parse tree, make Food objects, and
			// add them to the FishBowl panel.
			// 블락 노드들
			Node blockNode = XMLReader.getNode(gamePanelNode, XMLReader.E_BLOCK);
			NodeList blnodeList = blockNode.getChildNodes();

			for (int i = 0; i < blnodeList.getLength(); i++) {
				Node blnode = blnodeList.item(i);
				if (blnode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				// found!!, <Obj> tag
				if (blnode.getNodeName().equals(XMLReader.E_OBJ)) {
					int x = Integer.parseInt(XMLReader.getAttr(blnode, "x"));
					int y = Integer.parseInt(XMLReader.getAttr(blnode, "y"));
					int w = Integer.parseInt(XMLReader.getAttr(blnode, "w"));
					int h = Integer.parseInt(XMLReader.getAttr(blnode, "h"));

					ImageIcon icon = null;

					String imgFilePath = XMLReader.getAttr(blnode, "img");

					if (imgFilePath != null)
						icon = new ImageIcon(imgFilePath);

					Block block = new Block(x, y, w, h, icon);
					add(block);
					blockList.add(block);

				}
			}

			// blockArray[0].setLocation(this.getX(), this.getY()+10);

			// 플레이어 노드
			Node charNode = XMLReader.getNode(gamePanelNode, XMLReader.E_CHARCTER);
			NodeList charnodeList = charNode.getChildNodes();

			for (int i = 0; i < charnodeList.getLength(); i++) {
				Node chnode = charnodeList.item(i);
				if (chnode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				// found!!, <Obj> tag
				if (chnode.getNodeName().equals(XMLReader.E_CHAROBJ)) {
					int x = Integer.parseInt(XMLReader.getAttr(chnode, "x"));
					int y = Integer.parseInt(XMLReader.getAttr(chnode, "y"));
					int w = Integer.parseInt(XMLReader.getAttr(chnode, "w"));
					int h = Integer.parseInt(XMLReader.getAttr(chnode, "h"));

					ImageIcon icon = null;

					String charimgFilePath = XMLReader.getAttr(chnode, "img");

					if (charimgFilePath != null)
						icon = new ImageIcon(charimgFilePath);

					Character player = new Character(x, y, w, h, icon);
					add(player);
					realPlayer1[0] = player;
				}
			}

			this.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					switch (key) {
					case KeyEvent.VK_LEFT:
						realPlayer1[0].setLocation(realPlayer1[0].getX() - 10, realPlayer1[0].getY());
						break;
					case KeyEvent.VK_RIGHT:
						realPlayer1[0].setLocation(realPlayer1[0].getX() + 10, realPlayer1[0].getY());
						break;

					case KeyEvent.VK_UP:
						realPlayer1[0].setLocation(realPlayer1[0].getX(), realPlayer1[0].getY() - 10);
						break;

					case KeyEvent.VK_DOWN:
						realPlayer1[0].setLocation(realPlayer1[0].getX(), realPlayer1[0].getY() + 10);
						break;

					case KeyEvent.VK_Z:
						shoot();

						break;

					case KeyEvent.VK_X: // 폭탄

						break;

					}
				}
			});

		}

		public void paintComponent(Graphics g) {
			g.drawImage(bgImg.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	public static void main(String[] args) {
		new MetalSlug();

	}

}