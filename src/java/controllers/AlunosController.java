package controllers;

import beans.Aluno;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.AlunoModel;

/**
 *
 * @author Correia Martins
 */
public class AlunosController extends HttpServlet {
    // criar uma lista para receber alunos vindos do model
    List<Aluno> alunosDados;
    // Cria um objeto Aluno para a classe toda
    Aluno aluno = new Aluno();
        /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // vamos instanciar o objeto Model do aluno para solicitarmos os dados
            AlunoModel am = new AlunoModel();

            // vamos executar o método listar do objeto AlunoModel (am)
            alunosDados = am.listar();

            // vamos enviar toda essa parada louca para a View
            request.setAttribute("listaAlunos", alunosDados);
            request.getRequestDispatcher("view_listar.jsp").forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute("mensagem", ex.getMessage());
            request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            
            String operacao = request.getParameter("operacao");
            
            switch (operacao) {
            case "Inserir":
                request.setAttribute("mensagem", sql.getMessage());
                request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                break;
            case "Pesquisar":
                request.setAttribute("mensagem", sql.getMessage());
                request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                break;
            case "Editar":
                request.setAttribute("mensagem", sql.getMessage());
                request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                break;
            case "Atualizar":
                request.setAttribute("mensagem", sql.getMessage());
                request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                break;
            case "Excluir":
                request.setAttribute("mensagem", "Excluir");
                request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                break;
            case "ConfirmarExclusao":
                request.setAttribute("mensagem", "Confirmar Exclusão");
                request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                break;
        }
    }
}