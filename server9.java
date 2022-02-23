import java.io.*;
import java.net.*;

class server9 {
    public static void main(String[] args) {
        try {
            String str1, str2;
            DatagramSocket ds;
            DatagramPacket incoming, outgoing;
            byte[] data1 = new byte[1024];
            byte[] data2 = new byte[1024];
            BufferedReader br1;

            int serverport = 1500;
            int clientport = 1700;
            ds = new DatagramSocket(serverport);
            br1 = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                // get input from console
                System.out.println("Enter the data to send");
                str1 = br1.readLine();
                data1 = str1.getBytes();

                // create an outgoing packet to send data
                outgoing = new DatagramPacket(data1, data1.length, InetAddress.getLocalHost(), clientport);
                ds.send(outgoing);
                // if (str1.equals("bye"))
                //     break;

                // create an incoming packet to receive data
                incoming = new DatagramPacket(data2, data2.length);
                ds.receive(incoming);

                // Parse and display data from incoming packet
                str2 = new String(incoming.getData());
                System.out.println("Received Data:\n" + str2);
                if (str2.equals("bye"))
                    break;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}