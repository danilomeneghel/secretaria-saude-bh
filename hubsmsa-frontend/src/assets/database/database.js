// Banco de dados para o json-server (servidor fake para dados mockados)

// Adicione no array abaixo o que será disponibilizado na API
const apiArray = [
  conselhoProfissional(),
  especialidadePrincipal(),
  especialidadeSecundaria(),
  pesquisarEstrutura(),
  estabelecimentoDecidir(),
  editarEstrutura(),
  historicoEstrutura(),
  pesquisarEmpresa(),
  recuperarHistoricoEmpresa(),
  recuperarEmpresa(),
  recuperarEstabelecimentoEstruturaInterna(),
  recuperarEstruturaEstabelecimento(),
  recuperarMatriz()
];

function especialidadePrincipal() {
  return {
    "especialidade-principal": [{
      "id": 2,
      "name": "teste"
    }]
  }
}

function conselhoProfissional() {
  return {
    "conselho-profissional": [{
      "id": 1,
      "nome": "teste",
      "status": "A",
      "especialidades": [{ id: 1, codigo: 2, descricao: 'Epa' }],
    }]
  }
}

function especialidadeSecundaria() {
  return {
    "especialidade-secundaria": [{
      "id": 1,
      "name": "opa"
    }]
  }
}

function pesquisarEstrutura() {
  return {
    "estrutura": {
      "totalRegistros": 2,
      "itens": [
        {
          "id": 1,
          "codigoEstrutura": 1,
          "nomeEstrutura": "Almoxarifado",
          "tipoEstrutura": "HO",
          "dataAtualizacao": "30/03/2018",
          "status": "A"
        },
        {
          "id": 2,
          "codigoEstrutura": 15,
          "nomeEstrutura": "Oficina",
          "tipoEstrutura": "HO",
          "dataAtualizacao": "15/01/2019",
          "status": "I"
        }
      ]
    }
  }
}

function estabelecimentoDecidir() {
  return {
    "estabelecimentos-decidir": [{
      id: 3,
      nomeFantasia: 'Hospital de Belo Horizonte Hospital',
      tipoEstrutura: 'imovel',
      situacaoInstitucional: 'O',
      codigoCnes: '12354789',
      tipoEstabelecimento: {
        id: 1,
        descricao: 'Descricao Tipo Estabelecimento'
      },
      subtipoEstabelecimento: {
        id: 2,
        descricao: 'Descricao Subtipo Estabelecimento'
      },
      atividadePrincipal: {
        atividade: 'Grupo',
        descricao: 'Descricao atividade principal',
      },
      atividadesSecundarias: {
        itens: [{
          atividade: 'Assitencia a Saúde',
          descricao: 'Apoio Diagnóstico',
          atividade: 'Assitencia a Saúde',
          descricao: 'Reabilitação'
        }],
        totalRegistros: 2
      },
      endereco: {
        logradouro: 'Av Presidente Antonio Carlos',
        numero: '1596',
        bairro: 'Cachoeirinha',
        complemento: 'Complemento',
        regional: 1,
        uf: 'MG',
        municipio: 'Belo Horizonte',
        cep: '31130122',
        latitude: '-19.8157',
        longitude: '-43.9542 19° 48´ 57² Sul',
        codigoIbgeMunicipio: '3106200',
        codigoEndcorp: 766270
      },
      contato: {
        telefoneCompleto: '(31) 3449-7674',
        email: 'hospitalbelohorizonte@hob.com.br',
        nomeDiretor: 'Maria Jose Ferreira',
        registroConselhoClasse: 'registro de classe',
        acessoInternet: 'I'
      },
      codigoHubsmsa: {
        codigoSmsa: null,
        codigoContabil: null,
        codigoPostoColeta: null,
        codigoTipoUnidade: null,
        codigoSiatuReceita: null
      },
      camposAlterados: ['bairro', 'nomeFantasia', 'telefoneCompleto', 'tipoEstrutura', 'acessoInternet']
    }]
  }
}

function editarEstrutura() {
  return {
    "estrutura-editar": {
      "id": 1,
      "codigoEstrutura": 1,
      "nomeEstrutura": "Almoxarifado",
      "tipoEstrutura": "AD",
      "dataAtualizacao": "30/03/2018",
      "status": "A"
    }
  }
}

function historicoEstrutura() {
  return {
    "historico-estrutura": {
      "totalRegistros": 1,
      "itens": [{
        "dadoAnterior": "Almoxerifados",
        "dadoAtual": "Almoxarifado",
        "dataAlteracaoDadoAtual": "15/04/2018",
        "usuarioResponsavelAlteracao": "Bruna Ringuini"
      }
      ]
    }
  }
}


function pesquisarEmpresa() {
  return {
    "empresa": {
      "totalRegistros": 2,
      "itens": [{
        "id": 1,
        "codigo": 1,
        "nomeEmpresa": "Empresa Teste 1",
        "formaCadastro": "Carga Inicial",
        "dataAtualizacao": "27/02/2020",
        "status": 'A'
      },
      {
        "id": 2,
        "codigo": 2,
        "nomeEmpresa": "Empresa Teste 2",
        "formaCadastro": "Carga Inicial",
        "dataAtualizacao": "27/01/2020",
        "status": 'I'
      }
      ]
    }
  }
}

function recuperarEmpresa() {
  return {
    "empresa-recuperar-dados": {
      "id": 1,
      "codigo": 1,
      "nome": "Empresa Teste 1",
      "status": 'A'
    }
  }
}

function recuperarHistoricoEmpresa() {
  return {
    "recuperar-historico-empresa": {
      "totalRegistros": 2,
      "itens": [{
        "dadoAnterior": "teste",
        "dadoAtual": "teste 2",
        "dataAlteracaoDadoAtual": '03/03/2020',
        "usuarioResponsavelAlteracao": "Um usuario qualquer"
      }]
    }
  }
}

function recuperarEstruturaEstabelecimento() {
  return {
    "recuperar-estrutura-estabelecimento": {
      "totalRegistros": 2,
      "itens": [{
        "id": 1,
        "cnes": 3525770,
        "nome": "Clínica de doenças pulmonares do hospital de Belo Horizonte",
        "regional": "Norte",
        "situacao": "Oficial (CNES)",
        "matriz": "Unidade de pronto atendimento",
        "status": "Ativo"
      },
      {
        "id": 2,
        "cnes": 7312,
        "nome": "Hospital de Belo Horizonte",
        "regional": "Venda Nova",
        "situacao": "Oficial (CNES)",
        "matriz": null,
        "status": "Inativo"
      }]
    }
  }
}

function recuperarEstabelecimentoEstruturaInterna() {
  return {
    "recuperar-estabelecimento-estrutura-interna": {
      "id": 1,
      "nome": "Clínica de doenças pulmonares do hospital de Belo Horizonte",
      "cnes": 3525770,
      "status": "Ativo",
      "statusInstitucional": "Oficial (CNES)",
      "regional": "Norte"
    }
  }
}

function recuperarMatriz() {
  return {
    "recuperar-matriz": {
      "id": 1,
      "nome": "teste",
      "codigo": 2
    }
  }
}

module.exports = () => Object.assign({}, ...apiArray);
