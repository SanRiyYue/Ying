import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ServerUDP {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(6666); //监听指定端口
        System.out.println("Server listening on port 6666");
        for (;;){ //无限循环
            //数据缓冲区
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            ds.receive(packet);//收取一个UDP数据包
            //收取到的数据存储在buffer中，由packet.getOffset(),packet.getLength()指定起始位置和长度
            //将其按UTF-8编码转换为String:
            String cmd = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
            //发送数据
            String resp = "bad command";
            switch (cmd) {
                case "date": {
                    resp = LocalDate.now().toString();
                    break;
                }
                case "time": {
                    resp = LocalTime.now().toString();
                    break;
                }
                case "datetime": {
                    resp = LocalDateTime.now().toString();
                    break;
                }
                case "weather":{
                    resp = "sunny, 10~15℃.";
                    break;
                }
                default:{
                    resp = "unknown command";
                    break;
                }

            }
            //System.out.println("[server]: " + cmd + ">>> " + resp);
            packet.setData(resp.getBytes(StandardCharsets.UTF_8));
            ds.send(packet);
        }
    }
}
