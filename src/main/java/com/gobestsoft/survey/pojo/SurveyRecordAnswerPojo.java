package com.gobestsoft.survey.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("survey_record_answer")
@Data
public class SurveyRecordAnswerPojo extends Model<SurveyRecordAnswerPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;

    private Integer surveyId;

    private Integer questionId;

    private Integer position;

    private Integer answer;

    private String createTime;

}
