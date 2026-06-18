package br.com.spectral.model;

public class ContaSalario extends Conta {

    public ContaSalario() {
        super();
    }

    public ContaSalario(Cliente cliente) {
        super();
        if (cliente != null) {
            this.idCliente = cliente.getId();
        }
    }

    @Override
    public void debitar(Double valor) {
        if (this.saldo < valor) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        lancamentos.add(new LancamentoDebito(valor));
        this.saldo -= valor;
    }
}
