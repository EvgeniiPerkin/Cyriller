import java.util.ArrayList;

public class CyrNumber {
	/**Максимальное число для написания прописью*/
    public final long MaxValue = 9999999999L;
    /**
     * Склоняет число прописью в мужском роде  для неодушевленных предметов
     * @param value
     * @return
     */
    public CyrResult Decline(long value) {
        return this.Decline(value, GendersEnum.Masculine, AnimatesEnum.Inanimated);
    }
    /**
     * Склоняет число прописью в указанном роде и одушевленности.
     * @param value
     * @param gender
     * @param animate
     * @return
     */
    public CyrResult Decline(long value, GendersEnum gender, AnimatesEnum animate)
    {
        CyrResult result = new CyrResult(
            this.toString(value, CasesEnum.Nominative, gender, animate),
            this.toString(value, CasesEnum.Genitive, gender, animate),
            this.toString(value, CasesEnum.Dative, gender, animate),
            this.toString(value, CasesEnum.Accusative, gender, animate),
            this.toString(value, CasesEnum.Instrumental, gender, animate),
            this.toString(value, CasesEnum.Prepositional, gender, animate)
        );

        return result;
    }
    /**
     * Склоняет число прописью в мужском роде для неодушевленных предметов
     * @param value
     * @return
     */
    public CyrResult Decline(double value)
    {
        return this.Decline(value, GendersEnum.Masculine, AnimatesEnum.Inanimated);
    }
    /**
     * Склоняет число прописью в указанном роде и одушевленности.
     * @param value
     * @param gender
     * @param animate
     * @return
     */
    public CyrResult Decline(double value, GendersEnum gender, AnimatesEnum animate)
    {
        CyrResult result = new CyrResult(
            this.toString(value, CasesEnum.Nominative, gender, animate),
            this.toString(value, CasesEnum.Genitive, gender, animate),
            this.toString(value, CasesEnum.Dative, gender, animate),
            this.toString(value, CasesEnum.Accusative, gender, animate),
            this.toString(value, CasesEnum.Instrumental, gender, animate),
            this.toString(value, CasesEnum.Prepositional, gender, animate)
        );

        return result;
    }
    /**
     * Склоняет денежную сумму в указанной валюте.
     * @param value
     * @param currency
     * @return
     */
    public CyrResult Decline(double value, Currency currency) {
        CyrResult result = new CyrResult(
            this.toString(value, CasesEnum.Nominative, currency),
            this.toString(value, CasesEnum.Genitive, currency),
            this.toString(value, CasesEnum.Dative, currency),
            this.toString(value, CasesEnum.Accusative, currency),
            this.toString(value, CasesEnum.Instrumental, currency),
            this.toString(value, CasesEnum.Prepositional, currency)
        );

        return result;
    } 
    /**
     * Склоняет количество указанных единиц.
     * @param value
     * @param item
     * @return
     */
    public CyrResult Decline(double value, Item item)
    {
        CyrResult result = new CyrResult(
            this.toString(value, CasesEnum.Nominative, item),
            this.toString(value, CasesEnum.Genitive, item),
            this.toString(value, CasesEnum.Dative, item),
            this.toString(value, CasesEnum.Accusative, item),
            this.toString(value, CasesEnum.Instrumental, item),
            this.toString(value, CasesEnum.Prepositional, item)
        );

        return result;
    }
    /**
     * Склоняет число прописью в указнный падеж мужского рода для неодушевленных предметов 
     * @param value
     * @param cases
     * @return
     */
    public String toString(long value, CasesEnum cases)
    {
        return this.toString(value, cases, GendersEnum.Masculine, AnimatesEnum.Inanimated);
    }
    /**
     * Склоняет число прописью в указнный падеж мужского рода  для неодушевленных предметов
     * @param value
     * @param cases
     * @return
     */
    public String toString(double value, CasesEnum cases)
    {
        return this.toString(value, cases, GendersEnum.Masculine, AnimatesEnum.Inanimated);
    }
    /**
     * Склоняет число прописью в указнный падеж, род и одушевленность.
     * @param value
     * @param cases
     * @param gender
     * @param animate
     * @return
     */
    public String toString(double value, CasesEnum cases, GendersEnum gender, AnimatesEnum animate) {
    	String str = Double.toString(value);
    	String[] parts = str.split("\\.");
        long i = Long.parseLong(parts[0], 10);
        StringBuilder sb = new StringBuilder();
        Strings s = new Strings(cases, gender, animate);
    	
        sb.append(this.toString(i, cases, gender, animate));
        
        if (parts.length > 1) {
            parts[1] = parts[1].replaceAll("[0]+$", "");;
        }
        
        if (parts.length > 1 && !(parts[1] == null || parts[1].isEmpty())) {
            String[] decimals;
            long d = Long.parseLong(parts[1], 10);
            String builder = new StringBuilder(parts[1]).reverse().toString();
            long dr = Long.parseLong(builder);
            
            if (dr < 10) {
                decimals = s.getDecimalTen();
            }
            else if (dr < 100) {
                decimals = s.getDecimalHundred();
            }
            else if (dr < 1000) {
                decimals = s.getDecimalThousand();
            }
            else if (dr < 1000000) {
                decimals = s.getDecimalMillion();
            }
            else {
                decimals = s.getDecimalBillion();
            }

            sb.append(" ");
            sb.append(this.Case(i, s.getInteger()[0], s.getInteger()[1], s.getInteger()[2]));
            sb.append(" и ");
            sb.append(this.toString(d, cases, gender, animate));
            sb.append(" ");
            sb.append(this.Case(d, decimals[0], decimals[1], decimals[2]));
        }
        
    	return sb.toString();
    }
    /**
     * Склоняет денежную сумму в указанный падеж и валюту.
     * @param value
     * @param cases
     * @param currency
     * @return
     */
    public String toString(double value, CasesEnum cases, Currency currency) {
    	String str = Double.toString(value);
        String[] parts = str.split("\\.");
        long i = Long.parseLong(parts[0], 10);
        StringBuilder sb = new StringBuilder();
        Strings s = new Strings(cases, currency.getIntegerGender(), AnimatesEnum.Inanimated);
        String[] iname = currency.GetIntegerName(cases);
        String[] dname = currency.GetDecimalName(cases);

        sb.append(this.toString(i, cases, currency.getIntegerGender(), AnimatesEnum.Inanimated));
        sb.append(" ").append(this.Case(i, iname[0], iname[1], iname[2]));

        if (parts.length > 1) {
            parts[1] = parts[1].replaceAll("[0]+$", "");;
        }

        if (parts.length > 1 && !(parts[1] == null || parts[1].isEmpty())) {
            String v = parts[1];

            while (v.length() < currency.getDecimals()) {
                v += "0";
            }

            if (v.length() > currency.getDecimals()) {
            	ArrayList<String> chars = new ArrayList<String>();
            	
            	for (char c : v.toCharArray()) {
            	  chars.add(String.valueOf(c));
            	}

                chars.add(currency.getDecimals(), ".");
                v = String.join("", chars);
            }

            long d = (long)Math.round(Double.parseDouble(v));

            sb.append(" и ");
            sb.append(this.toString(d, cases, currency.getDecimalGender(), AnimatesEnum.Inanimated));
            sb.append(" ");
            sb.append(this.Case(d, dname[0], dname[1], dname[2]));
        }

        return sb.toString();
    }
    /**
     * Склоняет количество указанных единиц в указанный падеж.
     * @param value
     * @param cases
     * @param item
     * @return
     */
    public String toString(double value, CasesEnum cases, Item item) {

        long i = (long)value;
        StringBuilder sb = new StringBuilder();
        //Strings s = new Strings(Case, Item.Gender, Item.Animate);
        GendersEnum gender = i < value ? GendersEnum.Feminine : item.getGender();
        AnimatesEnum animate = i == value && i < 20 ? item.GetAnimate() : AnimatesEnum.Inanimated;
        String[] name;

        sb.append(this.toString(value, cases, gender, animate)).append(" ");

        if (i < value) {
            name = item.GetName(CasesEnum.Nominative, i);
            sb.append(name[1]);
        }
        else {
            name = item.GetName(cases, i);
            sb.append(this.Case(i, name[0], name[1], name[2]));
        }

        return sb.toString();
    }
    /**
     * Склоняет число прописью в указнный падеж, род и одушевленность.
     *  Выбрасывает Exception, если значение больше максимально допустимого MaxValue/>.
     * @param value
     * @param cases
     * @param gender
     * @param animate
     * @return
     */
    public String toString(long value, CasesEnum cases, GendersEnum gender, AnimatesEnum animate) {
    	if (value > MaxValue) {
            throw new IllegalArgumentException();
        }

        Strings s = new Strings(cases, gender, animate);

        if (value == 0) {
            return s.getZero();
        }

        StringBuilder r = new StringBuilder();
        long v;

        if (value < 0) {
            r.append("минус ");
            value = Math.abs(value);
        }

        v = value / 1000000000;

        if (v > 0) {
            r.append(this.toString(v, cases, GendersEnum.Masculine, animate)).append(" ").append(this.Case(v, s.getBillion()[0], s.getBillion()[1], s.getBillion()[2])).append(" ");
            value = value - 1000000000 * v;
        }

        v = value / 1000000;

        if (v > 0) {
            r.append(this.toString(v, cases, GendersEnum.Masculine, animate)).append(" ").append(this.Case(v, s.getMillion()[0], s.getMillion()[1], s.getMillion()[2])).append(" ");
            value = value - 1000000 * v;
        }

        v = value / 1000;

        if (v > 0) {
            r.append(this.toString(v, cases, GendersEnum.Feminine, animate)).append(" ").append(this.Case(v, s.getThousand()[0], s.getThousand()[1], s.getThousand()[2])).append(" ");
            value = value - 1000 * v;
        }

        v = value / 100;

        if (v > 0) {
            r.append(s.getHundreds()[(int)v - 1]).append(" ");
            value = value - 100 * v;
        }

        if (value >= 20 || value == 10) {
            v = value / 10;
            r.append(s.getTens()[(int)v - 1]).append(" ");
            value = value - v * 10;
        }

        if (value > 0) {
            r.append(s.getNumbers()[(int)value - 1]);
        }

        return r.toString().trim();
    } 
    /**
     * Выбирает правильный вариант слова в зависимости от указанного числа.
     * @param value Число для выбора правильного варианта слова.
     * @param one Вариант слова для употребления с числительным один.
     * @param two Вариант слова для употребления с числительными два, три, четыре.
     * @param five Вариант слова для употребления с числительными больше четырех.
     * @return
     */
    public String Case(long value, String one, String two, String five) {
        long t = (value % 100 > 20) ? value % 10 : value % 20;

        switch ((int)t) {
            case 1:
                return one;
            case 2:
            case 3:
            case 4:
                return two;
            default:
                return five;
        }
    }
}
