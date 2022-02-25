package Currencies;

import Enums.CasesEnum;
import Enums.GendersEnum;

public class RurCurrency extends Currency{
	public RurCurrency(){
        this.setName("Российский рубль (руб) ₽");
        this.setIntegerGender(GendersEnum.Masculine);
        this.setDecimalGender(GendersEnum.Feminine);
        this.setDecimals(2);
    }
	
	@Override
	public String[] GetIntegerName(CasesEnum cases) {
        switch (cases) {
            case Nominative:
                return new String[] { "рубль", "рубля", "рублей" };
            case Genitive:
                return new String[] { "рубля", "рублей", "рублей" };
            case Dative:
                return new String[] { "рублю", "рублям", "рублям" };
            case Accusative:
                return new String[] { "рубль", "рубля", "рублей" };
            case Instrumental:
                return new String[] { "рублем", "рублями", "рублями" };
            case Prepositional:
                return new String[] { "рубле", "рублях", "рублях" };
        }

        throw new IllegalArgumentException("Invalid decline case!");
	}

	@Override
	public String[] GetDecimalName(CasesEnum cases) {
        switch (cases) {
            case Nominative:
                return new String[] { "копейка", "копейки", "копеек" };
            case Genitive:
                return new String[] { "копейки", "копеек", "копеек" };
            case Dative:
                return new String[] { "копейке", "копейкам", "копейкам" };
            case Accusative:
                return new String[] { "копейку", "копейки", "копеек" };
            case Instrumental:
                return new String[] { "копейкой", "копейками", "копейками" };
            case Prepositional:
                return new String[] { "копейке", "копейках", "копейках" };
        }

        throw new IllegalArgumentException("Invalid decline case!");
	}

}
