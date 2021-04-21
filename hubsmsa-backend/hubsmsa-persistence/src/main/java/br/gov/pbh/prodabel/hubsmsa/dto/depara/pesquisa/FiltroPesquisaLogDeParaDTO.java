package br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import javax.ws.rs.QueryParam;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.gov.pbh.prodabel.hubsmsa.dto.FiltroRevisaoDTO;
import io.swagger.v3.oas.annotations.Parameter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiltroPesquisaLogDeParaDTO extends FiltroRevisaoDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7420865811533572288L;
	
	private static final String HORA_INICIAL = "00:00:00";
	private static final String HORA_FINAL = "23:59:59";
	
    @Parameter(description = "Filtro de id De/Para", required = false)
    @QueryParam("id")
    private Long id;

    @Parameter(description = "Filtro de Tipo De/Para", required = false)
    @QueryParam("idTipoDePara")
    private Long idTipoDePara;

    @Parameter(description = "Filtro de usuário", required = false)
    @QueryParam("idUsuario")
    private Long idUsuario;

	@Parameter(description = "Filtro Data Inicial do Tipo de De/Para", required = false)
	@QueryParam("dataInicial")
	private String dataInicial;

	@Parameter(description = "Filtro Data Final do Tipo de De/Para", required = false)
	@QueryParam("dataFinal")
	private String dataFinal;
	  
    @Parameter(description = "Filtro nome do De/Para", required = false)
    @QueryParam("nomeDePara")
    private String nomeDePara;

    @Parameter(description = "Filtros do sistema primário", required = false)
    @QueryParam("idSistemaPrimario")
    private Long idSistemaPrimario;

    @Parameter(description = "Filtros do sistema secundario", required = false)
    @QueryParam("idSistemaSecundario")
    private Long idSistemaSecundario;

    private String loginUsuario;

    private Set<Long> codigosDePara;



		/**
		 * Instantiates a new filtro pesquisa log de para DTO.
		 */
		public FiltroPesquisaLogDeParaDTO() {
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

        public String getNomeDePara() {
          return nomeDePara;
        }

        public void setNomeDePara(String nomeDePara) {
          this.nomeDePara = nomeDePara;
        }

        public Long getIdSistemaPrimario() {
          return idSistemaPrimario;
        }

        public void setIdSistemaPrimario(Long idSistemaPrimario) {
          this.idSistemaPrimario = idSistemaPrimario;
        }

        public Long getIdSistemaSecundario() {
          return idSistemaSecundario;
        }

        public void setIdSistemaSecundario(Long idSistemaSecundario) {
          this.idSistemaSecundario = idSistemaSecundario;
        }

        public Long getId() {
          return id;
        }

        public void setId(Long id) {
          this.id = id;
        }

        public Long getIdUsuario() {
          return idUsuario;
        }

        public void setIdUsuario(Long idUsuario) {
          this.idUsuario = idUsuario;
        }

        public String getLoginUsuario() {
          return loginUsuario;
        }

        public void setLoginUsuario(String loginUsuario) {
          this.loginUsuario = loginUsuario;
        }

        public Set<Long> getCodigosDePara() {
          return codigosDePara;
        }

        public void setCodigosDePara(Set<Long> codigosDePara) {
          this.codigosDePara = codigosDePara;
        }

	}
