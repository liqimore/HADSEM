
package com.codefog.admin.dao.cms;

import com.codefog.admin.bean.entity.cms.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<Article,Long>
        ,JpaRepository<Article,Long>,JpaSpecificationExecutor<Article> {

    List<Article> findAllByIdChannel(Long idChannel);
}
