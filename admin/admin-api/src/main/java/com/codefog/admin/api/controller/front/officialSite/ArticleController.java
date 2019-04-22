package com.codefog.admin.api.controller.front.officialSite;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.entity.cms.Article;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.dao.cms.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offcialSite/article")
public class ArticleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private ArticleRepository articleRepository;
    @RequestMapping(method = RequestMethod.GET)
    public Object get(@Param("id") Long id,@Param("type")String type) {
        logger.info("type:{},id:{}",type,id);
         Article article = articleRepository.findById(id).get();
        return Rets.success(article);
    }
}
