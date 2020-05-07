package configuration;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

public class DataValidation {

    public static boolean emailValidation(JFXTextField emailTextField, String warning) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        boolean isEmailValid = true;
        validator.setMessage(warning);
        emailTextField.getValidators().add(validator);

        if (!emailTextField.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com")) {
            emailTextField.validate();
            isEmailValid = false;
        } else {
            emailTextField.resetValidation();
        }
        return isEmailValid;
    }

    public static boolean textValidation(JFXTextField inputTextField, String warning) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        boolean isTextValid = true;
        validator.setMessage(warning);
        inputTextField.getValidators().add(validator);

        if (!inputTextField.getText().matches("[a-zA-Z]{3,16}")) {
            inputTextField.validate();
            isTextValid = false;
        } else {
            inputTextField.resetValidation();
        }

        return isTextValid;
    }

    public static boolean doubleValidator(JFXTextField numberTextField, String warning) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        boolean isNumberValid = true;
        validator.setMessage(warning);
        numberTextField.getValidators().add(validator);

        if (!numberTextField.getText().matches("[0-9]*[.]?([0-9]{1,2})")) {
            numberTextField.validate();
            isNumberValid = false;
        } else {
            numberTextField.resetValidation();
        }

        return isNumberValid;
    }

    public static boolean phoneValidator(JFXTextField phoneTextField, String warning) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        boolean isPhoneValid = true;
        validator.setMessage(warning);
        phoneTextField.getValidators().add(validator);

        if (!phoneTextField.getText().matches("[0-9]{10}")) {
            phoneTextField.validate();
            isPhoneValid = false;
        } else {
            phoneTextField.resetValidation();
        }

        return isPhoneValid;
    }
    /*
    public static boolean isDateValid(String dateTextField, Label inputLabel, String warning) {
        boolean isDateValid = true;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate.parse(dateTextField, dateFormatter);
        } catch (DateTimeParseException e) {
            inputLabel.setText(warning);
            return false;
        }
        return isDateValid;
    }
     */
}