package br.gov.pbh.prodabel.hubsmsa.dto.aud;

import java.io.Serializable;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;

public class AudGenericoDTO implements Serializable {
  
  private static final long serialVersionUID = -7347839125351659546L;

  private Long id;
  private RevisionType revType;
  private Revision revisao;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RevisionType getRevType() {
    return revType;
  }

  public void setRevType(RevisionType revType) {
    this.revType = revType;
  }

  public Revision getRevisao() {
    return revisao;
  }

  public void setRevisao(Revision revisao) {
    this.revisao = revisao;
  }

}
