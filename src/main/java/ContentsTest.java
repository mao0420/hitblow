import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContentsTest {
    @Test
    void testReadText() {
        String description = """
                ルール説明を表示します。
                                
                ヒット&ブローはプログラム側がランダムで設定した数字を当てるゲームです。
                                
                このプログラムでは被り無しの0～9の数字が3桁設定されます。
                ユーザー側が3桁の数字を入力し、その数字と正解の数字を比較して次のヒントが表示されます。
                                
                ヒット:桁の位置も数字も合っている数字の数です。
                ブロー:数字は合っているが、桁の位置が違う数字の数です。
                                
                例:正解が[083]入力が[385]の時、
                　 8は桁の位置も数字も合っている為ヒット、3は桁の位置が違うが数字は合っている為ブローとなり、
                　 ヒットとブローの数がそれぞれ1つずつの為、ヒット:1 ブロー:1と表示されます。
                                
                その後、再度数字の入力から繰り返し、3桁全部の数字を当てた場合はゲームクリアです。
                少ない回数でのクリアを目指してください。
                                
                10回目の入力までに正解の数字を見つけられないとゲームオーバーになります。
                また、数値入力時に"G"を入力するとギブアップとしてゲームを終了する事が出来ます。
                                   
                """;
        String resultDescription = Contents.readText("src/main/java/Description.txt");
        assertEquals(description, resultDescription);
    }

//    @Test
//    void testGetBlow() {
//        String testBlow = "[0-3]";
//        int conversionBlow = Contents.getBlow(null,null,0);
//        String resultBlow = String.valueOf(conversionBlow);
//        System.out.print(resultBlow);
//        //assertEquals(testBlow, resultBlow);
//    }
}
