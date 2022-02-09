/**
 * оболочка для отчества
 * @author Perkin
 *
 */
public class Patronymic {
	/**часть составного отчества, которая не должна склоняться*/
	private String prefix;
	/**часть отчества, которая может склоняться.*/
	private String patronymic;
	/**часть составного отчества, которая не должна склоняться*/
	private String suffix;
	/**
	 * Возвращает префиксную часть составного отчества, которая не должна склоняться.
	 * @return
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * Устанавливает префиксную часть составного отчества, которая не должна склоняться.
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
	 * Устанавливает основную часть отчества, которая может склоняться.
	 * @param patronymic
	 */
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	/**
	 * Возвращает суффиксную часть составного отчества, которая не должна склоняться.
	 * @return
	 */
	public String getSuffix() {
		return suffix;
	}
	/**
	 * Устанавливает суффиксную часть составного отчества, которая не должна склоняться.
	 * @param suffix
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
