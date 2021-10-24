package com.holun.edu.service;

import com.holun.edu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.holun.edu.entity.chapter.ChapterVo;
import java.util.List;

/**
 * <p>
 * 课程章节 服务类
 * </p>
 *
 * @author holun
 * @since 2021-10-03
 */
public interface EduChapterService extends IService<EduChapter> {

    //查询某个课程包含的所有章节（每个章节中包含的小节也都会一起查出来）
    List<ChapterVo> getChapterByCourseId(String courseId);

    //根据章节id，删除章节
    boolean deleteChapter(String chapterId);

    //根据课程id，删除章节
    void deleteChapterByCourseId(String courseId);
}
