import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUI extends JFrame implements MouseListener {

	private TicTacToe tictactoe;
	private Image imgX, imgO;
	private Draw draw;
	private int[][] mtx ;
	private int n;
	private int size;
	private byte xClick;
	private byte yClick;

	public GUI(int[][] mtx, int n) {
		this.mtx = mtx;
		this.n = n;
		this.size = n*100;
		imageLoad();
		initialize();
		switch (n) {
		case 3 :
			tictactoe = new TicTacToe3(mtx);
			break;
		case 4 :
			tictactoe = new TicTacToe4(mtx);
			break;
		default :
			tictactoe = new TicTacToeN(mtx,n);
			break;
		}
	}
	
	public void initialize() {
		this.setSize(size, size+30);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Tic Tac Toe");
		
		draw = new Draw();
		draw.addMouseListener(this);
		draw.setVisible(true);
		this.add(draw);
		this.setVisible(true);
	}
	
	public void imageLoad() {
		try {
			imgX = ImageIO.read(new File("pic/x.png"));
			imgO = ImageIO.read(new File("pic/o.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class Draw extends JLabel {

		private Color color;
		
		public Draw() {
			repaint();
			color = Color.GRAY;
		}

		protected void paintComponent(Graphics g) {

			super.paintComponent(g);
			
			g.setColor(color);
			g.fillRect(0,0, size, size);
			g.setColor(Color.BLACK);
			
			for (int i = 1; i < n; i++) {
				// Vertikal
				g.drawLine(i*(size/n), 0, i*(size/n), size);
				// Horizontal
				g.drawLine(0, i*(size/n), size, i*(size/n));
			}
			
			for (int i = 0; i < mtx.length; i++) {
				for (int j = 0; j < mtx.length; j++) {
					if(mtx[i][j] == 1) {
						g.drawImage(imgX,i*(size/n),j*(size/n),(size/n),(size/n),null);
					}
					if(mtx[i][j] == 2) {
						g.drawImage(imgO,i*(size/n),j*(size/n),(size/n),(size/n),null);
					}
					
				}
			}
			repaint();
		}
		
		void setColor(Color newColor) {
			color = newColor;
		}
	}

	public byte[] getClickPos() {
		byte[] tmp = {xClick,yClick};
		return tmp;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		xClick = (byte) (e.getX()/(size/n));
		yClick = (byte) (e.getY()/(size/n));
		
		if (mtx[xClick][yClick] == 0) {
	
			mtx[xClick][yClick] = 1;
			
			draw.setColor(Color.GRAY);
			
			int end = tictactoe.step();
			
			switch (end) {
			case 1 :
				draw.setColor(Color.BLUE);
				break;
			case -1 :
				draw.setColor(Color.RED);
				break;
			case -2 :
				draw.setColor(Color.PINK);
				break;
			}
		
			draw.repaint();
		}
	}
		
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}