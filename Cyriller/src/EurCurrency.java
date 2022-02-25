
public class EurCurrency extends Currency{
	public EurCurrency(){
        this.setName("Евро (EUR) €");
        this.setIntegerGender(GendersEnum.Masculine);
        this.setDecimalGender(GendersEnum.Feminine);
        this.setDecimals(2);
    }

	@Override
	public String[] GetIntegerName(CasesEnum cases) {
		switch (cases) {
            case Nominative:
                return new String[] { "евро", "евро", "евро" };
            case Genitive:
                return new String[] { "евро", "евро", "евро" };
            case Dative:
                return new String[] { "евро", "евро", "евро" };
            case Accusative:
                return new String[] { "евро", "евро", "евро" };
            case Instrumental:
                return new String[] { "евро", "евро", "евро" };
            case Prepositional:
                return new String[] { "евро", "евро", "евро" };
        }

        throw new IllegalArgumentException("Invalid decline case!");
	}

	@Override
	public String[] GetDecimalName(CasesEnum cases) {
		switch (cases) {
            case Nominative:
                return new String[] { "цент", "цента", "центов" };
            case Genitive:
                return new String[] { "цента", "центов", "центов" };
            case Dative:
                return new String[] { "центу", "центам", "центам" };
            case Accusative:
                return new String[] { "цент", "цента", "центов" };
            case Instrumental:
                return new String[] { "центом", "центами", "центами" };
            case Prepositional:
                return new String[] { "центе", "центах", "центах" };
        }

        throw new IllegalArgumentException("Invalid decline case!");
	}
}
