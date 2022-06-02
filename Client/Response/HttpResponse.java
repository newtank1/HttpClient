package Client.Response;

import java.io.IOException;

public class HttpResponse {
    public static final String CRLF="\r\n";

    HttpResponseHeader header;
    byte[] data;

    public HttpResponse(HttpResponseHeader header, byte[] data) {
        this.header = header;
        this.data = data;
    }

    public HttpResponse(String version){
        header=new HttpResponseHeader(version);
    }

    public int getStatus(){
        return header.getStatus();
    }

    @Override
    public String toString() {
        if (data != null) {
            return header+CRLF+CRLF+new String(data);
        }
        return header+CRLF+CRLF;
    }
}
