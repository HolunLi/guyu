package com.holun.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.holun.edu.entity.EduSubject;
import com.holun.edu.entity.excel.SubjectData;
import com.holun.edu.service.EduSubjectService;
import com.holun.servicebase.exceptionhandler.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener没有交给spring管理,所以不能使用@Autuwired注解，注入subjectService对象
    private EduSubjectService subjectService;

    public SubjectExcelListener() {}

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //一行一行的读取excel表中的数据，每次读取有两个值，第一个值是一级分类，第二个值是二级分类
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null) {
            throw new GuliException(20001, "excel表中没有数据!");
        }

        //添加一级分类
        EduSubject oneSubject = this.existOneSubject(subjectData.getOneSubjectName());
        if(oneSubject == null) { //没有相同一级分类，将添加到edu_subject表中
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());//一级分类名称
            subjectService.save(oneSubject);
        }

        //获取一级分类id值
        String pid = oneSubject.getId();
        //添加二级分类
        EduSubject twoSubject = this.existTwoSubject(subjectData.getTwoSubjectName(), pid);
        if(twoSubject == null) {  //没有相同二级分类，将添加到edu_subject表中
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName()); //二级分类名称
            subjectService.save(twoSubject);
        }
    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    /**
     * 读取数据完毕后，执行的操作，该方法用不到
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
