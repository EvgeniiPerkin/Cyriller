
public enum CasesEnum {
    /**Именительный (Nominative), Кто? Что? (есть).*/
    Nominative(1),
    /**Родительный (Genitive), Кого? Чего? (нет).*/
    Genitive(2),
    /**Дательный (Dative), Кому? Чему? (дам).*/
    Dative(3),
    /**Винительный (Accusative), Кого? Что? (вижу).*/
    Accusative(4),
    /**Творительный (Instrumental), Кем? Чем? (горжусь).*/
    Instrumental(5),
    /**Предложный (Prepositional), О ком? О чем? (думаю).*/
    Prepositional(6);
    public final int value;

    CasesEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
