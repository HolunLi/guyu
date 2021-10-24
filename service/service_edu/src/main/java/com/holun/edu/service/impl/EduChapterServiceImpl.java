package com.holun.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.holun.edu.entity.EduChapter;
import com.holun.edu.entity.EduVideo;
import com.holun.edu.entity.chapter.ChapterVo;
import com.holun.edu.mapper.EduChapterMapper;
import com.holun.edu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.holun.edu.service.EduVideoService;
import com.holun.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 课程章节 服务实现类
 * </p>
 *
 * @author holun
 * @since 2021-10-03
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterByCourseId(String courseId) {
        //baseMapper调mapper层的相关方法
        List<ChapterVo> chapterVoList = baseMapper.getChapterByCourseId(courseId);

        return chapterVoList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        //查询要删除的章节，包含的小节数量
        int count = videoService.count(queryWrapper);
        if (count > 0) //若章节包含小节，无法删除
            throw  new GuliException(20001, "不能删除");
        int result = baseMapper.deleteById(chapterId);

        return result > 0;
    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
