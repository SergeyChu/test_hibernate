package hibernateEntities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Accounts implements Identifiable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idaccounts", unique = true, nullable = false)
	private int idaccounts;

	@Column(name = "accdesc", unique = false, nullable = false, length = 100)
	private String accdesc;

	@ManyToOne
	@JoinColumn(name="idusers", nullable=false)
	private Users idusers;

	@OneToMany(mappedBy="idaccounts")
	private Set<Transacs> transacs;

	//Dummy constructor to let Hibernate work
	Accounts(){
		
	}

	
	public Accounts(Users pUsers, String pAccdesc) {

		this.idusers = pUsers;
		this.accdesc = pAccdesc;
				
	}

	public Users getUsers() {
		return idusers;
	}

	public void setUsers(Users pUsersToSet) {
		this.idusers = pUsersToSet;
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

	@Override
	public int getId() {
		return idaccounts;
	}

	public void setId(int pId) {
		this.idaccounts = pId;
	}
}
