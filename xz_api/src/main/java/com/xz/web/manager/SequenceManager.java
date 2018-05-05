package com.xz.web.manager;


import com.xz.web.mapper.entity.SysSeqUid;
import com.xz.web.mapper.ext.SequenceExtMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class SequenceManager {
	
	private static final Logger logger  = Logger.getLogger(SequenceManager.class);
	
	private Map<String, SequenceObject> seqMap = new ConcurrentHashMap<String, SequenceObject>();
	
	@Autowired
	private SequenceExtMapper mapper;
	
	public long getUid(String module) {
		long uid = 0;
		synchronized (SequenceManager.class) {
			SequenceObject so = update(module);
			uid = so.getValue().get();
		}
		return uid;
	}
	
	private SequenceObject update(String type) {
		SequenceObject so = getNewSeq(type);
		if(so != null) {
			return so;
		} else {
			try {
				Thread.sleep(100);
				so = getNewSeq(type);
				if(so != null) {
					return so;
				} else {
					return null;
				}
			} catch (InterruptedException e) {
				logger.error("", e);
			}
			return null;
		}
	}
	
	private SequenceObject getNewSeq(String module) {
		SysSeqUid seq = mapper.selectByModule(module);
		long value = seq.getSeqUid();
		long max = seq.getSeqUid() + seq.getMaxUnit();
		int n = mapper.updateByModule(module, max);
		if(n > 0) {
			SequenceObject so = new SequenceObject();
			so.getMax().addAndGet(max);
			so.getValue().addAndGet(value);
			return so;
		} else {
			return null;
		}
	}
}


class SequenceObject {
	private AtomicLong max = new AtomicLong();
	private AtomicLong value = new AtomicLong();
	
	public AtomicLong getMax() {
		return max;
	}
	public AtomicLong getValue() {
		return value;
	}
	
}
