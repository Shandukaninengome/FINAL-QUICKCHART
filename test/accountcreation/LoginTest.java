package accountcreation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    Login login;

    @BeforeEach
    public void setUp() {
        login = new Login();
    }

    // Test 1: Valid username
    @Test
    public void testCheckUserName_Valid() {
        assertTrue(login.checkUserName("ky_l1"));
    }

    // Test 2: Invalid username - no underscore
    @Test
    public void testCheckUserName_NoUnderscore() {
        assertFalse(login.checkUserName("kyle1"));
    }

    // Test 3: Invalid username - too long
    @Test
    public void testCheckUserName_TooLong() {
        assertFalse(login.checkUserName("kyle_smith"));
    }

    // Test 4: Valid password
    @Test
    public void testCheckPasswordComplexity_Valid() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec#ke99!"));
    }

    // Test 5: Invalid password - no special character
    @Test
    public void testCheckPasswordComplexity_NoSpecial() {
        assertFalse(login.checkPasswordComplexity("Password1"));
    }

    // Test 6: Invalid password - too short
    @Test
    public void testCheckPasswordComplexity_TooShort() {
        assertFalse(login.checkPasswordComplexity("Ab1!"));
    }

    // Test 7: Valid cellphone number
    @Test
    public void testCheckCellPhoneNumber_Valid() {
        assertTrue(login.checkCellPhoneNumber("+27831234567"));
    }

    // Test 8: Invalid cellphone - wrong start
    @Test
    public void testCheckCellPhoneNumber_WrongStart() {
        assertFalse(login.checkCellPhoneNumber("0831234567"));
    }

    // Test 9: Successful registration
    @Test
    public void testRegisterUser_Success() {
        String result = login.registerUser(
                "ky_l1",
                "Ch&&sec#ke99!",
                "+27831234567");
        assertEquals("User registered successfully.", result);
    }

    // Test 10: Failed registration - bad username
    @Test
    public void testRegisterUser_BadUsername() {
        String result = login.registerUser(
                "kyle",
                "Ch&&sec#ke99!",
                "+27831234567");
        assertNotEquals("User registered successfully.", result);
    }

    // Test 11: Successful login
    @Test
    public void testLoginUser_Success() {
        login.registerUser(
                "ky_l1",
                "Ch&&sec#ke99!",
                "+27831234567");
        assertTrue(login.loginUser("ky_l1", "Ch&&sec#ke99!"));
    }

    // Test 12: Failed login - wrong password
    @Test
    public void testLoginUser_WrongPassword() {
        login.registerUser(
                "ky_l1",
                "Ch&&sec#ke99!",
                "+27831234567");
        assertFalse(login.loginUser("ky_l1", "wrongpass"));
    }

    // Test 13: Login status message - success
    @Test
    public void testReturnLoginStatus_Success() {
        String result = login.returnLoginStatus(true, "ky_l1");
        assertTrue(result.contains("Welcome"));
    }

    // Test 14: Login status message - failure
    @Test
    public void testReturnLoginStatus_Failure() {
        String result = login.returnLoginStatus(false, "ky_l1");
        assertTrue(result.contains("incorrect"));
    }
}