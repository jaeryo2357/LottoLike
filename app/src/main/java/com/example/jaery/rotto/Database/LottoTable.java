package com.example.jaery.rotto.Database;

public final class LottoTable {

        public static final String _TABLENAME = "Timer";

        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                        +"date"+" text primary key, "
                        +"N1"+" integer, "
                        +"N2"+" integer, "
                        +"N3"+" integer, "
                        +"N4"+" integer, "
                        +"N5"+" integer, "
                        +"N6"+" integer, "
                        +"winner"+" integer, "   //당첨 금액
                        +"bonusNo"+" integer, "  //보너스 번호
                        +"drwNo"+" integer);";  //회차번호

}