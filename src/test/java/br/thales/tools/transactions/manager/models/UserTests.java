package br.thales.tools.transactions.manager.models;

import br.thales.test.TestBase;

import br.thales.tools.transactions.manager.error.ModelException;
import br.thales.tools.transactions.manager.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import static org.junit.Assert.assertThrows;

public class UserTests extends TestBase {

    User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void normalize0NameTest() {
        String[] name = {};
        ModelException modelException = assertThrows(
                "For empty name, null response is expected",
                ModelException.class,
                () -> { User.normalizeName(name); }
        );
        Assert.isTrue(modelException.getMessage().equals("Name cannot be empty"), "Wrong message: " + modelException.getMessage());
    }

    @Test
    public void normalize1NameTest() {
        String[] name = {"Thales"};
        try {
            name = User.normalizeName(name);
        } catch (ModelException e) {
            System.out.println("Error testing: " + e.getMessage());
            Assert.isTrue(false, "Error testing: " + e.getMessage());
            return;
        }
        Assert.isTrue(name.length == 3, WRONG_SIZE + name.length);
        Assert.isTrue("Thales".equals(name[0]), WRONG_TEXT + name[0]);
        Assert.isTrue("".equals(name[1]), WRONG_TEXT + name[1]);
        Assert.isTrue("".equals(name[2]), WRONG_TEXT + name[2]);
    }
    @Test
    public void normalize2NameTest() {
        String[] name = {"Thales", "Pasqual"};
        try {
            name = User.normalizeName(name);
        } catch (ModelException e) {
            System.out.println("Error testing: " + e.getMessage());
            Assert.isTrue(false, "Error testing: " + e.getMessage());
            return;
        }
        Assert.isTrue(name.length == 3, WRONG_SIZE + name.length);
        Assert.isTrue("Thales".equals(name[0]), WRONG_TEXT + name[0]);
        Assert.isTrue("".equals(name[1]), WRONG_TEXT + name[1]);
        Assert.isTrue("Pasqual".equals(name[2]), WRONG_TEXT + name[2]);
    }
    @Test
    public void normalize3NameTest() {
        String[] name = {"Thales", "Bueno", "Pasqual"};
        try {
            name = User.normalizeName(name);
        } catch (ModelException e) {
            System.out.println("Error testing: " + e.getMessage());
            Assert.isTrue(false, "Error testing: " + e.getMessage());
            return;
        }
        Assert.isTrue(name.length == 3, WRONG_SIZE + name.length);
        Assert.isTrue("Thales".equals(name[0]), WRONG_TEXT + name[0]);
        Assert.isTrue("Bueno".equals(name[1]), WRONG_TEXT + name[1]);
        Assert.isTrue("Pasqual".equals(name[2]), WRONG_TEXT + name[2]);
    }
    @Test
    public void normalizeNNameTest() {
        String[] name = {"Thales", "Bueno", "ExtraName", "ExtraName2", "Pasqual"};
        try {
            name = User.normalizeName(name);
        } catch (ModelException e) {
            System.out.println("Error testing: " + e.getMessage());
            Assert.isTrue(false, "Error testing: " + e.getMessage());
            return;
        }
        Assert.isTrue(name.length == 3, WRONG_SIZE + name.length);
        Assert.isTrue("Thales".equals(name[0]), WRONG_TEXT + name[0]);
        Assert.isTrue("Bueno ExtraName ExtraName2".equals(name[1]), WRONG_TEXT + name[1]);
        Assert.isTrue("Pasqual".equals(name[2]), WRONG_TEXT + name[2]);
    }

    @Test
    public void setFullNameTest() {
        try {
            user.setFullName("Thales Bueno Pasqual");
        } catch (ModelException e) {
            e.printStackTrace();
        }
        Assert.isTrue("Thales".equals(user.getFirstName()), WRONG_TEXT + user.getFirstName());
        Assert.isTrue("Bueno".equals(user.getMiddleName()), WRONG_TEXT + user.getMiddleName());
        Assert.isTrue("Pasqual".equals(user.getLastName()), WRONG_TEXT + user.getLastName());

    }

    @Test
    public void getFullNameTest() {
        user.setFirstName("Thales");
        user.setMiddleName("Bueno");
        user.setLastName("Pasqual");
        Assert.isTrue("Thales Bueno Pasqual".equals(user.getFullName()), WRONG_TEXT + user.getFullName());
    }
}
