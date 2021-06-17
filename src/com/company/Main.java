package com.company;


import javax.swing.*;

/**
 *
 * @author danzlo
 */

public class Main {


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Login app = new Login();
            app.setLocationRelativeTo(null);
            app.setVisible(true);
            app.setVisible(true);

        }
        );
    }
}










