

public class Main {

    
	public static void main(String[] args) {
		CyrName cyrName = new CyrName();
        String name = "Илона МакФерсона";
		CyrResult result = cyrName.Decline(name, GendersEnum.Masculine, false);
		String[] k = result.ToArray();
		
		for(String d : k) {
			System.out.println(d);
		}
	}

}
