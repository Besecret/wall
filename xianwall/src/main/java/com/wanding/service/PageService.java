
package com.wanding.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanding.util.queryhelper.Page;
import com.wanding.util.queryhelper.QueryExecutor;


@Service
public class PageService{
	@Autowired
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession = null;
	/**
	* @Title: selectPage
	* @Description: TODO(公共分页查询)
	* @param start  当前页
	* @param length 条目数
	* @throws
	*/
    protected <T> Page<T> selectPage(int pageNum, int pageSize, QueryExecutor<T> queryExecutor) {

        PageHelper.offsetPage(pageNum, pageSize);
        List<T> list = queryExecutor.execute();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        return new Page<T>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), null, pageInfo.getList());
    }

	
}