import java.io.*;
import java.net.*;

public class server1 {
    public static void main(String args[]) {
        try {
            int serverport = 8020;
            ServerSocket server = new ServerSocket(serverport);
            System.out.println("Server waiting for connection...");
            Socket client = server.accept();
            System.out.println("SERVER SOCKET IS CREATED");

            // receives data from client (like a container)
            DataInputStream input = new DataInputStream(client.getInputStream());
            PrintStream output = new PrintStream(client.getOutputStream());

            String option = input.readLine();
            if (option.equals("upload")) {
                System.out.println("Upload text");

                // receive filename from client
                String filename = input.readLine(); // just filename

                // points to location and creates an empty file with name "filename" in server
                File file = new File("./server-files/" + filename);

                //STORAGE
                // writer to write data into the file with name "file" in server
                FileOutputStream fout = new FileOutputStream(file);

                // process to writing data into the file,character by character
                int ch;
                while ((ch = input.read()) != -1) {
                    //typecast into character equivalent of ASCII
                    fout.write((char) ch); 
                }
                fout.close();
                input.close();
            }
            
            if (option.equals("download")) {
                System.out.println("download text");
                //receive path of file located in server
                String path = input.readLine();

                // points to location and creates pointer to that file in 'path'
                File file = new File(path);

                // reader for reading contents from file named "file"
                FileInputStream fin = new FileInputStream(file);

                // send file to client
                int ch;
                while ((ch = fin.read()) != -1) {
                    output.print((char) ch);
                }
                fin.close();
                output.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}