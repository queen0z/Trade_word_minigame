import java.util.Random;

public class EnglishTextSource implements TextSource {
    private static final String[] WORDS = {
        "export", "import", "trade", "customs", "tariff", "logistics", "invoice",
        "warehouse", "contract", "shipment", "insurance", "container", "cargo", "risk", "demand", "payment", "incoterms", 
        "drawer", "drawee", "UCP600", "buyer", "seller" , "market", "carrier"
    };

    private Random random = new Random();

    @Override
    public String get() {
        return WORDS[random.nextInt(WORDS.length)];
    }
}
