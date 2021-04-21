package br.gov.pbh.prodabel.hubsmsa.util;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
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
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso.VisualizarLogAcessoUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;

// TODO: Auto-generated Javadoc
public class LogAcessoUsuarioUtil {

  /**
   * Instantiates a new tipo de para util.
   */
  private LogAcessoUsuarioUtil() {}

  /**
   * Gerar excel.
   *
   * @param dtos the dtos
   * @param dataGeracao the data geracao
   * @return the byte[]
   * @throws DAOException the DAO exception
   */
  public static byte[] gerarExcel(List<VisualizarLogAcessoUsuarioDTO> dtos, Date dataGeracao)
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
    int endRow = 3;

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
      tableTitle.setCellValue("Log de Acessos de Usuários no Sistema de Integração");
    }
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A1_D1));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A2_D2));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A3_D3));
    sheet.addMergedRegion(CellRangeAddress.valueOf(ExcelUtil.A4_D4));

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Data do Acesso");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Usuário");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("E-mail");

    cell = row.createCell(cellnum++);
    cell.setCellStyle(tableHeaderStyle);
    cell.setCellValue("Login");

    try {
      for (VisualizarLogAcessoUsuarioDTO dto : dtos) {

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
  private static void defineValoresLinhaExcel(int cellnum, Row row,
      VisualizarLogAcessoUsuarioDTO dto,
      XSSFSheet sheet, CellStyle textStyle) {

    int coluna = 0;
    Cell cell = row.createCell(cellnum++);
    sheet.setColumnWidth(coluna++, 15 * 300);
    row.createCell(0).setCellValue(dto.getDataAcesso());
    cell.setCellStyle(textStyle);
    sheet.autoSizeColumn(1);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getNome());
    sheet.autoSizeColumn(2);

    cell = row.createCell(cellnum++);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna++, 20 * 300);
    cell.setCellValue(dto.getEmail());
    sheet.autoSizeColumn(2);

    cell = row.createCell(cellnum);
    cell.setCellStyle(textStyle);
    sheet.setColumnWidth(coluna, 20 * 300);
    cell.setCellValue(dto.getLogin());
    sheet.autoSizeColumn(2);

  }

  /**
   * Gerar csv.
   *
   * @param dtos the dtos
   * @param date the date
   * @return the byte[]
   */
  public static byte[] gerarCsv(List<VisualizarLogAcessoUsuarioDTO> dtos) {
    ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(arrayOutputStream));

      CSVPrinter csvPrinter =
          new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';').withHeader("Data do Acesso",
              "Usuário", "E-mail", "Login"));

      for (VisualizarLogAcessoUsuarioDTO dto : dtos) {
        csvPrinter.printRecord(dto.getDataAcesso(), dto.getNome(), dto.getEmail(), dto.getLogin());
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

}
