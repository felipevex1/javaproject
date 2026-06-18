package br.com.spectral.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.spectral.db.ConexaoDB;
import br.com.spectral.model.ContaPoupanca;

public class ContaPoupancaDao {

    public List<ContaPoupanca> getLista() {
        List<ContaPoupanca> contas = new ArrayList<>();
        String sql = "SELECT numero, saldo, taxa_rendimento, id_cliente FROM conta_poupanca ORDER BY numero";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ContaPoupanca c = new ContaPoupanca();
                c.setNumero(rs.getInt("numero"));
                c.setSaldo(rs.getDouble("saldo"));
                c.setTaxaRendimento(rs.getDouble("taxa_rendimento"));
                int idCli = rs.getInt("id_cliente");
                if (!rs.wasNull()) {
                    c.setIdCliente(idCli);
                }
                contas.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas poupanca: " + e.getMessage(), e);
        }
        return contas;
    }

    public void gravar(ContaPoupanca conta) {
        String sql = "INSERT INTO conta_poupanca (saldo, taxa_rendimento, id_cliente) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, conta.getSaldo());
            ps.setDouble(2, conta.getTaxaRendimento());
            if (conta.getIdCliente() != null) {
                ps.setInt(3, conta.getIdCliente());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    conta.setNumero(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gravar conta poupanca: " + e.getMessage(), e);
        }
    }

    public void alterar(ContaPoupanca conta) {
        String sql = "UPDATE conta_poupanca SET saldo = ?, taxa_rendimento = ?, id_cliente = ? WHERE numero = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setDouble(1, conta.getSaldo());
            ps.setDouble(2, conta.getTaxaRendimento());
            if (conta.getIdCliente() != null) {
                ps.setInt(3, conta.getIdCliente());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setInt(4, conta.getNumero());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar conta poupanca: " + e.getMessage(), e);
        }
    }

    public void deletar(ContaPoupanca conta) {
        String sql = "DELETE FROM conta_poupanca WHERE numero = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, conta.getNumero());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta poupanca: " + e.getMessage(), e);
        }
    }
}
