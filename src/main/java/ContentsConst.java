public class ContentsConst {
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String START_MESSAGE = """
            ルール説明を表示しますか？次の選択肢から数字で入力してください。
            1.ルール説明を表示
            2.ルール説明をスキップ""";
    public static final String CHOOSE_ONE_OR_TWO = "1か2で入力してください。";
    public static final String WARNING_GAME_END = "入力ミスが連続した為、ゲームを終了します。\n";
    public static final String START_HIT_BLOW = "それでは、ヒット&ブローを開始します。\n";
    public static final String INPUT_MESSAGE = """
            現在までの入力回数:%d回
            「3桁の数字」を「重複無し」で入力してください。
            ギブアップする際は"G"を入力してください。""";
    public static final String CHOOSE_GIVE_UP = "ギブアップが選択されました。";
    public static final String INPUT_TREE_DIGIT = "3桁の数字で入力してください。";
    public static final String WARNING_DUPLICATION = "数字に重複があります、1つの回答内で同じ数字は使えません。";
    public static final String HIT = "ヒット:%d";
    public static final String BLOW = "ブロー:%d\n";
    public static final String CANNOT_FOUND_CORRECT_NUMBER = "10回目の入力までに正解の数字を見つけられませんでした。\n";
    public static final String RESULT_HEADER = "入力回数    入力した数字      ヒット     ブロー";
    public static final String HISTORY = "%s回目         %s            %s         %s";
    public static final String GAME_CLEAR = """
            ゲームクリア！
            正解の数字は%sでした！
            入力回数:%d回
            """;
    public static final String GAME_OVER = """
            ゲームオーバー！
            正解の数字は%sでした！
            """;
    public static final String CHOOSE_RETRY = """
            ゲームをリトライしますか？次の選択肢から数字で入力してください。
            1.ゲームをリトライ
            2.ゲームを終了""";
    public static final String GAME_RETRY = "ゲームをリトライします。\n";
    public static final String GAME_END = "ゲームを終了します。";
    public static final String WARNING_GAME_END_2 = "選択肢以外の入力がされている為、ゲームを終了します。";
}
