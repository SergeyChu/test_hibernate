package hibernateEntities;

import java.util.Set;
import javax.persistence.*;


@Entity
@Table(name = "users")
public class Users implements Identifiable {
			
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idusers", unique = true, nullable = false, insertable = false, updatable = false)
	private int idusers;
	
	@Column(name = "username", nullable = false, length = 100)
	private String username;
	
	@Column(name = "userlastname", nullable = false, length = 100)
	private String userlastname;

	@Column(name = "useremail", length = 100)
	private String useremail;
	
	@Column(name = "userphone", length = 100)
	private String userphone;

	@OneToMany(mappedBy="idusers")
	private Set<Accounts> accounts;
	
	//Dummy constructor to let Hibernate work
	Users() {
			
	}
	
	public Users(String pUsername, String pUserlastname, String pUseremail, String pUserphone){

		this.idusers = 0;
		this.username = pUsername;
		this.userlastname = pUserlastname;
		this.useremail = pUseremail;
		this.userphone = pUserphone;		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String pUsername) {
		this.username = pUsername;
	}

	public String getUserlastname() {
		return userlastname;
	}

	public void setUserlastname(String pUserlastname) {
		this.userlastname = pUserlastname;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String pUseremail) {
		this.useremail = pUseremail;
	}

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String pUserphone) {
		this.userphone = pUserphone;
	}

	public int getIdusers() {
		return idusers;
	}

	@Override
	public int getId() {
		return this.idusers;
	}

	public void setId(int pId) {
		this.idusers = pId;
	}
}
