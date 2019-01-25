package by.bsu.orienteering.service;

import by.bsu.orienteering.model.PersonResult;
import by.bsu.orienteering.model.Source;
import by.bsu.orienteering.parser.IParser;

import java.util.List;

/**
 * Created by alexey.memelov on 21-Dec-18.
 */
public class OBelarusParserTest {

    private static String test = "   1 Монич Денис               Немига-Норд          КМС   488 1989 00:46:31      1 I       \n" +
            "   2 Шамшур Дмитрий            Лично                КМС   384 1985 00:47:05      2 I       \n" +
            "   3 Мелеховец Павел           БГУ                  КМС   100 1991 00:50:44      3 I       \n" +
            "   4 Каржов Павел              Азимут Спартак       МС     26 1992 00:51:16      4 I       \n" +
            "   5 Алексеенок Алексей        Эридан               МС    607 1988 00:51:37      5 I       \n" +
            "   6 Астровлянчик Сергей       БГУ                  КМС    86 1985 00:52:07      6 I       \n" +
            "   7 Мельник Тарас UKR         Volyn-O МЦТСК UKR    КМС    21 1993 00:53:00      7 -       \n" +
            "   8 Цяк Александр             Берестье             II    148 1985 00:53:08      8 I       \n" +
            "   9 Витюк Виктор UKR          Volyn-O UKR                  8 1993 00:53:32      9 -       \n" +
            "  10 Стасевич Игорь            Берестье             КМС   145 1990 00:55:44     10 I       \n" +
            "  11 Горбатовский Антон        СОЖ                  КМС   545 1979 00:56:29     11 I       \n" +
            "  12 Федоришкин Александр      Алькор               МС     35 1988 00:56:47     12 I       \n" +
            "  13 Мемелов Алексей           БГУ                  КМС   103 1988 00:57:15     13 I       \n" +
            "  14 Окушко Константин         СОЖ                  I     555 1983 00:58:10     14 I       \n" +
            "  15 Сердитов Вадим            БНТУ                 КМС   212 1993 00:58:52     15 I       \n" +
            "  16 Дубровский Виталий        СОЖ                  КМС   546 1985 00:59:26     16 I       \n" +
            "  17 Ходан Александр           Немига-Норд          КМС   490 1988 00:59:26     16 I       \n" +
            "  18 Масловский Дмитрий        Гомельская область   II    307 1989 00:59:49     18 I       \n" +
            "  19 Шванц Алексей             БГУ                  I     118 1991 01:00:51     19 II      \n" +
            "  20 Марченко Андрей           СОЖ                  I     553 1983 01:08:37     20 II      \n" +
            "  21 Шкраба Евгений            БГУ                  КМС   121 1991 01:11:12     21 III     \n" +
            "  22 Залещук Роман             Берестье             КМС   135 1989 01:16:16     22 III     \n" +
            "  23 Михайлюков Михаил         СОЖ                  I     554 1983 01:37:44     23 -       \n" +
            "  24 Тарасюк Вадим             БГУ                  II    111 1992 01:39:43     24 -       \n" +
            "  25 Федоришкин Вячеслав       Баклан               КМС    70 1983 снят            -       \n" +
            "  26 Сынков Михаил             Березино Минск       КМС   196 1994 снят            -       \n" +
            "  27 Стрельцов Василий         БНТУ                 КМС   214 1990 снят            -       \n" +
            "  28 Салин Андрей              Эридан               МС    619 1988 снят            -       ";


    public static void main(String[] args) {
        IParser parser = ParserFactory.getInstance(Source.OBELARUS);
        List<PersonResult> personResults = parser.parseResults(test);
        System.out.println(personResults.size());
    }



}