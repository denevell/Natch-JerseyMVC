package org.denevell.natch.web.jerseymvc.threads.io;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class ThreadsOutput {
        
	public ThreadsOutput() {
	}
        private long numOfThreads;
        private List<ThreadOutput> threads = new ArrayList<ThreadOutput>();

        public List<ThreadOutput> getThreads() {
                return threads;
        }

        public void setThreads(List<ThreadOutput> posts) {
                this.threads = posts;
        }

        public long getNumOfThreads() {
                return numOfThreads;
        }

        public void setNumOfThreads(long numOfThreads) {
                this.numOfThreads = numOfThreads;
        }

}