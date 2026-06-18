package br.com.spectral.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import br.com.spectral.model.ContaPoupanca;

public class ContaPoupancaDao {
    private static String arquivo = "contapoupanca.xml";
    private static List<ContaPoupanca> contas = new ArrayList<ContaPoupanca>();

    public List<ContaPoupanca> getLista() {
        XStream xs = new XStream();
        File f = new File(arquivo);
        if (!(f.exists())) {
            return new ArrayList<ContaPoupanca>();
        }
        contas = (List<ContaPoupanca>) xs.fromXML(f);
        int proximo = 0;
        for (ContaPoupanca c : contas) {
            if (c.getNumero() > proximo) {
                proximo = c.getNumero();
            }
        }
        ContaPoupanca.setProximoNumero(proximo + 1);
        return contas;
    }

    public void gravar(ContaPoupanca conta) throws IOException {
        List<ContaPoupanca> lista = getLista();
        if (lista == null) {
            lista = new ArrayList<ContaPoupanca>();
        }
        lista.add(conta);
        XStream xs = new XStream();
        String xml = xs.toXML(lista);
        FileWriter fw = new FileWriter(arquivo);
        fw.write(xml);
        fw.close();
    }

    public void alterar() throws IOException {
        XStream xs = new XStream();
        String xml = xs.toXML(contas);
        FileWriter fw = new FileWriter(arquivo);
        fw.write(xml);
        fw.close();
    }

    public void deletar(ContaPoupanca conta) throws IOException {
        List<ContaPoupanca> lista = getLista();
        lista.removeIf(c -> c.getNumero().equals(conta.getNumero()));
        XStream xs = new XStream();
        String xml = xs.toXML(lista);
        FileWriter fw = new FileWriter(arquivo);
        fw.write(xml);
        fw.close();
    }
}