import java.net.*;

public class client5 {
    public static void main(String[] args) {
        DatagramSocket client = null;
        try {
            int serverport = 6789;
            client = new DatagramSocket();
            byte[] buffer = new byte[1000];


            // create an outgoing packet to send data
            DatagramPacket outgoing = new DatagramPacket(args[1].getBytes(), args[1].length(),
                    InetAddress.getByName(args[0]), serverport);
            client.send(outgoing);

            // create an incoming packet to receive data
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            client.receive(incoming);

            // Parse and display data from incoming packet
            String msg = new String(incoming.getData());
            System.out.println("Data received from server: " + msg);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}