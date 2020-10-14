package DAO;

import hibernateEntities.Accounts;


public class AccountsDao extends DaoTest<Accounts>{

	public AccountsDao(){

		super(Accounts.class, "idaccounts");
	}

}
