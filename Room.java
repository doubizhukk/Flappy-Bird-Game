package FlappyBirdApp;

import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Room {

    public ArrayList<Rectangle> tubes;
    private int time;
    private int currentTime = 0;
    private int spd = 4;
    private Random random;
    private final int SPACE_TUBES = 94;

    private final int WIDTH_TUNES = 32;
    private BufferedImage pipeNorth;
    private BufferedImage pipeSouth;

    public Room(int time){
        tubes = new ArrayList<>();
        this.time = time;
        random = new Random();
        try{
            pipeNorth = ImageIO.read(getClass().getResource("pipe-north.png"));
            pipeSouth = ImageIO.read(getClass().getResource("pipe-south.png"));
        }catch(IOException e){}
    }

    public void update(){

        currentTime++;
        if(currentTime == time){
            //creat our new tube
            currentTime = 0;

            int height1 = random.nextInt(FlappyBird.HEIGHT/2);

            int y2 = height1 + SPACE_TUBES;
            int height2 = FlappyBird.HEIGHT - y2;

            tubes.add(new Rectangle(FlappyBird.WIDTH, 0, WIDTH_TUNES, height1));
            tubes.add(new Rectangle(FlappyBird.WIDTH, y2, WIDTH_TUNES, height2));

        }

        for(int i = 0; i < tubes.size(); i++){
            Rectangle rect = tubes.get(i);
            rect.x -= spd;

            if(rect.x + rect.width <= 0){
                tubes.remove(i--);
                FlappyBird.score++;
                continue;
            }

        }

    }

    public void render(Graphics g){
        g.setColor(Color.BLUE);

        for(int i = 0; i < tubes.size(); i++){
            Rectangle rect = tubes.get(i);
            //g.fillRect(rect.x, rect.y, rect.width, rect.height);
            if(rect.y == 0){
                g.drawImage(pipeSouth, rect.x, rect.y, rect.width, rect.height,  null);
            }else{
                g.drawImage(pipeNorth, rect.x, rect.y, rect.width, rect.height, null);
            }
        }

    }

}
