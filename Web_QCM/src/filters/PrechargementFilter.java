package filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Etudiant;
import beans.Professeur;
import dao.EtudiantDao;
import dao.ProfesseurDao;
import dao.DAOFactory;

public class PrechargementFilter implements Filter {
    public static final String CONF_DAO_FACTORY      = "daofactory";
    public static final String ATT_SESSION_ETUDIANTS   = "etudiants";
    public static final String ATT_SESSION_PROFESSEURS = "professeurs";

    private EtudiantDao          etudiantDao;
    private ProfesseurDao        professeurDao;

    public void init( FilterConfig config ) throws ServletException {
        /* Récupération d'une instance de nos DAO etudiant et professeur */
        this.etudiantDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getEtudiantDao();
        this.professeurDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) )
                .getProfesseurDao();
    }

    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException,
            ServletException {
        /* Cast de l'objet request */
        HttpServletRequest request = (HttpServletRequest) req;

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /*
         * Si la map des etudiants n'existe pas en session, alors l'utilisateur se
         * connecte pour la première fois et nous devons précharger en session
         * les infos contenues dans la BDD.
         */
        if ( session.getAttribute( ATT_SESSION_ETUDIANTS ) == null ) {
            /*
             * Récupération de la liste des etudiants existants, et enregistrement
             * en session
             */
            List<Etudiant> listeEtudiants = etudiantDao.lister();
            Map<Long,Etudiant> mapEtudiants = new HashMap<Long, Etudiant>();
            for ( Etudiant etudiant : listeEtudiants ) {
                mapEtudiants.put( etudiant.getId_Etudiant(), etudiant );
            }
            session.setAttribute( ATT_SESSION_ETUDIANTS, mapEtudiants );
        }

        /*
         * De même pour la map des professeurs
         */
        if ( session.getAttribute( ATT_SESSION_PROFESSEURS ) == null ) {
            /*
             * Récupération de la liste des professeurs existantes, et
             * enregistrement en session
             */
            List<Professeur> listeProfesseurs = professeurDao.lister();
            Map<Long, Professeur> mapProfesseurs = new HashMap<Long, Professeur>();
            for ( Professeur professeur : listeProfesseurs ) {
                mapProfesseurs.put( professeur.getId_Professeur(), professeur );
            }
            session.setAttribute( ATT_SESSION_PROFESSEURS, mapProfesseurs );
        }

        /* Pour terminer, poursuite de la requête en cours */
        chain.doFilter( request, res );
    }

    public void destroy() {
    }
}