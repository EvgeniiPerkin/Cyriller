
public enum CasesEnum {
    /**Именительный, Кто? Что? (есть)*/
    Nominative(1),
    /**Родительный, Кого? Чего? (нет)*/
    Genitive(2),
    /**Дательный, Кому? Чему? (дам)*/
    Dative(3),
    /** Винительный, Кого? Что? (вижу)*/
    Accusative(4),
    /**Творительный, Кем? Чем? (горжусь)*/
    Instrumental(5),
    /**Предложный, О ком? О чем? (думаю)*/
    Prepositional(6);
    public final int value;


    CasesEnum(int value){
        this.value = value;
    }


    public int getValue() {
        return value;
    }
}
