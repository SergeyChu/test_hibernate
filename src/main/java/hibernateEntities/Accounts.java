package hibernateEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Accounts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idaccounts", unique = true, nullable = false)
	private int idaccounts;
	
	@Column(name = "idusers", unique = false, nullable = false)
	private int idusers;
	
	@Column(name = "accdesc", unique = false, nullable = false, length = 100)
	private String accdesc;
	
	//Dummy constructor to let Hibernate work
	Accounts(){
		
	}

	
	public Accounts(int pIdusers, String pAccdesc) {
		
		this.idusers = pIdusers;
		this.accdesc = pAccdesc;
				
	}

	public int getIdusers() {
		return idusers;
	}

	public void setIdusers(int pIdusers) {
		this.idusers = pIdusers;
	}

	public String getAccdesc() {
		return accdesc;
	}

	public void setAccdesc(String pAccdesc) {
		this.accdesc = pAccdesc;
	}

	public int getIdaccounts() {
		return idaccounts;
	}
	
}
