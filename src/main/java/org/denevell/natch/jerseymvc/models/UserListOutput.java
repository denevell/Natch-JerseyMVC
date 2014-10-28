package org.denevell.natch.jerseymvc.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserListOutput {
	
	private long numUsers;
	private List<UserOutput> users = new ArrayList<UserOutput>();

	public List<UserOutput> getUsers() {
		return users;
	}

	public void setUsers(List<UserOutput> users) {
		this.users = users;
	}

    public long getNumUsers() {
        return numUsers;
    }

    public void setNumUsers(long num) {
        this.numUsers = num;
    }

}

