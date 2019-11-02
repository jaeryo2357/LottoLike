package com.example.jaery.rotto.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jaery.rotto.Item.BasicItem;
import com.example.jaery.rotto.Item.List_Item;
import com.example.jaery.rotto.Item.What_DrwN0;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by jaery on 2018-05-04.
 */




public class LottoDB {


    private static final int DATABASE_VERSION = 2;
    public static SQLiteDatabase LottoDB;
    public static SQLiteDatabase MyListDB;
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
            db.execSQL(MyListTable._CREATE);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+LottoTable._TABLENAME);
            db.execSQL("DROP TABLE IF EXISTS "+MyListTable._TABLENAME);
            onCreate(db);
        }


    }

    public LottoDB(Context context){
        this.mCtx = context;
    }



    public void LottoInsert(String date,int N1,int N2,int N3,int N4,int N5,int N6,long winner,int bonusNo,int drwNo) {
        LottoDB.execSQL("INSERT INTO " + LottoTable._TABLENAME + " VALUES ('" + date + "'," + N1 + ","+N2+","+N3+","+N4+","+N5+","+N6+","+winner+","+bonusNo+","+drwNo+");");   // string은 값은 '이름' 처럼 따음표를 붙여줘야함

    }

    public void MyListInsert(String numbers, String time,int drwNo) {

        MyListDB.execSQL("INSERT INTO " + MyListTable._TABLENAME + " (number,time,level,money,correct,drwNo) VALUES ('"+numbers+"','"+time+"',-1,0,'',"+drwNo+");");   // string은 값은 '이름' 처럼 따음표를 붙여줘야함
    }


    public ArrayList<BasicItem> GetMyList(){

        ArrayList<BasicItem> items = new ArrayList<>();
        int drwN0 = 0;


        Cursor cursor= MyListDB.rawQuery("select * from "+MyListTable._TABLENAME+" ORDER BY drwNo desc ",null);

        while (cursor.moveToNext())
        {
            if(drwN0!=cursor.getInt(6))
            {
                drwN0 = cursor.getInt(6);
                items.add(new What_DrwN0(0,drwN0,cursor.getString(2)));
            }

            ArrayList<Integer> integers = new ArrayList<>();

            String correct = cursor.getString(5);
            if(!correct.equals("")) {
                String[] corrects = correct.split(",");

                for (int i = 0; i < corrects.length; i++) {
                    int n = Integer.parseInt(corrects[i]);
                    integers.add(n);
                }
            }
            items.add(new List_Item(1,cursor.getInt(0),cursor.getLong(4),cursor.getInt(3),cursor.getString(1),integers));

        }
        cursor.close();
        return items;
    }


    public HashMap<String,String> GetNo(String drwNo)
    {
        HashMap<String,String> hashMap = new HashMap<>();

        Cursor cursor= LottoDB.rawQuery("select * from "+LottoTable._TABLENAME+" where drwNo ='"+drwNo+"'",null);

        if(cursor.moveToNext()){
            hashMap.put("date",cursor.getString(0));//날짜
            hashMap.put("N1",""+cursor.getInt(1));//번호1
            hashMap.put("N2",""+cursor.getInt(2));//번호2
            hashMap.put("N3",""+cursor.getInt(3));//번호3
            hashMap.put("N4",""+cursor.getInt(4));//번호4
            hashMap.put("N5",""+cursor.getInt(5));//번호5
            hashMap.put("N6",""+cursor.getInt(6));//번호6
            hashMap.put("winner",""+cursor.getLong(7));//1등 당첨금액
            hashMap.put("bonusNo",""+cursor.getInt(8));//보너스 번호
        }
        cursor.close();
        return hashMap;
    }

    public String[] GetList(){

        ArrayList<String> arrayList =  new ArrayList<>();

        for(int i=BasicDB.getRottoN(mCtx);i>=200;i--)
        {
            Cursor cursor= LottoDB.rawQuery("select * from "+LottoTable._TABLENAME+" where drwNo ='"+i+"'",null);

            if(cursor.moveToNext()){
                arrayList.add(i+"회 ("+cursor.getString(0)+")");//날짜

            }else
                break;
        }

        return arrayList.toArray(new String[]{});

    }

    public void MyListCheck(ArrayList<Integer> integers,long money,int bonus,int drwNo)
    {


        Cursor cursor= MyListDB.rawQuery("select * from "+MyListTable._TABLENAME+" where drwNo= '"+drwNo+"'",null);

        while (cursor.moveToNext())
        {
            int primary_key = cursor.getInt(0);
            int level = 6; //6등
            int correctScore = 0;
            String correctString = "";
            boolean bonusCheck =false;
           String number= cursor.getString(1);
           String[] numbers = number.split(",");
           for(int i=0;i<numbers.length;i++)
           {
               int n =Integer.parseInt(numbers[i]);
               if(integers.contains(n)) {
                   if(correctScore!=0)correctString +=",";
                   correctScore++;
                   correctString += i;
               }else if(n==bonus)
               {
                   if(correctScore!=0)correctString +=",";
                   correctString += i;
                   bonusCheck = true;
               }
           }

            if(correctScore==3){
                level=5;
                money = 5000;
            }
            else if(correctScore==4){
                level=4;
                money = 50000;
            }
            else if(correctScore==5)
            {
                if(bonusCheck)
                {
                    level=2;
                    money = 49815170;
                } else
                {
                    level =3;
                    money = 1524722;
                }
            }
            else if(correctScore==6)
            {
                level=1;
            }else
            {
                money=0;
            }

            MyListDB.execSQL("UPDATE "+MyListTable._TABLENAME+" SET level="+level+",money="+money+",correct='"+correctString+"' where id="+primary_key+";");
        }



        cursor.close();
    }


    public LottoDB open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, LottoTable._TABLENAME+".db", null, DATABASE_VERSION);
        LottoDB = mDBHelper.getWritableDatabase();

        mDBHelper = new DatabaseHelper(mCtx, MyListTable._TABLENAME+".db", null, DATABASE_VERSION);
        MyListDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){ //3개 table 쓰기 모드 종료
        LottoDB.close();
        MyListDB.close();
    }

}