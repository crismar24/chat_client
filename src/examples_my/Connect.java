package examples_my;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

public class Connect {
    // Определяем номер порта, на котором нас ожидает сервер для ответа
    public int portNumber;
    public String host;    //"127.0.0.1"
    BufferedReader br;
    PrintWriter pw;
    // Подготавливаем строку для запроса - просто строка
    public String sentString;
    public String reciveString;
    public Socket socket;

    public Connect(int portNumber, String host) {
        this.portNumber = portNumber;
        this.host = host;
    }

    public void init() throws IOException {
        // Пишем, что стартовали клиент
        System.out.println("Client is started");

        // Открыть сокет (Socket) для обращения к локальному компьютеру
        // Сервер мы будем запускать на этом же компьютере
        // Это специальный класс для сетевого взаимодействия c клиентской стороны
        socket = new Socket(host, portNumber);

        // Создать поток для чтения символов из сокета
        // Для этого надо открыть поток сокета - socket.getInputStream()
        // Потом преобразовать его в поток символов - new InputStreamReader
        // И уже потом сделать его читателем строк - BufferedReader
        // ip server / client ?
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Создать поток для записи символов в сокет
        // ip server / client ?
        pw = new PrintWriter(socket.getOutputStream(), true);

    }

    public void sent(String sentString) throws Exception {
        // Отправляем строку в сокет
        pw.println(sentString);

        //pw.close();
        //socket.close();
    }

    // получаем данные с сокета
    public void recive(JTextArea inputShow, JList<String> jList, HashSet<String> hashSetUsers, DefaultListModel<String> modelList) throws Exception {
        while (true) {
            reciveString = null;
            if ((reciveString = br.readLine()) != null) {
                // Получаем ответ от сервера
                if (reciveString.contains("- Server correct-")) {
                    // Посылаем клиенту Положительную проверку на соединение с Сервером
                    pw.println("- Server correct-");
                } else {
                    //получить пользователя
                    int firstNumberChar = reciveString.indexOf(": ");
                    if (firstNumberChar > 0) {
                        String user = reciveString.substring(0, firstNumberChar);
                        //если в пришедшем Ответе есть новый пользователь -
                        // - то добавляем его в userList / hashSetUsers
                        if (!hashSetUsers.contains(user)) {
                            hashSetUsers.add(user);
                        }

                        // TODO Сделать - Посылать ответ всем юзерам из hashSetUsers
                        // Создать класс Connection - объект будет создаваться каждый раз(возможно только для ногового пользователя,единожды) при отправке сообщения
                        // Отправим всем клиентам по ip сообщение
                        for (String userSet : hashSetUsers) {
                            Connect connect = new Connect(1777, userSet);
                            connect.init();
                            connect.sent(reciveString);
                            connect.close();
                        }

                        //очищаем список пользователей
                        modelList.clear();

                        //заполняем List на форме всеми уник. пользователями
                        for (Object userSet : hashSetUsers) {
                            modelList.addElement(userSet.toString());
                        }
                        // Оправим на Табло сервера текст сообщения сервера
                        fillInputShow(inputShow, reciveString);
                    }
                }
            }
        }
    }

    private void close() throws IOException {
        //br.close();
        pw.close();
        socket.close();
    }

    private void fillInputShow(JTextArea inputShow, String reciveString) {
        String stringInputShow = inputShow.getText();

//        inputShow.append("\r\n" + host + " _ " + portNumber + ": " + reciveString);
        inputShow.append("\r\n"+ reciveString);
    }

    public void checkConnectionServer() {
        // Отправляем строку в сокет
        pw.println("Request of connection of the server");

    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSentString() {
        return sentString;
    }

    public void setSentString(String sentString) {
        this.sentString = sentString;
    }
}
