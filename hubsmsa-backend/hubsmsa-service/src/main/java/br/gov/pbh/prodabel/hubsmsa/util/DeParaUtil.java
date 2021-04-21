package br.gov.pbh.prodabel.hubsmsa.util;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.io.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro.CadastrarCampoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro.CadastrarDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro.CadastrarSistemaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.PesquisaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;

// TODO: Auto-generated Javadoc
public final class DeParaUtil {

  private static final Boolean CADASTRADOS = Boolean.TRUE;
  private static final Boolean NAO_CADASTRADOS = Boolean.FALSE;

  private static Integer seis = 6;
  private static Integer oito = 8;
  private static Integer zero = 0;
  private static Integer um = 1;
  private static Integer dois = 2;

  /**
   * Instantiates a new de para util.
   */
  private DeParaUtil() {}

  /**
   * Atualiza a lista de campos De/Para primario com os dados enviados da tela.
   * 
   * @param dtoEdicao
   * @param dePara
   */
  public static void atualizarCamposPrimarios(final CadastrarDeParaDTO dtoEdicao,
      final DePara dePara) {

    Map<Long, List<DeParaPrimario>> camposDeParaPrimariosDoBanco =
        agruparCamposDeParaPrimarioDoBancoPorCodigo(dePara);

    Set<DeParaPrimario> camposPrimariosJaCadastradosAtualizados = new HashSet<>();
    Set<DeParaPrimario> camposPrimariosNovos = new HashSet<>();
    dtoEdicao.getSistemaPrimario().getCamposDePara().forEach(primario -> {
      if (primario.getId() != null) {
        // Atualiza as descricoes dos codigos ja cadastrados
        DeParaPrimario deParaPrimario = camposDeParaPrimariosDoBanco.get(primario.getId()).get(0);
        deParaPrimario.setCodigo(primario.getCodigo());
        deParaPrimario.setDePara(dePara);
        deParaPrimario.setDescricao(primario.getDescricao());

        camposPrimariosJaCadastradosAtualizados.add(deParaPrimario);
      } else {
        DeParaPrimario deParaPrimario = new DeParaPrimario();
        deParaPrimario.setCodigo(primario.getCodigo());
        deParaPrimario.setDePara(dePara);
        deParaPrimario.setDescricao(primario.getDescricao());

        camposPrimariosNovos.add(deParaPrimario);
      }
    });

    // Substitui a lista atualizada de campos
    dePara.setDeParaPrimario(camposPrimariosJaCadastradosAtualizados);
    // Adiciona os novos na lista
    dePara.getDeParaPrimario().addAll(camposPrimariosNovos);

  }

  /**
   * Atualiza a lista de campos De/Para secundario com os dados enviados da tela.
   * 
   * @param dtoEdicao
   * @param dePara
   */
  public static void atualizarCamposSecundarios(final CadastrarDeParaDTO dtoEdicao,
      final DePara dePara) {

    Map<Long, List<DeParaSecundario>> camposDeParaSecundariosDoBanco =
        agruparCamposDeParaSecundarioDoBancoPorCodigo(dePara);

    Set<DeParaSecundario> camposSecundariosJaCadastradosAtualizados = new HashSet<>();
    Set<DeParaSecundario> camposSecundariosNovos = new HashSet<>();
    dtoEdicao.getSistemaSecundario().getCamposDePara().forEach(secundario -> {
      if (secundario.getId() != null) {
        // Atualiza as descricoes dos codigos ja cadastrados
        DeParaSecundario deParaSecundario =
            camposDeParaSecundariosDoBanco.get(secundario.getId()).get(0);
        deParaSecundario.setCodigo(secundario.getCodigo());
        deParaSecundario.setDePara(dePara);
        deParaSecundario.setDescricao(secundario.getDescricao());

        camposSecundariosJaCadastradosAtualizados.add(deParaSecundario);
      } else {
        DeParaSecundario deParaSecundario = new DeParaSecundario();
        deParaSecundario.setCodigo(secundario.getCodigo());
        deParaSecundario.setDePara(dePara);
        deParaSecundario.setDescricao(secundario.getDescricao());

        camposSecundariosNovos.add(deParaSecundario);
      }
    });

    // Substitui a lista atualizada de campos
    dePara.setDeParaSecundario(camposSecundariosJaCadastradosAtualizados);
    // Adiciona os novos na lista
    dePara.getDeParaSecundario().addAll(camposSecundariosNovos);

  }

