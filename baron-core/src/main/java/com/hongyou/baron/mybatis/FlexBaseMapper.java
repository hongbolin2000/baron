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

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.field.FieldQueryBuilder;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Row;
import org.apache.ibatis.cursor.Cursor;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 统一方法调用，在这里禁用MyBatis Flex的所有方法
 *
 * @author Berlin
 */
public interface FlexBaseMapper<T> extends BaseMapper<T> {
    
    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insert(T entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insertSelective(T entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * AbstractMethod
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insert(T t, boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insertWithPk(T entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insertSelectiveWithPk(T entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * AbstractMethod
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insertWithPk(T t, boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insertBatch(Collection<T> collection) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insertBatch(Collection<T> entities, int size) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insertBatchSelective(Collection<T> entities) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insertBatchSelective(Collection<T> entities, int size) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insertOrUpdate(T entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insertOrUpdateSelective(T entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int insertOrUpdate(T entity, boolean ignoreNulls) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    int deleteById(Serializable serializable);

    /**
     * AbstractMethod
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int deleteBatchByIds(Collection<? extends Serializable> collection) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int deleteBatchByIds(Collection<? extends Serializable> ids, int size) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int deleteByMap(Map<String, Object> whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int deleteByCondition(QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    int deleteByQuery(QueryWrapper queryWrapper);

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int update(T entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * AbstractMethod
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int update(T t, boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int updateByMap(T entity, Map<String, Object> whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int updateByMap(T entity, boolean ignoreNulls, Map<String, Object> whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int updateByCondition(T entity, QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int updateByCondition(T entity, boolean ignoreNulls, QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int updateByQuery(T entity, QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * AbstractMethod
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default int updateByQuery(T t, boolean b, QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default T selectOneByEntityId(T entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * AbstractMethod
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default T selectOneById(Serializable serializable) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default T selectOneByMap(Map<String, Object> whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default T selectOneByCondition(QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default T selectOneByQuery(QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> R selectOneByQueryAs(QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default T selectOneWithRelationsByMap(Map<String, Object> whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default T selectOneWithRelationsByCondition(QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default T selectOneWithRelationsByQuery(QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default T selectOneWithRelationsById(Serializable id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> R selectOneWithRelationsByIdAs(Serializable id, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> R selectOneWithRelationsByQueryAs(QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * AbstractMethod
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default List<T> selectListByIds(Collection<? extends Serializable> collection) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default List<T> selectListByMap(Map<String, Object> whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default List<T> selectListByMap(Map<String, Object> whereConditions, Long count) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default List<T> selectListByCondition(QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default List<T> selectListByCondition(QueryCondition whereConditions, Long count) {
        throw new UnsupportedOperationException();
    }

    /**
     * AbstractMethod
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default List<T> selectListByQuery(QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    @SuppressWarnings("unchecked")
    default List<T> selectListByQuery(QueryWrapper queryWrapper, Consumer<FieldQueryBuilder<T>>... consumers) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    Cursor<T> selectCursorByQuery(QueryWrapper queryWrapper);

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> Cursor<R> selectCursorByQueryAs(QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * AbstractMethod
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default List<Row> selectRowsByQuery(QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> List<R> selectListByQueryAs(QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    @SuppressWarnings("unchecked")
    default <R> List<R> selectListByQueryAs(QueryWrapper queryWrapper, Class<R> asType, Consumer<FieldQueryBuilder<R>>... consumers) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default List<T> selectListWithRelationsByQuery(QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> List<R> selectListWithRelationsByQueryAs(QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    @SuppressWarnings("unchecked")
    default <R> List<R> selectListWithRelationsByQueryAs(QueryWrapper queryWrapper, Class<R> asType, Consumer<FieldQueryBuilder<R>>... consumers) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default List<T> selectAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default List<T> selectAllWithRelations() {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default Object selectObjectByQuery(QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> R selectObjectByQueryAs(QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * AbstractMethod
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default List<Object> selectObjectListByQuery(QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> List<R> selectObjectListByQueryAs(QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default long selectCountByQuery(QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default long selectCountByCondition(QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default Page<T> paginate(Number pageNumber, Number pageSize, QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default Page<T> paginateWithRelations(Number pageNumber, Number pageSize, QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default Page<T> paginate(Number pageNumber, Number pageSize, QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default Page<T> paginateWithRelations(Number pageNumber, Number pageSize, QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default Page<T> paginate(Number pageNumber, Number pageSize, Number totalRow, QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default Page<T> paginateWithRelations(Number pageNumber, Number pageSize, Number totalRow, QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default Page<T> paginate(Number pageNumber, Number pageSize, Number totalRow, QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default Page<T> paginateWithRelations(Number pageNumber, Number pageSize, Number totalRow, QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default Page<T> paginate(Page<T> page, QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    @SuppressWarnings("unchecked")
    default Page<T> paginate(Page<T> page, QueryWrapper queryWrapper, Consumer<FieldQueryBuilder<T>>... consumers) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default Page<T> paginateWithRelations(Page<T> page, QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    @SuppressWarnings("unchecked")
    default Page<T> paginateWithRelations(Page<T> page, QueryWrapper queryWrapper, Consumer<FieldQueryBuilder<T>>... consumers) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> Page<R> paginateAs(Number pageNumber, Number pageSize, QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> Page<R> paginateAs(Number pageNumber, Number pageSize, Number totalRow, QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> Page<R> paginateAs(Page<R> page, QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    @SuppressWarnings("unchecked")
    default <R> Page<R> paginateAs(Page<R> page, QueryWrapper queryWrapper, Class<R> asType, Consumer<FieldQueryBuilder<R>>... consumers) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> Page<R> paginateWithRelationsAs(Number pageNumber, Number pageSize, QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> Page<R> paginateWithRelationsAs(Number pageNumber, Number pageSize, Number totalRow, QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <R> Page<R> paginateWithRelationsAs(Page<R> page, QueryWrapper queryWrapper, Class<R> asType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    @SuppressWarnings("unchecked")
    default <R> Page<R> paginateWithRelationsAs(Page<R> page, QueryWrapper queryWrapper, Class<R> asType, Consumer<FieldQueryBuilder<R>>... consumers) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <E> Page<E> xmlPaginate(String dataSelectId, Page<E> page, QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <E> Page<E> xmlPaginate(String dataSelectId, Page<E> page, Map<String, Object> otherParams) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <E> Page<E> xmlPaginate(String dataSelectId, Page<E> page, QueryWrapper queryWrapper, Map<String, Object> otherParams) {
        throw new UnsupportedOperationException();
    }

    /**
     * 为了统一方法调用，在这里禁用MyBatis Fex的所有方法
     *
     * @deprecated 禁用的方法
     */
    @Override
    @Deprecated(since = "1.0.0")
    default <E> Page<E> xmlPaginate(String dataSelectId, String countSelectId, Page<E> page, QueryWrapper queryWrapper, Map<String, Object> otherParams) {
        throw new UnsupportedOperationException();
    }
}
