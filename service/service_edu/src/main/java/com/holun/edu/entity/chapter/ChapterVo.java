package com.holun.edu.entity.chapter;

import lombok.Data;
import java.util.List;

@Data
public class ChapterVo {
    private String id;
    private String title;
    //一个章节包含多个小节
    private List<VideoVo> children;
}
