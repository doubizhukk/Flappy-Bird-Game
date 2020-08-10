package FlappyBirdApp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FlappyBird extends Canvas implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 640, HEIGHT = 400;
    private boolean running = false;
    private Thread thread;
    private BufferedImage background;

    public static int score = 0;

    public static Room room;
    public Bird bird;

    public FlappyBird(){

        Dimension d = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(d);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        room = new Room(80);
        bird = new Bird(20, FlappyBird.HEIGHT/2, room.tubes);
        try{
            //get background image
            background = ImageIO.read(getClass().getResource("background.png"));

        }catch(IOException e){}

    }

    public synchronized void start(){

        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start();

    }

    public synchronized void stop(){

        if(!running) return;
        running = false;
        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        int fps = 0;
        double timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double ns = 1000000000/60;
        double delta = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            while(delta >= 1){
                update();
                render();
                fps++;
                delta--;
            }
           if(System.currentTimeMillis() - timer >= 1000){
               //System.out.println("FPS: " + fps);
               fps = 0;
               timer += 1000;
           }
        }

    }
    private void render(){

        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(background, 0, 0, WIDTH, HEIGHT,null);
        room.render(g);
        bird.render(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 19));
        g.drawString("Your Score: " + score, 10, 20);
        g.dispose();
        bs.show();

    }
    private void update(){
        //System.out.println("Working!");
        room.update();
        bird.update();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_UP){
            bird.isPressed = true;
            //System.out.println("up");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_UP){
            bird.isPressed = false;
            //System.out.println("down");
        }
    }
}
