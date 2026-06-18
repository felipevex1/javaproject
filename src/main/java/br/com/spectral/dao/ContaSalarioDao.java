package br.com.spectral.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.spectral.db.ConexaoDB;
import br.com.spectral.model.ContaSalario;

public class ContaSalarioDao {

    public List<ContaSalario> getLista() {
        List<ContaSalario> contas = new ArrayList<>();
        String sql = "SELECT numero, saldo, id_cliente FROM conta_salario ORDER BY numero";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ContaSalario c = new ContaSalario();
                c.setNumero(rs.getInt("numero"));
                c.setSaldo(rs.getDouble("saldo"));
                int idCli = rs.getInt("id_cliente");
                if (!rs.wasNull()) {
                    c.setIdCliente(idCli);
                }
                contas.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas salario: " + e.getMessage(), e);
        }
        return contas;
    }

    public void gravar(ContaSalario conta) {
        String sql = "INSERT INTO conta_salario (saldo, id_cliente) VALUES (?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, conta.getSaldo());
            if (conta.getIdCliente() != null) {
                ps.setInt(2, conta.getIdCliente());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    conta.setNumero(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gravar conta salario: " + e.getMessage(), e);
        }
    }

    public void alterar(ContaSalario conta) {
        String sql = "UPDATE conta_salario SET saldo = ?, id_cliente = ? WHERE numero = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setDouble(1, conta.getSaldo());
            if (conta.getIdCliente() != null) {
                ps.setInt(2, conta.getIdCliente());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, conta.getNumero());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar conta salario: " + e.getMessage(), e);
        }
    }

    public void deletar(ContaSalario conta) {
        String sql = "DELETE FROM conta_salario WHERE numero = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, conta.getNumero());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta salario: " + e.getMessage(), e);
        }
    }
}
