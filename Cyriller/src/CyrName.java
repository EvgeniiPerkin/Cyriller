import java.util.regex.Pattern;

public class CyrName {
	private Pattern patronymicSuffixRegex;
	private Pattern patronymicPrefixRegex;

	public CyrName() {
		patronymicSuffixRegex = Pattern.compile("([- ](((оглы)|(улы)|(уулу))|([кг]ызы)))$");
		patronymicPrefixRegex = Pattern.compile("^(ибн[- ])");
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
        
		return null;
	}
	public String[] Decline(String fullName, int cases, int gender, boolean shorten) {
		return null;
	}
	public Patronymic SplitPatronymic(String fullPatronymic) {
		Patronymic result = new Patronymic();
		if (fullPatronymic == null || fullPatronymic.isEmpty()) {
			result.setPrefix(null);
			result.setPatronymic(fullPatronymic);
			result.setSuffix(null);
			return result;
		}

		result.setPatronymic(fullPatronymic);
		
		return null;
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
}
