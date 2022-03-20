import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.net.*;
import java.io.*;

public class client3 extends JFrame implements ActionListener {
    JLabel label = new JLabel("Enter your message: ");
    JTextField textfield = new JTextField(20);
    JButton sendbtn = new JButton("SEND");
    JPanel panel = new JPanel();
    JTextArea textarea = new JTextArea(20,20);
    JScrollPane scrollpane = new JScrollPane(textarea);

    Socket client;
    DataInputStream din;
    PrintWriter pw;

    public client3() {
        super("Client Window");

        panel.add(label);
        panel.add(textfield);
        panel.add(sendbtn);
        add(panel, BorderLayout.SOUTH);

        add(scrollpane);
        textarea.setEditable(false);

        sendbtn.addActionListener(this);
        textfield.addActionListener(this);
        
        setSize(500,500);
        setVisible(true);
        try {
            int serverport = 6789;
            client = new Socket("localhost",serverport);
            textarea.setText("Client is now Connected to Server\n");
            din = new DataInputStream(client.getInputStream());
            pw = new PrintWriter(client.getOutputStream());
        } catch(Exception e) {
            System.out.println("Exception: "+e.getMessage());
        }
    }

    public void receive() {
        try {
            String msg;
            while((msg = din.readLine()) != null) {
                String temp = "\nServer: "+msg;
                textarea.append(temp);
            }
        } catch (Exception e) {
            System.out.println("Exception: "+e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String msg = "\nClient: "+textfield.getText();
            textarea.append(msg);
            pw.println(textfield.getText());
            pw.flush();
            textfield.setText("");
        } catch(Exception e) {
            System.out.println("Exception: "+e.getMessage());
        }
    }

    public static void main(String[] args) {
        new client3().receive();
    }

}