package br.gov.pbh.prodabel.hubsmsa.util;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import br.gov.pbh.prodabel.hubsmsa.dto.alteracao.FiltroPesquisaAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.alteracao.VisualizarAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.AssuntoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoAlteracaoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;

// TODO: Auto-generated Javadoc
public final class AlteracaoUtil {

  /**
   * Instantiates a new alteracao util.
   */
  private AlteracaoUtil() {}

  private static final String HEAD =
      "<!DOCTYPE html><html><head><style>table {border-collapse:separate;border-spacing:0 15px;margin-top: -40px}th{color:black;font-size: 16px;column-width: 200px;}h3{text-align: center;border: 5px solid white;font-size: 23px;margin-top:-50px;} tr{text-align:center;border: 5px solid rgb(14, 13, 13);}td{width:calc(100%/5);font-size: 13px;column-width: 150px;}th,td{padding: 5px 10px;}</style><meta charset=\"UTF-8\"></head>";
  private static final String FOOTER = "</table></div></div></body></html>";

  /**
   * Define style sheet.
   *
   * @param headerStyle the header style
   * @param textStyle the text style
   * @param dateStyle the date style
   * @param createHelper the create helper
   */
  public static void defineStyleSheet(CellStyle headerStyle, CellStyle textStyle,
      CellStyle dateStyle, CreationHelper createHelper) {
    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    headerStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    headerStyle.setAlignment(HorizontalAlignment.LEFT);
    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    textStyle.setAlignment(HorizontalAlignment.LEFT);
    textStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    dateStyle.setAlignment(HorizontalAlignment.LEFT);
    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

  }

  /**
   * Define valores linha excel.
   *
   * @param cellnum the cellnum
   * @param row the row
   * @param alteracao the alteracao
   * @param sheet the sheet
   * @param textStyle the text style
   */
  public static void defineValoresLinhaExcel(int cellnum, Row row, VisualizarAlteracaoDTO alteracao,
      XSSFSheet sheet, CellStyle textStyle) {

    Cell cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(1, alteracao.getDataAlteracao().toString().length() * 300);
    row.createCell(0).setCellValue(toDate(alteracao.getDataAlteracao()));

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(2, alteracao.getAssunto().getName().length() * 300);
    cell.setCellValue(alteracao.getAssunto().getName());

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(3, alteracao.getDescricao().length() * 300);
    row.createCell(2).setCellValue(alteracao.getDescricao());

  }

