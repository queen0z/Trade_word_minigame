import java.util.Random;

public class KoreanTextSource implements TextSource {
    private static final String[] WORDS = {
        "수출", "수입", "관세", "환율", "물류", "통관", "운송", "계약", "세관", "보험",
        "화물", "결제", "선적", "보관", "서류", "대금", "리스크", "검수", "견적", "송장",
        "관세법", "원산지", "대외무역", "보세", "보세창고", "신용장", "추심", "송금", "세관장",
        "납세고지", "선하증권", "정기선", "체화료", "용선자", "승낙", "청약", "간접무역", "분할선적",
        "환적", "인수인도조건"
    };

    private Random random = new Random();

    @Override
    public String get() {
        return WORDS[random.nextInt(WORDS.length)];
    }
}
