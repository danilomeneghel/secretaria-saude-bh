package br.gov.pbh.prodabel.hubsmsa.util;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import javax.swing.text.MaskFormatter;
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
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.VisualizarEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;

// TODO: Auto-generated Javadoc
public class EmpresaUtil {

  /**
   * Instantiates a new empresa util.
   */
  private EmpresaUtil() {

  }

  /**
   * Gerar excel.
   *
   * @param dtos the dtos
   * @param dataGeracao the data geracao
   * @return the byte[]
   * @throws DAOException the DAO exception
   */
  public static byte[] gerarExcel(List<VisualizarEmpresaDTO> dtos, Date dataGeracao)
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
    int endRow = 5;

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
      tableTitle.setCellValue("Dados de Empresa");
    }
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A1_F1));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A2_F2));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A3_F3));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A4_F4));

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Nome Empresarial");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Nome Fantasia");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("CNPJ");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("CNES");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Ativo");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Site");

    try {
      for (VisualizarEmpresaDTO dto : dtos) {

        row = sheet.createRow(rownum++);
        cellnum = 0;
        if (null != dto.getCnpj() && !ExcelUtil.STRING_VAZIO.equals(dto.getCnpj())) {
          dto.setCnpj(adicionarMaskCNPJ(dto.getCnpj()));
        } else {
          dto.setCnpj(ExcelUtil.STRING_VAZIO);
        }
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
   * Gerar excel.
   *
   * @param dtos the dtos
   * @param dataGeracao the data geracao
   * @return the byte[]
   * @throws DAOException the DAO exception
   */
  public static byte[] gerarExcelHistorico(List<HistoricoEmpresaDTO> dtos, Date dataGeracao)
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
    int endRow = 12;

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
      tableTitle.setCellValue("Logs de Empresas");
    }
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A1_M1));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A2_M2));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A3_M3));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A4_M4));

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
    cell.setCellValue("ID da Empresa");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Nome Empresarial");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Nome Fantasia");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("CNPJ");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("CNES");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Ativo");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Site");

    try {
      for (HistoricoEmpresaDTO dto : dtos) {

        row = sheet.createRow(rownum++);
        cellnum = 0;
        if (null != dto.getCnpj() && !ExcelUtil.STRING_VAZIO.equals(dto.getCnpj())) {
          dto.setCnpj(adicionarMaskCNPJ(dto.getCnpj()));
        } else {
          dto.setCnpj(ExcelUtil.STRING_VAZIO);
        }
        defineValoresLinhaExcelHistorico(cellnum, row, dto, sheet, textStyle);
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
  private static void defineValoresLinhaExcelHistorico(int cellnum, Row row,
      HistoricoEmpresaDTO dto,
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
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getNomeUsuario());
    sheet.autoSizeColumn(2);

    // Email
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 30 * 300);
    cell.setCellValue(dto.getEmail());
    sheet.autoSizeColumn(2);

    // login
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 30 * 300);
    cell.setCellValue(dto.getLogin());
    sheet.autoSizeColumn(2);

    // Revisao
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 8 * 300);
    cell.setCellValue(dto.getRevisao());
    sheet.autoSizeColumn(2);
    
    // Tipo da Revisão
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 8 * 300);
    cell.setCellValue(dto.getTipoRevisao());
    sheet.autoSizeColumn(2);

    // ID do tipo
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 8 * 300);
    cell.setCellValue(dto.getId());
    sheet.autoSizeColumn(2);

    // Nome Empresarial
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 30 * 350);
    cell.setCellValue(dto.getNomeEmpresarial());
    sheet.autoSizeColumn(2);

    // Nome Fantasia
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 30 * 500);
    cell.setCellValue(dto.getNomeFantasia());
    sheet.autoSizeColumn(2);

    // CNPJ
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 15 * 300);
    if (null != dto.getCnpj() && !ExcelUtil.STRING_VAZIO.equals(dto.getCnpj())) {
      cell.setCellValue(dto.getCnpj());
    } else {
      cell.setCellValue(ExcelUtil.STRING_VAZIO);

    }
    sheet.autoSizeColumn(2);

    // Cnes
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 15 * 300);
    if (null != dto.getCnes() && !ExcelUtil.STRING_VAZIO.equals(dto.getCnes().toString())) {
      cell.setCellValue(dto.getCnes());
    } else {
      cell.setCellValue(ExcelUtil.STRING_VAZIO);
    }
    sheet.autoSizeColumn(3);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 5 * 300);
    cell.setCellValue(dto.getStatus());
    sheet.autoSizeColumn(5);

    // Site
    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna, 30 * 500);
    if (null != dto.getSite() && !ExcelUtil.STRING_VAZIO.equals(dto.getSite())) {
      cell.setCellValue(dto.getSite());
    } else {
      cell.setCellValue(ExcelUtil.STRING_VAZIO);
    }
    sheet.autoSizeColumn(6);
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
    tableHeaderStyle.setAlignment(HorizontalAlignment.LEFT);
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
  private static void defineValoresLinhaExcel(int cellnum, Row row, VisualizarEmpresaDTO dto,
      XSSFSheet sheet, CellStyle textStyle) {

    Cell cell = row.createCell(cellnum++);
    sheet.setColumnWidth(0, 35 * 300);
    row.createCell(0).setCellValue(dto.getNomeEmpresarial());
    cell.setCellStyle(textStyle);
    sheet.autoSizeColumn(1);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(1, 20 * 300);
    cell.setCellValue(dto.getNomeFantasia());
    sheet.autoSizeColumn(2);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(2, 15 * 350);
    if (null != dto.getCnpj() && !ExcelUtil.STRING_VAZIO.equals(dto.getCnpj())) {
      cell.setCellValue(dto.getCnpj());
    } else {
      cell.setCellValue(ExcelUtil.STRING_VAZIO);

    }
    sheet.autoSizeColumn(3);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(3, 10 * 300);
    if (null != dto.getCnes() && !ExcelUtil.STRING_VAZIO.equals(dto.getCnes().toString())) {
      cell.setCellValue(dto.getCnes());
    } else {
      cell.setCellValue(ExcelUtil.STRING_VAZIO);
    }

    sheet.autoSizeColumn(4);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(4, 10 * 300);
    cell.setCellValue(dto.getStatus().getName());
    sheet.autoSizeColumn(5);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(5, 25 * 300);
    if (null != dto.getSite() && !ExcelUtil.STRING_VAZIO.equals(dto.getSite())) {
      cell.setCellValue(dto.getSite());
    } else {
      cell.setCellValue(ExcelUtil.STRING_VAZIO);
    }
    sheet.autoSizeColumn(6);
  }


  /**
   * Gerar csv.
   *
   * @param dtos the dtos
   * @param date the date
   * @return the byte[]
   */
  public static byte[] gerarCsv(List<VisualizarEmpresaDTO> dtos) {
    ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(arrayOutputStream));

      CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';')
          .withHeader("Nome Empresarial", "Nome Fantasia", "CNPJ", "CNES", "Ativo", "Site"));

      for (VisualizarEmpresaDTO dto : dtos) {
        if (null != dto.getCnpj() && !ExcelUtil.STRING_VAZIO.equals(dto.getCnpj())) {
          dto.setCnpj(adicionarMaskCNPJ(dto.getCnpj()));
        } else {
          dto.setCnpj(ExcelUtil.STRING_VAZIO);
        }

        csvPrinter.printRecord(dto.getNomeEmpresarial(), dto.getNomeFantasia(), dto.getCnpj(),
            dto.getCnes(), dto.getStatus().getName(), dto.getSite());

      }
      csvPrinter.flush();
      writer.flush();
      csvPrinter.close();
      writer.close();
      arrayOutputStream.close();

    } catch (java.io.IOException e) {

    }

    return arrayOutputStream.toByteArray();
  }

  /**
   * Gerar historico csv.
   *
   * @param historicoEmpresas the historico empresas
   * @return the object
   */
  public static Object gerarHistoricoCsv(List<HistoricoEmpresaDTO> historicoEmpresas) {
    ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(arrayOutputStream));

      CSVPrinter csvPrinter = new CSVPrinter(writer,
          CSVFormat.DEFAULT.withDelimiter(';').withHeader("Data do Evento", "Usuário", "E-mail",
              "Login", "Revisão", "Tipo da Revisão", "ID da Empresa", "Nome Empresarial",
              "Nome Fantasia", "CNPJ", "CNES", "Ativo",
              "Site"));

      for (HistoricoEmpresaDTO historico : historicoEmpresas) {
        if (null != historico.getCnpj() && !ExcelUtil.STRING_VAZIO.equals(historico.getCnpj())) {
          historico.setCnpj(adicionarMaskCNPJ(historico.getCnpj()));
        } else {
          historico.setCnpj(ExcelUtil.STRING_VAZIO);
        }

        csvPrinter.printRecord(historico.getDataEvento(), historico.getNomeUsuario(),
            historico.getEmail(), historico.getLogin(), historico.getRevisao(),
            historico.getTipoRevisao(), historico.getId(),
            historico.getNomeEmpresarial(), historico.getNomeFantasia(), historico.getCnpj(),
            historico.getCnes(),
            historico.getStatus(), historico.getSite());

      }

      csvPrinter.flush();
      writer.flush();
      csvPrinter.close();
      writer.close();
      arrayOutputStream.close();

    } catch (java.io.IOException e) {

    }

    return arrayOutputStream.toByteArray();
  }

  /**
   * Adicionar mask CNPJ.
   *
   * @param value the value
   * @return the string
   */
  public static String adicionarMaskCNPJ(String value) {
    MaskFormatter maskFormatter;
    try {
      maskFormatter = new MaskFormatter("##.###.###/####-##");
      maskFormatter.setAllowsInvalid(true);
      maskFormatter.setValueContainsLiteralCharacters(false);
      if (ExcelUtil.STRING_VAZIO.equals(value)) {
        return ExcelUtil.STRING_VAZIO;
      } else {
        return maskFormatter.valueToString(value);
      }
    } catch (ParseException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }

  }

  /**
   * Verifica se é um cpf válido.
   *
   * @param CPF o cpf
   * @return true, se o cpf for válido
   */
  public static boolean validarCPF(String CPF) {
    // considera-se erro CPF's formados por uma sequencia de numeros iguais
    if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
        || CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
        || CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
        || CPF.equals("99999999999") || (CPF.length() != 11))
      return (false);

    char dig10, dig11;
    int sm, i, r, num, peso;

    // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
    try {
      // Calculo do 1o. Digito Verificador
      sm = 0;
      peso = 10;
      for (i = 0; i < 9; i++) {
        // converte o caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0
        // (48 eh a posicao de '0' na tabela ASCII)
        num = CPF.charAt(i) - 48;
        sm = sm + (num * peso);
        peso = peso - 1;
      }

      r = 11 - (sm % 11);
      if ((r == 10) || (r == 11))
        dig10 = '0';
      else
        dig10 = (char) (r + 48); // converte no respectivo caractere numerico

      // Calculo do 2o. Digito Verificador
      sm = 0;
      peso = 11;
      for (i = 0; i < 10; i++) {
        num = CPF.charAt(i) - 48;
        sm = sm + (num * peso);
        peso = peso - 1;
      }

      r = 11 - (sm % 11);
      if ((r == 10) || (r == 11))
        dig11 = '0';
      else
        dig11 = (char) (r + 48);

      // Verifica se os digitos calculados conferem com os digitos informados.
      if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
        return (true);
      else
        return (false);
    } catch (InputMismatchException erro) {
      return (false);
    }
  }

}
