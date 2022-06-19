package Client.Request;

import Client.Cache.Content;
import Client.Exception.BadRequest;
import Client.Utils.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;

/*
* 构建请求的类
* */

public class HttpRequestBuilder {
    String CRLF="\r\n";

    public HttpRequest buildRequest(BufferedReader reader) throws IOException, BadRequest {//从流中构建请求
        String s;
        HttpRequestHeader header=null;
        StringBuilder sb=new StringBuilder();
        while (!(s = reader.readLine()).isEmpty()) {
            sb.append(s);
            sb.append(CRLF);
        }
        sb.append(CRLF);
        header = new HttpRequestHeader(sb.toString());

        Content content=new Content(header.getUri());
        if(content.exists()){//尝试给出本地缓存时间
            DateUtil dateUtil=new DateUtil();
            long lastModified=content.getLastModified();
            header.setAttribute("if-modified-since", dateUtil.longToDate(lastModified));
        }

        String length=header.getAttribute("content-length");
        if(length!=null){
            int len=Integer.parseInt(length);
            char[] chars=new char[len];
            reader.read(chars);
            return new HttpRequest(header,String.valueOf(chars));
        }
        return new HttpRequest(header,"");
    }

}
