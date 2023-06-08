package com.dotseven.captchaservice.captchatest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaptchaTestRepository extends JpaRepository<CaptchaTest, Long> {

    @Query(value = "SELECT * FROM captcha_test ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    public CaptchaTest getRandomTest();

    @Query(value = "SELECT answer FROM (SELECT correct_answer1 AS answer FROM captcha_test UNION  SELECT correct_answer2 AS answer FROM captcha_test) AS subquery WHERE answer <> :answer1_selected and answer <> :answer2_selected ORDER BY RANDOM() LIMIT 6", nativeQuery = true)
    public List<String> getRandomAnswers(@Param("answer1_selected") String firstAnswerSelected,
            @Param("answer2_selected") String secondAnswerSelected);
}
