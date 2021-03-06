package org.hibernate.tool.internal.metadata;

import java.util.Properties;

import org.hibernate.boot.Metadata;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.api.metadata.MetadataDescriptor;
import org.hibernate.tool.api.reveng.ReverseEngineeringConstants;
import org.hibernate.tool.api.reveng.ReverseEngineeringStrategy;
import org.hibernate.tool.internal.reveng.DefaultReverseEngineeringStrategy;
import org.hibernate.tool.internal.reveng.RevengMetadataBuilder;

public class RevengMetadataDescriptor implements MetadataDescriptor {
	
	private ReverseEngineeringStrategy reverseEngineeringStrategy = new DefaultReverseEngineeringStrategy();
    private Properties properties = new Properties();

	public RevengMetadataDescriptor(
			ReverseEngineeringStrategy reverseEngineeringStrategy, 
			Properties properties) {
		this.properties.putAll(Environment.getProperties());
		if (properties != null) {
			this.properties.putAll(properties);
		}
		if (reverseEngineeringStrategy != null) {
			this.reverseEngineeringStrategy = reverseEngineeringStrategy;
		}
		if (this.properties.get(ReverseEngineeringConstants.PREFER_BASIC_COMPOSITE_IDS) == null) {
			this.properties.put(ReverseEngineeringConstants.PREFER_BASIC_COMPOSITE_IDS, true);
		}
	}

	public Properties getProperties() {
		Properties result = new Properties();
		result.putAll(properties);
		return result;
	}
    
	public Metadata createMetadata() {
		return RevengMetadataBuilder
				.create(properties, reverseEngineeringStrategy)
				.build();
	}
	
}
