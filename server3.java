import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.net.*;

public class server3 extends JFrame implements ActionListener {
    JButton sendbtn = new JButton("SEND");
    JLabel label = new JLabel("Enter your message:");
    JTextField textfield = new JTextField(20);
    JTextArea textarea = new JTextArea(20, 20);
    JScrollPane scrollpane = new JScrollPane(textarea);
    JPanel panel = new JPanel();

    ServerSocket server;
    Socket client;
    DataInputStream input;
    PrintWriter output;

    public server3() {
        super("Server Window");

        panel.add(label);
        panel.add(textfield);
        panel.add(sendbtn);

        add(panel, BorderLayout.SOUTH);
        add(scrollpane);

        sendbtn.addActionListener(this);
        textfield.addActionListener(this);

        textarea.setEditable(false);
        setSize(500, 500);
        setVisible(true);
        try {
            int serverport = 6789;
            server = new ServerSocket(serverport);
            textarea.setText("Server is waiting for Client\n");
            client = server.accept();
            textarea.append("Client is now Connected\n");
            input = new DataInputStream(client.getInputStream());
            output = new PrintWriter(client.getOutputStream());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public void receive() {
        try {
            String msg;
            while((msg=input.readLine()) != null) {
                String temp = "\nClient: "+msg;
                textarea.append(temp);
            }
        } catch(Exception e) {
            System.out.println("Exception: "+e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try{
            String msg = "\nServer: "+textfield.getText();
            textarea.append(msg);
            output.println(textfield.getText());
            output.flush();
            textfield.setText("");
        } catch(Exception e) {
            System.out.println("Exception: "+e.getMessage());
        }
    }

    public static void main(String[] args) {
        new server3().receive();
    }
}
