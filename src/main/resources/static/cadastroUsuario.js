function cadastrarUsuario(){
	let nome = $("#nome").val();
	let matricula = $("#matricula").val();
	let email = $("#email").val();
	let cargo = $("#cargo")[0].value;

	$.ajax({
		type: "POST",
		url: "/cadastro",
		data:{
			nome:nome,
			matricula:matricula,
			email:email,
			cargo:cargo,
		},
		success:function(data){
			alertaSucesso(data);
		},
		error: function(){
			alert("Não ok");
		}
	});
}