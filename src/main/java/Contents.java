import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.text.Normalizer;

public class Contents {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {//mainメソッド
        boolean retry = true;
        while (retry) {
            int ruleCount = 0;//ルール説明のミスカウント初期化
            int missCount = 0;
            int[] correct = new int[3];//Answer用の配列、3つの1桁数字を格納する。
            int trial = 0;//試行回数のカウンタ―初期化
            String[][] history = new String[10][4];//10回分の入力履歴の配列を初期化
            boolean ruleLoop = true;
            System.out.println(Constants.WELCOME);
            while (ruleLoop) {//falseになるまで繰り返す。
                ruleCount++;//カウントを+1する。
                ruleLoop = rule(ruleCount);//ルール説明メソッドへ
            }
            if (ruleCount <= 4) {
                int[] answer = game(correct);//ゲーム内容メソッドへ
                input(trial, answer, history, missCount);//数値入力メソッドへ
            }
            retry = end();//ゲーム終了メソッドへ
        }
    }

    //ルール説明メソッド
    public static boolean rule(int ruleCount) {
        System.out.println(Constants.DESCRIPTION_CHOOSE);
        String input = scan.nextLine();//入力フォーム。1または1,2以外で判定。2は特に処理が無い為そのままif文を通り過ぎる。
        input = input.replace(" ", "");//入力された内容からスペースを削除する。
        input = Normalizer.normalize(input, Normalizer.Form.NFKC);//全角英数字を半角英数字に変換。
        if (input.equals(Constants.ONE)) {//1が入力された場合、ルール説明を表示。
            System.out.println(readText("src/main/java/Description.txt"));
        }
        if (!((input.equals(Constants.ONE)) || (input.equals(Constants.TWO)))) {//1か2以外はミス入力として処理。
            System.out.println(Constants.CHOOSE_ONE_OR_TWO);
            //System.out.println(ruleCount);//＃確認用ミスカウント表示。
            if (ruleCount >= 5) {
                System.out.println(Constants.END_OF_WARNING);//ここから終了メソッドへ以降予定。
                return false;
            }
            return true;//ミス入力時は再度入力処理へ戻す為、同じメソッドを再度読み込みループ。
        }
        System.out.println(Constants.START_MESSAGE);
        return false;
    }

    //ゲーム内容メソッド
    public static int[] game(int[] correct) {
        for (int i = 0; i < 3; i++) {//iが3に到達した時ループから脱出する。
            correct[i] = (int) Math.floor(Math.random() * 10);//各要素に乱数を10倍して少数以下を切り捨て、0～9までの数値となる。
        }
        //System.out.println(Arrays.toString(correct));//＃確認用表示、3桁分の数字をランダムに設定、この時点で含まれる重複は以下で処理。
        while (correct[0] == correct[1]) {//1つめと2つめの数字が重複した場合は違う数字になるまで2つめの数値のランダムを繰り返す。
            correct[1] = (int) Math.floor(Math.random() * 10);
        }
        while (correct[0] == correct[2] || correct[1] == correct[2]) {//1つめと3つめ、2つめと3つめのどちらかが重複した場合は違う数字になるまで3つめの数値のランダムを繰り返す。
            correct[2] = (int) Math.floor(Math.random() * 10);
        }
        //System.out.println(Arrays.toString(correct));//＃確認用表示、重複がある場合は再抽選で重複が無くなる。
        return correct;
    }

    //数値入力メソッド
    private static void input(int trial, int[] answer, String[][] history, int missCount) {
        System.out.printf((Constants.INPUT_MESSAGE) + "%n", trial);
        String input = scan.nextLine();//入力フォーム。3桁の整数、G、3桁以外の整数、整数やG以外の文字、3桁数字内の重複で判定。
        input = input.replace(" ", "");//入力された内容からスペースを削除する。
        input = input.toUpperCase();//小文字の英数字を大文字に変換。
        input = Normalizer.normalize(input, Normalizer.Form.NFKC);//全角英数字を半角英数字に変換。
        //System.out.println(input);//＃確認用表示、入力された内容が変換されているかの確認。
        if (input.equals("G")) {//ギブアップ選択時、ゲームオーバーメソッドに移行する。
            System.out.println(Constants.CHOOSE_GIVE_UP);
            over(answer);//ゲームオーバーメソッドへ
            return;
        }
        int digit = input.length();
        if (digit != 3) {//数値の長さが3以外の場合はミス入力として処理。
            System.out.println(Constants.INPUT_THREE_DIGIT);
            numMiss(trial, answer, history, missCount);//数値入力ミスメソッドへ
            return;//ミス回数が5回以上の場合は数値入力ミスメソッドで再読み込みがされない為、ここでメソッドを終了し、終了メソッドへ移行する。
        }
        if (!((input.matches("^[0-9]{3}$")) || (input.matches("^G$")))) {//数値0~9の3桁、Gの1桁以外の入力を弾く
            System.out.println(Constants.INPUT_THREE_DIGIT);
            numMiss(trial, answer, history, missCount);//数値入力ミスメソッドへ
            return;
        }
        int[] prc = new int[3];//入力の配列、入力した3桁の数字を1桁ずつに分けて入れる。
        prc[0] = Integer.parseInt(input) / 100;//100の桁を取り出す、1/100を行い小数点切り捨て。
        prc[1] = Integer.parseInt(input) / 10 % 10;//10の桁を取り出す、1/10を行い、1/10の余りを取り出す。
        prc[2] = (int) Math.floor(Integer.parseInt(input) % 10);//1の桁を取り出す、1/10の余りを取り出す。
        //System.out.println(Arrays.toString(prc));//＃確認用表示、それぞれ格納した配列を表示。
        if (prc[0] == prc[1] || prc[0] == prc[2] || prc[1] == prc[2]) {//数値の重複確認、
            System.out.println(Constants.DUPLICATE_NUMBERS);
            numMiss(trial, answer, history, missCount);//数値入力ミスメソッドへ
            return;
        }
        judge(prc, answer, history, trial, input);//判定メソッドへ
    }

