package com.briup.crmcustomer.mapper;

import com.briup.crmcustomer.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.briup.crmcustomer.entity.ext.ActivityExt;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author briup
 * @since 2022-11-22
 */
public interface ActivityMapper extends BaseMapper<Activity> {
    List<ActivityExt> getAll(Long Actid);
}
