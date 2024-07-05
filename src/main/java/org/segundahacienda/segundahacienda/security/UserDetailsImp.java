package org.segundahacienda.segundahacienda.security;

import org.segundahacienda.segundahacienda.logic.Proveedor;
import org.segundahacienda.segundahacienda.logic.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImp implements UserDetails {

    private Usuario user;
    private Proveedor prov;

    public UserDetailsImp(Usuario user, Proveedor prov) {
        this.user = user;
        this.prov = prov;
    }

    public Usuario getUser() {
        return user;
    }

    public Proveedor getProv() {
        return prov;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRol()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getContrasenha();
    }

    @Override
    public String getUsername() {
        return user.getCredencial();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}