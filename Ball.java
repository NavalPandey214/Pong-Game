import java.awt.*;
import java.util.Random;
public class Ball extends Rectangle {

    int xVelocity;
    int yVelocity;
    int initialSpeed=5;
    Random random;

    Ball(int x,int y,int width,int height)
    {
        super(x,y,width,height);
        random=new Random();
        int direction=random.nextInt(2);
        if(direction==0)
        {
             direction=-1;
        }
      setXDirection(direction);
        direction=random.nextInt(2);
        if(direction==0)
        {
            direction=-1;
        }
        setYDirection(direction);
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.white);
        g.fillOval(x,y,width,height);
        g.setColor(Color.yellow);
        g.drawLine(1000/2,0,1000/2,555);
    }

    public void move()
    {
      y+=yVelocity;
      x+=xVelocity;
    }

    public void setYDirection(int direction)
    {
    yVelocity=direction;
    }
    public void setXDirection(int direction)
    {
        xVelocity=direction;
    }

}
