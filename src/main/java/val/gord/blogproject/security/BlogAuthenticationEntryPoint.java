package val.gord.blogproject.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

/**
 * Used by the <code>ExceptionTranslationFilter</code> to return status and a message
 * {@link BasicAuthenticationFilter}.
 * <p>
 */
public class BlogAuthenticationEntryPoint implements AuthenticationEntryPoint{
	@Override
	public void commence(HttpServletRequest request,
						 HttpServletResponse response,
						 AuthenticationException authException)
			throws IOException, ServletException {

		response.sendError(HttpStatus.UNAUTHORIZED.value(),
				"Unauthorized. Please refer to API docs to get an API key");
	}
}
