package rulesets;

import java.awt.Component;
import java.util.HashMap;

import librarysystem.NewMemberWindow;


final public class RuleSetFactory {
	
	public RuleSetFactory() {}
	static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
	
	static {
		map.put(NewMemberWindow.class, new MemberRuleSet());
	}
	
	public static RuleSet getRuleSet(Component c) {
		Class<? extends Component> cl = c.getClass();
		if(!map.containsKey(cl)) {
			throw new IllegalArgumentException(
					"No RuleSet found for this Component");
		}
		return map.get(cl);
	}
}
