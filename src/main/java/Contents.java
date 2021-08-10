import java.util.Objects;
import java.util.Scanner;
public class Contents {
    public static void main(String[] args){
        int rulecount = 0;
        Scanner scan=new Scanner(System.in);
        while(true){
            System.out.println("ヒット&ブローへようこそ！");
            System.out.println("ルール説明を表示しますか？次の選択肢から半角数字で入力してください。");
            System.out.println("1.ルール説明を表示");
            System.out.println("2.ルール説明をスキップ");
            String input = scan.next();
            if (input.equals("1")) {
                System.out.println("ルール説明を表示します。");
                System.out.println("ヒット&ブローはプログラム側がランダムで設定した数字を当てるゲームです。\n");
                System.out.println("このプログラムでは被り無しの0～9の数字が3桁設定されます。");
                System.out.println("ユーザー側が3桁の半角数字を入力し、その数字と正解の数字を比較して次のヒントが表示されます。\n");
                System.out.println("ヒット:桁の位置も数字も合っている数字の数です。");
                System.out.println("ブロー:数字は合っているが、桁の位置が違う数字の数です。\n");
                System.out.println("その後、再度数値の入力から繰り返し、3桁全部の数字を当てた場合はゲームクリアです。");
                System.out.println("少ない回数でのクリアを目指してください。\n");
                System.out.println("10回目の入力までに正解を見つけられないとゲームオーバーになります。");
                System.out.println("また、数値入力時に\"G\"を入力するとギブアップとしてゲームを終了する事が出来ます。\n");
                break;
            }
            if (input.equals("2")) {
                break;
            }
            if(!(input.equals("1"))^(input.equals("2"))){
                System.out.println("1か2で入力してください。");{
                    rulecount += 1;
                    System.out.println(rulecount);//
                    if (rulecount >= 5){
                        System.out.println("5以上");
                    }
                    continue;
                }
            }
        }
        System.out.println("それでは、ヒット&ブローを開始します。");
    }
}