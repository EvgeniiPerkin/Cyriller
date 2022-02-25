package DeclensionOfNames;
/**
 * Класс отчество
 * @author Perkin
 *
 */
public class Patronymic {
	/**префиксная часть составного отчества*/
	private String prefix;
	/**основная часть отчества, которая может склоняться.*/
	private String patronymic;
	/**суффиксная часть составного отчества, которая не должна склоняться*/
	private String suffix;
	/**
	 * Возвращает префиксную часть составного отчества, которая не должна склоняться.
	 * @return
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * устанавливает префиксную часть составного отчества, которая не должна склоняться.
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * Возвращает основную часть отчества, которая может склоняться.
	 * @return
	 */
	public String getPatronymic() {
		return patronymic;
	}
	/**
	 * устанавливает основную часть отчества, которая может склоняться.
	 * @param patronymic
	 */
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	/**
	 * Возвращает суффиксную часть составного отчества, которая не должна склоняться
	 * @return
	 */
	public String getSuffix() {
		return suffix;
	}
	/**
	 * Устанавливает суффиксную часть составного отчества, которая не должна склоняться
	 * @param suffix
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
