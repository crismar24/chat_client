package examples_my;

import javax.swing.*;
import java.io.IOException;

public class ClientReciveDataThread extends Thread {
    private Connect finalConnect;
    JTextArea inputShow;
    JList<String> jList;
    DefaultListModel model;
    public ClientReciveDataThread(Connect finalConnect, JTextArea inputShow, JList<String> jList, DefaultListModel model) {
        this.finalConnect = finalConnect;
        this.inputShow = inputShow;
        this.jList = jList;
        this.model = model;
    }

    public void run(){
        try {
            finalConnect.recive(inputShow, jList, model);
            //если в пришедшем Ответе есть новый пользователь -
            // - то добавляем его в userList

        } catch (IOException e) {
            e.printStackTrace(System.out);  //Socket closed , why ?
        }
    }


}
