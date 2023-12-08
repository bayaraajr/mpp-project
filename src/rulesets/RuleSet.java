package rulesets;

import java.awt.Component;

 interface RuleSet {
	public void applyRules(Component ob) throws RuleException;
}
