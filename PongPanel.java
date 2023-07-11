import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class PongPanel extends JPanel implements Runnable {

    static final int Game_Width = 1000;
    static final int Game_Height = (int) (Game_Width * (0.555));

    static final int Paddle_Height = 100;

    static final int Paddle_Width = 25;

    static final int Ball_Width = 20;

    Paddle paddle1;
    Paddle paddle2;

    Ball ball;
    Image image;
    Graphics graphics;
    Dimension screen_size = new Dimension(Game_Width, Game_Height);

    Thread gameThread;

    Score Score=new Score(Game_Width,Game_Height);


    PongPanel() {
        newBall();
        newPaddle();
        gameThread = new Thread(this);
        this.setFocusable(true);
        this.addKeyListener(new AL());

        gameThread.start();
        this.setPreferredSize(screen_size);

    }


    public void newBall()
    {

        Random random=new Random();
        ball = new Ball(Game_Width/2-Ball_Width/2, random.nextInt(Game_Height-Ball_Width), Ball_Width, Ball_Width);
    }

    public void newPaddle() {
        paddle1 = new Paddle(0, Game_Height / 2 - Paddle_Height / 2, Paddle_Width, Paddle_Height, 1);
        paddle2 = new Paddle(Game_Width - Paddle_Width, Game_Height / 2 - Paddle_Height / 2, Paddle_Width, Paddle_Height, 2);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);

    }


    public void move() {
        ball.move();
        paddle1.move();
        paddle2.move();
    }

   public void collision()
   {
       if(ball.y<0)
       {
        ball.setYDirection(-ball.yVelocity);
       }
       if(ball.y>Game_Height-Ball_Width)
       {
           ball.setYDirection(-ball.yVelocity);
       }

       if(ball.intersects(paddle1))
       {
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(-ball.yVelocity);
       }
       if(ball.intersects(paddle2))
       {
           ball.setXDirection(-ball.xVelocity);
           ball.setYDirection(-ball.yVelocity);
       }
       if(ball.x<=0)
       {
        newBall();
        newPaddle();
           Score.player2++;
       }
       if(ball.x>=Game_Width-Ball_Width)
       {
           newBall();
           newPaddle();
           Score.player1++;
       }
       if(paddle1.y<0)
       {
        paddle1.y=0;
       }
       if(paddle1.y>Game_Height-Paddle_Height)
       {
           paddle1.y=Game_Height-Paddle_Height;
       }
       if(paddle2.y<0)
       {
           paddle2.y=0;
       }
       if(paddle2.y> Game_Height-Paddle_Height)
       {
           paddle2.y=Game_Height-Paddle_Height;
       }
   }

    private void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        Score.draw(g);

    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            if (delta >= 1) {
                move();
                collision();
                repaint();
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter

    {
        public void KeyPressed (KeyEvent e)
        {
            paddle1.KeyPressed(e);
            paddle2.KeyPressed(e);
        }

        public void KeyReleased (KeyEvent e)
        {
            paddle1.KeyReleased(e);
            paddle2.KeyReleased(e);
        }

    }

}
