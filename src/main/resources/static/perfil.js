function salvarPerfil(){
    let nome = $("#nome").val();
    let matricula = $("#matricula").val();
    let email = $("#email").val();
    let cargo = $("#cargo option:selected").val();
    let ativo = $("#ativo").prop("checked");
    let senhaAtual = $("#senhaAtual").val();
    let novaSenha = $("#novaSenha").val();
    let confSenha = $("#confSenha").val();

    $.ajax({
        type: "POST",
        url: "/edit/usuario",
        data:{
            nome:nome,
            matricula:matricula,
            email:email,
            cargo:cargo,
            ativo:ativo,
            senhaAtual:senhaAtual,
            novaSenha:novaSenha,
            confSenha:confSenha
        },
        success: function(data){
            if(data.sucesso){
                alertaSucesso("Cadastro atualizado com sucesso");
            }else{
                alert(data.mensagem);
            }
        },
        error: function(){
            alert("Deu ruim");
        }
    })
}