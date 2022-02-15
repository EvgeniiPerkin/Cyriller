import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CyrName {
	private Pattern patronymicSuffixRegex;
	private Pattern patronymicPrefixRegex;

	public CyrName() {
		patronymicSuffixRegex = Pattern.compile("((?i)[- ](((?i)(����)|(���)|(����))|([(?i)��](?i)���)))$", Pattern.UNICODE_CASE);
		patronymicPrefixRegex = Pattern.compile("^((?i)���[- ])", Pattern.UNICODE_CASE);
	}
	
	/**
	 * �������� ������ ��� � ��������� �����.
	 * @param surname �������, � ������������ ������.
	 * @param name ���, � ������������ ������.
	 * @param patronumic ��������, � ������������ ������.
	 * @param cases �����, � ������� ����� �����������.
	 * @param gender ���, ���������� �����
	 * @param shorten ��������� �� ��� � �������� � ���������� ���������. � �������, ������ ���� ��������, ����� ������ �. �.
	 * @return ���������� ��������� ���������
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
            gender = patronymic.endsWith("��") ? 2 : 1;
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
                case "���":
                    name = SetEnd(name, 2, "���");
                    break;
            }

            if (name == temp) {
                switch (SubstringRight(name, 2)){
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
                switch (SubstringRight(name, 1)){
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
		return null;
	}
	/**
	 * ������������, ���? ���? (�������)
	 * @param surname �������, ��� ���������.
	 * @param isFeminine True, ��� ������� �������.
	 * @return ���������� ��������� ���������.
	 */
	public String DeclineSurnameInstrumental(String surname, boolean isFeminine) {
		return null;
	}
	/**
	 * �����������, ����? ���? (����)
	 * @param surname �������, ��� ���������.
	 * @param isFeminine True, ��� ������� �������.
	 * @return ���������� ��������� ���������.
	 */
	public String DeclineSurnameAccusative(String surname, boolean isFeminine) {
		return null;
	}
	/**
	 * ���������, ����? ����? (���)
	 * @param surname �������, ��� ���������.
	 * @param isFeminine True, ��� ������� �������.
	 * @return ���������� ��������� ���������.
	 */
	public String DeclineSurnameDative(String surname, boolean isFeminine) {
		return null;
	}
	/**
	 * �����������, ����? ����? (���)
	 * @param surname �������, ��� ���������.
	 * @param isFeminine True, ��� ������� �������.
	 * @return ���������� ��������� ���������.
	 */
	public String DeclineSurnameGenitive(String surname, boolean isFeminine) {
		return null;
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
	 * ���������, �������� �� ��������� ������ �������, ������ ��� ������� ������ �� ��������.
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
	 * ���������� true, ���� ������� ������ �������� ������, null ��� ������������� �� ����� (.).
	 * ������: "��������" -> false, "�." -> true.
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
