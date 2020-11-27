package models;

// essa classe será responsável pelo CRUD
import beans.Aluno;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBConnection;

public class AlunoModel implements Serializable {

    // declarando os atributos principais
    private Connection conexao = null;
    private String status;

    // método construtor
    // toda vez que a classe AlunoModel for instanciada,
    // o construtor fará automaticamente a conexão com o banco
    public AlunoModel() throws SQLException {
        this.conexao = DBConnection.getInstance().getConnection();
    }

    // implementar os métodos do CRUD
    // método inserir - Create (insert)
    public void inserir(Aluno aluno) {
        try {
            String sql = "INSERT INTO alunos (ra, nome, curso) VALUES (?,?,?)";
            try (
                    
                PreparedStatement ps = conexao.prepareStatement(sql)) {
                
                    ps.setString(1, aluno.getRa());
                    ps.setString(2, aluno.getNome());
                    ps.setString(3, aluno.getCurso());
                
                    ps.execute();
                    ps.close();
            }
            conexao.close(); // fecha a conexão com o banco

            // mensagem para o usuário (deu certo)
            this.status = "Aluno [" + aluno.getNome() + "] inserido com sucesso!";

        } catch (SQLException ex) {
            // se houve erro, vamos avisar o usuário
            this.status = "Erro ao inserir o aluno [" + ex.getMessage() + "]";
        }
    }
    // métodos de listar e pesquisar (Read - select)
    public List<Aluno> listar() {
        // variável para receber a lista de alunos originadas do banco
        List<Aluno> alunos = new ArrayList();

        // tratamento de erros
        try {
            String sql = "SELECT * FROM alunos ORDER BY nome ASC;";
            try (
                    //preparar os dados (consulta ou query)
                    // e envia para o banco de dados
                    PreparedStatement ps = conexao.prepareStatement(sql);
                    // declarar uma variável para RECEBER os dados (vetor)
                    ResultSet rs = ps.executeQuery()) { // Cursor do Android

                // vamos percorrer (laço) os registros retornados (se houver)
                // para transformar os dados TABULARES (Relacional) em objetos
                while (rs.next()) { //enquanto houver próximo
                    Aluno aluno = new Aluno();
                    aluno.setId(rs.getInt("id"));
                    aluno.setRa(rs.getString("ra"));
                    aluno.setNome(rs.getString("nome"));
                    aluno.setCurso(rs.getString("curso"));

                    // colocar o objeto que foi "populado ou alimentado"
                    // na nossa lista de alunos (List<Aluno>)
                    alunos.add(aluno);
                }
                // fechamento das instâncias rs e ps
                rs.close();
                ps.close();
            }
            // vamos retornar a lista de objetos
            return alunos;
            
        } catch (SQLException ex) {
            throw new RuntimeException("falha ao listar.", ex);
        }
    }

    public List<Aluno> pesquisar(Aluno aluno, String tipo) {
        List<Aluno> alunos = new ArrayList();
        PreparedStatement ps = null;
        String sql = new String();
        
        try{
            switch(tipo){     
                case "ra":
                    sql = "SELECT * FROM alunos WHERE ra=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, aluno.getRa());
                break;
                
                case "nome":
                    sql = "SELECT * FROM alunos WHERE nome LIKE ? ORDER BY nome ASC";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, "%" + aluno.getNome() + "%");

                break;
                
                case "curso":
                    sql = "SELECT * FROM alunos WHERE curso LIKE ? ORDER BY nome ASC";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, "%" + aluno.getCurso() + "%");
                break;
            }
                ResultSet rs = ps.executeQuery(); 
                while (rs.next()) { 
                    aluno = new Aluno();
                    aluno.setId(rs.getInt("id"));
                    aluno.setRa(rs.getString("ra"));
                    aluno.setNome(rs.getString("nome"));
                    aluno.setCurso(rs.getString("curso"));
                    alunos.add(aluno);
                }    
                rs.close();
                ps.close();
                return alunos;

        }catch(SQLException ex){
            throw new RuntimeException("Falha ao pesquisar");
        }
    }
    
    public void Editar(Aluno aluno) { 
        //n usei
    }

    public void atualizar(Aluno aluno) {
        try {
            String sql = "UPDATE alunos SET nome=?, curso=? WHERE ra=?";
            try (
                PreparedStatement ps = conexao.prepareStatement(sql)) {
                
                ps.setString(1, aluno.getNome());
                ps.setString(2, aluno.getCurso());
                ps.setString(3, aluno.getRa());

                ps.executeUpdate();
                ps.close();
            }
            conexao.close(); 
            this.status = "Aluno [" + aluno.getNome() + "] atualizar com sucesso!";
        } catch (SQLException ex) {
            this.status = "Erro ao atualizar o aluno [" + ex.getMessage() + "]";  
        }   
    }
    
    // método para excluir um registro (Delete - delete)
    public void excluir(Aluno aluno) {
        try {
            String sql = "DELETE FROM alunos WHERE ra=?";
            try (
                PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setString(1, aluno.getRa());
                ps.execute();
                ps.close();
            }
            conexao.close(); 
            this.status = "Aluno [" + aluno.getNome() + "] excluido com sucesso!";
        } catch (SQLException ex) {
            this.status = "Erro ao excluir o aluno [" + ex.getMessage() + "]";
        }
    }

    // método que retorna um texto quando chamamos o modelo.toString()
    @Override
    public String toString() {
        return status;
    }
}
