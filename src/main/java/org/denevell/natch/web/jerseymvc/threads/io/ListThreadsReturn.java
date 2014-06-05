package org.denevell.natch.web.jerseymvc.threads.io;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class ListThreadsReturn {
        
	public ListThreadsReturn() {
	}
        private long numOfThreads;
        private List<ThreadReturn> threads = new ArrayList<ThreadReturn>();

        public List<ThreadReturn> getThreads() {
                return threads;
        }

        public void setThreads(List<ThreadReturn> posts) {
                this.threads = posts;
        }

        public long getNumOfThreads() {
                return numOfThreads;
        }

        public void setNumOfThreads(long numOfThreads) {
                this.numOfThreads = numOfThreads;
        }

}