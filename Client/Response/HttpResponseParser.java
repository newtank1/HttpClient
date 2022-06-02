package Client.Response;

import java.io.IOException;
import java.io.InputStream;

public interface HttpResponseParser {
    String CRLF="\r\n";
    String CRLFCRLF = "\r\n\r\n";
    HttpResponse parseResponse(InputStream inputStream) throws IOException;
}
