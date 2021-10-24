package com.holun.edu.mapper;

import com.holun.edu.entity.EduChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.holun.edu.entity.chapter.ChapterVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程章节 Mapper 接口
 * </p>
 *
 * @author holun
 * @since 2021-10-03
 */
@Mapper
public interface EduChapterMapper extends BaseMapper<EduChapter> {
    List<ChapterVo> getChapterByCourseId(String courseId);
}
