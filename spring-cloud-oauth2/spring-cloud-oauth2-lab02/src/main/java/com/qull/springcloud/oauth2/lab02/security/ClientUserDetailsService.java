package com.qull.springcloud.oauth2.lab02.security;

import com.qull.springcloud.oauth2.lab02.user.ClientUser;
import com.qull.springcloud.oauth2.lab02.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-9 下午3:42
 */
@Service
public class ClientUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ClientUser> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return new ClientUserDetails(optionalUser.get());
    }
}
