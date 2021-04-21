package br.gov.pbh.prodabel.hubsmsa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
public class TimeUtil {

  private static final Logger LOGGER = Logger.getLogger(TimeUtil.class.getName());

  private static final String DD_MM_YYYY = "dd/MM/yyyy";

  public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
  private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

  /**
   * Instantiates a new time util.
   */
  TimeUtil() {
    throw new IllegalStateException("Classe utilitária.");
  }

  private static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
  private static DateTimeFormatter formatterDateTime =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

  private static DateTimeFormatter formatterDateTimeBd =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(DD_MM_YYYY);

  /**
   * To local time.
   *
   * @param time the time
   * @return the local time
   */
  public static LocalTime toLocalTime(String time) {
    return LocalTime.parse(time, formatterTime);
  }

  /**
   * To local date time.
   *
   * @param time the time
   * @return the local date time
   */
  public static LocalDateTime toLocalDateTime(String time) {
    return LocalDateTime.parse(time, formatterDateTime);
  }

  /**
   * To local date.
   *
   * @param time the time
   * @return the local date
   */
  public static LocalDate toLocalDate(String time) {
    return LocalDate.parse(time, formatterDate);
  }

  /**
   * To date.
   *
   * @param date the date
   * @param formato the formato
   * @return the date
   */
  public static Date toDate(String date) {

    SimpleDateFormat formater = new SimpleDateFormat(DD_MM_YYYY);
    try {
      return formater.parse(date);
    } catch (ParseException e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }
    return null;
  }


  /**
   * To date time.
   *
   * @param date the date
   * @return the date
   */
  public static Date toDateTime(String date) {

    SimpleDateFormat formater = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
    try {
      return formater.parse(date);
    } catch (ParseException e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }
    return null;
  }

  /**
   * Formatar data.
   *
   * @param data the data
   * @return the string
   */
  public static String formatarData(LocalDate data) {
    return data.format(formatterDate);
  }

  /**
   * Formatar data.
   *
   * @param data the data
   * @param formato the formato
   * @return the string
   */
  public static String formatarData(Date data, String formato) {
    SimpleDateFormat formater = new SimpleDateFormat(formato);
    return formater.format(data);
  }

  /**
   * Formatar hora.
   *
   * @param data the data
   * @return the string
   */
  public static String formatarHora(LocalTime data) {
    return data.format(formatterTime);
  }

  /**
   * Formatar hora.
   *
   * @param data the data
   * @return the string
   */
  public static String formatarHora(LocalDateTime data) {
    return data.format(formatterDateTime);
  }

  /**
   * Formatar somete data.
   *
   * @param data the data
   * @return the string
   */
  public static String formatarSometeData(LocalDateTime data) {
    return data.format(formatterDate);
  }

  /**
   * Formatar local date time.
   *
   * @param data the data
   * @return the string
   */
  public static String formatarLocalDateTime(LocalDateTime data) {
    return data.format(formatterDateTime);
  }

  /**
   * Formatar local date time bd.
   *
   * @param data the data
   * @return the string
   */
  public static String formatarLocalDateTimeBd(LocalDateTime data) {
    return data.format(formatterDateTimeBd);
  }

  /**
   * Formatar data cabecalho.
   *
   * @param date the date
   * @return the string
   */
  public static String formatarDataCabecalho(Date date) {
    SimpleDateFormat formatadorData = new SimpleDateFormat(DD_MM_YYYY);
    SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm");
    return new StringBuilder().append(formatadorData.format(date)).append(" às ")
        .append(formatadorHora.format(date)).toString();
  }

  /**
   * To formato data pt br.
   *
   * @param dataFormatoSql the data formato sql
   * @return the string
   */
  public static String toFormatoDataPtBr(String dataFormatoSql) {

    String ds1 = dataFormatoSql;
    SimpleDateFormat sdf1 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    SimpleDateFormat sdf2 = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
    try {
      return sdf2.format(sdf1.parse(ds1));

    } catch (ParseException e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }
    return null;
  }

  /**
   * Convert data to str.
   *
   * @param data the data
   * @param formato the formato
   * @return the string
   */
  public static String convertDataToStr(Date data, String formato) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
    return dateFormat.format(data);
  }

  /**
   * Formatar tipo date.
   *
   * @param data the data
   * @return the string
   */
  @SuppressWarnings("deprecation")
  public static String formatarTipoDate(Date data) {
    return data.toLocaleString();
  }

  /**
   * Formatar nome arquivo data.
   *
   * @param date the date
   * @return the string
   */
  public static String formatarNomeArquivoData(final Date date) {
    final SimpleDateFormat formatadorData = new SimpleDateFormat("ddMMyyyy_HHmm");
    return formatadorData.format(date);
  }
}
