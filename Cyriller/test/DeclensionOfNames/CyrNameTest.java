package DeclensionOfNames;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import Enums.CasesEnum;
import Enums.GendersEnum;
import main.CyrResult;

public class CyrNameTest {
	private CyrName cyrName;
	
	public CyrNameTest() {
		this.cyrName = new CyrName();
	}
	
	@Test
	void FeminineFullNameIsCorrectlyDeclined() {
		CyrResult result = this.cyrName.Decline("Иванова Наталья Петровна", GendersEnum.Feminine, false);

		assertEquals("Ивановой Натальи Петровны", result.Get(CasesEnum.Genitive));
        assertEquals("Ивановой Наталье Петровне", result.Get(CasesEnum.Dative));
        assertEquals("Иванову Наталью Петровну", result.Get(CasesEnum.Accusative));
        assertEquals("Ивановой Натальей Петровной", result.Get(CasesEnum.Instrumental));
        assertEquals("Ивановой Наталье Петровне", result.Get(CasesEnum.Prepositional));
        
        result = this.cyrName.Decline("Сафаралиева Койкеб Кямил Кызы", GendersEnum.Feminine, false);

        assertEquals("Сафаралиевой Койкеб Кямил Кызы", result.Get(CasesEnum.Genitive));
        assertEquals("Сафаралиевой Койкеб Кямил Кызы", result.Get(CasesEnum.Dative));
        assertEquals("Сафаралиеву Койкеб Кямил Кызы", result.Get(CasesEnum.Accusative));
        assertEquals("Сафаралиевой Койкеб Кямил Кызы", result.Get(CasesEnum.Instrumental));
        assertEquals("Сафаралиевой Койкеб Кямил Кызы", result.Get(CasesEnum.Prepositional));
        
        result = this.cyrName.Decline("Сафаралиева Койкеб Кямил-Кызы", GendersEnum.Feminine, false);

        assertEquals("Сафаралиевой Койкеб Кямил-Кызы", result.Get(CasesEnum.Genitive));
        assertEquals("Сафаралиевой Койкеб Кямил-Кызы", result.Get(CasesEnum.Dative));
        assertEquals("Сафаралиеву Койкеб Кямил-Кызы", result.Get(CasesEnum.Accusative));
        assertEquals("Сафаралиевой Койкеб Кямил-Кызы", result.Get(CasesEnum.Instrumental));
        assertEquals("Сафаралиевой Койкеб Кямил-Кызы", result.Get(CasesEnum.Prepositional));
        
        result = this.cyrName.Decline("Иванова Наталья Петровна", GendersEnum.Feminine, true);

        assertEquals("Ивановой Н. П.", result.Get(CasesEnum.Genitive));
        assertEquals("Ивановой Н. П.", result.Get(CasesEnum.Dative));
        assertEquals("Иванову Н. П.", result.Get(CasesEnum.Accusative));
        assertEquals("Ивановой Н. П.", result.Get(CasesEnum.Instrumental));
        assertEquals("Ивановой Н. П.", result.Get(CasesEnum.Prepositional));
        
        result = this.cyrName.Decline("Сафаралиева Койкеб Кямил Кызы", GendersEnum.Feminine, true);

        assertEquals("Сафаралиевой К. К.", result.Get(CasesEnum.Genitive));
        assertEquals("Сафаралиевой К. К.", result.Get(CasesEnum.Dative));
        assertEquals("Сафаралиеву К. К.", result.Get(CasesEnum.Accusative));
        assertEquals("Сафаралиевой К. К.", result.Get(CasesEnum.Instrumental));
        assertEquals("Сафаралиевой К. К.", result.Get(CasesEnum.Prepositional));
	}
	
