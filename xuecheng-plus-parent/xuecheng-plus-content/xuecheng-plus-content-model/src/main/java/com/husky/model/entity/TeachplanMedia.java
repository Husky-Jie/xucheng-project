package com.husky.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "teachplan_media")
public class TeachplanMedia{
    private static final long serialVersionUID = 1L;
    private String changePeople;
    private Long courseId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    private String createPeople;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String mediaFilename;
    private String mediaId;
    private Long teachplanId;
    
    public TeachplanMedia(TeachplanMedia teachplanMedia) {
        if (Objects.nonNull(teachplanMedia)) {
            this.changePeople=teachplanMedia.changePeople;
            this.courseId=teachplanMedia.courseId;
            this.createDate=teachplanMedia.createDate;
            this.createPeople=teachplanMedia.createPeople;
            this.id=teachplanMedia.id;
            this.mediaFilename=teachplanMedia.mediaFilename;
            this.mediaId=teachplanMedia.mediaId;
            this.teachplanId=teachplanMedia.teachplanId;
        }
    }
}
