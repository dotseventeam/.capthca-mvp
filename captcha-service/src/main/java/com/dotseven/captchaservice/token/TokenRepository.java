package com.dotseven.captchaservice.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>{


    @Query(value = "SELECT * FROM token WHERE app_id = :appId AND captcha_id = :captchaId",nativeQuery = true)
    public Token getToken(@Param("appId") String appID, @Param("captchaId") String captchaID);
}
