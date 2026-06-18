package br.com.spectral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.spectral.db.ConexaoDB;
import br.com.spectral.model.Cliente;

public class ClienteDao {

    public List<Cliente> getLista() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nome, cpf FROM cliente ORDER BY id";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf")
                );
                clientes.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage(), e);
        }
        return clientes;
    }

    public void gravar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, cpf) VALUES (?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gravar cliente: " + e.getMessage(), e);
        }
    }

    public void alterar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, cpf = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setInt(3, cliente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar cliente: " + e.getMessage(), e);
        }
    }

    public void deletar(Cliente cliente) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, cliente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar cliente: " + e.getMessage(), e);
        }
    }
}
