package br.com.treinaweb.twbiblioteca.dao;

import br.com.treinaweb.twbiblioteca.models.Emprestimo;

import java.util.List;

public interface EmprestimoDAO {
    List<Emprestimo> buscarTodos();
}
