package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import vistas.*;

public class PuntoGrano {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // con esto se adapta el diseño al sistema operativo
        VistaLogin vistaLogin = new VistaLogin();
        vistaLogin.setVisible(true);
    }
}
