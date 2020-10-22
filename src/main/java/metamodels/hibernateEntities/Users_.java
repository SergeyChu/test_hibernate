package hibernateEntities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Users.class)
public abstract class Users_ {

	public static volatile SingularAttribute<Users, String> userlastname;
	public static volatile SingularAttribute<Users, Integer> idusers;
	public static volatile SingularAttribute<Users, String> userphone;
	public static volatile SetAttribute<Users, Accounts> accounts;
	public static volatile SingularAttribute<Users, String> username;
	public static volatile SingularAttribute<Users, String> useremail;

	public static final String USERLASTNAME = "userlastname";
	public static final String IDUSERS = "idusers";
	public static final String USERPHONE = "userphone";
	public static final String ACCOUNTS = "accounts";
	public static final String USERNAME = "username";
	public static final String USEREMAIL = "useremail";

}

