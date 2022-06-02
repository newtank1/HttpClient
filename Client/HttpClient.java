package Client;

import Client.Exception.BadRequest;
import Client.Request.*;
import Client.Response.HttpResponse;
import Client.Response.HttpResponseParser;
import Client.Response.ResponseParserImpl;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HttpClient {
    private final HttpRequestBuilder builder=new RequestBuilderImpl();
    private final HttpRequestSender sender=new RequestSenderImpl();
    private final HttpResponseParser parser=new ResponseParserImpl();

    public void go() {
        String CRLF="\r\n";
        Socket socket=new Socket();
        try {
            socket.connect(new InetSocketAddress("127.0.0.1", 20000));
            BufferedInputStream bufferedInputStream=new BufferedInputStream(socket.getInputStream());
            BufferedReader consoleReader=new BufferedReader(new InputStreamReader(System.in));
            HttpRequest request=null;
            while (request==null){
                try {
                    request=builder.buildRequest(consoleReader);
                } catch (BadRequest ignored) {
                    System.out.println("wrong request!");
                }
            }
            sender.send(request,socket);
            HttpResponse response=parser.parseResponse(bufferedInputStream);
            System.out.println(response);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new HttpClient().go();
    }
}
