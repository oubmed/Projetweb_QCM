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
import dao.DAOFactory;
import forms.CreationEtudiantForm;

@WebServlet("/CreationEtudiant")
public class CreationEtudiant extends HttpServlet {
    public static final String CONF_DAO_FACTORY   = "daofactory";
    public static final String CHEMIN             = "chemin";
    public static final String ATT_ETUDIANT       = "etudiant";
    public static final String ATT_FORM           = "form";
    public static final String SESSION_ETUDIANTS  = "etudiants";

    public static final String VUE_SUCCES         = "/WEB-INF/afficherEtudiant.jsp";
    public static final String VUE_FORM           = "/WEB-INF/creerEtudiant.jsp";
 
    private EtudiantDao          etudiantDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.etudiantDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getEtudiantDao();
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
        CreationEtudiantForm form = new CreationEtudiantForm( etudiantDao );

        /* Traitement de la requête et récupération du bean en résultant */
        Etudiant etudiant = form.creerEtudiant( request, chemin );

        /* Ajout du bean et de l'objet métier à l'objet requête */
        request.setAttribute( ATT_ETUDIANT, etudiant );
        request.setAttribute( ATT_FORM, form );

        /* Si aucune erreur */
        if ( form.getErreurs().isEmpty() ) {
            /* Alors récupération de la map des clients dans la session */
            HttpSession session = request.getSession();
            Map<Long, Etudiant> etudiants = (HashMap<Long,Etudiant>) session.getAttribute( SESSION_ETUDIANTS );
            /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
            if ( etudiants == null ) {
            	etudiants = new HashMap<Long, Etudiant>();
            }
            /* Puis ajout du client courant dans la map */
            etudiants.put( etudiant.getId_Etudiant(), etudiant );
            /* Et enfin (ré)enregistrement de la map en session */
            session.setAttribute( SESSION_ETUDIANTS, etudiants );

            /* Affichage de la fiche récapitulative */
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }
}