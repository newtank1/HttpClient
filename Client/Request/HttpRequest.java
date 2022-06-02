package Client.Request;

public class HttpRequest {
    public static final String CRLF="\r\n";
    private final HttpRequestHeader header;
    private final String data;

    public HttpRequest(HttpRequestHeader header, String data) {
        this.header = header;
        this.data = data;

        if("post".equalsIgnoreCase(getMethod())){
            String[] params=data.split("&");
            for (String param : params) {
                if(!param.isEmpty()) {
                    String[] kv = param.split("=");
                    header.putParam(kv[0], kv[1]);
                }
            }
        }
    }

    public HttpRequestHeader getHeader() {
        return header;
    }

    public String getData() {
        return data;
    }

    public String getMethod() {
        return header.getMethod();
    }

    public String getUri() {
        return header.getUri();
    }

    public String getVersion() {
        return header.getVersion();
    }

    public String getParam(String key){
        return header.getParam(key);
    }

    public String getAttribute(String key){
        return header.getAttribute(key);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(header.toString());
        if(data!=null) sb.append(data);
        return sb.toString();
    }

}
