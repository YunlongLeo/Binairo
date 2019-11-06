import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	private Board board;
	private int size;
	private Draw draw;

	

	public GUI(Board board) {
		this.board = board;
		size = board.getSize();
		draw = new Draw();
		draw.setPreferredSize(new Dimension(50*size,50*size));
		Container cp = getContentPane();
		cp.add(draw);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setBackground(Color.GRAY);
		setVisible(true);
		
	}
	
	private class Draw extends JPanel{
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.WHITE);
			g2.setStroke(new BasicStroke(2));
			for (int i = 0; i < size; i++) {
				g2.drawLine(0, i * 50, size * 50, i * 50);
				g2.drawLine(i * 50, 0, i * 50, size * 50);
			}

			g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					char[] c = new char[1];
					if (board.getBoard()[i][j].getValue() == '0') {
						c[0] = board.getBoard()[i][j].getValue();
						g2.drawChars(c, 0, c.length, j * 50 + 18, i * 50 + 35);
					} else if (board.getBoard()[i][j].getValue() == '1') {
						c[0] = board.getBoard()[i][j].getValue();
						g2.drawChars(c, 0, c.length, j * 50 + 18, i * 50 + 35);
					}
				}
			}
		}
	}
	
	public void update(Board board) {
		this.board = board;
		size = board.getSize();
	}

}