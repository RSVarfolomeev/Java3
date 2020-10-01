package ru.gb.chat.client;

import ru.gb.chat.common.Common;
import ru.gb.javatwo.network.SocketThread;
import ru.gb.javatwo.network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler, SocketThreadListener {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("QA");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JPanel panelChangeNickname = new JPanel(new BorderLayout());
    private final JButton btnChangeNickname = new JButton("Change nickname");
    private final JTextField tfChangeNickname = new JTextField();

    private final JList<String> userList = new JList<>();
    private boolean shownIoErrors = false;
    private SocketThread socketThread;
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss: ");
    private final String WINDOW_TITLE = "Chat";

    private ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle(WINDOW_TITLE);
        log.setEditable(false);
        log.setLineWrap(true);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUser = new JScrollPane(userList);
        scrollUser.setPreferredSize(new Dimension(100, 0));
        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);
        tfMessage.addActionListener(this);
        btnLogin.addActionListener(this);
        btnDisconnect.addActionListener(this);
        btnChangeNickname.addActionListener(this);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        panelBottom.setVisible(false);
        panelChangeNickname.add(btnChangeNickname, BorderLayout.WEST);
        panelChangeNickname.add(tfChangeNickname, BorderLayout.CENTER);
        panelBottom.add(panelChangeNickname, BorderLayout.NORTH);

        add(scrollLog, BorderLayout.CENTER);
        add(scrollUser, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);
        wrtLogFileToLogArea();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { // Event Dispatching Thread
                new ClientGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend || src == tfMessage) {
            sendMessage();
        } else if (src == btnLogin) {
            connect();
        } else if (src == btnChangeNickname || src == tfChangeNickname) {
            changeNickname();
        } else if (src == btnDisconnect) {
            socketThread.close();
        } else {
            throw new RuntimeException("Unknown source: " + src);
        }
    }

    private void connect() {
        try {
            Socket socket = new Socket(tfIPAddress.getText(), Integer.parseInt(tfPort.getText()));
            socketThread = new SocketThread(this, "Client", socket);
        } catch (IOException exception) {
            showException(Thread.currentThread(), exception);
        }
    }

    private void sendMessage() {
        String msg = tfMessage.getText();
        if ("".equals(msg)) return;
        tfMessage.setText(null);
        tfMessage.grabFocus();
        socketThread.sendMessage(Common.getTypeBcastClient(msg));
    }

    private void changeNickname() {
        String msg = tfChangeNickname.getText();
        if ("".equals(msg)) return;
        tfChangeNickname.setText(null);
        tfChangeNickname.grabFocus();
        socketThread.sendMessage(Common.getChangeNicknameLoginPassword(msg, tfLogin.getText(), tfPassword.getText()));
    }

    private void putLog(String msg) {
        if ("".equals(msg)) return;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    private void showException(Thread t, Throwable e) {
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if (ste.length == 0)
            msg = "Empty Stacktrace";
        else {
            msg = String.format("Exception in \"%s\" %s: %s\n\tat %s",
                    t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
            JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, msg, "Exception", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        showException(t, e);
        System.exit(1);
    }

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Start");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        panelBottom.setVisible(false);
        panelTop.setVisible(true);
        setTitle(WINDOW_TITLE);
        userList.setListData(new String[0]);
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        panelBottom.setVisible(true);
        panelTop.setVisible(false);
        String login = tfLogin.getText();
        String password = new String(tfPassword.getPassword());
        thread.sendMessage(Common.getAuthRequest(login, password));
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        handleMessage(msg);
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        showException(thread, exception);
    }

    private void wrtLogFileToLogArea() {
        try (BufferedReader in = new BufferedReader(new FileReader("log.txt"))) {
            String logFile = "";
            for (int i = 0; i < LogFileNumberLines() - 100; ++i) {
                in.readLine();
            }
            for (int i = LogFileNumberLines() - 100; i < LogFileNumberLines(); ++i) {
                logFile += in.readLine() + "\n";
            }
            log.append(logFile);
            log.setCaretPosition(log.getDocument().getLength());
        } catch (IOException e) {
            if (!shownIoErrors) {
                shownIoErrors = true;
                showException(Thread.currentThread(), e);
            }
        }
    }

    private int LogFileNumberLines() {
        try (BufferedReader in = new BufferedReader(new FileReader("log.txt"))) {
            int j = 0;
            while (in.readLine() != null) {
                j++;
            }
            return j;
        } catch (IOException e) {
            if (!shownIoErrors) {
                shownIoErrors = true;
                showException(Thread.currentThread(), e);
            }
        }
        return 0;
    }

    private void wrtMsgToLogFile(String msg) {
        try (FileWriter out = new FileWriter("log.txt", true)) {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException e) {
            if (!shownIoErrors) {
                shownIoErrors = true;
                showException(Thread.currentThread(), e);
            }
        }
    }

    //      Исходный вариант метода.
//    private void wrtMsgToLogFile(String msg, String username) {
//        try (FileWriter out = new FileWriter("log.txt", true)) {
//            out.write(username + ": " + msg + "\n");
//            out.flush();
//        } catch (IOException e) {
//            if (!shownIoErrors) {
//                shownIoErrors = true;
//                showException(Thread.currentThread(), e);
//            }
//        }
//    }

    private void handleMessage(String msg) {
        String[] arr = msg.split(Common.DELIMITER);
        String msgType = arr[0];
        switch (msgType) {
            case Common.AUTH_ACCEPT:
                String msgAA = WINDOW_TITLE + " entered with nickname: " + arr[1];
                setTitle(msgAA);
                wrtMsgToLogFile(msgAA);
                break;
            case Common.AUTH_DENIED:
                putLog(msg);
                wrtMsgToLogFile(msg);
                break;
            case Common.MSG_FORMAT_ERROR:
                putLog(msg);
                wrtMsgToLogFile(msg);
                socketThread.close();
                break;
            case Common.TYPE_BROADCAST:
                String msgTB = DATE_FORMAT.format(Long.parseLong(arr[1])) +
                        arr[2] + ": " + arr[3];
                putLog(msgTB);
                wrtMsgToLogFile(msgTB);
                break;
            case Common.CHANGE_NICKNAME:
                String msgCN = DATE_FORMAT.format(Long.parseLong(arr[1])) +
                        arr[2] + " change nickname --> " + arr[3];
                putLog(msgCN);
                wrtMsgToLogFile(msgCN);
                break;
            case Common.USER_LIST:
                String users = msg.substring(Common.USER_LIST.length() +
                        Common.DELIMITER.length());
                String[] usersArr = users.split(Common.DELIMITER);
                Arrays.sort(usersArr);
                userList.setListData(usersArr);
                break;
            default:
                throw new RuntimeException("Unknown message type: " + msg);
        }
    }

}
