import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';

export const permissoesRotas = {

  exemplo: [
    PermissaoUsuarioEnum.CADASTRO_PESQUISAREXEMPLO,
    PermissaoUsuarioEnum.CADASTRO_MANTEREXEMPLO
  ],

  empresas: [
    PermissaoUsuarioEnum.CADASTROS_EMPRESA_CONSULTAR,
    PermissaoUsuarioEnum.CADASTROS_EMPRESA_CADASTRAR,
    PermissaoUsuarioEnum.CADASTROS_EMPRESA_EDITAR,
    PermissaoUsuarioEnum.CADASTROS_EMPRESA_EXCLUIR,
    PermissaoUsuarioEnum.CADASTROS_EMPRESA_EXPORTARDADOS,
  ],

  sistemas: [
    PermissaoUsuarioEnum.CADASTROS_SISTEMAEMPRESA_CONSULTAR,
    PermissaoUsuarioEnum.CADASTROS_SISTEMAEMPRESA_CADASTRAR,
    PermissaoUsuarioEnum.CADASTROS_SISTEMAEMPRESA_EDITAR,
    PermissaoUsuarioEnum.CADASTROS_SISTEMAEMPRESA_EXCLUIR,
    PermissaoUsuarioEnum.CADASTROS_SISTEMAEMPRESA_EXPORTARDADOS,
  ],

  contatoDeEmpresas: [
    PermissaoUsuarioEnum.CADASTROS_CONTATOEMPRESA_CONSULTAR,
    PermissaoUsuarioEnum.CADASTROS_CONTATOEMPRESA_CONSULTAR,
    PermissaoUsuarioEnum.CADASTROS_CONTATOEMPRESA_CONSULTAR,
    PermissaoUsuarioEnum.CADASTROS_CONTATOEMPRESA_CONSULTAR,
    PermissaoUsuarioEnum.CADASTROS_CONTATOEMPRESA_CONSULTAR,
  ],

  tiposDeDePara: [
    PermissaoUsuarioEnum.CADASTROS_TIPODEPARA_CONSULTAR,
    PermissaoUsuarioEnum.CADASTROS_TIPODEPARA_CADASTRAR,
    PermissaoUsuarioEnum.CADASTROS_TIPODEPARA_EDITAR,
    PermissaoUsuarioEnum.CADASTROS_TIPODEPARA_EXCLUIR,
    PermissaoUsuarioEnum.CADASTROS_TIPODEPARA_EXPORTARDADOS,

  ],

  dePara: [
    PermissaoUsuarioEnum.CADASTROS_DEPARA_CONSULTAR,
    PermissaoUsuarioEnum.CADASTROS_DEPARA_CADASTRAR,
    PermissaoUsuarioEnum.CADASTROS_DEPARA_EDITAR,
    PermissaoUsuarioEnum.CADASTROS_DEPARA_EXCLUIR,
    PermissaoUsuarioEnum.CADASTROS_DEPARA_EXPORTARDADOS,
  ],

  contatoServico: [
    PermissaoUsuarioEnum.CADASTROS_ASSOC_CONT_NOTIF_SERV_CONSULTAR,
    PermissaoUsuarioEnum.CADASTROS_ASSOC_CONT_NOTIF_SERV_CADASTRAR,
    PermissaoUsuarioEnum.CADASTROS_ASSOC_CONT_NOTIF_SERV_EDITAR,
    PermissaoUsuarioEnum.CADASTROS_ASSOC_CONT_NOTIF_SERV_EXCLUIR,
    PermissaoUsuarioEnum.CADASTROS_ASSOC_CONT_NOTIF_SERV_EXPORTAR,
  ],

  usuario: [
    PermissaoUsuarioEnum.CADASTROS_ACESSO_USUARIO_CONSULTAR,
  ]



};
