package br.gov.pbh.prodabel.hubsmsa.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.EnumUtils;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusExecucao;

// TODO: Auto-generated Javadoc
public class EnumUtil {

  /**
   * To status enum list.
   *
   * @param enumValues the enum values
   * @return the list
   */
  @SuppressWarnings("unchecked")
  public static List<StatusEnum> toStatusEnumList(List<String> enumValues) {
    Map<String, StatusEnum> enummap = EnumUtils.getEnumMap(StatusEnum.class);
    return (List<StatusEnum>) enummap.keySet().stream().map(key -> {
      if (enumValues.contains(key)) {
        return Optional.of(enummap.get(key));
      }
      return Optional.empty();
    }).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
  }

  /**
   * To status execucao enum list.
   *
   * @param enumValues the enum values
   * @return the list
   */
  @SuppressWarnings("unchecked")
  public static List<StatusExecucao> toStatusExecucaoEnumList(List<String> enumValues) {
    Map<String, StatusExecucao> enummap = EnumUtils.getEnumMap(StatusExecucao.class);
    return (List<StatusExecucao>) enummap.keySet().stream().map(key -> {
      if (enumValues.contains(key)) {
        return Optional.of(enummap.get(key));
      }
      return Optional.empty();
    }).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
  }

}
