package br.com.spectral.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.thoughtworks.xstream.XStream;
import br.com.spectral.model.ContaCorrente;

public class ContaCorrenteDao {
    private static String arquivo = "contacorrente.xml";
    private static List<ContaCorrente> contascorrentes = new ArrayList<ContaCorrente>();

    public List<ContaCorrente> getLista() {
        XStream xs = new XStream();
        File f = new File(arquivo);

        if (!(f.exists())) {
            return new ArrayList<ContaCorrente>();
        }

        contascorrentes = (List<ContaCorrente>) xs.fromXML(f);

        int proximo = 0;
        for (ContaCorrente c: contascorrentes) {
            if (c.getNumero() > proximo) {
                proximo = c.getNumero();
            }
        }
        
        ContaCorrente.setProximoNumero(proximo+1);
        return contascorrentes;
    }
    
    public void gravar(ContaCorrente contaCorrente) throws IOException {
        List<ContaCorrente> contas = getLista();

        if (contas == null) {
            contas = new ArrayList<ContaCorrente>();
        }
        contas.add(contaCorrente);

        XStream xs = new XStream();
        String xml = xs.toXML(contas);

        FileWriter fw = new FileWriter(arquivo);
        fw.write(xml);
        fw.close();
    }

    public void alterar() throws IOException {
 
        XStream xs = new XStream();
        String xml = xs.toXML(contascorrentes);

        FileWriter fw = new FileWriter(arquivo);
        fw.write(xml);
        fw.close();
    }

}