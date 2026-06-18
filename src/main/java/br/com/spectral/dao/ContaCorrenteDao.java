package br.com.spectral.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.spectral.db.ConexaoDB;
import br.com.spectral.model.ContaCorrente;

public class ContaCorrenteDao {

    public List<ContaCorrente> getLista() {
        List<ContaCorrente> contas = new ArrayList<>();
        String sql = "SELECT numero, saldo, limite, id_cliente FROM conta_corrente ORDER BY numero";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ContaCorrente c = new ContaCorrente();
                c.setNumero(rs.getInt("numero"));
                c.setSaldo(rs.getDouble("saldo"));
                c.setLimite(rs.getDouble("limite"));
                int idCli = rs.getInt("id_cliente");
                if (!rs.wasNull()) {
                    c.setIdCliente(idCli);
                }
                contas.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas correntes: " + e.getMessage(), e);
        }
        return contas;
    }

    public void gravar(ContaCorrente conta) {
        String sql = "INSERT INTO conta_corrente (saldo, limite, id_cliente) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, conta.getSaldo());
            ps.setDouble(2, conta.getLimite());
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
            throw new RuntimeException("Erro ao gravar conta corrente: " + e.getMessage(), e);
        }
    }

    public void alterar(ContaCorrente conta) {
        String sql = "UPDATE conta_corrente SET saldo = ?, limite = ?, id_cliente = ? WHERE numero = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setDouble(1, conta.getSaldo());
            ps.setDouble(2, conta.getLimite());
            if (conta.getIdCliente() != null) {
                ps.setInt(3, conta.getIdCliente());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setInt(4, conta.getNumero());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar conta corrente: " + e.getMessage(), e);
        }
    }

    public void deletar(ContaCorrente conta) {
        String sql = "DELETE FROM conta_corrente WHERE numero = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, conta.getNumero());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta corrente: " + e.getMessage(), e);
        }
    }
}
