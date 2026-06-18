package br.com.spectral.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.spectral.dao.ClienteDao;
import br.com.spectral.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GuiCliente implements Initializable {
    private Cliente cliente;

    @FXML
    private ListView<Cliente> lstClientes;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCpf;
    @FXML
    private Button btnGravar;

    @FXML
    private void btnIncluirAction(ActionEvent event) {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        btnGravar.setDisable(false);
        txtNome.requestFocus();
    }

    @FXML
    private void btnGravarAction(ActionEvent event) {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        if (nome.isEmpty() || cpf.isEmpty()) {
            exibirMensagem("Nome e CPF sao obrigatorios.");
            return;
        }
        try {
            cliente = new Cliente(nome, cpf);
            new ClienteDao().gravar(cliente);
        } catch (IOException e) {
            exibirMensagem(e.getMessage());
            return;
        }
        preencherLista();
        btnGravar.setDisable(true);
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        cliente = lstClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            exibirMensagem("Selecione um cliente para deletar.");
            return;
        }
        try {
            new ClienteDao().deletar(cliente);
        } catch (IOException e) {
            exibirMensagem(e.getMessage());
            return;
        }
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        btnGravar.setDisable(true);
        preencherLista();
    }

    @FXML
    private void lstClientesKeyPressed(KeyEvent event) {
        getCliente();
    }

    @FXML
    private void lstClientesMouseClicked(MouseEvent event) {
        getCliente();
    }

    private void getCliente() {
        cliente = lstClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) return;
        txtId.setText(cliente.getId().toString());
        txtNome.setText(cliente.getNome());
        txtCpf.setText(cliente.getCpf());
        btnGravar.setDisable(true);
    }

    private void preencherLista() {
        List<Cliente> clientes = new ClienteDao().getLista();
        ObservableList<Cliente> data = FXCollections.observableArrayList(clientes);
        lstClientes.setItems(data);
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
