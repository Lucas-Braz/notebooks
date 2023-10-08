package com.controle.notebooks.Controller;

import com.controle.notebooks.Model.M_Usuario;
import com.controle.notebooks.Service.S_Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_Usuario {
    @GetMapping("/")
    public String getLogin(HttpSession session){
        if(session.getAttribute("usuario") != null) {
            return "redirect:/Home";
        }else{
            return "index";
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public boolean postLogin(@RequestParam("matricula") String matricula,
                             @RequestParam("senha") String senha,
                             HttpSession session){
        session.setAttribute("usuario", S_Usuario.verificaLogin(matricula, senha));
        if(session.getAttribute("usuario") == null){
            return false;
        }else{
            return true;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("usuario", null);
        return "redirect:/";
    }

    @GetMapping("/cadastro")
    public String getCadastro(){
        return "usuario/cadastro";
    }

    @PostMapping("/cadastro")
    @ResponseBody
    public String cadastrarUsuario(@RequestParam("nome") String nome,
                                   @RequestParam("email") String email,
                                   @RequestParam("matricula") String matricula,
                                   @RequestParam("cargo") String cargo){
        return S_Usuario.cadastrarUsuario(nome,  cargo, matricula, email);
    }

    @GetMapping("/testeUpdate")
    @ResponseBody
    public void testeUpdate(HttpSession session){
        Object usuario = session.getAttribute("usuario");
        S_Usuario.updateTeste((M_Usuario) usuario);
    }
}
