package br.com.spectral.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.spectral.dao.ClienteDao;
import br.com.spectral.dao.ContaSalarioDao;
import br.com.spectral.model.Cliente;
import br.com.spectral.model.ContaSalario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GuiContaSalario implements Initializable {
    private ContaSalario conta;

    @FXML
    private ListView<ContaSalario> lstContas;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtSaldo;
    @FXML
    private ComboBox<Cliente> cmbCliente;
    @FXML
    private Button btnGravar;

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
        txtNumero.setText("");
        txtSaldo.setText("");
        cmbCliente.getSelectionModel().clearSelection();
        btnGravar.setDisable(false);
    }

    @FXML
    private void btnGravarAction(ActionEvent event) {
        Cliente cli = cmbCliente.getValue();
        conta = new ContaSalario(cli);
        try {
            new ContaSalarioDao().gravar(conta);
        } catch (IOException e) {
            exibirMensagem(e.getMessage());
            return;
        }
        preencherLista();
        btnGravar.setDisable(true);
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        conta = lstContas.getSelectionModel().getSelectedItem();
        if (conta == null) {
            exibirMensagem("Selecione uma conta para deletar.");
            return;
        }
        try {
            new ContaSalarioDao().deletar(conta);
        } catch (IOException e) {
            exibirMensagem(e.getMessage());
            return;
        }
        txtNumero.setText("");
        txtSaldo.setText("");
        cmbCliente.getSelectionModel().clearSelection();
        btnGravar.setDisable(true);
        preencherLista();
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
        txtNumero.setText(conta.getNumero().toString());
        txtSaldo.setText(conta.getSaldo().toString());
        selecionarClienteNoCombo(conta.getIdCliente());
        btnGravar.setDisable(true);
    }

    private void selecionarClienteNoCombo(Integer idCliente) {
        if (idCliente == null) {
            cmbCliente.getSelectionModel().clearSelection();
            return;
        }
        for (Cliente c : cmbCliente.getItems()) {
            if (c.getId().equals(idCliente)) {
                cmbCliente.getSelectionModel().select(c);
                return;
            }
        }
    }

    private void preencherLista() {
        List<ContaSalario> contas = new ContaSalarioDao().getLista();
        ObservableList<ContaSalario> data = FXCollections.observableArrayList(contas);
        lstContas.setItems(data);
    }

    private void carregarClientes() {
        List<Cliente> clientes = new ClienteDao().getLista();
        ObservableList<Cliente> data = FXCollections.observableArrayList(clientes);
        cmbCliente.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarClientes();
        preencherLista();
    }

    private void exibirMensagem(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensagem);
        alerta.setTitle("Sistema Financeiro");
        alerta.show();
    }
}
