package br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.ws.rs.QueryParam;
import org.apache.commons.lang3.StringUtils;
import br.gov.pbh.prodabel.hubsmsa.dto.FiltroRevisaoDTO;
import io.swagger.v3.oas.annotations.Parameter;

public class FiltroPesquisaLogContatoEmpresaDTO extends FiltroRevisaoDTO  {

	private static final long serialVersionUID = 3422516342921094380L;
	
	private static final String HORA_INICIAL = "00:00:00";
	private static final String HORA_FINAL = "23:59:59";
	
	@Parameter(description = "Filtro de Empresa", required = false)
	@QueryParam("idEmpresa")
	private Long idEmpresa;

	@Parameter(description = "Filtro Data Inicial do Evento", required = false)
	@QueryParam("dataInicial")
	private String dataInicial;
	
	@Parameter(description = "Filtro Data Final do Evento", required = false)
	@QueryParam("dataFinal")
	private String dataFinal;
	
	@Parameter(description = "Filtro nome do contato", required = false)
	@QueryParam("nome")
	private String nome;

    @Parameter(description = "Filtro código id Contato Empresa", required = false)
    @QueryParam("id")
    private Long id;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	
	/**
	 * @return the dataInicial
	 */
	public LocalDateTime getDataInicial() {
		if (this.dataInicial!= null && !this.dataInicial.isEmpty()) {
			StringBuilder dataHora = new StringBuilder(this.dataInicial).append(" ").append(HORA_INICIAL);
			return StringUtils.isNotBlank(this.dataInicial)
					? LocalDateTime.parse(dataHora, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
					: null;
		} else {
			return null;
		}
	}
	
	/**
	 * @return the dataFinal
	 */
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

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

}
