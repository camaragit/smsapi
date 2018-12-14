package dame.api.orange.ws.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Data
public class MyUserDetails implements UserDetails {
    private final Long Id;
    private final String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final String password;
    private final User user;
    private final  boolean enabled;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Profil role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getNomRole()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
