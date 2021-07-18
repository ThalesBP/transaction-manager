package br.thales.tools.transactions.manager.models;

import br.thales.test.TestBase;

import br.thales.tools.transactions.manager.error.ModelException;
import br.thales.tools.transactions.manager.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import static org.junit.Assert.assertThrows;

public class CustomerTests extends TestBase {

    Customer customer;

    @Before
    public void setUp() {
        customer = new Customer();
    }

    @Test
    public void normalize0NameTest() {
        String[] name = {};
        ModelException modelException = assertThrows(
                "For empty name, null response is expected",
                ModelException.class,
                () -> { Customer.normalizeName(name); }
        );
        Assert.isTrue(modelException.getMessage().equals("Name cannot be empty"), "Wrong message: " + modelException.getMessage());
    }

    @Test
    public void normalize1NameTest() {
        String[] name = {"Thales"};
        try {
            name = Customer.normalizeName(name);
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
            name = Customer.normalizeName(name);
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
            name = Customer.normalizeName(name);
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
            name = Customer.normalizeName(name);
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
        customer.setName("Thales Bueno Pasqual");
        Assert.isTrue("Thales".equals(customer.getFirstName()), WRONG_TEXT + customer.getFirstName());
        Assert.isTrue("Bueno".equals(customer.getMiddleName()), WRONG_TEXT + customer.getMiddleName());
        Assert.isTrue("Pasqual".equals(customer.getLastName()), WRONG_TEXT + customer.getLastName());

    }

    @Test
    public void getFullNameTest() {
        customer.setFirstName("Thales");
        customer.setMiddleName("Bueno");
        customer.setLastName("Pasqual");
        Assert.isTrue("Thales Bueno Pasqual".equals(customer.getName()), WRONG_TEXT + customer.getName());
    }
}
