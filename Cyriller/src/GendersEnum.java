
public enum GendersEnum {
	/**Не определено*/
	Undefined(0),
	/**Мужской*/
	Masculine(1),
	/**Женский*/
	Feminine(2),
	/**Нейтральный*/
	Neuter(3);
    public final int value;


    GendersEnum(int value){
        this.value = value;
    }


    public int getValue() {
        return value;
    }
}
