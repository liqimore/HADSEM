package com.codefog.admin.api.controller.front.officialSite;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.entity.cms.Article;
import com.codefog.admin.bean.enumeration.cms.BannerTypeEnum;
import com.codefog.admin.bean.enumeration.cms.ChannelEnum;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.bean.vo.offcialSite.Banner;
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
@RequestMapping("/offcialSite/solution")
public class SolutionController extends BaseController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public Object index() {
        Map<String, Object> dataMap = new HashMap<>();

        Banner banner = bannerService.queryBanner(BannerTypeEnum.SOLUTION.getValue());
        dataMap.put("banner", banner);

        List<Solution> solutions = new ArrayList<>();
        Page<Article> articlePage = articleService.query(1, 10, ChannelEnum.SOLUTION.getId());
        for (Article article : articlePage.getRecords()) {
            solutions.add(new Solution(article.getId(), article.getTitle(), article.getImg()));
        }
        dataMap.put("solutionList", solutions);

        Map map = new HashMap();
        map.put("data", dataMap);
        return Rets.success(map);

    }
}
