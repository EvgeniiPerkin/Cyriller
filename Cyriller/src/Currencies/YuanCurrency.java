package Currencies;

import Enums.CasesEnum;
import Enums.GendersEnum;

public class YuanCurrency extends Currency{
	public YuanCurrency(){
        this.setName("Китайский юань (CNY) ¥");
        this.setIntegerGender(GendersEnum.Masculine);
        this.setDecimalGender(GendersEnum.Feminine);
        this.setDecimals(2);
    }

	@Override
	public String[] GetIntegerName(CasesEnum cases) {
        switch (cases) {
            case Nominative:
                return new String[] { "юань", "юаня", "юаней" };
            case Genitive:
                return new String[] { "юаня", "юаней", "юаней" };
            case Dative:
                return new String[] { "юаню", "юаням", "юаням" };
            case Accusative:
                return new String[] { "юань", "юаня", "юаней" };
            case Instrumental:
                return new String[] { "юанем", "юанями", "юанями" };
            case Prepositional:
                return new String[] { "юане", "юанях", "юанях" };
        }

        throw new IllegalArgumentException("Invalid decline case!");
	}

	@Override
	public String[] GetDecimalName(CasesEnum cases) {
        switch (cases) {
            case Nominative:
                return new String[] { "цзяо", "цзяо", "цзяо" };
            case Genitive:
                return new String[] { "цзяо", "цзяо", "цзяо" };
            case Dative:
                return new String[] { "цзяо", "цзяо", "цзяо" };
            case Accusative:
                return new String[] { "цзяо", "цзяо", "цзяо" };
            case Instrumental:
                return new String[] { "цзяо", "цзяо", "цзяо" };
            case Prepositional:
                return new String[] { "цзяо", "цзяо", "цзяо" };
        }

        throw new IllegalArgumentException("Invalid decline case!");
	}

}
