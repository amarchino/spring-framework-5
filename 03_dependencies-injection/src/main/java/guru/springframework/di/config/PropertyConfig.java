package guru.springframework.di.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import guru.springframework.di.examplebeans.FakeDataSource;
import guru.springframework.di.examplebeans.FakeJmsBroker;

@Configuration
@PropertySource("classpath:datasource.properties")
@PropertySource("classpath:jms.properties")
public class PropertyConfig {
	
	@Autowired Environment env;
	
	@Value("${guru.username}") String user;
	@Value("${guru.password}") String password;
	@Value("${guru.dburl}") String url;
	
	@Value("${guru.jms.username}") String jmsUser;
	@Value("${guru.jms.password}") String jmsPassword;
	@Value("${guru.jms.url}") String jmsUrl;

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		return propertySourcesPlaceholderConfigurer;
	}
	
	@Bean
	public FakeDataSource fakeDataSource() {
		FakeDataSource fakeDataSource = new FakeDataSource();
		fakeDataSource.setUser(env.getProperty("USERNAME", user));
		fakeDataSource.setPassword(password);
		fakeDataSource.setUrl(url);
		return fakeDataSource;
	}
	
	@Bean
	public FakeJmsBroker fakeJmsBroker() {
		FakeJmsBroker fakeJmsBroker = new FakeJmsBroker();
		fakeJmsBroker.setUser(jmsUser);
		fakeJmsBroker.setPassword(jmsPassword);
		fakeJmsBroker.setUrl(jmsUrl);
		return fakeJmsBroker;
	}
}
