package br.com.treinaweb.twbiblioteca.models;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutorTest {

//    @BeforeEach
//    void antesDeCadaMetodoDeTeste() {
//        System.out.println("Antes de cada método de teste");
//    }
//
//    @AfterEach
//    void depoisDeCadaMetodoDeTeste() {
//        System.out.println("Depois de cada metódo de teste");
//    }
//
//    @BeforeAll
//    static void antesDaClasse() {
//        System.out.println("Antes da classe");
//    }
//
//    @AfterAll
//    static void depoisDaClasse() {
//        System.out.println("Depois da classe");
//    }

    @Test
    void quandoChamarMetodoEstaVivoComDataFalecimentoNulaDeveRetornarTrue() {
        //Cenário
        var autor = new Autor();
        //Execução
        var estaVivo = autor.estaVivo();
        //Verificação
        assertTrue(estaVivo);

    }

    @Test
    void quandoChamarMetodoEstaVivoComDataFalecimentoNaoNulaDeveRetornarFalse() {
        // Cenário
        var autor = new Autor();
        autor.setDataFalecimento(LocalDate.now());
        // Ação
        var estaVivo = autor.estaVivo();
        //Execução
        assertFalse(estaVivo);

    }
}
