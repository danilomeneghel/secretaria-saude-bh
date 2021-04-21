import { VisualizarSistemaDTO } from './visualizar-sistema-dto.model';

export class VisualizarDeParaDTO {
  nomeDePara: string;
  nomeTipoDePara: string;
  status: string;
  sistemaPrimario: VisualizarSistemaDTO;
  sistemaSecundario: VisualizarSistemaDTO;
}
