package dao;

import beans.Etudiant;

import java.util.List;

public interface EtudiantDao {
    void creer( Etudiant etudiant ) throws DAOException;

    Etudiant trouver( long id ) throws DAOException;

    List<Etudiant> lister() throws DAOException;

    void supprimer( Etudiant etudiant ) throws DAOException;
}