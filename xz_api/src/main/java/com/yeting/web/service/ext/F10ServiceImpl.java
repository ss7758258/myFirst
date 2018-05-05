package com.yeting.web.service.ext;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.enums.AjaxStatus;
import com.yeting.web.bo.f10.F10Bo;
import com.yeting.web.mapper.ext.ArticleExtMapper;
import com.yeting.web.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class F10ServiceImpl implements F10Service {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleExtMapper articleExtMapper;

    @Override
    public YTResponseBody<PageInfo<F10Bo>> selectMain(Integer startPage, Integer pageSize) {
        YTResponseBody<PageInfo<F10Bo>> responseBody = new YTResponseBody<PageInfo<F10Bo>>();
        PageInfo<F10Bo> page = new PageInfo();
        page.setPageNum(startPage);
        page.setPageSize(pageSize);
        page = articleService.selectMainPageFromRedis(page, startPage, pageSize);
        responseBody.setStatus(AjaxStatus.SUCCESS);
        responseBody.setData(page);
        return responseBody;
    }
}
