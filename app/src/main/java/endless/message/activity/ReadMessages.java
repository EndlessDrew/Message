package endless.message.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取信息的内容
 * Created by z on 2016/9/9.
 */
public class ReadMessages {
    private Activity activity;
    private Uri  uri;
    List<GetMassages> getMassages;

    /**
     *
     *
     * @param uri
     */
    public ReadMessages( Activity activity,Uri uri){
        getMassages=new ArrayList<GetMassages>();
        this.activity=activity;
        this.uri=uri;
    }

    /**
     * 读取短信的各种内容
     * @return
     */
    public List<GetMassages> MassageType(){
        String[] projection =new String[]{
           "_id",   //id
           "address", //地址
           "person",  //姓名
           "body" , //信息主体
           "date",//日期
           "type"
        };
        Cursor cursor = activity.managedQuery(uri, projection, null, null,
                "date desc");
        int nameColumn = cursor.getColumnIndex("person");
        int phoneNumberColumn = cursor.getColumnIndex("address");
        int smsbodyColumn = cursor.getColumnIndex("body");
        int dateColumn = cursor.getColumnIndex("date");
        int typeColumn = cursor.getColumnIndex("type");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                GetMassages smsinfo = new GetMassages();
                smsinfo.setName(cursor.getString(nameColumn));
                smsinfo.setDate(cursor.getString(dateColumn));
                smsinfo.setPhoneNumber(cursor.getString(phoneNumberColumn));
                smsinfo.setSmsbody(cursor.getString(smsbodyColumn));
                smsinfo.setType(cursor.getString(typeColumn));
                getMassages.add(smsinfo);
            }
//            cursor.close();
        }
        return getMassages;
    }
    }





