package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Correia Martins
 */
public class DBConnection {
    // configurações para conexão com o bando de dados MySQL
    private final String driver = "com.mysql.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/mvc_alunos";
    // credenciais de acesso
    private final String usuario = "root";
    private final String senha = "";
    // variável para armazenar a conexão com o banco de dados
    private static DBConnection conexao = null;
    /**
     * Método que prepara a configuração para a conexão
     *
     * @throws SQLException
     */
    private DBConnection() throws SQLException{
        try{
            Class.forName(driver);
        }catch(ClassNotFoundException e){
            throw new SQLException("driver");
        }
    }
    /**
     * Realiza a conexão, caso tenha sucesso
     *
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, usuario, senha);
        }catch(SQLException e){
            throw new SQLException(e.getMessage());
        }   
        return conn;
    }
     /**
     * Retorna uma instância da conexão para utilização (Singleton)
     *
     * @return Connection
     * @throws SQLException
     */
    public static DBConnection getInstence() throws SQLException{
    if (conexao == null){
        conexao = new DBConnection();
    }
    return conexao;
    }
}
