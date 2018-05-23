package dao;

import beans.Professeur;

import java.util.List;

public interface ProfesseurDao {
    void creer( Professeur professeur ) throws DAOException;

    Professeur trouver( long id ) throws DAOException;

    List<Professeur> lister() throws DAOException;

    void supprimer( Professeur Professeur ) throws DAOException;
}