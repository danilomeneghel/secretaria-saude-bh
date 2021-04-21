package br.gov.pbh.prodabel.hubsmsa.dto;

import java.util.Date;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

public class EnversDTO extends BaseDTO {

	private static final long serialVersionUID = 356543310466221899L;
	private Long idRevision;
	private Date dateRevision;
	private String user;
	private String entityName;
	private Object entityId; 
	private String field;
	private String value;
	private String oldValue;
	private RevisionType revisionType;
	private StatusEnum status;
	private String nomeEmpresa;
    private String tipoDePara;
    private String sistemaPrimarioNome;
	
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	public Long getIdRevision() {
		return idRevision;
	}
	public void setIdRevision(Long idRevision) {
		this.idRevision = idRevision;
	}	
	public String getKey() {
		return entityName + field + entityId;
	}
	public Date getDateRevision() {
		return dateRevision;
	}
	public void setDateRevision(Date date) {
		this.dateRevision = date;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Object getEntityId() {
		return entityId;
	}
	public void setEntityId(Object entityId) {
		this.entityId = entityId;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public RevisionType getRevisionType() {
		return revisionType;
	}
	public void setRevisionType(RevisionType revisionType) {
		this.revisionType = revisionType;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
    public String getTipoDePara() {
      return tipoDePara;
    }

    public void setTipoDePara(String tipoDePara) {
      this.tipoDePara = tipoDePara;
    }

    public String getSistemaPrimarioNome() {
      return sistemaPrimarioNome;
    }

    public void setSistemaPrimarioNome(String sistemaPrimarioNome) {
      this.sistemaPrimarioNome = sistemaPrimarioNome;
    }
    @Override
    public String toString() {
		return "[idRevision: "+idRevision+"- revisionType: "+revisionType.name()+" - entityName: "+entityName+" - field: "+field+" - oldValue: "+(oldValue != null ? oldValue: "null")+" - value: "+(value != null ? value: "null")+"]";
	}
    

	
}
