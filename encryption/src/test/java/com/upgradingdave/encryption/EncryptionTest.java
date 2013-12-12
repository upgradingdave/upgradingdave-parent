package com.upgradingdave.encryption;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/encryption-test-context.xml"})
public class EncryptionTest {

    @Autowired
    EncryptedValue simple;

    /**
     * This test should run as is. To test environment variables, Try setting APP_ENCRYPTION_PASSWORD to wrong
     * password and watch it fail. Then set it to correct password and it should work.
     */
    @Test
    public void decrypt(){

        assertEquals("hotdog", simple.getValue());

    }

}
