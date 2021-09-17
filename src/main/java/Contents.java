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
     * メインメソッド
     * 基本となるメソッド、流れに沿って他のメソッドを実行する。
     *
     * @param args mainメソッド実行用の引数
     */
    public static void main(String[] args) {
        boolean hasRetryGame = true;
        while (hasRetryGame) {
            //ルール説明のミスカウント初期化
            int ruleDescriptionMissCount = Constants.CONSTANT_RULE_MISS_COUNT_FORMAT;
            //数値入力のミスカウント初期化
            int numberEntryMissCount = Constants.CONSTANT_NUMBER_ENTRY_MISS_COUNT_FORMAT;
            //試行回数のカウンタ―初期化
            int tryTimes = Constants.CONSTANT_TRY_TIMES_COUNT_FORMAT;
            //10回分の入力履歴の配列を初期化
            String[][] inputHistory = new String[10][4];
            boolean hasLoopRule = false;
            System.out.println(Constants.MESSAGE_WELCOME);
            //trueになるまで繰り返す。
            while (!(hasLoopRule)) {
                //カウントを+1する。
                ruleDescriptionMissCount++;
                //ルール説明メソッドへ
                hasLoopRule = ruleDescription(ruleDescriptionMissCount);
            }
            if (Constants.CONSTANT_MISS_INPUT_ABNORMAL_END >= ruleDescriptionMissCount) {
                //正解設定メソッドへ
                int[] answer = correctAnswerNumber();
                //数値入力メソッドへ
                numberEntry(tryTimes, answer, inputHistory, numberEntryMissCount);
            }
            //ゲーム終了メソッドへ
            hasRetryGame = gameEnd();
        }
    }

    /**
     * ルール説明メソッド
     * ルール説明を表示するかを選択する。
     *
     * @param ruleCount ミス入力カウント
     * @return true:正常入力時、または、入力ミスが規定回数を超えた際 false:入力ミス時
     */
    public static boolean ruleDescription(int ruleCount) {
        System.out.printf(Constants.MESSAGE_DESCRIPTION_CHOOSE, Constants.SELECTION_ONE, Constants.SELECTION_TWO);
        //入力フォーム。1または1,2以外で判定。2は特に処理が無い為そのままif文を通り過ぎる。
        String numberEntry = scan.nextLine();
        //入力された内容からスペースを削除する。
        numberEntry = numberEntry.replace(" ", "");
        //全角英数字を半角英数字に変換。
        numberEntry = Normalizer.normalize(numberEntry, Normalizer.Form.NFKC);
        if (Constants.SELECTION_ONE.equals(numberEntry)) {
            //1が入力された場合、テキスト読み込みメソッドへ移行し、ルール説明のテキストファイルの内容を表示。
            System.out.println(readText("src/main/java/Description.txt"));
        }
        if (!((Constants.SELECTION_ONE.equals(numberEntry)) || (Constants.SELECTION_TWO.equals(numberEntry)))) {
            //1か2以外はミス入力として処理。
            System.out.println(Constants.ERROR_CHOOSE_ONE_OR_TWO);
            //＃確認用ミスカウント表示。
            //System.out.println(ruleCount);
            if (Constants.CONSTANT_RULE_MISS_COUNT <= ruleCount) {
                //ここから終了メソッドへ以降予定。
                System.out.println(Constants.ERROR_END_OF_WARNING);
                return true;
            }
            //ミス入力時はfalseを返しメソッドを繰り返す。
            return false;
        }
        System.out.println(Constants.MESSAGE_START_MESSAGE);
        return true;
    }

    /**
     * 正解設定メソッド
     * ゲーム内での正解の数値を設定する。
     *
     * @return 正解の数値を返す
     */
    public static int[] correctAnswerNumber() {
        //正解の数値用の配列、3つの1桁数字を格納する。
        int[] correct = new int[Constants.CONSTANT_DIGIT_NUMBER];
        //0～9格納用のリストを作成
        ArrayList<Integer> number = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            //0～9の数を1つずつリストに格納。
            number.add(i);
        }
        //シャッフル前リスト確認用
        //System.out.println(number);
        //リストに格納した0～9の数をシャッフルする。
        Collections.shuffle(number);
        //シャッフル後リスト確認用
        //System.out.println(number);
        for (int i = 0; i < correct.length; i++) {
            //シャッフルしたリストを正解の配列の要素数分格納する。
            correct[i] = number.get(i);
        }
        //確認用、この内容が正解の数値となる
        //System.out.println(Arrays.toString(correct));
        return correct;
    }

    /**
     * 数値入力メソッド
     * 数値を入力し、入力された数値の整合性をチェックする。
     *
     * @param tryTimes             現在が何回目の入力か
     * @param answer               正解の数値
     * @param inputHistory         入力履歴
     * @param numberEntryMissCount ミス入力回数カウンタ
     */
    private static void numberEntry(int tryTimes, int[] answer, String[][] inputHistory, int numberEntryMissCount) {
        System.out.printf(Constants.MESSAGE_INPUT_MESSAGE, tryTimes, Constants.SELECTION_G);
        //入力フォーム。3桁の整数、G、3桁以外の整数、整数やG以外の文字、3桁数字内の重複で判定。
        String input = scan.nextLine();
        //入力された内容からスペースを削除する。
        input = input.replace(" ", "");
        //小文字の英数字を大文字に変換。
        input = input.toUpperCase();
        //全角英数字を半角英数字に変換。
        input = Normalizer.normalize(input, Normalizer.Form.NFKC);
        //＃確認用表示、入力された内容が変換されているかの確認。
        //System.out.println(input);
        if (Constants.SELECTION_G.equals(input)) {
            //ギブアップ選択時、ゲームオーバーメソッドに移行する。
            System.out.println(Constants.MESSAGE_CHOOSE_GIVE_UP);
            //ゲームオーバーメッセージを表示
            System.out.printf(Constants.MESSAGE_GAME_OVER, Arrays.toString(answer));
            return;
        }
        int digit = input.length();
        if (Constants.CONSTANT_INPUT_NUMERIC_LENGTH != digit) {
            //数値の長さが3以外の場合はミス入力として処理。
            System.out.println(Constants.ERROR_INPUT_THREE_DIGIT);
            //数値入力ミスメソッドへ
            numberInputMiss(tryTimes, answer, inputHistory, numberEntryMissCount);
            return;
        }
        if (!((input.matches("^[0-9]{3}$") || (input.matches("^G$"))))) {
            //数値0~9の3桁、Gの1桁以外の入力を弾く
            System.out.println(Constants.ERROR_INPUT_THREE_DIGIT);
            //数値入力ミスメソッドへ
            numberInputMiss(tryTimes, answer, inputHistory, numberEntryMissCount);
            return;
        }
        //入力の配列、入力した3桁の数字を1桁ずつに分けて入れる。
        int[] inputArray = new int[Constants.CONSTANT_DIGIT_NUMBER];
        //100の桁を取り出す、1/100を行い小数点切り捨て。
        inputArray[0] = Integer.parseInt(input) / 100;
        //10の桁を取り出す、1/10を行い、1/10の余りを取り出す。
        inputArray[1] = Integer.parseInt(input) / 10 % 10;
        //1の桁を取り出す、1/10の余りを取り出す。
        inputArray[2] = Integer.parseInt(input) % 10;
        //＃確認用表示、それぞれ格納した配列を表示。
        //System.out.println(Arrays.toString(inputArray));
        if (inputArray[0] == inputArray[1] || inputArray[0] == inputArray[2] || inputArray[1] == inputArray[2]) {
            //数値の重複確認
            System.out.println(Constants.ERROR_DUPLICATE_NUMBERS);
            //数値入力ミスメソッドへ
            numberInputMiss(tryTimes, answer, inputHistory, numberEntryMissCount);
            return;
        }
        //判定メソッドへ
        judge(tryTimes, inputArray, answer, inputHistory, input);
    }

    /**
     * 判定メソッド
     * 入力された数値と正解の数値を比較し、一致しているか処理を行い、入力履歴に格納する。
     *
     * @param tryTimes     現在が何回目の入力か
     * @param inputArray   入力された数値の配列
     * @param answer       正解の数値
     * @param inputHistory 入力履歴
     * @param input        入力された数値
     */
    private static void judge(int tryTimes, int[] inputArray, int[] answer, String[][] inputHistory, String input) {
        //試行回数を履歴用変数に保存
        int temporaryInputTimes = tryTimes;
        //試行回数を1増やす
        tryTimes++;
        //ヒットブロー計算メソッドへ
        int[] hitBlowCounter = getHitBlow(inputArray, answer);
        //桁も数字も合っている数。
        System.out.printf((Constants.MESSAGE_HIT) + "%n", hitBlowCounter[Constants.CONSTANT_ARRAY_HIT_COUNTER]);
        //桁は合っていないが数字は合っている数、ヒットとの合計が3より上になる事は無い。
        System.out.printf((Constants.MESSAGE_BLOW) + "%n", hitBlowCounter[Constants.CONSTANT_ARRAY_BLOW_COUNTER]);
        if (Constants.CONSTANT_HIT_ANSWER_NUMBER == hitBlowCounter[Constants.CONSTANT_ARRAY_HIT_COUNTER]) {
            //hitが3桁全てである場合はゲームクリアとする。
            //ゲームクリアメッセージを表示
            System.out.printf((Constants.MESSAGE_GAME_CLEAR), Arrays.toString(answer), tryTimes);
            return;
        } else if (Constants.CONSTANT_GAME_OVER_LIMIT == tryTimes) {
            //試行回数が10回目でゲームクリアに到達できない場合はゲームオーバーとする。
            System.out.println(Constants.MESSAGE_NUMBER_FIND_FAILURE);
            //ゲームオーバーメッセージを表示
            System.out.printf(Constants.MESSAGE_GAME_OVER, Arrays.toString(answer));
            return;
        }
        inputHistory[temporaryInputTimes][0] = String.valueOf(tryTimes);
        inputHistory[temporaryInputTimes][1] = input;
        inputHistory[temporaryInputTimes][2] = String.valueOf(hitBlowCounter[Constants.CONSTANT_ARRAY_HIT_COUNTER]);
        inputHistory[temporaryInputTimes][3] = String.valueOf(hitBlowCounter[Constants.CONSTANT_ARRAY_BLOW_COUNTER]);
        System.out.println(Constants.MESSAGE_LOG_HEADER);
        for (int k = 0; k < tryTimes; k++) {
            System.out.printf((Constants.MESSAGE_LOG) + "%n", inputHistory[k][0], inputHistory[k][1], inputHistory[k][2], inputHistory[k][3]);
        }
        //再度数値入力メソッドから繰り返す。その際ミスカウントをリセットする。
        numberEntry(tryTimes, answer, inputHistory, Constants.CONSTANT_NUMBER_ENTRY_MISS_COUNT_RESET);
    }

    /**
     * ヒットブロー計算メソッド
     * 入力された数値と正解の数値を比較し、ヒットとブローの数を配列に格納する。
     *
     * @param inputArray 入力された数値の配列
     * @param answer     正解の数値
     * @return ブロー数を返す
     */
    public static int[] getHitBlow(int[] inputArray, int[] answer) {
        //ヒットのカウンタ初期化
        int hitCounter = Constants.CONSTANT_HIT_COUNT_FORMAT;
        //ブローのカウンタ初期化
        int blowCounter = Constants.CONSTANT_BLOW_COUNT_FORMAT;
        //ヒットとブローの数値を格納する配列の初期化
        int[] hitBlowCounter = new int[2];
        for (int i = 0; i < inputArray.length; i++) {
            //iが3に到達した時ループから脱出する。
            if (inputArray[i] == answer[i]) {
                //ヒットの処理、入力数値と正解数値の同じ桁同士を比較し、数値が同じ場合はヒットを加算する。
                hitCounter++;
            }
            for (int j = 0; j < inputArray.length; j++) {
                //jが3に到達した時ループから脱出し、iのカウンターを増やし再度カウントし直す。
                if (i != j) {
                    //iとjが同数(ヒットと同じ)の場合は無視して次のループへ。
                    if (inputArray[i] == answer[j]) {
                        //ブローの処理、iとjが違う数値(桁の位置が違う)の要素内の数値を比較し、数値が同じ場合はブローを加算する。
                        blowCounter++;
                    }
                }
            }
        }
        //ヒットの数値を格納
        hitBlowCounter[Constants.CONSTANT_ARRAY_HIT_COUNTER] = hitCounter;
        hitBlowCounter[Constants.CONSTANT_ARRAY_BLOW_COUNTER] = blowCounter;
        return hitBlowCounter;
    }

    /**
     * テキスト読み込みメソッド
     * テキストファイルの読み込みを実施する。
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
            System.out.println(Constants.ERROR_FILE_LOADING_FAILURE);
        }
        return stringBuilder.toString();
    }

    /**
     * 数値入力ミスメソッド
     * 入力ミス時にカウントを増やし、カウントが5以上の際にゲームを終了する。
     *
     * @param tryTimes             現在が何回目の入力か
     * @param answer               正解の数値
     * @param inputHistory         入力履歴
     * @param numberEntryMissCount ミス入力回数カウンタ
     */
    public static void numberInputMiss(int tryTimes, int[] answer, String[][] inputHistory, int numberEntryMissCount) {
        //カウントを+1する。
        numberEntryMissCount++;
        //＃確認用ミスカウント表示。
        //System.out.println(missCount);
        if (Constants.CONSTANT_NUMBER_ENTRY_MISS_COUNT <= numberEntryMissCount) {
            //＃確認用表示、ここから終了メソッドへ以降予定。
            System.out.println(Constants.ERROR_END_OF_WARNING);
            return;
        }
        numberEntry(tryTimes, answer, inputHistory, numberEntryMissCount);
    }

    /**
     * ゲーム終了メソッド
     * ゲーム終了時にゲームをやり直すか確認する。
     *
     * @return true:リトライ選択時 false:ゲーム終了選択時、入力ミス時
     */
    public static boolean gameEnd() {
        System.out.printf(Constants.MESSAGE_CHOOSE_RETRY, Constants.SELECTION_ONE, Constants.SELECTION_TWO);
        //入力フォーム。1または1,2以外で判定。
        String input = scan.nextLine();
        //入力された内容からスペースを削除する。
        input = input.replace(" ", "");
        //全角英数字を半角英数字に変換。
        input = Normalizer.normalize(input, Normalizer.Form.NFKC);
        if (Constants.SELECTION_ONE.equals(input)) {
            //1が入力された場合、再度メインメソッドを読み込む。
            System.out.println(Constants.MESSAGE_GAME_RETRY);
            return true;
        } else if (Constants.SELECTION_TWO.equals(input)) {
            //2が入力された場合、プログラムを終了する。
            System.out.println(Constants.MESSAGE_GAME_END);
        } else {
            //1か2以外はミス入力としてエラーメッセ―ジを挟み、2と同様にプログラムを終了する。
            System.out.println(Constants.ERROR_END_OF_WARNING_2);
        }
        return false;
    }
}