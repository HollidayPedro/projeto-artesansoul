package com.artesansoul.artesansoulV3.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gerenciarConta")
public class GerenciarContaController {

    @GetMapping("/TelaGerenciar")
    public String telaGerenciar(){

        return "gerenciarConta";

    }

    @GetMapping("/FormAtualizarPerfil")
    public String formAtualizar(){

        return "atualizarPerfil";

    }

}
