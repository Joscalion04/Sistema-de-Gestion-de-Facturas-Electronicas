package org.segundahacienda.segundahacienda.presentation;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("acerca_de")
public class Acerca_De {
    @GetMapping("/acerca_de")
    public String acerca_de(){
        return "/acerca_de/acerca_de";
    }
}
