import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;
import java.text.Normalizer;

public class Contents {
    static Scanner scan = new Scanner(System.in);

    /**
     * mainメソッド、ここから他のメソッドを実行
     *
     * @param args mainメソッド実行用の引数
     */
    public static void main(String[] args) {//mainメソッド
        boolean retryGame = true;
        while (retryGame) {
            int ruleDescriptionMissCount = Constants.CONSTANT_RULE_MISS_COUNT_FORMAT;//ルール説明のミスカウント初期化
            int numberEntryMissCount = Constants.CONSTANT_NUMBER_ENTRY_MISS_COUNT_FORMAT;//数値入力のミスカウント初期化
            int tryTimes = Constants.CONSTANT_TRY_TIMES_COUNT_FORMAT;//試行回数のカウンタ―初期化
            String[][] inputHistory = new String[10][4];//10回分の入力履歴の配列を初期化
            boolean ruleLoop = false;
            System.out.println(Constants.MESSAGE_WELCOME);
            while (!(ruleLoop)) {//trueになるまで繰り返す。
                ruleDescriptionMissCount++;//カウントを+1する。
                ruleLoop = ruleDescription(ruleDescriptionMissCount);//ルール説明メソッドへ
            }
            if (Constants.CONSTANT_MISS_INPUT_ABNORMAL_END >= ruleDescriptionMissCount) {
                int[] answer = correctAnswerNumber();//正解設定メソッドへ
                numberEntry(tryTimes, answer, inputHistory, numberEntryMissCount);//数値入力メソッドへ
            }
            retryGame = gameEnd();//ゲーム終了メソッドへ
        }
    }

    /**
     * ルール説明を表示するかを選択するメソッド
     *
     * @param ruleCount ミス入力カウント
     * @return true:正常入力時、または、入力ミスが規定回数を超えた際 false:入力ミス時
     */
    public static boolean ruleDescription(int ruleCount) {
        System.out.println(Constants.MESSAGE_DESCRIPTION_CHOOSE);
        String numberEntry = scan.nextLine();//入力フォーム。1または1,2以外で判定。2は特に処理が無い為そのままif文を通り過ぎる。
        numberEntry = numberEntry.replace(" ", "");//入力された内容からスペースを削除する。
        numberEntry = Normalizer.normalize(numberEntry, Normalizer.Form.NFKC);//全角英数字を半角英数字に変換。
        if (Constants.SELECTION_ONE.equals(numberEntry)) {//1が入力された場合、ルール説明を表示。
            System.out.println(readText("src/main/java/Description.txt"));
        }
        if (!((Constants.SELECTION_ONE.equals(numberEntry)) || (Constants.SELECTION_TWO.equals(numberEntry)))) {//1か2以外はミス入力として処理。
            System.out.println(Constants.ERROR_CHOOSE_ONE_OR_TWO);
            //System.out.println(ruleCount);//＃確認用ミスカウント表示。
            if (Constants.CONSTANT_RULE_MISS_COUNT <= ruleCount) {
                System.out.println(Constants.ERROR_END_OF_WARNING);//ここから終了メソッドへ以降予定。
                return true;
            }
            return false;//ミス入力時は再度入力処理へ戻す為、同じメソッドを再度読み込みループ。
        }
        System.out.println(Constants.MESSAGE_START_MESSAGE);
        return true;
    }

    /**
     * ゲーム内で使用する正解の数値を設定するメソッド
     *
     * @return 正解の数値を返す
     */
    public static int[] correctAnswerNumber() {
        int[] correct = new int[Constants.CONSTANT_DIGIT_NUMBER];//正解の数値用の配列、3つの1桁数字を格納する。
        ArrayList<Integer> number = new ArrayList<>();//0～9格納用のリストを作成
        for (int i = 0; i <= 9; i++) {//0～9の数を1つずつリストに格納。
            number.add(i);
        }
        //System.out.println(number);//シャッフル前リスト確認用
        Collections.shuffle(number);//リストに格納した0～9の数をシャッフルする。
        //System.out.println(number);//シャッフル後リスト確認用
        for (int i = 0; i < correct.length; i++) {//シャッフルしたリストを正解の配列の要素数分格納する。
            correct[i] = number.get(i);
        }
        //System.out.println(Arrays.toString(correct));//確認用、この内容が正解の数値となる
        return correct;
    }

