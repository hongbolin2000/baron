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
package com.hongyou.baron;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 生成器抽象类
 *
 * @author Berlin
 */
public class AbstractGenerator {

    /**
     * 生成前的准备
     *
     * @param baseDirectory 项目路径
     * @param targetDirectory 输出的目标路径
     */
    public File prepare(
            final String baseDirectory, final String targetDirectory
    ) throws GenerationException, IOException {
        File file = new File(baseDirectory, targetDirectory);
        if (file.exists()) {
            for (File child: Objects.requireNonNull(file.listFiles())) {
                Files.delete(Paths.get(child.getPath()));
            }
            return file;
        }
        if (!file.mkdir()) {
            throw new GenerationException("文件夹创建失败: {}", file.getPath());
        }
        return file;
    }
}
