package com.lottolike.jaery.lotto.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lottolike.jaery.lotto.data.OfficialLottoData;
import com.lottolike.jaery.lotto.data.UserLottoData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jaery on 2018-05-04.
 */

public class LottoDB {

    public static LottoDB instance = null;

    //version 6 : 당첨번호를 파싱해서 받자
    private static final int DATABASE_VERSION = 6;
    private static SQLiteDatabase myListDB;
    private DatabaseHelper mDBHelper;

    private LottoDB() {}

    public static LottoDB getInstance(Context context) {
        if (instance == null) {
            synchronized (LottoDB.class) {
                if (instance == null) {
                    instance = new LottoDB();
                    instance.open(context);
                }
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

    public void myListInsert(ArrayList<Integer> selectedNumber) {
        String numbers = selectedNumber.toString();
        numbers = numbers.substring(1, numbers.length() - 1).replaceAll(" ", "");
        myListDB.execSQL("INSERT INTO " + MyListTable._TABLENAME + " (number, level, money) VALUES ('" + numbers + "', -1, '0');");   // string은 값은 '이름' 처럼 따음표를 붙여줘야함
    }


    public ArrayList<UserLottoData> getMyList() {

        ArrayList<UserLottoData> items = new ArrayList<>();

        Cursor cursor = myListDB.rawQuery("select * from " + MyListTable._TABLENAME + " ORDER BY level asc", null);

        while (cursor.moveToNext()) {

            items.add(new UserLottoData(cursor.getInt(0), cursor.getInt(3), cursor.getString(4), cursor.getString(1)));
        }
        cursor.close();
        return items;
    }



    //리스트의 모든 번호에 대해서 체크
    public void myListCheck(List<OfficialLottoData> data) {
        Cursor cursor = myListDB.rawQuery("select * from " + MyListTable._TABLENAME, null);

        String money = "0원";

        int bonus = data.get(0).getBonusNumber();
        String[] correctArray = data.get(0).getOfficialLottoNumber().split(",");


        while (cursor.moveToNext()) {
            int primary_key = cursor.getInt(0);
            int level = 6; //6등
            int correctScore = 0;
            boolean bonusCheck = false;

            String myNumber = cursor.getString(1);
            String[] myNumbers = myNumber.split(",");

            for (int i = 0; i < myNumbers.length; i++) {
                int number = Integer.parseInt(myNumbers[i]);
                int position = Arrays.binarySearch(correctArray, myNumbers[i], new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return Integer.parseInt(o1) - Integer.parseInt(o2);
                    }
                });
                if (position != -1) {
                    correctScore++;
                } else if (number == bonus) {
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
                    money = data.get(1).getVictoryMoney() + "원";
                } else {
                    level = 3;
                    money = data.get(2).getVictoryMoney() + "원";
                }
            } else if (correctScore == 6) {
                level = 1;
                money = data.get(0).getVictoryMoney() + "원";
            } else {
                money = "0원";
            }
            myListDB.execSQL("UPDATE " + MyListTable._TABLENAME + " SET level=" + level + ",money='" + money + "' where id=" + primary_key + ";");
        }

        cursor.close();
    }

    public LottoDB open(Context context) throws SQLException {

        mDBHelper = new DatabaseHelper(context, MyListTable._TABLENAME + ".db", null, DATABASE_VERSION);
        myListDB = mDBHelper.getWritableDatabase();
        return this;
    }
}