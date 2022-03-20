import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.net.*;
import java.io.*;

public class client1 extends JFrame implements ActionListener {
    JButton b1, b2;
    JLabel l1, l2, msg1, msg2;
    JPanel p1, p2, p3;

    Socket client;
    DataInputStream input;
    PrintStream output;
    String remoteadd;

    public client1() {
        super("Application");
        b1 = new JButton("Upload");
        b2 = new JButton("Download");

        l1 = new JLabel("Upload a file");
        l2 = new JLabel("Download a file");
        msg1 = new JLabel("");
        msg2 = new JLabel("");

        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();

        p1.add(l1);
        p1.add(b1);

        p3.add(msg1);
        p3.add(msg2);

        p2.add(l2);
        p2.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        add(p1, BorderLayout.NORTH);
        add(p3, BorderLayout.CENTER);
        add(p2, BorderLayout.SOUTH);

        setVisible(true);
        setSize(300, 300);

        try {
            int serverport = 8020;
            Socket client = new Socket("localhost", serverport);
            System.out.println("CLIENT CONNECTED TO SERVER");

            // obtain server address and socket information
            remoteadd = client.getRemoteSocketAddress().toString();
            System.out.println(remoteadd);

            input = new DataInputStream(client.getInputStream());
            output = new PrintStream(client.getOutputStream());

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == b1) {
                // Launch FileChooser UI
                JFileChooser j = new JFileChooser();
                j.showOpenDialog(client1.this);

                // obtain filename and path
                String filename = j.getSelectedFile().getName();
                String path = j.getSelectedFile().getPath();
                // System.out.println("Filename: " + filename);
                // System.out.println("Path: " + path);
                // sends data to server (like a container)

                output.println("upload");
                output.println(filename);

                //reader to read data from file
                // path: C:\Users\hyder\Documents\dummy-file.txt
                FileInputStream fin = new FileInputStream(path);
                int ch;
                while ((ch = fin.read()) != -1) {
                    output.print((char) ch);
                }
                fin.close();
                output.close();

                msg1.setText(filename + " is successfully uploaded");
            }
            if (ae.getSource() == b2) {
                //'remoteadd' to help in browsing files stored on "server-fies"
                JFileChooser j1 = new JFileChooser(remoteadd);
                j1.showOpenDialog(client1.this);

                String filename = j1.getSelectedFile().getName();
                String path = j1.getSelectedFile().getPath();

                output.println("download");
                output.println(path);

                //STORAGE
                //writer to write data into the file with name 'filename' on client
                FileOutputStream fout = new FileOutputStream("./client-files/" + filename);
                int ch;
                while ((ch = input.read()) != -1) {
                    fout.write((char) ch);
                }
                fout.close();
                input.close();

                msg2.setText(filename + " IS DOWNLOADED");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new client1();
    }
}