package Client.Request;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestSenderImpl implements HttpRequestSender{
    @Override
    public void send(HttpRequest request, Socket socket) throws IOException {
        OutputStream stream = socket.getOutputStream();
        stream.write(request.toString().getBytes(StandardCharsets.UTF_8));
        stream.flush();
    }
}
