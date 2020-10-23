package main;


import hibernateEntities.Users;
import org.apache.logging.log4j.Logger;
import services.UsersService;
import testclasses.TestUsers1;
import testclasses.TestUsersService;

public class Starter {

	public static void main(String[] args) {

		new TestUsersService();
	}

}
