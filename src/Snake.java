import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
import javax.swing.JOptionPane;

public class Snake {
    int doDai = 3;
    int x[];
    int y[];

    public static final int GO_UP = 1;
    public static final int GO_DOWN = -1;
    public static final int GO_LEFT = 2;
    public static final int GO_RIGHT = -2;

    int vector = Snake.GO_DOWN;

    long t1 = 0;
    long t2 = 0;

    int speed = 200;
    int maxLen = 10;
    boolean udAfterChangeVt = true;
    int speedMultiplier = 1; // Biến theo dõi tốc độ

    public Snake() {
        x = new int[100];
        y = new int[100];

        x[0] = 5;
        y[0] = 4;

        x[1] = 5;
        y[1] = 3;

        x[2] = 5;
        y[2] = 2;
    }

    public void resetGame() {
        x = new int[100];
        y = new int[100];

        x[0] = 5;
        y[0] = 4;

        x[1] = 5;
        y[1] = 3;

        x[2] = 5;
        y[2] = 2;

        doDai = 3;
        vector = Snake.GO_DOWN;
        speedMultiplier = 1; // Reset tốc độ
    }

    public void setVector(int v) {
        if (vector == v) {
            speedMultiplier++;
        } else if (vector != -v && udAfterChangeVt) {
            vector = v;
            udAfterChangeVt = false;
            speedMultiplier = 1; // Reset tốc độ khi đổi hướng
        }
    }

    public boolean toaCoNamTrongThanRan(int x1, int y1) {
        for (int i = 0; i < doDai; i++) {
            if (this.x[i] == x1 && this.y[i] == y1) return true;
        }
        return false;
    }

    public Point layToaDoMoi() {
        Random r = new Random();
        int x, y;
        do {
            x = r.nextInt(19);
            y = r.nextInt(19);
        } while (toaCoNamTrongThanRan(x, y));
        return new Point(x, y);
    }

    public static int getCurrentSpeed() {
        return (int) (200 * 0.8);
    }

    public void update() {
        if (doDai == maxLen) {
            GameScreen.isPlaying = false;
            resetGame();
            GameScreen.CurrentLevel++;
            maxLen += 5;
            speed = getCurrentSpeed();
        }

        for (int i = 1; i < doDai; i++) {
            if (x[0] == x[i] && y[0] == y[i]) {
                String name = JOptionPane.showInputDialog("Mời bạn nhập tên: ");
                FrameScreen.users.add(new User(name, String.valueOf(GameScreen.diem)));

                GameScreen.isPlaying = false;
                GameScreen.isGameOver = true;
                GameScreen.diem = 0;
                GameScreen.CurrentLevel = 1;
            }
        }

        if (System.nanoTime() - t2 > 200_000_000) {
            udAfterChangeVt = true;

            Data.HeadGoUp.update();
            Data.HeadGoDown.update();
            Data.HeadGoRight.update();
            Data.HeadGoLeft.update();

            t2 = System.nanoTime();
        }

        if (System.nanoTime() - t1 > speed * 1_000_000 / speedMultiplier) {
            if (GameScreen.bg[x[0]][y[0]] == 2) {
                doDai++;
                GameScreen.bg[x[0]][y[0]] = 0;
                GameScreen.bg[layToaDoMoi().x][layToaDoMoi().y] = 2;

                GameScreen.diem += 100;
            }

            for (int i = doDai - 1; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (vector) {
                case GO_UP:
                    y[0]--;
                    break;
                case GO_DOWN:
                    y[0]++;
                    break;
                case GO_LEFT:
                    x[0]--;
                    break;
                case GO_RIGHT:
                    x[0]++;
                    break;
            }

            if (x[0] < 0) x[0] = 19;
            if (x[0] > 19) x[0] = 0;
            if (y[0] < 0) y[0] = 19;
            if (y[0] > 19) y[0] = 0;

            t1 = System.nanoTime();
        }
    }

    public void veRan(Graphics g) {
        g.setColor(Color.green);
        for (int i = 0; i < doDai; i++) {
            g.drawImage(Data.imageBody, x[i] * 20 + GameScreen.padding, y[i] * 20 + GameScreen.padding, null);
        }

        switch (vector) {
            case GO_UP:
                g.drawImage(Data.HeadGoUp.getCurrentImage(), x[0] * 20 - 6 + GameScreen.padding, y[0] * 20 - 6 + GameScreen.padding, null);
                break;
            case GO_DOWN:
                g.drawImage(Data.HeadGoDown.getCurrentImage(), x[0] * 20 - 6 + GameScreen.padding, y[0] * 20 - 6 + GameScreen.padding, null);
                break;
            case GO_LEFT:
                g.drawImage(Data.HeadGoLeft.getCurrentImage(), x[0] * 20 - 6 + GameScreen.padding, y[0] * 20 - 6 + GameScreen.padding, null);
                break;
            case GO_RIGHT:
                g.drawImage(Data.HeadGoRight.getCurrentImage(), x[0] * 20 - 6 + GameScreen.padding, y[0] * 20 - 6 + GameScreen.padding, null);
                break;
        }
    }
}
