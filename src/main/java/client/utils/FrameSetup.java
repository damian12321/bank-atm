package client.utils;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class FrameSetup {
    private static final Color COLOR = new Color(227, 227, 227);
    public static void setupFrame(JFrame jFrame)
    {

        jFrame.setTitle("Damian's Bank");
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setSize(new Dimension(800, 700));
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCordX = (objDimension.width - jFrame.getWidth()) / 2;
        int iCordY = (objDimension.height - jFrame.getHeight()) / 2;
        jFrame.setLocation(iCordX, iCordY);
        jFrame.getContentPane().setBackground(COLOR);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }
}
