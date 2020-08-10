package FlappyBirdApp;

import javax.swing.*;

public class FlappyBirdTest{

    public static void main(String[] args){

        JFrame frame = new JFrame("Flappy Bird");
        FlappyBird flappy = new FlappyBird();
        frame.add(flappy);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        flappy.start();
    }


}
