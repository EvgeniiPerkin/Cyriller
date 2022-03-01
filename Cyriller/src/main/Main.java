package main;

import java.math.BigDecimal;
import Currencies.RurCurrency;
import DeclensionOfNames.CyrName;
import DeclinationOfDigit.CyrNumber;
import Enums.GendersEnum;

public class Main {    
	public static void main(String[] args) {
		CyrName cyrName = new CyrName();
        String name = "Илона МакФерсона";
		CyrResult result = cyrName.Decline(name, GendersEnum.Masculine, false);
		String[] k = result.ToArray();
		
		for(String d : k) {
			System.out.println(d);
		}
		
		CyrNumber cyrNumber = new CyrNumber();
		BigDecimal value = new BigDecimal("8100000.00");
        result = cyrNumber.Decline(value, new RurCurrency());
        String[] df = result.ToArray();
        
        for(String d : df) {
			System.out.println(d);
		}
        /*986lksadfasdf47 ----lkjlkjlkjlkj helloddd*/
	}

}
