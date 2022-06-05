package Client.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    public static final String DATE_FORMAT_A="EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String DATE_FORMAT_B="EEEEEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String DATE_FORMAT_C="EEE MMM dd HH:mm:ss yyyy";
    private String useDateFormat=DATE_FORMAT_A;
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }
    public String longToDate(long time){
        Date date=new Date(time);
        DateFormat dateFormat=new SimpleDateFormat(useDateFormat, Locale.ENGLISH);
        String ret= dateFormat.format(date);
        if(DATE_FORMAT_C.equals(useDateFormat)&&ret.charAt(8)=='0'){
            char[] chars=ret.toCharArray();
            chars[8]=' ';
            return String.valueOf(chars);
        }
        return ret;
    }

    public long dateToLong(String date) throws ParseException {
        try {
            return new SimpleDateFormat(DATE_FORMAT_A,Locale.ENGLISH).parse(date).getTime();
        } catch (ParseException e) {
            try {
                return new SimpleDateFormat(DATE_FORMAT_B,Locale.ENGLISH).parse(date).getTime();
            } catch (ParseException ex) {
                return new SimpleDateFormat(DATE_FORMAT_C,Locale.ENGLISH).parse(date).getTime();
            }
        }
    }

    public String getFormat(){
        return useDateFormat;
    }
    public void setFormat(String format){
        useDateFormat=format;
    }
    public static void main(String[] args) throws ParseException {
        long time = System.currentTimeMillis();
        DateUtil dateUtil=new DateUtil();
        System.out.println(dateUtil.longToDate(time));
        Date date=new SimpleDateFormat(dateUtil.useDateFormat, Locale.ENGLISH).parse(dateUtil.longToDate(time));
        System.out.println(date);
    }
}
