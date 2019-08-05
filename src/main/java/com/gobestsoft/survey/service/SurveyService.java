package com.gobestsoft.survey.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.survey.mapper.SurveyMapper;
import com.gobestsoft.survey.mapper.SurveyQuestionMapper;
import com.gobestsoft.survey.mapper.SurveyRecordAnswerMapper;
import com.gobestsoft.survey.pojo.SurveyPojo;
import com.gobestsoft.survey.pojo.SurveyQuestionPojo;
import com.gobestsoft.survey.pojo.SurveyRecordAnswerPojo;
import com.gobestsoft.survey.util.DateUtil;
import com.gobestsoft.survey.util.ToolUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 问卷调查
 */
@Service
public class SurveyService {

    @Resource
    private SurveyMapper surveyMapper;

    @Resource
    private SurveyQuestionMapper surveyQuestionMapper;

    @Resource
    private SurveyRecordAnswerMapper surveyRecordAnswerMapper;


    /**
     * 按照条件获取问卷列表
     * @param page
     * @param title
     * @param status
     * @return
     */
    public List<SurveyPojo> selectByCondition(Page<SurveyPojo> page, String title, Integer status) {
        Wrapper<SurveyPojo> wrapper = new EntityWrapper<>();
        wrapper.eq(ToolUtil.isNotEmpty(status),"status",status)
                .like("title",title)
                .eq("del_flg",0)
                .orderBy("create_time",false);
        return surveyMapper.selectPage(page, wrapper);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    public SurveyPojo selectById(Integer id){
        SurveyPojo surveyPojo = surveyMapper.selectById(id);
        Wrapper<SurveyQuestionPojo> wrapper = new EntityWrapper<>();
        wrapper.eq("survey_id",id).eq("del_flg",0);
        List<SurveyQuestionPojo> questions = surveyQuestionMapper.selectList(wrapper);
        questions.forEach(question ->{
            try {
                JSONArray opts = JSONArray.parseArray(question.getOption());
                List<Map<String, String>> options = new ArrayList<>();
                for(int i=0; i<opts.size(); i++){
                    Map<String,String> map = JSONObject.parseObject(opts.getString(i),new TypeReference<Map<String,String>>(){});
                    options.add(map);
                }
                question.setOptions(options);
            } catch (Exception e) {
                question.setOptions(new ArrayList<>());
            }
        });
        surveyPojo.setQuestionList(questions);
        return surveyPojo;
    }

    /**
     * 修改
     * @param pojo
     */
    public void update(SurveyPojo pojo){

        Wrapper<SurveyQuestionPojo> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id").eq("survey_id",pojo.getId()).eq("del_flg",0);
        List<Object> questionIds = surveyQuestionMapper.selectObjs(wrapper);
        List<SurveyQuestionPojo> questionList = pojo.getQuestionList();
        for(SurveyQuestionPojo question : questionList){
            question.setSurveyId(pojo.getId());
            if(!ToolUtil.contains(questionIds, question.getId())){
                surveyQuestionMapper.insert(question);
            }else{
                surveyQuestionMapper.updateById(question);
                questionIds.remove(question.getId());
            }
        }
        for(Object questionId : questionIds){
            SurveyQuestionPojo question = new SurveyQuestionPojo();
            question.setId((Integer) questionId);
            question.setDelFlg(1);
            surveyQuestionMapper.updateById(question);
        }

        int questionNum = surveyQuestionMapper.selectCount(wrapper);
        pojo.setQuestionNum(questionNum);
        surveyMapper.updateById(pojo);

    }

    /**
     * 新建
     * @param pojo
     */
    public void add(SurveyPojo pojo) {
        String now = DateUtil.getAllTime();
        pojo.setCreateTime(now);
        surveyMapper.insert(pojo);

        Integer surveyId = pojo.getId();
        List<SurveyQuestionPojo> questionList = pojo.getQuestionList();
        questionList.forEach(question ->{
            question.setSurveyId(surveyId);
            surveyQuestionMapper.insert(question);
        });
    }

    /**
     * 删除
     * @param id
     */
    public void remove(Integer id) {
        SurveyPojo pojo = new SurveyPojo();
        pojo.setId(id);
        pojo.setDelFlg(1);
        Wrapper<SurveyPojo> wrapper = new EntityWrapper<>();
        wrapper.eq("id",id);
        surveyMapper.update(pojo,wrapper);
    }

    /**
     * 录入
     * @param list
     */
    public void record(List<SurveyRecordAnswerPojo> list) {
        SurveyPojo surveyPojo = new SurveyPojo();
        for(SurveyRecordAnswerPojo answer : list){
            answer.setCreateTime(DateUtil.getAllTime());
            surveyRecordAnswerMapper.insert(answer);
            surveyPojo.setId(answer.getSurveyId());
        }
        surveyPojo.setStatus(1);
        surveyMapper.updateAllById(surveyPojo);
    }


    /**
     * 统计
     * @param id
     * @return
     */
    public SurveyPojo getSurveyById(Integer id){
        return surveyMapper.selectById(id);
    }

    public List<Object> getAnswersById(Integer id){
        Wrapper<SurveyRecordAnswerPojo> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("answer").eq("survey_id", id).groupBy("answer").orderBy("answer");
        return surveyRecordAnswerMapper.selectObjs(wrapper);
    }

    public Map<Integer,Object> getStatisticData(Integer id, List<Object> answers){

        Map<Integer,Object> result = new HashMap<>();
        Wrapper<SurveyRecordAnswerPojo> wrapper = new EntityWrapper<>();
        wrapper.eq("survey_id", id).groupBy("position");
        List<SurveyRecordAnswerPojo> records = surveyRecordAnswerMapper.selectList(wrapper);
        for(SurveyRecordAnswerPojo record : records){
            List<Map<String, Object>> data = surveyRecordAnswerMapper.getStatisticData(id, record.getPosition());

            data.addAll(getMaps(answers));

            data = ToolUtil.removeRepeatMapByKey(data, "answer");

            Collections.sort(data, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    int i = (int)o1.get("answer") - (int)o2.get("answer");
                    if(i == 0){
                        return (int)o1.get("answer") - (int)o2.get("answer");
                    }
                    return i;
                }
            });

            result.put(record.getPosition(), data);
        };

        return result;
    }

    private List<Map<String, Object>> getMaps(List<Object> answers) {
        List<Map<String, Object>> _temp = new LinkedList<>();
        for(Object _answer : answers){
            Map<String,Object> m = new HashMap<>();
            m.put("answer", _answer);
            m.put("num", -1);
            _temp.add(m);
        }
        return _temp;
    }
}
