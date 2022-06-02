package Client.Request;

import Client.Exception.BadRequest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public interface HttpRequestBuilder {
    String CRLF="\r\n";
    HttpRequest buildRequest(BufferedReader reader) throws IOException, BadRequest;

    HttpRequest buildRequest(String s) throws BadRequest;
}
