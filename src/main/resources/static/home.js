$('a').click(function(event){
    event.preventDefault();
    if(!$(this).hasClass('btn')){
        $('a').removeClass('active disabled');
        $(this).addClass('active disabled');
    }
    controleRotasGet($(this).attr("href"));
});

$('.navbar-brand').off('click');

function controleRotasGet(url){
    switch(url){
        case "/logout":
            gerarSwal(url);
            break;
        case "/cadastro/notebooks":
            $.get(url,function(data){
                $(".container").html(data);
                $("#enviar").click(cadastrarNotebooks);
            });
            break;
        case "/cadastro":
            $.get(url,function(data){
                $(".container").html(data);
                $("#enviar").click(cadastrarUsuario);
            });
            break;
        case "/cadastro/software":
                    $.get(url,function(data){
                        $(".container").html(data);
                        $("#enviar").click(enviarCadastroSoftware);
                    });
                    break;
        default:
            $.get(url,function(data){
                $(".container").html(data);
            });
    }
}

function gerarSwal(urlSucesso){
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success me-2',
        cancelButton: 'btn btn-danger ms-2'
      },
      buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
      title: 'Sair?',
      text: "Você realmente deseja sair da aplicação?",
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: '<i class="fa-solid fa-thumbs-up"></i> Sim!',
      cancelButtonText: '<i class="fa-solid fa-thumbs-down"></i> Não!',
      reverseButtons: false
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href=urlSucesso;
        }
    });
}

function alertaSucesso(mensagem){
    Swal.fire({
        position: 'top-end',
        toast: true,
        icon: 'success',
        title: mensagem,
        showConfirmButton: false,
        timer: 2000
    });
}