import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameScreen extends JPanel implements Runnable{
	
	// Ma trận nền
	static int [][] bg = new int [20][20];
	
	static int padding = 10;
	static int WIDTH = 400;
	static int HEIGHT = 400;
	
	 // Trạng thái của trò chơi
	static boolean isPlaying = false;
	static boolean enableTextStartGame = true;
	static boolean isGameOver = false;
	
	// Đối tượng rắn
	Snake snake;
	Thread thread;
	

    // Thông tin về cấp độ và điểm số
	static int CurrentLevel = 1;
	static int diem = 0;
	
	// Khởi tạo GameScreen
	public GameScreen() {
		snake = new Snake();
		Data.loadImage();
		Data.loadAllAnimation();
		
		bg[10][10] = 2;
		
		thread = new Thread(this);
		thread.start();
	}
	
	// Thread chạy trò chơi
	public void run() {
		long t = 0;
		long t3 = 0;
		while(true) {
			
			// Cập nhật vị trí thức ăn và hiển thị chữ "PRESS SPACE TO PLAY GAME!"
			if(System.currentTimeMillis()-t3>500) {
				enableTextStartGame =! enableTextStartGame;
				t3 = System.currentTimeMillis();
			}
			

            // Nếu đang chơi, cập nhật trạng thái của rắn và thức ăn
			if(isPlaying) {
				if(System.currentTimeMillis()-t>200) {
					Data.Food.update();
					t=System.currentTimeMillis();
				}
				
				snake.update();
			}
			
			repaint();
			try {
				thread.sleep(34);
			} catch (InterruptedException e) {}
		}
	}
	

	public void paintBg(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, WIDTH+padding*2+300, HEIGHT+padding*2);
		for(int i = 0;i< 20;i++) {
			for(int j =0 ;j<20;j++) {
				
				if(bg[i][j] == 2) {
					g.drawImage(Data.Food.getCurrentImage(), i*20-7+padding, j*20-7+padding, null);					
				}
			}
		}
	}
	
	private void veKhung(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(0, 0, WIDTH+padding*2, HEIGHT+padding*2);
		g.drawRect(1, 1, WIDTH+padding*2-2, HEIGHT+padding*2-2);
		g.drawRect(2, 2, WIDTH+padding*2-4, HEIGHT+padding*2-4);
		
		g.drawRect(0, 0, WIDTH+padding*2+300, HEIGHT+padding*2);
		g.drawRect(1, 1, WIDTH+padding*2-2+300, HEIGHT+padding*2-2);
		g.drawRect(2, 2, WIDTH+padding*2-4+300, HEIGHT+padding*2-4);
	}
	
	// Vẽ các phần tử trên giao diện
	public void paint(Graphics g) {
		paintBg(g);
		snake.veRan(g);
		veKhung(g);
		
		// Hiển thị thông báo "PRESS SPACE TO PLAY GAME!" nếu trò chơi chưa bắt đầu
		if(!isPlaying) {
			if(enableTextStartGame) {
				 g.setColor(Color.red);
	                g.setFont(g.getFont().deriveFont(18.0f));
	                g.drawString("PRESS SPACE TO PLAY GAME!", 70, 200);
			}
			
		}

        // Hiển thị thông báo "GAME OVER!" nếu trò chơi kết thúc
		if(isGameOver) {
			g.setColor(Color.red);
            g.setFont(g.getFont().deriveFont(18.0f));
            g.drawString("GAMEOVER!", 145, 160);
		}
		
		  // Hiển thị thông tin về cấp độ, điểm số và top 10 người chơi
		g.setColor(Color.white);
		g.setFont(g.getFont().deriveFont(18.0f));
		g.drawString("LEVEL: "+ CurrentLevel, 450, 30);
		g.drawString("SCORE: "+ diem, 450, 60);
		g.drawString("TOP 10: ", 450, 90);
		
		List<User> top10Players = FrameScreen.getTop10Players();
        for (int i = 0; i < top10Players.size(); i++) {
            g.drawString((i+1)+"."+ top10Players.get(i).toString(), 450, i*30 + 120);
        }
	}
	
}