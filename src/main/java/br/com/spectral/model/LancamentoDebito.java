package br.com.spectral.model;

public class LancamentoDebito extends Lancamento {
    public LancamentoDebito(Double valor) {
        super(valor);
    }

    public Double getValor() {
        return this.valor*-1;
    }
    
}
