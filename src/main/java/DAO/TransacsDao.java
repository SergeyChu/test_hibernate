package DAO;

import hibernateEntities.Transacs;


public class TransacsDao extends DaoTest<Transacs>{

	public TransacsDao(){

		super(Transacs.class, "idtransacs");
	}

}
