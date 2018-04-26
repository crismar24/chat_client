package examples_my;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainApp {

    private static String ipServer;
    private static int port;
    JFrame myWindow;
    JButton jButton;
    JTextField input;
    JTextField inputShow;
    Container container;
    Label labelInput;
    Label labelInputShow;

    public static void main(String[] args) {

        //ipServer = getIPComputer();
        port = 1777;

        Connect connect = new Connect(port, ipServer);

        MyWindow myWindow = new MyWindow(connect);
        myWindow.setVisible(true);


        //createUI(connect);

    }

    private static void sentMessage(Connect finalConnect1, JTextField input) {
        try {
            finalConnect1.sent(input.getText());
            input.setText("");
        } catch (Exception e1) {
            e1.printStackTrace(System.out);
        }
    }

    private static String getIPComputer() {
        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();
            ipServer = local.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipServer;
    }
}



