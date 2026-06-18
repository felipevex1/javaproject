package br.com.spectral.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import br.com.spectral.model.ContaSalario;

public class ContaSalarioDao {
    private static String arquivo = "contasalario.xml";
    private static List<ContaSalario> contas = new ArrayList<ContaSalario>();

    public List<ContaSalario> getLista() {
        XStream xs = new XStream();
        File f = new File(arquivo);
        if (!(f.exists())) {
            return new ArrayList<ContaSalario>();
        }
        contas = (List<ContaSalario>) xs.fromXML(f);
        int proximo = 0;
        for (ContaSalario c : contas) {
            if (c.getNumero() > proximo) {
                proximo = c.getNumero();
            }
        }
        ContaSalario.setProximoNumero(proximo + 1);
        return contas;
    }

    public void gravar(ContaSalario conta) throws IOException {
        List<ContaSalario> lista = getLista();
        if (lista == null) {
            lista = new ArrayList<ContaSalario>();
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

    public void deletar(ContaSalario conta) throws IOException {
        List<ContaSalario> lista = getLista();
        lista.removeIf(c -> c.getNumero().equals(conta.getNumero()));
        XStream xs = new XStream();
        String xml = xs.toXML(lista);
        FileWriter fw = new FileWriter(arquivo);
        fw.write(xml);
        fw.close();
    }
}