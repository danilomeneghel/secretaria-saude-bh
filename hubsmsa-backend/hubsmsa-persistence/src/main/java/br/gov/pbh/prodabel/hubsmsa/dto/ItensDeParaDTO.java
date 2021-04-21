package br.gov.pbh.prodabel.hubsmsa.dto;

import java.io.Serializable;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;

// TODO: Auto-generated Javadoc
public class ItensDeParaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1359207379988747297L;

    /**
     * Instantiates a new consulta de para primario DTO.
     */
    public ItensDeParaDTO() {}

    /**
     * Instantiates a new consulta de para primario DTO.
     *
     * @param id the id
     * @param revisao the revisao
     * @param revisaoType the revisao type
     * @param codigo the codigo
     * @param descricao the descricao
     * @param tipoDePara the tipo de para
     */
    public ItensDeParaDTO(Long id, Revision revisao, RevisionType revisaoType,
        String codigo, String descricao, String tipoDePara, Long idDePara) {
      super();
      this.id = id;
      this.revisao = revisao;
      this.revisaoType = revisaoType;
      this.codigo = codigo;
      this.descricao = descricao;
      this.tipoDePara = tipoDePara;
      this.idDePara = idDePara;
    }

    Long id;
	
    Revision revisao;
	
    RevisionType revisaoType;
	
    String codigo;
	
    String descricao;

    String tipoDePara;

    Long idDePara;

    public Long getIdDePara() {
      return idDePara;
    }

    public void setIdDePara(Long idDePara) {
      this.idDePara = idDePara;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public Revision getRevisao() {
      return revisao;
    }

    public void setRevisao(Revision revisao) {
      this.revisao = revisao;
    }

    public RevisionType getRevisaoType() {
      return revisaoType;
    }

    public void setRevisaoType(RevisionType revisaoType) {
      this.revisaoType = revisaoType;
    }

    public String getCodigo() {
      return codigo;
    }

    public void setCodigo(String codigo) {
      this.codigo = codigo;
    }

    public String getDescricao() {
      return descricao;
    }

    public void setDescricao(String descricao) {
      this.descricao = descricao;
    }

    public String getTipoDePara() {
      return tipoDePara;
    }

    public void setTipoDePara(String tipoDePara) {
      this.tipoDePara = tipoDePara;
    }
	
}
