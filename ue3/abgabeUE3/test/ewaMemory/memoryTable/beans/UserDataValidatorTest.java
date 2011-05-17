/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ewaMemory.memoryTable.beans;

import ewaMemory.memoryTable.controller.UserDataValidator;
import javax.faces.validator.ValidatorException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stephan
 */
public class UserDataValidatorTest {
    private final UserDataValidator validator;

    public UserDataValidatorTest() {
        validator = new UserDataValidator();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

   @Test
   public void validateDate_passValidDate_shouldPass() {
        validator.validateDate(null, null, "12.07.1989");
   }

      @Test
   public void validateDate_passEmptyString_shouldPass() {
        validator.validateDate(null, null, "");
   }



    @Test(expected = ValidatorException.class)
    public void validateDate_passInvalidDay_tooLong_shouldThrowException() {
        validator.validateDate(null, null, "134.07.1989");
    }

    @Test(expected = ValidatorException.class)
    public void validateDate_passInvalidDay_zero_shouldThrowException() {
        validator.validateDate(null, null, "0.07.1989");
    }

    @Test(expected = ValidatorException.class)
    public void validateDate_passInvalidMonth_tooLong_shouldThrowException() {
        validator.validateDate(null, null, "12.156.1989");
    }

    @Test(expected = ValidatorException.class)
    public void validateDate_passInvalidMonth_zero_shouldThrowException() {
        validator.validateDate(null, null, "12.0.1989");
    }

    @Test(expected = ValidatorException.class)
    public void validateDate_passInvalidYear_tooLong_shouldThrowException() {
        validator.validateDate(null, null, "12.07.19899");
    }

    @Test(expected = ValidatorException.class)
    public void validateDate_passInvalidYear_tooShort_shouldThrowException() {
        validator.validateDate(null, null, "12.07.198");
    }

    @Test(expected = ValidatorException.class)
    public void validateDate_passCharacters_shouldThrowException() {
        validator.validateDate(null, null, "12.Ac.198");
    }

    @Test(expected = ValidatorException.class)
    public void validateDate_passInvalidDelimiters_shouldThrowException() {
        validator.validateDate(null, null, "12/07/1989");
    }

    @Test
    public void validateStacksize_passEmptyString_shouldPass() {
        validator.validateStacksize(null, null, "");
    }

    @Test
    public void validateStacksize_pass2x2_shouldPass() {
        validator.validateStacksize(null, null, "2x2");
    }

    @Test
    public void validateStacksize_pass4x4_shouldPass() {
        validator.validateStacksize(null, null, "4x4");
    }

    @Test
    public void validateStacksize_pass6x6_shouldPass() {
        validator.validateStacksize(null, null, "6x6");
    }

     @Test(expected = ValidatorException.class)
    public void validateStacksize_pass2x4_shouldThrowException() {
        validator.validateStacksize(null, null, "2x4");
    }

      @Test(expected = ValidatorException.class)
    public void validateStacksize_pass3x3_shouldThrowException() {
        validator.validateStacksize(null, null, "3x3");
    }

      @Test(expected = ValidatorException.class)
    public void validateStacksize_pass4x6_shouldThrowException() {
        validator.validateStacksize(null, null, "4x6");
    }


}