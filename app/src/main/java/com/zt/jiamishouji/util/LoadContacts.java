package com.zt.jiamishouji.util;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.zt.jiamishouji.bean.MyContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/9 0009.
 */
public class LoadContacts extends AsyncTask<Void,Void,List<MyContacts>> {

    private Context context;
    private CallbackContacts callbackContacts;

    public LoadContacts(Context context, CallbackContacts callbackContacts) {
        this.context = context;
        this.callbackContacts = callbackContacts;
    }

    @Override
    protected List<MyContacts> doInBackground(Void... params) {
        //联系人列表
        ArrayList<MyContacts> myContactses = new ArrayList<>();
        Cursor cursor = context.getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{"_id", "display_name", "data1"}, null, null, null);
        while(cursor.moveToNext())
        {
            MyContacts myContacts = new MyContacts();
            String display_name = cursor.getString(cursor.getColumnIndex("display_name"));
            String data1 = cursor.getString(cursor.getColumnIndex("data1"));
            myContacts.setName(display_name);
            myContacts.setPhone(data1);
            myContactses.add(myContacts);
        }
        return myContactses;
    }

    @Override
    protected void onPostExecute(List<MyContacts> myContactses) {
        super.onPostExecute(myContactses);
        if(myContactses!=null&&myContactses.size()>0)
        {
            callbackContacts.getContacts(myContactses);
        }
    }

    public interface CallbackContacts
    {
        public void getContacts(List<MyContacts> result);
    }
}
