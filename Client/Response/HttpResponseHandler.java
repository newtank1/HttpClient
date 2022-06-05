package Client.Response;


import Client.Cache.Content;
import Client.HttpClient;
import Client.Utils.DateUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public class HttpResponseHandler {
    public void handleResponse(HttpResponse response){
        System.out.println("----response start----");
        System.out.println(response);
        byte[] data=response.getData();
        if(response.getStatus()==200&&Content.isFile(response.getUri())){
            long lastModified=System.currentTimeMillis();
            try {
                DateUtil dateUtil=new DateUtil();
                lastModified = dateUtil.dateToLong(response.getAttribute("last-modified"));
            }catch (ParseException ignored) {

            }
            Content content=new Content(response.getUri(),lastModified);
            try {
                content.createContent(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("----response end----");
        if(response.getStatus()==301||response.getStatus()==302){
            String location=response.getAttribute("location");
            String requestData="GET "+location+" "+response.getVersion()+"\r\n\r\n";
            HttpClient httpClient = new HttpClient(new ByteArrayInputStream(requestData.getBytes(StandardCharsets.UTF_8)));
            httpClient.go();
        }
    }
}
