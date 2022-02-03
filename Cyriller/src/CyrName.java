
public class CyrName {
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
		return null;
	}
	public String[] Decline(String fullName, int cases, int gender, boolean shorten) {
		return null;
	}
}
