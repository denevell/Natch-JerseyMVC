package org.denevell.natch.jerseymvc.screens.pwrequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.app.template.TemplateController;

@WebServlet("/app/pwrequest")
public class PwRequestServlet extends HttpServlet implements PwRequestVars {
  
  private static final long serialVersionUID = 1L;
  private String resetPwEmail;
  private TemplateController delegate = new TemplateController();
  
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html");
    delegate.createRawTemplate(req, resp, new PwRequestPresenter(this, req).onGet(req));
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resetPwEmail = req.getParameter("resetpw_email");
    try {
      new PwRequestPresenter(this, req).onPost(req, resp);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getRestPwEmail() {
    return resetPwEmail;
  }

}
