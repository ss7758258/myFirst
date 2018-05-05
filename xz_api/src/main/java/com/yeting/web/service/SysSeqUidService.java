package com.yeting.web.service;

import com.yeting.framework.common.base.BaseService;
import com.yeting.web.mapper.entity.SysSeqUid;

public interface SysSeqUidService extends BaseService<SysSeqUid> {
    long getSQId(String module);
}
