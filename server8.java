import java.io.*;
import java.net.*;

class server8 {
    public static void main(String[] args) {
        String str1, str2;
        BufferedReader br1, br2;
        PrintWriter pw;
        ServerSocket server;
        Socket client;

        try {
            int serverport = 5678;
            server = new ServerSocket(serverport);
            System.out.println("Server Socket Created" + server);
            client = server.accept();
            System.out.println("Connection Created" + client);

            br1 = new BufferedReader(new InputStreamReader(System.in));
            br2 = new BufferedReader(new InputStreamReader(client.getInputStream()));
            pw = new PrintWriter(client.getOutputStream(), true);
            while (true) {
                // get input from console
                System.out.println("Enter the data to send");
                str1 = br1.readLine();

                // send data to client
                pw.println(str1);

                // receive data from client
                str2 = br2.readLine();
                System.out.println("Received Data: \n" + str2);
                if (str2.equals("bye"))
                    break;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}