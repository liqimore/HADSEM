package com.codefog.admin.api.controller.cms;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.annotion.core.BussinessLog;
import com.codefog.admin.bean.constant.factory.PageFactory;
import com.codefog.admin.bean.dictmap.CommonDict;
import com.codefog.admin.bean.entity.cms.Article;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.dao.cms.ArticleRepository;
import com.codefog.admin.service.cms.ArticleService;
import com.codefog.admin.utils.Maps;
import com.codefog.admin.utils.factory.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

/**
 * 文章管理
 */
@RestController
@RequestMapping("/article")
public class ArticleMgrController extends BaseController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleService articleService;
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑文章",key="name",dict = CommonDict.class)
    public Object save(){
        Article article = getFromJson(Article.class);
        articleRepository.save(article);
        return Rets.success();
    }
    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除文章",key="id",dict = CommonDict.class)
    public Object remove(Long id){
        articleRepository.deleteById(id);
        return Rets.success();
    }
    @RequestMapping(method = RequestMethod.GET)
    public Object get(@Param("id") Long id) {
        Article article = articleRepository.findById(id).get();
        return Rets.success(article);
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String cfgName, @RequestParam(required = false) String cfgValue) {
        Page<Article> page = new PageFactory<Article>().defaultPage();

        page = articleService.findPage(page, Maps.newHashMap("cfgName",cfgName,"cfgValue",cfgValue));
        page.setRecords(page.getRecords());
        return Rets.success(page);
    }
}
