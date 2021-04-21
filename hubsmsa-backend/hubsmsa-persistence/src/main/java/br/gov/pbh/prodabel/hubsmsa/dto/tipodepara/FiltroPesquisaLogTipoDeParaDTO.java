package br.gov.pbh.prodabel.hubsmsa.dto.tipodepara;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.ws.rs.QueryParam;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.gov.pbh.prodabel.hubsmsa.dto.FiltroRevisaoDTO;
import io.swagger.v3.oas.annotations.Parameter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiltroPesquisaLogTipoDeParaDTO extends FiltroRevisaoDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7420865811533572288L;
	
	private static final String HORA_INICIAL = "00:00:00";
	private static final String HORA_FINAL = "23:59:59";
	
    @Parameter(description = "Filtro de Tipo De/Para", required = false)
    @QueryParam("idTipoDePara")
    private Long idTipoDePara;

	@Parameter(description = "Filtro Data Inicial do Tipo de De/Para", required = false)
	@QueryParam("dataInicial")
	private String dataInicial;

	@Parameter(description = "Filtro Data Final do Tipo de De/Para", required = false)
	@QueryParam("dataFinal")
	private String dataFinal;
	  
	@Parameter(description = "Filtro nome do Tipo de De/Para", required = false)
	@QueryParam("nome")
	private String nome;


		/**
		 * Instantiates a new filtro pesquisa log de para DTO.
		 */
		public FiltroPesquisaLogTipoDeParaDTO() {
		}

		public LocalDateTime getDataInicial() {
			if (this.dataInicial != null && !this.dataInicial.isEmpty()) {
				StringBuilder dataHora = new StringBuilder(this.dataInicial).append(" ").append(HORA_INICIAL);
				return StringUtils.isNotBlank(this.dataInicial)
						? LocalDateTime.parse(dataHora, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
						: null;
			} else {
				return null;
			}
		}

		public LocalDateTime getDataFinal() {
			if (this.dataFinal != null && !this.dataFinal.isEmpty()) {
				StringBuilder dataHora = new StringBuilder(this.dataFinal).append(" ").append(HORA_FINAL);
				return StringUtils.isNotBlank(this.dataFinal)
						? LocalDateTime.parse(dataHora, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
						: null;
			} else {
				return null;
			}

		}

        public Long getIdTipoDePara() {
          return idTipoDePara;
        }

        public void setIdTipoDePara(Long idTipoDePara) {
          this.idTipoDePara = idTipoDePara;
        }

        public void setDataInicial(String dataInicial) {
			this.dataInicial = dataInicial;
		}

		public void setDataFinal(String dataFinal) {
			this.dataFinal = dataFinal;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}


	}
