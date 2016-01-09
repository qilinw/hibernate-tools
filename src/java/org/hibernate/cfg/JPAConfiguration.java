package org.hibernate.cfg;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.hibernate.boot.Metadata;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;

public class JPAConfiguration extends Configuration {
	
	private Properties properties = new Properties();
	private Metadata metadata = null;
	
	public JPAConfiguration(EntityManagerFactoryBuilderImpl entityManagerFactoryBuilder) {
		metadata = entityManagerFactoryBuilder.getMetadata();
		EntityManagerFactory entityManagerFactory = 
				entityManagerFactoryBuilder.build();
		properties.putAll(entityManagerFactory.getProperties());
	}
	
	public Metadata getMetadata() {
		return metadata;
	}
	
	public Properties getProperties() {
		return properties;
	}
	
}
