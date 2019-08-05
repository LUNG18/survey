package com.gobestsoft.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.survey.pojo.SurveyPojo;
import org.apache.ibatis.annotations.Update;

/**
 * 问卷调查
 */
public interface SurveyMapper extends BaseMapper<SurveyPojo> {

    @Update("update survey_paper set record_num=record_num+1,status=#{status} where id=#{id}")
    Integer updateAllById(SurveyPojo surveyPojo);

}