  /**
   * Agrupa os campos De/Para enviados da tela pelo codigo
   * 
   * @param dtoSistema
   * @return
   */
  private static Map<Long, List<CadastrarCampoDeParaDTO>> agruparCamposDeParaDaTelaPorCodigo(
      final CadastrarSistemaDeParaDTO dtoSistema) {
    return dtoSistema.getCamposDePara().stream()
        .collect(Collectors.groupingBy(CadastrarCampoDeParaDTO::getId));
  }

  /**
   * Agrupa os campos De/Para primarios ja cadastrados pelo codigo
   * 
   * @param dePara
   * @return
   */
  private static Map<Long, List<DeParaPrimario>> agruparCamposDeParaPrimarioDoBancoPorCodigo(
      final DePara dePara) {
    return dePara.getDeParaPrimario().stream()
        .collect(Collectors.groupingBy(DeParaPrimario::getId));
  }

  /**
   * Agrupa os campos De/Para secundarios ja cadastrados pelo codigo
   * 
   * @param dePara
   * @return
   */
  private static Map<Long, List<DeParaSecundario>> agruparCamposDeParaSecundarioDoBancoPorCodigo(
      final DePara dePara) {
    return dePara.getDeParaSecundario().stream()
        .collect(Collectors.groupingBy(DeParaSecundario::getId));
  }

  /**
   * 
   * @param status Status dos campos: TRUE - Cadastrados, FALSE - Novos
   * @param camposDeParaPrimariosDaTela - Campos recebidos da tela
   * @param camposDeParaPrimariosDoBanco - Campos ja cadastrados atualmente no BD
   * @return Lista de campos ja cadastrados ou a lista de campos novos
   */
  private static List<CadastrarCampoDeParaDTO> recuperarCamposDeParaPrimarios(final Boolean status,
      final Map<Long, List<CadastrarCampoDeParaDTO>> camposDeParaPrimariosDaTela,
      final Map<Long, List<DeParaPrimario>> camposDeParaPrimariosDoBanco) {

    return camposDeParaPrimariosDaTela.values().stream().flatMap(List::stream).collect(
        // O partitioningBy agrupa os valores em um Map onde a chave e TRUE/FALSE conforme resultado
        // da condicao no 'contains'
        Collectors.partitioningBy(
            deParaItem -> camposDeParaPrimariosDoBanco.keySet().contains(deParaItem.getId())))
        .get(status);
  }

  /**
   * 
   * @param status Status dos campos: TRUE - Cadastrados, FALSE - Novos
   * @param camposDeParaPrimariosDaTela - Campos recebidos da tela
   * @param camposDeParaPrimariosDoBanco - Campos ja cadastrados atualmente no BD
   * @return Lista de campos ja cadastrados ou a lista de campos novos
   */
  private static List<CadastrarCampoDeParaDTO> recuperarCamposDeParaSecundarios(
      final Boolean status,
      final Map<Long, List<CadastrarCampoDeParaDTO>> camposDeParaSecundariosDaTela,
      final Map<Long, List<DeParaSecundario>> camposDeParaSecundariosDoBanco) {

    return camposDeParaSecundariosDaTela.values().stream().flatMap(List::stream).collect(
        // O partitioningBy agrupa os valores em um Map onde a chave e TRUE/FALSE conforme resultado
        // da condicao no 'contains'
        Collectors.partitioningBy(
            deParaItem -> camposDeParaSecundariosDoBanco.keySet().contains(deParaItem.getId())))
        .get(status);
  }

