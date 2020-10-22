package hibernateEntities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Accounts.class)
public abstract class Accounts_ {

	public static volatile SetAttribute<Accounts, Transacs> transacs;
	public static volatile SingularAttribute<Accounts, Users> idusers;
	public static volatile SingularAttribute<Accounts, Integer> idaccounts;
	public static volatile SingularAttribute<Accounts, String> accdesc;

	public static final String TRANSACS = "transacs";
	public static final String IDUSERS = "idusers";
	public static final String IDACCOUNTS = "idaccounts";
	public static final String ACCDESC = "accdesc";

}

