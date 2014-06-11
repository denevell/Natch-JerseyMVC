package org.denevell.natch.web.jerseymvc.io;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserList {
	
	private long numUsers;
	private List<User> users = new ArrayList<User>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

    public long getNumUsers() {
        return numUsers;
    }

    public void setNumUsers(long num) {
        this.numUsers = num;
    }

}

