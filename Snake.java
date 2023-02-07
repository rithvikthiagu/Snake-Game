import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

//function defining the basis of the game including the game screen,
// messages, snake, food, and key interactions
public class Snake extends JPanel implements KeyListener, ActionListener {
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 500;
    public static final int SPEED = 5;
    public static final int HEIGHT = 500;
    public static final int SCALE = 50;
    public static int score = 0;
    public static int highScore = 0;

    private Timer timer;
    private int delay = 1000 / SPEED;
    private boolean gameOver = false;
    private boolean running = false;


    private Food food;
    private Random rand;
    private int xDir = 0;
    private int yDir = 0;
    private ArrayList<BodyPart> snake;
    
    public Snake() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        snake = new ArrayList<BodyPart>();
        rand = new Random();
        food = new Food();
        timer = new Timer(delay, this);
        timer.start();
        reset();
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (running) {
            g.setColor(Color.blue);
            for (int i = 0; i < snake.size(); i++) {
                snake.get(i).drawSnake(g);
            }
            food.drawSnake(g);
            g.drawString("Score: " + score, 10, 20);
            g.setFont(new Font("Arial", Font.PLAIN, 200));
            g.setColor(Color.white);

        } else {
            g.setColor(Color.white);
            g.drawString("Press Space to Play", 200, 250);
            g.drawString("High Score: " + highScore, 220, 270);

        }
        g.setFont(new Font("Arial", Font.PLAIN, 100));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over!", 180, 250);
            g.drawString("Press Space to Play Again", 130, 270);
            g.setFont(new Font("Arial", Font.PLAIN, 50));

        }
    }

    public void move() {
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).setPosition(snake.get(i - 1).getX(), snake.get(i - 1).getY());
        }
        snake.get(0).setPosition(snake.get(0).getX() + xDir * SCALE, snake.get(0).getY() + yDir * SCALE);
    }

    public void foodCheck() {
        if (snake.get(0).getX() == food.getX() && snake.get(0).getY() == food.getY()) {
            score++;
            snake.add(new BodyPart(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY()));
            food.setPosition(rand.nextInt(WIDTH / SCALE - 1) * SCALE, rand.nextInt(HEIGHT / SCALE - 1) * SCALE);
        }
    }

    public void collisionCheck() {
        if (snake.get(0).getX() < 0 || snake.get(0).getX() >= WIDTH || snake.get(0).getY() < 0
                || snake.get(0).getY() >= HEIGHT) {
            gameOver = true;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY()) {
                gameOver = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !gameOver) {
            move();
            collisionCheck();
            foodCheck();

        }
        if (gameOver) {
            updateHighScore();
            reset();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            if (gameOver) {
                reset();
            }
            running = !running;
        }
        if (key == KeyEvent.VK_LEFT && xDir != 1) {
            xDir = -1;
            yDir = 0;
        }
        if (key == KeyEvent.VK_DOWN && yDir != -1) {
            yDir = 1;
            xDir = 0;
        }
        if (key == KeyEvent.VK_RIGHT && xDir != -1) {
            xDir = 1;
            yDir = 0;
        }
        if (key == KeyEvent.VK_UP && yDir != 1) {
            yDir = -1;
            xDir = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void updateHighScore() {
        int currentScore = score;
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

    public void reset() {
        running = false;
        gameOver = false;
        snake.clear();
        snake.add(new BodyPart(WIDTH / 2, HEIGHT / 2));
        food.setPosition(rand.nextInt(WIDTH / SCALE - 1) * SCALE, rand.nextInt(HEIGHT / SCALE - 1) * SCALE);
        xDir = 0;
        score = 0;
        yDir = 0;
    }
}
