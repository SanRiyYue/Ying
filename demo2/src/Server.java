import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080); //监听指定端口
        System.out.println("Server is running...");
        for (;;) {
            Socket socket = server.accept();
            System.out.println("connected from " + socket.getRemoteSocketAddress());
            Thread t = new Handler(socket);
            t.start();
        }
    }
}

class Handler extends Thread {
    Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (InputStream input = this.socket.getInputStream()) {
            try (OutputStream output = this.socket.getOutputStream()) {
                handle(input, output);
            }
        } catch (IOException e) {
            try {
                this.socket.close();
            } catch (IOException e1) {
            }
            System.out.println("Client disconnected.");
        }
    }

    private void handle(InputStream input, OutputStream output) throws IOException {
        System.out.println("Process new http request...");
        var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        //读取HTTP请求
        boolean requestOK = false;
        String first = reader.readLine();
        if (first.startsWith("GET / HTTP/1.")) {
            requestOK = true;
        }
        for (;;) {
            String header = reader.readLine();
            if (header.isEmpty()) {
                break;
            }
            System.out.println(header);
        }
        System.out.println(requestOK ? "Response OK" : "Response Not OK");
        if (!requestOK) {
            //发送错误响应
            writer.write("HTTP/1.0 404 Not Found\r\n");
            writer.write("Content-Type: 0\r\n");
            writer.write("\r\n");
            writer.flush();
        } else {
            //发送成功请求
            String data = "<html><body><h1>hello, world!</h1></body></html>";
            int length = data.getBytes(StandardCharsets.UTF_8).length;
            writer.write("HTTP/1.0 200 OK\r\n");
            writer.write("Connection: close\r\n");
            writer.write("Content-Type: text/html\r\n");
            writer.write("Content-Length: " + length + "\r\n");
            writer.write("\r\n");
            writer.write(data);
            writer.flush();
        }
    }
}
