 
package EjecutarAplicacion;


import javax. swing.*;
import java.awt.*;
import javax.swing.text.*;
import java.text.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
enum TipoDeCoche {
MINI, UTILITARIO, FAMILIAR, DEPORTIVO;
// devuelve el enumerado a partir de su nombre
public static TipoDeCoche getTipo(String cadena){
return Enum.valueOf(TipoDeCoche.class, cadena.toUpperCase());
} }//TipoDeCoche
enum TipoDeSeguro { A_TERCEROS, A_TODO_RIESGO };
class Coche {
String modelo;
String color;
boolean esMetalizado;
String matricula;
TipoDeCoche tipo;
int añoDeFabricacion;
TipoDeSeguro seguro;

public Coche(String modelo, String color, boolean esMetalizado, String matricula,
        TipoDeCoche tipo, int año, TipoDeSeguro  seguro){
    this.modelo= modelo;
this.color= color;
this.esMetalizado= esMetalizado;
this.matricula = matricula;
this.tipo = tipo;
this.añoDeFabricacion = año;
this.seguro = seguro;
}

public String toString(){ 
Formatter formato = new Formatter();

formato.format("- %10s - %8s - %5d - %6s - %7s - metalizada: %5b - %8s",
modelo, color, añoDeFabricacion, tipo, matricula, esMetalizado, seguro);
return formato.toString();

}
}//Coche

class Aplicacion{
private java.util.List<Coche> lista;

public Aplicacion(){
lista = new ArrayList<Coche>(); 

}

public void añadirCoche(Coche coche){
lista. add(coche);

}

public java.util.List<Coche> obtenerLista() {
return lista;

}
}// Aplicacion

class InterfazGrafica extends JFrame{
private Aplicacion aplicacion;
private JButton añadir;
private JButton visualizar;
private DefaultListModel modeloLista;

OyenteVisualizar oyenteVisualizar;

public InterfazGrafica(Aplicacion aplicacion) {
this.aplicacion = aplicacion;
oyenteVisualizar = new OyenteVisualizar();
JPanel panel = new JPanel ();
panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
Border borde = BorderFactory.createEmptyBorder(10,10,10,10);
TitledBorder titulo=BorderFactory.createTitledBorder(borde,"Lista coches",TitledBorder.CENTER, TitledBorder.TOP);
 
panel .setBorder(titulo);
modeloLista = new DefaultListModel ();
JList lista = new JList(modeloLista);
panel .add(new JScrollPane(lista));

this.setLayout(new BoxLayout(this.getContentPane() , BoxLayout .PAGE_AXIS));
add(panel);
add(panelBotones());

setJMenuBar(creaMenus());
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setTitle("Gestion de coches");
setSize(300,250);
    setVisible(true);
}//constructor

private JPanel panelBotones(){
JPanel panel = new JPanel();
panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
panel .setBorder(new EmptyBorder(0,10,10,10));
añadir = new JButton("Añadir");
añadir .setMnemonic(KeyEvent .VK_A);
añadir.addActionListener(new OyenteNuevo());
visualizar = new JButton("Visualizar");
visualizar.setMnemonic(KeyEvent .VK_V);
visualizar.addActionListener(new OyenteVisualizar());
panel .add(Box.createHorizontalGlue());
panel .add(añadir);
panel .add(Box.createRigidArea(new Dimension(10,0)));
panel.add(visualizar);
return panel;

}//panelBotones

private JMenuBar creaMenus(){
JMenuBar barraMenu = new JMenuBar();
JMenu menuOperaciones = new JMenu("Operaciones");
menuOperaciones . setMnemonic(KeyEvent .VK_0);
    JMenuItem listar = new JMenuItem("Listar coches", KeyEvent.VK_1);
listar. addActionListener(oyenteVisualizar);
menuOperaciones .add(listar);
menuOperaciones .add(new JSeparator());
JMenuItem salir= new JMenuItem("Salir", KeyEvent.VK_S);
salir.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e){
System.exit(0);
}
});
menuOperaciones.add(salir);
barraMenu.add(menuOperaciones);
return barraMenu;
}//creaMenus


class OyenteVisualizar implements ActionListener{
public void actionPerformed(ActionEvent evento) { 
modeloLista.clear();
Iterator iterador = aplicacion.obtenerLista().listIterator();
while(iterador.hasNext())
modeloLista.addElement (iterador.next());

}

}//0yenteVisualizar

