package FlappyBirdApp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

public class Bird extends Rectangle {

    private static final long serialVersionUID = 1L;
    private int spd = 4;
    public boolean isPressed = false;
    private BufferedImage birdImage;
    private ArrayList<Rectangle> tubes;

    private float gravity = 0.3f;

    public Bird(int x, int y, ArrayList<Rectangle> tubes){
        setBounds(x, y, 32, 32);
        this.tubes = tubes;
        try{
            birdImage = ImageIO.read(getClass().getResource("bird.png"));
        }catch(IOException e){}
    }

    public void update(){
        if(isPressed){
            y -= spd;
        }else{
            y += spd;
        }
        for(int i = 0; i < tubes.size(); i++){
            if(this.intersects(tubes.get(i))){
                //restart the game
                //System.exit(1);

                FlappyBird.room = new Room(80);
                tubes = FlappyBird.room.tubes;
                y = FlappyBird.HEIGHT/2;
                FlappyBird.score = 0;
                break;
            }
        }
        if(y >= FlappyBird.HEIGHT){
            //restart the game
            FlappyBird.room = new Room(80);
            tubes = FlappyBird.room.tubes;
            y = FlappyBird.HEIGHT/2;
            FlappyBird.score = 0;
        }

    }

    public void render(Graphics g){
        g.drawImage(birdImage, x, y, width, height, null);
    }

}
