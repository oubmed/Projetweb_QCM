package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Admin;

public class AdminDaoImpl implements AdminDao {

    private static final String SQL_SELECT        = "SELECT id_Admin, nom, prenom, mdp , FROM Admin ORDER BY id";
    private static final String SQL_SELECT_PAR_ID = "SELECT id_Admin, nom, prenom, mdp , FROM Admin WHERE id = ?";
    private static final String SQL_INSERT        = "INSERT INTO Admin (nom, prenom, mdp ) VALUES (?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Admin WHERE id_Admin = ?";

    private DAOFactory          daoFactory;

    AdminDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode définie dans l'interface adminDao */
    @Override
    public Admin trouver( long id ) throws DAOException {
        return trouver( SQL_SELECT_PAR_ID, id );
    }

    /* Implémentation de la méthode définie dans l'interface adminDao */
    @Override
    public void creer( Admin admin ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    admin.getNom(), admin.getPrenom(),
                    admin.getMdp() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de l'admin, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                admin.setId_Admin( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création del'admin en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    /* Implémentation de la méthode définie dans l'interface adminDao */
    @Override
    public List<Admin> lister() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Admin> admins = new ArrayList<Admin>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                admins.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return admins;
    }

    /* Implémentation de la méthode définie dans l'interface adminDao */
    @Override
    public void supprimer( Admin admin ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, admin.getId_Admin() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression du admin, aucune ligne supprimée de la table." );
            } else {
                admin.setId_Admin( null );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( preparedStatement, connexion );
        }
    }

    /*
     * Méthode générique utilisée pour retourner un admin depuis la base de
     * données, correspondant à la requête SQL donnée prenant en paramètres les
     * objets passés en argument.
     */
    private Admin trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Admin admin = null;

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
                admin = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtilitaire.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return admin;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des admins (un ResultSet) et
     * un bean admin.
     */
    private static Admin map( ResultSet resultSet ) throws SQLException {
    	Admin admin = new Admin();
        admin.setId_Admin( resultSet.getLong( "id_admin" ) );
        admin.setNom( resultSet.getString( "nom" ) );
        admin.setPrenom( resultSet.getString( "prenom" ) );
        admin.setMdp( resultSet.getString( "mdp" ) );
       
        return admin;
    }

}