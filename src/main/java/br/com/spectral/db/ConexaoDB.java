package br.com.spectral.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoDB {
    private static final String URL = "jdbc:sqlite:sistema.db";
    private static Connection conexao;

    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(URL);
                criarTabelas();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco: " + e.getMessage(), e);
        }
        return conexao;
    }

    private static void criarTabelas() throws SQLException {
        String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "cpf TEXT NOT NULL)";

        String sqlContaCorrente = "CREATE TABLE IF NOT EXISTS conta_corrente (" +
                "numero INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "saldo REAL NOT NULL DEFAULT 0, " +
                "limite REAL NOT NULL DEFAULT 0, " +
                "id_cliente INTEGER, " +
                "FOREIGN KEY (id_cliente) REFERENCES cliente(id))";

        String sqlContaPoupanca = "CREATE TABLE IF NOT EXISTS conta_poupanca (" +
                "numero INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "saldo REAL NOT NULL DEFAULT 0, " +
                "taxa_rendimento REAL NOT NULL DEFAULT 0, " +
                "id_cliente INTEGER, " +
                "FOREIGN KEY (id_cliente) REFERENCES cliente(id))";

        String sqlContaSalario = "CREATE TABLE IF NOT EXISTS conta_salario (" +
                "numero INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "saldo REAL NOT NULL DEFAULT 0, " +
                "id_cliente INTEGER, " +
                "FOREIGN KEY (id_cliente) REFERENCES cliente(id))";

        String sqlLancamento = "CREATE TABLE IF NOT EXISTS lancamento (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tipo TEXT NOT NULL, " +
                "valor REAL NOT NULL, " +
                "data TEXT NOT NULL, " +
                "hora TEXT NOT NULL, " +
                "conta_numero INTEGER NOT NULL, " +
                "conta_tipo TEXT NOT NULL)";

        try (Statement st = conexao.createStatement()) {
            st.execute(sqlCliente);
            st.execute(sqlContaCorrente);
            st.execute(sqlContaPoupanca);
            st.execute(sqlContaSalario);
            st.execute(sqlLancamento);
        }
    }
}
