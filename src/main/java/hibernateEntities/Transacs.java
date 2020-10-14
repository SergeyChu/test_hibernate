package hibernateEntities;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "transacs")
public class Transacs {
	
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
	
	@Column(name = "idaccounts", nullable = false, length = 100)
	private int idaccounts;

	@Column(name = "is_income", nullable = false, columnDefinition = "TINYINT(4)")
	private boolean is_income;

		
	public Transacs(String pDatetime, String pDescription, int pSumm, int pIdaccounts, boolean pIs_income){

		this.datetime = pDatetime;
		this.description = pDescription;
		this.summ = pSumm;
		this.idaccounts = pIdaccounts;
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

	public int getIdaccounts() {
		return idaccounts;
	}

	public void setIdaccounts(int idaccounts) {
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
		
}