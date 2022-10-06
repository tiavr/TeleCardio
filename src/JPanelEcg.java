/* JPanelEcg.java */


import java.awt.Color;
import java.awt.Dimension;

/**
 * JPanel pour l'affichage d'un ECG.
 * CETTE CLASSE NE DOIT PAS ÊTRE MODIFIÉE.
 * @author Francis JAMBON - Polytech Grenoble
 * @version 2.1
 */
public class JPanelEcg extends javax.swing.JPanel {
    
    public static final int LARGEUR = 200; // largeur preferee
    public static final int HAUTEUR = 100; // hauteur preferee

    private java.util.List<Double> ecg; // enregistrement de l'ECG
    private double ymin_d; // valeur de ymin par defaut 
    private double ymax_d; // valeur de ymax par defaut
    
    /**
     * Constructeur.
     **/
    public JPanelEcg() {
        super();
        this.ecg = null;
        this.ymin_d = 0;
        this.ymax_d = 0;
    }
    
    /**
     * Mise a jour de l'ECG et affichage de la courbe.
     * Si l'ECG est null, seul le cadre est affiche.
     * @param ecg Liste de Double contenant l'ECG.
     */
    public void setEcg(java.util.List<Double> ecg) {
        this.ecg = ecg;
        this.ymin_d = Double.POSITIVE_INFINITY;
        this.ymax_d = Double.NEGATIVE_INFINITY;
        this.repaint();
    }
    
     /**
     * Mise a jour de l'ECG et affichage de la courbe.
     * Si l'ECG est null, seul le cadre est affiche.
     * @param ecg la liste de "Double" contenant l'ECG.
     * @param ymin_d la valeur de ymin par defaut.
     * @param ymax_d la valeur de ymax par defaut.
     */
    public void setEcg(java.util.List<Double> ecg, double ymin_d, double ymax_d) {
        this.ecg = ecg;
        this.ymin_d = ymin_d;
        this.ymax_d = ymax_d;
        this.repaint();
    }

    /**
     * Methode effectuant le dessin de la courbe par
     * surchage de la methode paintComponent(...) de JPanel.
     **/
    @Override
    public void paintComponent(java.awt.Graphics g) {

        super.paintComponent(g);
        
        // dessin du cadre
        g.setColor(Color.BLACK);
        g.drawRect(0,0,this.getWidth()-1, this.getHeight()-1);
        
        if (this.ecg != null) {
            
            // parametres de la courbe
            int xmin=0;
            int xmax=this.ecg.size()-1;
            
            // detection automatique de ymin_d et ymax_d
            double ymin = this.ymin_d;
            double ymax = this.ymax_d;
            for (int x=0 ; x<=xmax ; x++) {
                double y = this.ecg.get(x);
                if (ymin > y) ymin = y;
                if (ymax < y) ymax = y;
            }
            
            // parametres de l'ecran
            int largeur = this.getWidth();
            int hauteur = this.getHeight();
 
            // dessin de l'axe des x
            g.setColor(Color.LIGHT_GRAY);
            if (ymin<ymax) {
                int y0 = y2graph(0, ymin, ymax, hauteur);
                g.drawLine(1, y0 , this.getWidth()-2, y0);
            }
             
            // dessin de la courbe par la methode de la polyline
            g.setColor(Color.BLUE);
            if (xmin<xmax && ymin<ymax) {
                int xg[] = new int[xmax+1];
                int yg[] = new int[xmax+1]; 
                for (int x=0 ; x<=xmax ; x++) {
                    double y = this.ecg.get(x);
                    xg[x] = x2graph(x, xmin, xmax, largeur);
                    yg[x] = y2graph(y, ymin, ymax, hauteur);
                }
                g.drawPolyline(xg, yg, xmax+1);
            }
        }
    }
    
    /**
     * Indique la taille préférée du JPanel.
     * @return la taille préférée
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(LARGEUR, HAUTEUR);
    }
    
    /**
     * Methode statique permettant d'effectuer un changement de repere entre
     * le repere mathematique et le repere de la zone d'affichage graphique
     * pour l'axe des abscisses.
     * @param x la valeur mathematique
     * @param xmin la valeur mathematique minimale a afficher
     * @param xmax la valeur mathematique maximale a afficher
     * @param largeur la largeur de la zone d'affichage
     * @return la position dans le repere de la zone d'affichage
     */
    private static int x2graph(double x, double xmin, double xmax, int largeur) {
        return (int) Math.round( (largeur-1) * (x-xmin) / (xmax-xmin) );
    }
    
    /**
     * Methode statique permettant d'effectuer un changement de repere entre
     * le repere mathematique et le repere de la zone d'affichage graphique
     * pour l'axe des ordonnees.
     * @param y la valeur mathematique
     * @param ymin_d la valeur mathematique minimale a afficher
     * @param ymax_d la valeur mathematique maximale a afficher
     * @param hauteur la hauteur de la zone d'affichage
     * @return la position dans le repere de la zone d'affichage
     **/
    private static int y2graph(double y, double ymin, double ymax, int hauteur) {
        return (int) Math.round( (hauteur-1) * (1 - ((y-ymin)/(ymax-ymin)) ) );
    }
    
}