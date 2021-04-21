export enum SexoEnum {
  MASCULINO = 'M',
  FEMININO = 'F',
  IGNORADO = 'I',
  NAO_SE_APLICA = 'N',
}

export const sexoEnumMensagem = {
  [SexoEnum.MASCULINO]: 'Masculino',
  [SexoEnum.FEMININO]: 'Feminino',
  [SexoEnum.IGNORADO]: 'Ignorado',
  [SexoEnum.NAO_SE_APLICA]: 'Não se aplica'
};
