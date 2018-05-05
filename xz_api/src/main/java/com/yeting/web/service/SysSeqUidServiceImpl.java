package com.yeting.web.service;

import com.yeting.framework.common.base.BaseServiceImpl;
import com.yeting.web.manager.SequenceManager;
import com.yeting.web.mapper.entity.SysSeqUid;
import com.yeting.web.mapper.ext.SequenceExtMapper;
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
