package com.holun.edu.service;

import com.holun.edu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.holun.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author holun
 * @since 2021-10-02
 */
public interface EduSubjectService extends IService<EduSubject> {
    //添加课程分类
    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    //查询所有的一级分类（一级分类包含的二级分类也会一起查出来）
    List<OneSubject> getAllOneSubject();
}
