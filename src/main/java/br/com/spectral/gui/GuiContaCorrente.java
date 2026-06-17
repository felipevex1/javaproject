package br.com.spectral.gui;

import br.com.spectral.dao.ContaCorrenteDao;
import br.com.spectral.model.ContaCorrente;

import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class GuiContaCorrente implements Initializable {
    private ContaCorrente contaCorrente;

    @FXML
    private ListView<ContaCorrente> lstContas;

    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtLimite;
    @FXML
    private TextField txtSaldo;

    @FXML
    private void btnLancamentoAction(ActionEvent event) {
        getContaCorrente();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/GuiLancamento.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        Stage stage = new Stage();
        stage.setTitle("Lançamentos");
        stage.setScene(scene);
        stage.show();
        
    }
    @FXML
    private void btnIncluirAction(ActionEvent event) {
        txtLimite.setText("");
        txtNumero.setText("");
        txtSaldo.setText("");
        txtLimite.requestFocus();
    }
    @FXML
    private void btnGravarAction(ActionEvent event) {
        Double limite = Double.parseDouble(txtLimite.getText());
        contaCorrente = new ContaCorrente(limite);
        try {
            new ContaCorrenteDao().gravar(contaCorrente);
        } catch (IOException e) {
            exibirMensagem(e.getMessage());
            return;
        }
        preencherLista();
    }
    @FXML
    private void lstContasKeyPressed(KeyEvent event) {
        getContaCorrente();
    }
    @FXML
    private void lstContasMouseClicked(MouseEvent event) {
        getContaCorrente();
    }

    private void getContaCorrente (){
        contaCorrente = lstContas.getSelectionModel().getSelectedItem();
        txtLimite.setText(contaCorrente.getLimite().toString());
        txtNumero.setText(contaCorrente.getNumero().toString());
        txtSaldo.setText(contaCorrente.getSaldo().toString());
    }
    private void preencherLista (){
        List<ContaCorrente> contas = new ContaCorrenteDao().getLista();
        ObservableList<ContaCorrente> data = FXCollections.observableArrayList(contas);
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