  /**
   * Atualiza o De/Para com os dados da empresa e sistema primarios enviados da tela
   * 
   * @param dtoEdicao
   * @param dePara
   */
  public static void atualizarSistemaPrimario(final CadastrarDeParaDTO dtoEdicao,
      final DePara dePara) {
    Empresa empresaPrimaria = new Empresa();
    empresaPrimaria.setId(dtoEdicao.getSistemaPrimario().getIdEmpresa());

    Sistema sistemaPrimario = new Sistema();
    sistemaPrimario.setId(dtoEdicao.getSistemaPrimario().getIdSistema());
    sistemaPrimario.setEmpresa(empresaPrimaria);

    dePara.setSistemaPrimario(sistemaPrimario);
  }

  /**
   * Atualiza o De/Para com os dados da empresa e sistema secundarios enviados da tela
   * 
   * @param dtoEdicao
   * @param dePara
   */
  public static void atualizarSistemaSecundario(final CadastrarDeParaDTO dtoEdicao,
      final DePara dePara) {
    Empresa empresaSecundaria = new Empresa();
    empresaSecundaria.setId(dtoEdicao.getSistemaSecundario().getIdEmpresa());

    Sistema sistemaSecundario = new Sistema();
    sistemaSecundario.setId(dtoEdicao.getSistemaSecundario().getIdSistema());
    sistemaSecundario.setEmpresa(empresaSecundaria);

    dePara.setSistemaSecundario(sistemaSecundario);
  }

  /**
   * Gets the ids campos primarios removidos.
   *
   * @param dtoEdicao the dto edicao
   * @param dePara the de para
   * @return the ids campos primarios removidos
   */
  public static List<Long> getIdsCamposPrimariosRemovidos(final CadastrarDeParaDTO dtoEdicao,
      final DePara dePara) {
    List<String> codigosPrimariosDaTela = dtoEdicao.getSistemaPrimario().getCamposDePara().stream()
        .map(CadastrarCampoDeParaDTO::getCodigo).collect(Collectors.toList());

    return dePara.getDeParaPrimario().stream()
        .filter(deParaItem -> !codigosPrimariosDaTela.contains(deParaItem.getCodigo()))
        .map(DeParaPrimario::getId).collect(Collectors.toList());
  }

  /**
   * Gets the ids campos secundarios removidos.
   *
   * @param dtoEdicao the dto edicao
   * @param dePara the de para
   * @return the ids campos secundarios removidos
   */
  public static List<Long> getIdsCamposSecundariosRemovidos(final CadastrarDeParaDTO dtoEdicao,
      final DePara dePara) {
    List<String> codigosSecundariosDaTela = dtoEdicao.getSistemaSecundario().getCamposDePara()
        .stream().map(CadastrarCampoDeParaDTO::getCodigo).collect(Collectors.toList());

    return dePara.getDeParaSecundario().stream()
        .filter(deParaItem -> !codigosSecundariosDaTela.contains(deParaItem.toString()))
        .map(DeParaSecundario::getId).collect(Collectors.toList());

  }

  /**
   * Gerar csv.
   *
   * @param dtos the dtos
   * @param date the date
   * @return the byte[]
   */
  public static byte[] gerarCsv(List<PesquisaDeParaDTO> dtos) {
    ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(arrayOutputStream));

      CSVPrinter csvPrinter = new CSVPrinter(writer,
          CSVFormat.DEFAULT.withDelimiter(';').withHeader("Tipo de De/Para", "Nome", "Ativo",
              "Empresa/Sistema Primário", "Código Primário", "Descrição Sistema Primário",
              "Empresa/Sistema Secundário", "Código Secundário", "Descrição Sistema Secundário"));
      for (PesquisaDeParaDTO dto : dtos) {
        csvPrinter.printRecord(dto.getNomeTipoDePara(), dto.getNomeDePara(), dto.getStatus(),
            dto.getSistemaPrimario().replace(" - ", "/"), dto.getCodigosPrimarios(),
            dto.getDescricaoPrimario(), dto.getSistemaSecundario().replace(" - ", "/"),
            dto.getCodigosSecundarios(), dto.getDescricaoSecundario());
      }

