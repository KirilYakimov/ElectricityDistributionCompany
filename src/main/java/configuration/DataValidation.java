package configuration;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;

public class DataValidation {

    public static boolean emailValidation(JFXTextField emailTextField, String warning) {
        RegexValidator valid = new RegexValidator();

        valid.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        emailTextField.setValidators(valid);
        emailTextField.getValidators().get(0).setMessage(warning);
        emailTextField.validate();

        return emailTextField.getText().matches(valid.getRegexPattern());
    }

    public static boolean textValidation(JFXTextField inputTextField, String warning) {
        RegexValidator valid = new RegexValidator();

        valid.setRegexPattern("[a-zA-Z]{3,16}");

        inputTextField.setValidators(valid);
        inputTextField.getValidators().get(0).setMessage(warning);
        inputTextField.validate();

        return inputTextField.getText().matches(valid.getRegexPattern());
    }

    public static boolean doubleValidator(JFXTextField numberTextField, String warning) {
        RegexValidator valid = new RegexValidator();

        valid.setRegexPattern("[0-9]*[.]?([0-9]{1,2})" );

        numberTextField.setValidators(valid);
        numberTextField.getValidators().get(0).setMessage(warning);
        numberTextField.validate();

        return numberTextField.getText().matches(valid.getRegexPattern());
    }

    public static boolean phoneValidator(JFXTextField phoneTextField, String warning) {
        RegexValidator valid = new RegexValidator();

        valid.setRegexPattern("[0-9]{10}");

        phoneTextField.setValidators(valid);
        phoneTextField.getValidators().get(0).setMessage(warning);
        phoneTextField.validate();

        return phoneTextField.getText().matches(valid.getRegexPattern());
    }
    /*
    public static boolean isDateValid(JFXDatePicker datePicker, String warning) {
        RegexValidator valid = new RegexValidator();

        //valid.setRegexPattern("[0-9]{1,2}+\\/[0-9]{1,2}+\\/[0-9]{4}");
        valid.setRegexPattern("[0-9]{4}+\\-[0-9]{1,2}+\\-[0-9]{1,2}");
        datePicker.setValidators(valid);
        datePicker.getValidators().get(0).setMessage(warning);
        datePicker.validate();

        return datePicker.getValue().toString().matches(valid.getRegexPattern());
    }
     */
}