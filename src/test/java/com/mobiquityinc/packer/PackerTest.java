package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PackerTest {
    private Packer packer;

    @Before
    public void setup() {
        packer = new Packer();
    }

    @Test
    public void testPack_valid() {
        String fileName = "/Users/analiaramirez/Git/mobiquity/src/test/resources/input-test.txt";
        try {
            String result = packer.pack(fileName);
            assertNotNull(result);
            assertEquals("Error during test execution:\n", "4\n\n2,7\n8,9\n", result);
        } catch (APIException e) {
            fail("Error during test execution:\n" + e);
        }
    }

    @Test
    public void testPack_invalid() {
        String fileName = "";
        try {
            String result = packer.pack(fileName);
            fail("Error during test execution.\n");
        } catch (APIException e) {
            assertEquals("Error during test execution.", "Error Processing File.", e.getMessage());
        }
    }
}
