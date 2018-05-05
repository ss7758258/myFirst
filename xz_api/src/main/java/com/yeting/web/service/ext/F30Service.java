package com.yeting.web.service.ext;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.weixin.Weixin;
import com.yeting.framework.exception.MessageException;
import com.yeting.web.bo.f30.F20Bo;
import com.yeting.web.bo.f30.F40Bo;
import com.yeting.web.vo.PagerVo;

public interface F30Service {

    YTResponseBody<Boolean> getMyInfo(Weixin weixin) throws MessageException;

    YTResponseBody<PageInfo<F20Bo>> getMyCollection(Weixin weixin, PagerVo pageNum, Integer pageSize) throws MessageException;

    YTResponseBody<Boolean> deleteCollection(Long id, Long feedId, String openId) throws MessageException;

    YTResponseBody<PageInfo<F40Bo>> getMyComments(Weixin weixin, PagerVo obj, Integer pageSize)throws MessageException;

    YTResponseBody<Boolean> setComment(Weixin weixin, Long id)throws MessageException;

    YTResponseBody<Boolean> deleteComment(Weixin weixin, Long id, Long feedId)throws MessageException;

    YTResponseBody<Boolean> setUnComment(Weixin weixin, Long id) throws MessageException;
}
