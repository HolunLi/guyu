package com.holun.edu.mapper;

import com.holun.edu.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.holun.edu.entity.subject.OneSubject;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author holun
 * @since 2021-10-02
 */
@Mapper
public interface EduSubjectMapper extends BaseMapper<EduSubject> {
    //查询所有的一级分类（一级分类包含的二级分类也会一起查出来）
    List<OneSubject> getAllOneSubject();
}
