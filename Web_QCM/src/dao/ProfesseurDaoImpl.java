package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Professeur;

public class ProfesseurDaoImpl implements ProfesseurDao {

    private static final String SQL_SELECT        = "SELECT id_Professeur, nom, prenom, mdp , filiere, adresse, telephone, email, image FROM Professeur ORDER BY id";
    private static final String SQL_SELECT_PAR_ID = "SELECT id_Professeur, nom, prenom, mdp , filiere, adresse, telephone, email, image FROM Professeur WHERE id = ?";
    private static final String SQL_INSERT        = "INSERT INTO Professeur (nom, prenom, mdp , filiere, adresse, telephone, email, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Professeur WHERE id_Professeur = ?";

    private DAOFactory          daoFactory;

    ProfesseurDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode définie dans l'interface ProfesseurDao */
    @Override
    public Professeur trouver( long id ) throws DAOException {
        return trouver( SQL_SELECT_PAR_ID, id );
    }

    /* Implémentation de la méthode définie dans l'interface ProfesseurDao */
    @Override
    public void creer( Professeur professeur ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    professeur.getNom(), professeur.getPrenom(),professeur.getMdp(),
                    professeur.getFiliere(),professeur.getAdresse(), professeur.getTelephone(),
                    professeur.getEmail(), professeur.getImage() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de l'Professeur, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                professeur.setId_Professeur( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création del'Professeur en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    /* Implémentation de la méthode définie dans l'interface ProfesseurDao */
    @Override
    public List<Professeur> lister() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Professeur> professeurs = new ArrayList<Professeur>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                professeurs.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return professeurs;
    }

    /* Implémentation de la méthode définie dans l'interface ProfesseurDao */
    @Override
    public void supprimer( Professeur Professeur ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, Professeur.getId_Professeur() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression du Professeur, aucune ligne supprimée de la table." );
            } else {
                Professeur.setId_Professeur( null );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( preparedStatement, connexion );
        }
    }

    /*
     * Méthode générique utilisée pour retourner un Professeur depuis la base de
     * données, correspondant à la requête SQL donnée prenant en paramètres les
     * objets passés en argument.
     */
    private Professeur trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Professeur professeur = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un id) et exécution.
             */
            preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
                professeur = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return professeur;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des Professeurs (un ResultSet) et
     * un bean Professeur.
     */
    private static Professeur map( ResultSet resultSet ) throws SQLException {
    	Professeur professeur = new Professeur();
    	professeur.setId_Professeur( resultSet.getLong( "id_Professeur" ) );
    	professeur.setNom( resultSet.getString( "nom" ) );
    	professeur.setPrenom( resultSet.getString( "prenom" ) );
    	professeur.setMdp( resultSet.getString( "mdp" ) );
    	professeur.setFiliere( resultSet.getString( "filiere" ) );
    	professeur.setAdresse( resultSet.getString( "adresse" ) );
    	professeur.setTelephone( resultSet.getString( "telephone" ) );
    	professeur.setEmail( resultSet.getString( "email" ) );
    	professeur.setImage( resultSet.getString( "image" ) );
        return professeur;
    }

}