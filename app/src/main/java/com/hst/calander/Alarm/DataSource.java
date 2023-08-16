package com.hst.calander.Alarm;

import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class DataSource {
    private static final String DATA_FILE_NAME = "alarmme.txt";
    private static final long MAGIC_NUMBER = 6080266734549300801L;
    private static final String TAG = "AlarmMe";
    private static long mNextId;
    private static final DataSource mDataSource = new DataSource();
    private static Context mContext = null;
    private static ArrayList<Alarm> mList = null;

    protected DataSource() {
    }

    public static synchronized DataSource getInstance(Context context) {
        DataSource dataSource;
        synchronized (DataSource.class) {
            if (mContext == null) {
                mContext = context.getApplicationContext();
                load();
            }
            dataSource = mDataSource;
        }
        return dataSource;
    }

    private static void load() {
        Log.i(TAG, "DataSource.load()");
        mList = new ArrayList<>();
        mNextId = 1L;
        try {
            DataInputStream dataInputStream = new DataInputStream(mContext.openFileInput(DATA_FILE_NAME));
            if (MAGIC_NUMBER == dataInputStream.readLong()) {
                mNextId = dataInputStream.readLong();
                int readInt = dataInputStream.readInt();
                for (int i = 0; i < readInt; i++) {
                    Alarm alarm = new Alarm(mContext);
                    alarm.deserialize(dataInputStream);
                    mList.add(alarm);
                }
            }
            dataInputStream.close();
        } catch (IOException unused) {
        }
    }

    public static void save() {
        Log.i(TAG, "DataSource.save()");
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(mContext.openFileOutput(DATA_FILE_NAME, 0));
            dataOutputStream.writeLong(MAGIC_NUMBER);
            dataOutputStream.writeLong(mNextId);
            dataOutputStream.writeInt(mList.size());
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).serialize(dataOutputStream);
            }
            dataOutputStream.close();
        } catch (IOException unused) {
        }
    }

    public static int size() {
        return mList.size();
    }

    public static Alarm get(int i) {
        return mList.get(i);
    }

    public static void add(Alarm alarm) {
        long j = mNextId;
        mNextId = 1 + j;
        alarm.setId(j);
        mList.add(alarm);
        Collections.sort(mList);
        save();
    }

    public static void remove(int i) {
        mList.remove(i);
        save();
    }

    public static void update(Alarm alarm) {
        alarm.update();
        Collections.sort(mList);
        save();
    }
}
