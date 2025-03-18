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
package com.hongyou.baron.model;

import com.hongyou.baron.GenerationException;
import com.hongyou.baron.util.SUID;
import lombok.Data;

/**
 * 字段枚举值
 *
 * @author Berlin
 */
@Data
public class Enumeration {

    /**
     * 表定义字段字典记录ID(中英文)
     */
    private String ctfvlid;
    private String etfvlid;

    /**
     * 枚举值
     */
    private String code;

    /**
     * 枚举代码
     */
    private String field;

    /**
     * 英文标签
     */
    private String elabel;

    /**
     * 中文标签
     */
    private String clabel;

    /**
     * 描述
     */
    private String remark;

    /**
     * 生成枚举对象
     *
     * @param line 待解析的文本行
     * @throws GenerationException 生成枚举时发生的异常
     */
    public Enumeration(final String line) throws GenerationException {
        String[] items = line.split("\\s+", 4);

        if (items.length < 3) {
            throw new GenerationException("无效的枚举值\n{}", line);
        }

        this.code = items[0];
        this.field = items[1];
        this.elabel = items[1];
        this.clabel = items[2];

        if (items.length > 3) {
            this.remark = items[3];
        } else {
            this.remark = "";
        }
        this.ctfvlid = Long.toString(SUID.getSnowflakeNextId());
        this.etfvlid = Long.toString(SUID.getSnowflakeNextId());
    }
}
