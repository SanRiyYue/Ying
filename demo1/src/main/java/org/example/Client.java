package org.example;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.spi.LocaleServiceProvider;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        //连接到服务器localhost, 端口1099
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        //查找名称为"WorldClock"的服务并强制转型为WorldClock接口
        WorldClock worldClock = (WorldClock) registry.lookup("WorldClock");
        //正常调用接口方法
        LocalDateTime now = worldClock.getLocalDateTime("Asia/Shanghai");
        //打印调用结果
        System.out.println(now);
    }
}
