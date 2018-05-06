

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGameMain extends JPanel implements ActionListener {
    public static JFrame menu;
    public static JFrame jFrame;
    public static JButton confim;
    public static JButton exit;
    public static JLabel info;
    public static JLabel info1;
    public static JLabel info2;
    public static SnakeGameMain.ActionHandlerMain actionHandlerMain = new SnakeGameMain.ActionHandlerMain();
    public static final int SCALE = 32;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static int speed = 20;


    private Snake s = new Snake(5,6,5,5);
    Apple apple = new Apple(Math.abs((int) (Math.random()*SnakeGameMain.WIDTH-1)),Math.abs((int) (Math.random()*SnakeGameMain.HEIGHT-1)));
    private  Timer timer = new Timer(1000/speed,this);


    public SnakeGameMain(){
        timer.start();
        addKeyListener(new KeyBoard());
        setFocusable(true);
    }

    public void paint(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH*SCALE,HEIGHT*SCALE);

        for (int x=0; x< WIDTH*SCALE; x+=SCALE){
            g.setColor(Color.black);
            g.drawLine(x,0,x,HEIGHT*SCALE);
        }

        for (int y=0; y< HEIGHT*SCALE; y+=SCALE){
            g.setColor(Color.black);
            g.drawLine(0,y,WIDTH*SCALE,y);
        }
        g.setColor(Color.red);
        g.fillOval(apple.posX*SCALE+4,apple.posY*SCALE+4, SCALE-8,SCALE-8);
        for(int l=1;l<s.length;l++){
            g.setColor(Color.green);
            g.fillRect(s.sX[l]*SCALE+3,s.sY[l]*SCALE+3,SCALE-6,SCALE-6);
            g.setColor(Color.white);
            g.fillRect(s.sX[0]*SCALE+3,s.sY[0]*SCALE+3,SCALE-6,SCALE-6);
        }


    }


    public static void main(String[] args) {

        menu = new JFrame("Snake");
        menu.setLayout(new GridLayout(5, 1));
        menu.setDefaultCloseOperation(3);
        menu.setResizable(false);
        menu.setSize(500, 400);
        menu.setLocationRelativeTo((Component)null);
        exit = new JButton("выйти");
        info = new JLabel("  Цель - не умиреть");
        info1 = new JLabel(" Правила игры:");
        info2 = new JLabel(" При съедании яблок растет длина , кажое  яблоко даёт +1 к длине ");
        confim = new JButton("Начать");
        menu.add(exit);
        menu.add(info);
        menu.add(info1);
        menu.add(info2);
        menu.add(confim);
        menu.setVisible(true);
        confim.addActionListener(actionHandlerMain);
        exit.addActionListener(actionHandlerMain);

        jFrame = new JFrame("Snake");
        jFrame.setSize(WIDTH*SCALE+16,HEIGHT*SCALE+7);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(false);
        jFrame.add(new SnakeGameMain());
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);

    }
    public static class ActionHandlerMain implements ActionListener {
        public ActionHandlerMain() {
        }

        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == SnakeGameMain.confim) {
                    menu.setVisible(false);
                    jFrame.setVisible((true));



                }

                if (e.getSource() == SnakeGameMain.exit) {
                    System.exit(0);
                }
            } catch (Exception var3) {
                System.out.println("Crash");
            }

        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        s.move();
        if ((s.sX[0] == apple.posX) && (s.sY[0] == apple.posY)) {
            apple.setRandomPosition();
            s.length++;
        }
        for (int l = 1; l < s.length; l++) {
            if ((s.sX[l] == apple.posX) && (s.sY[l] == apple.posY)) {
                apple.setRandomPosition();
            }
            if ((s.sX[0] == s.sX[l]) && (s.sY[0] == s.sY[l])) {
                timer.stop();
                JOptionPane.showMessageDialog(null,"You are lose!");
                jFrame.setVisible(false);
                s.length=2;
                s.direction=0;
                apple.setRandomPosition();
                jFrame.setVisible(true);
                timer.start();
            }
            repaint();
        }
    }
    public class KeyBoard extends KeyAdapter{
        public void keyPressed (KeyEvent event){
            int key = event.getKeyCode();
            if((key==KeyEvent.VK_UP) &&(s.direction !=2))s.direction=0;
            if((key==KeyEvent.VK_DOWN) &&(s.direction !=0))s.direction=2;
            if((key==KeyEvent.VK_LEFT) &&(s.direction !=1))s.direction=3;
            if((key==KeyEvent.VK_RIGHT) &&(s.direction !=3))s.direction=1;



        }

    }
}
