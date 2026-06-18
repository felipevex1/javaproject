package br.com.spectral.model;

public class ContaPoupanca extends Conta {
    private Double taxaRendimento;

    public ContaPoupanca() {
        super();
        this.taxaRendimento = 0.0;
    }

    public ContaPoupanca(Double taxaRendimento) {
        super();
        this.taxaRendimento = taxaRendimento;
    }

    public ContaPoupanca(Double taxaRendimento, Cliente cliente) {
        super();
        this.taxaRendimento = taxaRendimento;
        if (cliente != null) {
            this.idCliente = cliente.getId();
        }
    }

    public Double getTaxaRendimento() {
        return this.taxaRendimento;
    }

    public void setTaxaRendimento(Double taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    @Override
    public void debitar(Double valor) {
        if (this.saldo < valor) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        lancamentos.add(new LancamentoDebito(valor));
        this.saldo -= valor;
    }

    public void aplicarRendimento() {
        Double rendimento = this.saldo * (this.taxaRendimento / 100.0);
        creditar(rendimento);
    }
}
