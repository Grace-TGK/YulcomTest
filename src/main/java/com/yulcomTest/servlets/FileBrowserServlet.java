package com.yulcomTest.servlets;

import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileBrowserServlet
 */
@WebServlet("/FileBrowserServlet")
public class FileBrowserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileBrowserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Spécifions le chemin du répertoire distant
		    String remoteDirectoryPath = "/home/ubuntu/mon_repertoire";

		    // Accédons au répertoire distant
		    File directory = new File(remoteDirectoryPath);
		    File[] files = directory.listFiles();

		    // Génération d'une réponse HTML pour afficher les fichiers et répertoires
		    StringBuilder htmlResponse = new StringBuilder();
		    htmlResponse.append("<ul>");
		    for (File file : files) {
		        htmlResponse.append("<li>").append(file.getName()).append("</li>");
		    }
		    htmlResponse.append("</ul>");

		    // Réponse dans le flux de sortie
		    response.getWriter().println(htmlResponse.toString());
}

	
	protected void doDownload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtenons le nom du fichier à télécharger à partir de la requête
        String fileName = request.getParameter("file");

        // Spécifions le chemin complet du fichier sur le serveur
        String filePath = "/home/ubuntu/mon_repertoire/" + fileName;
        File file = new File(filePath);

        // Configurons les en-têtes de la réponse pour permettre le téléchargement du fichier
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        // Copions le contenu du fichier dans le flux de sortie de la réponse
        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
    }
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
