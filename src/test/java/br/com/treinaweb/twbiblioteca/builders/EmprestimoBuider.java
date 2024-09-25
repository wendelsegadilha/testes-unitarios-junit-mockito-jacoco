package br.com.treinaweb.twbiblioteca.builders;

import br.com.treinaweb.twbiblioteca.models.Emprestimo;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoBuider {

    private Emprestimo emprestimo;

    public static EmprestimoBuider builder() {
        var builder = new EmprestimoBuider();
        var cliente = ClienteBuilder.builder().build();
        var obra = ObraBuilder.builder().build();
        var dataEmprestimo = LocalDate.now();
        var dataDevolucao = dataEmprestimo.plusDays(3);
        var emprestimo = new Emprestimo(cliente, List.of(obra), dataEmprestimo, dataDevolucao);
        builder.emprestimo = emprestimo;
        return builder;
    }

    public EmprestimoBuider dataDevolucao(LocalDate dataDevolucao) {
        emprestimo.setDataDevolucao(dataDevolucao);
        return this;
    }

    public Emprestimo build() {
        return emprestimo;
    }
}
