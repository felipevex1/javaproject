package br.com.spectral.gui;

import br.com.spectral.dao.ContaCorrenteDao;
import br.com.spectral.model.ContaCorrente;

import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class GuiContaCorrente implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        //System.out.println("You clicked me!");
        List<ContaCorrente> contas = new ContaCorrenteDao().getLista();
        ContaCorrente cc = new ContaCorrente();
        try {
            new ContaCorrenteDao().gravar(cc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        label.setText("Fim!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}
