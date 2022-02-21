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
	 * �������� ������ ��� � ��������� �����.
	 * @param fullName ������ ���, � ������������ ������.
	 * @param cases �����, � ������� ����� �����������.
	 * @param gender ���, ���������� �����
	 * @param shorten ��������� �� ��� � �������� � ���������� ���������. � �������, ������ ���� ��������, ����� ������ �. �.
	 * @return ���������� ��������� ���������
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
            patronymic = patronymicBefore + patronymic + patronymicAfter;
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
	 * �����������, ����? ����? (���)
	 * @param name ���, ��� ���������
	 * @param isFeminine True, ��� ������� ����.
	 * @param shorten ��������� �� ���, � �������, ���� ����� �.
	 * @return ���������� ��������� ���������.
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
                case "���":
                    name = SetEnd(name, 2, "���");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)) {
                    case "��":
                    case "��":
                    case "��":
                        name = SetEnd(name, "�");
                        break;
                    case "��":
                        name = SetEnd(name, "��");
                        break;
                    case "��":
                        name = SetEnd(name, "��");
                        break;
                    case "��":
                    case "��":
                    case "��":
                    case "��":
                    case "��":
                    case "��":
                        name = SetEnd(name, "�");
                        break;
                }
            }

            if (name == temp) {
                switch (SubstringRight(name, 1)) {
                    case "�":
                        name = SetEnd(name, "�");
                        break;
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                        break;
                    case "�":
                        name = SetEnd(name, "�");
                        break;
                    case "�":
                        name = SetEnd(name, (isFeminine ? "�" : "�"));
                        break;
                    default:
                        if (!isFeminine)
                            name = name + "�";
                        break;
                }
            }

        }

        return name;
    }
    /**
     * ���������, ����? ����? (���)
     * @param name ���, ��� ���������.
     * @param isFeminine True, ��� ������� ����.
     * @param shorten ��������� �� ���, � �������, ���� ����� �.
     * @return ���������� ��������� ���������.
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
                case "���":
                    name = SetEnd(name, 2, "���");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)) {
                    case "��":
                    case "��":
                    case "��":
                        name = SetEnd(name, "�");
                        break;
                    case "��":
                        name = SetEnd(name, "��");
                        break;
                    case "��":
                        name = SetEnd(name, "��");
                        break;
                    case "��":
                        name = SetEnd(name, "��");
                        break;
                }
            }

            if (name == temp) {
                switch (SubstringRight(name, 1)) {
                    case "�":
                    case "�":
                        name = SetEnd(name, "�");
                        break;
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                        break;
                    case "�":
                        name = SetEnd(name, (isFeminine ? "�" : "�"));
                        break;
                    default:
                        if (!isFeminine) {
                            name = name + "�";
                        }
                        break;
                }
            }
        }

        return name;
    }
    /**
     * �����������, ����? ���? (����)
     * @param name ���, ��� ���������.
     * @param isFeminine True, ��� ������� ����.
     * @param shorten ��������� �� ���, � �������, ���� ����� �.
     * @return ���������� ��������� ���������.
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
                case "���":
                    name = SetEnd(name, 2, "���");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)) {
                    case "��":
                    case "��":
                    case "��":
                        name = SetEnd(name, "�");
                        break;
                    case "��":
                        name = SetEnd(name, "��");
                        break;
                    case "��":
                        name = SetEnd(name, "��");
                        break;
                }
            }

            if (name == temp) {
                switch (SubstringRight(name, 1)) {
                    case "�":
                        name = SetEnd(name, "�");
                        break;
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                        break;
                    case "�":
                        name = SetEnd(name, "�");
                        break;
                    case "�":
                        if (!isFeminine) {
                            name = SetEnd(name, "�");
                        }
                        break;
                    default:
                        if (!isFeminine)
                            name = name + "�";
                        break;
                }
            }
        }

        return name;
    }
    /**
     * ������������, ���? ���? (�������)
     * @param name ���, ��� ���������.
     * @param isFeminine True, ��� ������� ����.
     * @param shorten ��������� �� ���, � �������, ���� ����� �.
     * @return ���������� ��������� ���������.
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
                case "���":
                    name = SetEnd(name, 2, "����");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)) {
                    case "��":
                    case "��":
                    case "��":
                        name = SetEnd(name, 1, "��");
                        break;
                    case "��":
                        name = SetEnd(name, 2, "���");
                        break;
                    case "��":
                        name = SetEnd(name, 2, "���");
                        break;
                    case "��":
                    case "��":
                    case "��":
                    case "��":
                    case "��":
                        name = SetEnd(name, 1, "��");
                        break;
                }
            }

            if (name == temp) {
                switch (SubstringRight(name, 1)) {
                    case "�":
                        name = SetEnd(name, 1, "��");
                        break;
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                        break;
                    case "�":
                        name = SetEnd(name, 1, "��");
                        break;
                    case "�":
                        name = SetEnd(name, 1, (isFeminine ? "��" : "��"));
                        break;
                    default:
                        if (!isFeminine) {
                            name = name + "��";
                        }
                        break;
                }
            }
        }

        return name;
    }
    /**
     * ����������, � ���? � ���? (�����)
     * @param name ���, ��� ���������.
     * @param isFeminine True, ��� ������� ����.
     * @param shorten ��������� �� ���, � �������, ���� ����� �.
     * @return ���������� ��������� ���������.
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
                case "���":
                    name = SetEnd(name, 2, "���");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)) {
                    case "��":
                    case "��":
                        name = SetEnd(name, "�");
                        break;
                    case "��":
                        name = SetEnd(name, "�");
                        break;
                    case "��":
                        name = SetEnd(name, "��");
                        break;
                    case "��":
                        name = SetEnd(name, "��");
                        break;
                    case "��":
                        name = SetEnd(name, "��");
                        break;
                }
            }

            if (name == temp) {
                switch (SubstringRight(name, 1)) {
                    case "�":
                    case "�":
                        name = SetEnd(name, "�");
                        break;
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                        break;
                    case "�":
                        name = SetEnd(name, (isFeminine ? "�" : "�"));
                        break;
                    default:
                        if (!isFeminine) {
                            name = name + "�";
                        }
                        break;
                }
            }
        }

        return name;
    }
    /**
     * �����������, ����? ����? (���)
     * @param patronymic ��������, ��� ���������.
     * @param isFeminine True, ��� ������� �������.
     * @param shorten ��������� �� ��������, � �������, �������� ����� �.
     * @return ���������� ��������� ���������.
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
                case "�":
                    patronymic = SetEnd(patronymic, "�");
                    break;
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                    break;
                case "�":
                    patronymic = SetEnd(patronymic, "�");
                    break;
                case "�":
                    patronymic = SetEnd(patronymic, (isFeminine ? "�" : "�"));
                    break;
                default:
                    if (!isFeminine) {
                        patronymic = patronymic + "�";
                    }
                    break;
            }
        }

        return patronymic;
    }
    /**
     * ���������, ����? ����? (���)
     * @param patronymic ��������, ��� ���������.
     * @param isFeminine True, ��� ������� �������.
     * @param shorten ��������� �� ��������, � �������, �������� ����� �.
     * @return ���������� ��������� ���������.
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
                case "�":
                case "�":
                    patronymic = SetEnd(patronymic, "�");
                    break;
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                    break;
                case "�":
                    patronymic = SetEnd(patronymic, (isFeminine ? "�" : "�"));
                    break;
                default:
                    if (!isFeminine) {
                        patronymic = patronymic + "�";
                    }
                    break;
            }
        }

        return patronymic;
    }
    /**
     * �����������, ����? ���? (����)
     * @param patronymic ��������, ��� ���������.
     * @param isFeminine True, ��� ������� �������.
     * @param shorten ��������� �� ��������, � �������, �������� ����� �.
     * @return ���������� ��������� ���������.
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
                case "�":
                    patronymic = SetEnd(patronymic, "�");
                    break;
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                    break;
                case "�":
                    patronymic = SetEnd(patronymic, "�");
                    break;
                case "�":
                    if (!isFeminine)
                        patronymic = SetEnd(patronymic, "�");
                    break;
                default:
                    if (!isFeminine)
                        patronymic = patronymic + "�";
                    break;
            }
        }

        return patronymic;
    }
    /**
     * ������������, ���? ���? (�������)
     * @param patronymic ��������, ��� ���������.
     * @param isFeminine True, ��� ������� �������.
     * @param shorten ��������� �� ��������, � �������, �������� ����� �.
     * @return ���������� ��������� ���������.
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
                case "��":
                    patronymic = patronymic + (patronymic.toLowerCase() == "�����" ? "��" : "��");
                    break;
                case "��":
                    patronymic = SetEnd(patronymic, 2, "���");
                    break;
            }

            if (patronymic == temp) {
                switch (SubstringRight(patronymic, 1)) {
                    case "�":
                        patronymic = SetEnd(patronymic, 1, "��");
                        break;
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                    case "�":
                        break;
                    case "�":
                        patronymic = SetEnd(patronymic, 1, "��");
                        break;
                    case "�":
                        patronymic = SetEnd(patronymic, 1, (isFeminine ? "��" : "��"));
                        break;
                    default:
                        if (!isFeminine) {
                            patronymic = patronymic + "��";
                        }
                        break;
                }
            }
        }

        return patronymic;
    }
    /**
     * ������������, ���? ���? (�������)
     * @param patronymic ��������, ��� ���������.
     * @param isFeminine True, ��� ������� �������.
     * @param shorten ��������� �� ��������, � �������, �������� ����� �.
     * @return ���������� ��������� ���������.
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
                case "�":
                case "�":
                    patronymic = SetEnd(patronymic, "�");
                    break;
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                    break;
                case "�":
                    patronymic = SetEnd(patronymic, (isFeminine ? "�" : "�"));
                    break;
                default:
                    if (!isFeminine) {
                        patronymic = patronymic + "�";
                    }
                    break;
            }
        }

        return patronymic;
    }
	/**
	 * �������� ������� � ��������� �����.
	 * @param surname �������, � ������������ ������.
	 * @param cases �����, � ������� ����� ����������� �������, ��� 1 � ������������, 2 � �����������, 3 � ���������, 4 � �����������, 5 � ������������, 6 � ����������.
	 * @param isFeminine True, ��� ������� �������.
	 * @return ���������� ��������� ���������.
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
	 * ����������, � ���? � ���? (�����)
	 * @param surname �������, ��� ���������.
	 * @param isFeminine True, ��� ������� �������.
	 * @return ���������� ��������� ���������.
	 */
	public String DeclineSurnamePrepositional(String surname, boolean isFeminine) {
        String temp = surname;
        String end;

        end = SubstringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, "��");
                    break;
                case "���":
                    surname = SetEnd(surname, 2, "���");
                    break;
            }
        }
        else {
            switch (end) {
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, 1, "��");
                    break;
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, "��");
                    break;
                case "���":
                case "���":
                    surname = SetEnd(surname, 1, "��");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 2);

        switch (end) {
            case "��":
                surname = SetEnd(surname, "�");
                break;
        }

        if (surname != temp) {
            return surname;
        }

        if (!isFeminine) {
            switch (end) {
                case "��":
                    surname = SetEnd(surname, "��");
                    break;
                case "��":
                case "��":
                    surname = SetEnd(surname, 2, "���");
                    break;
                case "��":
                    surname = SetEnd(surname, "��");
                    break;
                case "��":
                case "��":
                case "��":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, "��");
                    }
                    break;
                case "��":
                    if (surname.toLowerCase() == "�������" || surname.toLowerCase() == "�������") {
                        surname = SetEnd(surname, "��");
                    }
                    else {
                        surname = SetEnd(surname, "��");
                    }
                    break;
            }
        }
        else {
            switch (end) {
                case "��":
                    surname = SetEnd(surname, "��");
                    break;
                case "��":
                    surname = SetEnd(surname, "��");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "�":
                    switch (surname.substring(surname.length() - 2, 1)) {
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                            break;
                        default:
                            surname = SetEnd(surname, "�");
                            break;
                    }
                    break;
                case "�":
                    surname = SetEnd(surname, "�");
                    break;
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                    surname = surname + "�";
                    break;
                case "�":
                    if (!surname.endsWith("��") && !surname.endsWith("��")) {
                        surname = surname + "�";
                    }
                    break;
                case "�":
                case "�":
                    surname = SetEnd(surname, "�");
                    break;
            }
        }
        else {
            switch (end) {
                case "�":
                    switch (surname.substring(surname.length() - 2, 1)) {
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                            break;
                        default:
                            surname = SetEnd(surname, "�");
                            break;
                    }
                    break;
                case "�":
                    surname = SetEnd(surname, "�");
                    break;
            }
        }

        return surname;
    }
	/**
	 * ������������, ���? ���? (�������)
	 * @param surname �������, ��� ���������.
	 * @param isFeminine True, ��� ������� �������.
	 * @return ���������� ��������� ���������.
	 */
	public String DeclineSurnameInstrumental(String surname, boolean isFeminine) {
		String temp = surname;
        String end;

        end = SubstringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
                case "���":
                    surname = SetEnd(surname, 2, "����");
                    break;
                case "���":
                    surname = SetEnd(surname, 2, "���");
                    break;
                case "���":
                    surname = SetEnd(surname, "��");
                    break;
            }
        }
        else {
            switch (end) {
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, "��");
                    break;
                case "���":
                case "���":
                    surname = SetEnd(surname, 1, "��");
                    break;
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, 1, "��");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 2);

        if (!isFeminine) {
            switch (end) {
                case "��":
                    surname = SetEnd(surname, 2, "���");
                    break;
                case "��":
                case "��":
                    surname = SetEnd(surname, 2, "����");
                    break;
                case "��":
                    surname = SetEnd(surname, 2, "���");
                    break;
                case "��":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, "��");
                    }
                    break;
                case "��":
                case "��":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, "��");
                    }
                    break;
                case "��":
                    if (surname.toLowerCase() == "�������" || surname.toLowerCase() == "�������") {
                        surname = SetEnd(surname, 2, "���");
                    }
                    else {
                        surname = SetEnd(surname, 2, "���");
                    }
                    break;
                case "��":
                case "��":
                case "��":
                case "��":
                case "��":
                case "��":
                case "��":
                case "��":
                    surname = surname + "��";
                    break;
                case "��":
                case "��":
                case "��":
                case "��":
                case "��":
                case "��":
                case "��":
                case "��":
                    if (surname.toLowerCase() != "������" && surname.toLowerCase() != "��������" && surname.toLowerCase() != "������" && surname.toLowerCase() != "����") {
                        surname = surname + "��";
                    }
                    break;
                case "��":
                case "��":
                case "��":
                case "��":
                case "��":
                    surname = SetEnd(surname, 1, "��");
                    break;
            }
        }
        else {
            switch (end) {
                case "��":
                    surname = SetEnd(surname, "��");
                    break;
                case "��":
                    surname = SetEnd(surname, "��");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "�":
                    switch (surname.substring(surname.length() - 2, 1)) {
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "��");
                            break;
                    }
                    break;
                case "�":
                    surname = SetEnd(surname, 1, "��");
                    break;
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                    surname = surname + "��";
                    break;
                case "�":
                    if (!surname.endsWith("��") && !surname.endsWith("��")) {
                        surname = surname + "��";
                    }
                    break;
                case "�":
                    surname = surname + "��";
                    break;
                case "�":
                case "�":
                    surname = SetEnd(surname, 1, "��");
                    break;
            }
        }
        else {
            switch (end) {
                case "�":
                    switch (surname.substring(surname.length() - 2, 1)) {
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "��");
                            break;
                    }
                    break;
                case "�":
                    surname = SetEnd(surname, 1, "��");
                    break;
            }
        }

        return surname;
	}
	/**
	 * �����������, ����? ���? (����)
	 * @param surname �������, ��� ���������.
	 * @param isFeminine True, ��� ������� �������.
	 * @return ���������� ��������� ���������.
	 */
	public String DeclineSurnameAccusative(String surname, boolean isFeminine) {
		String temp = surname;
        String end;

        end = SubstringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, 2, "���");
                    break;
                case "���":
                    surname = SetEnd(surname, 2, "���");
                    break;
            }
        }
        else {
            switch (end) {
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, "�");
                    break;
                case "���":
                case "���":
                    surname = SetEnd(surname, 1, "��");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 2);

        if (!isFeminine) {
            switch (end) {
                case "��":
                    surname = SetEnd(surname, "��");
                    break;
                case "��":
                case "��":
                    surname = SetEnd(surname, 2, "���");
                    break;
                case "��":
                    surname = SetEnd(surname, "��");
                    break;
                case "��":
                case "��":
                case "��":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, 2, "���");
                    }
                    break;
                case "��":
                    if (surname.toLowerCase() == "�������" || surname.toLowerCase() == "�������") {
                        surname = SetEnd(surname, "��");
                    }
                    else {
                        surname = SetEnd(surname, "��");
                    }
                    break;
            }
        }
        else {
            switch (end) {
                case "��":
                    surname = SetEnd(surname, "��");
                    break;
                case "��":
                    surname = SetEnd(surname, "��");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "�":
                    switch (surname.substring(surname.length() - 2, 1)) {
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                            break;
                        default:
                            surname = SetEnd(surname, "�");
                            break;
                    }
                    break;
                case "�":
                    surname = SetEnd(surname, "�");
                    break;
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                    surname = surname + "�";
                    break;
                case "�":
                    if (!surname.endsWith("��") && !surname.endsWith("��")) {
                        surname = surname + "�";
                    }
                    break;
                case "�":
                case "�":
                    surname = SetEnd(surname, "�");
                    break;
            }
        }
        else {
            switch (end) {
                case "�":
                    switch (surname.substring(surname.length() - 2, 1)) {
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                            break;
                        default:
                            surname = SetEnd(surname, "�");
                            break;
                    }
                    break;
                case "�":
                    surname = SetEnd(surname, "�");
                    break;
            }
        }

        return surname;
	}
	/**
	 * ���������, ����? ����? (���)
	 * @param surname �������, ��� ���������.
	 * @param isFeminine True, ��� ������� �������.
	 * @return ���������� ��������� ���������.
	 */
	public String DeclineSurnameDative(String surname, boolean isFeminine) {
		String temp = surname;
        String end;

        end = SubstringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, 2, "���");
                    break;
                case "���":
                    surname = SetEnd(surname, 2, "���");
                    break;
            }
        }
        else {
            switch (end) {
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, 1, "��");
                    break;
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, 2, "��");
                    break;
                case "���":
                case "���":
                    surname = SetEnd(surname, 1, "��");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 2);

        switch (end) {
            case "��":
                surname = SetEnd(surname, 1, "�");
                break;
        }

        if (surname != temp) {
            return surname;
        }

        if (!isFeminine) {
            switch (end) {
                case "��":
                    surname = SetEnd(surname, 2, "��");
                    break;
                case "��":
                case "��":
                    surname = SetEnd(surname, 2, "���");
                    break;
                case "��":
                    surname = SetEnd(surname, 2, "��");
                    break;
                case "��":
                case "��":
                case "��":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, 2, "���");
                    }
                    break;
                case "��":
                    if (surname.toLowerCase() == "�������" || surname.toLowerCase() == "�������") {
                        surname = SetEnd(surname, 2, "��");
                    }
                    else {
                        surname = SetEnd(surname, 2, "��");
                    }
                    break;
            }
        }
        else {
            switch (end) {
                case "��":
                    surname = SetEnd(surname, 2, "��");
                    break;
                case "��":
                    surname = SetEnd(surname, 2, "��");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "�":
                    switch (surname.substring(surname.length() - 2, 1)) {
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "�");
                            break;
                    }
                    break;
                case "�":
                    surname = SetEnd(surname, 1, "�");
                    break;
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                    surname = surname + "�";
                    break;
                case "�":
                    if (!surname.endsWith("��") && !surname.endsWith("��")) {
                        surname = surname + "�";
                    }
                    break;
                case "�":
                case "�":
                    surname = SetEnd(surname, 1, "�");
                    break;
            }
        }
        else {
            switch (end) {
                case "�":
                    switch (surname.substring(surname.length() - 2, 1)) {
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "�");
                            break;
                    }
                    break;
                case "�":
                    surname = SetEnd(surname, 1, "�");
                    break;
            }
        }

        return surname;
	}
	/**
	 * �����������, ����? ����? (���)
	 * @param surname �������, ��� ���������.
	 * @param isFeminine True, ��� ������� �������.
	 * @return ���������� ��������� ���������.
	 */
	public String DeclineSurnameGenitive(String surname, boolean isFeminine) {
		String temp = surname;
        String end = null;

        end = SubstringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, 2, "���");
                    break;
                case "���":
                    surname = SetEnd(surname, 2, "���");
                    break;
                case "���":
                    surname = SetEnd(surname, "���");
                    break;
            }
        }
        else {
            switch (end) {
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, 1, "��");
                    break;
                case "���":
                case "���":
                case "���":
                case "���":
                case "���":
                    surname = SetEnd(surname, 2, "��");
                    break;
                case "���":
                case "���":
                    surname = SetEnd(surname, 1, "��");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 2);

        switch (end) {
            case "��":
            case "��":
            case "��":
            case "��":
            case "��":
            case "��":
            case "��":
                surname = SetEnd(surname, 1, "�");
                break;
        }

        if (surname != temp) {
            return surname;
        }

        if (!isFeminine) {
            switch (end) {
                case "��":
                    surname = SetEnd(surname, 1, "��");
                    break;
                case "��":
                case "��":
                    surname = SetEnd(surname, 2, "���");
                    break;
                case "��":
                    surname = SetEnd(surname, 2, "��");
                    break;
                case "��":
                case "��":
                case "��":
                    if (surname.length() > 4) {
                        surname = SetEnd(surname, 2, "���");
                    }
                    break;
                case "��":
                    if (surname.toLowerCase() == "�������" || surname.toLowerCase() == "�������") {
                        surname = SetEnd(surname, 2, "��");
                    }
                    else {
                        surname = SetEnd(surname, 2, "��");
                    }
                    break;
            }
        }
        else {
            switch (end) {
                case "��":
                    surname = SetEnd(surname, 2, "��");
                    break;
                case "��":
                    surname = SetEnd(surname, 2, "��");
                    break;
            }
        }

        if (surname != temp) {
            return surname;
        }

        end = SubstringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "�":
                    switch (surname.substring(surname.length() - 2, 1)) {
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "�");
                            break;
                    }
                    break;
                case "�":
                    surname = SetEnd(surname, 1, "�");
                    break;
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                case "�":
                    surname = surname + "�";
                    break;
                case "�":
                    if (!surname.endsWith("��") && !surname.endsWith("��")) {
                        surname = surname + "�";
                    }
                    break;
                case "�":
                case "�":
                    surname = SetEnd(surname, 1, "�");
                    break;
            }
        }
        else {
            switch (end) {
                case "�":
                    switch (surname.substring(surname.length() - 2, 1)) {
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                        case "�":
                            break;
                        default:
                            surname = SetEnd(surname, 1, "�");
                            break;
                    }
                    break;
                case "�":
                    surname = SetEnd(surname, 1, "�");
                    break;
            }
        }

        return surname;
	}

	/**
	 * ������������ ��� �������� ��������� �������, � ������� �������� �������� �����-���� ��� �����-����.
	 * @param fullPatronymic ������ ��������. �������: "�����-����", "����� ����", "��� �����".
	 * @return ���������� ��������
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
	 * ��������� ��������� ��������. ������: "��������" -> "�.", "�������" -> "�.".
	 * @param value
	 * @return ���������� null, ���� ������� �������� �������� ������ ������� ��� null.
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
