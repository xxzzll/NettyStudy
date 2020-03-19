package com.huawei.io.bio;

import com.huawei.io.bio.entities.HostInfo;
import com.huawei.io.bio.utils.InputUtil;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author xixi
 * @Description： bio客户端
 * @create 2020/3/19
 * @since 1.0.0
 */
public class BIOEchoClient {
    public static void main(String[] args) throws Exception{
        Socket client = new Socket(HostInfo.HOST_NAME, HostInfo.PORT);
        Scanner scan = new Scanner(client.getInputStream());
        scan.useDelimiter("\n");
        PrintStream out = new PrintStream(client.getOutputStream());
        boolean flag = true;
        while (flag){
            String inputData = InputUtil.getString("请输入要发送的内容：").trim();
            out.println(inputData);
            if (scan.hasNext()){
                String str = scan.next();
                System.out.println(str);
            }
            if ("byebye".equalsIgnoreCase(inputData)){
                flag = false;
            }
        }
        client.close();
    }
}
