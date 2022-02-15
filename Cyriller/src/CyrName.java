import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CyrName {
	private Pattern patronymicSuffixRegex;
	private Pattern patronymicPrefixRegex;

	public CyrName() {
		patronymicSuffixRegex = Pattern.compile("((?i)[- ](((?i)(оглы)|(улы)|(уулу))|([(?i)кг](?i)ызы)))$", Pattern.UNICODE_CASE);
		patronymicPrefixRegex = Pattern.compile("^((?i)ибн[- ])", Pattern.UNICODE_CASE);
	}
	
	/**
	 * Склоняет полное имя в указанный падеж.
	 * @param surname Фамилия, в именительном падеже.
	 * @param name Имя, в именительном падеже.
	 * @param patronumic Отчество, в именительном падеже.
	 * @param cases Падеж, в который нужно просклонять.
	 * @param gender Пол, указанного имени
	 * @param shorten Сократить ли имя и отчество в результате склонения. К примеру, Иванов Иван Иванович, будет Иванов И. И.
	 * @return Возвращает результат склонения
	 */
	public CyrNameResult Decline(String surname, String name, String patronumic, CasesEnum cases, GendersEnum gender, boolean shorten) {
		String[] values = this.Decline(surname, name, patronumic, cases.getValue(), gender.getValue(), shorten);
		CyrNameResult result = new CyrNameResult(values);
		return result;
	}
	/**
	 * Склоняет полное имя в указанный падеж.
	 * @param fullName Полное имя, в именительном падеже.
	 * @param cases Падеж, в который нужно просклонять.
	 * @param gender Пол, указанного имени
	 * @param shorten Сократить ли имя и отчество в результате склонения. К примеру, Иванов Иван Иванович, будет Иванов И. И.
	 * @return Возвращает результат склонения
	 */
	public CyrNameResult Decline(String fullName, CasesEnum cases, GendersEnum gender, boolean shorten) {
		String[] values = this.Decline(fullName, cases.getValue(), gender.getValue(), shorten);
		CyrNameResult result = new CyrNameResult(values);
		return result;
	}
	public CyrResult Decline(String surname, String name, String patronumic, GendersEnum gender, boolean shorten) {
		return null;
	}
	public CyrResult Decline(String fullName, GendersEnum gender, boolean shorten) {
		return null;
	}
	public String[] Decline(String inputSurname, String inputName, String inputPatronymic, int inputCase, int inputGender, boolean inputShorten) {
		String temp = null;
        int caseNumber = 0;
        String surname = null;
        String name = null;
        String patronymic = null;
        String patronymicAfter = null;
        String patronymicBefore = null;
        boolean declinePatronymic = true;
        int gender = 0;
        boolean isFeminine = false;
        int index = 0;
        String surnameNew = null;
        String surnameOld = null;

        caseNumber = inputCase;
        gender = inputGender;
        surname = this.ProperCase(inputSurname);
        name = this.ProperCase(inputName);
        
        Patronymic p =  SplitPatronymic(inputPatronymic);
        patronymic = p.getPatronymic();
        patronymicAfter = p.getSuffix();
        patronymicBefore = p.getPrefix();
        
        declinePatronymic = patronymicAfter == null || patronymicAfter.isEmpty();
        patronymic = this.ProperCase(patronymic);
        
        if (caseNumber < 1 || caseNumber > 6) {
            caseNumber = 1;
        }

        if (gender < 0 || gender > 2) {
            gender = 0;
        }
        
        if (gender == 0) {
            gender = patronymic.endsWith("на") ? 2 : 1;
        }

        isFeminine = (gender == 2);

        surnameOld = surname;
        surnameNew = "";
        index = surnameOld.indexOf("-");
        
        while (index > 0) {
            temp = this.ProperCase(surnameOld.substring(0, index));
            surnameNew = surnameNew + this.DeclineSurname(temp, caseNumber, isFeminine) + "-";
            surnameOld = surnameOld.substring(index + 1);
            index = surnameOld.indexOf("-");
        }

        temp = this.ProperCase(surnameOld);
        surnameNew = surnameNew + this.DeclineSurname(temp, caseNumber, isFeminine);
        surname = surnameNew;
        
        switch (caseNumber){
            case 1:
                if (inputShorten) {
                    name = this.Shorten(name);
                    patronymic = this.Shorten(patronymic);
                }
                break;
            case 2:
                name = this.DeclineNameGenitive(name, isFeminine, inputShorten);

                if (declinePatronymic || inputShorten) {
                    patronymic = this.DeclinePatronymicGenitive(patronymic, isFeminine, inputShorten);
                }
                break;

            case 3:
                name = this.DeclineNameDative(name, isFeminine, inputShorten);

                if (declinePatronymic || inputShorten) {
                    patronymic = this.DeclinePatronymicDative(patronymic, isFeminine, inputShorten);
                }
                break;

            case 4:
                name = this.DeclineNameAccusative(name, isFeminine, inputShorten);

                if (declinePatronymic || inputShorten) {
                    patronymic = this.DeclinePatronymicAccusative(patronymic, isFeminine, inputShorten);
                }
                break;

            case 5:
                name = this.DeclineNameInstrumental(name, isFeminine, inputShorten);

                if (declinePatronymic || inputShorten) {
                    patronymic = this.DeclinePatronymicInstrumental(patronymic, isFeminine, inputShorten);
                }
                break;

            case 6:
                name = this.DeclineNamePrepositional(name, isFeminine, inputShorten);

                if (declinePatronymic || inputShorten) {
                    patronymic = this.DeclinePatronymicPrepositional(patronymic, isFeminine, inputShorten);
                }
                break;
        }

        if (!inputShorten){
            patronymic = patronymicBefore + patronymic + patronymicAfter;
        }

        return new String[] { surname, name, patronymic };
	}
	public String[] Decline(String fullName, int cases, int gender, boolean shorten) {
		return null;
	}

    public String DeclineNameGenitive(String name, boolean isFeminine, boolean shorten)
    {
        String temp;

        if (this.IsShorten(name)){
            return name;
        }

        if (shorten) {
            name = this.Shorten(name);
        }
        else{
            temp = name;

            switch (SubstringRight(name, 3).ToLower()) {
                case "лев":
                    name = SetEnd(name, 2, "ьва");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)){
                    case "ей":
                    case "ий":
                    case "ай":
                        name = SetEnd(name, "я");
                        break;
                    case "ел":
                        name = SetEnd(name, "ла");
                        break;
                    case "ец":
                        name = SetEnd(name, "ца");
                        break;
                    case "га":
                    case "жа":
                    case "ка":
                    case "ха":
                    case "ча":
                    case "ща":
                        name = SetEnd(name, "и");
                        break;
                }
            }

            if (name == temp) {
                switch (SubstringRight(name, 1)){
                    case "а":
                        name = SetEnd(name, "ы");
                        break;
                    case "е":
                    case "ё":
                    case "и":
                    case "о":
                    case "у":
                    case "э":
                    case "ю":
                        break;
                    case "я":
                        name = SetEnd(name, "и");
                        break;
                    case "ь":
                        name = SetEnd(name, (isFeminine ? "и" : "я"));
                        break;
                    default:
                        if (!isFeminine)
                            name = name + "а";
                        break;
                }
            }

        }

        return name;
    }
	/**
	 * Склоняет фамилию в указанный падеж.
	 * @param surname Фамилия, в именительном падеже.
	 * @param cases Падеж, в который нужно просклонять фамилию, где 1 – именительный, 2 – родительный, 3 – дательный, 4 – винительный, 5 – творительный, 6 – предложный.
	 * @param isFeminine True, для женских фамилий.
	 * @return Возвращает результат склонения.
	 */
	public String DeclineSurname(String surname, int cases, boolean isFeminine) {
		String result = surname;
		
		switch (cases){
            case 2:
                result = this.DeclineSurnameGenitive(surname, isFeminine);
                break;
            case 3:
                result = this.DeclineSurnameDative(surname, isFeminine);
                break;
            case 4:
                result = this.DeclineSurnameAccusative(surname, isFeminine);
                break;
            case 5:
                result = this.DeclineSurnameInstrumental(surname, isFeminine);
                break;
            case 6:
                result = this.DeclineSurnamePrepositional(surname, isFeminine);
                break;
        }

        return result;
	}
	/**
	 * Предложный, О ком? О чем? (думаю)
	 * @param surname Фамилия, для склонения.
	 * @param isFeminine True, для женских фамилий.
	 * @return Возвращает результат склонения.
	 */
	public String DeclineSurnamePrepositional(String surname, boolean isFeminine) {
		return null;
	}
	/**
	 * Творительный, Кем? Чем? (горжусь)
	 * @param surname Фамилия, для склонения.
	 * @param isFeminine True, для женских фамилий.
	 * @return Возвращает результат склонения.
	 */
	public String DeclineSurnameInstrumental(String surname, boolean isFeminine) {
		return null;
	}
	/**
	 * Винительный, Кого? Что? (вижу)
	 * @param surname Фамилия, для склонения.
	 * @param isFeminine True, для женских фамилий.
	 * @return Возвращает результат склонения.
	 */
	public String DeclineSurnameAccusative(String surname, boolean isFeminine) {
		return null;
	}
	/**
	 * Дательный, Кому? Чему? (дам)
	 * @param surname Фамилия, для склонения.
	 * @param isFeminine True, для женских фамилий.
	 * @return Возвращает результат склонения.
	 */
	public String DeclineSurnameDative(String surname, boolean isFeminine) {
		return null;
	}
	/**
	 * Родительный, Кого? Чего? (нет)
	 * @param surname Фамилия, для склонения.
	 * @param isFeminine True, для женских фамилий.
	 * @return Возвращает результат склонения.
	 */
	public String DeclineSurnameGenitive(String surname, boolean isFeminine) {
		return null;
	}

	/**
	 * Используется для разбивки составных отчеств, к примеру тюркские варианты Салим-оглы или Салим-кызы.
	 * @param fullPatronymic Полное отчество. Примеры: "Салим-оглы", "Салим Оглы", "Ибн Салим".
	 * @return возвращает отчество
	 */
	public Patronymic SplitPatronymic(String fullPatronymic) {
		Patronymic result = new Patronymic();
		if (fullPatronymic == null || fullPatronymic.isEmpty()) {
			result.setPrefix(null);
			result.setPatronymic(fullPatronymic);
			result.setSuffix(null);
			return result;
		}

		result.setPatronymic(fullPatronymic);

        Matcher prefixMatch = this.patronymicPrefixRegex.matcher(fullPatronymic);
        Matcher suffixMatch = this.patronymicSuffixRegex.matcher(fullPatronymic);

        result.setPatronymic(fullPatronymic);
        
        boolean isPrefix = false;
        
        while (prefixMatch.find()){
        	result.setPrefix(prefixMatch.group());
        	result.setPatronymic(prefixMatch.replaceAll(""));
            isPrefix = true;
	    }
	        
        if (!isPrefix){
        	result.setPrefix(null);
        }
        
        boolean isSuffiix = false;
        
        while (suffixMatch.find()){
        	result.setSuffix(suffixMatch.group());
        	result.setPatronymic(suffixMatch.replaceAll(""));
            isSuffiix = true;
	    }
        if (!isSuffiix){
        	result.setSuffix(null);
        }
        
		return result;
	}
	public String ProperCase(String value) {
		if (value != null) {
			value = value.replaceAll("[\uFEFF-\uFEFF]", "").trim();
		}
		
		if(value == null || value.isEmpty()) { 
			return "";
		
		}
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}
	/**
	 * Сокращает указанное значение. Пример: "Петровна" -> "П.", "Николай" -> "Н.".
	 * @param value
	 * @return Возвращает null, если входное значение является пустой строкой или null.
	 */
	protected String Shorten(String value){
        if (isNullOrWhitespace(value)) {
            return null;
        }

        String result = new String(new char[] { value.charAt(0), '.' });

        return result;
    }
	/**
	 * Указывает, является ли указанная строка нулевой, пустой или состоит только из пробелов.
	 * @param s
	 * @return
	 */
	public static boolean isNullOrWhitespace(String s) {
	    return s == null || isWhitespace(s);

	}
	
	private static boolean isWhitespace(String s) {
	    int length = s.length();
	    if (length > 0) {
	        for (int i = 0; i < length; i++) {
	            if (!Character.isWhitespace(s.charAt(i))) {
	                return false;
	            }
	        }
	        return true;
	    }
	    return false;
	}
	/**
	 * Возвращает true, если входная строка является пустой, null или заканчивается на точку (.).
	 * Пример: "Петровна" -> false, "Н." -> true.
	 * @param value
	 * @return 
	 */
	protected boolean IsShorten(String value)
    {
        if (value == null || value.isEmpty()) {
        	return true;
        }

        return value.charAt(value.length() - 1) == '.';
    }
}
