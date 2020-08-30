package com.lottolike.jaery.lotto.lotto.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lottolike.jaery.lotto.lotto.domain.LottoRankInfo;
import com.lottolike.jaery.lotto.lotto.model.BasicItem;
import com.lottolike.jaery.lotto.lotto.model.LottoListItem;

import java.util.ArrayList;

/**
 * Created by jaery on 2018-05-04.
 */

public class LottoDB {

    public static LottoDB instance = null;

    private static final int DATABASE_VERSION = 5;
    private static SQLiteDatabase myListDB;
    private DatabaseHelper mDBHelper;

    private LottoDB() {}

    public static LottoDB getInstance(Context context) {
        synchronized (context) {
            if (instance == null) {
                instance = new LottoDB();
                instance.open(context);
            }
        }
        return instance;
    }

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

    public void myListInsert(String numbers) {

        myListDB.execSQL("INSERT INTO " + MyListTable._TABLENAME + " (number, level, money, correct) VALUES ('" + numbers + "', -1, '0', '');");   // string은 값은 '이름' 처럼 따음표를 붙여줘야함
    }


    public ArrayList<BasicItem> getMyList() {

        ArrayList<BasicItem> items = new ArrayList<>();

        Cursor cursor = myListDB.rawQuery("select * from " + MyListTable._TABLENAME + " ORDER BY level asc", null);

        while (cursor.moveToNext()) {

            ArrayList<Integer> integers = new ArrayList<>();

            String correct = cursor.getString(5);
            if (!correct.equals("")) {
                String[] corrects = correct.split(",");

                for (int i = 0; i < corrects.length; i++) {
                    int n = Integer.parseInt(corrects[i]);
                    integers.add(n);
                }
            }
            items.add(new LottoListItem(1, cursor.getInt(0), cursor.getString(4), cursor.getInt(3), cursor.getString(1), integers));

        }
        cursor.close();
        return items;
    }



    //리스트의 모든 번호에 대해서 체크
    public void myListCheck(String correct, ArrayList<LottoRankInfo> rankInfo) {
        Cursor cursor = myListDB.rawQuery("select * from " + MyListTable._TABLENAME, null);

        String money = "0원";
        ArrayList<Integer> correctList = new ArrayList<>();
        String[] correctArray = correct.split(",");

        for (int index = 0; index < 5; index++) {
            correctList.add(Integer.parseInt(correctArray[index]));
        }
        correctList.add(Integer.parseInt(correctArray[correctArray.length - 1].split("[+]")[0]));

        int bonus = Integer.parseInt(correctArray[correctArray.length - 1].split("[+]")[1]);


        while (cursor.moveToNext()) {
            int primary_key = cursor.getInt(0);
            int level = 6; //6등
            int correctScore = 0;
            String correctString = "";
            boolean bonusCheck = false;

            String myNumber = cursor.getString(1);
            String[] myNumbers = myNumber.split(",");

            for (int i = 0; i < myNumbers.length; i++) {
                int number = Integer.parseInt(myNumbers[i]);
                if (correctList.contains(number)) {
                    if (correctScore != 0) correctString += ",";
                    correctScore++;
                    correctString += i;
                } else if (number == bonus) {
                    if (correctScore != 0) correctString += ",";
                    correctString += i;
                    bonusCheck = true;
                }
            }

            if (correctScore == 3) {
                level = 5;
                money = "5000원";
            } else if (correctScore == 4) {
                level = 4;
                money = "50000원";
            } else if (correctScore == 5) {
                if (bonusCheck) {
                    level = 2;
                    money = rankInfo.get(1).getMoney() + "원";
                } else {
                    level = 3;
                    money = rankInfo.get(2).getMoney() + "원";
                }
            } else if (correctScore == 6) {
                level = 1;
                money = rankInfo.get(0).getMoney() + "원";
            } else {
                money = "0원";
            }
            myListDB.execSQL("UPDATE " + MyListTable._TABLENAME + " SET level=" + level + ",money='" + money + "',correct='" + correctString + "' where id=" + primary_key + ";");
        }

        cursor.close();
    }

    public LottoDB open(Context context) throws SQLException {

        mDBHelper = new DatabaseHelper(context, MyListTable._TABLENAME + ".db", null, DATABASE_VERSION);
        myListDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close() { //3개 table 쓰기 모드 종료
        if (myListDB != null) {
            myListDB.close();
        }
    }

}