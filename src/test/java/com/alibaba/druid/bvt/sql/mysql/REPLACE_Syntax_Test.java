/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.druid.bvt.sql.mysql;

import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Assert;
import junit.framework.TestCase;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;

public class REPLACE_Syntax_Test extends TestCase {
    public void test_0() throws Exception {
        String sql = "REPLACE INTO T SELECT * FROM T;";

        SQLStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();

        String text = SQLUtils.toSQLString(stmtList, JdbcConstants.MYSQL);

        Assert.assertEquals("REPLACE INTO T\n\tSELECT *\n\tFROM T;", text);
    }

    public void test_1() throws Exception {
        String sql = "REPLACE DELAYED INTO `online_users` SET `session_id`='3580cc4e61117c0785372c426eddd11c', `user_id` = 'XXX', `page` = '/', `lastview` = NOW();";

        SQLStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();

        String text = SQLUtils.toSQLString(stmtList, JdbcConstants.MYSQL);

        Assert.assertEquals("REPLACE DELAYED INTO `online_users` (`session_id`, `user_id`, `page`, `lastview`)\nVALUES ('3580cc4e61117c0785372c426eddd11c', 'XXX', '/', NOW());",
                text);
    }

    public void test_2() throws Exception {
        String sql = " replace into t(col1,col2)values(?,?)";

        SQLStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();

        String text = SQLUtils.toSQLString(stmtList, JdbcConstants.MYSQL);

        Assert.assertEquals("REPLACE INTO t (col1, col2)\nVALUES (?, ?)", text);
    }

}
