package com.lottolike.jaery.lotto.lotto.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lottolike.jaery.lotto.model.BasicItem;
import com.lottolike.jaery.lotto.model.List_Item;

import java.util.ArrayList;

/**
 * Created by jaery on 2018-05-04.
 */

public class LottoDB {


    private static final int DATABASE_VERSION = 4;
    private static SQLiteDatabase MyListDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        private DatabaseHelper(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MyListTable._CREATE);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + MyListTable._TABLENAME);
            onCreate(db);
        }


    }

    public LottoDB(Context context) {
        this.mCtx = context;
    }

    public void MyListInsert(String numbers) {

        MyListDB.execSQL("INSERT INTO " + MyListTable._TABLENAME + " (number, level, money, correct) VALUES ('" + numbers + "', -1, 0, '');");   // string은 값은 '이름' 처럼 따음표를 붙여줘야함
    }


    public ArrayList<BasicItem> GetMyList() {

        ArrayList<BasicItem> items = new ArrayList<>();

        Cursor cursor = MyListDB.rawQuery("select * from " + MyListTable._TABLENAME + " ORDER BY level desc", null);

        while (cursor.moveToNext()) {
//            if(drwN0!=cursor.getInt(6))
//            {
//                drwN0 = cursor.getInt(6);
//                items.add(new What_DrwN0(0,drwN0,cursor.getString(2)));
//            }

            ArrayList<Integer> integers = new ArrayList<>();

            String correct = cursor.getString(5);
            if (!correct.equals("")) {
                String[] corrects = correct.split(",");

                for (int i = 0; i < corrects.length; i++) {
                    int n = Integer.parseInt(corrects[i]);
                    integers.add(n);
                }
            }
            items.add(new List_Item(1, cursor.getInt(0), cursor.getLong(4), cursor.getInt(3), cursor.getString(1), integers));

        }
        cursor.close();
        return items;
    }



    //리스트의 모든 번호에 대해서 체크
    public void MyListCheck(ArrayList<Integer> integers, long money, int bonus) {
        Cursor cursor = MyListDB.rawQuery("select * from " + MyListTable._TABLENAME, null);

        while (cursor.moveToNext()) {
            int primary_key = cursor.getInt(0);
            int level = 6; //6등
            int correctScore = 0;
            String correctString = "";
            boolean bonusCheck = false;
            String number = cursor.getString(1);
            String[] numbers = number.split(",");
            for (int i = 0; i < numbers.length; i++) {
                int n = Integer.parseInt(numbers[i]);
                if (integers.contains(n)) {
                    if (correctScore != 0) correctString += ",";
                    correctScore++;
                    correctString += i;
                } else if (n == bonus) {
                    if (correctScore != 0) correctString += ",";
                    correctString += i;
                    bonusCheck = true;
                }
            }

            if (correctScore == 3) {
                level = 5;
                money = 5000;
            } else if (correctScore == 4) {
                level = 4;
                money = 50000;
            } else if (correctScore == 5) {
                if (bonusCheck) {
                    level = 2;
                    money = 49815170;
                } else {
                    level = 3;
                    money = 1524722;
                }
            } else if (correctScore == 6) {
                level = 1;
            } else {
                money = 0;
            }
            MyListDB.execSQL("UPDATE " + MyListTable._TABLENAME + " SET level=" + level + ",money=" + money + ",correct='" + correctString + "' where id=" + primary_key + ";");
        }

        cursor.close();
    }

    public LottoDB open() throws SQLException {

        mDBHelper = new DatabaseHelper(mCtx, MyListTable._TABLENAME + ".db", null, DATABASE_VERSION);
        MyListDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close() { //3개 table 쓰기 모드 종료
        MyListDB.close();
    }

}