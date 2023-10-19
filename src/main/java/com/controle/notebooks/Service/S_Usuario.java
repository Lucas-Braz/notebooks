package com.controle.notebooks.Service;

import com.controle.notebooks.Model.M_Resposta;
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
            m_usuario.setAtivo(true);

            try{
                r_usuario.save(m_usuario);
                mensagem += "Usuário cadastrado com sucesso!";
            }catch (DataIntegrityViolationException e){
                mensagem += "Falha ao cadastrar usuário";
            }
        }
        return mensagem;
    }

    public static M_Resposta updateUsuario(String nome, String cargo, String matricula,String email, String senhaAtual,
                                     String novaSenha, String confSenha,boolean ativo, Object usuario){
        boolean podeEditar = false;
        String mensagem = "";

        M_Usuario m_usuario = (M_Usuario) usuario;

        if(m_usuario.getId_cargo() != 1){
            matricula = m_usuario.getMatricula().toString();
            cargo = m_usuario.getId_cargo().toString();
            ativo = m_usuario.isAtivo();
        }
        matricula = S_Generico.limparNumero(matricula);

        if(senhaAtual.equals(m_usuario.getSenha())){
            podeEditar = true;
            if(S_Generico.textoEstaVazio(nome)){
                podeEditar = false;
                mensagem += "O nome precisa ser preenchido!";
            }
            if(S_Generico.textoEstaVazio(matricula)){
                podeEditar = false;
                mensagem += "A matricula precisa ser preenchida!";
            }
            if(!S_Generico.validarEmail(email)){
                podeEditar = false;
                mensagem += "e-Mail Inválido!";
            }
            if(!novaSenha.equals(confSenha) && !S_Generico.textoEstaVazio(novaSenha)){
                podeEditar = false;
                mensagem += "A Nova senha e a confirmação de senha precisam ser iguais!";
            }

            if(podeEditar){
                m_usuario.setNome(nome);
                m_usuario.setMatricula(Long.parseLong(matricula));
                m_usuario.setId_cargo(Long.parseLong(cargo));
                m_usuario.setEmail(email);
                if(!S_Generico.textoEstaVazio(novaSenha)) {
                    m_usuario.setSenha(novaSenha);
                }
                m_usuario.setAtivo(ativo);
                try {
                    r_usuario.save(m_usuario);
                    mensagem += "Perfil atualizado com sucesso";
                }catch (DataIntegrityViolationException e){
                    podeEditar = false;
                    mensagem += "Falha ao tentar atualizar o cadastro: "+ e.getMessage();
                }
            }
        }else{
            mensagem += "Senha inválida, o cadastro não pode ser editado!";
        }
        return new M_Resposta(podeEditar,mensagem);
    }
}
