package com.example.controleValidade.dto;

import com.example.controleValidade.model.Lote;
import com.example.controleValidade.model.Produto;

import java.util.List;
import java.time.LocalDate;

public class ProdutoExportacao {

    private String produto;
    private List<LoteExportacao> lotes;

    public ProdutoExportacao(Produto produto) {
        this.produto = produto.getNome();

        this.lotes = produto.getLotes()
                .stream()
                .map(LoteExportacao::new)
                .toList();
    }

    public String getProduto() {
        return produto;
    }

    public List<LoteExportacao> getLotes() {
        return lotes;
    }

    public static class LoteExportacao {

        private String codigoLote;
        private LocalDate dataValidade;
        private Integer quantidade;

        public LoteExportacao(Lote lote) {
            this.codigoLote = lote.getCodigoLote();
            this.dataValidade = lote.getDataValidade();
            this.quantidade = lote.getQuantidade();
        }

        public String getCodigoLote() {
            return codigoLote;
        }

        public LocalDate getDataValidade() {
            return dataValidade;
        }

        public Integer getQuantidade() {
            return quantidade;
        }
    }
}