package servlets;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Professeur;
import dao.ProfesseurDao;
import dao.DAOException;
import dao.DAOFactory;




@WebServlet("/SuppresionProfesseur")
public class SuppressionProfesseur extends HttpServlet {
    public static final String CONF_DAO_FACTORY     = "daofactory";
    public static final String PARAM_ID_PROFESSEUR  = "idProfesseur";
    public static final String SESSION_PROFESSEURS  = "professeurs";

    public static final String VUE                  = "/listeProfesseurs";

    private ProfesseurDao          professeurDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.professeurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getProfesseurDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Récupération du paramètre */
        String idProfesseur = getValeurParametre( request, PARAM_ID_PROFESSEUR );
        Long id = Long.parseLong( idProfesseur );

        /* Récupération de la Map des professeurs enregistrés en session */
        HttpSession session = request.getSession();
        Map<Long, Professeur>  professeurs = (HashMap<Long, Professeur>) session.getAttribute( SESSION_PROFESSEURS );

        /* Si l'id du professeur et la Map des professeurs ne sont pas vides */
        if ( id != null && professeurs != null ) {
            try {
                /* Alors suppression du professeur de la BDD */
            	professeurDao.supprimer( professeurs.get( id ) );
                /* Puis suppression du professeur de la Map */
            	professeurs.remove( id );
            } catch ( DAOException e ) {
                e.printStackTrace();
            }
            /* Et remplacement de l'ancienne Map en session par la nouvelle */
            session.setAttribute( SESSION_PROFESSEURS, professeurs );
        }

        /* Redirection vers la fiche récapitulative */
        response.sendRedirect( request.getContextPath() + VUE );
    }

    /*
     * Méthode utilitaire qui retourne null si un paramètre est vide, et son
     * contenu sinon.
     */
    private static String getValeurParametre( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}