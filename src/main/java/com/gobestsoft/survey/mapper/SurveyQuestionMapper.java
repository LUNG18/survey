package com.gobestsoft.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.survey.pojo.SurveyQuestionPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 问卷题目
 */
public interface SurveyQuestionMapper extends BaseMapper<SurveyQuestionPojo> {


    /**
     * 查询题号
     * @return
     */
    List<Integer> selectPosition(@Param("surveyId") Integer surveyId, @Param("answer") Integer answer);
}
