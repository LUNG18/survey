package com.gobestsoft.survey.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.survey.pojo.SurveyPojo;
import com.gobestsoft.survey.pojo.SurveyRecordAnswerPojo;
import com.gobestsoft.survey.util.ToolUtil;
import com.gobestsoft.survey.basic.BaseController;
import com.gobestsoft.survey.basic.tips.Tip;
import com.gobestsoft.survey.exception.BizExceptionEnum;
import com.gobestsoft.survey.exception.BusinessException;
import com.gobestsoft.survey.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 问卷调查
 */
@Controller
@RequestMapping("/survey")
@Slf4j
@CrossOrigin
public class SurveyController extends BaseController {

    private final String PREFIX = "/survey/";

    @Resource
    private SurveyService surveyService;

    /**
     * 跳转首页面
     * @return
     */
    @RequestMapping("")
    public String index(){
        return PREFIX + "index";
    }

    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return PREFIX + "list";
    }

    /**
     * 跳转至详情（录入）页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/detail")
    public String detail(@RequestParam("id") Integer id, Model model) {
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        SurveyPojo pojo = surveyService.selectById(id);
        model.addAttribute("survey", pojo);
        model.addAttribute("surveyId", id);
        return PREFIX + "detail";
    }

    /**
     * 跳转至统计页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/statistic")
    public String statistic(@RequestParam("id") Integer id, Model model) {
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        SurveyPojo surveyPojo = surveyService.getSurveyById(id);
        model.addAttribute("survey", surveyPojo);
        List<Object> answers = surveyService.getAnswersById(id);
        model.addAttribute("answers", answers);
        Map<Integer,Object> map = surveyService.getStatisticData(id, answers);
        model.addAttribute("map", map);

        return PREFIX + "statistic";
    }

    /**
     * 跳转至编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestParam("id") Integer id, Model model)  {
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        SurveyPojo pojo = surveyService.selectById(id);
        model.addAttribute("survey", pojo);
        model.addAttribute("surveyId", id);
        model.addAttribute("status", pojo.getStatus());
        return PREFIX + "edit";
    }

    /**
     * 跳转至新增页面
     * @return
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "add";
    }




    /**
     * 获取问卷列表
     * @param title
     * @param status
     * @return
     */
    @RequestMapping("/getList")
    @ResponseBody
    public Object list(@RequestParam(required = false) String title, @RequestParam(required = false) Integer status) {
        Page<SurveyPojo> page = defaultPage();
        List<SurveyPojo> result = surveyService.selectByCondition(page, title, status);
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 保存问卷
     * @return
     */
    @RequestMapping("/doSave")
    @ResponseBody
    public Tip doSave(@RequestBody SurveyPojo pojo) {

        try {
            if(ToolUtil.isNotEmpty(pojo.getId())){
                surveyService.update(pojo);
            }else{
                surveyService.add(pojo);
            }
        } catch (Exception e) {
            return fail(e.getMessage());
        }

        return success(pojo.getId());
    }

    /**
     * 删除问卷
     * @return
     */
    @RequestMapping("/remove")
    @ResponseBody
    public Tip remove(Integer id) {

        surveyService.remove(id);

        return success();
    }

    /**
     * 保存答案录入
     * @return
     */
    @RequestMapping("/doRecord")
    @ResponseBody
    public Tip doRecord(@RequestBody List<SurveyRecordAnswerPojo> list) {

        surveyService.record(list);

        return success();
    }
}
