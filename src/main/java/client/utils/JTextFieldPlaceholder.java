package client.utils;

import javax.swing.*;
import java.awt.*;


public class JTextFieldPlaceholder extends JTextField {

    private String ph;

    public JTextFieldPlaceholder(String ph) {
        this.ph = ph;
    }

    public JTextFieldPlaceholder() {
        this.ph = null;
    }

    @Override
    public String getText() {
        String text = super.getText();

        if (text.trim().length() == 0 && ph != null) {
            text = ph;
        }

        return text;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (super.getText().length() > 0 || ph == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(super.getDisabledTextColor());
        g2.drawString(ph, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
    }
}