import java.awt.*;

public class Body {
    private int x;
    private int y;
    private int size;

    public Body(int x, int y) {
        this.x = x;
        this.y = y;
        size = Snake.SCALE;
    }

    public void drawSnake(Graphics g) {
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
