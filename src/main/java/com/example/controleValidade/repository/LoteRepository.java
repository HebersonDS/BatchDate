package com.example.controleValidade.repository;

import com.example.controleValidade.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {
    List<Lote> findByDataValidadeBefore(LocalDate data);

    List<Lote> findByDataValidadeBetween(LocalDate inicio,LocalDate fim);

    List<Lote> findByProduto_IdAndDataValidadeBetween(
            Long idProduto,
            LocalDate inicio,
            LocalDate fim
    );

    boolean existsByProdutoId(Long idProduto);
}
