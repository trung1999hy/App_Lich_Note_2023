package com.hst.calander.Alarm;

import android.content.Context;
import android.content.Intent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class Alarm implements Comparable<Alarm> {
    public static final int EVERY_DAY = 127;
    public static final int MONTHLY = 2;
    public static final int NEVER = 0;
    public static final int ONCE = 0;
    public static final int WEEKLY = 1;
    public static final int YEARLY = 3;
    private Context mContext;
    private long mNextOccurence;
    private long mId = 0;
    private String mTitle = "";
    private long mDate = System.currentTimeMillis();
    private boolean mEnabled = true;
    private int mOccurence = 0;
    private int mDays = EVERY_DAY;

    public Alarm(Context context) {
        this.mContext = context;
        update();
    }

    public long getId() {
        return this.mId;
    }

    public void setId(long j) {
        this.mId = j;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public int getOccurence() {
        return this.mOccurence;
    }

    public void setOccurence(int i) {
        this.mOccurence = i;
        update();
    }

    public long getDate() {
        return this.mDate;
    }

    public void setDate(long j) {
        this.mDate = j;
        update();
    }

    public boolean getEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    public int getDays() {
        return this.mDays;
    }

    public void setDays(int i) {
        this.mDays = i;
        update();
    }

    public long getNextOccurence() {
        return this.mNextOccurence;
    }

    public boolean getOutdated() {
        return this.mNextOccurence < System.currentTimeMillis();
    }

    @Override
    public int compareTo(Alarm alarm) {
        long nextOccurence = getNextOccurence();
        long nextOccurence2 = alarm.getNextOccurence();
        if (this == alarm) {
            return 0;
        }
        int i = (nextOccurence > nextOccurence2 ? 1 : (nextOccurence == nextOccurence2 ? 0 : -1));
        if (i > 0) {
            return 1;
        }
        return i < 0 ? -1 : 0;
    }

    public void update() {
        Calendar calendar = Calendar.getInstance();
        if (this.mOccurence == 1) {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(this.mDate);
            calendar2.set(calendar.get(1), calendar.get(2), calendar.get(5));
            if (this.mDays != 0) {
                while (true) {
                    int i = (calendar2.get(7) + 5) % 7;
                    if (calendar2.getTimeInMillis() > calendar.getTimeInMillis()) {
                        if (((1 << i) & this.mDays) > 0) {
                            break;
                        }
                    }
                    calendar2.add(5, 1);
                }
            } else {
                calendar2.add(1, 10);
            }
            this.mNextOccurence = calendar2.getTimeInMillis();
        } else {
            this.mNextOccurence = this.mDate;
        }
        if (this.mOccurence == 1) {
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTimeInMillis(this.mDate);
            calendar3.set(calendar.get(1), calendar.get(2), calendar.get(5));
            if (this.mDays != 0) {
                while (true) {
                    int i2 = (calendar3.get(7) + 5) % 7;
                    if (calendar3.getTimeInMillis() > calendar.getTimeInMillis()) {
                        if (((1 << i2) & this.mDays) > 0) {
                            break;
                        }
                    }
                    calendar3.add(5, 1);
                }
            } else {
                calendar3.add(1, 10);
            }
            this.mNextOccurence = calendar3.getTimeInMillis();
        } else {
            this.mNextOccurence = this.mDate;
        }
        this.mDate = this.mNextOccurence;
    }

    public void toIntent(Intent intent) {
        intent.putExtra("epic.brazil.calendar.alarm.id", this.mId);
        intent.putExtra("epic.brazil.calendar.alarm.title", this.mTitle);
        intent.putExtra("epic.brazil.calendar.alarm.date", this.mDate);
        intent.putExtra("epic.brazil.calendar.alarm.alarm", this.mEnabled);
        intent.putExtra("epic.brazil.calendar.alarm.occurence", this.mOccurence);
        intent.putExtra("epic.brazil.calendar.alarm.days", this.mDays);
    }

    public void fromIntent(Intent intent) {
        this.mId = intent.getLongExtra("epic.brazil.calendar.alarm.id", 0L);
        this.mTitle = intent.getStringExtra("epic.brazil.calendar.alarm.title");
        this.mDate = intent.getLongExtra("epic.brazil.calendar.alarm.date", 0L);
        this.mEnabled = intent.getBooleanExtra("epic.brazil.calendar.alarm.alarm", true);
        this.mOccurence = intent.getIntExtra("epic.brazil.calendar.alarm.occurence", 0);
        this.mDays = intent.getIntExtra("epic.brazil.calendar.alarm.days", 0);
        update();
    }

    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeLong(this.mId);
        dataOutputStream.writeUTF(this.mTitle);
        dataOutputStream.writeLong(this.mDate);
        dataOutputStream.writeBoolean(this.mEnabled);
        dataOutputStream.writeInt(this.mOccurence);
        dataOutputStream.writeInt(this.mDays);
    }

    public void deserialize(DataInputStream dataInputStream) throws IOException {
        this.mId = dataInputStream.readLong();
        this.mTitle = dataInputStream.readUTF();
        this.mDate = dataInputStream.readLong();
        this.mEnabled = dataInputStream.readBoolean();
        this.mOccurence = dataInputStream.readInt();
        this.mDays = dataInputStream.readInt();
        update();
    }
}
