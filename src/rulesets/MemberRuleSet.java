package rulesets;

import librarysystem.NewMemberWindow;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Rules:
 * 1. All fields must be nonempty
 * 2. First name must be 2 to 35 characters long, and a single character without numbers.
 * 3. Last name must be 2 to 35 characters long, and a single character without numbers.
 * 4. Street must be 2 to 75 characters long, and a single character with numbers.
 * 5. City must be 2 to 75 characters long, and a single character with numbers.
 * 6. State must be 2 to 75 characters long, and a single character with numbers.
 * 7. Zip Code must be 5 digits.
 * 8. Phone Number must be a number and 14 digits and includes "()".
 *
 */
public class MemberRuleSet implements RuleSet {

    private NewMemberWindow newMemberWindow;
    private static final String NAME_PATTERN = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)?$";
    private static final String STREET_PATTERN = "^[a-zA-Z0-9\\s]+$";
    private static final String ZIP_CODE_PATTERN = "^[0-9]{5}(?:-[0-9]{4})?$";
    private static final String PHONE_NUMBER_PATTERN = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";

    @Override
    public void applyRules(Component com) throws RuleException {
        this.newMemberWindow = (NewMemberWindow) com;
        noneEmptyRule();
        valFirstName();
        valLastName();
        valStreetName();
        valCityName();
        valStateName();
        isZip();
        isPhone();
    }

    private void noneEmptyRule() throws RuleException {
        if(this.newMemberWindow.getFirstNameField().trim().isEmpty()
                || this.newMemberWindow.getLastNameField().trim().isEmpty()
                || this.newMemberWindow.gertStreetField().trim().isEmpty()
                || this.newMemberWindow.gertCityField().trim().isEmpty()
                || this.newMemberWindow.getStateField().trim().isEmpty()
                || this.newMemberWindow.getZipField().trim().isEmpty()
                || this.newMemberWindow.getTelephoneField().trim().isEmpty()
        ) {
            throw new RuleException("All fields must be non-empty!");
        }
    }

    private void valFirstName() throws RuleException {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(this.newMemberWindow.getFirstNameField().trim());
        if(!matcher.matches() || this.newMemberWindow.getFirstNameField().trim().length() < 2 || this.newMemberWindow.getFirstNameField().trim().length() > 35) {
            throw new RuleException("First name must be 2 to 35 characters long, and a single character without numbers and special characters");
        }
    }

    private void valLastName() throws RuleException {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(this.newMemberWindow.getLastNameField().trim());
        if(!matcher.matches() || this.newMemberWindow.getLastNameField().trim().length() < 2 || this.newMemberWindow.getLastNameField().trim().length() > 35) {
            throw new RuleException("Last name must be 2 to 35 characters long, and a single character without numbers and special characters");
        }
    }

    private void valStreetName() throws RuleException {
//        Pattern pattern = Pattern.compile(STREET_PATTERN);
//        Matcher matcher = pattern.matcher(this.newMemberWindow.gertStreetField().trim());
        if(this.newMemberWindow.gertStreetField().trim().length() < 2 || this.newMemberWindow.gertStreetField().trim().length() > 75) {
            throw new RuleException("Street must be 2 to 75 characters long, and a single character with numbers.");
        }
    }

    private void valCityName() throws RuleException {
//        Pattern pattern = Pattern.compile(STREET_PATTERN);
//        Matcher matcher = pattern.matcher(this.newMemberWindow.gertCityField().trim());
        if(this.newMemberWindow.gertCityField().trim().length() < 2 || this.newMemberWindow.gertCityField().trim().length() > 75) {
            throw new RuleException("City must be 2 to 75 characters long, and a single character with numbers.");
        }
    }

    private void valStateName() throws RuleException {
        Pattern pattern = Pattern.compile(STREET_PATTERN);
        Matcher matcher = pattern.matcher(this.newMemberWindow.getStateField().trim());
        if(!matcher.matches() || this.newMemberWindow.getStateField().trim().length() < 2 || this.newMemberWindow.getStateField().trim().length() > 75) {
            throw new RuleException("State must be 2 to 75 characters long, and a single character with numbers.");
        }
    }

    private void isPhone() throws RuleException {
//        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
//        Matcher matcher = pattern.matcher(this.newMemberWindow.getTelephoneField().trim());
        if(this.newMemberWindow.getTelephoneField().trim().length() == 0) {
            throw new RuleException("Invalid phone number");
        }
    }

    private void isZip() throws RuleException {
        Pattern pattern = Pattern.compile(ZIP_CODE_PATTERN);
        Matcher matcher = pattern.matcher(this.newMemberWindow.getZipField().trim());
        if(!matcher.matches() || this.newMemberWindow.getZipField().trim().length() != 5) {
            throw new RuleException("Invalid zip code");
        }
    }
}
