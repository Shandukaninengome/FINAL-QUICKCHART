package accountcreation;

public class Login {

    private String username;
    private String password;
    private String cellphone;

    // Checks if username is valid
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Checks if password is valid
    public boolean checkPasswordComplexity(String password) {
        boolean hasUpper = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasNumber = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return password.length() >= 8
                && hasUpper
                && hasNumber
                && hasSpecial;
    }

    // Checks if cellphone number is valid
    public boolean checkCellPhoneNumber(String cellphone) {
        return cellphone.startsWith("+27")
                && cellphone.length() == 12;
    }

    // Registers a new user
    public String registerUser(String username,
                               String password,
                               String cellphone) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted, "
                 + "please ensure that your username contains "
                 + "an underscore and is no more than 5 "
                 + "characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, "
                 + "please ensure that the password contains "
                 + "at least 8 characters, a capital letter, "
                 + "a number and a special character.";
        }

        if (!checkCellPhoneNumber(cellphone)) {
            return "Cell phone number is incorrectly "
                 + "formatted, please ensure the number is "
                 + "in the format +27 followed by 9 digits.";
        }

        this.username  = username;
        this.password  = password;
        this.cellphone = cellphone;

        return "User registered successfully.";
    }

    // Logs in a user
    public boolean loginUser(String username, String password) {
        return this.username.equals(username)
                && this.password.equals(password);
    }

    // Returns login status message
    public String returnLoginStatus(boolean status,
                                    String username) {
        if (status) {
            return "Welcome to quickchat" + username
                 + ", it is great to see you again.";
        }
        return "Username or password incorrect, "
             + "please try again.";
    }
}