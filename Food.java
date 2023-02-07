import java.awt.*;
import java.util.Random;

public class Food {
    private int x;
    private int y;
    private int size;

    public Food() {
        size = Snake.SCALE;
        x = new Random().nextInt(Snake.WIDTH / size - 1) * size;
        y = new Random().nextInt(Snake.HEIGHT / size - 1) * size;
    }

    public void drawSnake(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
