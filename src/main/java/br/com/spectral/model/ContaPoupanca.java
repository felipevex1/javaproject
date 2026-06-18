package br.com.spectral.model;

public class ContaPoupanca extends Conta {
    private Double taxaRendimento;
    private static Integer proximoNumero = 1;

    public ContaPoupanca() {
        super();
        this.numero = proximoNumero;
        proximoNumero++;
        this.taxaRendimento = 0.0;
    }

    public ContaPoupanca(Double taxaRendimento) {
        super();
        this.numero = proximoNumero;
        proximoNumero++;
        this.taxaRendimento = taxaRendimento;
    }

    public Double getTaxaRendimento() {
        return this.taxaRendimento;
    }

    public void setTaxaRendimento(Double taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    public static void setProximoNumero(Integer proximoNumero) {
        ContaPoupanca.proximoNumero = proximoNumero;
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