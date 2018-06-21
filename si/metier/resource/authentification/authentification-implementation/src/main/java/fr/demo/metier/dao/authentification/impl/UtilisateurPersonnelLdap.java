package fr.demo.metier.dao.authentification.impl;

import fr.demo.metier.dto.authentification.UtilisateurPersonnelDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * Created on 19/01/2015
 * For si
 */

@Repository("utilisateurPersonnelLdap")
public class UtilisateurPersonnelLdap {

  private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurPersonnelLdap.class);

  private Hashtable<String, String> env = new Hashtable<>();

  @Value("${ldap.base.search}")
  private String baseSearch;

  @Value("${ldap.provider.url}")
  private String providerUrl;

  public UtilisateurPersonnelDto getByLogin(String login, String motDePasse) {
    setLdapEnvironnement(login, motDePasse);

    LdapContext ctx = null;
    UtilisateurPersonnelDto personnel;
    try {
      // Authenticate the logon user
      ctx = new InitialLdapContext(env, null);
      /**
       * Once the above line was executed successfully, the user is said to be
       * authenticated and the InitialDirContext object will be created.
       */
      personnel = new UtilisateurPersonnelDto();
      ctx.setRequestControls(null);
      NamingEnumeration<?> namingEnum = ctx.search("CN=" + login + "," + this.baseSearch, "(objectclass=user)", getSimpleSearchControls());
      while (namingEnum.hasMore()) {
        SearchResult result = (SearchResult) namingEnum.next();
        Attributes attrs = result.getAttributes();
        personnel.setLoginAnnuaire(attrs.get("cn").get().toString());
        personnel.setNom(attrs.get("sn").get().toString());
        personnel.setPrenom(attrs.get("givenName").get().toString());
        personnel.setId(Long.parseLong(attrs.get("employeeID").get().toString()));
        personnel.setAdresseEmail(attrs.get("mail").get().toString());
        personnel.setNumeroRH(attrs.get("employeeID").get().toString());
      }

      namingEnum.close();
      ctx.close();
    } catch (NamingException e) {
      // Authentication failed, just check on the exception and do something about it.
      personnel = null;
      LOGGER.warn("Authentification échouée !", e);
    } finally {
      if (ctx != null) {
        try {
          ctx.close();
        } catch (NamingException e) {
          LOGGER.warn("Cannot close the ldap context", e);
        }
      }
    }
    return personnel;
  }

  private void setLdapEnvironnement(String login, String motDePasse) {
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.PROVIDER_URL, this.providerUrl);
    // The value of Context.SECURITY_PRINCIPAL must be the logon username with the domain name
    env.put(Context.SECURITY_PRINCIPAL, "CN=" + login + "," + this.baseSearch);
    // The value of the Context.SECURITY_CREDENTIALS should be the user's password
    env.put(Context.SECURITY_CREDENTIALS, motDePasse);
  }

  private SearchControls getSimpleSearchControls() {
    SearchControls searchControls = new SearchControls();
    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    searchControls.setTimeLimit(30000);
    //String[] attrIDs = {"objectGUID"};
    //searchControls.setReturningAttributes(attrIDs);
    return searchControls;
  }

}
