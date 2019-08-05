package com.gobestsoft.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.survey.pojo.SurveyRecordAnswerPojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 问卷答案
 */
public interface SurveyRecordAnswerMapper extends BaseMapper<SurveyRecordAnswerPojo> {


    @Select("select answer,count(1) AS num from survey_record_answer" +
            " where survey_id=#{surveyId} and position=#{position} group by answer")
    List<Map<String, Object>> getStatisticData(@Param("surveyId") Integer surveyId, @Param("position") Integer position);

}
