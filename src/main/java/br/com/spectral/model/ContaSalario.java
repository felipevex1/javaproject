package br.com.spectral.model;

public class ContaSalario extends Conta {
    private static Integer proximoNumero = 1;

    public ContaSalario() {
        super();
        this.numero = proximoNumero;
        proximoNumero++;
    }

    public ContaSalario(Cliente cliente) {
        super();
        this.numero = proximoNumero;
        proximoNumero++;
        if (cliente != null) {
            this.idCliente = cliente.getId();
        }
    }

    public static void setProximoNumero(Integer proximoNumero) {
        ContaSalario.proximoNumero = proximoNumero;
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
