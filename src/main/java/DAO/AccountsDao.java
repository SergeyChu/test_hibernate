package DAO;

import hibernateEntities.Accounts;


public class AccountsDao extends Dao<Accounts> {

	public AccountsDao(){
		super(Accounts.class, "idaccounts");
	}

}
