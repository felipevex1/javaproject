package br.com.spectral.model;

public class ContaCorrente extends Conta {
    private Double limite;

    public ContaCorrente() {
        super();
        this.limite = 0.0;
    }

    public ContaCorrente(Double limite) {
        super();
        this.limite = limite;
    }

    public ContaCorrente(Double limite, Cliente cliente) {
        super();
        this.limite = limite;
        if (cliente != null) {
            this.idCliente = cliente.getId();
        }
    }

    public Double getLimite() {
        return this.limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    @Override
    public void debitar(Double valor) {
        if ((this.saldo + this.limite) < valor) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        lancamentos.add(new LancamentoDebito(valor));
        this.saldo -= valor;
    }
}
