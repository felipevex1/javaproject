package br.com.spectral.gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import br.com.spectral.dao.ContaCorrenteDao;
import br.com.spectral.dao.ContaPoupancaDao;
import br.com.spectral.dao.ContaSalarioDao;
import br.com.spectral.dao.LancamentoDao;
import br.com.spectral.model.Conta;
import br.com.spectral.model.ContaCorrente;
import br.com.spectral.model.ContaPoupanca;
import br.com.spectral.model.ContaSalario;
import br.com.spectral.model.Lancamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GuiLancamento implements Initializable {
    private Conta conta;
    private String contaTipo;

    @FXML
    private TableView<Lancamento> tblLancamentos;
    @FXML
    private TableColumn<Lancamento, LocalDate> colData = new TableColumn<>();
    @FXML
    private TableColumn<Lancamento, LocalTime> colHora = new TableColumn<>();
    @FXML
    private TableColumn<Lancamento, Double> colValor = new TableColumn<>();
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtLimite;
    @FXML
    private TextField txtSaldo;
    @FXML
    private TextField txtValor;

    @FXML
    private void btnDebitarAction(ActionEvent event) {
        try {
            Double valor = Double.parseDouble(txtValor.getText());
            int qtdAntes = conta.getLancamentos().size();
            conta.debitar(valor);
            Lancamento novo = conta.getLancamentos().get(qtdAntes);
            new LancamentoDao().gravar(novo, conta.getNumero(), contaTipo);
            salvarConta();
        } catch (Exception e) {
            exibirMensagem(e.getMessage());
        }
        preencherLista();
        txtSaldo.setText(conta.getSaldo().toString());
    }

    @FXML
    private void btnCreditarAction(ActionEvent event) {
        try {
            Double valor = Double.parseDouble(txtValor.getText());
            int qtdAntes = conta.getLancamentos().size();
            conta.creditar(valor);
            Lancamento novo = conta.getLancamentos().get(qtdAntes);
            new LancamentoDao().gravar(novo, conta.getNumero(), contaTipo);
            salvarConta();
        } catch (Exception e) {
            exibirMensagem(e.getMessage());
        }
        preencherLista();
        txtSaldo.setText(conta.getSaldo().toString());
    }

    private void salvarConta() {
        if (conta instanceof ContaCorrente) {
            new ContaCorrenteDao().alterar((ContaCorrente) conta);
        } else if (conta instanceof ContaPoupanca) {
            new ContaPoupancaDao().alterar((ContaPoupanca) conta);
        } else if (conta instanceof ContaSalario) {
            new ContaSalarioDao().alterar((ContaSalario) conta);
        }
    }

    public void setConta(Conta conta) {
        this.conta = conta;
        if (conta instanceof ContaCorrente) {
            contaTipo = "CORRENTE";
            txtLimite.setText(((ContaCorrente) conta).getLimite().toString());
            txtLimite.setDisable(false);
        } else if (conta instanceof ContaPoupanca) {
            contaTipo = "POUPANCA";
            txtLimite.setText(((ContaPoupanca) conta).getTaxaRendimento().toString());
            txtLimite.setDisable(false);
        } else {
            contaTipo = "SALARIO";
            txtLimite.setText("0.0");
            txtLimite.setDisable(true);
        }
        txtNumero.setText(conta.getNumero().toString());
        txtSaldo.setText(conta.getSaldo().toString());

        // Carrega lancamentos do banco
        List<Lancamento> doBanco = new LancamentoDao().getLista(conta.getNumero(), contaTipo);
        conta.setLancamentos(new java.util.ArrayList<>(doBanco));
        preencherLista();
    }

    private void preencherLista() {
        List<Lancamento> lancamentos = conta.getLancamentos();
        ObservableList<Lancamento> data = FXCollections.observableArrayList(lancamentos);
        tblLancamentos.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colData.setCellValueFactory(new PropertyValueFactory<Lancamento, LocalDate>("dataOcorrencia"));
        colHora.setCellValueFactory(new PropertyValueFactory<Lancamento, LocalTime>("horaOcorrencia"));
        colValor.setCellValueFactory(new PropertyValueFactory<Lancamento, Double>("valor"));
    }

    private void exibirMensagem(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensagem);
        alerta.setTitle("Sistema Financeiro");
        alerta.show();
    }
}
