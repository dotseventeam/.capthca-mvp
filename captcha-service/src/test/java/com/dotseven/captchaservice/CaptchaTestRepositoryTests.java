/*package com.dotseven.captchaservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CaptchaTestRepositoryTests {
    
    @Autowired
    private CaptchaTestRepository repo;

    private CaptchaTest captchaTest = new CaptchaTest();

    @Test
    public void giveCaptchaTestObjectTests(){
        repo.save(captchaTest);
    }

    @Test
    public void injectedComponentsAreNotNull() {
        assertNotNull(repo);
    }

    @Test
    public void Test() {
        List<String> randomAnswers = repo.getRandomAnswers(8);
        if (randomAnswers.isEmpty())
            assertEquals(randomAnswers.size(), 0);
        else
            assertEquals(randomAnswers.size(), 8);
    }

}
*/