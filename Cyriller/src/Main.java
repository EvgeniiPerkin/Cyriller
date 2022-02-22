

public class Main {

    
	public static void main(String[] args) {
		CyrName cyrName = new CyrName();
        String name = "Семенова Дарья Николаевна";
		CyrResult result = cyrName.Decline(name, GendersEnum.Feminine, false);
		String[] k = result.ToArray();
		
		for(String d : k) {
			System.out.println(d);
		}
	}

}
