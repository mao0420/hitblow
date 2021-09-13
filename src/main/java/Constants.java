public class Constants {
    public static final String SELECTION_ONE = "1";
    public static final String SELECTION_TWO = "2";
    public static final String SELECTION_G = "G";
    public static final int CONSTANT_RULE_MISS_COUNT_FORMAT = 0;
    public static final int CONSTANT_RULE_MISS_COUNT = 5;
    public static final int CONSTANT_NUMBER_ENTRY_MISS_COUNT_FORMAT = 0;
    public static final int CONSTANT_NUMBER_ENTRY_MISS_COUNT_RESET = 0;
    public static final int CONSTANT_NUMBER_ENTRY_MISS_COUNT = 5;
    public static final int CONSTANT_TRY_TIMES_COUNT_FORMAT = 0;
    public static final int CONSTANT_DIGIT_NUMBER = 3;
    public static final int CONSTANT_INPUT_NUMERIC_LENGTH = 3;
    public static final int CONSTANT_HIT_COUNT_FORMAT = 0;
    public static final int CONSTANT_BLOW_COUNT_FORMAT = 0;
    public static final int CONSTANT_ARRAY_HIT_COUNTER = 0;
    public static final int CONSTANT_ARRAY_BLOW_COUNTER = 1;
    public static final int CONSTANT_HIT_ANSWER_NUMBER = 3;
    public static final int CONSTANT_MISS_INPUT_ABNORMAL_END = 4;
    public static final int CONSTANT_GAME_OVER_LIMIT = 10;
    public static final String MESSAGE_WELCOME = "ヒット&ブローへようこそ！";
    public static final String MESSAGE_DESCRIPTION_CHOOSE = """
            ルール説明を表示しますか？次の選択肢から数字で入力してください。
            %s.ルール説明を表示
            %s.ルール説明をスキップ
            """;
    public static final String ERROR_FILE_LOADING_FAILURE = "ルール説明の読み込みに失敗しました。";
    public static final String MESSAGE_START_MESSAGE = "それでは、ヒット&ブローを開始します。\n";
    public static final String ERROR_CHOOSE_ONE_OR_TWO = SELECTION_ONE + "か" + SELECTION_TWO + "で入力してください。";
    public static final String ERROR_END_OF_WARNING = "入力ミスが連続した為、ゲームを終了します。";
    public static final String MESSAGE_INPUT_MESSAGE = """
            現在までの入力回数:%d回
                
            「3桁の数字」を「重複無し」で入力してください。
            ギブアップする際は%sを入力してください。
            """;
    public static final String MESSAGE_CHOOSE_GIVE_UP = "ギブアップが選択されました。\n";
    public static final String ERROR_INPUT_THREE_DIGIT = CONSTANT_DIGIT_NUMBER + "桁の数字で入力してください。";
    public static final String ERROR_DUPLICATE_NUMBERS = "数字に重複があります、1つの回答内で同じ数字は使えません。";
    public static final String MESSAGE_HIT = "ヒット:%s";
    public static final String MESSAGE_BLOW = "ブロー:%s\n";
    public static final String MESSAGE_LOG_HEADER = "入力回数    入力した数字      ヒット     ブロー";
    public static final String MESSAGE_LOG = "%s回目         %s            %s         %s";
    public static final String MESSAGE_NUMBER_FIND_FAILURE = CONSTANT_GAME_OVER_LIMIT + "回目の入力までに正解の数字を見つけられませんでした。\n";
    public static final String MESSAGE_GAME_CLEAR = """
            ゲームクリア！
            正解の数字は%sでした！
            入力回数:%d回
            
            """;
    public static final String MESSAGE_GAME_OVER = """
            ゲームオーバー！
            正解の数字は%sでした！
            
            """;
    public static final String MESSAGE_CHOOSE_RETRY = """
            ゲームをリトライしますか？次の選択肢から数字で入力してください。
            %s.ゲームをリトライ
            %s.ゲームを終了
            """;
    public static final String MESSAGE_GAME_RETRY = "ゲームをリトライします。\n";
    public static final String MESSAGE_GAME_END = "ゲームを終了します。";
    public static final String ERROR_END_OF_WARNING_2 = "選択肢以外の入力がされている為、ゲームを終了します。";
    public static final int TEST_ELEMENT_DIGITS = 3;
    public static final int TEST_HIT_ZERO = 0;
    public static final int TEST_HIT_ONE = 1;
    public static final int TEST_HIT_TWO = 2;
    public static final int TEST_HIT_THREE = 3;
    public static final int TEST_BLOW_ZERO = 0;
    public static final int TEST_BLOW_ONE = 1;
    public static final int TEST_BLOW_TWO = 2;
    public static final int TEST_BLOW_THREE = 3;
}