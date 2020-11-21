package beans;

import java.io.Serializable;
/*
* @author Correia Martins
*/
public class Aluno implements Serializable {
    // criando os atributos do objeto
    private int id;
    private String ra;
    private String nome;
    private String curso;
    
    public Aluno(){
    }
    public int getid(){
       return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getRa(){
       return ra;
    }
    public void setRa(String ra){
        this.ra = ra;
    }
    public String getNome(){
       return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getCurso(){
       return curso;
    }
    public void setCurso(String curso){
        this.curso = curso;
    }
  
}
