package taco.cloud.democlient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

public class RestIngredientService implements IngredientService {
    
    private RestTemplate restTemplate;

    public RestIngredientService(String accessToken) {
        this.restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(org.springframework.http.MediaType.ALL));
        messageConverters.add(converter);
        this.restTemplate.setMessageConverters(messageConverters);
        if (accessToken != null) {
            this.restTemplate
                .getInterceptors()
                .add(getBearerTokenInterceptor(accessToken));
        }
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        ClientHttpRequestInterceptor interceptor = 
            new ClientHttpRequestInterceptor() {

                @Override
                public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                        ClientHttpRequestExecution execution) throws IOException {
                    request.getHeaders().add("Authorization", "Bearer " + accessToken);
                    return execution.execute(request, body);
                }
                
                
            };
        
        return interceptor;
    }

    @Bean
    @RequestScope
    public IngredientService ingredientService(OAuth2AuthorizedClientService clientService) {
        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();

        String accessToken = null;

        if (authentication.getClass()
                .isAssignableFrom(OAuth2AuthenticationToken.class)) {
                    OAuth2AuthenticationToken oauthToken = 
                        (OAuth2AuthenticationToken) authentication;
                    String clientRegistrationId = 
                        oauthToken.getAuthorizedClientRegistrationId();
                    if (clientRegistrationId.equals("taco-admin-client")) {
                        OAuth2AuthorizedClient client = 
                            clientService.loadAuthorizedClient(
                                    clientRegistrationId, oauthToken.getName());
                        accessToken = client.getAccessToken().getTokenValue();
                    }    
        }
        
        return new RestIngredientService(accessToken);        
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return Arrays.asList(restTemplate.getForObject(
                "http://localhost:8080/api/ingredients", 
                Ingredient[].class));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return restTemplate.postForObject(
                "http://localhost:8080/api/ingredients", 
                    ingredient, 
                    Ingredient.class);
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void delete(Ingredient entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAll(Iterable<? extends Ingredient> entities) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterable<Ingredient> findAllById(Iterable<String> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public <S extends Ingredient> Iterable<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

}
