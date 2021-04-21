package br.gov.pbh.prodabel.hubsmsa.constants;

import java.time.format.DateTimeFormatter;

public final class ConstanteUtil {

  /**
   * Instantiates a new constante util.
   */
  private ConstanteUtil() {}

  public static final String REGEX_REMOCAO_ACENTOS = "[^\\p{ASCII}]";
  public static final String UNACCENT = "public.unaccent";
  public static final String PORCENTAGEM = "%";
  public static final String MESSAGE = "Campo deve ter, no máximo, {max} caracteres.";
  public static final String MESSAGE_MIN = "Campo deve ter, no minimo, {min} caracteres.";

  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

  public static final String CONTENT_TYPE = "Content-Type";
  public static final String CONTENT_TYPE_DOCUMENT_XLSX =
      "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  public static final String CONTENT_TYPE_DOCUMENT_PDF = "application/pdf";
  public static final String CONTENT_DISPOSITION = "Content-Disposition";

  public static final String ATTACHMENT_FILENAME_EXEMPLO = "attachment; filename=Exemplo";

  public static final String ATTACHMENT_EXTENSION_PDF = ".pdf";
  public static final String ATTACHMENT_EXTENSION_XLSX = ".xlsx";
  public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

  public static final int K_BYTE = 8192;
  public static final char COMMA_SEPARATOR = ';';

  public static final String DOUBLE_LINE = "<br><br>";
  public static final String SINGLE_LINE = "<br>";

  public static final String UTF_8 = "UTF-8";
  public static final String HIFEN = "-";
  public static final int TAMANHO_MINIMO_LINHA = 5;
  public static final String COMMA = ",";
  public static final String REGEX_ONLY_NUMBERS = "[^0-9]";

  public static final String OPEN_PARENTHESIS = "(";
  public static final String CLOSE_PARENTHESIS = ")";
  public static final int INDEX_NOT_FOUND = -1;
  public static final int INCREMENTO = 1;

  public static final String HTTP_SITE_PREFIX = "http://";
  public static final String HTTPS_SITE_PREFIX = "https://";

  public static final String ATTACHMENT_FILENAME_EMPRESAS =
      "attachment; filename=HUB-SMSA_Empresas_";
  public static final String ATTACHMENT_FILENAME_LOG_EMPRESAS =
      "attachment; filename=HUB-SMSA_LogsEmpresas_";
  public static final String ATTACHMENT_FILENAME_LOG_TIPODEPARA =
      "attachment; filename=HUB-SMSA_LogsTiposDe-Para_";
  public static final String ATTACHMENT_FILENAME_LOG_CONTATO_EMPRESAS =
      "attachment; filename=HUB-SMSA_LogsContatosEmpresas_";
  public static final String ATTACHMENT_FILENAME_CONTATO_EMPRESAS =
      "attachment; filename=HUB-SMSA_ContatoEmpresas_";
  public static final String ATTACHMENT_FILENAME_SISTEMAS =
      "attachment; filename=HUB-SMSA_SistemasEmpresas_";
  public static final String ATTACHMENT_FILENAME_DE_PARA = "attachment; filename=HUB-SMSA_De-Para_";
  public static final String ATTACHMENT_FILENAME_LOG_DE_PARA =
      "attachment; filename=HUB-SMSA_LogsDe-Para_";

  public static final String ATTACHMENT_FILENAME_TIPOSDEPARA =
      "attachment; filename=HUB-SMSA_TiposDePara_";
  public static final String ATTACHMENT_FILENAME_LOG_ACESSO_USUARIO =
      "attachment; filename=HUB-SMSA_AcessosUsuarios_";
  public static final String CONTENT_TYPE_DOCUMENT_CSV = "text/csv";
  public static final String ATTACHMENT_EXTENSION_CSV = ".csv";
  public static final String CONTENT_TYPE_UTF_8 = "text/html; charset=utf-8";
  public static final String SENDER_NO_REPLY = "noreply_hubsmsa@pbh.gov.br";
  public static final String JNDI_SMTP_MAIL = "java:/mail/hubsmsa";

  /**
   * Parametros de consultas
   */
  public static final String PARAM_ID = "id";
  public static final String PARAM_USERNAME = "username";
  public static final String PARAM_NOME = "nome";
  public static final String PARAM_EMAIL = "email";

  /**
   * Paths de variaveis de entidades
   */
  public static final String PATH_EMPRESA_ID = "empresa.id";
  public static final String PATH_USUARIO_ID = "usuario.id";

}
