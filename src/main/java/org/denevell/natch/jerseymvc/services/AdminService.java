package org.denevell.natch.jerseymvc.services;

import java.util.ArrayList;
import java.util.List;

import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class AdminService {

  private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);

  public UserListOutput getUsers(String authKey) {
    UserListOutput users = sService
        .target(ManifestVarsListener.getValue("user_service"))
        .path("user").path("list").request()
        .header("AuthKey", authKey).get(UserListOutput.class);
    return users;
  }

  public static class UserListOutput {
    public long numUsers;
    public List<UserOutput> users = new ArrayList<UserOutput>();
    public static class UserOutput {
      public String username;
      public boolean admin;
      public boolean resetPasswordRequest;
      public String recoveryEmail;
    }
  }

}
