package DeclinationOfDigit;

import Enums.AnimatesEnum;
import Enums.CasesEnum;
import Enums.GendersEnum;
import main.CyrResult;

public class Item {
    protected CyrResult singular;
    protected CyrResult plural;
    protected CyrNoun noun;
    
    public Item(CyrNoun Noun) {
        this.noun = Noun;
        this.singular = noun.Decline();
        this.plural = noun.DeclinePlural();
    }
    
    public String[] GetName(CasesEnum cases, long value) {
        if (this.getGender() == GendersEnum.Feminine) {
            return GetFeminine(cases);
        }
        else if (this.getGender() == GendersEnum.Neuter) {
            return GetNeuter(cases);
        }
        else {
            return this.GetMasculine(cases, value);
        }
    }
    
    public GendersEnum getGender() {
    	return this.noun.getGender();
    }
    
    public AnimatesEnum GetAnimate() {
    	 return this.noun.getAnimate();
    }

    protected String[] GetMasculine(CasesEnum cases, long value) {
        if (this.GetAnimate() == AnimatesEnum.Animated && value < 20) {
            switch (cases) {
                case Nominative:
                    return new String[] { singular.Get(CasesEnum.Nominative), singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Nominative) };
                case Genitive:
                    return new String[] { singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive) };
                case Dative:
                    return new String[] { singular.Get(CasesEnum.Dative), plural.Get(CasesEnum.Dative), plural.Get(CasesEnum.Dative) };
                case Accusative:
                    return new String[] { singular.Get(CasesEnum.Accusative), plural.Get(CasesEnum.Accusative), plural.Get(CasesEnum.Accusative) };
                case Instrumental:
                    return new String[] { singular.Get(CasesEnum.Instrumental), plural.Get(CasesEnum.Instrumental), plural.Get(CasesEnum.Instrumental) };
                case Prepositional:
                    return new String[] { singular.Get(CasesEnum.Prepositional), plural.Get(CasesEnum.Prepositional), plural.Get(CasesEnum.Prepositional) };
                default:
                    throw new IllegalArgumentException("Invalid Case!");
            }
        }
        else {
            switch (cases) {
                case Nominative:
                    if (noun.getName().toLowerCase() == "год") {
                        return new String[] { singular.Get(CasesEnum.Nominative), singular.Get(CasesEnum.Genitive), "лет" };
                    }

                    return new String[] { singular.Get(CasesEnum.Nominative), singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive) };
                case Genitive:
                    if (noun.getName().toLowerCase() == "год") {
                        return new String[] { singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive), "лет" };
                    }

                    return new String[] { singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive) };
                case Dative:
                    return new String[] { singular.Get(CasesEnum.Dative), plural.Get(CasesEnum.Dative), plural.Get(CasesEnum.Dative) };
                case Accusative:
                    if (noun.getName().toLowerCase() == "год") {
                        return new String[] { singular.Get(CasesEnum.Accusative), singular.Get(CasesEnum.Accusative), "лет" };
                    }

                    return new String[] { singular.Get(CasesEnum.Nominative), singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive) };
                case Instrumental:
                    return new String[] { singular.Get(CasesEnum.Instrumental), plural.Get(CasesEnum.Instrumental), plural.Get(CasesEnum.Instrumental) };
                case Prepositional:
                    return new String[] { singular.Get(CasesEnum.Prepositional), plural.Get(CasesEnum.Prepositional), plural.Get(CasesEnum.Prepositional) };
                default:
                    throw new IllegalArgumentException("Invalid Case!");
            }
        }
    }
    
    protected String[] GetFeminine(CasesEnum cases) {
        switch (cases) {
            case Nominative:
                return new String[] { singular.Get(CasesEnum.Nominative), singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive) };
            case Genitive:
                return new String[] { singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive) };
            case Dative:
                return new String[] { singular.Get(CasesEnum.Dative), plural.Get(CasesEnum.Dative), plural.Get(CasesEnum.Dative) };
            case Accusative:
                return new String[] { singular.Get(CasesEnum.Accusative), singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Accusative) };
            case Instrumental:
                return new String[] { singular.Get(CasesEnum.Instrumental), plural.Get(CasesEnum.Instrumental), plural.Get(CasesEnum.Instrumental) };
            case Prepositional:
                return new String[] { singular.Get(CasesEnum.Prepositional), plural.Get(CasesEnum.Prepositional), plural.Get(CasesEnum.Prepositional) };
            default:
                throw new IllegalArgumentException("Invalid Case!");
        }
    }

    protected String[] GetNeuter(CasesEnum cases) {
        switch (cases) {
            case Nominative:
                return new String[] { singular.Get(CasesEnum.Nominative), singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive) };
            case Genitive:
                return new String[] { singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive) };
            case Dative:
                return new String[] { singular.Get(CasesEnum.Dative), plural.Get(CasesEnum.Dative), plural.Get(CasesEnum.Dative) };
            case Accusative:
                return new String[] { singular.Get(CasesEnum.Accusative), singular.Get(CasesEnum.Genitive), plural.Get(CasesEnum.Genitive) };
            case Instrumental:
                return new String[] { singular.Get(CasesEnum.Instrumental), plural.Get(CasesEnum.Instrumental), plural.Get(CasesEnum.Instrumental) };
            case Prepositional:
                return new String[] { singular.Get(CasesEnum.Prepositional), plural.Get(CasesEnum.Prepositional), plural.Get(CasesEnum.Prepositional) };
            default:
                throw new IllegalArgumentException("Invalid Case!");
        }
    }
}
