package puntograno.vistas;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

class PanelConFondo extends JDesktopPane {

    private Image fondo;

    public PanelConFondo() {
        fondo = new ImageIcon(getClass().getResource("/puntograno/imagenes/fondo-principal.jpg")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
