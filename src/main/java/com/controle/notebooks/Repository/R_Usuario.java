package com.controle.notebooks.Repository;

import com.controle.notebooks.Model.M_Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface R_Usuario extends JpaRepository<M_Usuario, Long> {
    @Query(value="SELECT * FROM usuarios WHERE matricula = :matricula and " +
            "senha = :senha", nativeQuery = true)
    M_Usuario buscarPorMatriculaSenha (@Param("matricula") Long matricula,
                        @Param("senha") String senha);

    @Modifying
    @Transactional
    @Query("UPDATE M_Usuario u SET u.senha = :senha where u.id = :id")
    void atualizaSenhaUsuarioPorId(@Param("senha") String senha,@Param("id") Long id);
}
