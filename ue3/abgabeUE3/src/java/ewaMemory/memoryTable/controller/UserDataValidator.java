/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ewaMemory.memoryTable.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author stephan
 */
public class UserDataValidator {

    private Pattern datePattern;
    private final Pattern stacksizePattern;
    private static final String DATE_PATTERN =
            "(0?[1-9]|[12][0-9]|3[01])[.](0?[1-9]|1[012])[.]((19|20)\\d\\d)";
    private static final String STACKSIZE_PATTERN =
            "(2x2|4x4|6x6)";

    public UserDataValidator() {
        datePattern = Pattern.compile(DATE_PATTERN);
        stacksizePattern = Pattern.compile(STACKSIZE_PATTERN);
    }

    public void validateDate(FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {
        String date = (String) value;

        if (date.isEmpty()) {
            return;
        }

        Matcher matcher = datePattern.matcher(date);
        if (matcher.matches()) {
            return;
        }

        FacesMessage msg = new FacesMessage(
                FacesMessage.SEVERITY_WARN, "Failed to match date pattern: (TT.MM.YYYY)!", null); //TODO needs i18n support
        throw new ValidatorException(msg);
    }

    public void validateStacksize(FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {

        String stacksize = (String) value;

        if (stacksize.isEmpty()) {
            return;
        }

        Matcher matcher = stacksizePattern.matcher(stacksize);
        if (matcher.matches()) {
            return;
        }

        FacesMessage msg = new FacesMessage(
                FacesMessage.SEVERITY_WARN, "Invalid stacksize - only 2x2, 4x4 and 6x6 supported!", null); //TODO needs i18n support
        throw new ValidatorException(msg);
    }
}
