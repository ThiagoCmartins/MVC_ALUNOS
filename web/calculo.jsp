<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="erro.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Calculando...</title>
    </head>
    <body>
        <%
            String txt_peso, txt_altura;
            float peso = 0F, altura = 0F, imc = 0F;

            txt_peso = request.getParameter("peso");
            txt_altura = request.getParameter("altura");

            if (!txt_peso.trim().equals("")) {
                peso = Float.parseFloat(txt_peso);
            }
            if (!txt_altura.trim().equals("")) {
                altura = Float.parseFloat(txt_altura);
            }
            if (peso <= 0 || altura <= 0) {
                out.print("Pesso Ou Altura Incorretos.");
            } else {
                imc = (peso / (altura * altura));
                out.print("IMC foi: " + imc + "<br>");
            }
            if (imc > 0 & imc <= 18) {
                out.print("MAGREZA");
            } else if (imc > 18 & imc <= 24) {
                out.print("NORMAL");
            } else if (imc >= 25 & imc <= 29) {
                out.print("SOBREPESO");
            } else if (imc >= 30 & imc <= 39) {
                out.print("OBESIDADE");
            } else if (imc >= 40) {
                out.print("OBESIDADE GRAVE");
            }
        %>     
    </body>
</html>