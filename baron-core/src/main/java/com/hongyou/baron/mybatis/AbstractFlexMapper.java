/*
 * Copyright 2024, Hongyou Software Development Studio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hongyou.baron.mybatis;

import com.mybatisflex.core.exception.FlexAssert;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.provider.EntitySqlProvider;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.table.TableInfo;
import com.mybatisflex.core.table.TableInfoFactory;
import com.mybatisflex.core.util.MapperUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.exceptions.TooManyResultsException;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 统一定义MyBatis Flex Mapper的方法
 *
 * @author Berlin
 */
public interface AbstractFlexMapper<T> extends FlexBaseMapper<T> {

    /*
     *******************************************************************************************************************
     * 数据插入方法
     ******************************************************************************************************************/
    /**
     * 插入一条数据，忽略空值
     *
     * @param entity 实体数据
     */
    @Override
    @SuppressWarnings("deprecation")
    default int insert(T entity) {
        FlexAssert.notNull(entity, "entity");
        return insert(entity, false);
    }

    /**
     * 插入一条数据
     *
     * @param entity 实体数据
     * @param ignoreNulls 是否忽略空值
     */
    @Override
    @SuppressWarnings("deprecation")
    @InsertProvider(type = EntitySqlProvider.class, method = "insert")
    int insert(@Param("$$entity") T entity, @Param("$$ignoreNulls") boolean ignoreNulls);

    /**
     * 批量插入数据
     *
     * @param entities 实体数据集合
     */
    @InsertProvider(type = EntitySqlProvider.class, method = "insertBatch")
    int insertBatch(@Param("$$entities") List<T> entities);

    /**
     * 通过实体集合分批次插入数据
     *
     * @param entities 实体数据集合
     * @param batchSize 每批插入的条数
     */
    default int insertBatch(List<T> entities, int batchSize) {
        FlexAssert.notEmpty(entities, "entities");

        // 避免设置脏数据，缺省为1000条每批
        int size = batchSize <= 0 ? 1000 : batchSize;

        // 计算插入的次数
        int entitiesSize = entities.size();
        int insertCount = entitiesSize / size + (entitiesSize % size == 0 ? 0 : 1);

        int sum = 0;
        for(int i = 0; i < insertCount; ++i) {
            List<T> list = entities.subList(i * size, Math.min(i * size + size, entitiesSize));
            sum += this.insertBatch(list);
        }
        return sum;
    }

    /**
     * 插入或修改一条数据
     * <p>如果主键存在时则进行修改、主键不存在时进行插入操作</p>
     *
     * @param entity 实体数据
     */
    default int save(T entity) {
        FlexAssert.notNull(entity, "entity");
        TableInfo tableInfo = TableInfoFactory.ofEntityClass(entity.getClass());
        Object[] pkArgs = tableInfo.buildPkSqlArgs(entity);
        if (pkArgs.length != 0 && pkArgs[0] != null) {
            return this.update(entity, false);
        } else {
            return this.insert(entity, false);
        }
    }

    /*
     *******************************************************************************************************************
     * 数据删除方法
     ******************************************************************************************************************/
    /**
     * 通过实体类删除一条数据
     *
     * @param entity 实体类数据
     */
    @Override
    default int delete(T entity) {
        FlexAssert.notNull(entity, "entity");
        TableInfo tableInfo = TableInfoFactory.ofEntityClass(entity.getClass());
        Object[] pkArgs = tableInfo.buildPkSqlArgs(entity);
        return this.delete(pkArgs);
    }

    /**
     * 通过主键删除数据
     *
     * @param pk 主键
     */
    @DeleteProvider(type = EntitySqlProvider.class, method = "deleteById")
    int delete(@Param("$$primaryValue") Serializable pk);

    /**
     * 通过主键集合删除数据
     *
     * @param pks 主键集合
     */
    @DeleteProvider(type = EntitySqlProvider.class, method = "deleteBatchByIds")
    int deleteIds(@Param("$$primaryValue") List<Long> pks);

    /**
     * 通过主键集合分批次删除数据
     * <p>如果删除的数量很大，建议使用分批次删除的方式，减小数据库的压力</p>
     *
     * @param pks 主键集合
     * @param batchSize 每批删除的条数
     */
    default int delete(List<Long> pks, int batchSize) {
        FlexAssert.notEmpty(pks, "identity keys");

        // 避免设置脏数据，缺省为1000条每批
        int size = batchSize <= 0 ? 1000 : batchSize;

        // 计算删除的次数
        int entitiesSize = pks.size();
        int deleteCount = entitiesSize / size + (entitiesSize % size == 0 ? 0 : 1);

        int sum = 0;
        for(int i = 0; i < deleteCount; ++i) {
            List<Long> list = pks.subList(i * size, Math.min(i * size + size, entitiesSize));
            sum += this.deleteIds(list);
        }
        return sum;
    }

