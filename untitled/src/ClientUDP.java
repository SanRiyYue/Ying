import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClientUDP {
    public static void main(String[] args) throws IOException {
        for (;;) {
            DatagramSocket ds = new DatagramSocket();
            ds.setSoTimeout(1000);
            ds.connect(InetAddress.getByName("localhost"), 6666);//连接到指定服务器和端口
            //发送：
            DatagramPacket packet = null;
            Scanner scanner = new Scanner(System.in);
            System.out.print("[client]: ");
            String message = null;

            message = scanner.nextLine();
            byte[] data = message.getBytes();
            packet = new DatagramPacket(data, data.length);
            ds.send(packet);
            //接收：
            byte[] buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);
            ds.receive(packet);
            String resp = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println(resp);
            ds.disconnect();
        }
    }
}
