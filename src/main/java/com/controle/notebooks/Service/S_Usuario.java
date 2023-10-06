package com.controle.notebooks.Service;

import com.controle.notebooks.Model.M_Usuario;
import com.controle.notebooks.Repository.R_Usuario;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class S_Usuario {
    private static R_Usuario r_usuario;

    public S_Usuario(R_Usuario r_usuario) {
        this.r_usuario =  r_usuario;
    }

    public static M_Usuario verificaLogin(String matricula, String senha){
        matricula = S_Generico.limparNumero(matricula);

        if(S_Generico.textoEstaVazio(matricula)){
            return null;
        }else if(S_Generico.textoEstaVazio(senha)){
            return null;
        }
        return r_usuario.buscarPorMatriculaSenha(Long.parseLong(matricula),
                senha);
    }

    public static String cadastrarUsuario(String nome, String cargo, String matricula, String email){
        boolean podeSalvar = true;
        String mensagem = "";

        if(S_Generico.textoEstaVazio(nome)){
            podeSalvar = false;
            mensagem += "O nome precisa ser preenchido!";
        }
        if(!S_Generico.validarEmail(email)){
            podeSalvar = false;
            mensagem += "e-Mail inválido!";
        }
        if(S_Generico.textoEstaVazio(S_Generico.limparNumero(matricula))){
            podeSalvar = false;
            mensagem += "A matricula precisa ser informada!";
        }
        if(S_Generico.textoEstaVazio(S_Generico.limparNumero(cargo))){
            podeSalvar = false;
            mensagem += "O cargo deve ser selecionado!";
        }

        if(podeSalvar){
            M_Usuario m_usuario = new M_Usuario();
            m_usuario.setNome(nome);
            m_usuario.setEmail(email);
            m_usuario.setMatricula(Long.parseLong(matricula));
            m_usuario.setId_cargo(Long.parseLong(cargo));
            m_usuario.setSenha(S_GeradorSenha.gerarSenha(5,3,2));

            try{
                r_usuario.save(m_usuario);
                mensagem += "Usuário cadastrado com sucesso!";
            }catch (DataIntegrityViolationException e){
                mensagem += "Falha ao cadastrar usuário";
            }
        }
        return mensagem;
    }
}
