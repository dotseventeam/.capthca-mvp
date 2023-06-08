package com.dotseven.captchaservice.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {

    
    @Query(value = "SELECT * FROM app WHERE app_id= :appId",nativeQuery = true)
    public App getApp(@Param("appId") String appID);

    
}
