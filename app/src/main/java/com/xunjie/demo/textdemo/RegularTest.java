package com.xunjie.demo.textdemo;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created Time: 2016/9/21.
 * <p/>
 * Author:  zhy
 * <p/>
 * 功能：
 */
public class RegularTest {
    /**
     * 验证身份证号是否符合规则
     *
     * @param text 身份证号
     * @return
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /**
     * 银行卡 15 到30位
     *
     * @param text
     * @return
     */
    public static boolean bankId(String text) {
        String regx = "\\d{15,30}";


        return text.matches(regx);
    }

    public static InputFilter getEmojiFilter() {
        InputFilter emojiFilter = new InputFilter() {
            String limitEx="[～×÷∞≠∑\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+-|{}【】‘；：”“’。，、？]|";

            String xx="()+ */-=~:;\"'!?×÷&{∑≠∞,.⑩⑨〉〈><‘’］［）" +
                    "（}{]¢[)◇£￥(◆'Σ¾⅜Ητ⁴ⅹ" +
                    "ΕΟⅸ²¼Ⅴ!@#$%^&*()～！？；：" +
                    "、…=”“+-<`:>/—;?_囍\\|‘~／’\\[\\]'" +
                    "￥€\"{},£¢.·©卍卐®■□●○◆◇(｛“”" +
                    "｝)[]{}（<>）［‘〈〉’］《『︻︼』》〔" +
                    "｢︽︾｣〕【︵︿﹀︶】〘︷﹁﹂︸〙「︹﹃﹄︺" +
                    "」←↚↤↥↛↑→↜↦↧↝↓↔↞↨↩↟↕↖↠↪↫↡↗↘↢↬" +
                    "↭↣↙↮↸⇂⇃↹↯↰↺⇄⇅↻↱↲↼⇆⇇↽↳↴↾⇈⇉↿↵↶⇀" +
                    "⇊⇋⇁↷⇌⇖⇠⇡⇗⇍⇎⇘⇢⇣⇙⇏⇐⇚⇤⇥⇛⇑⇒⇜⇦⇧⇝⇓" +
                    "⇔⇞⇨⇩⇟⇕⊕<+-≤⊗%≦×÷≪‰∀≮±=>∂∃≥≠∼≧∅∆≫≈" +
                    "≡≯∇∈∠∮∯∧∉∋∨∰∴∣∌∏∥∵∶∩∑√∪∷⊂∫∝∞" +
                    "∬⊃⊄∭∟⊅ⁿκλα⊆⊇βμνγ∪∩δξοε°§ζπρη※℃θςσι∮" +
                    "τΕΟΠΖυφΗΡΣΘχψΙΤΥΚωΑΛΦΧΜΒΓΝΨΩΞΔ①⑴⑵②③⑶" +
                    "⑷④⑤⑸⑹⑥⑦⑺⑻⑧⑨⑼⑽⑩㈠ⅠⅪⅫⅡ㈡㈢ⅢⅬⅭⅣ" +
                    "㈣㈤ⅤⅮⅯⅥ㈥㈦ⅦⅰⅱⅧ㈧㈨ⅨⅲⅳⅩ㈩ⅴⅾ½⅓ⅿⅵⅶ" +
                    "¹⅔¼²ⅷⅸ³¾⅛⁴ⅹⅺ₁⅜⅝₂ⅻⅼ₃⅞㊣₄ⅽ]|";
            Pattern emoji = Pattern.compile(
                    "[x]|" +
                            "[\ud83c\udc00-\ud83c\udfff]|" +
                            "[\ud83d\udc00-\ud83d\udfff]|" +
                            "[\u2600-\u27ff]",


                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Pattern pattern = Pattern.compile(limitEx);

            @Override
            public CharSequence filter(CharSequence source, int start,
                                       int end, Spanned dest, int dstart, int dend) {


                Matcher emojiMatcher = emoji.matcher(source);


                if (emojiMatcher.find()) {


                    return "";


                }
                return null;


            }
        };
        return emojiFilter;

    }


}