    /**
     * 通过查询条件删除数据
     *
     * @param wrapper 查询条件
     */
    @DeleteProvider(type = EntitySqlProvider.class, method = "deleteByQuery")
    int deleteQuery(@Param("$$query") QueryWrapper wrapper);

    /*
     *******************************************************************************************************************
     * 数据修改方法
     ******************************************************************************************************************/

    /**
     * 更新一条记录
     *
     * @param entity 实体数据
     * @param ignoreNulls 是否忽略空值
     */
    @Override
    @SuppressWarnings("deprecation")
    @UpdateProvider(type = EntitySqlProvider.class, method = "update")
    int update(@Param("$$entity") T entity, @Param("$$ignoreNulls") boolean ignoreNulls);

    /**
     * 通过查询条件更新数据
     *
     * @param entity 实体数据
     * @param wrapper 查询条件
     */
    default int update(T entity, QueryWrapper wrapper) {
        FlexAssert.notNull(entity, "entity");
        FlexAssert.notNull(wrapper, "query wrapper");
        return this.updateQuery(entity, false, wrapper);
    }

    /**
     * 通过查询条件更新数据
     *
     * @param entity 实体数据
     * @param ignoreNullValue 是否忽略空值
     * @param wrapper 查询条件
     */
    @UpdateProvider(type = EntitySqlProvider.class, method = "updateByQuery")
    int updateQuery(
            @Param("$$entity") T entity,
            @Param("$$ignoreNulls") boolean ignoreNullValue,
            @Param("$$query") QueryWrapper wrapper
    );

    /*
     *******************************************************************************************************************
     * 数据查询方法
     ******************************************************************************************************************/
    /**
     * 通过主键查询数据
     *
     * @param pk 主键
     */
    @SelectProvider(type = EntitySqlProvider.class, method = "selectOneById")
    T get(@Param("$$primaryValue") Long pk);

    /**
     * 查询单条记录
     *
     * @param wrapper 查询条件
     */
    default T single(QueryWrapper wrapper) {
        FlexAssert.notNull(wrapper, "query wrapper");
        wrapper.limit(2);
        List<T> results = this.list(wrapper);
        if (results.isEmpty()) {
            return null;
        }
        if (results.size() > 1) {
            throw new TooManyResultsException("Expected one result, but found: " + results.size());
        }
        return results.get(0);
    }

    /**
     * 通过主键集合查询数据
     *
     * @param pks 主键集合
     */
    @SelectProvider(type = EntitySqlProvider.class, method = "selectListByIds")
    List<T> listIds(@Param("$$primaryValue") Collection<Long> pks);

    /**
     * 通过条件查询数据
     *
     * @param wrapper 查询条件
     */
    @SelectProvider(type = EntitySqlProvider.class, method = "selectListByQuery")
    List<T> list(@Param("$$query") QueryWrapper wrapper);

    /**
     * 游标查询
     * <p>对大量数据进行处理时，为防止方法内存泄漏情况，应该使用游标（Cursor）方式进行数据查询并处理数据</p>
     * <p>数据查询并写入到缓存、Excel导出等等场景经常使用游标查询</p>
     * <p>详细使用方式请参考MF官方文档的selectCursorByQuery方法</p>
     *
     * @param wrapper 查询条件
     */
    @SelectProvider(type = EntitySqlProvider.class, method = "selectListByQuery")
    Cursor<T> listCursor(@Param("$$query") QueryWrapper wrapper);

    /**
     * 查询指定的第一列返回数据集合
     * <p>如：QueryWrapper.create().select(ACCOUNT.id).where(...);</p>
     *
     * @param wrapper 查询条件
     */
    @SelectProvider(type = EntitySqlProvider.class, method = "selectObjectByQuery")
    List<Object> listObject(@Param("$$query") QueryWrapper wrapper);

    /**
     * 查询总记录数
     *
     * @param wrapper 查询条件
     */
    default long count(QueryWrapper wrapper) {
        FlexAssert.notNull(wrapper, "query wrapper");
        wrapper.select(QueryMethods.count());
        return MapperUtil.getLongNumber(this.listObject(wrapper));
    }

    /*
     *******************************************************************************************************************
     * 数据分页方法
     ******************************************************************************************************************/
    default Page<T> paginate(
            int pageNumber, int pageSize, QueryWrapper wrapper
    ) {
        FlexAssert.notNull(wrapper, "query wrapper");
        Page<T> page = new Page<>(pageNumber, pageSize);
        return MapperUtil.doPaginate(this, page, wrapper, null, false);
    }
}