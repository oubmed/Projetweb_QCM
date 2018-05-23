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

import beans.Etudiant;
import dao.EtudiantDao;
import dao.DAOException;
import dao.DAOFactory;

@WebServlet("/SuppressionEtudiant")
public class SuppressionEtudiant extends HttpServlet {
    public static final String CONF_DAO_FACTORY   = "daofactory";
    public static final String PARAM_ID_ETUDIANT  = "idEtudaint";
    public static final String SESSION_ETUDIANTS  = "etudiants";

    public static final String VUE                = "/listeEtudiants";

    private EtudiantDao          etudiantDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.etudiantDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getEtudiantDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Récupération du paramètre */
        String idEtudiant = getValeurParametre( request, PARAM_ID_ETUDIANT );
        Long id = Long.parseLong( idEtudiant );

        /* Récupération de la Map des etudiants enregistrés en session */
        HttpSession session = request.getSession();
        Map<Long, Etudiant>  etudiants = (HashMap<Long, Etudiant>) session.getAttribute( SESSION_ETUDIANTS );

        /* Si l'id du etudiant et la Map des etudiants ne sont pas vides */
        if ( id != null && etudiants != null ) {
            try {
                /* Alors suppression du etudiant de la BDD */
            	etudiantDao.supprimer( etudiants.get( id ) );
                /* Puis suppression du etudiant de la Map */
            	etudiants.remove( id );
            } catch ( DAOException e ) {
                e.printStackTrace();
            }
            /* Et remplacement de l'ancienne Map en session par la nouvelle */
            session.setAttribute( SESSION_ETUDIANTS, etudiants );
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