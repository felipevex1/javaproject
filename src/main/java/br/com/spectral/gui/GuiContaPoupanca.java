package br.com.spectral.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.spectral.dao.ContaPoupancaDao;
import br.com.spectral.model.ContaPoupanca;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GuiContaPoupanca implements Initializable {
    private ContaPoupanca conta;

    @FXML
    private ListView<ContaPoupanca> lstContas;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtTaxa;
    @FXML
    private TextField txtSaldo;

    @FXML
    private void btnLancamentoAction(ActionEvent event) {
        try {
            getConta();
            if (conta == null) {
                exibirMensagem("Selecione uma conta.");
                return;
            }
            FXMLLoader floader = new FXMLLoader(getClass().getResource("/fxml/GuiLancamento.fxml"));
            Parent root = (Parent) floader.load();
            GuiLancamento guiLancamento = floader.<GuiLancamento>getController();
            guiLancamento.setConta(conta);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Lancamentos");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            exibirMensagem(e.getMessage());
        }
    }

    @FXML
    private void btnIncluirAction(ActionEvent event) {
        txtTaxa.setText("");
        txtNumero.setText("");
        txtSaldo.setText("");
        txtTaxa.requestFocus();
    }

    @FXML
    private void btnGravarAction(ActionEvent event) {
        Double taxa = Double.parseDouble(txtTaxa.getText());
        conta = new ContaPoupanca(taxa);
        try {
            new ContaPoupancaDao().gravar(conta);
        } catch (IOException e) {
            exibirMensagem(e.getMessage());
            return;
        }
        preencherLista();
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        conta = lstContas.getSelectionModel().getSelectedItem();
        if (conta == null) {
            exibirMensagem("Selecione uma conta para deletar.");
            return;
        }
        try {
            new ContaPoupancaDao().deletar(conta);
        } catch (IOException e) {
            exibirMensagem(e.getMessage());
            return;
        }
        txtTaxa.setText("");
        txtNumero.setText("");
        txtSaldo.setText("");
        preencherLista();
    }

    @FXML
    private void btnRendimentoAction(ActionEvent event) {
        conta = lstContas.getSelectionModel().getSelectedItem();
        if (conta == null) {
            exibirMensagem("Selecione uma conta para aplicar rendimento.");
            return;
        }
        try {
            conta.aplicarRendimento();
            new ContaPoupancaDao().alterar();
        } catch (Exception e) {
            exibirMensagem(e.getMessage());
            return;
        }
        txtSaldo.setText(conta.getSaldo().toString());
    }

    @FXML
    private void lstContasKeyPressed(KeyEvent event) {
        getConta();
    }

    @FXML
    private void lstContasMouseClicked(MouseEvent event) {
        getConta();
    }

    private void getConta() {
        conta = lstContas.getSelectionModel().getSelectedItem();
        if (conta == null) return;
        txtTaxa.setText(conta.getTaxaRendimento().toString());
        txtNumero.setText(conta.getNumero().toString());
        txtSaldo.setText(conta.getSaldo().toString());
    }

    private void preencherLista() {
        List<ContaPoupanca> contas = new ContaPoupancaDao().getLista();
        ObservableList<ContaPoupanca> data = FXCollections.observableArrayList(contas);
        lstContas.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherLista();
    }

    private void exibirMensagem(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensagem);
        alerta.setTitle("Sistema Financeiro");
        alerta.show();
    }
}