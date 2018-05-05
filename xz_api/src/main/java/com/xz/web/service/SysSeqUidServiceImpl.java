package com.xz.web.service;

import com.xz.framework.common.base.BaseServiceImpl;
import com.xz.web.manager.SequenceManager;
import com.xz.web.mapper.entity.SysSeqUid;
import com.xz.web.mapper.ext.SequenceExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysSeqUidServiceImpl extends BaseServiceImpl<SysSeqUid> implements SysSeqUidService {

    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private SequenceExtMapper sequenceExtMapper;

    @Override
    public long getSQId(String module) {
        return sequenceManager.getUid(module);
    }
}
