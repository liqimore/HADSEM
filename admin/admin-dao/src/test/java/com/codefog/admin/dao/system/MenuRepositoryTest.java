package com.codefog.admin.dao.system;

import com.codefog.admin.dao.BaseApplicationStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MenuRepositoryTest
 *
 * @author enilu
 * @version 2018/8/14 0014
 */
public class MenuRepositoryTest extends BaseApplicationStartTest {
    @Autowired
    private MenuRepository menuRepository;
    @Test
    public void deleteRelationByMenu() {
        menuRepository.deleteRelationByMenu(203L);
    }
}