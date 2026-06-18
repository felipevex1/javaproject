package br.com.spectral.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class GuiPrincipal {

    @FXML
    private void btnContaCorrenteAction(ActionEvent event) {
        abrirTela("/fxml/GuiContaCorrente.fxml", "Conta Corrente");
    }

    @FXML
    private void btnContaPoupancaAction(ActionEvent event) {
        abrirTela("/fxml/GuiContaPoupanca.fxml", "Conta Poupanca");
    }

    @FXML
    private void btnContaSalarioAction(ActionEvent event) {
        abrirTela("/fxml/GuiContaSalario.fxml", "Conta Salario");
    }

    private void abrirTela(String fxml, String titulo) {
        try {
            FXMLLoader floader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = (Parent) floader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            exibirMensagem(e.getMessage());
        }
    }

    private void exibirMensagem(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensagem);
        alerta.setTitle("Sistema Financeiro");
        alerta.show();
    }
}