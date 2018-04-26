package examples_my;

import javax.swing.*;
import java.io.IOException;

public class ClientReciveDataThread extends Thread {
    private Connect finalConnect;
    JTextArea inputShow;
    JList<String> jList;
    public ClientReciveDataThread(Connect finalConnect, JTextArea inputShow, JList<String> jList) {
        this.finalConnect = finalConnect;
        this.inputShow = inputShow;
        this.jList = jList;
    }

    public void run(){
        try {
            finalConnect.recive(inputShow, jList);
            //если в пришедшем Ответе есть новый пользователь -
            // - то добавляем его в userList

        } catch (IOException e) {
            e.printStackTrace(System.out);  //Socket closed , why ?
        }
    }


}
