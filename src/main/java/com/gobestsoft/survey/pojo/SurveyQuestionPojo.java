package com.gobestsoft.survey.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@TableName("survey_question")
@Data
public class SurveyQuestionPojo extends Model<SurveyQuestionPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;

    private Integer surveyId;

    private String title;

    private String description;

    private Integer position;

    private Integer type;

    private Integer delFlg;

    private String option;

    @TableField(exist=false)
    private List<Map<String, String>> options;

}
