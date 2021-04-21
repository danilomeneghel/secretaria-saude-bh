package br.gov.prodabel.hubsmsa.handler.test;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.handler.ServicosHandler;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.service.ContatoEmpresaServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.ServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.SistemaService;

// TODO: Auto-generated Javadoc
@RunWith(MockitoJUnitRunner.class)
public class ServicosHandlerTest {

  private static final String SISREDE = "SISREDE";
  private static final String SIGRAH = "SIGRAH";

  private ServicosHandler servicosHandler;

  @Mock
  private SistemaService sistemaService;

  @Mock
  private ServicoService servicoService;

  @Mock
  private ContatoEmpresaServicoService contatoService;

  /**
   * Inits the.
   */
  @Before
  public void init() {

    servicosHandler = new ServicosHandler(SISREDE, SIGRAH, "Servico");

  }

  /**
   * Teste.
   */
  @Test
  @Ignore
  public void setServicesTest() {
    Mockito.when(sistemaService.consultarSistemaPorNome(SISREDE))
        .thenReturn(sistemaSisredeMock(SISREDE));
    Mockito.when(sistemaService.consultarSistemaPorNome(SIGRAH))
        .thenReturn(sistemaSisredeMock(SIGRAH));
    Mockito.when(
        servicoService.consultarServico(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(Arrays.asList(servicoMock()));

    Mockito.when(contatoService.buscarContatosSucesso(Mockito.any()))
        .thenReturn(contatosSucessoMock());
    Mockito.when(contatoService.buscarContatosFalha(Mockito.any()))
        .thenReturn(contatosSucessoMock());

    servicosHandler.setServices(sistemaService, servicoService, contatoService);

    assertEquals(servicosHandler.getContatosEmpresaSucesso(), contatosSucessoMock());
    assertEquals(servicosHandler.getServico(), servicoMock());
    assertEquals(servicosHandler.getSistemaPrimario(), sistemaSisredeMock(SISREDE));
    assertEquals(servicosHandler.getSistemaSecundario(), sistemaSisredeMock(SIGRAH));
  }

  /**
   * Contatos sucesso mock.
   *
   * @return the list
   */
  private List<ContatoEmpresaServico> contatosSucessoMock() {

    ContatoEmpresa contatoEmpresa = new ContatoEmpresa();
    contatoEmpresa.setSetor("TI");
    contatoEmpresa.setId(1L);
    contatoEmpresa.setEmail("teste@email.com");

    ContatoEmpresaServico contato = new ContatoEmpresaServico();
    contato.setId(1L);
    contato.setServico(servicoMock());
    contato.setContatoEmpresa(contatoEmpresa);

    return Arrays.asList(contato);

  }

  /**
   * Sistema sisrede mock.
   *
   * @param nomeSistema the nome sistema
   * @return the sistema
   */
  private Sistema sistemaSisredeMock(String nomeSistema) {

    Sistema sisrede = new Sistema();
    Empresa empresa = empresaMock();
    sisrede.setAtivo(StatusEnum.A);
    sisrede.setNome(nomeSistema);
    sisrede.setEmpresa(empresa);

    return sisrede;
  }

  /**
   * Empresa mock.
   *
   * @return the empresa
   */
  private Empresa empresaMock() {
    Empresa empresa = new Empresa();
    empresa.setNomeEmpresarial("Empresa teste");
    empresa.setAtivo(StatusEnum.A);
    return empresa;
  }

  /**
   * Servico mock.
   *
   * @return the servico
   */
  private Servico servicoMock() {
    Servico servico = new Servico();
    servico.setNome("Agendamento");
    return servico;
  }



}
