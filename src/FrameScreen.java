import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class FrameScreen extends JFrame{
	
	GameScreen game;
	
	   // Danh sách người chơi
	public static ArrayList<User> users;
	
	public FrameScreen() {
		
		users = new ArrayList<User>();		
		readData();// Đọc dữ liệu từ tệp
		
		game = new GameScreen();
		add(game);
		this.addKeyListener(new handler());// Thêm trình xử lý sự kiện phím
		
		 // Xử lý sự kiện đóng cửa sổ
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				upDateData();// Cập nhật dữ liệu trước khi đóng cửa sổ
			}
			
			
			
		});
	}
	public static void main(String[] args) {
		FrameScreen f = new FrameScreen();
		f.setVisible(true);
		f.setSize(750,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Snake game");
	}
	 // Lớp xử lý sự kiện phím
	private class handler implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {

            // Bắt đầu trò chơi khi nhấn phím SPACE
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				GameScreen.isPlaying =! GameScreen.isPlaying;
				if(GameScreen.isGameOver) GameScreen.isGameOver = false;
				game.snake.resetGame();
			}
			
			  // Điều khiển hướng của rắn bằng các phím mũi tên
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				game.snake.setVector(Snake.GO_UP);
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				game.snake.setVector(Snake.GO_DOWN);
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				game.snake.setVector(Snake.GO_LEFT);
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				game.snake.setVector(Snake.GO_RIGHT);
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {}
		
	}
	
	  // Cập nhật dữ liệu người chơi vào tệp
	public static void upDateData() {
		BufferedWriter bw = null;
		try {
			FileWriter fw = new FileWriter("data/data.txt");
			 bw = new BufferedWriter(fw);
			 
			// Ghi dữ liệu của từng người chơi vào tệp
			 for(User u: users) {
				 bw.write(u.getName()+ " " + u.getDiem());
				 bw.newLine();
			 }
			
		}catch (IOException ex) {}
		finally {
			try {
				bw.close();
			} catch (IOException e) {}
		}
	}
	
	 // Đọc dữ liệu từ tệp
	public static void readData() {
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader("data/data.txt");
			br = new BufferedReader(fr);
			
			String line = null;
			while((line = br.readLine())!= null) {
				String[] str = line.split(" ");
				users.add(new User(str[0], str[1]));
			}
			
		} catch (IOException e) {}
		finally {
			try {
				br.close();
			} catch (IOException e) {}
		}
	}

    // Lấy ra top 5 người chơi có điểm số cao nhất
	public static List<User> getTop5Players() {
        Collections.sort(users, (u1, u2) -> Integer.compare(Integer.parseInt(u2.getDiem()), Integer.parseInt(u1.getDiem())));
        
        if (users.size() > 10) {
            return users.subList(0, 10);
        } else {
            return users;
        }
    }
}
