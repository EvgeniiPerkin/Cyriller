import java.math.BigDecimal;

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
		BigDecimal value = new BigDecimal("1986122223.123");
        result = cyrNumber.Decline(value, new EurCurrency());
        String[] df = result.ToArray();
        
        for(String d : df) {
			System.out.println(d);
		}
	}

}
