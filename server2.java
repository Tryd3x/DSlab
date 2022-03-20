import java.util.*;
import java.net.*;
import java.io.*;

public class server2 {
    ServerSocket server;
    Socket client;
    DataInputStream input;
    DataOutputStream output;
    FileInputStream fin = null;
    FileOutputStream fout = null;
    Properties props;

    server2() {
        try {
            System.out.println("Server is running...");

            //instantiate empty property 
            props = new Properties();
            //read contents from NameList.dat and load it into 'props'
            fin = new FileInputStream("Namelist.dat");
            if (fin != null) {
                props.load(fin);// <---
                fin.close();
            }

            //Initialize connection with client
            int serverport = 1500;
            server = new ServerSocket(serverport);
            client = server.accept();
            System.out.println("Client connected");
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void runserver() {
        while (true) {
            try {
                String option = input.readUTF();
                if (option.equals("lookup")) {
                    //host
                    String host = input.readUTF();
                    System.out.println(host);
                    //search ip
                    String ip = lookup(host);
                    if (ip == null)
                        output.writeUTF("Host name not found");
                    else
                        output.writeUTF("IP address of " + host + " is " + ip);
                }
                if (option.equals("add")) {
                    String host = input.readUTF();
                    String ip = input.readUTF();
                    boolean b = addHost(host, ip);
                    if (b == true)
                        output.writeUTF("Host name registered");
                    else
                        output.writeUTF("Ip address already exists");
                }
                if (option.equals("remove")) {
                    String host = input.readUTF();
                    boolean b = removeHost(host);
                    if (b == true)
                        output.writeUTF("Hostname removed");
                    else
                        output.writeUTF("Invalid hostname");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    boolean addHost(String name, String ip) {
        //check if host exists
        if (props.get(name) != null)
            return false;//exists
        else
            props.put(name, ip);
        //update NameList.dat
        try {
            fout = new FileOutputStream("NameList.dat");
            props.store(fout, "Namespace");
            fout.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    boolean removeHost(String name) {
        if (props.get(name) != null)//it exists
            props.remove(name);
        else
            return false;
        try {
            fout = new FileOutputStream("NameList.dat");
            props.store(fout, "NameSpace");
            fout.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    String lookup(String name) {
        return (String) props.get(name);
    }

    public static void main(String args[]) {
        new server2().runserver();
    }
}