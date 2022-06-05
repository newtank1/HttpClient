package Client.Response;


import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class HttpResponseHeader {
    String version;
    String status;
    int statusCode;
    Map<String,String> attributes=new HashMap<>();

    public final Map<Integer,String> statusMap=new HashMap<>(){{
        put(200,"OK");
        put(301,"FOUND");
        put(302,"MOVED PERMANENTLY");
        put(304,"NOT MODIFIED");
        put(404,"NOT FOUND");
        put(400,"BAD REQUEST");
        put(405,"UNSUPPORTED METHOD");
        put(500,"SERVER ERROR");
    }};

    public HttpResponseHeader(String header) {
        String[] lines = header.split(HttpResponseParser.CRLF);
        String[] firstLineTokens = lines[0].split(" ",3);
        version = firstLineTokens[0];
        statusCode = Integer.parseInt(firstLineTokens[1]);
        status = firstLineTokens[2];
        for (int i = 1; i < lines.length; i++) {
            String[] kv = lines[i].split(":", 2);
            attributes.put(kv[0].toLowerCase(), kv[1].strip());
        }
    }


    @Override
    public String toString() {
        StringJoiner stringJoiner=new StringJoiner(HttpResponse.CRLF);
        stringJoiner.add(version+" "+statusCode+" "+status);
        for (String s : attributes.keySet()) {
            stringJoiner.add(s+": "+attributes.get(s));
        }
        return stringJoiner.toString();
    }
    public String getAttribute(String key){
        return attributes.get(key);
    }

    public int getStatus() {
        return statusCode;
    }
}
