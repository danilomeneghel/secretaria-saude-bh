package br.gov.pbh.prodabel.hubsmsa.security;

public final class ConstantesPermissoes {

	private ConstantesPermissoes() {

	}

	//Empresa
	public static final String CONSULTAR_EMPRESA = "CADASTROS_EMPRESA_CONSULTAR";
	public static final String EXCLUIR_EMPRESA = "CADASTROS_EMPRESA_EXCLUIR";
	public static final String CADASTRAR_EMPRESA = "CADASTROS_EMPRESA_CADASTRAR";
	public static final String EDITAR_EMPRESA = "CADASTROS_EMPRESA_EDITAR";
	public static final String EXPORTAR_EMPRESA = "CADASTROS_EMPRESA_EXPORTARDADOS";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_TYPE_DOCUMENT_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String ATTACHMENT_FILENAME_EMPRESAS = "attachment; filename=Empresas";
	public static final String ATTACHMENT_EXTENSION_XLSX = ".xlsx";
	public static final String ATTACHMENT_EXTENSION_CSV = ".csv";
	public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	
	//De Para
	public static final String EDITAR_DE_PARA = "CADASTROS_DEPARA_EDITAR";
	public static final String EXCLUIR_DE_PARA = "CADASTROS_DEPARA_EXCLUIR";
	public static final String CADASTRAR_DE_PARA = "CADASTROS_DEPARA_CADASTRAR";
	public static final String EXPORTAR_DE_PARA = "CADASTROS_DEPARA_EXPORTARDADOS";
	public static final String CONSULTAR_DE_PARA = "CADASTROS_DEPARA_CONSULTAR";
	
	// Sistema de Empresa
	public static final String CONSULTAR_SISTEMA = "CADASTROS_SISTEMAEMPRESA_CONSULTAR";
	public static final String EXPORTAR_SISTEMA = "CADASTROS_SISTEMAEMPRESA_EXPORTARDADOS";
	public static final String CADASTRAR_SISTEMA = "CADASTROS_SISTEMAEMPRESA_CADASTRAR";
	public static final String EXCLUIR_SISTEMA = "CADASTROS_SISTEMAEMPRESA_EXCLUIR";
	public static final String EDITAR_SISTEMA = "CADASTROS_SISTEMAEMPRESA_EDITAR";
	
	// Contato de Empresa
	public static final String CADASTRAR_CONTATO_EMPRESA = "CADASTROS_CONTATOEMPRESA_CADASTRAR";
	public static final String EXPORTAR_CONTATO_EMPRESA = "CADASTROS_CONTATOEMPRESA_EXPORTARDADOS";
	public static final String EXCLUIR_CONTATO_EMPRESA = "CADASTROS_CONTATOEMPRESA_EXCLUIR";
	public static final String EDITAR_CONTATO_EMPRESA = "CADASTROS_CONTATOEMPRESA_EDITAR";
	public static final String CONSULTAR_CONTATO_EMPRESA = "CADASTROS_CONTATOEMPRESA_CONSULTAR";

	// Tipo De Para
	public static final String CADASTRAR_TIPO_DE_PARA = "CADASTROS_TIPODEPARA_CADASTRAR";
	public static final String CONSULTAR_TIPO_DE_PARA = "CADASTROS_TIPODEPARA_CONSULTAR";
	public static final String EDITAR_TIPO_DE_PARA = "CADASTROS_TIPODEPARA_EDITAR";
	public static final String EXPORTAR_TIPO_DE_PARA = "CADASTROS_TIPODEPARA_EXPORTARDADOS";
	public static final String EXCLUIR_TIPO_DE_PARA = "CADASTROS_TIPODEPARA_EXCLUIR";

	// Exemplo
	public static final String PESQUISAR_EXEMPLO = "CADASTRO_PESQUISAREXEMPLO";
	public static final String PESQUISAR_MANTER_EXEMPLO = "CADASTRO_MANTEREXEMPLO";
}