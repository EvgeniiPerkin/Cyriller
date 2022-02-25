package DeclinationOfDigit;
import java.util.ArrayList;

public class CyrRule {
    private final String Unavailable = "*";
    /** Оригинальная строка, из которой было создано данное правило*/
    protected String originalRuleString;
    /**Кол-во символов для удаления с конца слова, при применении данного правила склонения.*/
    protected int cut;
    /**Новое окончание слова, при применении данного правила склонения.*/
    protected String end;
    /**
     * 
     * @param rule Строка, описывающая правило склонения.
     * Пример:
     * "ен2" - удалить два символа, с конца слова и добавить "ен".
     * "ым" - не удалять ни одного символа, с конца слова, но добавить "ым".
     * "*" - данное правило не применимо. Всегда возвращает Empty
     */
    public CyrRule(String rule)
    {
        if (rule == null || rule.isEmpty()) {
            this.end = "";
            this.cut = 0;
            return;
        }

        ArrayList<Character> endChars = new ArrayList<Character>();
        ArrayList<Character> cutChars = new ArrayList<Character>();

        for (int i = 0; i < rule.length(); i++) {
        	char ch = rule.charAt(i);
            if (Character.isDigit(ch)) {
                cutChars.add(ch);
            }
            else {
                endChars.add(ch);
            }
        }

        this.originalRuleString = rule;
        this.end = endChars.toArray().toString();

        switch (cutChars.size()) {
            case 0:
                this.cut = 0;
                break;
            case 1:
                this.cut = (int)Character.getNumericValue(cutChars.get(0));
                break;
            default:
                this.cut = Integer.parseInt(cutChars.toArray().toString());
                break;
        }
    }
    /**
     * Склоняет указанное слово.
     * @param name Слово для склонения.
     * @return
     */
    public String Apply(String name) {
        if (this.end == Unavailable) {
            return "";
        }

        int length = name.length() - cut;

        if (length <= 0) {
            return this.end;
        }

        return name.substring(0, length) + end;
    }
    /**
     * Отменяет склонение указанного слова.
     * Используется для восстановления исходной формы слов, отсутствующих в словаре.
     * Пример: слово "прасными" будет склоняться по правилу склонения слова "красными", следовательно, исходная форма будет "прасный".
     * @param original Оригинальное слово, для получения удаленного окончания при склонении.
     * @param current Слово для восстановления в исходное положение.
     * @return
     */
    public String Revert(String original, String current) {
        if (this.end == Unavailable) {
            return current;
        }

        int length = current.length() - this.end.length();
        String originalEnd = original.substring(original.length() - this.cut);

        return current.substring(0, length) + originalEnd;
    }
    
    @Override
    public String toString() {
        return this.originalRuleString;
    }
}
