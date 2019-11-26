package com.app.config;


import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.MaxSizeConfig.MaxSizePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.allowedOrigins("http://localhost:4200")
				.allowedMethods("GET","POST","DELETE");
	}



	@Bean
	public SCryptPasswordEncoder enc()
	{
		return new SCryptPasswordEncoder();
	}


	@Bean
	public Config cacheConfig()
	{
		return new Config().setInstanceName("jwtApp-cache")				// cache instance name
				.addMapConfig(new MapConfig()
						.setName("employee-cache")  		// cache name per module
						.setTimeToLiveSeconds(2000)
						.setMaxSizeConfig(new MaxSizeConfig(200, MaxSizePolicy.FREE_HEAP_SIZE))
						.setEvictionPolicy(EvictionPolicy.LRU)
				);
	}

//	@Bean(name = "encryptorBean")
//	public StringEncryptor stringEncryptor() {
//		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//		config.setPassword("srv");
//		config.setAlgorithm("PBEWithMD5AndDES");
//		config.setKeyObtentionIterations("1000");
//		config.setPoolSize("1");
//		config.setProviderName("SunJCE");
//		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//		config.setStringOutputType("base64");
//		encryptor.setConfig(config);
//		return encryptor;
//	}




}
