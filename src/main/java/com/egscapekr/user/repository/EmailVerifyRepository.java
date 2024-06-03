package com.egscapekr.user.repository;

import com.egscapekr.user.entity.EmailVerify;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailVerifyRepository extends CrudRepository<EmailVerify, String> {

    List<EmailVerify> findByVerifyCode(String verifyCode);
}
