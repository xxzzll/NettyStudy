package com.huawei.io.bio;

import com.huawei.io.bio.entities.HostInfo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 基础知识：
 *      网络模型：
 *          TCP/IP网络模型是从OSI七层模型中演化来的，
 *          osi模型分为物理层，数据链路层，网络层，传输层，会话层，表示层，应用层
 *
 *      TCP/IP网络模型分为：
 *          网络接口层，网际层，传输层，应用层
 *
 * BIO：
 *  1、初步认识
 *      bio提供了一种端对端的通信，相对于传输层的一种封装;
 *      相对于开发人员隐藏了传输细节，将这些固定的"套路"抽象出来，提供一种端对端的通信，可以开发人员更专注于开发，
 *      并且这种通信是阻塞式的（block input output）
 *  2、阻塞式
 *      服务端启动，等待客户端的连接，在客户端连接到服务端后，服务端启动一个线程去监听客户端消息，客户端发送消息，
 *      并等待服务端返回(客户端一直阻塞)，服务端收到消息，并将消息返回给客户端，此时一次交互完成。
 *      如不需交互，则客户端释放连接。
 *
 *  3、模拟代码分析：
 *      服务端：
 *          1）通过ServerSocket创建监听，并创建线程池;
 *          2）当ServerSocket通过accept()方法接受到客户端请求时，线程池将要分出一个线程来执行所要进行的操作;
 *          3）（分出的线程）等待客户端输入完成（即客户端安排做的事），服务端会执行自己的处理并返回相应的结果给客户端;
 *          4）关闭线程（等待线程执行完成后结束线程）;
 *          5）ServerSocket继续通过accept()方法接受到客户端请求
 *
 *      客户端：
 *          1）通过Socket创建连接到指定服务端的连接;
 *          2）将输入信息写入到OutputStream流中，等待服务端返回信息;
 *          3）接收到返回的信息后，则接着往下执行，如不需输入，则释放连接;
 *
 * 以上参考地址：https://www.cnblogs.com/shlearn/p/11657536.html
 * @author xixi
 * @Description： bio 服务端
 * @create 2020/3/19
 * @since 1.0.0
 */
public class BIOEchoServer {
    public static void main(String[] args) throws Exception{
        ServerSocket socket = new ServerSocket(HostInfo.PORT);
        System.out.println("服务端已经启动，监听端口为：" + HostInfo.PORT);
        boolean flag = true;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        while (flag){
            Socket client = socket.accept();
            executorService.submit(new EchoClientHandler(client));
        }
        executorService.shutdown();
        socket.close();
    }

    private static class EchoClientHandler implements Runnable{

        private Socket client;
        private Scanner scanner;
        private PrintStream out;
        private boolean flag = true;

        public EchoClientHandler(Socket client){
            this.client = client;
            try {
                this.scanner = new Scanner(this.client.getInputStream());
                this.scanner.useDelimiter("\n");
                this.out = new PrintStream(this.client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (this.flag){
                if(this.scanner.hasNext()){
                    String var = this.scanner.next().trim();
                    System.out.println("收到客户端发来的"+var);
                    if("byebye".equals(var)){
                        this.out.print("888888");
                        this.flag = false;
                    } else {
                        out.println("【echo】" + var);
                    }
                }
            }
            try {
                this.scanner.close();
                this.out.close();
                this.client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