class OyenteNuevo implements ActionListener{
public void actionPerformed(ActionEvent evento) {
boolean error = false;
PanelDatosCoche panel= new PanelDatosCoche();
if (JOptionPane.showConfirmDialog(InterfazGrafica.this
,panel
, "Introduzca datos"
,JOptionPane.OK_CANCEL_OPTION
,JOptionPane.PLAIN_MESSAGE
) == JOptionPane.OK_OPTION) {
String modelo = panel .campoModelo.getText();
String color = panel.campoColor.getText();
Boolean esMetalizado = panel .esMetalizada.isSelected();
String matricula = panel.campoMatricula.getText();
TipoDeCoche tipo = TipoDeCoche.getTipo(panel.tipoCoche.getSelectedItem().toString());
int año= 0;
try {
año =Integer.parseInt(panel.campoAño.getText());
}catch (NumberFormatException e){
error = true;
JOptionPane.showMessageDialog(null,
"<html><u>El año con cuatro dígitos (e.g. 2003)",
"Error",
JOptionPane . ERROR_MESSAGE) ;
}
error = error || (modelo.length() ==0) ||
(color. length() ==0)|| (matricula.length() ==0);
if (error)
JOptionPane.showMessageDialog(null,
"<html>Tipo de error "+
"<p color='red'>Algún campo está vacío" +
" <em> Debe rellenarlos todos",
"Error",
JOptionPane.ERROR_MESSAGE) ;
TipoDeSeguro seguro;
if (panel.todoRiesgo.isSelected())
seguro = TipoDeSeguro.A_TODO_RIESGO;
else
seguro = TipoDeSeguro.A_TERCEROS;
if (!error) {
Coche coche = new Coche(modelo, color, esMetalizado,
matricula, tipo, año, seguro);
aplicacion.añadirCoche(coche);
}
}else{
JOptionPane.showMessageDialog(null,
"Operación no realizada",
"Aviso",
JOptionPane.WARNING_MESSAGE) ;
}
}

}//0yenteNuevo

}//interfaz grafica

class PanelDatosCoche extends JPanel {
JTextField campoModelo;
JTextField campoColor;
JCheckBox esMetalizada;
JTextField campoMatricula;
JComboBox tipoCoche;
JFormattedTextField campoAño;
JRadioButton todoRiesgo;
JRadioButton terceros;

public PanelDatosCoche() {
setLayout(new GridLayout(7,2));
JLabel modelo = new JLabel("Modelo: ", JLabel.RIGHT);
campoModelo = new JTextField();
add(modelo);
add(campoModelo);

JLabel color = new JLabel("Color: ", JLabel.RIGHT);
campoColor = new JTextField();
add(color);
add(campoColor);

JLabel matricula = new JLabel("Matricula: ", JLabel.RIGHT);
campoMatricula = new JTextField();
add(matricula);
add(campoMatricula);

ButtonGroup grupoBotones= new ButtonGroup();
todoRiesgo = new JRadioButton("A todo riesgo", true);
todoRiesgo.setMnemonic(KeyEvent .VK_R);

terceros = new JRadioButton("A terceros");

terceros. setMnemonic(KeyEvent .VK_T);

grupoBotones .add(todoRiesgo);

grupoBotones.add(terceros);

add(todoRiesgo);

add(terceros);

JLabel año = new JLabel("Año de fabricación : ", JLabel .RIGHT);
MaskFormatter formato=null;
try{
formato = new MaskFormatter("####");
        } catch (ParseException e){
//se captura la excepción y no se hace nada
        }
campoAño = new JFormattedTextField(formato);
add(año);
add(campoAño) ;

JLabel tipo = new JLabel("Tipo de coche : ", JLabel.RIGHT);
tipoCoche = new JComboBox(TipoDeCoche.values());

add(tipo);

add(tipoCoche);

JLabel pintura = new JLabel("Tipo de pintura : ", JLabel .RIGHT);
esMetalizada = new JCheckBox("Metalizada", false);

add(pintura);

add(esMetalizada);

TitledBorder titulo;
titulo = BorderFactory.createTitledBorder("Datos del coche");
setBorder(titulo);

}

public static void main(String[] args) {
JFrame ventana = new JFrame("Panel de datos");
ventana.add(new PanelDatosCoche());
ventana .pack();
ventana.setVisible(true);
}
}//PanelDatosCoche

public class EjecutarAplicacion{

public static void main(String args[]){
Aplicacion aplicacion = new Aplicacion();
InterfazGrafica ventana = new InterfazGrafica(aplicacion);

}

}//EjecutarAplicacion