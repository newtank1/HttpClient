package Client.Request;

import java.io.IOException;
import java.net.Socket;

public interface HttpRequestSender {
    void send(HttpRequest request, Socket socket) throws IOException;
}
