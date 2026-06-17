package br.com.spectral.gui;

import br.com.spectral.dao.ContaCorrenteDao;
import br.com.spectral.model.ContaCorrente;
import br.com.spectral.model.Lancamento;

import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

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
    private ContaCorrente contaCorrente;

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
        Double valor = Double.parseDouble(txtValor.getText());
        contaCorrente.debitar(valor);
        try {
            new ContaCorrenteDao().alterar();
        } catch (IOException e) {
            exibirMensagem(e.getMessage());
        }
        preencherLista();
    }
    @FXML
    private void btnCreditarAction(ActionEvent event) { 
        Double valor = Double.parseDouble(txtValor.getText());
        contaCorrente.creditar(valor);
        try {
            new ContaCorrenteDao().alterar();
        } catch (IOException e) {
            exibirMensagem(e.getMessage());
        }
        preencherLista();
    }

    private void preencherLista (){
        List<Lancamento> lancamentos = contaCorrente.getLancamentos();
        ObservableList<Lancamento> data = FXCollections.observableArrayList(lancamentos);
        tblLancamentos.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colData.setCellValueFactory(new PropertyValueFactory<Lancamento, LocalDate>("dataOcorrencia"));
        colHora.setCellValueFactory(new PropertyValueFactory<Lancamento, LocalTime>("horaOcorrencia"));
        colValor.setCellValueFactory(new PropertyValueFactory<Lancamento, Double>("valor"));
        
        preencherLista();
    }    

    private void exibirMensagem(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensagem);
        alerta.setTitle("Sistema Financeiro");
        alerta.show();
    }
}
