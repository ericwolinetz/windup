package org.jboss.windup.engine.visitor.reporter;

import javax.inject.Inject;

import org.jboss.windup.engine.visitor.base.EmptyGraphVisitor;
import org.jboss.windup.graph.dao.NamespaceDaoBean;
import org.jboss.windup.graph.model.meta.xml.NamespaceMeta;
import org.jboss.windup.graph.model.resource.ArchiveEntryResource;
import org.jboss.windup.graph.model.resource.Resource;
import org.jboss.windup.graph.model.resource.XmlResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shows the XML Namespaces used in the application and references to the XML
 * files which contain the namespace. 
 * 
 * @author bradsdavis@gmail.com
 *
 */
public class NamespacesFoundReporter extends EmptyGraphVisitor {

	private static final Logger LOG = LoggerFactory.getLogger(NamespacesFoundReporter.class);
	
	@Inject
	private NamespaceDaoBean namespaceDao;
	
	@Override
	public void run() {
		for(NamespaceMeta namespace : namespaceDao.getAll()) {
			LOG.info("Namespace Found: "+namespace.getURI());
			
			for(XmlResource xml : namespace.getXmlResources()) {
				//report the xml files that contain the namespace...
				Resource resource = xml.getResource();
				if(resource instanceof ArchiveEntryResource) {
					ArchiveEntryResource ar = (ArchiveEntryResource)resource;
					LOG.info(" - "+ar.getArchiveEntry());
				}
			}
		}
	}
}