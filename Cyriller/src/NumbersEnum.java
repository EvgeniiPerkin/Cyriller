
public enum NumbersEnum {
    /**Единственное число*/
    Singular(1),
    /**Множественное число*/
    Plural(2);public final int value;

    NumbersEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
