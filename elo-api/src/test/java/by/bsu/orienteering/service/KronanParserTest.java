package by.bsu.orienteering.service;

import by.bsu.orienteering.model.PersonResult;
import by.bsu.orienteering.model.Source;
import by.bsu.orienteering.parser.IParser;

import java.util.List;

/**
 * Created by alexey.memelov on 21-Dec-18.
 */
public class KronanParserTest {

    private static String test =
                    "1\tАлексеёнок Алексей\t88\tМС\tКСО Эридан -\t38:07\tМС\n" +
                            "2\tСалин Андрей\t88\tМС\tКСО Эридан -\t38:33\tМС\n" +
                            "3\tМихалкин Дмитрий\t80\tМС\tКСО Эридан -\t38:46\tМС\n" +
                            "4\tЛаскаржевский Владислав UKR\t82\t\tСК Камволь -\t40:07\t-\n" +
                            "5\tСтрельцов Василий\t90\tМС\tСК Камволь -\t40:32\tКМС\n" +
                            "6\tКрасько Павел\t80\tКМС\tГродненская обла -\t43:14\tКМС\n" +
                            "7\tЖуравлёв Андрей\t78\tКМС\tКСО Немига-Норд -\t44:14\tКМС\n" +
                            "8\tЗлобин Денис\t82\tМС\tСТК Мэта -\t45:21\tКМС\n" +
                            "9\tКривашеев Андрей\t94\tМС\tМогилевская обла -\t46:12\t1\n" +
                            "10\tМиронов Дмитрий\t80\tМС\tМинская область -\t47:42\t1\n" +
                            "11\tНовак Дмитрий\t86\tКМС\tКСО Белая Русь -\t48:06\t1\n" +
                            "12\tФедоришкин Александр\t88\tКМС\tКЛ Баклан -\t48:09\t1\n" +
                            "13\tРогалевич Сергей\t83\tКМС\tКСО Немига-Норд -\t49:16\t1\n" +
                            "14\tВоверис Эдгарас LTU\t67\t\tКО Сильван люкс -\t49:21\t1\n" +
                            "15\tНовиченко Антон\t97\tКМС\tКСО Эридан -\t49:27\t1\n" +
                            "16\tРожков Александр\t97\tМС\tМогилевская обла -\t49:33\t1\n" +
                            "17\tЛычков Игорь\t83\tКМС\tМинская область -\t50:51\t1\n" +
                            "18\tДубровский Виталий\t85\tКМС\tСК Сож -\t51:21\t1\n" +
                            "19\tСолодкин Сергей\t84\tМС\tг. Минск -\t51:23\t1\n" +
                            "20\tГорбачев Николай\t89\tКМС\tСТК Мэта -\t51:48\t2\n" +
                            "21\tЖаховский Евгений\t98\tКМС\tКСО Эридан -\t53:41\t2\n" +
                            "22\tКрюков Дмитрий\t88\tМС\tг. Минск -\t53:51\t2\n" +
                            "23\tАлексеёнок Александр\t74\tКМС\tКСО Эридан -\t54:22\t2\n" +
                            "24\tАфанасьев Иван\t79\tМС\tКСО Эридан -\t54:32\t2\n" +
                            "25\tСынков Михаил\t84\tКМС\tМинская область -\t55:00\t2\n" +
                            "26\tМемелов Алексей\t88\tКМС\tМинская область -\t58:33\t2\n" +
                            "27\tШванц Алексей\t91\tКМС\tГродненская обла -\t58:45\t2\n" +
                            "28\tГойшик Алексей\t95\tКМС\tМинская область -\t1:00:57\t3\n" +
                            "29\tФедоришкин Вячеслав\t83\t1\tКЛ Баклан -\t1:02:49\t3\n" +
                            "30\tМихалкин Сергей\t89\tКМС\tКСО Эридан -\t1:03:08\t3\n" +
                            "31\tАлексеенок Никита\t00\t1\tКСО Эридан -\t1:04:31\t3\n" +
                            "32\tХодан Александр\t88\tКМС\tг. Минск -\t1:08:13\t3\n" +
                            "33\tМальков Валентин\t88\t1\tКО БГУ -\t1:09:13\t3\n" +
                            "34\tПисарчик Сергей\t80\tКМС\tКО БГУ -\t1:29:50\t-\n" +
                            "Мартыненко Александр\t99\t\tГродненская обла -\tснят пп24.4\t-\n" +
                            "Журавлев Вячеслав\t76\tКМС\tКСО Немига-Норд -\tснят пп20.10\t-\n" +
                            "Кухто Петр\t81\tКМС\tКЛ Баклан -\tснят пп20.10\t-\n" +
                            "Марков Виталий\t93\tКМС\tг. Минск -\tснят пп20.10\t-\n" +
                            "Ясюченя Сергей\t90\tКМС\tКСО Немига-Норд -\tснят пп20.10\t";




    public static void main(String[] args) {
        IParser parser = ParserFactory.getInstance(Source.KRONAN);
        List<PersonResult> personResults = parser.parseResults(test);
        System.out.println(personResults.size());
    }



}
