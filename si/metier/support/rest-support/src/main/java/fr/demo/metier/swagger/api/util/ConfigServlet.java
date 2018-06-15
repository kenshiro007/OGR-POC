package fr.demo.metier.swagger.api.util;

import com.wordnik.swagger.jaxrs.config.BeanConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class ConfigServlet extends HttpServlet {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServlet.class);

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    String swaggerApiBasepath = "";
    try {
      Context initContext = new InitialContext();
      Context webContext = (Context) initContext.lookup("java:/comp/env");
      swaggerApiBasepath = (String) webContext.lookup("swagger.api.basepath");
      LOGGER.debug("swagger.api.basepath  = {}", swaggerApiBasepath);
    } catch (NamingException e) {
      LOGGER.warn("swagger.api.basepath inconnu : Pb d'accès aux ressources jdni...", e);
    }

    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion("1.0.0");
    beanConfig.setBasePath(swaggerApiBasepath);
    beanConfig.setResourcePackage("fr.demo.metier.rest");
    beanConfig.setScan(true);
  }

}
