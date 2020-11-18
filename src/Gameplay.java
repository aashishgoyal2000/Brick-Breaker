import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	private static final long serialVersionUID = 1L;
	
	private boolean play = false;
	private int score = 0;
	
	private MapGenerator map;
	private int totalBrick = 21;
	
	private Timer timer;
	private int delay = 0;
	
	private int playerX = 310;
	
	private int ballPositionX = 120;
	private int ballPositionY = 250;
	private int ballDirX = -1;
	private int ballDirY = -1;
	
	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		map = new MapGenerator(3, 7);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		// backgroiund
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// draw tiles
		map.draw((Graphics2D)g);
		
		// borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		// scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);
		
		// padle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		// ball
		g.setColor(Color.yellow);
		g.fillOval(ballPositionX, ballPositionY, 20, 20);
		
		if (totalBrick <= 0) {
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won!", 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter To Restart", 190, 350);
		}
		
		if (ballPositionY > 570) {
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Scores: " + score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter To Restart", 190, 350);
		}
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		timer.start();
		if (play == true) {
			if (new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballDirY = -ballDirY;
			}
			
			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPositionX, ballPositionY, 20, 20);
						Rectangle brickRect = rect;
						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBrick--;
							score += 5;
							
							if (ballPositionX + 19 <= brickRect.x || ballPositionX + 1 >= brickRect.x + brickRect.width) {
								ballDirX = -ballDirX;
							}
							else {
								ballDirY = - ballDirY;
							}
							
							break A;
						}
					}
				}
			}
			
			ballPositionX += ballDirX;
			ballPositionY += ballDirY;
			if (ballPositionX < 0) {
				ballDirX = -ballDirX;
			}
			if (ballPositionY < 0) {
				ballDirY = -ballDirY;
			}
			if (ballPositionX > 670) {
				ballDirX = -ballDirX;
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			}
			else {
				moveRight();
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			}
			else {
				moveLeft();
			}		
		}
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {
				play = true;
				ballPositionX = 120;
				ballPositionY = 350;
				ballDirX = -1;
				ballDirY = -1;
				playerX = 321;
				score = 0;
				totalBrick = 21;
				map = new MapGenerator(3, 7);
				repaint();
			}
		}
		
	}
	
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	public void moveRight() {
		play = true;
		playerX += 20;
	}

	@Override
 	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}

}
