package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Etudiant;

public class EtudiantDaoImpl implements EtudiantDao {

    private static final String SQL_SELECT        = "SELECT id_Etudiant, nom, prenom, mdp , filiere, adresse, telephone, email, image FROM Etudiant ORDER BY id";
    private static final String SQL_SELECT_PAR_ID = "SELECT id_Etudiant, nom, prenom, mdp , filiere, adresse, telephone, email, image FROM Etudiant WHERE id = ?";
    private static final String SQL_INSERT        = "INSERT INTO Etudiant (nom, prenom, mdp , filiere, adresse, telephone, email, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Etudiant WHERE id_Etudiant = ?";

    private DAOFactory          daoFactory;

    EtudiantDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode définie dans l'interface etudiantDao */
    @Override
    public Etudiant trouver( long id ) throws DAOException {
        return trouver( SQL_SELECT_PAR_ID, id );
    }

    /* Implémentation de la méthode définie dans l'interface etudiantDao */
    @Override
    public void creer( Etudiant etudiant ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    etudiant.getNom(), etudiant.getPrenom(),etudiant.getMdp(),
                    etudiant.getFiliere(),etudiant.getAdresse(), etudiant.getTelephone(),
                    etudiant.getEmail(), etudiant.getImage());
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de l'etudiant, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                etudiant.setId_Etudiant( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création del'etudiant en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    /* Implémentation de la méthode définie dans l'interface etudiantDao */
    @Override
    public List<Etudiant> lister() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Etudiant> etudiants = new ArrayList<Etudiant>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                etudiants.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return etudiants;
    }

    /* Implémentation de la méthode définie dans l'interface etudiantDao */
    @Override
    public void supprimer( Etudiant etudiant ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, etudiant.getId_Etudiant() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression du etudiant, aucune ligne supprimée de la table." );
            } else {
                etudiant.setId_Etudiant( null );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( preparedStatement, connexion );
        }
    }

    /*
     * Méthode générique utilisée pour retourner un etudiant depuis la base de
     * données, correspondant à la requête SQL donnée prenant en paramètres les
     * objets passés en argument.
     */
    private Etudiant trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Etudiant etudiant = null;

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
                etudiant = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return etudiant;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des etudiants (un ResultSet) et
     * un bean etudiant.
     */
    private static Etudiant map( ResultSet resultSet ) throws SQLException {
    	Etudiant etudiant = new Etudiant();
        etudiant.setId_Etudiant( resultSet.getLong( "id_Etudiant" ) );
        etudiant.setNom( resultSet.getString( "nom" ) );
        etudiant.setPrenom( resultSet.getString( "prenom" ) );
        etudiant.setMdp( resultSet.getString( "mdp" ) );
        etudiant.setFiliere( resultSet.getString( "filiere" ) );
        etudiant.setAdresse( resultSet.getString( "adresse" ) );
        etudiant.setTelephone( resultSet.getString( "telephone" ) );
        etudiant.setEmail( resultSet.getString( "email" ) );
        etudiant.setImage( resultSet.getString( "image" ) );
        return etudiant;
    }

}