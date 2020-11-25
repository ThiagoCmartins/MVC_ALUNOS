<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- A linha abaixo faz a importação do código do topo--%>
<c:import url="topo.jsp" />

<div class="alert-success text-center espaco">
    Tela de Mensagens
</div>
<table class="table table-bordered table-striped text-center">
    <thead>
        <tr>
            <th class="text-justify">ID</th>
            <th class="text-justify">RA</th>
            <th class="text-justify">NOME</th>
            <th class="text-justify">CURSO</th>
            <th colspan="1">Operações</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="aluno" items="${alunoDados}">
        <form name="Excluir" method="post" action="AlunosController">
            <tr>
                <td class="align-middle text-justify">${aluno.id}</td>
                <td class="align-middle text-justify">${aluno.ra}</td>
                <td class="align-middle text-justify">${aluno.nome}</td>
                <td class="align-middle text-justify">${aluno.curso}</td>
                
                <td class="align-middle">
                        
                <input type="hidden" name="operacao" value="ConfirmarExclusao" />
                <input type="hidden" name="ra" value="${aluno.ra}" />
                <input
                    type="submit"
                    class="btn btn-danger btn-sm text-center"
                    name="bt_enviar"
                    value="Confirmar Exclusao"/>
                </td>
            </tr>
            
        </form>
    </c:forEach>
</tbody>
</table>

<c:out value="${excluir}" />

<%-- A linha abaixo faz a importação do código do rodapé--%>
<c:import url="rodape.jsp" />