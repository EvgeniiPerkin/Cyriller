
public class Strings {
    private String[] hundreds;
    private String[] tens;
    private String[] numbers;
    private String[] thousand;
    private String[] million;
    private String[] billion;
    private String zero;
	private String[] integer;
    private String[] decimalTen;
    private String[] decimalHundred;
    private String[] decimalThousand;
    private String[] decimalMillion;
    private String[] decimalBillion;

    public Strings(CasesEnum cases, GendersEnum gender, AnimatesEnum animate) {

        switch (cases) {
            case Nominative:
                this.hundreds = new String[] { "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот" };
                this.tens = new String[] { "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто" };
                this.numbers = new String[] { "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать" };
                this.thousand = new String[] { "тысяча", "тысячи", "тысяч" };
                this.million = new String[] { "миллион", "миллиона", "миллионов" };
                this.billion = new String[] { "миллиард", "миллиарда", "миллиардов" };
                this.zero = "ноль";

                if (gender == GendersEnum.Feminine) {
                    this.integer = new String[] { "целая", "целые", "целых" };
                    this.decimalTen = new String[] { "десятая", "десятые", "десятых" };
                    this.decimalHundred = new String[] { "сотая", "сотые", "сотых" };
                    this.decimalThousand = new String[] { "тысячная", "тысячные", "тысячных" };
                    this.decimalMillion = new String[] { "миллионная", "миллионные", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардная", "миллиардные", "миллиардных" };
                }
                else {
                    this.integer = new String[] { "целый", "целых", "целых" };
                    this.decimalTen = new String[] { "десятый", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотый", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячный", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионный", "миллионных", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардный", "миллиардных", "миллиардных" };
                }
                break;
            case Genitive:
                this.hundreds = new String[] { "ста", "двухсот", "трехсот", "четырехсот", "пятисот", "шестисот", "семисот", "восьмисот", "девятисот" };
                this.tens = new String[] { "десяти", "двадцати", "тридцати", "сорока", "пятидесяти", "шестидесяти", "семидесяти", "весьмидесяти", "девяноста" };
                this.numbers = new String[] { "одного", "двух", "трех", "четырех", "пяти", "шести", "семи", "восьми", "девяти", "десяти", "одиннадцати", "двенадцати", "тринадцати", "четырнадцати", "пятнадцати", "шестнадцати", "семнадцати", "восемнадцати", "девятнадцати" };
                this.thousand = new String[] { "тысячи", "тысяч", "тысяч" };
                this.million = new String[] { "миллиона", "миллионов", "миллионов" };
                this.billion = new String[] { "миллиарда", "миллиардов", "миллиардов" };
                this.zero = "ноля";

                if (gender == GendersEnum.Feminine) {
                    this.integer = new String[] { "целой", "целыми", "целыми" };
                    this.decimalTen = new String[] { "десятой", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотой", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячной", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионной", "миллионных", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардной", "миллиардных", "миллиардных" };
                }
                else {
                    this.integer = new String[] { "целого", "целых", "целых" };
                    this.decimalTen = new String[] { "десятого", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотого", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячного", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионного", "миллионных", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардного", "миллиардных", "миллиардных" };
                }
                break;
            case Dative:
                this.hundreds = new String[] { "ста", "двумстам", "тремстам", "четыремстам", "пятистам", "шестистам", "семистам", "восьмистам", "девятистам" };
                this.tens = new String[] { "десяти", "двадцати", "тридцати", "сорока", "пятидесяти", "шестидесяти", "семидесяти", "весьмидесяти", "девяноста" };
                this.numbers = new String[] { "одному", "двум", "трем", "четырем", "пяти", "шести", "семи", "восьми", "девяти", "десяти", "одиннадцати", "двенадцати", "тринадцати", "четырнадцати", "пятнадцати", "шестнадцати", "семнадцати", "восемнадцати", "девятнадцати" };
                this.thousand = new String[] { "тысяче", "тысячам", "тысячам" };
                this.million = new String[] { "миллиону", "миллионам", "миллионам" };
                this.billion = new String[] { "миллиарду", "миллиардам", "миллиардам" };
                this.zero = "ноля";

                if (gender == GendersEnum.Feminine) {
                    this.integer = new String[] { "целой", "целым", "целым" };
                    this.decimalTen = new String[] { "десятой", "десятым", "десятым" };
                    this.decimalHundred = new String[] { "сотой", "сотым", "сотым" };
                    this.decimalThousand = new String[] { "тысячной", "тысячным", "тысячным" };
                    this.decimalMillion = new String[] { "миллионной", "миллионным", "миллионным" };
                    this.decimalBillion = new String[] { "миллиардной", "миллиардным", "миллиардным" };
                }
                else {
                    this.integer = new String[] { "целому", "целым", "целым" };
                    this.decimalTen = new String[] { "десятому", "десятым", "десятым" };
                    this.decimalHundred = new String[] { "сотому", "сотым", "сотым" };
                    this.decimalThousand = new String[] { "тысячному", "тысячным", "тысячным" };
                    this.decimalMillion = new String[] { "миллионному", "миллионным", "миллионным" };
                    this.decimalBillion = new String[] { "миллиардному", "миллиардным", "миллиардным" };
                }
                break;
            case Accusative:
                this.hundreds = new String[] { "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот" };
                this.tens = new String[] { "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто" };

                if (animate == AnimatesEnum.Animated) {
                    this.numbers = new String[] { "одного", "двух", "трех", "четырех", "пять", "шести", "семи", "восьми", "девяти", "десяти", "одиннадцати", "двенадцати", "тринадцати", "четырнадцати", "пятнадцати", "шестнадцати", "семнадцати", "восемнадцати", "девятнадцати" };
                }
                else {
                    this.numbers = new String[] { "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать" };
                }

                this.thousand = new String[] { "тысячу", "тысячи", "тысяч" };
                this.million = new String[] { "миллион", "миллиона", "миллионов" };
                this.billion = new String[] { "миллиард", "миллиарда", "миллиардов" };
                this.zero = "ноль";

                if (gender == GendersEnum.Feminine) {
                    this.integer = new String[] { "целую", "целые", "целых" };
                    this.decimalTen = new String[] { "десятую", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотую", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячную", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионную", "миллионныех", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардную", "миллиардных", "миллиардных" };
                }
                else {
                    this.integer = new String[] { "целого", "целых", "целых" };
                    this.decimalTen = new String[] { "десятый", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотый", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячный", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионный", "миллионныех", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардный", "миллиардных", "миллиардных" };
                }
                break;
            case Instrumental:
                this.hundreds = new String[] { "ста", "двумястами", "тремястами", "четырьмястами", "пятьюстами", "шестьюстами", "семьюстами", "восьмьюстами", "девятьюстами" };
                this.tens = new String[] { "десятью", "двадцатью", "тридцатью", "сорока", "пятьюдесятью", "шестьюдесятью", "семьюдесятью", "восьмьюдесятью", "девяноста" };
                this.numbers = new String[] { "одним", "двумя", "тремя", "четырьмя", "пятью", "шестью", "семью", "восемью", "девятью", "десятью", "одиннадцатью", "двенадцатью", "тринадцатью", "четырнадцатью", "пятнадцатью", "шестнадцатью", "семнадцатью", "восемнадцатью", "девятнадцатью" };
                this.thousand = new String[] { "тысячей", "тысячами", "тысячами" };
                this.million = new String[] { "миллионом", "миллионами", "миллионами" };
                this.billion = new String[] { "миллиардом", "миллиардами", "миллиардами" };
                this.zero = "нолем";

                if (gender == GendersEnum.Feminine) {
                    this.integer = new String[] { "целой", "целыми", "целыми" };
                    this.decimalTen = new String[] { "десятой", "десятыми", "десятыми" };
                    this.decimalHundred = new String[] { "сотой", "сотыми", "сотыми" };
                    this.decimalThousand = new String[] { "тысячной", "тысячными", "тысячными" };
                    this.decimalMillion = new String[] { "миллионной", "миллионными", "миллионными" };
                    this.decimalBillion = new String[] { "миллиардной", "миллиардными", "миллиардными" };
                }
                else {
                    this.integer = new String[] { "целым", "целыми", "целыми" };
                    this.decimalTen = new String[] { "десятым", "десятыми", "десятыми" };
                    this.decimalHundred = new String[] { "сотым", "сотыми", "сотыми" };
                    this.decimalThousand = new String[] { "тысячным", "тысячными", "тысячными" };
                    this.decimalMillion = new String[] { "миллионным", "миллионными", "миллионными" };
                    this.decimalBillion = new String[] { "миллиардным", "миллиардными", "миллиардными" };
                }
                break;
            case Prepositional:
                this.hundreds = new String[] { "ста", "двухстах", "трехстах", "четырехстах", "пятистах", "шестистах", "семистах", "восьмистах", "девятистах" };
                this.tens = new String[] { "десяти", "двадцати", "тридцати", "сорока", "пятидесяти", "шестидесяти", "семидесяти", "восьмидесяти", "девяноста" };
                this.numbers = new String[] { "одном", "двух", "трех", "четырех", "пяти", "шести", "семи", "восеми", "девяти", "десяти", "одиннадцати", "двенадцати", "тринадцати", "четырнадцати", "пятнадцати", "шестнадцати", "семнадцати", "восемнадцати", "девятнадцати" };
                this.thousand = new String[] { "тысяче", "тысячах", "тысячах" };
                this.million = new String[] { "миллионе", "миллионах", "миллионах" };
                this.billion = new String[] { "миллиарде", "миллиардах", "миллиардах" };
                this.zero = "ноле";

                if (gender == GendersEnum.Feminine) {
                    this.integer = new String[] { "целой", "целых", "целых" };
                    this.decimalTen = new String[] { "десятой", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотой", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячной", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионной", "миллионных", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардной", "миллиардных", "миллиардных" };
                }
                else {
                    this.integer = new String[] { "целом", "целых", "целых" };
                    this.decimalTen = new String[] { "десятом", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотом", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячном", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионном", "миллионных", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардном", "миллиардных", "миллиардных" };
                }
                break;
        }

        if (gender == GendersEnum.Feminine) {
            switch (cases) {
                case Nominative:
                    this.numbers[0] = "одна";
                    this.numbers[1] = "две";
                    break;
                case Genitive:
                    this.numbers[0] = "одной";
                    this.numbers[1] = "двух";
                    break;
                case Dative:
                    this.numbers[0] = "одной";
                    this.numbers[1] = "двум";
                    break;
                case Accusative:
                    this.numbers[0] = "одну";

                    if (animate == AnimatesEnum.Animated) {
                        this.numbers[1] = "двух";
                    }
                    else {
                        this.numbers[1] = "две";
                    }

                    break;
                case Instrumental:
                    this.numbers[0] = "одной";
                    this.numbers[1] = "двумя";
                    break;
                case Prepositional:
                    this.numbers[0] = "одной";
                    this.numbers[1] = "двух";
                    break;
            }
        }
        else if (gender == GendersEnum.Neuter) {
            switch (cases) {
                case Nominative:
                    this.numbers[0] = "одно";
                    break;
                case Genitive:
                    this.numbers[0] = "одного";
                    break;
                case Dative:
                    this.numbers[0] = "одному";
                    break;
                case Accusative:
                    this.numbers[0] = "одно";
                    break;
                case Instrumental:
                    this.numbers[0] = "одним";
                    break;
                case Prepositional:
                    this.numbers[0] = "одном";
                    break;
            }
        }    	
    }
    
    public String[] getHundreds() {
		return hundreds;
	}
	public void setHundreds(String[] hundreds) {
		this.hundreds = hundreds;
	}
	public String[] getTens() {
		return tens;
	}
	public void setTens(String[] tens) {
		this.tens = tens;
	}
	public String[] getNumbers() {
		return numbers;
	}
	public void setNumbers(String[] numbers) {
		this.numbers = numbers;
	}
	public String[] getThousand() {
		return thousand;
	}
	public void setThousand(String[] thousand) {
		this.thousand = thousand;
	}
	public String[] getMillion() {
		return million;
	}
	public void setMillion(String[] million) {
		this.million = million;
	}
	public String[] getBillion() {
		return billion;
	}
	public void setBillion(String[] billion) {
		this.billion = billion;
	}
	public String getZero() {
		return zero;
	}
	public void setZero(String zero) {
		this.zero = zero;
	}
	public String[] getInteger() {
		return integer;
	}
	public void setInteger(String[] integer) {
		this.integer = integer;
	}
	public String[] getDecimalTen() {
		return decimalTen;
	}
	public void setDecimalTen(String[] decimalTen) {
		this.decimalTen = decimalTen;
	}
	public String[] getDecimalHundred() {
		return decimalHundred;
	}
	public void setDecimalHundred(String[] decimalHundred) {
		this.decimalHundred = decimalHundred;
	}
	public String[] getDecimalThousand() {
		return decimalThousand;
	}
	public void setDecimalThousand(String[] decimalThousand) {
		this.decimalThousand = decimalThousand;
	}
	public String[] getDecimalMillion() {
		return decimalMillion;
	}
	public void setDecimalMillion(String[] decimalMillion) {
		this.decimalMillion = decimalMillion;
	}
	public String[] getDecimalBillion() {
		return decimalBillion;
	}
	public void setDecimalBillion(String[] decimalBillion) {
		this.decimalBillion = decimalBillion;
	}
}
