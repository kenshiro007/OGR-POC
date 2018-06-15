//package fr.demo.metier.support.rest.client;
//
//import fr.demo.metier.service.cachemanager.CacheManager;
//import fr.demo.metier.service.property.PropertyService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriTemplate;
//
//import java.util.Map;
//
//public abstract class CacheClientRT {
//
//	/** logger **/
//	private static final Logger LOGGER = LoggerFactory
//			.getLogger(CacheClientRT.class);
//
//	private CacheManager cacheManager;
//
//	private PropertyService propertyService;
//
//	private RestTemplate restTemplate;
//
//	public CacheManager getCacheManager() {
//		return cacheManager;
//	}
//
//	public PropertyService getPropertyService() {
//		return propertyService;
//	}
//
//	public String getResourceUrl() {
//		return propertyService.getService(getUrlKey());
//	}
//
//	public RestTemplate getRestTemplate() {
//		return restTemplate;
//	}
//
//	public abstract String getUrlKey();
//
//	public void setCacheManager(CacheManager cacheManager) {
//		this.cacheManager = cacheManager;
//	}
//
//	public void setPropertyService(PropertyService propertyService) {
//		this.propertyService = propertyService;
//	}
//
//	public void setRestTemplate(RestTemplate restTemplate) {
//		this.restTemplate = restTemplate;
//	}
//
//	/**
//	 * Calcule une clé de cache à partir d'une url et de ses paramètres.
//	 * 
//	 * @param url
//	 *            L'url de la ressource (format
//	 *            {@link org.springframework.web.util.UriTemplate}).
//	 * @param urlVariables
//	 *            les paramètres pour l'url
//	 * @return la clé
//	 */
//	private String calculateKey(String url, Map<String, Object> urlVariables) {
//		String result = url;
//		if (urlVariables != null) {
//			result = new UriTemplate(url).expand(urlVariables).getPath();
//		}
//		LOGGER.info("calculateKey : " + result);
//		return result;
//	}
//
//	@SuppressWarnings("unchecked")
//	protected <E> E getForObject(String url, Class<E> clazz,
//			Map<String, Object> urlVariables) {
//
//		String expanded = url;
//		if (cacheManager != null) {
//			expanded = calculateKey(url, urlVariables);
//			E cachedValue = (E) cacheManager.get(expanded);
//			if (cachedValue != null) {
//				return cachedValue;
//			}
//		}
//
//		E response = restTemplate.getForObject(url, clazz, urlVariables);
//
//		if (cacheManager != null) {
//			cacheManager.put(expanded, response);
//		}
//
//		return response;
//	}
//
//	/**
//	 * <p>
//	 * Permet de faire un GET http pour un objet generic.
//	 * <p>
//	 * La réponse est cachée.
//	 * 
//	 * @param url
//	 *            L'url de la ressource (format
//	 *            {@link org.springframework.web.util.UriTemplate}).
//	 * @param typeRef
//	 *            la référence de type générique
//	 *
//	 * @param urlVariables
//	 *            les variable associés à l'url
//	 * @return le résultat de l'appel
//	 */
//	protected <E> E getForParameterizedType(String url,
//			ParameterizedTypeReference<E> typeRef,
//			Map<String, Object> urlVariables) {
//		String expanded = url;
//		if (cacheManager != null) {
//			expanded = calculateKey(url, urlVariables);
//			E cachedValue = (E) cacheManager.get(expanded);
//			if (cachedValue != null) {
//				return cachedValue;
//			}
//		}
//		ResponseEntity<E> responseEntity = restTemplate.exchange(url,
//				HttpMethod.GET, null, typeRef, urlVariables);
//		E response = responseEntity.getBody();
//
//		if (cacheManager != null) {
//			cacheManager.put(expanded, response);
//		}
//		return response;
//	}
//
//	/**
//	 * Permet de supprimer un élément du cache. Si le cache manager n'est pas
//	 * présent, la méthode retourne toujours false.
//	 * 
//	 * @param url
//	 *            L'url de la ressource (format
//	 *            {@link org.springframework.web.util.UriTemplate}).
//	 * @param urlVariables
//	 *            les variable associés à l'url
//	 * @return true si la ressource à été supprimée (si elle existait)
//	 */
//	protected boolean invalidateCache(String url,
//			Map<String, Object> urlVariables) {
//		if (cacheManager != null) {
//			return cacheManager.remove(calculateKey(url, urlVariables));
//		}
//		return false;
//	}
//
//	/**
//	 * Permet d'envoyer un élément en REST POST sur une url pour obtenir un
//	 * objet en retour. Le cache associé est invalidé. En cas d'erreur, le cache
//	 * n'est pas invalidé.
//	 * 
//	 * @param url
//	 *            l'url sur laquelle poster l'élément (format
//	 *            {@link org.springframework.web.util.UriTemplate})
//	 * @param request
//	 *            l'objet à poster
//	 * @param responseType
//	 *            le type du retour
//	 * @param urlVariables
//	 *            les variable associés à l'url
//	 * @return l'objet retourné par le post
//	 */
//	protected <E> E postForObject(String url, Object request,
//			Class<E> responseType, Map<String, Object> urlVariables) {
//
//		ResponseEntity<E> response = restTemplate.postForEntity(url, request,
//				responseType, urlVariables);
//		if (HttpStatus.Series.SUCCESSFUL.equals(response.getStatusCode())) {
//			invalidateCache(url, urlVariables);
//		}
//		return response.getBody();
//	}
//
//	/**
//	 * Permet de poster un objet generic.
//	 * 
//	 * @param url
//	 *            L'url de la ressource (format
//	 *            {@link org.springframework.web.util.UriTemplate}).
//	 * @param elemToPost
//	 *            l'élément à poster
//	 * @param typeRef
//	 *            la référence de type générique
//	 * @param urlVariables
//	 *            les variable associés à l'url
//	 * @return le résultat de l'appel
//	 */
//	protected <E, F> F postForParameterizedType(String url, E elemToPost,
//			ParameterizedTypeReference<F> typeRef,
//			Map<String, Object> urlVariables) {
//		HttpEntity<E> cvHttpEntiry = new HttpEntity<E>(elemToPost);
//		ResponseEntity<F> response = restTemplate.exchange(url,
//				HttpMethod.POST, cvHttpEntiry, typeRef, urlVariables);
//		return response.getBody();
//	}
//
//	/**
//	 * Identique à
//	 * {@link CacheClientRT#postForObject(String, Object, Class, Map)} mais avec
//	 * un appel en PUT. Aucun objet en retour.
//	 * 
//	 * @param url
//	 *            L'url de la ressource (format
//	 *            {@link org.springframework.web.util.UriTemplate}).
//	 * @param request
//	 * @param urlVariables
//	 */
//	protected void put(String url, Object request,
//			Map<String, Object> urlVariables) {
//		restTemplate.put(url, request, urlVariables);
//		invalidateCache(url, urlVariables);
//	}
//
//	
//	 /**
//   * appel delete.
//   * 
//   * @param url
//   *            L'url de la ressource (format
//   *            {@link org.springframework.web.util.UriTemplate}).
//   * @param urlVariables
//   */
//  protected void delete(String url, Map<String, Object> urlVariables) {
//    restTemplate.delete(url, urlVariables);
//    invalidateCache(url, urlVariables);
//  }
//}
