package com.qull.springcloud.oauth2.lab02.security;

import com.qull.springcloud.oauth2.lab02.user.ClientUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-9 下午3:10
 */
@Data
public class ClientUserDetails implements UserDetails {

    private ClientUser clientUser;

    public ClientUserDetails(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet();
    }

    public String getPassword() {
        return clientUser.getPassword();
    }

    public String getUsername() {
        return clientUser.getUsername();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
