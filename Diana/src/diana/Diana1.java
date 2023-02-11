package diana;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;

public class Diana1  extends JButton{
static final int NUM=5;
int centrox;
int centroy;

    public Diana1(){
this.setBackground(Color.cyan);
this.setPreferredSize(new Dimension(250,250));
this.addMouseListener(new MouseInputAdapter(){
public void mousePressed(MouseEvent e) {
int x=e.getX();
int y=e.getY();
String mensaje = "No has dado en el centro";
if(((Math.abs(x-centrox))<(centrox/NUM))&&
((Math.abs(y-centroy))<(centroy/NUM))){
mensaje = "Muy bien acertaste en el centro";
}
JOptionPane.showMessageDialog(Diana1.this,
mensaje,
"Pulsación del ratón realizada",
JOptionPane.INFORMATION_MESSAGE);
}
});
}//constructor

public void paintComponent (Graphics g){
     super.paintComponent(g);
int alto = getSize ().height;
int ancho = getSize ().width;
centrox = ancho/2;
centroy=alto/2;
for(int i= NUM; i>0;i--){
if(i%2==0) {
g.setColor(Color.green);
}else{
g.setColor(Color.blue);
}
if (i==1) {
g.setColor(Color.red);
}
int radioX = i * centrox/NUM;
int radioY = i * centroy/NUM;
g.fillOval(centrox-radioX, centroy-radioY, 2*radioX, 2*radioY);
}
g.setColor(Color.black);
g.drawLine(0, 0, ancho, alto);
g.drawLine(0, ancho, alto,0);
}//paint component
 
public static void main(String[] args) {
       JFrame ventana = new JFrame("Diana1");
       Diana1 diana= new Diana1();
       ventana.add(diana);
       JLabel etiqueta = new JLabel("Haz click en la diana",JLabel.CENTER);
       ventana.add(etiqueta, BorderLayout.NORTH);
       ventana.pack();
       ventana.setVisible(true);
    }

}

