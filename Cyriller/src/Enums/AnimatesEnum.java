package Enums;

public enum AnimatesEnum {
	Animated(1),
	Inanimated(2);
	
	public final int value;

	AnimatesEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