      csvPrinter.flush();
      writer.flush();
      csvPrinter.close();
      writer.close();
      arrayOutputStream.close();

    } catch (java.io.IOException e) {
      throw new NegocioException("Erro ao gerar arquivo");
    }

    return arrayOutputStream.toByteArray();
  }

  /**
   * Gerar excel.
   *
   * @param dtos the dtos
   * @param dataGeracao the data geracao
   * @return the byte[]
   * @throws DAOException the DAO exception
   */
  public static byte[] gerarExcel(List<PesquisaDeParaDTO> dtos, Date dataGeracao)
      throws DAOException {

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Relatório");
    CreationHelper createHelper = workbook.getCreationHelper();

    int rownumTitle = 0;
    int rownumSubTitle = 1;
    int rownumTableTitle = 3;
    int rownumTableHeaders = 4;
    int rownumTableHeaders2 = 5;
    int rownumWhiteCell = 2;

    int cellnumTitle = 0;
    int cellnumSubTitle = 0;
    int cellnumTableTitle = 0;
    int cellnumTableHeaders = 0;
    int cellnumTableHeaders2 = 3;
    int cellnumWhite = 0;

    int startColumm = 0;
    int endColumm = 8;

    int rownum = 5;
    int cellnum = 0;

    Cell cell = null;
    Cell cellTitle = null;
    Cell subTitle = null;
    Cell tableTitle = null;
    Cell whiteCell = null;

    Row row = null;
    Row rowTitle = null;
    Row rowSubTitle = null;
    Row rowTableTitle = null;
    Row rowTableHeaders = null;
    Row rowTableHeaders2 = null;
    Row rowWhite = null;

    CellStyle headerStyle = workbook.createCellStyle();
    CellStyle subHeaderStyle = workbook.createCellStyle();
    CellStyle textStyle = workbook.createCellStyle();
    CellStyle dateStyle = workbook.createCellStyle();
    CellStyle tableHeaderStyle = workbook.createCellStyle();
    CellStyle tableTitleStyle = workbook.createCellStyle();
    CellStyle rightBorder = workbook.createCellStyle();
    CellStyle topRightBorder = workbook.createCellStyle();

    defineStyleSheet(workbook, headerStyle, subHeaderStyle, tableTitleStyle, tableHeaderStyle,
        textStyle, dateStyle, createHelper);
    row = sheet.createRow(rownum++);
    rowTitle = sheet.createRow(rownumTitle++);
    rowSubTitle = sheet.createRow(rownumSubTitle++);
    rowTableTitle = sheet.createRow(rownumTableTitle++);
    rowTableHeaders = sheet.createRow(rownumTableHeaders++);
    rowTableHeaders2 = sheet.createRow(rownumTableHeaders2++);
    rowWhite = sheet.createRow(rownumWhiteCell++);

    for (int i = startColumm; i <= endColumm; i++) {

      cellTitle = rowTitle.createCell(cellnumTitle++);
      cellTitle.setCellStyle(headerStyle);
      cellTitle.setCellValue("HUB SMSA - Sistema de Integração");

      subTitle = rowSubTitle.createCell(cellnumSubTitle++);
      subTitle.setCellStyle(subHeaderStyle);
      subTitle.setCellValue("Relatório gerado em: " + montarDataCabecalho(dataGeracao));

      whiteCell = rowWhite.createCell(cellnumWhite++);
      whiteCell.setCellStyle(subHeaderStyle);
      whiteCell.setCellValue(ExcelUtil.STRING_VAZIO);

      tableTitle = rowTableTitle.createCell(cellnumTableTitle++);
      tableTitle.setCellStyle(tableTitleStyle);
      tableTitle.setCellValue("Dados de De/Para");

    }

    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A1_I1));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A2_I2));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A3_I3));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A4_I4));

    tableTitle = rowTableTitle.createCell(oito);
    topRightBorder.setBorderTop(BorderStyle.THIN);
    topRightBorder.setTopBorderColor(IndexedColors.BLACK.getIndex());
    topRightBorder.setBorderRight(BorderStyle.THIN);
    topRightBorder.setRightBorderColor(IndexedColors.BLACK.getIndex());
    tableTitle.setCellStyle(topRightBorder);

    cell = rowTableHeaders.createCell(cellnumTableHeaders++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Tipo de De/Para");
    cell = rowTableHeaders2.createCell(zero);
    rightBorder.setBorderRight(BorderStyle.THIN);
    rightBorder.setRightBorderColor(IndexedColors.BLACK.getIndex());
    cell.setCellStyle(rightBorder);
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A5_A6));

    cell = rowTableHeaders.createCell(cellnumTableHeaders++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Nome");
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.B5_B6));
    cell = rowTableHeaders2.createCell(um);
    cell.setCellStyle(rightBorder);

    cell = rowTableHeaders.createCell(cellnumTableHeaders++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Ativo");
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.C5_C6));
    cell = rowTableHeaders2.createCell(dois);
    cell.setCellStyle(rightBorder);

    cell = rowTableHeaders.createCell(cellnumTableHeaders++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Dados Sistema Primário");
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.D5_F5));

    cell = rowTableHeaders.createCell(seis);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Dados Sistema Secundário");
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.G5_I5));
    cell = rowTableHeaders.createCell(oito);
    cell.setCellStyle(topRightBorder);

    cell = rowTableHeaders2.createCell(cellnumTableHeaders2++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Empresa/Sistema");

    cell = rowTableHeaders2.createCell(cellnumTableHeaders2++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Código");

    cell = rowTableHeaders2.createCell(cellnumTableHeaders2++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Descrição");

    cell = rowTableHeaders2.createCell(cellnumTableHeaders2++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Empresa/Sistema");

    cell = rowTableHeaders2.createCell(cellnumTableHeaders2++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Código");

    cell = rowTableHeaders2.createCell(cellnumTableHeaders2++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Descrição");

    try {
      for (PesquisaDeParaDTO dto : dtos) {

        row = sheet.createRow(rownum++);
        cellnum = 0;
        defineValoresLinhaExcel(cellnum, row, dto, sheet, textStyle);
      }
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      workbook.write(baos);
      baos.close();
      workbook.close();
      return baos.toByteArray();

    } catch (IOException | java.io.IOException | IllegalArgumentException e) {
      throw new DAOException();
    }
  }

  /**
   * Montar data cabecalho.
   *
   * @param date the date
   * @return the string
   */
  private static String montarDataCabecalho(Date date) {
    SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm");
    return new StringBuilder().append(formatadorData.format(date)).append(" às ")
        .append(formatadorHora.format(date)).toString();
  }

  /**
   * Define style sheet.
   *
   * @param workbook the workbook
   * @param headerStyle the header style
   * @param subHeaderStyle the sub header style
   * @param tableTitleStyle the table title style
   * @param tableHeaderStyle the table header style
   * @param textStyle the text style
   * @param dateStyle the date style
   * @param createHelper the create helper
   */
  private static void defineStyleSheet(XSSFWorkbook workbook, CellStyle headerStyle,
      CellStyle subHeaderStyle, CellStyle tableTitleStyle, CellStyle tableHeaderStyle,
      CellStyle textStyle, CellStyle dateStyle, CreationHelper createHelper) {

    Font fontTitle = workbook.createFont();
    fontTitle.setBold(true);
    fontTitle.setFontHeightInPoints((short) 12);

    Font fontTable = workbook.createFont();
    fontTable.setBold(true);

    IndexedColorMap colorTableTitleMap = workbook.getStylesSource().getIndexedColors();
    IndexedColorMap colorTableHeaderMap = workbook.getStylesSource().getIndexedColors();

    XSSFColor tableTitleColor =
        new XSSFColor(new java.awt.Color(142, 169, 219), colorTableTitleMap);
    XSSFColor tableHeaderColor =
        new XSSFColor(new java.awt.Color(180, 198, 231), colorTableHeaderMap);

    headerStyle.setAlignment(HorizontalAlignment.LEFT);
    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    headerStyle.setBorderBottom(BorderStyle.THIN);
    headerStyle.setBottomBorderColor(IndexedColors.WHITE.getIndex());

    headerStyle.setFont(fontTitle);

    subHeaderStyle.setAlignment(HorizontalAlignment.LEFT);
    subHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    subHeaderStyle.setBorderBottom(BorderStyle.THIN);
    subHeaderStyle.setBottomBorderColor(IndexedColors.WHITE.getIndex());

    ((XSSFCellStyle) tableTitleStyle).setFillForegroundColor(tableTitleColor);
    tableTitleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    ((XSSFCellStyle) tableTitleStyle).setFillBackgroundColor(tableTitleColor);
    tableTitleStyle.setAlignment(HorizontalAlignment.CENTER);
    tableTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    tableTitleStyle.setBorderTop(BorderStyle.THIN);
    tableTitleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
    tableTitleStyle.setBorderBottom(BorderStyle.THIN);
    tableTitleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    tableTitleStyle.setBorderLeft(BorderStyle.THIN);
    tableTitleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    tableTitleStyle.setBorderRight(BorderStyle.THIN);
    tableTitleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
    tableTitleStyle.setFont(fontTable);

    ((XSSFCellStyle) tableHeaderStyle).setFillForegroundColor(tableHeaderColor);
    tableHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    ((XSSFCellStyle) tableHeaderStyle).setFillBackgroundColor(tableHeaderColor);
    tableHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
    tableHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    tableHeaderStyle.setBorderTop(BorderStyle.THIN);
    tableHeaderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
    tableHeaderStyle.setBorderBottom(BorderStyle.THIN);
    tableHeaderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    tableHeaderStyle.setBorderLeft(BorderStyle.THIN);
    tableHeaderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    tableHeaderStyle.setBorderRight(BorderStyle.THIN);
    tableHeaderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
    tableHeaderStyle.setFont(fontTable);

    textStyle.setAlignment(HorizontalAlignment.LEFT);
    textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    textStyle.setBorderTop(BorderStyle.THIN);
    textStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
    textStyle.setBorderBottom(BorderStyle.THIN);
    textStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    textStyle.setBorderLeft(BorderStyle.THIN);
    textStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    textStyle.setBorderRight(BorderStyle.THIN);
    textStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

    dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    dateStyle.setAlignment(HorizontalAlignment.LEFT);
    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
    dateStyle.setBorderTop(BorderStyle.THIN);
    dateStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
    dateStyle.setBorderBottom(BorderStyle.THIN);
    dateStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    dateStyle.setBorderLeft(BorderStyle.THIN);
    dateStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    dateStyle.setBorderRight(BorderStyle.THIN);
    dateStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

  }

  /**
   * Define valores linha excel.
   *
   * @param cellnum the cellnum
   * @param row the row
   * @param dto the dto
   * @param sheet the sheet
   * @param cell the cell
   * @param textStyle the text style
   */
  private static void defineValoresLinhaExcel(int cellnum, Row row, PesquisaDeParaDTO dto,
      XSSFSheet sheet, CellStyle textStyle) {

    Cell cell = row.createCell(cellnum++);
    sheet.setColumnWidth(0, 20 * 300);
    row.createCell(0).setCellValue(dto.getNomeTipoDePara());
    cell.setCellStyle(textStyle);
    sheet.autoSizeColumn(1);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(1, 20 * 300);
    cell.setCellValue(dto.getNomeDePara());
    sheet.autoSizeColumn(2);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(2, 10 * 300);
    cell.setCellValue(dto.getStatus());
    sheet.autoSizeColumn(3);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(3, 30 * 300);
    cell.setCellValue(dto.getSistemaPrimario());
    sheet.autoSizeColumn(4);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(4, 20 * 300);
    cell.setCellValue(dto.getCodigosPrimarios());
    sheet.autoSizeColumn(5);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(5, 30 * 300);
    cell.setCellValue(dto.getDescricaoPrimario());
    sheet.autoSizeColumn(6);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(6, 30 * 300);
    cell.setCellValue(dto.getSistemaSecundario());
    sheet.autoSizeColumn(7);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(7, 20 * 300);
    cell.setCellValue(dto.getCodigosSecundarios());
    sheet.autoSizeColumn(8);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(8, 30 * 300);
    cell.setCellValue(dto.getDescricaoSecundario());
    sheet.autoSizeColumn(9);
  }

  /**
   * Gerar excel log.
   *
   * @param dtos the dtos
   * @param dataGeracao the data geracao
   * @return the byte[]
   * @throws DAOException the DAO exception
   */
  public static byte[] gerarExcelLog(List<HistoricoDeParaDTO> dtos, Date dataGeracao)
      throws DAOException {

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Relatório");
    CreationHelper createHelper = workbook.getCreationHelper();

    int rownumTitle = 0;
    int rownumSubTitle = 1;
    int rownumTableTitle = 3;
    int rownumWhiteCell = 2;

    int cellnumTitle = 0;
    int cellnumSubTitle = 0;
    int cellnumTableTitle = 0;
    int cellnumWhite = 0;

    int startRow = 0;
    int endRow = 9;

    int rownum = 4;
    int cellnum = 0;

    Cell cell = null;
    Cell cellTitle = null;
    Cell subTitle = null;
    Cell tableTitle = null;
    Cell whiteCell = null;

    Row row = null;
    Row rowTitle = null;
    Row rowSubTitle = null;
    Row rowTableTitle = null;
    Row rowWhite = null;

    CellStyle headerStyle = workbook.createCellStyle();
    CellStyle subHeaderStyle = workbook.createCellStyle();
    CellStyle textStyle = workbook.createCellStyle();
    CellStyle dateStyle = workbook.createCellStyle();
    CellStyle tableHeaderStyle = workbook.createCellStyle();
    CellStyle tableTitleStyle = workbook.createCellStyle();

    defineStyleSheet(workbook, headerStyle, subHeaderStyle, tableTitleStyle, tableHeaderStyle,
        textStyle, dateStyle, createHelper);
    row = sheet.createRow(rownum++);
    rowTitle = sheet.createRow(rownumTitle++);
    rowSubTitle = sheet.createRow(rownumSubTitle++);
    rowTableTitle = sheet.createRow(rownumTableTitle++);
    rowWhite = sheet.createRow(rownumWhiteCell++);

    for (int i = startRow; i <= endRow; i++) {

      cellTitle = rowTitle.createCell(cellnumTitle++);
      cellTitle.setCellStyle(headerStyle);
      cellTitle.setCellValue("HUB SMSA - Sistema de Integração");

      subTitle = rowSubTitle.createCell(cellnumSubTitle++);
      subTitle.setCellStyle(subHeaderStyle);
      subTitle.setCellValue("Relatório gerado em: " + TimeUtil.formatarDataCabecalho(dataGeracao));

      whiteCell = rowWhite.createCell(cellnumWhite++);
      whiteCell.setCellStyle(subHeaderStyle);
      whiteCell.setCellValue(ExcelUtil.STRING_VAZIO);

      tableTitle = rowTableTitle.createCell(cellnumTableTitle++);
      tableTitle.setCellStyle(tableTitleStyle);
      tableTitle.setCellValue("Logs de De/Para");
    }
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A1_P1));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A2_P2));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A3_P3));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A4_P4));

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Data do Evento");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Usuário");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("E-mail");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Login");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Revisão");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Tipo da Revisão");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("ID do De/Para");

    cell = row.createCell(cellnum++);
    cell.setCellValue("Tipo de De/Para");
    cell.setCellStyle(tableHeaderStyle);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("De/Para");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Código Sistema Primário");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Código Sistema Secundário");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Empresa Primária");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Empresa Secundária");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("De/Para Primário");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("De/Para Secundário");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Ativo");

    try {
      for (HistoricoDeParaDTO dto : dtos) {

        row = sheet.createRow(rownum++);
        cellnum = 0;

        defineValoresLinhaExcelLog(cellnum, row, dto, sheet, textStyle);
      }
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      workbook.write(baos);
      baos.close();
      workbook.close();
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
   * @param dto the dto
   * @param sheet the sheet
   * @param cell the cell
   * @param textStyle the text style
   */
  private static void defineValoresLinhaExcelLog(int cellnum, Row row, HistoricoDeParaDTO dto,
      XSSFSheet sheet, CellStyle textStyle) {

    int coluna = 0;
    // Data Evento
    Cell cell = row.createCell(cellnum++);
    sheet.setColumnWidth(coluna++, 15 * 300);
    row.createCell(0).setCellValue(dto.getDataEvento());
    cell.setCellStyle(textStyle);
    sheet.autoSizeColumn(1);

    // Nome Usuario
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 15 * 300);
    cell.setCellValue(dto.getNomeUsuario());
    sheet.autoSizeColumn(2);

    // Email
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getEmailUsuario());
    sheet.autoSizeColumn(5);

    // login
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getLogin());
    sheet.autoSizeColumn(2);

    // Revisao
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 6 * 300);
    cell.setCellValue(dto.getIdRevisao());
    sheet.autoSizeColumn(2);

    // Tipo da Revisão
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 8 * 300);
    cell.setCellValue(dto.getTipoRevisao());
    sheet.autoSizeColumn(2);

    // ID do DePara
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 10 * 300);
    cell.setCellValue(dto.getIdDePara());
    sheet.autoSizeColumn(5);

    // Tipo de DePara
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getTipoDePara());
    sheet.autoSizeColumn(5);

    // DePara
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getNomeDePara());
    sheet.autoSizeColumn(5);

    // Código Sistema Primário
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getCodigoSistemaPrimario());
    sheet.autoSizeColumn(5);

    // Código Sistema Secundário
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getCodigoSistemaSecundario());
    sheet.autoSizeColumn(5);

    // Empresa primaria
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getEmpresaPrimaria());
    sheet.autoSizeColumn(5);

    // Empresa primaria
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getEmpresaSecundaria());
    sheet.autoSizeColumn(5);

    // DePara Primário
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getDeParaPrimario());
    sheet.autoSizeColumn(5);

    // DePara Secundário
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getDeParaSecundario());
    sheet.autoSizeColumn(5);

    // Ativo
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getAtivo());
    sheet.autoSizeColumn(5);

  }

  /**
   * Gerar log csv.
   *
   * @param historicos the historicos
   * @return the object
   */
  public static Object gerarLogCsv(List<HistoricoDeParaDTO> historicos) {
    ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(arrayOutputStream));

      CSVPrinter csvPrinter = new CSVPrinter(writer,
          CSVFormat.DEFAULT.withDelimiter(';').withHeader("Data do Evento", "Usuário",
              "E-mail Usuario", "Login", "Revisão", "Tipo da Revisão", "ID do De/Para",
              "Tipo de De/Para", "De/Para", "Código Sistema Primário", "Código Sistema Secundário",
              "Empresa Primária", "Empresa Secundária", "De/Para Primário", "De/Para Secundário",
              "Ativo"));

      for (HistoricoDeParaDTO historico : historicos) {

        csvPrinter.printRecord(historico.getDataEvento(), historico.getNomeUsuario(),
            historico.getEmailUsuario(), historico.getLogin(), historico.getIdRevisao(),
            historico.getTipoRevisao(), historico.getIdDePara(), historico.getTipoDePara(),
            historico.getNomeDePara(), historico.getCodigoSistemaPrimario(),
            historico.getCodigoSistemaSecundario(), historico.getEmpresaPrimaria(),
            historico.getEmpresaSecundaria(), historico.getDeParaPrimario(),
            historico.getDeParaSecundario(), historico.getAtivo());

      }

      csvPrinter.flush();
      writer.flush();
      csvPrinter.close();
      writer.close();
      arrayOutputStream.close();

    } catch (java.io.IOException e) {
      throw new NegocioException("Erro ao gerar arquivo");
    }
    return arrayOutputStream.toByteArray();
  }

}
