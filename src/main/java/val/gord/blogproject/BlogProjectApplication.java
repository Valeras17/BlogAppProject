package val.gord.blogproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import val.gord.blogproject.config.BlogJWTConfig;

@SpringBootApplication
@EnableConfigurationProperties(BlogJWTConfig.class)
public class BlogProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogProjectApplication.class, args);
	}

}
