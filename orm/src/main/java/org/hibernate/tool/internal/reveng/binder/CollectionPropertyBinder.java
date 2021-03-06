package org.hibernate.tool.internal.reveng.binder;

import org.hibernate.mapping.Collection;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;
import org.hibernate.tool.api.reveng.AssociationInfo;
import org.hibernate.tool.internal.reveng.DefaultAssociationInfo;

class CollectionPropertyBinder extends AbstractBinder {
	
	static CollectionPropertyBinder create(BinderContext binderContext) {
		return new CollectionPropertyBinder(binderContext);
	}
	
	private final PropertyBinder propertyBinder;
	
	private CollectionPropertyBinder(BinderContext binderContext) {
		super(binderContext);
		this.propertyBinder = PropertyBinder.create(binderContext);
	}

    Property bind(
    		String propertyName, 
    		boolean mutable,
			Table table, 
			ForeignKey fk, 
			Collection value, 
			boolean inverseProperty) {
    	AssociationInfo associationInfo = determineAssociationInfo(fk, inverseProperty, mutable);
    	BinderUtils.updateFetchMode(value, associationInfo.getFetch());
        return propertyBinder.bind(table, propertyName, value, associationInfo);
 	}
    
    private DefaultAssociationInfo determineAssociationInfo(
    		ForeignKey foreignKey, 
    		boolean inverseProperty, 
    		boolean mutable) {
    	AssociationInfo origin = BinderUtils
    			.getAssociationInfo(getRevengStrategy(), foreignKey, inverseProperty);
    	DefaultAssociationInfo result = DefaultAssociationInfo.create(null, null, mutable, mutable);
    	if(origin != null){
        	updateAssociationInfo(origin, result);
        }
        return result;
    }
    
    private void updateAssociationInfo(AssociationInfo origin, DefaultAssociationInfo target) {
    	if (origin.getCascade() != null) {
    		target.setCascade(origin.getCascade());
    	} else {
    		target.setCascade("all");
    	}
    	if(origin.getUpdate()!=null) {
    		target.setUpdate(origin.getUpdate());;
    	} 
    	if(origin.getInsert()!=null) {
    		target.setInsert(origin.getInsert());
    	}
    	target.setFetch(origin.getFetch());
    }

}
