package org.hibernate.tool.internal.reveng.binder;

import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.tool.api.reveng.ReverseEngineeringConstants;
import org.hibernate.tool.api.reveng.ReverseEngineeringStrategy;

abstract class AbstractBinder {
	
	final BinderContext binderContext;
	
	AbstractBinder(BinderContext binderContext) {
		this.binderContext = binderContext;
	}
	
	MetadataBuildingContext getMetadataBuildingContext() {
		return binderContext.metadataBuildingContext;
	}
	
	InFlightMetadataCollector getMetadataCollector() {
		return binderContext.metadataCollector;
	}
	
	ReverseEngineeringStrategy getRevengStrategy() {
		return binderContext.revengStrategy;
	}
	
	String getDefaultCatalog() {
		return binderContext.properties.getProperty(AvailableSettings.DEFAULT_CATALOG);
	}
	
	String getDefaultSchema() {
		return binderContext.properties.getProperty(AvailableSettings.DEFAULT_SCHEMA);
	}
	
	Boolean preferBasicCompositeIds() {
		return (Boolean)binderContext.properties.get(ReverseEngineeringConstants.PREFER_BASIC_COMPOSITE_IDS);
	}
	
}
