/*
 * Created on 14-Feb-2005
 *
 */
package org.hibernate.tool.ant;

import org.apache.tools.ant.BuildException;
import org.hibernate.tool.api.export.Exporter;
import org.hibernate.tool.api.export.ExporterConstants;
import org.hibernate.tool.api.export.ExporterFactory;
import org.hibernate.tool.api.export.ExporterType;
import org.hibernate.tool.util.ReflectionUtil;

/**
 * @author max
 *
 */
public class GenericExporterTask extends ExporterTask {

	public GenericExporterTask(HibernateToolTask parent) {
		super(parent);
	}

	String templateName;
	String exporterClass;
	String filePattern;
	String forEach;
	
	/**
	 * The FilePattern defines the pattern used to generate files.
	 * @param filePattern
	 */
	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;
	}
	
	public void setForEach(String forEach) {
		this.forEach = forEach;
	}
	
	public void setTemplate(String templateName) {
		this.templateName = templateName;
	}
	
	public void setExporterClass(String exporterClass) {
		this.exporterClass = exporterClass;
	}
	
	protected Exporter createExporter() {
		if (exporterClass == null) {
			return ExporterFactory.createExporter(ExporterType.GENERIC);
		} else {
			try {
				return (Exporter) ReflectionUtil.classForName(exporterClass).newInstance();
			} catch (ClassNotFoundException e) {
				throw new BuildException("Could not find custom exporter class: " + exporterClass, e);
			} catch (InstantiationException e) {
				throw new BuildException("Could not create custom exporter class: " + exporterClass, e);
			} catch (IllegalAccessException e) {
				throw new BuildException("Could not access custom exporter class: " + exporterClass, e);
			}
		}		
	}
	
	protected Exporter configureExporter(Exporter exp) {
		super.configureExporter(exp);
		if (templateName != null) {
			exp.getProperties().put(ExporterConstants.TEMPLATE_NAME, templateName);
		}
		if (filePattern != null) {
			exp.getProperties().put(ExporterConstants.FILE_PATTERN, filePattern);
		}
		if (forEach != null) {
			exp.getProperties().put(ExporterConstants.FOR_EACH, forEach);
		}
		return exp;
	}

	public String getName() {
		StringBuffer buf = new StringBuffer("generic exporter");
		if(exporterClass!=null) {
			buf.append( "class: " + exporterClass);
		}
		if(templateName!=null) {
			buf.append( "template: " + templateName);
		}
		return buf.toString();
	}
}
