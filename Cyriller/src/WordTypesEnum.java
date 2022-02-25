
public enum WordTypesEnum {
	None(0),
    /** Аббревиатура*/
    Abbreviation(1),
    /**Имя*/
    Name(2),
    /**Фамилия*/
    Surname(3),
    /**Отчество*/
    Patronymic(4),
    /**Топоним*/
    Toponym(5),
    /**Организация*/
    Organization(6),
    /**Торговая марка*/
    TradeMark(7),
    /**Превосходная степень*/
    Superlative(8),
    /**Качественное*/
    Qualitative(9),
    /**Местоименное*/
    Pronominal(10),
    /**Порядковое*/
    Ordinal(11),
    /**Притяжательное*/
    Possessive(12),
    /**Форма на -ею*/
    FormEy(13),
    /**Форма на -ою*/
    FormOy(14),
	/**Сравнительная степень на по-*/
    Comparative(15),
    /**Форма компаратива на -ей*/
    ComparativeEj(16);
	public final int value;

	WordTypesEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
