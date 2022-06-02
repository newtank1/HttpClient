package Client.Request;

import Client.Exception.BadRequest;
import Client.Response.HttpResponseHeader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class RequestBuilderImpl implements HttpRequestBuilder{

    @Override
    public HttpRequest buildRequest(BufferedReader reader) throws IOException, BadRequest {
        String s;
        HttpRequestHeader header=null;
        StringBuilder sb=new StringBuilder();
        while (!(s = reader.readLine()).isEmpty()) {
            sb.append(s);
            sb.append(CRLF);
        }
        sb.append(CRLF);
        header = new HttpRequestHeader(sb.toString());
        String length=header.getAttribute("content-length");
        if(length!=null){
            int len=Integer.parseInt(length);
            char[] chars=new char[len];
            reader.read(chars);
            return new HttpRequest(header,String.valueOf(chars));
        }
        return new HttpRequest(header,"");
    }

    @Override
    public HttpRequest buildRequest(String s) throws BadRequest {
        String[] strs=s.split(CRLF,2);
        HttpRequestHeader header=new HttpRequestHeader(strs[0]);
        String len=header.getAttribute("content-length");
        if(len!=null){
            try {
                int length = Integer.parseInt(len);
                String data=strs[2].substring(0,length);
                return new HttpRequest(header,data);
            }catch (RuntimeException e){
                throw new BadRequest();
            }
        }
        return new HttpRequest(header,null);
    }
}
