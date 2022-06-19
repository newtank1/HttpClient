package Client.Cache;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
* 表示缓存到本地的文件内容的类
* */
public class Content {
    public final String CACHE_PATH="./cache/";
    private String name;
    private long lastModified;//最近修改时间

    public Content (String name, long lastModified) {
        this.name = name;
        this.lastModified = lastModified;
    }

    public Content(String name) {
        this.name = name;
        if(exists()){
            lastModified=getLastModified();
        }
    }

    private static final List<String> fileTypes=new ArrayList<>(){{
        add(".txt");
        add(".png");
        add(".html");
        add(".js");
        add(".css");
        add(".ico");
    }};

    public static boolean isFile(String url){
        for (String fileType : fileTypes) {
            if(url.toLowerCase().endsWith(fileType)) return true;
        }
        return false;
    }

    public InputStream getContentBytes() throws FileNotFoundException {
        File file=new File(CACHE_PATH+name);
        return new FileInputStream(file);
    }

    public void createContent(byte[] bytes) throws IOException {//创建文件并设置最近修改时间
        File file=new File(CACHE_PATH+name);
        if(!file.exists())
            file.createNewFile();
        OutputStream os=new FileOutputStream(file);
        os.write(bytes);
        os.flush();
        os.close();
        boolean succ=file.setLastModified(lastModified);
    }

    public boolean exists(){
        return new File(CACHE_PATH+name).exists();
    }

    public long getLastModified(){
        return new File(CACHE_PATH+name).lastModified();
    }
}
