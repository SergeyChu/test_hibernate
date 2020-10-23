package hibernateEntities;

import javax.persistence.*;

@Entity
@Table(name = "transacs")
public class Transacs  implements Identifiable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtransacs", unique = true, nullable = false)
	private int idtransacs;
	
	@Column(name = "datetime", nullable = false, length = 100)
	private String datetime;
	
	@Column(name = "description", nullable = false, length = 100)
	private String description;

	@Column(name = "summ", nullable = false, length = 100)
	private int summ;
	
	@Column(name = "is_income", nullable = false, columnDefinition = "TINYINT(4)")
	private boolean is_income;

	@ManyToOne
	@JoinColumn(name="idaccounts", nullable=false)
	private Accounts idaccounts;
		
	public Transacs(Accounts pAccounts, String pDatetime, String pDescription, int pSumm,  boolean pIs_income){

		this.datetime = pDatetime;
		this.description = pDescription;
		this.summ = pSumm;
		this.idaccounts = pAccounts;
		this.is_income = pIs_income;
	}
	
	//Dummy constructor to let Hibernate work
	Transacs() {
		
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSumm() {
		return summ;
	}

	public void setSumm(int summ) {
		this.summ = summ;
	}

	public Accounts getAcounts() {
		return idaccounts;
	}

	public void setIdaccounts(Accounts idaccounts) {
		this.idaccounts = idaccounts;
	}

	public boolean getIs_income() {
		return is_income;
	}

	public void setIs_income(boolean is_income) {
		this.is_income = is_income;
	}

	public int getIdtransacs() {
		return idtransacs;
	}

	@Override
	public int getId() {
		return this.idtransacs;
	}

	public void setId(int pId) {
		this.idtransacs = pId;
	}
}