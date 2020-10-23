package DAO;

import hibernateEntities.Transacs;


public class TransacsDao extends Dao<Transacs> {

	public TransacsDao(){
		super(Transacs.class, "idtransacs");
	}

}
