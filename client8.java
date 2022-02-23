import java.io.*;
import java.net.*;

class client8 {
    public static void main(String[] args) {
        String str1, str2;
        BufferedReader br1, br2;
        PrintWriter pw;
        Socket client;

        try {
            int serverport = 5678;
            client = new Socket("localhost", serverport);
            System.out.println("Socket Created" + client);
            
            // reader for reading input from console
            br1 = new BufferedReader(new InputStreamReader(System.in));
            // reader for reading data from server
            br2 = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // writer for writing data to server
            pw = new PrintWriter(client.getOutputStream(), true);
            while (true) {
                // receive data from Server
                str1 = br2.readLine();
                System.out.println("Received Data:\n" + str1);
                if (str1.equals("bye"))
                    break;

                // send data to Server
                System.out.println("Enter the data to send:");
                str2 = br1.readLine();
                pw.println(str2);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}