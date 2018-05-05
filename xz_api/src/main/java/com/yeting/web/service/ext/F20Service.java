package com.yeting.web.service.ext;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.weixin.Weixin;
import com.yeting.framework.exception.MessageException;
import com.yeting.web.bo.f20.F10Bo;
import com.yeting.web.bo.f20.F20Bo;
import com.yeting.web.bo.f20.F40Bo;
import com.yeting.web.bo.f20.F70Bo;

public interface F20Service {

    YTResponseBody<F10Bo> selectDetailByArtId(Long artId, Integer pageSize, Weixin weixin, Integer hotCommentPageSize, Integer newCommentPageSize);

    YTResponseBody<F20Bo> saveComment(String feedId, String content, Boolean isAnonymity, Weixin weixin) throws MessageException;

    YTResponseBody<Boolean> updateArtLike(String feedId, String userId);

    YTResponseBody<Boolean> updateCommentLike(String id, String commentId, String userId);

    YTResponseBody<PageInfo<F70Bo>> selectCommentListByArtId(Long artId, String startPage, Integer pageSize, String openId);

    YTResponseBody<F40Bo> saveCollect(String feedId, Weixin weixin) throws MessageException;

    YTResponseBody<Boolean> updateArtShare(String feedId);

    YTResponseBody<Boolean> updateArtUnLike(String feedId, String userId);

    YTResponseBody<Boolean> updateCommentUnLike(String feedId, String commentId, String userId);

    YTResponseBody<F40Bo> updateUnCollect(String feedId, Weixin weixin);

    YTResponseBody<PageInfo<F70Bo>> selectNewCommentListByArtId(Long aLong, String startPage, Integer commentPageSize, String openId, Integer newCommentPageSize);
}