  /**
   * Gerar pdf.
   *
   * @param alteracoes the alteracoes
   * @param filtroDto the filtro dto
   * @return the byte[]
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public static byte[] gerarPdf(List<VisualizarAlteracaoDTO> alteracoes,
      FiltroPesquisaAlteracaoDTO filtroDto) throws DAOException {

    final String BODY = definirBody(filtroDto);
    final String TABLE_HEADER = definirCabecalhoTabela(alteracoes);
    final String TABLE_VARIABLES = definirVariavieisPdf();

    StringBuilder html = new StringBuilder();

    html.append(HEAD).append(BODY).append(TABLE_HEADER);

    alteracoes.stream().forEach(alteracao -> {
      String htmlTableVariables = TABLE_VARIABLES;

      htmlTableVariables = htmlTableVariables.replace("::DATA",
          StringEscapeUtils.escapeHtml4(toDate(alteracao.getDataAlteracao())));
      htmlTableVariables = htmlTableVariables.replace("::ASSUNTO",
          StringEscapeUtils.escapeHtml4(alteracao.getAssunto().getName()));
      htmlTableVariables = htmlTableVariables.replace("::DESCRICAO",
          StringEscapeUtils.escapeHtml4(alteracao.getDescricao()));

      html.append(htmlTableVariables);
    });

    html.append(FOOTER);
    try {

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PdfDocument doc = new PdfDocument(new PdfWriter(baos));
      doc.setDefaultPageSize(PageSize.A4.rotate());

      HtmlConverter.convertToPdf(new ByteArrayInputStream(html.toString().getBytes()), doc);

      return baos.toByteArray();

    } catch (Exception e) {

      throw new DAOException();

    }
  }

  /**
   * Gerar excel.
   *
   * @param alteracoes the alteracoes
   * @return the byte[]
   * @throws DAOException the DAO exception
   */
  public static byte[] gerarExcel(List<VisualizarAlteracaoDTO> alteracoes) throws DAOException {

    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
      XSSFSheet sheet = workbook.createSheet("Relatório");
      CreationHelper createHelper = workbook.getCreationHelper();

      int rownum = 0;
      int cellnum = 0;
      Cell cell = null;
      Row row = null;

      CellStyle headerStyle = workbook.createCellStyle();
      CellStyle textStyle = workbook.createCellStyle();
      CellStyle dateStyle = workbook.createCellStyle();

      defineStyleSheet(headerStyle, textStyle, dateStyle, createHelper);
      row = sheet.createRow(rownum++);

      cell = row.createCell(cellnum++);
      cell.setCellStyle(headerStyle);
      String dadoAnterior = "Data de Alteração";
      cell.setCellValue(dadoAnterior);

      cell = row.createCell(cellnum++);
      cell.setCellStyle(headerStyle);
      cell.setCellValue("Assunto");

      cell = row.createCell(cellnum++);
      cell.setCellStyle(headerStyle);
      cell.setCellValue(alteracoes.get(0).getAssunto().getName());

      for (VisualizarAlteracaoDTO alteracao : alteracoes) {

        row = sheet.createRow(rownum++);
        cellnum = 0;

        defineValoresLinhaExcel(cellnum, row, alteracao, sheet, textStyle);

      }
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      workbook.write(baos);
      baos.close();
      return baos.toByteArray();

    } catch (IOException | java.io.IOException | IllegalArgumentException e) {
      throw new DAOException();
    }

  }

  /**
   * To date.
   *
   * @param data the data
   * @return the string
   */
  private static String toDate(Date data) {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    return data == null ? "" : df.format(data);
  }

  /**
   * Gerar excel detalhado.
   *
   * @param alteracoes the alteracoes
   * @param assuntoEnumerated the assunto enumerated
   * @param entidadeNome the entidade nome
   * @return the byte[]
   * @throws DAOException the DAO exception
   */
  public static byte[] gerarExcelDetalhado(List<HistoricoAlteracaoDTO> alteracoes,
      String assuntoEnumerated, String entidadeNome) throws DAOException {
    try (XSSFWorkbook workbook = new XSSFWorkbook()) {

      XSSFSheet sheet = workbook.createSheet("Relatório");
      CreationHelper createHelper = workbook.getCreationHelper();

      int rownum = 0;
      int cellnum = 0;
      Cell cell = null;
      Row row = null;
      Row rowAbove = null;
      CellStyle headerStyle = workbook.createCellStyle();
      CellStyle textStyle = workbook.createCellStyle();
      CellStyle dateStyle = workbook.createCellStyle();

      defineStyleSheet(headerStyle, textStyle, dateStyle, createHelper);
      rowAbove = sheet.createRow(rownum++);
      cell = rowAbove.createCell(0);
      cell.setCellStyle(textStyle);
      String assunto = "Assunto: " + AssuntoEnum.valueOf(assuntoEnumerated).getName();
      cell.setCellValue(assunto);

      cell = rowAbove.createCell(1);
      cell.setCellStyle(textStyle);
      cell.setCellValue("Nome: " + entidadeNome);

      row = sheet.createRow(rownum++);

      cell = row.createCell(cellnum++);
      cell.setCellStyle(headerStyle);
      String dadoAnterior = "Dado Anterior";
      cell.setCellValue(dadoAnterior);

      cell = row.createCell(cellnum++);
      cell.setCellStyle(headerStyle);
      cell.setCellValue("Dado Atual");

      cell = row.createCell(cellnum++);
      cell.setCellStyle(headerStyle);
      cell.setCellValue("Data Alteração do Dado Atual");

      cell = row.createCell(cellnum++);
      cell.setCellStyle(headerStyle);
      cell.setCellValue("Usuário do Sistema Cliente Responsável pela Alteração");

      for (HistoricoAlteracaoDTO alteracao : alteracoes) {

        row = sheet.createRow(rownum++);
        cellnum = 0;

        defineValoresLinhaExcel(cellnum, row, alteracao, sheet, textStyle);

      }
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      workbook.write(baos);
      baos.close();
      return baos.toByteArray();

    } catch (IOException | java.io.IOException | IllegalArgumentException e) {
      throw new DAOException();
    }
  }

  /**
   * Define valores linha excel.
   *
   * @param cellnum the cellnum
   * @param row the row
   * @param alteracao the alteracao
   * @param sheet the sheet
   * @param cell the cell
   * @param textStyle the text style
   */
  private static void defineValoresLinhaExcel(int cellnum, Row row, HistoricoAlteracaoDTO alteracao,
      XSSFSheet sheet, CellStyle textStyle) {

    Cell cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(1, alteracao.getDadoAnterior().length() * 300);
    row.createCell(0).setCellValue(alteracao.getDadoAnterior());

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(2, alteracao.getDadoAtual().length() * 300);
    cell.setCellValue(alteracao.getDadoAtual());

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(3, 12 * 300);
    row.createCell(2).setCellValue(alteracao.getDataAlteracaoDadoAtual());

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(4, alteracao.getUsuarioResponsavelAlteracao().length() * 300);
    row.createCell(3).setCellValue(alteracao.getUsuarioResponsavelAlteracao());

  }

  /**
   * Gerar pdf detalhado.
   *
   * @param alteracoes the alteracoes
   * @param assuntoEnumerated the assunto enumerated
   * @param entidadeNome the entidade nome
   * @return the byte[]
   * @throws DAOException the DAO exception
   */
  public static byte[] gerarPdfDetalhado(List<HistoricoAlteracaoDTO> alteracoes,
      String assuntoEnumerated, String entidadeNome) throws DAOException {

    final String BODY = definirBodyDetalhado(assuntoEnumerated, entidadeNome);
    final String TABLE_HEADER = definirCabecalhoTabelaDetalhado();
    final String TABLE_VARIABLES = definirVariavieisPdfDetalhado();

    StringBuilder html = new StringBuilder();

    html.append(HEAD).append(BODY).append(TABLE_HEADER);

    alteracoes.stream().forEach(alteracao -> {

      String htmlTableVariables = TABLE_VARIABLES;

      htmlTableVariables = htmlTableVariables.replace("::DADO_ANTERIOR",
          StringEscapeUtils.escapeHtml4(alteracao.getDadoAnterior()));
      htmlTableVariables = htmlTableVariables.replace("::DADO_ATUAL",
          StringEscapeUtils.escapeHtml4(alteracao.getDadoAtual()));
      htmlTableVariables = htmlTableVariables.replace("::DATA_ALTERACAO",
          StringEscapeUtils.escapeHtml4(alteracao.getDataAlteracaoDadoAtual()));
      htmlTableVariables = htmlTableVariables.replace("::RESPONSAVEL",
          StringEscapeUtils.escapeHtml4(alteracao.getUsuarioResponsavelAlteracao()));

      html.append(htmlTableVariables);
    });

    html.append(FOOTER);
    try {

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PdfDocument doc = new PdfDocument(new PdfWriter(baos));
      doc.setDefaultPageSize(PageSize.A4.rotate());

      HtmlConverter.convertToPdf(new ByteArrayInputStream(html.toString().getBytes()), doc);

      return baos.toByteArray();

    } catch (Exception e) {

      throw new DAOException();

    }
  }

  /**
   * To date.
   *
   * @param data the data
   * @return the string
   */
  private static String toDate(LocalDateTime data) {
    return data == null ? "" : DateTimeFormatter.ofPattern("dd/MM/yyyy").format(data);
  }

  /**
   * Definir variavieis pdf detalhado.
   *
   * @return the string
   */
  private static String definirVariavieisPdfDetalhado() {
    return "<tr><td style=text-align:center;> ::DADO_ANTERIOR </td><td style=text-align:center;> ::DADO_ATUAL </td><td style=text-align:center;> ::DATA_ALTERACAO </td>"
        + " <td style=text-align:center;> ::RESPONSAVEL </td>" + " </tr>";
  }

  /**
   * Definir cabecalho tabela detalhado.
   *
   * @return the string
   */
  private static String definirCabecalhoTabelaDetalhado() {
    StringBuilder sb = new StringBuilder();
    sb.append("<tr>");
    sb.append(
        "<th style=text-align:center;>" + StringEscapeUtils.escapeHtml4("Dado Anterior") + "</th>");
    sb.append(
        "<th style=text-align:center;>" + StringEscapeUtils.escapeHtml4("Dado Atual") + "</th>");
    sb.append("<th style=text-align:center;>"
        + StringEscapeUtils.escapeHtml4("Data Alteração do Dado Atual") + "</th>");
    sb.append("<th style=text-align:center;>"
        + StringEscapeUtils.escapeHtml4("Usuário Responsável pela Alteração") + "</th></tr>");

    return sb.toString();
  }

  /**
   * Definir cabecalho tabela.
   *
   * @param alteracoes the alteracoes
   * @return the string
   */
  private static String definirCabecalhoTabela(List<VisualizarAlteracaoDTO> alteracoes) {
    StringBuilder sb = new StringBuilder();
    sb.append("<tr>");
    sb.append("<th style=text-align:center;>" + StringEscapeUtils.escapeHtml4("Data Alteração")
        + "</th><th style=text-align:center;>");
    sb.append(" " + StringEscapeUtils.escapeHtml4("Assunto") + "</th>");
    sb.append(" <th style=text-align:center;>"
        + StringEscapeUtils.escapeHtml4(alteracoes.get(0).getAssunto().getName()) + "</th></tr>");
    return sb.toString();
  }

  /**
   * Definir variavieis pdf.
   *
   * @return the string
   */
  private static String definirVariavieisPdf() {
    return "<tr><td style=text-align:center;> ::DATA </td><td style=text-align:center;> ::ASSUNTO </td><td style=text-align:center;> ::DESCRICAO </td>"
        + " </tr>";
  }

  /**
   * Definir body.
   *
   * @param filtroDto the filtro dto
   * @return the string
   */
  private static String definirBody(FiltroPesquisaAlteracaoDTO filtroDto) {

    String htmlBody =
        "<body><div style=display:flex;justify-content:center;><div style=width:auto;padding:2rem;margin:2rem 0;><header style=margin-bottom:50px><h3>"
            + StringEscapeUtils.escapeHtml4("Relatório de Alterações") + "</h3> <br> <h3>"
            + StringEscapeUtils.escapeHtml4("Período: ::DATA_INICIAL a ::DATA_FINAL")
            + "</h3>  </header><table cellpadding=0 cellspacing=0 style=width:100% >";
    htmlBody = htmlBody.replace("::DATA_INICIAL", toDate(filtroDto.getDataInicial()));
    htmlBody = htmlBody.replace("::DATA_FINAL", toDate(filtroDto.getDataFinal()));
    return htmlBody;
  }

  /**
   * Definir body detalhado.
   *
   * @param assuntoEnumerated the assunto enumerated
   * @param entidadeNome the entidade nome
   * @return the string
   */
  private static String definirBodyDetalhado(String assuntoEnumerated, String entidadeNome) {

    String htmlBody =
        "<body><div style=display:flex;justify-content:center;><div style=width:auto;padding:2rem;margin:2rem 0;><header style=margin-bottom:50px><h3>"
            + StringEscapeUtils.escapeHtml4("Histórico de Alterações") + "</h3> <br> <h3>"
            + StringEscapeUtils.escapeHtml4("Assunto: ::ASSUNTO")
            + " <span style=margin-left:30px></span> "
            + StringEscapeUtils.escapeHtml4("Nome: ::NOME")
            + "</h3> </div></header><table cellpadding=0 cellspacing=0 style=width:100% >";

    htmlBody = htmlBody.replace("::ASSUNTO",
        StringEscapeUtils.escapeHtml4(AssuntoEnum.valueOf(assuntoEnumerated).getName()));
    htmlBody = htmlBody.replace("::NOME", StringEscapeUtils.escapeHtml4(entidadeNome));

    return htmlBody;
  }

  /**
   * Ordenar lista alteracao.
   *
   * @param alteracoes the alteracoes
   * @param colunaNome the coluna nome
   * @param ordem the ordem
   */
  public static void ordenarListaAlteracao(List<VisualizarAlteracaoDTO> alteracoes,
      String colunaNome, String ordem) {
    if (ColunaOrdenacaoAlteracaoEnum.ASSUNTO
        .equals(ColunaOrdenacaoAlteracaoEnum.valueOf(colunaNome.toUpperCase()))
        || ColunaOrdenacaoAlteracaoEnum.DESCRICAO
            .equals(ColunaOrdenacaoAlteracaoEnum.valueOf(colunaNome.toUpperCase()))) {
      if (TipoOrdenacaoEnum.ASC.equals(TipoOrdenacaoEnum.valueOf(ordem.toUpperCase()))) {
        alteracoes.sort(Comparator.comparing(VisualizarAlteracaoDTO::getDescricao));
      } else {
        alteracoes.sort(Comparator.comparing(VisualizarAlteracaoDTO::getDescricao).reversed());
      }
    } else {
      if (TipoOrdenacaoEnum.ASC.equals(TipoOrdenacaoEnum.valueOf(ordem.toUpperCase()))) {
        alteracoes.sort(Comparator.comparing(VisualizarAlteracaoDTO::getDataAlteracao));
      } else {
        alteracoes.sort(Comparator.comparing(VisualizarAlteracaoDTO::getDataAlteracao).reversed());
      }
    }
  }
}
