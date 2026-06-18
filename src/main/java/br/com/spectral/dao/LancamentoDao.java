package br.com.spectral.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.com.spectral.db.ConexaoDB;
import br.com.spectral.model.Lancamento;
import br.com.spectral.model.LancamentoCredito;
import br.com.spectral.model.LancamentoDebito;

public class LancamentoDao {

    public List<Lancamento> getLista(Integer contaNumero, String contaTipo) {
        List<Lancamento> lancamentos = new ArrayList<>();
        String sql = "SELECT tipo, valor, data, hora FROM lancamento " +
                     "WHERE conta_numero = ? AND conta_tipo = ? ORDER BY id";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, contaNumero);
            ps.setString(2, contaTipo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("tipo");
                    Double valor = rs.getDouble("valor");
                    LocalDate data = LocalDate.parse(rs.getString("data"));
                    LocalTime hora = LocalTime.parse(rs.getString("hora"));
                    Lancamento l;
                    if ("CREDITO".equals(tipo)) {
                        l = new LancamentoCredito(valor);
                    } else {
                        l = new LancamentoDebito(valor);
                    }
                    l.setDataOcorrencia(data);
                    l.setHoraOcorrencia(hora);
                    lancamentos.add(l);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar lancamentos: " + e.getMessage(), e);
        }
        return lancamentos;
    }

    public void gravar(Lancamento lancamento, Integer contaNumero, String contaTipo) {
        String sql = "INSERT INTO lancamento (tipo, valor, data, hora, conta_numero, conta_tipo) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            String tipo = (lancamento instanceof LancamentoCredito) ? "CREDITO" : "DEBITO";
            // Salva sempre o valor positivo (LancamentoDebito.getValor retorna negativo)
            Double valor = Math.abs(lancamento.getValor());
            ps.setString(1, tipo);
            ps.setDouble(2, valor);
            ps.setString(3, lancamento.getDataOcorrencia().toString());
            ps.setString(4, lancamento.getHoraOcorrencia().toString());
            ps.setInt(5, contaNumero);
            ps.setString(6, contaTipo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gravar lancamento: " + e.getMessage(), e);
        }
    }

    public void deletarPorConta(Integer contaNumero, String contaTipo) {
        String sql = "DELETE FROM lancamento WHERE conta_numero = ? AND conta_tipo = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, contaNumero);
            ps.setString(2, contaTipo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar lancamentos: " + e.getMessage(), e);
        }
    }
}
