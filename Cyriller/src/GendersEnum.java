
public enum GendersEnum {
	/**��������������*/
	Undefined(0),
	/**�������*/
	Masculine(1),
	/**�������*/
	Feminine(2),
	/**�������*/
	Neuter(3);
    public final int value;


    GendersEnum(int value){
        this.value = value;
    }


    public int getValue() {
        return value;
    }
}
