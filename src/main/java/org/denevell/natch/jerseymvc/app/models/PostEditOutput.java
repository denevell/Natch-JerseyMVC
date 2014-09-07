package org.denevell.natch.jerseymvc.app.models;

public class PostEditOutput {

  public PostEditOutput() {}

  private boolean successful;
  private String error = "";
  private String errorMessage;

  public boolean isSuccessful() {
    return successful;
  }
  
  public void setSuccessful(boolean successful) {
    this.successful = successful;
  }

  public String getError() {
    return error;
  }
  
  public void setError(String error) {
    this.error = error;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}