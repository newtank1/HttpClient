package Client.Response;


public class HttpResponse {
    public static final String CRLF="\r\n";

    private final HttpResponseHeader header;
    private String uri;
    private byte[] data;

    public HttpResponse(HttpResponseHeader header, String uri, byte[] data) {
        this.header = header;
        this.uri = uri;
        this.data = data;
    }

    public int getStatus(){
        return header.getStatus();
    }

    public String getAttribute(String key){
        return header.getAttribute(key);
    }

    public String getVersion(){
        return header.version;
    }

    public byte[] getData() {
        return data;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        if (data != null) {
            return header+CRLF+CRLF+new String(data);
        }
        return header+CRLF+CRLF;
    }
}
