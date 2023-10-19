package com.controle.notebooks.Controller;

import com.controle.notebooks.Model.M_Resposta;
import com.controle.notebooks.Model.M_Usuario;
import com.controle.notebooks.Service.S_Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getCadastro(HttpServletRequest request){
        if(request.getHeader("Referer") != null){
            return "usuario/cadastro";
        }else{
            return "redirect:/";
        }
    }

    @PostMapping("/cadastro")
    @ResponseBody
    public String cadastrarUsuario(@RequestParam("nome") String nome,
                                   @RequestParam("email") String email,
                                   @RequestParam("matricula") String matricula,
                                   @RequestParam("cargo") String cargo){
        return S_Usuario.cadastrarUsuario(nome,  cargo, matricula, email);
    }

    @GetMapping("/edit/usuario")
    public String getEditUsuario(HttpServletRequest request,
                                 HttpSession session,
                                 Model model){
        if(request.getHeader("Referer") != null){
            Object usuario = session.getAttribute("usuario");
            model.addAttribute("usuario", usuario);
            if(((M_Usuario) usuario).getId_cargo() == 1){
                return "/usuario/pv/edit_usuario_admin";
            }else{
                return "/usuario/pv/edit_usuario";
            }
        }else{
            return "redirect:/";
        }
    }

    @PostMapping("/edit/usuario")
    @ResponseBody
    public M_Resposta postEditUsuario(@RequestParam("nome") String nome,
                                      @RequestParam("email") String email,
                                      @RequestParam("senhaAtual") String senhaAtual,
                                      @RequestParam("novaSenha") String novaSenha,
                                      @RequestParam("confSenha") String confSenha,
                                      @RequestParam(value="cargo", required = false) String cargo,
                                      @RequestParam(value="matricula", required = false) String matricula,
                                      @RequestParam(value="ativo", required = false) boolean ativo,
                                      HttpSession session
                                  ){
        return S_Usuario.updateUsuario(nome,cargo,matricula,email,senhaAtual,novaSenha,confSenha, ativo, session.getAttribute("usuario"));
    }
}
