package spaceshoot;




import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
   class Shoot{
        private int x;
        private int y;

        public Shoot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
        
    }

public class Game extends JPanel implements KeyListener,ActionListener{

    Timer timer=new Timer(5, this);
 
    
    private BufferedImage image;
    private int current_time;
    private int gun_count;
    
    private ArrayList<Shoot> shoots=new ArrayList<Shoot>();
    private int shootY=1;
    private int topX=0;
    private int topdirX=2;
    private int spaceshipx=0;
    private int dirspaceX=20;
    
    public boolean control(){
        
        for (Shoot ates : shoots) {
            if (new Rectangle(ates.getX(),ates.getY(),10,20).intersects(new Rectangle(topX,0,20,20))) {
                return true;
            }
            
        }
        return false;
        
    }
    public Game()  {
        try {
            image=ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        setBackground(Color.black);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        current_time+=5;
        
        g.setColor(Color.white);
        g.fillOval(topX, 0, 20, 20);
        
        g.drawImage(image, spaceshipx, 490,image.getWidth()/10,image.getHeight()/10, this);
        
        for (Shoot ates:shoots) {
            if (ates.getY()<0) {
                shoots.remove(ates);
            } 
        }
        g.setColor(Color.BLUE);
        for (Shoot ates : shoots) {
            g.fillOval(ates.getX(), ates.getY(), 10, 20);
        }
        
        if (control()) {
            timer.stop();
            String message="Game Over \n"
                                      +"Playing Time:\n"+current_time/1000.0
                                      +"Shoot Count::"+gun_count;
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
        
    }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }

   
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c=e.getKeyCode();
        if (c ==KeyEvent.VK_LEFT) {
            if (spaceshipx<=0) {
                spaceshipx=0;
            }
            else{
                spaceshipx-=dirspaceX;
            }
        }
        if (c==KeyEvent.VK_RIGHT) {
            if (spaceshipx>=750) {
                spaceshipx=750;
            }
            else{
                spaceshipx+=dirspaceX;
            }
        }
        if (c==KeyEvent.VK_CONTROL) {
            shoots.add(new Shoot(spaceshipx+15, 490));
            
            gun_count++;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        for (Shoot ates:shoots) {
            ates.setY(ates.getY()-shootY);
        }
        
        topX+=topdirX;
        if (topX>=770) {
            topdirX=-topdirX;
        }
        if (topX<=0) {
            topdirX=-topdirX;
        }

       repaint();
    }
    
}