    /**
     * 数値を入力し、数値の内容に整合性があるかをチェックするメソッド
     *
     * @param tryTimes             現在が何回目の入力か
     * @param answer               正解の数値
     * @param inputHistory         入力履歴
     * @param numberEntryMissCount ミス入力回数カウンタ
     */
    private static void numberEntry(int tryTimes, int[] answer, String[][] inputHistory, int numberEntryMissCount) {
        System.out.printf((Constants.MESSAGE_INPUT_MESSAGE) + "%n", tryTimes);
        String input = scan.nextLine();//入力フォーム。3桁の整数、SELECTION_G、3桁以外の整数、整数やG以外の文字、3桁数字内の重複で判定。
        input = input.replace(" ", "");//入力された内容からスペースを削除する。
        input = input.toUpperCase();//小文字の英数字を大文字に変換。
        input = Normalizer.normalize(input, Normalizer.Form.NFKC);//全角英数字を半角英数字に変換。
        //System.out.println(input);//＃確認用表示、入力された内容が変換されているかの確認。
        if (Constants.SELECTION_G.equals(input)) {//ギブアップ選択時、ゲームオーバーメソッドに移行する。
            System.out.println(Constants.MESSAGE_CHOOSE_GIVE_UP);
            gameOver(answer);//ゲームオーバーメソッドへ
            return;
        }
        int digit = input.length();
        if (Constants.CONSTANT_INPUT_NUMERIC_LENGTH != digit) {//数値の長さが3以外の場合はミス入力として処理。
            System.out.println(Constants.ERROR_INPUT_THREE_DIGIT);
            numberInputMiss(tryTimes, answer, inputHistory, numberEntryMissCount);//数値入力ミスメソッドへ
            return;//ミス回数が5回以上の場合は数値入力ミスメソッドで再読み込みがされない為、ここでメソッドを終了し、終了メソッドへ移行する。
        }
        if (("^[0-9]{3}$".matches(input) || ("^G$".matches(input)))) {//数値0~9の3桁、Gの1桁以外の入力を弾く
            System.out.println(Constants.ERROR_INPUT_THREE_DIGIT);
            numberInputMiss(tryTimes, answer, inputHistory, numberEntryMissCount);//数値入力ミスメソッドへ
            return;
        }
        int[] inputArray = new int[Constants.CONSTANT_DIGIT_NUMBER];//入力の配列、入力した3桁の数字を1桁ずつに分けて入れる。
        inputArray[0] = Integer.parseInt(input) / 100;//100の桁を取り出す、1/100を行い小数点切り捨て。
        inputArray[1] = Integer.parseInt(input) / 10 % 10;//10の桁を取り出す、1/10を行い、1/10の余りを取り出す。
        inputArray[2] = (int) Math.floor(Integer.parseInt(input) % 10);//1の桁を取り出す、1/10の余りを取り出す。
        //System.out.println(Arrays.toString(inputArray));//＃確認用表示、それぞれ格納した配列を表示。
        if (inputArray[0] == inputArray[1] || inputArray[0] == inputArray[2] || inputArray[1] == inputArray[2]) {//数値の重複確認、
            System.out.println(Constants.ERROR_DUPLICATE_NUMBERS);
            numberInputMiss(tryTimes, answer, inputHistory, numberEntryMissCount);//数値入力ミスメソッドへ
            return;
        }
        judge(tryTimes, inputArray, answer, inputHistory, input);//判定メソッドへ
    }

    /**
     * 入力された数値と正解の数値を比較し、一致しているか処理を行い、入力履歴に格納するメソッド
     *
     * @param tryTimes     現在が何回目の入力か
     * @param inputArray   入力された数値の配列
     * @param answer       正解の数値
     * @param inputHistory 入力履歴
     * @param input        入力された数値
     */
    private static void judge(int tryTimes, int[] inputArray, int[] answer, String[][] inputHistory, String input) {
        tryTimes++;//試行回数を1増やす
        int hitCounter = Constants.CONSTANT_HIT_COUNT_FORMAT;//ヒットのカウンタ―初期化
        int blowCounter = Constants.CONSTANT_BLOW_COUNT_FORMAT;//ブローのカウンタ―初期化
        hitCounter = getHit(inputArray, answer, hitCounter);//ヒット計算メソッドへ
        blowCounter = getBlow(inputArray, answer, blowCounter);//ブロー計算メソッドへ
        System.out.printf((Constants.MESSAGE_HIT) + "%n", hitCounter);//桁も数字も合っている数。
        System.out.printf((Constants.MESSAGE_BLOW) + "%n", blowCounter);//桁は合っていないが数字は合っている数、ヒットとの合計が3より上になる事は無い。
        if (Constants.CONSTANT_HIT_ANSWER_NUMBER == hitCounter) {//hitが3桁全てである場合はゲームクリアとする。
            gameClear(answer, tryTimes);//ゲームクリアメソッドへ
            return;
        }
        if (Constants.CONSTANT_GAME_OVER_LIMIT == tryTimes) {//試行回数が10回目でゲームクリアに到達できない場合はゲームオーバーとする。
            System.out.println(Constants.MESSAGE_NUMBER_FIND_FAILURE);
            gameOver(answer);//ゲームオーバーメソッドへ
            return;
        }
        inputHistory[tryTimes - 1][0] = String.valueOf(tryTimes);
        inputHistory[tryTimes - 1][1] = input;
        inputHistory[tryTimes - 1][2] = String.valueOf(hitCounter);
        inputHistory[tryTimes - 1][3] = String.valueOf(blowCounter);
        System.out.println(Constants.MESSAGE_LOG_HEADER);
        for (int k = 0; k < tryTimes; k++) {
            System.out.printf((Constants.MESSAGE_LOG) + "%n", inputHistory[k][0], inputHistory[k][1], inputHistory[k][2], inputHistory[k][3]);
        }
        numberEntry(tryTimes, answer, inputHistory, Constants.CONSTANT_NUMBER_ENTRY_MISS_COUNT_RESET);//再度数値入力メソッドから繰り返す。その際ミスカウントをリセットする。
    }

