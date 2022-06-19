package Client;

import Client.Exception.BadRequest;
import Client.Request.*;
import Client.Response.HttpResponse;
import Client.Response.HttpResponseHandler;
import Client.Response.HttpResponseParser;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
    private final HttpRequestBuilder builder=new HttpRequestBuilder();
    private final HttpRequestSender sender=new HttpRequestSender();
    private final HttpResponseParser parser=new HttpResponseParser();
    private final HttpResponseHandler handler=new HttpResponseHandler();
    private final InputStream in;
    private static final Map<String,String> config;

    public HttpClient(InputStream in) {
        this.in = in;
    }

    public void go() {
        String CRLF="\r\n";
        Socket socket=new Socket();
        try {
            socket.connect(new InetSocketAddress(config.get("Address"), Integer.parseInt(config.get("Port"))));//获取目标地址
            BufferedInputStream bufferedInputStream=new BufferedInputStream(socket.getInputStream());
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));//获取输入
            HttpRequest request=null;
            while (request==null){
                try {
                    request=builder.buildRequest(reader);//构建请求
                } catch (BadRequest ignored) {
                    System.out.println("wrong request!");
                }
            }
            sender.send(request,socket);//发送请求
            HttpResponse response=parser.parseResponse(bufferedInputStream,request.getUri());//解析响应
            handler.handleResponse(response);//处理
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new HttpClient(System.in).go();
    }

    static {
        config=new HashMap<>();//读配置文件
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("Config.txt")));
            String s;
            while ((s= reader.readLine())!=null){
                String[] strs=s.split(":",2);
                config.put(strs[0],strs[1]);
            }
            System.out.println("Config loaded");
        } catch (IOException e) {
            System.err.println("Failed to load Config.txt");
            e.printStackTrace();
        }
    }
}
