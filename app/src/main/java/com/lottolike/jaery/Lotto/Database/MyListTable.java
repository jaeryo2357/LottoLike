package com.lottolike.jaery.Lotto.Database;

public final class MyListTable {

        public static final String _TABLENAME = "MyList";

        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                        +"id"+" integer primary key AUTOINCREMENT, "
                        +"number"+" text,"
                        +"time"+" text,"
                        +"level"+" integer,"    //등수
                        +"money"+" integer, "   //당첨 금액
                        +"correct"+" text, "    //맞는 자리
                        +"drwNo"+" integer);";  //회차번호

}