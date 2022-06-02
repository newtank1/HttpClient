package Client.Response;

import java.io.IOException;
import java.io.InputStream;

public class ResponseParserImpl implements HttpResponseParser{

    @Override
    public HttpResponse parseResponse(InputStream inputStream) throws IOException {
        String headStr=readHeader(inputStream);
        HttpResponseHeader header=new HttpResponseHeader(headStr);
        String lenStr=header.getAttribute("content-length");
        if(lenStr==null) return new HttpResponse(header,null);
        try{
            byte[] data = inputStream.readNBytes(Integer.parseInt(lenStr.strip()));
            return new HttpResponse(header,data);
        }catch (RuntimeException e){
            e.printStackTrace();
            return new HttpResponse(header,null);
        }
    }
    private String readHeader(InputStream inputStream) throws IOException {
        StringBuilder bytes = new StringBuilder();
        while (bytes.length() < 4 || !CRLFCRLF.equals(bytes.substring(bytes.length() - 4, bytes.length()))) {
            bytes.append((char) inputStream.read());

        }
        return bytes.toString();
    }
}
