package com.holun.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.holun.edu.entity.EduSubject;
import com.holun.edu.entity.excel.SubjectData;
import com.holun.edu.entity.subject.OneSubject;
import com.holun.edu.listener.SubjectExcelListener;
import com.holun.edu.mapper.EduSubjectMapper;
import com.holun.edu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author holun
 * @since 2021-10-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        InputStream inputStream = null;
        try {
            //获取指向excel表的输入流
            inputStream = file.getInputStream();
            //读取excel表中的数据
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<OneSubject> getAllOneSubject() {
        //baseMapper调mapper层的相关方法
        List<OneSubject> allSubject = baseMapper.getAllOneSubject();

        return allSubject;
    }
}
