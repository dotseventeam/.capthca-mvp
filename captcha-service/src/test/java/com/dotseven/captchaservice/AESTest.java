package com.dotseven.captchaservice;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dotseven.captchaservice.token.AES;

import static org.junit.Assert.*;

@SpringBootTest
public class AESTest {

    @Test
    public void testEncryptionDecryption() {
        String originalString = "Hello, World!";
        String secretKey = "mySecretKey";

        String encryptedString = AES.encrypt(originalString, secretKey);
        assertNotNull(encryptedString);

        String decryptedString = AES.decrypt(encryptedString, secretKey);
        assertNotNull(decryptedString);

        assertEquals(originalString, decryptedString);
    }

    @Test
    public void testAES256EncryptionDecryption() {
        String originalString = "Hello, World!";
        String secretKey = "mySecretKey";

        String encryptedString = AES.encrypt(originalString, secretKey);
        assertNotNull(encryptedString);

        String decryptedString = AES.decrypt(encryptedString, secretKey);
        assertNotNull(decryptedString);

        assertEquals(originalString, decryptedString);
    }
}