    //ヒット計算メソッド

    /**
     * 入力された数値と正解の数値を比較し、ヒットの数を出すメソッド
     *
     * @param inputArray 　入力された数値の配列
     * @param answer     　正解の数値
     * @param hitCounter 　ヒット数
     * @return ヒット数を返す
     */
    public static int getHit(int[] inputArray, int[] answer, int hitCounter) {
        for (int i = 0; i < 3; i++) {//iが3に到達した時ループから脱出する。
            if (inputArray[i] == answer[i]) {//入力数値と正解数値のそれぞれ違う桁同士を比較し、数値が同じ違う桁がある毎にblowが1加算される。
                hitCounter++;
            }
        }
        return hitCounter;
    }

    /**
     * 入力された数値と正解の数値を比較し、ブローの数を出すメソッド
     *
     * @param inputArray  入力された数値の配列
     * @param answer      正解の数値
     * @param blowCounter ブロー数
     * @return ブロー数を返す
     */
    public static int getBlow(int[] inputArray, int[] answer, int blowCounter) {
        for (int i = 0; i < 3; i++) {//iが3に到達した時ループから脱出する。
            for (int j = 0; j < 3; j++) {//jが3に到達した時ループから脱出し、iのカウンターを増やし再度カウントし直す。
                if (i != j) {//iとjが同数(ヒットと同じ)の場合は無視して次のループへ。
                    if (inputArray[i] == answer[j]) {//iとjが違う数字(桁の位置が違う)場合、お互いの要素の数値を比較し、数字が同じ場合はblowをを加算する。
                        blowCounter++;
                    }
                }
            }
        }
        return blowCounter;
    }

    /**
     * テキストファイルを読み込むメソッド
     *
     * @param filePath テキストファイルのファイルパス
     * @return 読み込んだテキストファイルの内容を返す
     */
    public static String readText(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(filePath)) {
            int lineText;
            while (-1 != (lineText = fileReader.read())) {
                stringBuilder.append((char) lineText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 入力ミス時にカウントを増やし、カウントが5以上の場合はゲームを終了させるメソッド
     *
     * @param tryTimes             現在が何回目の入力か
     * @param answer               正解の数値
     * @param inputHistory         入力履歴
     * @param numberEntryMissCount ミス入力回数カウンタ
     */
    private static void numberInputMiss(int tryTimes, int[] answer, String[][] inputHistory, int numberEntryMissCount) {
        numberEntryMissCount++;//カウントを+1する。
        //System.out.println(missCount);//＃確認用ミスカウント表示。
        if (Constants.CONSTANT_NUMBER_ENTRY_MISS_COUNT <= numberEntryMissCount) {
            System.out.println(Constants.ERROR_END_OF_WARNING);//＃確認用表示、ここから終了メソッドへ以降予定。
            return;
        }
        numberEntry(tryTimes, answer, inputHistory, numberEntryMissCount);
    }

    /**
     * ゲームクリア時の文章を表示するメソッド
     *
     * @param answer       正解の数値
     * @param inputHistory 入力履歴
     */
    private static void gameClear(int[] answer, int inputHistory) {
        System.out.printf((Constants.MESSAGE_GAME_CLEAR), Arrays.toString(answer), inputHistory);
    }

    /**
     * ゲーム失敗時の文章を表示するメソッド
     *
     * @param answer 正解の数値
     */
    private static void gameOver(int[] answer) {
        System.out.printf((Constants.MESSAGE_GAME_OVER), Arrays.toString(answer));
    }

    /**
     * ゲーム終了時にゲームをやり直すか確認するメソッド
     *
     * @return true:リトライ選択時 false:ゲーム終了選択時、入力ミス時
     */
    private static boolean gameEnd() {
        System.out.println(Constants.MESSAGE_CHOOSE_RETRY);
        String input = scan.nextLine();//入力フォーム。1または1,2以外で判定。
        input = input.replace(" ", "");//入力された内容からスペースを削除する。
        input = Normalizer.normalize(input, Normalizer.Form.NFKC);//全角英数字を半角英数字に変換。
        if (input.equals(Constants.SELECTION_ONE)) {//1が入力された場合、再度メインメソッドを読み込む。
            System.out.println(Constants.MESSAGE_GAME_RETRY);
            return true;
        }
        if (input.equals(Constants.SELECTION_TWO)) {//2が入力された場合、プログラムを終了する。
            System.out.println(Constants.MESSAGE_GAME_END);
        } else {//1か2以外はミス入力としてエラーメッセ―ジを挟み、2と同様にプログラムを終了する。
            System.out.println(Constants.ERROR_END_OF_WARNING_2);
        }
        return false;
    }
}