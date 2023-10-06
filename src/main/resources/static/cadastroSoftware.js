function enviarCadastroSoftware(){
    let nome = $("#nome").val();
    let exigeLicenca = $("#pago")[0].checked;

    $.ajax({
        type: "POST",
        url: "/cadastro/software",
        data:{
            nome: nome,
            pago: exigeLicenca
        },
        success: function(data){
            alert(data);
        },
        error: function(){
            alert("Deu ruim");
        }
    });
}