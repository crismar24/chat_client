package examples_my;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;

public class ClientReciveDataThread extends Thread {
    private Connect finalConnect;
    JTextArea inputShow;
    JList<String> jList;
    HashSet<String> hashSetUsers;
    DefaultListModel<String> modelList;

    public ClientReciveDataThread(HashSet<String> hashSetUsers, DefaultListModel<String> modelList, Connect finalConnect, JTextArea inputShow, JList<String> jList, DefaultListModel model) {
        this.finalConnect = finalConnect;
        this.inputShow = inputShow;
        this.jList = jList;
        this.hashSetUsers = hashSetUsers;
        this.modelList = modelList;
    }

    public void run() {
        try {
            finalConnect.recive(inputShow, jList, hashSetUsers, modelList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
