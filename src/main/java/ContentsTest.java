import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentsTest {

    //チュートリアルテキストの整合性テスト
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

    //正解数値設定テスト
    @Test
    void testGame() {
        int[] actual = Contents.game();
        assertTrue(actual[0] != actual[1]);
        assertTrue(actual[0] != actual[2]);
        assertTrue(actual[1] != actual[2]);
        assertEquals(3, actual.length);
    }

    //ヒット数0テスト
    @Test
    void testGetHit0() {
        int expect = 0;
        int[] answer = {0, 1, 2};
        int[] input = {3, 4, 5};
        int actual = Contents.getHit(answer, input, 0);
        assertEquals(expect, actual);
    }

    //ヒット数1テスト
    @Test
    void testGetHit1() {
        int expect = 1;
        int[] answer = {0, 1, 2};
        int[] input = {0, 3, 4};
        int actual = Contents.getHit(answer, input, 0);
        assertEquals(expect, actual);
    }

    //ヒット数2テスト
    @Test
    void testGetHit2() {
        int expect = 2;
        int[] answer = {0, 1, 2};
        int[] input = {0, 1, 3};
        int actual = Contents.getHit(answer, input, 0);
        assertEquals(expect, actual);
    }

    //ヒット数3テスト
    @Test
    void testGetHit3() {
        int expect = 3;
        int[] answer = {0, 1, 2};
        int[] input = {0, 1, 2};
        int actual = Contents.getHit(answer, input, 0);
        assertEquals(expect, actual);
    }

    //ブロー数0テスト
    @Test
    void testGetBlow0() {
        int expect = 0;
        int[] answer = {0, 1, 2};
        int[] input = {0, 1, 2};
        int actual = Contents.getBlow(answer, input, 0);
        assertEquals(expect, actual);
    }

    //ブロー数1テスト
    @Test
    void testGetBlow1() {
        int expect = 1;
        int[] answer = {0, 1, 2};
        int[] input = {2, 3, 4};
        int actual = Contents.getBlow(answer, input, 0);
        assertEquals(expect, actual);
    }

    //ブロー数2テスト
    @Test
    void testGetBlow2() {
        int expect = 2;
        int[] answer = {0, 1, 2};
        int[] input = {1, 2, 3};
        int actual = Contents.getBlow(answer, input, 0);
        assertEquals(expect, actual);
    }

    //ブロー数3テスト
    @Test
    void testGetBlow3() {
        int expect = 3;
        int[] answer = {0, 1, 2};
        int[] input = {2, 0, 1};
        int actual = Contents.getBlow(answer, input, 0);
        assertEquals(expect, actual);
    }
}
