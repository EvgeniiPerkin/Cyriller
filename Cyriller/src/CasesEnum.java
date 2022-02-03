
public enum CasesEnum {
    /**������������, ���? ���? (����)*/
    Nominative(1),
    /**�����������, ����? ����? (���)*/
    Genitive(2),
    /**���������, ����? ����? (���)*/
    Dative(3),
    /** �����������, ����? ���? (����)*/
    Accusative(4),
    /**������������, ���? ���? (�������)*/
    Instrumental(5),
    /**����������, � ���? � ���? (�����)*/
    Prepositional(6);
    public final int value;


    CasesEnum(int value){
        this.value = value;
    }


    public int getValue() {
        return value;
    }
}
