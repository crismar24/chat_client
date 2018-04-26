package examples_my;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyWindow extends JFrame {

    Label labelChatWindow;
    JTextArea chatWindow;
    String ipServer;
    int port;
    JTextField input;
    JTextField inputShow;
    Label labelInput;
    Label labelInputShow;
    JList <String> userList;

    public MyWindow(Connect connect) throws HeadlessException {
        setTitle("Client");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JTextField labelInputServer = new JTextField("Server IP: ");
        labelInputServer.setEditable(false);
        labelInputServer.setBackground(Color.LIGHT_GRAY);

        chatWindow = new JTextArea();
        chatWindow.setLineWrap(true);
        chatWindow.setWrapStyleWord(true);
        chatWindow.setEditable(false);

        labelInput = new Label("Write message:");
        labelInput.setBackground(Color.LIGHT_GRAY);


        JButton connectServerButton = new JButton("Connect server");


        JTextField inputServer = new JTextField();
        inputServer.setPreferredSize(new Dimension(200,25));

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new GridLayout(1,3,5,5));
        headingPanel.setPreferredSize(new Dimension(700, 25));
        headingPanel.add(labelInputServer, BorderLayout.WEST);
        headingPanel.add(inputServer, BorderLayout.CENTER);
        headingPanel.add(connectServerButton, BorderLayout.EAST);
        mainPanel.add(headingPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1,2,5,5));
        centerPanel.setPreferredSize(new Dimension(330, 450));
        JScrollPane scrollPanelChatWindow = new JScrollPane(chatWindow);
        scrollPanelChatWindow.createVerticalScrollBar();
        scrollPanelChatWindow.setPreferredSize(new Dimension(330,300));
        centerPanel.add(scrollPanelChatWindow);

        DefaultListModel<String> model = new DefaultListModel<>();
        userList = new JList(model);
        userList.setPreferredSize(new Dimension(330,300));
//        for (int i = 0; i < 25; i++) {
//            model.addElement("192.169.0." + i);
//        }
        JScrollPane scrollPaneluserList = new JScrollPane(userList);
        scrollPaneluserList.createVerticalScrollBar();
        scrollPaneluserList.setPreferredSize(new Dimension(330,300));
        centerPanel.add(scrollPaneluserList, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel downPanel = new JPanel();
        downPanel.setLayout(new GridLayout(1,2,5,5));
        downPanel.setPreferredSize(new Dimension(700, 25));
        input = new JTextField();
        input.setPreferredSize(new Dimension(500,50));
        downPanel.add(input, BorderLayout.WEST);
        JButton sentButton = new JButton("send message");
        sentButton.setPreferredSize(new Dimension(170,50));
        downPanel.add(sentButton, BorderLayout.EAST);
        mainPanel.add(downPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setPreferredSize(new Dimension(700, 500));
        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Connect finalConnect1 = connect;
        sentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sentMessage(finalConnect1, input);
            }
        });

        input.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (e.getKeyChar() == '\n') {
                            String textInput = input.getText().replaceAll("[\\s]{2,}", " ");
                            textInput = textInput.trim();
                            if (textInput.equals("")) {
                                input.setText("");
                            }else {
                                sentMessage(finalConnect1, input);

                            }
                        }
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );

        connectServerButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (connect.socket != null) {
                        connect.socket.close();
                    }

                    ipServer = inputServer.getText();
                    connect.setHost(inputServer.getText());

                    // Инициализация клиента
                    connect.init();

                    new Thread(new ClientReciveDataThread(connect, chatWindow, userList, model)).start();
                    //если в пришедшем Ответе есть новый пользователь -
                    // - то добавляем его в userList

                    connect.sent("Request of connection of the server");

                } catch (Exception e1) {
                    e1.printStackTrace(System.out);
                }
            }
        });

    }

    void sentMessage(Connect finalConnect1, JTextField input) {
        String textInput = input.getText().replaceAll("[\\s]{2,}", " ");
        textInput = textInput.trim();
        if (textInput.equals("")) {
            input.setText("");
        } else {
            try {

                finalConnect1.sent(ipServer + ": "+input.getText());
                input.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
