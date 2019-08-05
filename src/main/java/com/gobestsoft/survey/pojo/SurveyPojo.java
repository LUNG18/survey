package com.gobestsoft.survey.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@TableName("survey_paper")
@Data
public class SurveyPojo extends Model<SurveyPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;

    private String title;

    private String description;

    private Integer status;

    private Integer questionNum;

    private Integer recordNum;

    private String createTime;

    private Integer delFlg;

    @TableField(exist = false)
    private List<SurveyQuestionPojo> questionList;

}
