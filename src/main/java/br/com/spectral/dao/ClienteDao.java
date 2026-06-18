package br.com.spectral.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import br.com.spectral.model.Cliente;

public class ClienteDao {
    private static String arquivo = "cliente.xml";
    private static List<Cliente> clientes = new ArrayList<Cliente>();

    public List<Cliente> getLista() {
        XStream xs = new XStream();
        File f = new File(arquivo);
        if (!(f.exists())) {
            return new ArrayList<Cliente>();
        }
        clientes = (List<Cliente>) xs.fromXML(f);
        int proximo = 0;
        for (Cliente c : clientes) {
            if (c.getId() > proximo) {
                proximo = c.getId();
            }
        }
        Cliente.setProximoId(proximo + 1);
        return clientes;
    }

    public void gravar(Cliente cliente) throws IOException {
        List<Cliente> lista = getLista();
        if (lista == null) {
            lista = new ArrayList<Cliente>();
        }
        lista.add(cliente);
        XStream xs = new XStream();
        String xml = xs.toXML(lista);
        FileWriter fw = new FileWriter(arquivo);
        fw.write(xml);
        fw.close();
    }

    public void alterar() throws IOException {
        XStream xs = new XStream();
        String xml = xs.toXML(clientes);
        FileWriter fw = new FileWriter(arquivo);
        fw.write(xml);
        fw.close();
    }

    public void deletar(Cliente cliente) throws IOException {
        List<Cliente> lista = getLista();
        lista.removeIf(c -> c.getId().equals(cliente.getId()));
        XStream xs = new XStream();
        String xml = xs.toXML(lista);
        FileWriter fw = new FileWriter(arquivo);
        fw.write(xml);
        fw.close();
    }
}
