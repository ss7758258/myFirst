package com.xz.web.service;

import com.xz.framework.common.base.BaseService;
import com.xz.web.mapper.entity.SysSeqUid;

public interface SysSeqUidService extends BaseService<SysSeqUid> {
    long getSQId(String module);
}
