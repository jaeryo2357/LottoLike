package com.example.jaery.rotto.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by jaery on 2018-05-04.
 */




public class LottoDB {


    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase LottoDB;

    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(LottoTable._CREATE);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+LottoTable._TABLENAME);

            onCreate(db);
        }


    }

    public LottoDB(Context context){
        this.mCtx = context;
    }



    public boolean LottoInsert(String date,int N1,int N2,int N3,int N4,int N5,int N6,int winner,int bonusNo,int drwNo) {

            LottoDB.execSQL("INSERT INTO " + LottoTable._TABLENAME + " VALUES ('" + date + "'," + N1 + ","+N2+","+N3+","+N4+","+N5+","+N6+","+winner+","+bonusNo+","+drwNo+");");   // string은 값은 '이름' 처럼 따음표를 붙여줘야함
            return true;

    }


    public LottoDB open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, LottoTable._TABLENAME+".db", null, DATABASE_VERSION);
        LottoDB = mDBHelper.getWritableDatabase();

        return this;
    }

    public void close(){ //3개 table 쓰기 모드 종료
        LottoDB.close();

    }

}