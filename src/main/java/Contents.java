import java.util.Objects;
import java.util.Scanner;
import java.util.Arrays;
import java.text.Normalizer;
public class Contents {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {//mainメソッド
        int rulecount = 0;//ルール説明のミスカウント初期化
        int trial = 0;//試行回数のカウンタ―初期化
        System.out.println("ヒット&ブローへようこそ！");
        rule(rulecount);//ルール説明メソッドへ
        game(trial);//ゲーム内容メソッドへ
    }
    private static void rule(int rulecount){//ルール説明メソッド
        System.out.println("ルール説明を表示しますか？次の選択肢から数字で入力してください。");
        System.out.println("1.ルール説明を表示");
        System.out.println("2.ルール説明をスキップ");
        String input = scan.nextLine();//入力フォーム。1または1,2以外で判定。2は特に処理が無い為そのままif文を通り過ぎる。
        input = input.replace(" ", "");//入力された内容からスペースを削除する。
        input = Normalizer.normalize(input,Normalizer.Form.NFKC);//全角英数字を半角英数字に変換。
        if (input.equals("1")) {//1が入力された場合、ルール説明を表示。
            System.out.println("ルール説明を表示します。");
            System.out.println("ヒット&ブローはプログラム側がランダムで設定した数字を当てるゲームです。\n");
            System.out.println("このプログラムでは被り無しの0～9の数字が3桁設定されます。");
            System.out.println("ユーザー側が3桁の数字を入力し、その数字と正解の数字を比較して次のヒントが表示されます。\n");
            System.out.println("ヒット:桁の位置も数字も合っている数字の数です。");
            System.out.println("ブロー:数字は合っているが、桁の位置が違う数字の数です。\n");
            System.out.println("その後、再度数字の入力から繰り返し、3桁全部の数字を当てた場合はゲームクリアです。");
            System.out.println("少ない回数でのクリアを目指してください。\n");
            System.out.println("10回目の入力までに正解を見つけられないとゲームオーバーになります。");
            System.out.println("また、数値入力時に\"G\"を入力するとギブアップとしてゲームを終了する事が出来ます。\n");
        }
        if (!(input.equals("1")) ^ (input.equals("2"))) {//1か2以外はミス入力として処理。
            System.out.println("1か2で入力してください。");
            {
                rulecount += 1;
                System.out.println(rulecount);//＃確認用ミスカウント表示。
                if (rulecount >= 5) {
                    System.out.println("5以上");//＃確認用表示、ここから終了メソッドへ以降予定。
                    //終了メソッドへ
                }
            }
            rule(rulecount);//ミス入力時は再度入力処理へ戻す為、同じメソッドを再度読み込みループ。
        }
        System.out.println("それでは、ヒット&ブローを開始します。\n");
    }
    private static void game(int trial){//ゲーム内容メソッド
        int[] ans = new int[3];//Answer用の配列、3つの1桁数字を格納する。
        ans[0] = (int)Math.floor(Math.random() * 10);//各要素に乱数を10倍して少数以下を切り捨て、0～9までの数値となる。
        ans[1] = (int)Math.floor(Math.random() * 10);
        ans[2] = (int)Math.floor(Math.random() * 10);
        System.out.println(Arrays.toString(ans));//＃確認用表示、3桁分の数字をランダムに設定、この時点で含まれる重複は以下で処理。
        while(ans[0] == ans[1]){//1つめと2つめの数字が重複した場合は違う数字になるまで2つめの数値のランダムを繰り返す。
            ans[1] = (int)Math.floor(Math.random() * 10);
        }
        while(ans[0] == ans[2] || ans[1] == ans[2]) {//1つめと3つめ、2つめと3つめのどちらかが重複した場合は違う数字になるまで3つめの数値のランダムを繰り返す。
            ans[2] = (int)Math.floor(Math.random() * 10);
        }
        System.out.println(Arrays.toString(ans));//＃確認用表示、重複がある場合は再抽選で重複が無くなる。
        input(trial);//数値入力メソッドへ
    }
    private static void input(int trial){//数値入力メソッド
        trial++;
        System.out.println("試行回数:"+ trial +"回目\n");
        System.out.println("「3桁の数字」を「重複無し」で入力してください。");
        System.out.println("ギブアップする際は\"G\"を入力してください");
        String input = scan.nextLine();//入力フォーム。3桁の整数、G、3桁以外の整数、整数やG以外の文字、3桁数字内の重複で判定。
        input = input.replace(" ", "");//入力された内容からスペースを削除する。
        input = input.toUpperCase();//小文字の英数字を大文字に変換。
        input = Normalizer.normalize(input,Normalizer.Form.NFKC);//全角英数字を半角英数字に変換。
        System.out.println(input);//＃確認用表示、入力された内容が変換されているかの確認。
        if (input.equals("G")){//ギブアップ選択時、ゲームオーバーメソッドに移行する。
            System.out.println("ギブアップが選択されました。");
            //ゲームオーバーメソッドへ
        }
    }
}