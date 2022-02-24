
public class CyrNumber {
	/**Максимальное число для написания прописью*/
    public final long MaxValue = 9999999999L;
    /**
     * Склоняет число прописью в мужском роде  для неодушевленных предметов
     * @param value
     * @return
     */
    public CyrResult Decline(long value) {
        return this.Decline(value, GendersEnum.Masculine, AnimatesEnum.Inanimated);
    }
    /**
     * Склоняет число прописью в указанном роде и одушевленности.
     * @param value
     * @param gender
     * @param animate
     * @return
     */
    public CyrResult Decline(long value, GendersEnum gender, AnimatesEnum animate)
    {
        CyrResult result = new CyrResult(
            this.toString(value, CasesEnum.Nominative, gender, animate),
            this.toString(value, CasesEnum.Genitive, gender, animate),
            this.toString(value, CasesEnum.Dative, gender, animate),
            this.toString(value, CasesEnum.Accusative, gender, animate),
            this.toString(value, CasesEnum.Instrumental, gender, animate),
            this.toString(value, CasesEnum.Prepositional, gender, animate)
        );

        return result;
    }
    /**
     * Склоняет число прописью в мужском роде для неодушевленных предметов
     * @param value
     * @return
     */
    public CyrResult Decline(double value)
    {
        return this.Decline(value, GendersEnum.Masculine, AnimatesEnum.Inanimated);
    }
    /**
     * Склоняет число прописью в указанном роде и одушевленности.
     * @param value
     * @param gender
     * @param animate
     * @return
     */
    public CyrResult Decline(double value, GendersEnum gender, AnimatesEnum animate)
    {
        CyrResult result = new CyrResult(
            this.toString(value, CasesEnum.Nominative, gender, animate),
            this.toString(value, CasesEnum.Genitive, gender, animate),
            this.toString(value, CasesEnum.Dative, gender, animate),
            this.toString(value, CasesEnum.Accusative, gender, animate),
            this.toString(value, CasesEnum.Instrumental, gender, animate),
            this.toString(value, CasesEnum.Prepositional, gender, animate)
        );

        return result;
    }
    /**
     * Склоняет денежную сумму в указанной валюте.
     * @param value
     * @param currency
     * @return
     */
    public CyrResult Decline(double value, Currency currency) {
        CyrResult result = new CyrResult(
            this.toString(value, CasesEnum.Nominative, currency),
            this.toString(value, CasesEnum.Genitive, currency),
            this.toString(value, CasesEnum.Dative, currency),
            this.toString(value, CasesEnum.Accusative, currency),
            this.toString(value, CasesEnum.Instrumental, currency),
            this.toString(value, CasesEnum.Prepositional, currency)
        );

        return result;
    } 
    /**
     * Склоняет количество указанных единиц.
     * @param value
     * @param item
     * @return
     */
    public CyrResult Decline(double value, Item item)
    {
        CyrResult result = new CyrResult(
            this.toString(value, CasesEnum.Nominative, item),
            this.toString(value, CasesEnum.Genitive, item),
            this.toString(value, CasesEnum.Dative, item),
            this.toString(value, CasesEnum.Accusative, item),
            this.toString(value, CasesEnum.Instrumental, item),
            this.toString(value, CasesEnum.Prepositional, item)
        );

        return result;
    }
    /**
     * Склоняет число прописью в указнный падеж мужского рода для неодушевленных предметов 
     * @param value
     * @param cases
     * @return
     */
    public String toString(long value, CasesEnum cases)
    {
        return this.toString(value, cases, GendersEnum.Masculine, AnimatesEnum.Inanimated);
    }
    /**
     * Склоняет число прописью в указнный падеж мужского рода  для неодушевленных предметов
     * @param value
     * @param cases
     * @return
     */
    public String toString(double value, CasesEnum cases)
    {
        return this.toString(value, cases, GendersEnum.Masculine, AnimatesEnum.Inanimated);
    }
    /**
     * Склоняет число прописью в указнный падеж, род и одушевленность.
     * @param value
     * @param cases
     * @param gender
     * @param animate
     * @return
     */
    public String toString(double value, CasesEnum cases, GendersEnum gender, AnimatesEnum animate) {
    	return "";
    }
    public String toString(double value, CasesEnum cases, Currency currency) {
    	return "";
    }
    public String toString(double value, CasesEnum cases, Item item) {
    	return "";
    }
    public String toString(long value, CasesEnum cases, GendersEnum gender, AnimatesEnum animate) {
    	return "";
    }    
}
