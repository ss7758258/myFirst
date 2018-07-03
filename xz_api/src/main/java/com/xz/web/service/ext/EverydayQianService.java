package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.web.bo.everydayQian.X500Bo;
import com.xz.web.mapper.entity.TiQianList;
import com.xz.web.mapper.entity.TiUserQianList;

import java.util.List;

public interface EverydayQianService {

    XZResponseBody<X500Bo> selectEverydayQian();

    XZResponseBody<String> saveEverydayQian();

    int save(TiUserQianList obj);

    List<TiUserQianList> testSelect();

    long countActiveQianList();

    TiQianList randomActiveQianList(int randomNum);
}
