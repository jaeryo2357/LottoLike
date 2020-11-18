package com.lottolike.jaery.lotto.data.userlottodata.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.userlottodata.UserLottoData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jaery on 2018-05-04.
 */

public class LottoDBHelper {

    private static final String TABLE_NAME = "UserLottoDataList";

    private static final String TABLE_CREATE =
            "create table " + TABLE_NAME + "("
                    + "id" + " integer primary key AUTOINCREMENT, "
                    + "number" + " text,"
                    + "time" + " text,"
                    + "level" + " integer,"    //등수
                    + "money" + " text); ";   //당첨 금액


    public static LottoDBHelper instance = null;

    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase myListDB;

    private LottoDBHelper() {}

    public static LottoDBHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (LottoDBHelper.class) {
                if (instance == null) {
                    instance = new LottoDBHelper();
                    instance.open(context);
                }
            }
        }

        return instance;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        private DatabaseHelper(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }


    }

    public void insertLottoUserData(@NotNull  UserLottoData data) {
        String numbers = data.getUserNumbers();
        myListDB.execSQL("INSERT INTO " + TABLE_NAME + " (number, level, money) VALUES ('" + numbers + "', -1, '0');");   // string은 값은 '이름' 처럼 따음표를 붙여줘야함
    }


    public List<UserLottoData> getUserLottoDataList() {

        ArrayList<UserLottoData> items = new ArrayList<>();

        Cursor cursor = myListDB.rawQuery("select * from " + TABLE_NAME + " ORDER BY level asc", null);

        while (cursor.moveToNext()) {

            items.add(new UserLottoData(cursor.getInt(0), cursor.getInt(3), cursor.getString(4), cursor.getString(1)));
        }
        cursor.close();
        return items;
    }



    //리스트의 모든 번호에 대해서 체크
    public void calculateUserLottoDataList(List<OfficialLottoMainData> data) {
        Cursor cursor = myListDB.rawQuery("select * from " + TABLE_NAME, null);

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
            myListDB.execSQL("UPDATE " + TABLE_NAME + " SET level=" + level + ",money='" + money + "' where id=" + primary_key + ";");
        }

        cursor.close();
    }

    public LottoDBHelper open(Context context) throws SQLException {

        DatabaseHelper mDBHelper = new DatabaseHelper(context, TABLE_NAME + ".db", null, DATABASE_VERSION);
        myListDB = mDBHelper.getWritableDatabase();
        return this;
    }
}