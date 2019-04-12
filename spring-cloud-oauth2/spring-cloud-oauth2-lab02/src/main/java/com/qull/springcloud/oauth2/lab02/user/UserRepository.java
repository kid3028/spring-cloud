package com.qull.springcloud.oauth2.lab02.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-9 下午3:26
 */
public interface UserRepository extends JpaRepository<ClientUser, Long> {

    Optional<ClientUser> findByUsername(String username);
}
