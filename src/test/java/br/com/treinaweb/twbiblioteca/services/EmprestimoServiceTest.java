package br.com.treinaweb.twbiblioteca.services;

import br.com.treinaweb.twbiblioteca.builders.ClienteBuilder;
import br.com.treinaweb.twbiblioteca.builders.EmprestimoBuider;
import br.com.treinaweb.twbiblioteca.builders.ObraBuilder;
import br.com.treinaweb.twbiblioteca.dao.EmprestimoDAO;
import br.com.treinaweb.twbiblioteca.enums.Reputacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {

    @Mock
    private EmprestimoDAO emprestimoDAO;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Mock
    private NotificacaoService notificacaoService;

    @Test
    void quandoMetodoNovoForChamadoDeveRetornarUmEmprestimo() {

        // Cenário
        var cliente = ClienteBuilder.builder().build();
        var obra = ObraBuilder.builder().build();

        // Ação
        var emprestimo = emprestimoService.novo(cliente, List.of(obra));

        // Verificação
        assertEquals(cliente, emprestimo.getCliente());
        assertEquals(List.of(obra), emprestimo.getLivros());
        assertEquals(LocalDate.now(), emprestimo.getDataEmprestimo());
        assertEquals(LocalDate.now().plusDays(2), emprestimo.getDataDevolucao());
    }

    @Test
    void quandoMetodoNovoForChamadoComClienteReputacaoRuimDeveRetornarDataDevolucaoComUmDia() {

        // Cenário
        var cliente = ClienteBuilder.builder().reputacao(Reputacao.RUIM).build();
        var obra = ObraBuilder.builder().build();

        // Ação
        var emprestimo = emprestimoService.novo(cliente, List.of(obra));

        // Verificação
        assertEquals(LocalDate.now().plusDays(1), emprestimo.getDataDevolucao());

    }

    @Test
    void quandoMetodoNovoForChamadoComClienteReputacaoRegularDeveRetornarDataDevolucaoComDoisDia() {

        // Cenário
        var cliente = ClienteBuilder.builder().reputacao(Reputacao.REGULAR).build();
        var obra = ObraBuilder.builder().build();

        // Ação
        var emprestimo = emprestimoService.novo(cliente, List.of(obra));

        // Verificação
        assertEquals(LocalDate.now().plusDays(2), emprestimo.getDataDevolucao());

    }

    @Test
    void quandoMetodoNovoForChamadoComClienteReputacaoBoaDeveRetornarDataDevolucaoComTresDia() {

        // Cenário
        var cliente = ClienteBuilder.builder().reputacao(Reputacao.BOA).build();
        var obra = ObraBuilder.builder().build();

        // Ação
        var emprestimo = emprestimoService.novo(cliente, List.of(obra));

        // Verificação
        assertEquals(LocalDate.now().plusDays(3), emprestimo.getDataDevolucao());
    }

    @Test
    void quandoMetodoNovoForChamadoComUmaObraNuloOuVaziaDeveLançarUmaExcecaoDoTipoIllegalArgumentException() {
        // Cenário
        var cliente = ClienteBuilder.builder().reputacao(Reputacao.REGULAR).build();
        var mensagem = "Obras não pode ser nula e nem vazio";

        // ação
        var exception = assertThrows(IllegalArgumentException.class, () -> emprestimoService.novo(cliente, null));

        //verificação
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    public void quandoChamarMetodoNotificarDeveRetornarQuantidadeDeclientesNotificados() {

        // Cenário
        var emprestimos = List.of(
                EmprestimoBuider.builder().build(),
                EmprestimoBuider.builder().dataDevolucao(LocalDate.now().minusDays(1)).build(),
                EmprestimoBuider.builder().dataDevolucao(LocalDate.now().minusDays(2)).build()
        );

        //when -> quando | then -> então
        Mockito.when(emprestimoDAO.buscarTodos()).thenReturn(emprestimos); //Mocando chamada de método

        // execução
        emprestimoService.notificarAtrasos();

        // verificação
        Mockito.verify(notificacaoService).notificar(emprestimos.get(1));

        // Criação do mock
        List<String> mockList = Mockito.mock(List.class);

        // Simula uma interação
        mockList.add("item");

        // Verifica se o método add foi chamado
        Mockito.verify(mockList).add("item");

    }

}
