/*
 *    Copyright 2009-2022 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.keven.mybatis.test;

import com.keven.mybatis.entity.Blog;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.keven.mybatis.constant.JdbcConstant.*;

public class JunitTest {
  @Test
  public void test() throws IOException {
    String resources = "mybatis-config.xml";
    InputStream resourceAsStream = Resources.getResourceAsStream(resources);

    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
    SqlSession sqlSession = sessionFactory.openSession();

    Blog blog = sqlSession.selectOne("com.keven.mybatis.mapper.BlogMapper.selectBlogById", 1);
    System.out.println(blog);

  }

  private Configuration configuration;
  private JdbcTransaction jdbcTransaction;
  private Connection connection;

  @Before
  public void init() throws IOException, SQLException {
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory build = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
    configuration = build.getConfiguration();
    connection = DriverManager.getConnection(URL,USER,PASSWORD);
    jdbcTransaction = new JdbcTransaction(connection);
  }

  @Test
  public void simpleExecutorTest() {
    SimpleExecutor simpleExecutor = new SimpleExecutor(configuration,jdbcTransaction);

  }


}
