
public abstract class CyrBaseWord {
	protected CyrResult GetResult(String name, CyrRule[] rules) {
        CyrResult result = new CyrResult (
            rules[0].Apply(name),
            rules[1].Apply(name),
            rules[2].Apply(name),
            rules[3].Apply(name),
            rules[4].Apply(name),
            rules[5].Apply(name)
        );

        return result;
    }
}
