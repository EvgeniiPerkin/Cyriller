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
	 * @return Возвращает результат склонения в виде
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
	 * @return Возвращает результат склонения в виде
	 */
	public CyrNameResult Decline(String fullName, CasesEnum cases, GendersEnum gender, boolean shorten) {
		String[] values = this.Decline(fullName, cases.getValue(), gender.getValue(), shorten);
		CyrNameResult result = new CyrNameResult(values);
		return result;
	}
	public CyrResult Decline(String surname, String name, String patronumic, GendersEnum gender, boolean shorten) {
		String[] cases = new String[6];

        for (CyrDeclineCase c : CyrDeclineCase.GetCollection())  {
            int caseIndex = c.getIndex();
            String[] values = this.Decline(surname, name, patronumic, caseIndex, gender.value, shorten);
            CyrNameResult caseResult = new CyrNameResult(values);

            cases[caseIndex - 1] = caseResult.toString();
        }

        CyrResult result = new CyrResult(cases);

        return result;
	}
	public CyrResult Decline(String fullName, GendersEnum gender, boolean shorten) {
		String[] cases = new String[6];

        for (CyrDeclineCase c : CyrDeclineCase.GetCollection())  {
            int caseIndex = c.getIndex();
            String[] values = this.Decline(fullName, caseIndex, gender.value, shorten);
            CyrNameResult caseResult = new CyrNameResult(values);

            cases[caseIndex - 1] = caseResult.toString();
        }

        CyrResult result = new CyrResult(cases);

        return result;
	}
	/**
	 * Склоняет полное имя в указанный падеж.
	 * @param inputSurname Фамилия, в именительном падеже.
	 * @param inputName Имя, в именительном падеже.
	 * @param inputPatronymic Отчество, в именительном падеже.
	 * @param inputCase Падеж, в который нужно просклонять, где 1 – именительный, 2 – родительный, 3 – дательный, 4 – винительный, 5 – творительный, 6 – предложный.
	 * @param inputGender Пол, указанного имени, где 0 – определить автоматически, 1 – мужской, 2 – женский.
	 * @param inputShorten Сократить ли имя и отчество в результате склонения. К примеру, Иванов Иван Иванович, будет Иванов И. И.
	 * @return Возвращает результат склонения в виде массива из трех элементов [Фамилия, Имя, Отчество].
	 */
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
            patronymic = (patronymicBefore == null ? "" : patronymicBefore) + patronymic + (patronymicAfter == null ? "" : patronymicAfter);
        }

        return new String[] { surname, name, patronymic };
	}
	/**
	 * Склоняет полное имя в указанный падеж.
	 * @param fullName Полное имя, в именительном падеже.
	 * @param cases Падеж, в который нужно просклонять, где 1 – именительный, 2 – родительный, 3 – дательный, 4 – винительный, 5 – творительный, 6 – предложный.
	 * @param gender Пол, указанного имени, где 0 – определить автоматически, 1 – мужской, 2 – женский.
	 * @param shorten Сократить ли имя и отчество в результате склонения. К примеру, Иванов Иван Иванович, будет Иванов И. И.
	 * @return Возвращает результат склонения в виде массива из трех элементов [Фамилия, Имя, Отчество].
	 */
	public String[] Decline(String fullName, int cases, int gender, boolean shorten) {
		String surname = null;
        String name = null;
        String patronymic = null;
        String str1 = null;
        String str2 = null;
        String str3 = null;
        int spaceIndex = 0;

        spaceIndex = fullName.indexOf(" ");

        if (spaceIndex > 0) {
            str1 = fullName.substring(0, spaceIndex).trim();
            fullName = fullName.substring(spaceIndex).trim();

            spaceIndex = fullName.indexOf(" ");

            if (spaceIndex > 0) {
                str2 = fullName.substring(0, spaceIndex).trim();
                str3 = fullName.substring(spaceIndex).trim();
            }
            else {
                str2 = fullName.trim();
            }
        }
        else {
            str1 = fullName.trim();
        }

        if (!(str3 == null || str3.isEmpty())) {
            if (str2.endsWith("ич") || str2.endsWith("вна") || str2.endsWith("чна")) {
                surname = str3;
                name = str1;
                patronymic = str2;
            }
            else {
                surname = str1;
                name = str2;
                patronymic = str3;
            }
        }
        else if (!(str2 == null || str2.isEmpty())) {
            if (str2.endsWith("ич") || str2.endsWith("вна") || str2.endsWith("чна")) {
                name = str1; ;
                patronymic = str2;
            }
            else {
                surname = str1;
                name = str2;
            }
        }
        else {
            surname = str1;
        }

        return Decline(surname, name, patronymic, cases, gender, shorten);
	}
	/**
	 * Родительный, Кого? Чего? (нет)
	 * @param name Имя, для склонения.
	 * @param isFeminine True, для женских имен.
	 * @param shorten Сократить ли имя, к примеру, Иван будет И.
	 * @return Возвращает результат склонения.
	 */
    public String DeclineNameGenitive(String name, boolean isFeminine, boolean shorten) {
        String temp;

        if (this.IsShorten(name)) {
            return name;
        }

        if (shorten) {
            name = this.Shorten(name);
        }
        else {
            temp = name;

            switch (SubstringRight(name, 3).toLowerCase()) {
                case "лев":
                    name = SetEnd(name, 2, "ьва");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)) {
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
                        name = SetEnd(name, "й");
                        break;
                }
            }

            if (name == temp) {
                switch (SubstringRight(name, 1)) {
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
                            name += "а";
                        break;
                }
            }

        }

        return name;
    }
    /**
     * Дательный, Кому? Чему? (дам)
     * @param name Имя, для склонения.
     * @param isFeminine True, для женских имен.
     * @param shorten Сократить ли имя, к примеру, Иван будет И.
     * @return Возвращает результат склонения.
     */
    public String DeclineNameDative(String name, boolean isFeminine, boolean shorten) {
        String temp;

        if (this.IsShorten(name)) {
            return name;
        }

        if (shorten) {
            name = this.Shorten(name);
        }
        else {
            temp = name;

            switch (SubstringRight(name, 3).toLowerCase()) {
                case "лев":
                    name = SetEnd(name, 2, "ьву");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)) {
                    case "ей":
                    case "ий":
                    case "ай":
                        name = SetEnd(name, "ю");
                        break;
                    case "ел":
                        name = SetEnd(name, "лу");
                        break;
                    case "ец":
                        name = SetEnd(name, "цу");
                        break;
                    case "ия":
                        name = SetEnd(name, "ии");
                        break;
                }
            }

            if (name == temp) {
                switch (SubstringRight(name, 1)) {
                    case "а":
                    case "я":
                        name = SetEnd(name, "е");
                        break;
                    case "е":
                    case "ё":
                    case "и":
                    case "о":
                    case "у":
                    case "э":
                    case "ю":
                        break;
                    case "ь":
                        name = SetEnd(name, (isFeminine ? "и" : "ю"));
                        break;
                    default:
                        if (!isFeminine) {
                            name += "у";
                        }
                        break;
                }
            }
        }

        return name;
    }
    /**
     * Винительный, Кого? Что? (вижу)
     * @param name Имя, для склонения.
     * @param isFeminine True, для женских имен.
     * @param shorten Сократить ли имя, к примеру, Иван будет И.
     * @return Возвращает результат склонения.
     */
    public String DeclineNameAccusative(String name, boolean isFeminine, boolean shorten) {
        String temp;

        if (this.IsShorten(name)) {
            return name;
        }

        if (shorten) {
            name = this.Shorten(name);
        }
        else {
            temp = name;

            switch (SubstringRight(name, 3).toLowerCase()) {
                case "лев":
                    name = SetEnd(name, 2, "ьва");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)) {
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
                }
            }

            if (name == temp) {
                switch (SubstringRight(name, 1)) {
                    case "а":
                        name = SetEnd(name, "у");
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
                        name = SetEnd(name, "ю");
                        break;
                    case "ь":
                        if (!isFeminine) {
                            name = SetEnd(name, "я");
                        }
                        break;
                    default:
                        if (!isFeminine)
                            name += "а";
                        break;
                }
            }
        }

        return name;
    }
    /**
     * Творительный, Кем? Чем? (горжусь)
     * @param name Имя, для склонения.
     * @param isFeminine True, для женских имен.
     * @param shorten Сократить ли имя, к примеру, Иван будет И.
     * @return Возвращает результат склонения.
     */
    public String DeclineNameInstrumental(String name, boolean isFeminine, boolean shorten) {
        String temp;

        if (this.IsShorten(name)) {
            return name;
        }

        if (shorten) {
            name = this.Shorten(name);
        }
        else {
            temp = name;

            switch (SubstringRight(name, 3).toLowerCase()) {
                case "лев":
                    name = SetEnd(name, 2, "ьвом");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)) {
                    case "ей":
                    case "ий":
                    case "ай":
                        name = SetEnd(name, 1, "ем");
                        break;
                    case "ел":
                        name = SetEnd(name, 2, "лом");
                        break;
                    case "ец":
                        name = SetEnd(name, 2, "цом");
                        break;
                    case "жа":
                    case "ца":
                    case "ча":
                    case "ша":
                    case "ща":
                        name = SetEnd(name, 1, "ей");
                        break;
                }
            }

            if (name == temp) {
                switch (SubstringRight(name, 1)) {
                    case "а":
                        name = SetEnd(name, 1, "ой");
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
                        name = SetEnd(name, 1, "ей");
                        break;
                    case "ь":
                        name = SetEnd(name, 1, (isFeminine ? "ью" : "ем"));
                        break;
                    default:
                        if (!isFeminine) {
                            name += "ом";
                        }
                        break;
                }
            }
        }

        return name;
    }
    /**
     * Предложный, О ком? О чем? (думаю)
     * @param name Имя, для склонения.
     * @param isFeminine True, для женских имен.
     * @param shorten Сократить ли имя, к примеру, Иван будет И.
     * @return Возвращает результат склонения.
     */
    public String DeclineNamePrepositional(String name, boolean isFeminine, boolean shorten) {
        String temp;

        if (this.IsShorten(name)) {
            return name;
        }

        if (shorten) {
            name = this.Shorten(name);
        }
        else {
            temp = name;

            switch (SubstringRight(name, 3).toLowerCase()) {
                case "лев":
                    name = SetEnd(name, 2, "ьве");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)) {
                    case "ей":
                    case "ай":
                        name = SetEnd(name, "е");
                        break;
                    case "ий":
                        name = SetEnd(name, "и");
                        break;
                    case "ел":
                        name = SetEnd(name, "ле");
                        break;
                    case "ец":
                        name = SetEnd(name, "це");
                        break;
                    case "ия":
                        name = SetEnd(name, "ии");
                        break;
                }
            }

            if (name == temp) {
                switch (SubstringRight(name, 1)) {
                    case "а":
                    case "я":
                        name = SetEnd(name, "е");
                        break;
                    case "е":
                    case "ё":
                    case "и":
                    case "о":
                    case "у":
                    case "э":
                    case "ю":
                        break;
                    case "ь":
                        name = SetEnd(name, (isFeminine ? "и" : "е"));
                        break;
                    default:
                        if (!isFeminine) {
                            name += "е";
                        }
                        break;
                }
            }
        }

        return name;
    }
    /**
     * Родительный, Кого? Чего? (нет)
     * @param patronymic Отчество, для склонения.
     * @param isFeminine True, для женских отчеств.
     * @param shorten Сократить ли отчество, к примеру, Иванович будет И.
     * @return Возвращает результат склонения.
     */
    public String DeclinePatronymicGenitive(String patronymic, boolean isFeminine, boolean shorten) {
        if (this.IsShorten(patronymic)) {
            return patronymic;
        }

        if (shorten) {
            patronymic = this.Shorten(patronymic);
        }
        else {
            switch (SubstringRight(patronymic, 1)) {
                case "а":
                    patronymic = SetEnd(patronymic, "ы");
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
                    patronymic = SetEnd(patronymic, "и");
                    break;
                case "ь":
                    patronymic = SetEnd(patronymic, (isFeminine ? "и" : "я"));
                    break;
                default:
                    if (!isFeminine) {
                        patronymic += "а";
                    }
                    break;
            }
        }

        return patronymic;
    }
    /**
     * Дательный, Кому? Чему? (дам)
     * @param patronymic Отчество, для склонения.
     * @param isFeminine True, для женских отчеств.
     * @param shorten Сократить ли отчество, к примеру, Иванович будет И.
     * @return Возвращает результат склонения.
     */
    public String DeclinePatronymicDative(String patronymic, boolean isFeminine, boolean shorten) {
        if (this.IsShorten(patronymic)) {
            return patronymic;
        }

        if (shorten) {
            patronymic = this.Shorten(patronymic);
        }
        else {
            switch (SubstringRight(patronymic, 1)) {
                case "а":
                case "я":
                    patronymic = SetEnd(patronymic, "е");
                    break;
                case "е":
                case "ё":
                case "и":
                case "о":
                case "у":
                case "э":
                case "ю":
                    break;
                case "ь":
                    patronymic = SetEnd(patronymic, (isFeminine ? "и" : "ю"));
                    break;
                default:
                    if (!isFeminine) {
                        patronymic += "у";
                    }
                    break;
            }
        }

        return patronymic;
    }
    /**
     * Винительный, Кого? Что? (вижу)
     * @param patronymic Отчество, для склонения.
     * @param isFeminine True, для женских отчеств.
     * @param shorten Сократить ли отчество, к примеру, Иванович будет И.
     * @return Возвращает результат склонения.
     */
    public String DeclinePatronymicAccusative(String patronymic, boolean isFeminine, boolean shorten) {
        if (this.IsShorten(patronymic)) {
            return patronymic;
        }

        if (shorten) {
            patronymic = this.Shorten(patronymic);
        }
        else {
            switch (SubstringRight(patronymic, 1)) {
                case "а":
                    patronymic = SetEnd(patronymic, "у");
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
                    patronymic = SetEnd(patronymic, "ю");
                    break;
                case "ь":
                    if (!isFeminine)
                        patronymic = SetEnd(patronymic, "я");
                    break;
                default:
                    if (!isFeminine)
                        patronymic += "а";
                    break;
            }
        }

        return patronymic;
    }
    /**
     *  Творительный, Кем? Чем? (горжусь)
     * @param patronymic Отчество, для склонения.
     * @param isFeminine True, для женских отчеств.
     * @param shorten Сократить ли отчество, к примеру, Иванович будет И.
     * @return Возвращает результат склонения.
     */
    public String DeclinePatronymicInstrumental(String patronymic, boolean isFeminine, boolean shorten) {
        String temp;

        if (this.IsShorten(patronymic)) {
            return patronymic;
        }

        if (shorten) {
            patronymic = this.Shorten(patronymic);
        }
        else {
            temp = patronymic;

            switch (SubstringRight(patronymic, 2)) {
                case "ич":
                    patronymic = patronymic + (patronymic.toLowerCase() == "ильич" ? "ом" : "ем");
                    break;
                case "на":
                    patronymic = SetEnd(patronymic, 2, "ной");
                    break;
            }

            if (patronymic == temp) {
                switch (SubstringRight(patronymic, 1)) {
                    case "а":
                        patronymic = SetEnd(patronymic, 1, "ой");
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
                        patronymic = SetEnd(patronymic, 1, "ей");
                        break;
                    case "ь":
                        patronymic = SetEnd(patronymic, 1, (isFeminine ? "ью" : "ем"));
                        break;
                    default:
                        if (!isFeminine) {
                            patronymic += "ом";
                        }
                        break;
                }
            }
        }

        return patronymic;
    }
    /**
     * Творительный, Кем? Чем? (горжусь)
     * @param patronymic Отчество, для склонения.
     * @param isFeminine True, для женских отчеств.
     * @param shorten Сократить ли отчество, к примеру, Иванович будет И.
     * @return Возвращает результат склонения.
     */
    public String DeclinePatronymicPrepositional(String patronymic, boolean isFeminine, boolean shorten) {
        if (this.IsShorten(patronymic)) {
            return patronymic;
        }

        if (shorten) {
            patronymic = this.Shorten(patronymic);
        }
        else {
            switch (SubstringRight(patronymic, 1)) {
                case "а":
                case "я":
                    patronymic = SetEnd(patronymic, "е");
                    break;
                case "е":
                case "ё":
                case "и":
                case "о":
                case "у":
                case "э":
                case "ю":
                    break;
                case "ь":
                    patronymic = SetEnd(patronymic, (isFeminine ? "и" : "е"));
                    break;
                default:
                    if (!isFeminine) {
                        patronymic += "е";
                    }
                    break;
            }
        }

        return patronymic;
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
		
		if (surname.length() <= 1 || cases < 2 || cases > 6) {
            result = surname;
            return result;
        }
		
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
        String temp = surname;
        String end;

        end = SubstringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
                case "жий":
                case "ний":
                case "ций":
                case "чий":
                case "ший":
                case "щий":
                    surname = SetEnd(surname, "ем");
                    break;
                case "лец":
                    surname = SetEnd(surname, 2, "ьце");
                    break;
            }
        }
        else {
            switch (end) {
                case "ова":
                case "ева":
                case "ина":
                case "ына":
                    surname = SetEnd(surname, 1, "ой");
                    break;
                case "жая":
                case "цая":
                case "чая":
                case "шая":
                case "щая":
                    surname = SetEnd(surname, "ей");
                    break;
                case "ска":
                case "цка":
                    surname = SetEnd(surname, 1, "ой");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 2);

        switch (end) {
            case "ия":
                surname = SetEnd(surname, "и");
                break;
        }

        if (surname != temp) {
            return surname;
        }

        if (!isFeminine) {
            switch (end) {
                case "ок":
                    surname = SetEnd(surname, "ке");
                    break;
                case "ёк":
                case "ек":
                    surname = SetEnd(surname, 2, "ьке");
                    break;
                case "ец":
                    surname = SetEnd(surname, "це");
                    break;
                case "ий":
                case "ый":
                case "ой":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, "ом");
                    }
                    break;
                case "ей":
                    if (surname.toLowerCase() == "соловей" || surname.toLowerCase() == "воробей") {
                        surname = SetEnd(surname, "ье");
                    }
                    else {
                        surname = SetEnd(surname, "ее");
                    }
                    break;
            }
        }
        else {
            switch (end) {
                case "ая":
                    surname = SetEnd(surname, "ой");
                    break;
                case "яя":
                    surname = SetEnd(surname, "ей");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = SetEnd(surname, "е");
                            break;
                    }
                    break;
                case "я":
                    surname = SetEnd(surname, "е");
                    break;
                case "б":
                case "в":
                case "г":
                case "д":
                case "ж":
                case "з":
                case "к":
                case "л":
                case "м":
                case "н":
                case "п":
                case "р":
                case "с":
                case "т":
                case "ф":
                case "ц":
                case "ч":
                case "ш":
                case "щ":
                    surname = surname + "е";
                    break;
                case "х":
                    if (!surname.endsWith("их") && !surname.endsWith("ых")) {
                        surname += "е";
                    }
                    break;
                case "ь":
                case "й":
                    surname = SetEnd(surname, "е");
                    break;
            }
        }
        else {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
	                    case "а":
	                    case "е":
	                    case "ё":
	                    case "и":
	                    case "о":
	                    case "у":
	                    case "э":
	                    case "ы":
	                    case "ю":
	                    case "я":
                            break;
                        default:
                            surname = SetEnd(surname, "е");
                            break;
                    }
                    break;
                case "я":
                    surname = SetEnd(surname, "е");
                    break;
            }
        }

        return surname;
    }
	/**
	 * Творительный, Кем? Чем? (горжусь)
	 * @param surname Фамилия, для склонения.
	 * @param isFeminine True, для женских фамилий.
	 * @return Возвращает результат склонения.
	 */
	public String DeclineSurnameInstrumental(String surname, boolean isFeminine) {
		String temp = surname;
        String end;

        end = SubstringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
	            case "лец":
	                surname = SetEnd(surname, 2, "ьцом");
	                break;
	            case "бец":
	                surname = SetEnd(surname, 2, "цем");
	                break;
	            case "кой":
	                surname = SetEnd(surname, "им");
	                break;
            }
        }
        else {
            switch (end) {
	            case "жая":
	            case "цая":
	            case "чая":
	            case "шая":
	            case "щая":
	                surname = SetEnd(surname, "ей");
	                break;
	            case "ска":
	            case "цка":
	                surname = SetEnd(surname, 1, "ой");
	                break;
	            case "еца":
	            case "ица":
	            case "аца":
	            case "ьца":
	                surname = SetEnd(surname, 1, "ей");
	                break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 2);

        if (!isFeminine)
        {
            switch (end) {
                case "ок":
                    surname = SetEnd(surname, 2, "ком");
                    break;
                case "ёк":
                case "ек":
                    surname = SetEnd(surname, 2, "ьком");
                    break;
                case "ец":
                    surname = SetEnd(surname, 2, "цом");
                    break;
                case "ий":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, "им");
                    }
                    break;
                case "ый":
                case "ой":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, "ым");
                    }
                    break;
                case "ей":
                    if (surname.toLowerCase() == "соловей" || surname.toLowerCase() == "воробей") {
                        surname = SetEnd(surname, 2, "ьем");
                    }
                    else {
                        surname = SetEnd(surname, 2, "еем");
                    }
                    break;
                case "оч":
                case "ич":
                case "иц":
                case "ьц":
                case "ьш":
                case "еш":
                case "ыш":
                case "яц":
                    surname = surname + "ем";
                    break;
                case "ин":
                case "ын":
                case "ен":
                case "эн":
                case "ов":
                case "ев":
                case "ёв":
                case "ун":
                    if (surname.toLowerCase() != "дарвин" && surname.toLowerCase() != "франклин" && surname.toLowerCase() != "чаплин" && surname.toLowerCase() != "грин") {
                        surname = surname + "ым";
                    }
                    break;
                case "жа":
                case "ца":
                case "ча":
                case "ша":
                case "ща":
                    surname = SetEnd(surname, 1, "ей");
                    break;
            }
        }
        else {
            switch (end) {
                case "ая":
                    surname = SetEnd(surname, "ой");
                    break;
                case "яя":
                    surname = SetEnd(surname, "ей");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "ой");
                            break;
                    }
                    break;
                case "я":
                    surname = SetEnd(surname, 1, "ей");
                    break;
                case "б":
                case "в":
                case "г":
                case "д":
                case "ж":
                case "з":
                case "к":
                case "л":
                case "м":
                case "н":
                case "п":
                case "р":
                case "с":
                case "т":
                case "ф":
                case "ц":
                case "ч":
                case "ш":
                    surname = surname + "ом";
                    break;
                case "х":
                    if (!surname.endsWith("их") && !surname.endsWith("ых")) {
                        surname = surname + "ом";
                    }
                    break;
                case "щ":
                    surname += "ем";
                    break;
                case "ь":
                case "й":
                    surname = SetEnd(surname, 1, "ем");
                    break;
            }
        }
        else {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "ой");
                            break;
                    }
                    break;
                case "я":
                    surname = SetEnd(surname, 1, "ей");
                    break;
            }
        }

        return surname;
	}
	/**
	 * Винительный, Кого? Что? (вижу)
	 * @param surname Фамилия, для склонения.
	 * @param isFeminine True, для женских фамилий.
	 * @return Возвращает результат склонения.
	 */
	public String DeclineSurnameAccusative(String surname, boolean isFeminine) {
		String temp = surname;
        String end;

        end = SubstringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
	            case "жий":
	            case "ний":
	            case "ций":
	            case "чий":
	            case "ший":
	            case "щий":
                    surname = SetEnd(surname, 2, "его");
                    break;
                case "лец":
                    surname = SetEnd(surname, 2, "ьца");
                    break;
            }
        }
        else {
            switch (end) {
	            case "ова":
	            case "ева":
	            case "ина":
	            case "ына":
                    surname = SetEnd(surname, "у");
                    break;
                case "ска":
                case "цка":
                    surname = SetEnd(surname, 1, "ую");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 2);

        if (!isFeminine) {
            switch (end) {
                case "ок":
                    surname = SetEnd(surname, "ка");
                    break;
                case "ёк":
                case "ек":
                    surname = SetEnd(surname, 2, "ька");
                    break;
                case "ец":
                    surname = SetEnd(surname, "ца");
                    break;
                case "ий":
                case "ый":
                case "ой":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, 2, "ого");
                    }
                    break;
                case "ей":
                    if (surname.toLowerCase() == "соловей" || surname.toLowerCase() == "воробей") {
                        surname = SetEnd(surname, "ья");
                    }
                    else {
                        surname = SetEnd(surname, "ея");
                    }
                    break;
            }
        }
        else {
            switch (end) {
                case "ая":
                    surname = SetEnd(surname, "ую");
                    break;
                case "яя":
                    surname = SetEnd(surname, "юю");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
	                    case "а":
	                    case "е":
	                    case "ё":
	                    case "и":
	                    case "о":
	                    case "у":
	                    case "э":
	                    case "ы":
	                    case "ю":
	                    case "я":
                            break;
                        default:
                            surname = SetEnd(surname, "у");
                            break;
                    }
                    break;
                case "я":
                    surname = SetEnd(surname, "ю");
                    break;
	                case "б":
	                case "в":
	                case "г":
	                case "д":
	                case "ж":
	                case "з":
	                case "к":
	                case "л":
	                case "м":
	                case "н":
	                case "п":
	                case "р":
	                case "с":
	                case "т":
	                case "ф":
	                case "ц":
	                case "ч":
	                case "ш":
	                case "щ":
	                    surname += "а";
	                    break;
	                case "х":
	                    if (!surname.endsWith("их") && !surname.endsWith("ых")) {
	                        surname += "а";
	                    }
	                    break;
	                case "ь":
	                case "й":
                    surname = SetEnd(surname, "я");
                    break;
            }
        }
        else {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
	                    case "а":
	                    case "е":
	                    case "ё":
	                    case "и":
	                    case "о":
	                    case "у":
	                    case "э":
	                    case "ы":
	                    case "ю":
	                    case "я":
                            break;
                        default:
                            surname = SetEnd(surname, "у");
                            break;
                    }
                    break;
                case "я":
                    surname = SetEnd(surname, "ю");
                    break;
            }
        }

        return surname;
	}
	/**
	 * Дательный, Кому? Чему? (дам)
	 * @param surname Фамилия, для склонения.
	 * @param isFeminine True, для женских фамилий.
	 * @return Возвращает результат склонения.
	 */
	public String DeclineSurnameDative(String surname, boolean isFeminine) {
		String temp = surname;
        String end;

        end = SubstringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
	            case "жий":
	            case "ний":
	            case "ций":
	            case "чий":
	            case "ший":
	            case "щий":
                    surname = SetEnd(surname, 2, "ему");
                    break;
                case "лец":
                    surname = SetEnd(surname, 2, "ьцу");
                    break;
            }
        }
        else {
            switch (end) {
	            case "ова":
	            case "ева":
	            case "ина":
	            case "ына":
                    surname = SetEnd(surname, 1, "ой");
                    break;
                case "жая":
                case "цая":
                case "чая":
                case "шая":
                case "щая":
                    surname = SetEnd(surname, 2, "ей");
                    break;
                case "ска":
                case "цка":
                    surname = SetEnd(surname, 1, "ой");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 2);

        switch (end) {
            case "ия":
                surname = SetEnd(surname, 1, "и");
                break;
        }

        if (surname != temp) {
            return surname;
        }

        if (!isFeminine) {
            switch (end) {
                case "ок":
                    surname = SetEnd(surname, 2, "ку");
                    break;
                case "ёк":
                case "ек":
                    surname = SetEnd(surname, 2, "ьку");
                    break;
                case "ец":
                    surname = SetEnd(surname, 2, "��");
                    break;
                case "ий":
                case "ый":
                case "ой":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, 2, "ому");
                    }
                    break;
                case "ей":
                    if (surname.toLowerCase() == "соловей" || surname.toLowerCase() == "воробей") {
                        surname = SetEnd(surname, 2, "ью");
                    }
                    else {
                        surname = SetEnd(surname, 2, "ею");
                    }
                    break;
            }
        }
        else {
            switch (end) {
                case "ая":
                    surname = SetEnd(surname, 2, "ой");
                    break;
                case "яя":
                    surname = SetEnd(surname, 2, "ей");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
	                    case "а":
	                    case "е":
	                    case "ё":
	                    case "и":
	                    case "о":
	                    case "у":
	                    case "э":
	                    case "ы":
	                    case "ю":
	                    case "я":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "е");
                            break;
                    }
                    break;
                case "я":
                    surname = SetEnd(surname, 1, "е");
                    break;
                case "б":
                case "в":
                case "г":
                case "д":
                case "ж":
                case "з":
                case "к":
                case "л":
                case "м":
                case "н":
                case "п":
                case "р":
                case "с":
                case "т":
                case "ф":
                case "ц":
                case "ч":
                case "ш":
                case "щ":
                    surname += "у";
                    break;
                case "х":
                    if (!surname.endsWith("их") && !surname.endsWith("ых")) {
                        surname += "у";
                    }
                    break;
                case "ь":
                case "й":
                    surname = SetEnd(surname, 1, "ю");
                    break;
            }
        }
        else {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
	                    case "а":
	                    case "е":
	                    case "ё":
	                    case "и":
	                    case "о":
	                    case "у":
	                    case "э":
	                    case "ы":
	                    case "ю":
	                    case "я":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "е");
                            break;
                    }
                    break;
                case "я":
                    surname = SetEnd(surname, 1, "е");
                    break;
            }
        }

        return surname;
	}
	/**
	 * Родительный, Кого? Чего? (нет)
	 * @param surname Фамилия, для склонения.
	 * @param isFeminine True, для женских фамилий.
	 * @return Возвращает результат склонения.
	 */
	public String DeclineSurnameGenitive(String surname, boolean isFeminine) {
		String temp = surname;
        String end = null;

        end = SubstringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
	            case "жий":
	            case "ний":
	            case "ций":
	            case "чий":
	            case "ший":
	            case "щий":
                    surname = SetEnd(surname, 2, "его");
                    break;
                case "лец":
                    surname = SetEnd(surname, 2, "ьца");
                    break;
                case "нок":
                    surname = SetEnd(surname, "нка");
                    break;
            }
        }
        else {
            switch (end) {
	            case "ова":
	            case "ева":
	            case "ина":
	            case "ына":
                    surname = SetEnd(surname, 1, "ой");
                    break;
                case "жая":
                case "цая":
                case "чая":
                case "шая":
                case "щая":
                    surname = SetEnd(surname, 2, "ей");
                    break;
                case "ска":
                case "цка":
                    surname = SetEnd(surname, 1, "ой");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 2);

        switch (end) {
	        case "га":
	        case "жа":
	        case "ка":
	        case "ха":
	        case "ча":
	        case "ша":
	        case "ща":
                surname = SetEnd(surname, 1, "и");
                break;
        }

        if (surname != temp) {
            return surname;
        }

        if (!isFeminine) {
            switch (end) {
                case "ок":
                    surname = SetEnd(surname, 1, "ка");
                    break;
                case "ёк":
                case "ек":
                    surname = SetEnd(surname, 2, "ька");
                    break;
                case "ец":
                    surname = SetEnd(surname, 2, "ца");
                    break;
                case "ий":
                case "ый":
                case "ой":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, 2, "ого");
                    }
                    break;
                case "ей":
                    if (surname.toLowerCase() == "соловей" || surname.toLowerCase() == "воробей") {
                        surname = SetEnd(surname, 2, "ья");
                    }
                    else {
                        surname = SetEnd(surname, 2, "ея");
                    }
                    break;
            }
        }
        else {
            switch (end) {
                case "ая":
                    surname = SetEnd(surname, 2, "ой");
                    break;
                case "яя":
                    surname = SetEnd(surname, 2, "ей");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
	                    case "а":
	                    case "е":
	                    case "ё":
	                    case "и":
	                    case "о":
	                    case "у":
	                    case "э":
	                    case "ы":
	                    case "ю":
	                    case "я":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "ы");
                            break;
                    }
                    break;
                case "я":
                    surname = SetEnd(surname, 1, "и");
                    break;
                case "б":
                case "в":
                case "г":
                case "д":
                case "ж":
                case "з":
                case "к":
                case "л":
                case "м":
                case "н":
                case "п":
                case "р":
                case "с":
                case "т":
                case "ф":
                case "ц":
                case "ч":
                case "ш":
                case "щ":
                    surname += "а";
                    break;
                case "х":
                    if (!surname.endsWith("их") && !surname.endsWith("ых")) {
                        surname +="а";
                    }
                    break;
                case "ь":
                case "й":
                    surname = SetEnd(surname, 1, "я");
                    break;
            }
        }
        else {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
	                    case "а":
	                    case "е":
	                    case "ё":
	                    case "и":
	                    case "о":
	                    case "у":
	                    case "э":
	                    case "ы":
	                    case "ю":
	                    case "я":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "ы");
                            break;
                    }
                    break;
                case "я":
                    surname = SetEnd(surname, 1, "и");
                    break;
            }
        }

        return surname;
	}

	/**
	 *  Используется для разбивки составных отчеств, к примеру тюркские варианты Салим-оглы или Салим-кызы.
	 * @param fullPatronymic Полное отчество. Примеры: "Салим-оглы", "Салим Оглы", "Ибн Салим".
	 * @return Возвращает суффиксную часть составного отчества, которая не должна склоняться.
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
	protected String SetEnd(String value, String add) {
        return SetEnd(value, add.length(), add);
    }
    protected String SetEnd(String value, int cut, String add) {
        return value.substring(0, value.length() - cut) + add;
    }
    protected String SubstringRight(String value, int cut) {
        if (cut > value.length()) {
            cut = value.length();
        }

        return value.substring(value.length() - cut);
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
	 *
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
	 * 
	 * 
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