	@Test
	void MasculineFullNameIsCorrectlyDeclined() {
         CyrResult result = this.cyrName.Decline("Иванов Иван Иванович", GendersEnum.Masculine, false);

         assertEquals("Иванова Ивана Ивановича", result.Get(CasesEnum.Genitive));
         assertEquals("Иванову Ивану Ивановичу", result.Get(CasesEnum.Dative));
         assertEquals("Иванова Ивана Ивановича", result.Get(CasesEnum.Accusative));
         assertEquals("Ивановым Иваном Ивановичем", result.Get(CasesEnum.Instrumental));
         assertEquals("Иванове Иване Ивановиче", result.Get(CasesEnum.Prepositional));
         
         result = this.cyrName.Decline("Карим Куржов Салим Оглы", GendersEnum.Masculine, false);

         assertEquals("Карима Куржова Салим Оглы", result.Get(CasesEnum.Genitive));
         assertEquals("Кариму Куржову Салим Оглы", result.Get(CasesEnum.Dative));
         assertEquals("Карима Куржова Салим Оглы", result.Get(CasesEnum.Accusative));
         assertEquals("Каримом Куржовом Салим Оглы", result.Get(CasesEnum.Instrumental));
         assertEquals("Кариме Куржове Салим Оглы", result.Get(CasesEnum.Prepositional));
     
         result = this.cyrName.Decline("Карим Куржов Салим-Оглы", GendersEnum.Masculine, false);

         assertEquals("Карима Куржова Салим-Оглы", result.Get(CasesEnum.Genitive));
         assertEquals("Кариму Куржову Салим-Оглы", result.Get(CasesEnum.Dative));
         assertEquals("Карима Куржова Салим-Оглы", result.Get(CasesEnum.Accusative));
         assertEquals("Каримом Куржовом Салим-Оглы", result.Get(CasesEnum.Instrumental));
         assertEquals("Кариме Куржове Салим-Оглы", result.Get(CasesEnum.Prepositional));

         result = this.cyrName.Decline("Иванов Иван Иванович", GendersEnum.Masculine, true);

         assertEquals("Иванова И. И.", result.Get(CasesEnum.Genitive));
         assertEquals("Иванову И. И.", result.Get(CasesEnum.Dative));
         assertEquals("Иванова И. И.", result.Get(CasesEnum.Accusative));
         assertEquals("Ивановым И. И.", result.Get(CasesEnum.Instrumental));
         assertEquals("Иванове И. И.", result.Get(CasesEnum.Prepositional));

         result = this.cyrName.Decline("Карим Куржов Салим Оглы", GendersEnum.Masculine, true);

         assertEquals("Карима К. С.", result.Get(CasesEnum.Genitive));
         assertEquals("Кариму К. С.", result.Get(CasesEnum.Dative));
         assertEquals("Карима К. С.", result.Get(CasesEnum.Accusative));
         assertEquals("Каримом К. С.", result.Get(CasesEnum.Instrumental));
         assertEquals("Кариме К. С.", result.Get(CasesEnum.Prepositional));

         result = this.cyrName.Decline("Илон МакФерсон", GendersEnum.Masculine, false);

         assertEquals("Илона МакФерсона", result.Get(CasesEnum.Genitive));
         assertEquals("Илону МакФерсону", result.Get(CasesEnum.Dative));
         assertEquals("Илона МакФерсона", result.Get(CasesEnum.Accusative));
         assertEquals("Илоном МакФерсоном", result.Get(CasesEnum.Instrumental));
         assertEquals("Илоне МакФерсоне", result.Get(CasesEnum.Prepositional));
         
         result = this.cyrName.Decline("Ахмед Гафуров ибн Мухаммад", GendersEnum.Masculine, false);

         assertEquals("Ахмеда Гафурова ибн Мухаммада", result.Get(CasesEnum.Genitive));
         assertEquals("Ахмеду Гафурову ибн Мухаммаду", result.Get(CasesEnum.Dative));
         assertEquals("Ахмеда Гафурова ибн Мухаммада", result.Get(CasesEnum.Accusative));
         assertEquals("Ахмедом Гафуровом ибн Мухаммадом", result.Get(CasesEnum.Instrumental));
         assertEquals("Ахмеде Гафурове ибн Мухаммаде", result.Get(CasesEnum.Prepositional));
	}
	
	@Test
    public void MasculineNameIsCorrectlyDeclinedInAccusativeCase() {
        String result = this.cyrName.DeclineNameAccusative("иван", false, false);
        assertEquals("ивана", result);
    }

	@Test
    public void MasculineNameIsCorrectlyDeclinedInDativeCase() {
        String result = this.cyrName.DeclineNameDative("иван", false, false);
        assertEquals("ивану", result);
    }

	@Test
    public void MasculineNameIsCorrectlyDeclinedInGenitiveCase() {
		String result = this.cyrName.DeclineNameGenitive("иван", false, false);
        assertEquals("ивана", result);
    }

	@Test
    public void MasculineNameIsCorrectlyDeclinedInInstrumentalCase() {
		String result = this.cyrName.DeclineNameInstrumental("иван", false, false);
        assertEquals("иваном", result);
    }

	@Test
    public void MasculineNameIsCorrectlyDeclinedInPrepositionalCase() {
		String result = this.cyrName.DeclineNamePrepositional("иван", false, false);
        assertEquals("иване", result);
    }

	@Test
    public void FeminineNameIsCorrectlyDeclinedInAccusativeCase() {
		String result = this.cyrName.DeclineNameAccusative("наталья", true, false);
        assertEquals("наталью", result);
    }

