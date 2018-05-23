package dao;

import java.util.List;

import beans.Admin;


public interface AdminDao {
	Admin trouver( long id ) throws DAOException;
	
	void creer( Admin admin ) throws DAOException;

    

    List<Admin> lister() throws DAOException;

    void supprimer( Admin admin ) throws DAOException;

}
