var EventosEnum = {
      CCE : {codigo: 110110, nome: "Carta de Correção Eletrônica"},
      CANC: { codigo: 110111, nome: "Cancelamento pelo emitente" },
      EPEC: { codigo: 110140, nome: "EPEC-Emissão em Contingência" }, 
      CONF_DEST : {codigo: 210200, nome: "Confirmação da Operação pelo Destinatário"},
      CIENCIA_OP_DEST : {codigo: 210210, nome: "Ciência da Operação pelo Destinatário"},
      DESC_OP_DEST : {codigo: 210220, nome: "Desconhecimento da Operação pelo Destinatário"},
      OP_NREALIZADA : {codigo: 210240, nome: "Operação não Realizada"},
      REG_PAS : {codigo: 610500, nome: "Registro Passagem NF-e"},
      REG_PAS_BRID : {codigo: 610550, nome: "Registro Passagem NF-e BRId"},
      CANC_REG_PAS : {codigo: 610501, nome: "Cancelamento Registro Passagem NF-e"},
      CTE_AUT : {codigo: 610600, nome: "CT-e Autorizado"},
      CANC_CTE_AUT : {codigo: 610601, nome: "CT-e Cancelado"},      
      PED_PRORROGACAO: { codigo: 0, nome: "Pedido de Prorrogação" },
      PED_CANC_PRORROGACAO: { codigo: 0, nome: "Cancelamento de Pedido de Prorrogação" },
      PED_FISCO_PRORROGACAO: { codigo: 0, nome: "Fisco Resposta Pedido de Prorrogação / Cancelamento Prorrogação" },
      MDFE_AUT: { codigo: 610610, nome: "MDF-e Autorizado" },
      MDFE_CANC : {codigo: 610611, nome: "MDF-e Cancelado"},      
      VIST_SUFRAMA : {codigo: 990900, nome: "Vistoria SUFRAMA"},
      INT_SUFRAMA : {codigo: 990910, nome: "Internalização SUFRAMA"}
    }; 

    $(document).ready(function() {
        $('.toggle').click(function() {
	        $(this).toggleClass('opened').next('.toggable').toggle();
        }).next('.toggable').hide();
        $('td > fieldset').addClass('fieldset-internal');
        if (getQuerystring('print')) {
            $('.toggle').click();
            window.print();
        }
    });
  
    
    function mostraAba(num)
    {
        $('.nft').hide(); //OK
        $('#botoes_nft li').removeClass('nftselected'); //OK
        $('#aba_nft_' + num).show(); //OK
        $('#tab_' + num).addClass('nftselected');
    }

    var janela = null;
    function visualizaEvento(idConteudo, nomeEvento)
    {      
        if(janela){
            janela.focus();
        }
        if (document.getElementById(idConteudo)) 
        {
            janela = window.open('', 'winNFeCompleta', 'top=50,left=50,width=800,height=500,scrollbars=yes,status=yes');
            var HTML = '';
            HTML += '		<html>';
            HTML += '			<head>';
            HTML += '				<title>Evento</title>';
            HTML += '<link href=\"../../style/common.css\" rel=\"stylesheet\" type=\"text/css\" />';
            HTML += '<link href=\"../../style/consultaPublicaNFe.css\" rel=\"stylesheet\" type=\"text/css\" />';
            HTML += '<link href=\"../../style/sistema.css\" rel=\"stylesheet\" type=\"text/css\" />';
            HTML += '<link href=\"../../style/nfe-vis.css\" rel=\"stylesheet\" type=\"text/css\" />';
            HTML += '			</head>';
            HTML += '		<body class="GeralXslt" style="overflow-y: inherit;"> ';
            HTML += document.getElementById(idConteudo).innerHTML;
            HTML += '        </body>';
            HTML += '		</html>';
            
            janela.document.write(HTML);
            janela.document.close();
        }
        else 
        {
          alert(nomeEvento + ' não disponível.');
        }
    }
    function finaliza()
    {
    try 
    {
        janela.close();
    } catch(e) 
    { 
    }
    }     

function getQuerystring(key, default_) {
	if (default_ == null) default_ = "";
	key = key.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
	var regex = new RegExp("[\\?&]" + key + "=([^&#]*)");
	var qs = regex.exec(window.location.href);
	if (qs == null) return default_;
	else return qs[1];
}