	@Test
    public void FeminineNameIsCorrectlyDeclinedInDativeCase() {
		String result = this.cyrName.DeclineNameDative("наталья", true, false);
        assertEquals("наталье", result);
    }

	@Test
    public void FeminineNameIsCorrectlyDeclinedInGenitiveCase() {
		String result = this.cyrName.DeclineNameGenitive("наталья", true, false);
        assertEquals("натальи", result);
    }

	@Test
    public void FeminineNameIsCorrectlyDeclinedInInstrumentalCase() {
		String result = this.cyrName.DeclineNameInstrumental("наталья", true, false);
        assertEquals("натальей", result);
    }

	@Test
    public void FeminineNameIsCorrectlyDeclinedInPrepositionalCase() {
		String result = this.cyrName.DeclineNamePrepositional("наталья", true, false);
        assertEquals("наталье", result);
    }

	@Test
    public void MasculineSurnameIsCorrectlyDeclinedInAccusativeCase() {
		String result = this.cyrName.DeclineSurnameAccusative("иванов", false);
		assertEquals("иванова", result);
    }

	@Test
    public void MasculineSurnameIsCorrectlyDeclinedInDativeCase() {
		String result = this.cyrName.DeclineSurnameDative("иванов", false);
		assertEquals("иванову", result);
    }

	@Test
    public void MasculineSurnameIsCorrectlyDeclinedInGenitiveCase() {
		String result = this.cyrName.DeclineSurnameGenitive("иванов", false);
		assertEquals("иванова", result);
    }

	@Test
    public void MasculineSurnameIsCorrectlyDeclinedInInstrumentalCase() {
		String result = this.cyrName.DeclineSurnameInstrumental("иванов", false);
		assertEquals("ивановым", result);
    }

	@Test
    public void MasculineSurnameIsCorrectlyDeclinedInPrepositionalCase() {
		String result = this.cyrName.DeclineSurnamePrepositional("иванов", false);
		assertEquals("иванове", result);
    }

	@Test
    public void FeminineSurnameIsCorrectlyDeclinedInAccusativeCase() {
		String result = this.cyrName.DeclineSurnameAccusative("петрова", true);
		assertEquals("петрову", result);
    }

	@Test
    public void FeminineSurnameIsCorrectlyDeclinedInDativeCase() {
		String result = this.cyrName.DeclineSurnameDative("петрова", true);
		assertEquals("петровой", result);
    }

	@Test
    public void FeminineSurnameIsCorrectlyDeclinedInGenitiveCase() {
		String result = this.cyrName.DeclineSurnameGenitive("петрова", true);
		assertEquals("петровой", result);
    }

	@Test
    public void FeminineSurnameIsCorrectlyDeclinedInInstrumentalCase() {
		String result = this.cyrName.DeclineSurnameInstrumental("петрова", true);
		assertEquals("петровой", result);
    }

	@Test
    public void FeminineSurnameIsCorrectlyDeclinedInPrepositionalCase() {
		String result = this.cyrName.DeclineSurnamePrepositional("петрова", true);
		assertEquals("петровой", result);
    }

	@Test
    public void MasculinePatronymicIsCorrectlyDeclinedInAccusativeCase() {
		String result = this.cyrName.DeclinePatronymicAccusative("иванович", false, false);
		assertEquals("ивановича", result);
    }

	@Test
    public void MasculinePatronymicIsCorrectlyDeclinedInDativeCase() {
		String result = this.cyrName.DeclinePatronymicDative("иванович", false, false);
		assertEquals("ивановичу", result);
    }

	@Test
    public void MasculinePatronymicIsCorrectlyDeclinedInGenitiveCase() {
		String result = this.cyrName.DeclinePatronymicGenitive("иванович", false, false);
		assertEquals("ивановича", result);
    }

	@Test
    public void MasculinePatronymicIsCorrectlyDeclinedInInstrumentalCase() {
		String result = this.cyrName.DeclinePatronymicInstrumental("иванович", false, false);
		assertEquals("ивановичем", result);
    }

	@Test
    public void MasculinePatronymicIsCorrectlyDeclinedInPrepositionalCase() {
		String result = this.cyrName.DeclinePatronymicPrepositional("иванович", false, false);
		assertEquals("ивановиче", result);
    }

	@Test
    public void FemininePatronymicIsCorrectlyDeclinedInAccusativeCase() {
		String result = this.cyrName.DeclinePatronymicAccusative("ивановна", true, false);
		assertEquals("ивановну", result);
    }

