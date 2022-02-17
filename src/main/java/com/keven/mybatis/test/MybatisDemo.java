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
import com.keven.mybatis.mapper.BlogMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author Keven
 */
public class MybatisDemo {

  public static void main(String[] args) throws IOException {
    // initLog();

    String resources = "mybatis-config.xml";
    InputStream resourceAsStream = Resources.getResourceAsStream(resources);

    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
    SqlSession sqlSession = sessionFactory.openSession();

    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    Blog selectBlogById = mapper.selectBlogById("1");
    System.out.println(selectBlogById);

    Blog blog = sqlSession.selectOne("com.keven.mybatis.mapper.BlogMapper.selectBlogById", 1);
    System.out.println(blog);
  }

  private static void initLog() throws IOException {
    FileInputStream fileInputStream = null;
    try {
      Properties properties = new Properties();
      fileInputStream = new FileInputStream("src/main/resources/log4j.properties");
      properties.load(fileInputStream);
      PropertyConfigurator.configure(properties);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (fileInputStream != null) {
        try {
          fileInputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
