package com.codefog.admin.api.controller.front.officialSite;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.entity.cms.Article;
import com.codefog.admin.bean.enumeration.cms.ChannelEnum;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.bean.vo.offcialSite.Banner;
import com.codefog.admin.bean.vo.offcialSite.News;
import com.codefog.admin.bean.vo.offcialSite.Product;
import com.codefog.admin.bean.vo.offcialSite.Solution;
import com.codefog.admin.service.cms.ArticleService;
import com.codefog.admin.service.cms.BannerService;
import com.codefog.admin.utils.factory.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offcialSite")
public class OffcialSiteController extends BaseController {

    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public Object index() {
        Map<String, Object> dataMap = new HashMap<>();

        Banner banner = bannerService.queryIndexBanner();
        dataMap.put("banner", banner);
        List<News> newsList = new ArrayList<>();
        List<com.codefog.admin.bean.entity.cms.Article> articles = articleService.queryIndexNews();
        for (com.codefog.admin.bean.entity.cms.Article article : articles) {
            News news = new News();
            news.setDesc(article.getTitle());
            news.setUrl("/article?id=" + article.getId());
            news.setSrc("static/images/icon/user.png");
            newsList.add(news);
        }
        dataMap.put("newsList", newsList);

        List<Product> products = new ArrayList<>();
        Page<Article> articlePage = articleService.query(1, 4, ChannelEnum.PRODUCT.getId());
        for (Article article : articlePage.getRecords()) {
            Product product = new Product();
            product.setId(article.getId());
            product.setName(article.getTitle());
            product.setImg(article.getImg());
            products.add(product);
        }
        dataMap.put("productList", products);

        List<Solution> solutions = new ArrayList<>();
        articlePage = articleService.query(1, 4, ChannelEnum.SOLUTION.getId());
        for (Article article : articlePage.getRecords()) {
            Solution solution = new Solution();
            solution.setId(article.getId());
            solution.setName(article.getTitle());
            solution.setImg(article.getImg());
            solutions.add(solution);
        }
        dataMap.put("solutionList", solutions);

        Map map = new HashMap();

        map.put("data", dataMap);
        return Rets.success(map);

    }
}
