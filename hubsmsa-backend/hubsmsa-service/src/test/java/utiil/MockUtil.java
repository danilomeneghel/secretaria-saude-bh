package utiil;

import java.sql.Timestamp;
import java.time.LocalDate;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.SimNaoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;

// TODO: Auto-generated Javadoc
public class MockUtil {



  /**
   * Mock contato empresa.
   *
   * @return the contato empresa
   */
  public static ContatoEmpresa mockContatoEmpresa() {
    ContatoEmpresa contato = new ContatoEmpresa();
    contato.setId(1L);
    contato.setEmail("email@email.com.br");
    contato.setEmpresa(MockUtil.mockEmpresa());
    contato.setNome("nome do contato");
    contato.setSetor("setor");
    contato.setStatus(StatusEnum.A);
    contato.setTelefone("123");
    return contato;
  }

  /**
   * Mock empresa.
   *
   * @return the empresa
   */
  public static Empresa mockEmpresa() {
    Empresa empresa = new Empresa();
    empresa.setId(1L);
    empresa.setAtivo(StatusEnum.A);
    empresa.setCnpj("111");
    empresa.setCodigoCnes(1L);
    empresa.setNomeEmpresarial("nomeEmpresarial");
    empresa.setNomeFantasia("nomeFantasia");
    empresa.setSite("site");

    return empresa;

  }

  /**
   * Mock contato empresa servico.
   *
   * @return the contato empresa servico
   */
  public static ContatoEmpresaServico mockContatoEmpresaServico() {
    ContatoEmpresaServico contatoEmpresaServico = new ContatoEmpresaServico();
    contatoEmpresaServico.setId(1L);
    contatoEmpresaServico.setContatoEmpresa(mockContatoEmpresa());
    contatoEmpresaServico.setServico(mockServico());
    contatoEmpresaServico.setNotificacaoFalha(SimNaoEnum.N);
    contatoEmpresaServico.setNotificacaoSucesso(SimNaoEnum.S);
    return contatoEmpresaServico;
  }

  /**
   * Mock servico.
   *
   * @return the servico
   */
  public static Servico mockServico() {
    Servico servico = new Servico();
    servico.setId(1L);
    servico.setDescricao("descricao");
    servico.setNome("nome");
    servico.setSistemaPrimario(mockSistema(1L));
    servico.setSistemaSecundario(mockSistema(2L));
    servico.setStatus(StatusEnum.A);

    return servico;
  }

  /**
   * Mock sistema primario.
   *
   * @return the sistema
   */
  private static Sistema mockSistema(Long id) {
    Sistema sistema = new Sistema();
    sistema.setId(id);
    sistema.setAtivo(StatusEnum.A);
    sistema.setDataAtualizacao(LocalDate.now());
    sistema.setDescricao("descricao");
    sistema.setEmpresa(MockUtil.mockEmpresa());
    sistema.setFormaCadastro(FormaCadastroEnum.C);
    sistema.setNome("nome");
    return sistema;
  }

  /**
   * Mock log servico.
   *
   * @return the log servico
   */
  public static LogServico mockLogServico() {
    LogServico logServico = new LogServico();
    logServico.setId(1L);
    logServico.setDataInicioEvento(new Timestamp(System.currentTimeMillis()));
    logServico.setDataFimEvento(new Timestamp(System.currentTimeMillis()));
    logServico.setRequisicao("teste");
    logServico.setResposta("teste");
    logServico.setServico(MockUtil.mockServico());
    return logServico;
  }

}
