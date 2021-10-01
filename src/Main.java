/*
* created by: 
*   Matthew Roberts
*   Damian Jurd
*   Dominic Verity
*
* Modified for purpose by: Joel Perry
* last edited: 1/10/21
* 
* based on provided COMP2000 Milestone 2 code with edits to allow color modification
*/

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.TextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.time.Duration;
import java.time.Instant;
import java.awt.Color;

class Main extends JFrame {
    
    class App extends JPanel implements MouseListener {
        TextField t1;
        TextField t2;
        TextField t3;

        TextField t4;
        TextField t5;
        
        Window stage;
        SetUtil stngs;  

        public App() {
            stngs = SetUtil.getSettings();
            setPreferredSize(new Dimension(stngs.WINDOW_SIZE.x, stngs.WINDOW_SIZE.y));
            this.addMouseListener(this);
            stage = new Window();  
            TextBox();
        }

        private void TextBox(){
            t1 = new TextField("r");
            t1.setBounds(810,370,37,30);
            add(t1);
            t2 = new TextField("g");
            t2.setBounds(850,370,37,30);
            add(t2);
            t3 = new TextField("b");
            t3.setBounds(890,370,37,30);
            add(t3);

            t4 = new TextField("Grd x");
            t4.setBounds(810,stngs.WINDOW_SIZE.y-40,37,30);
            add(t4);
            t5 = new TextField("Grd y");
            t5.setBounds(850,stngs.WINDOW_SIZE.y-40,37,30);
            add(t5);

            setLayout(null); 
        }

        @Override
        public void paint(Graphics g) {
            int ColR = StrgToNum(t1.getText());
            if(ColR >255){ColR = 255;} else if(ColR < 0){ColR = 0;}
            int ColG = StrgToNum(t2.getText());
            if(ColG >255){ColG = 255;} else if(ColG < 0){ColG = 0;}
            int ColB = StrgToNum(t3.getText());
            if(ColB >255){ColB = 255;} else if(ColB < 0){ColB = 0;}
            
            Point gridNum = new Point(10, 10);
            gridNum.x = StrgToNum(t4.getText());
            if(gridNum.x < 0){gridNum.x = 0;}
            gridNum.y = StrgToNum(t5.getText());
            if(gridNum.y < 0){gridNum.y = 0;}

            Color custCol = new Color(ColR, ColG, ColB);
            stage.paint(g, getMousePosition(), custCol, gridNum);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            stage.mouseClicked(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    public static void main(String[] args) throws Exception {
        Main window = new Main();
        window.run();
    }

    private Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        App canvas = new App();

        this.setContentPane(canvas);
        this.pack();
        this.setVisible(true);
    }

    public void run() {
        while (true) {
            Instant startTime = Instant.now();
            this.repaint();
            Instant endTime = Instant.now();
            long howLong = Duration.between(startTime, endTime).toMillis();
            try{
                Thread.sleep(20l - howLong);
            } catch (InterruptedException e){
                System.out.println("thread was interrupted, but who cares?");
            } catch (IllegalArgumentException e){
                System.out.println("application can't keep up with framerate");
            }
        }
    }

    private int StrgToNum(String s){
        try {
            return Integer.parseInt(s);           
        } catch(NumberFormatException e){
            return 0;
        }
    }
}