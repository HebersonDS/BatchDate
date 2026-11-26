package com.example.controleValidade.dto;

import com.example.controleValidade.model.Lote;
import com.example.controleValidade.model.Produto;

import java.time.LocalDate;

public class LoteAlerta {
    private String nomeProduto, categoria, codigoLote;
    private LocalDate dataValidade;
    private Integer quantidade;

    public LoteAlerta(Produto produto, Lote lote) {
        this.nomeProduto = produto.getNome();
        this.categoria = produto.getCategoria();
        this.codigoLote = lote.getCodigoLote();
        this.dataValidade = lote.getDataValidade();
        this.quantidade = lote.getQuantidade();
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getCategoria() {
        return categoria;
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