    //判定メソッド
    private static void judge(int[] prc, int[] ans, String[][] history, int trial, String input) {
        trial++;//試行回数を1増やす
        int hit = 0;//ヒットのカウンタ―初期化
        int blow = 0;//ブローのカウンタ―初期化
        hit = getHit(prc, ans, hit);//ヒット計算メソッドへ
        blow = getBlow(prc, ans, blow);//ブロー計算メソッドへ
        System.out.printf((Constants.HIT) + "%n", hit);//桁も数字も合っている数。
        System.out.printf((Constants.BLOW) + "%n", blow);//桁は合っていないが数字は合っている数、ヒットとの合計が3より上になる事は無い。
        if (hit == 3) {//hitが3桁全てである場合はゲームクリアとする。
            clear(ans, trial);//ゲームクリアメソッドへ
            return;
        }
        if (trial == 10) {//試行回数が10回目でゲームクリアに到達できない場合はゲームオーバーとする。
            System.out.println(Constants.NUMBER_FIND_FAILURE);
            over(ans);//ゲームオーバーメソッドへ
            return;
        }
        history[trial - 1][0] = String.valueOf(trial);
        history[trial - 1][1] = input;
        history[trial - 1][2] = String.valueOf(hit);
        history[trial - 1][3] = String.valueOf(blow);
        System.out.println(Constants.LOG_HEADER);
        for (int k = 0; k < trial; k++) {
            System.out.printf((Constants.LOG) + "%n", history[k][0], history[k][1], history[k][2], history[k][3]);
        }
        input(trial, ans, history, 0);//再度数値入力メソッドから繰り返す。その際ミスカウントをリセットする。
    }

    //ヒット計算メソッド
    public static int getHit(int[] prc, int[] ans, int hit) {
        for (int i = 0; i < 3; i++) {//iが3に到達した時ループから脱出する。
            if (prc[i] == ans[i]) {//入力数値と正解数値のそれぞれ違う桁同士を比較し、数値が同じ違う桁がある毎にblowが1加算される。
                hit++;
            }
        }
        return hit;
    }

    //ブロー計算メソッド
    public static int getBlow(int[] prc, int[] ans, int blow) {
        for (int i = 0; i < 3; i++) {//iが3に到達した時ループから脱出する。
            for (int j = 0; j < 3; j++) {//jが3に到達した時ループから脱出し、iのカウンターを増やし再度カウントし直す。
                if (i != j) {//iとjが同数(ヒットと同じ)の場合は無視して次のループへ。
                    if (prc[i] == ans[j]) {//iとjが違う数字(桁の位置が違う)場合、お互いの要素の数値を比較し、数字が同じ場合はblowをを加算する。
                        blow++;
                    }
                }
            }
        }
        return blow;
    }

    //テキストファイル読み込みメソッド
    public static String readText(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(filePath)) {
            int lineText;
            while ((lineText = fileReader.read()) != -1) {
                stringBuilder.append((char) lineText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    //数値入力ミスメソッド
    private static void numMiss(int trial, int[] ans, String[][] history, int missCount) {
        missCount++;//カウントを+1する。
        //System.out.println(missCount);//＃確認用ミスカウント表示。
        if (missCount >= 5) {
            System.out.println(Constants.END_OF_WARNING);//＃確認用表示、ここから終了メソッドへ以降予定。
            return;
        }
        input(trial, ans, history, missCount);
    }

    //ゲームクリアメソッド
    private static void clear(int[] ans, int trial) {
        System.out.printf((Constants.GAME_CLEAR), Arrays.toString(ans), trial);
    }

    //ゲームオーバーメソッド
    private static void over(int[] ans) {
        System.out.printf((Constants.GAME_OVER), Arrays.toString(ans));
    }

    //ゲーム終了メソッド
    private static boolean end() {
        System.out.println(Constants.CHOOSE_RETRY);
        String input = scan.nextLine();//入力フォーム。1または1,2以外で判定。
        input = input.replace(" ", "");//入力された内容からスペースを削除する。
        input = Normalizer.normalize(input, Normalizer.Form.NFKC);//全角英数字を半角英数字に変換。
        if (input.equals(Constants.ONE)) {//1が入力された場合、再度メインメソッドを読み込む。
            System.out.println(Constants.GAME_RETRY);
            return true;
        }
        if (input.equals(Constants.TWO)) {//2が入力された場合、プログラムを終了する。
            System.out.println(Constants.GAME_END);
        } else {//1か2以外はミス入力としてエラーメッセ―ジを挟み、2と同様にプログラムを終了する。
            System.out.println(Constants.END_OF_WARNING_2);
        }
        return false;
    }
}