	@Test
    public void FemininePatronymicIsCorrectlyDeclinedInDativeCase() {
		String result = this.cyrName.DeclinePatronymicDative("ивановна", true, false);
		assertEquals("ивановне", result);
    }

	@Test
    public void FemininePatronymicIsCorrectlyDeclinedInGenitiveCase() {
		String result = this.cyrName.DeclinePatronymicGenitive("ивановна", true, false);
		assertEquals("ивановны", result);
    }

	@Test
    public void FemininePatronymicIsCorrectlyDeclinedInInstrumentalCase() {
		String result = this.cyrName.DeclinePatronymicInstrumental("ивановна", true, false);
        assertEquals("ивановной", result);
    }

	@Test
    public void FemininePatronymicIsCorrectlyDeclinedInPrepositionalCase() {
		String result = this.cyrName.DeclinePatronymicPrepositional("ивановна", true, false);
        assertEquals("ивановне", result);
    }

	@Test
    public void PatronymicEmptySplitIntoPartsCorrectly() {
		Patronymic p = new Patronymic();

    	p = this.cyrName.SplitPatronymic("");

    	assertNull(p.getPrefix());
    	assertEquals("", p.getPatronymic());
        assertNull(p.getSuffix());
        
        p = this.cyrName.SplitPatronymic(null);

    	assertNull(p.getPrefix());
    	assertNull(p.getPatronymic());
        assertNull(p.getSuffix());
        
        p = this.cyrName.SplitPatronymic(" ");

    	assertNull(p.getPrefix());
    	assertEquals(" ", p.getPatronymic());
        assertNull(p.getSuffix()); 
    }

	@Test
    public void PatronymicIbnSplitIntoPartsCorrectly() {
		Patronymic p = new Patronymic();

        p = this.cyrName.SplitPatronymic("Ибн Салим");

        assertEquals("Ибн ", p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertNull(p.getSuffix());

        p = this.cyrName.SplitPatronymic("Ибн-Салим");

        assertEquals("Ибн-", p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertNull(p.getSuffix());

        p = this.cyrName.SplitPatronymic("Ибн-салим");

        assertEquals("Ибн-", p.getPrefix());
        assertEquals("салим", p.getPatronymic());
        assertNull(p.getSuffix());
    }

	@Test
    public void PatronymicOglySplitIntoPartsCorrectly() {
		Patronymic p = new Patronymic();

		p = this.cyrName.SplitPatronymic("Салим Оглы");

		assertNull(p.getPrefix());
		assertEquals("Салим", p.getPatronymic());
		assertEquals(" Оглы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим оглы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals(" оглы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим-Оглы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals("-Оглы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим-оглы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals("-оглы", p.getSuffix());
    }

	@Test
    public void PatronymicUlySplitIntoPartsCorrectly() {
		Patronymic p = new Patronymic();

		p = this.cyrName.SplitPatronymic("Салим Улы");

		assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals(" Улы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим улы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals(" улы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим-Улы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals("-Улы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим-улы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals("-улы", p.getSuffix());
    }

	@Test
    public void PatronymicUulуSplitIntoPartsCorrectly() {
		Patronymic p = new Patronymic();

		p = this.cyrName.SplitPatronymic("Салим Уулу");

		assertNull(p.getPrefix());
		assertEquals("Салим", p.getPatronymic());
		assertEquals(" Уулу", p.getSuffix());
        
        p = this.cyrName.SplitPatronymic("Салим уулу");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals(" уулу", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим-Уулу");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals("-Уулу", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим-уулу");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals("-уулу", p.getSuffix());
    }

	@Test
    public void PatronymicKyzySplitIntoPartsCorrectly() {
		Patronymic p = new Patronymic();

		p = this.cyrName.SplitPatronymic("Салим Кызы");

		assertNull(p.getPrefix());
		assertEquals("Салим", p.getPatronymic());
		assertEquals(" Кызы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим кызы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals(" кызы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим-Кызы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals("-Кызы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим-кызы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals("-кызы", p.getSuffix());
    }

	@Test
    public void PatronymicGyzySplitIntoPartsCorrectly() {
		Patronymic p = new Patronymic();

		p = this.cyrName.SplitPatronymic("Салим Гызы");

		assertNull(p.getPrefix());
		assertEquals("Салим", p.getPatronymic());
		assertEquals(" Гызы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим гызы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals(" гызы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим-Гызы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals("-Гызы", p.getSuffix());

        p = this.cyrName.SplitPatronymic("Салим-гызы");

        assertNull(p.getPrefix());
        assertEquals("Салим", p.getPatronymic());
        assertEquals("-гызы", p.getSuffix());
    }
}
