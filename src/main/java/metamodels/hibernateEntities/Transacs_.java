package hibernateEntities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transacs.class)
public abstract class Transacs_ {

	public static volatile SingularAttribute<Transacs, String> datetime;
	public static volatile SingularAttribute<Transacs, Integer> summ;
	public static volatile SingularAttribute<Transacs, Integer> idtransacs;
	public static volatile SingularAttribute<Transacs, String> description;
	public static volatile SingularAttribute<Transacs, Accounts> idaccounts;
	public static volatile SingularAttribute<Transacs, Boolean> is_income;

	public static final String DATETIME = "datetime";
	public static final String SUMM = "summ";
	public static final String IDTRANSACS = "idtransacs";
	public static final String DESCRIPTION = "description";
	public static final String IDACCOUNTS = "idaccounts";
	public static final String IS_INCOME = "is_income";

}

