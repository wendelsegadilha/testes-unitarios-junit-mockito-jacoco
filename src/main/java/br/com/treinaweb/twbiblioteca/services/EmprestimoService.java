package br.com.treinaweb.twbiblioteca.services;

import br.com.treinaweb.twbiblioteca.dao.EmprestimoDAO;
import br.com.treinaweb.twbiblioteca.models.Cliente;
import br.com.treinaweb.twbiblioteca.models.Emprestimo;
import br.com.treinaweb.twbiblioteca.models.Obra;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoService {

    private final EmprestimoDAO emprestimoDAO;
    private final NotificacaoService notificacaoService;

    public EmprestimoService(EmprestimoDAO emprestimoDAO, NotificacaoService notificacaoService) {
        this.emprestimoDAO = emprestimoDAO;
        this.notificacaoService = notificacaoService;
    }

    public Emprestimo novo(Cliente cliente, List<Obra> obras) {

        if (obras == null || obras.isEmpty()) {
            throw new IllegalArgumentException("Obras não pode ser nula e nem vazio");
        }

        var emprestimo = new Emprestimo();

        var dataemprestimo = LocalDate.now();
        var diasParaDevolucao = cliente.getReputacao().obterDiasParaDevolucao();
        var dataDevolucao = dataemprestimo.plusDays(diasParaDevolucao);

        emprestimo.setCliente(cliente);
        emprestimo.setLivros(obras);
        emprestimo.setDataEmprestimo(dataemprestimo);
        emprestimo.setDataDevolucao(dataDevolucao);

        return emprestimo;
    }

    public void notificarAtrasos() {
        var hoje = LocalDate.now();

        var emprestimos = emprestimoDAO.buscarTodos();
        for (var emprestimo : emprestimos) {
            var estaAtrasado = emprestimo.getDataDevolucao().isBefore(hoje);
            if (estaAtrasado) {
                notificacaoService.notificar(emprestimo);
            }
        }
    }

}
