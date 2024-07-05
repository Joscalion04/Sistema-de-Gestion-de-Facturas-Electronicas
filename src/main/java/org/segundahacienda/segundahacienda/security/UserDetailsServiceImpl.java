package org.segundahacienda.segundahacienda.security;

import org.segundahacienda.segundahacienda.data.ProveedoresRepository;
import org.segundahacienda.segundahacienda.data.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsuariosRepository userRepository;

    @Autowired
    ProveedoresRepository proveedoresRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return new org.segundahacienda.segundahacienda.security.UserDetailsImp(userRepository.findById(username).get(),
                    proveedoresRepository.encontrar_proveedor(username));
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
    }
}
