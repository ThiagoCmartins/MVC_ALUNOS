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
 * @author souza
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
            throws ServletException, IOException {
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // pega o valor enviado na variável "operacao"
        String operacao = request.getParameter("operacao");

        // criar um menu de opções com a estrutura de seleção switch
        switch (operacao) {
            case "Inserir":
                try {
                    
                    int validar = (Integer.parseInt(request.getParameter("ra")));
                    AlunoModel am = new AlunoModel();
                    //String valordigitado = request.getParameter("ra");
                    
                    if(request.getParameter("nome").trim().length() != 0 && 
                       request.getParameter("curso").trim().length() != 0 &&
                       request.getParameter("ra").trim().length() != 0){
                            
                            aluno.setRa(request.getParameter("ra"));
                            alunosDados = am.pesquisar(aluno, "ra");
                                 
                            aluno.setNome(request.getParameter("nome"));
                            aluno.setCurso(request.getParameter("curso"));    
                        
                        if(alunosDados.isEmpty()){          
                           
                            am.inserir(aluno);
         
                            request.setAttribute("mensagem", am.toString());
                            request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                            
                        }else{
                            request.setAttribute("mensagem", "Erro RA [Aluno já existe]");
                            request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                            }           
                
                }else{
                        request.setAttribute("mensagem", "Erro [campos Vazios]");
                        request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                    }
                } catch (SQLException sql) {
                    request.setAttribute("mensagem", sql.getMessage());
                    request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                
                }catch (NumberFormatException validar){
                         request.setAttribute("mensagem", "Erro RA [Didite apenas números]");
                         request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                }
                break;

            case "Pesquisar":
                String valorDigitado = request.getParameter("valor");
                
                if(valorDigitado.trim().length() == 0){
                    request.setAttribute("mensagem", "Erro [campos Vazios]");
                    request.getRequestDispatcher("view_pesquisar.jsp").forward(request, response);
                }
                
                try {              
                    AlunoModel am = new AlunoModel();
                    switch (request.getParameter("tipo")) {
                        case "ra":
                            aluno.setRa(valorDigitado.trim());
                            break;
                        case "nome":
                            aluno.setNome(valorDigitado.trim());
                            break;
                        case "curso":
                            aluno.setCurso(valorDigitado.trim());
                            break;
                    }

                    alunosDados = am.pesquisar(aluno, request.getParameter("tipo"));
                    
                    if (!alunosDados.isEmpty()){
                        request.setAttribute("listaAlunos", alunosDados);                   
                        request.getRequestDispatcher("view_listar.jsp").forward(request, response);
                    }
                    else{
                        request.setAttribute("mensagem", "Erro [Aluno não encontrado]");
                        request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                        
                    }
                } catch (SQLException sql) {
                    request.setAttribute("mensagem", sql.getMessage());
                    request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                }
                break;

            case "Editar":
                try {
                        AlunoModel am = new AlunoModel();
                        aluno.setRa(request.getParameter("ra"));
                        alunosDados = am.pesquisar(aluno, "ra");               
                    
                    request.setAttribute("alunoDados", alunosDados);
                    request.getRequestDispatcher("view_editar.jsp").forward(request, response);
                    
                } catch (SQLException sql) {
                    request.setAttribute("mensagem", sql.getMessage());
                    request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                }
                break;
                
            case "Atualizar":
                try{
                    if(request.getParameter("nome").trim().length() != 0 && 
                       request.getParameter("curso").trim().length() != 0){
                                            
                        AlunoModel am = new AlunoModel();          
                        
                        aluno.setRa(request.getParameter("ra"));
                        aluno.setNome(request.getParameter("nome"));
                        aluno.setCurso(request.getParameter("curso"));
                    
                        am.atualizar(aluno);
                    
                        request.setAttribute("mensagem", am.toString());
                        request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                        
                    }
                    else{
                        request.setAttribute("mensagem", "Erro [Campos vazios]");
                        request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                    }
                    
                }catch(SQLException sql){
                    request.setAttribute("mensagem", sql.getMessage());
                    request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                }
                break;
                
            case "Excluir":
                try{                   
                    AlunoModel am = new AlunoModel();
                    aluno.setRa(request.getParameter("ra"));
                    alunosDados = am.pesquisar(aluno, "ra");               
                    
                    request.setAttribute("alunoDados", alunosDados);
                    request.getRequestDispatcher("view_excluir.jsp").forward(request, response); 
                
                }catch(SQLException sql){
                    request.setAttribute("mensagem", sql.getMessage());
                    request.getRequestDispatcher("view_mensagem.jsp").forward(request, response); 
                }
                break;
                
            case "ConfirmarExclusao":
                try{
                    AlunoModel am = new AlunoModel();          
                    alunosDados = am.pesquisar(aluno, "ra");
                    aluno.setRa(request.getParameter("ra"));
                    
                    am.excluir(aluno);
                    
                    request.setAttribute("mensagem", am.toString());
                    request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                    
                }catch(SQLException sql){
                    request.setAttribute("mensagem", "Confirmar Exclusão");
                    request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                }
                break;
        }
    }
}