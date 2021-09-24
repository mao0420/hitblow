import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ContentsTest {

    private final StandardInputStream in = new StandardInputStream();
    private final StandardOutputStream out = new StandardOutputStream();

    @BeforeEach
    public void before() {
        System.setIn(in);
    }

    @AfterEach
    public void after() {
        System.setIn(null);
        System.setOut(null);
    }

    //ルール説明メソッド ルール説明表示時テスト
    @Test
    void testRuleDescriptionRuleDisplay() {
        in.inputln("1");
        System.setOut(out);
        Contents.ruleDescription(0);
        testGameEndReadLine(24);
        MatcherAssert.assertThat(out.readLine(), is(Constants.TEST_MESSAGE_START_MESSAGE));
    }

    //ルール説明メソッド ルール説明不要時テスト
    @Test
    void testRuleDescriptionRuleInvisible() {
        in.inputln("2");
        System.setOut(out);
        Contents.ruleDescription(0);
        testGameEndReadLine(3);
        MatcherAssert.assertThat(out.readLine(), is(Constants.TEST_MESSAGE_START_MESSAGE));
    }

    //ルール説明メソッド 異常入力時テスト
    @Test
    void testRuleDescriptionAbnormalEntry() {
        in.inputln("3");
        System.setOut(out);
        Contents.ruleDescription(0);
        testGameEndReadLine(3);
        MatcherAssert.assertThat(out.readLine(), is(Constants.ERROR_CHOOSE_ONE_OR_TWO));
    }

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
    void testCorrectAnswerNumber() {
        int[] actual = Contents.correctAnswerNumber();
        assertNotEquals(actual[0], actual[1]);
        assertNotEquals(actual[0], actual[2]);
        assertNotEquals(actual[1], actual[2]);
        assertEquals(Constants.TEST_ELEMENT_DIGITS, actual.length);
    }

    //数値入力メソッド ギブアップ時テスト
    @Test
    void testNumberEntryGiveUp() {
        in.inputln("G");
        System.setOut(out);
        int[] answer = {0, 1, 2};
        String[][] inputHistory = {{"0"}, {"0"}};
        Contents.numberEntry(0, answer, inputHistory, 0);
        testGameEndReadLine(4);
        String saveLine = String.join("\n", out.readLine(), out.readLine(), out.readLine(), out.readLine(), "\n");
        MatcherAssert.assertThat(saveLine, is(Constants.TEST_MESSAGE_GIVE_UP));
    }

    //数値入力メソッド 数値の長さテスト
    @Test
    void testNumberEntryNumericLength() {
        in.inputln("12");
        in.inputln("2");
        System.setOut(out);
        int[] answer = {0, 1, 2};
        String[][] inputHistory = {{"0"}, {"0"}};
        Contents.numberEntry(0, answer, inputHistory, 4);
        testGameEndReadLine(4);
        MatcherAssert.assertThat(out.readLine(), is(Constants.ERROR_INPUT_THREE_DIGIT));
    }

    //数値入力メソッド 正規表現テスト
    @Test
    void testNumberEntryRegexp() {
        in.inputln("abc");
        in.inputln("2");
        System.setOut(out);
        int[] answer = {0, 1, 2};
        String[][] inputHistory = {{"0"}, {"0"}};
        Contents.numberEntry(0, answer, inputHistory, 4);
        testGameEndReadLine(4);
        MatcherAssert.assertThat(out.readLine(), is(Constants.ERROR_INPUT_THREE_DIGIT));
    }

    //数値入力メソッド 重複確認テスト
    @Test
    void testNumberEntryDuplicate() {
        in.inputln("121");
        in.inputln("2");
        System.setOut(out);
        int[] answer = {0, 1, 2};
        String[][] inputHistory = {{"0"}, {"0"}};
        Contents.numberEntry(0, answer, inputHistory, 4);
        testGameEndReadLine(4);
        MatcherAssert.assertThat(out.readLine(), is(Constants.ERROR_DUPLICATE_NUMBERS));
    }

    //判定メソッド クリア時テスト
    @Test
    void testJudgeGameClear() {
        System.setOut(out);
        int[] inputArray = {0, 1, 2};
        int[] answer = {0, 1, 2};
        String[][] inputHistory = {{"0"}, {"0"}};
        Contents.judge(Constants.TEST_JUDGE_GAME_CLEAR_TRY_TIMES, inputArray, answer, inputHistory, "012");
        testGameEndReadLine(3);
        String saveLine = String.join("\n", out.readLine(), out.readLine(), out.readLine(), "\n");
        MatcherAssert.assertThat(saveLine, is(Constants.TEST_MESSAGE_GAME_CLEAR));
    }

    //判定メソッド 失敗時テスト
    @Test
    void testJudgeGameOver() {
        System.setOut(out);
        int[] inputArray = {3, 4, 5};
        int[] answer = {0, 1, 2};
        String[][] inputHistory = {{"0"}, {"0"}};
        Contents.judge(Constants.TEST_JUDGE_GAME_OVER_TRY_TIMES, inputArray, answer, inputHistory, "012");
        testGameEndReadLine(3);
        String saveLine = String.join("\n", out.readLine(), out.readLine(), out.readLine(), out.readLine(), "\n");
        MatcherAssert.assertThat(saveLine, is(Constants.TEST_MESSAGE_GAME_OVER));
    }

    //ヒットブロー計算メソッド ヒット数0テスト
    @Test
    void testGetHitBlowHit0() {
        int expect = Constants.TEST_HIT_ZERO;
        int[] answer = {0, 1, 2};
        int[] input = {3, 4, 5};
        int[] actual = Contents.getHitBlow(answer, input);
        assertEquals(expect, actual[Constants.CONSTANT_ARRAY_HIT_COUNTER]);
    }

    //ヒットブロー計算メソッド ヒット数1テスト
    @Test
    void testGetHitBlowHit1() {
        int expect = Constants.TEST_HIT_ONE;
        int[] answer = {0, 1, 2};
        int[] input = {0, 3, 4};
        int[] actual = Contents.getHitBlow(answer, input);
        assertEquals(expect, actual[Constants.CONSTANT_ARRAY_HIT_COUNTER]);
    }

    //ヒットブロー計算メソッド ヒット数2テスト
    @Test
    void testGetHitBlowHit2() {
        int expect = Constants.TEST_HIT_TWO;
        int[] answer = {0, 1, 2};
        int[] input = {0, 1, 3};
        int[] actual = Contents.getHitBlow(answer, input);
        assertEquals(expect, actual[Constants.CONSTANT_ARRAY_HIT_COUNTER]);
    }

    //ヒットブロー計算メソッド ヒット数3テスト
    @Test
    void testGetHitBlowHit3() {
        int expect = Constants.TEST_HIT_THREE;
        int[] answer = {0, 1, 2};
        int[] input = {0, 1, 2};
        int[] actual = Contents.getHitBlow(answer, input);
        assertEquals(expect, actual[Constants.CONSTANT_ARRAY_HIT_COUNTER]);
    }

    //ヒットブロー計算メソッド ブロー数0テスト
    @Test
    void testGetHitBlowBlow0() {
        int expect = Constants.TEST_BLOW_ZERO;
        int[] answer = {0, 1, 2};
        int[] input = {0, 1, 2};
        int[] actual = Contents.getHitBlow(answer, input);
        assertEquals(expect, actual[Constants.CONSTANT_ARRAY_BLOW_COUNTER]);
    }

    //ヒットブロー計算メソッド ブロー数1テスト
    @Test
    void testGetHitBlowBlow1() {
        int expect = Constants.TEST_BLOW_ONE;
        int[] answer = {0, 1, 2};
        int[] input = {2, 3, 4};
        int[] actual = Contents.getHitBlow(answer, input);
        assertEquals(expect, actual[Constants.CONSTANT_ARRAY_BLOW_COUNTER]);
    }

    //ヒットブロー計算メソッド ブロー数2テスト
    @Test
    void testGetHitBlowBlow2() {
        int expect = Constants.TEST_BLOW_TWO;
        int[] answer = {0, 1, 2};
        int[] input = {1, 2, 3};
        int[] actual = Contents.getHitBlow(answer, input);
        assertEquals(expect, actual[Constants.CONSTANT_ARRAY_BLOW_COUNTER]);
    }

    //ヒットブロー計算メソッド ブロー数3テスト
    @Test
    void testGetHitBlowBlow3() {
        int expect = Constants.TEST_BLOW_THREE;
        int[] answer = {0, 1, 2};
        int[] input = {2, 0, 1};
        int[] actual = Contents.getHitBlow(answer, input);
        assertEquals(expect, actual[Constants.CONSTANT_ARRAY_BLOW_COUNTER]);
    }

    //数値入力判定ミスメソッド
    @Test
    void testNumberInputMiss() {
        String[][] array = {{"0"}, {"0"}};

        System.setOut(out);

        Contents.numberInputMiss(0, new int[]{0}, array, 4);
        MatcherAssert.assertThat(out.readLine(), is(Constants.ERROR_END_OF_WARNING));
    }

    //ゲーム終了メソッド 終了時テスト
    @Test
    void testGameEndEnd() {
        in.inputln("2");
        System.setOut(out);
        Contents.gameEnd();
        testGameEndReadLine(3);
        MatcherAssert.assertThat(out.readLine(), is(Constants.MESSAGE_GAME_END));
    }

    //ゲーム終了メソッド 異常入力終了時テスト
    @Test
    void testGameEndWarning() {
        in.inputln("3");
        System.setOut(out);
        Contents.gameEnd();
        testGameEndReadLine(3);
        MatcherAssert.assertThat(out.readLine(), is(Constants.ERROR_END_OF_WARNING_2));
    }

    //ゲーム終了メソッド n行飛ばす
    void testGameEndReadLine(int skipLine) {
        for (int i = 0; i < skipLine; i++) {
            out.readLine();
        }
    }
}
