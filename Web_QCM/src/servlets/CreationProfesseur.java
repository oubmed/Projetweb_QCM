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
import dao.DAOFactory;
import forms.CreationProfesseurForm;

@WebServlet("/CreationProfesseur")
public class CreationProfesseur extends HttpServlet {
    public static final String CONF_DAO_FACTORY     = "daofactory";
    public static final String CHEMIN               = "chemin";
    public static final String ATT_PROFESSEUR       = "Professeur";
    public static final String ATT_FORM             = "form";
    public static final String SESSION_PROFESSEURS  = "professeurs";

    public static final String VUE_SUCCES           = "/WEB-INF/afficherProfesseur.jsp";
    public static final String VUE_FORM             = "/WEB-INF/creerProfesseur.jsp";

    private ProfesseurDao          professeurDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.professeurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getProfesseurDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* À la réception d'une requête GET, simple affichage du formulaire */
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /*
         * Lecture du paramètre 'chemin' passé à la servlet via la déclaration
         * dans le web.xml
         */
        String chemin = this.getServletConfig().getInitParameter( CHEMIN );

        /* Préparation de l'objet formulaire */
        CreationProfesseurForm form = new CreationProfesseurForm( professeurDao );

        /* Traitement de la requête et récupération du bean en résultant */
        Professeur professeur = form.creerProfesseur( request, chemin );

        /* Ajout du bean et de l'objet métier à l'objet requête */
        request.setAttribute( ATT_PROFESSEUR, professeur );
        request.setAttribute( ATT_FORM, form );

        /* Si aucune erreur */
        if ( form.getErreurs().isEmpty() ) {
            /* Alors récupération de la map des clients dans la session */
            HttpSession session = request.getSession();
            Map<Long, Professeur> professeurs = (HashMap<Long,Professeur>) session.getAttribute( SESSION_PROFESSEURS );
            /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
            if ( professeurs == null ) {
            	professeurs = new HashMap<Long, Professeur>();
            }
            /* Puis ajout du client courant dans la map */
            professeurs.put( professeur.getId_Professeur(), professeur );
            /* Et enfin (ré)enregistrement de la map en session */
            session.setAttribute( SESSION_PROFESSEURS, professeurs );

            /* Affichage de la fiche récapitulative */
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }
}