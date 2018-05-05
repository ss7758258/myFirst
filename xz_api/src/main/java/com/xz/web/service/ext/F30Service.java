package com.xz.web.service.ext;

import com.github.pagehelper.PageInfo;
import com.xz.framework.bean.ajax.YTResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.exception.MessageException;
import com.xz.web.bo.f30.F20Bo;
import com.xz.web.bo.f30.F40Bo;
import com.xz.web.vo.PagerVo;

public interface F30Service {

    YTResponseBody<Boolean> getMyInfo(Weixin weixin) throws MessageException;

    YTResponseBody<PageInfo<F20Bo>> getMyCollection(Weixin weixin, PagerVo pageNum, Integer pageSize) throws MessageException;

    YTResponseBody<Boolean> deleteCollection(Long id, Long feedId, String openId) throws MessageException;

    YTResponseBody<PageInfo<F40Bo>> getMyComments(Weixin weixin, PagerVo obj, Integer pageSize)throws MessageException;

    YTResponseBody<Boolean> setComment(Weixin weixin, Long id)throws MessageException;

    YTResponseBody<Boolean> deleteComment(Weixin weixin, Long id, Long feedId)throws MessageException;

    YTResponseBody<Boolean> setUnComment(Weixin weixin, Long id) throws MessageException;
}
