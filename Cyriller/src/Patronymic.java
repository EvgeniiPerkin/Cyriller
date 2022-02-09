/**
 * �������� ��� ��������
 * @author Perkin
 *
 */
public class Patronymic {
	/**����� ���������� ��������, ������� �� ������ ����������*/
	private String prefix;
	/**����� ��������, ������� ����� ����������.*/
	private String patronymic;
	/**����� ���������� ��������, ������� �� ������ ����������*/
	private String suffix;
	/**
	 * ���������� ���������� ����� ���������� ��������, ������� �� ������ ����������.
	 * @return
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * ������������� ���������� ����� ���������� ��������, ������� �� ������ ����������.
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * ���������� �������� ����� ��������, ������� ����� ����������.
	 * @return
	 */
	public String getPatronymic() {
		return patronymic;
	}
	/**
	 * ������������� �������� ����� ��������, ������� ����� ����������.
	 * @param patronymic
	 */
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	/**
	 * ���������� ���������� ����� ���������� ��������, ������� �� ������ ����������.
	 * @return
	 */
	public String getSuffix() {
		return suffix;
	}
	/**
	 * ������������� ���������� ����� ���������� ��������, ������� �� ������ ����������.
	 * @param suffix
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
