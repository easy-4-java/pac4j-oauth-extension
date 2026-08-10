/*
 * Copyright (c) 2018-present, easy-4-java (https://github.com/easy-4-java).
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
package org.pac4j.oauth.profile.baidu;

import org.pac4j.core.profile.Gender;
import org.pac4j.core.profile.converter.AbstractAttributeConverter;

/**
 * Attribute converter that normalises the raw {@code sex} value returned by
 * the Baidu user-info endpoint into a pac4j {@link Gender} enum.
 *
 * <p>Baidu returns gender as either a {@link String} with values
 * {@code "1"} (male) or {@code "0"} (female), or, for legacy/VK-style
 * payloads, as an {@link Integer} using {@code 2} for male and {@code 1}
 * for female. Anything else maps to {@link Gender#UNSPECIFIED}; unsupported
 * value types yield {@code null} so that the profile defers the decision
 * upstream.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see AbstractAttributeConverter
 * @see Gender
 */
public class BaiduGenderConverter extends AbstractAttributeConverter {

    /**
     * Creates a converter targeting the {@link Gender} target type. Required
     * by pac4j's reflective {@link AbstractAttributeConverter} contract.
     */
    public BaiduGenderConverter() {
        super(Gender.class);
    }

    /**
     * Performs the actual translation.
     *
     * @param attribute the raw attribute value as decoded by Jackson. May be
     *                  {@link String} (the documented Baidu format) or
     *                  {@link Integer} (VK compatibility). Other types yield
     *                  {@code null}.
     * @return the matching {@link Gender}, {@link Gender#UNSPECIFIED} for
     *         recognised-but-unknown values, or {@code null} if the type is
     *         not handled at all.
     */
    @Override
    protected Gender internalConvert(final Object attribute) {
        // for baidu: 用户性别, 性别。"1"表示男，"0"表示女
        if (attribute instanceof String) {
            final String s = ((String) attribute).toLowerCase();
            if ("1".equals(s) || "m".equals(s) || "male".equals(s)) {
                return Gender.MALE;
            } else if ("0".equals(s) || "f".equals(s) || "female".equals(s)) {
                return Gender.FEMALE;
            } else {
                return Gender.UNSPECIFIED;
            }
            // for Vk:
        } else if (attribute instanceof Integer) {
            Integer value = (Integer) attribute;
            if (value == 2) {
                return Gender.MALE;
            } else if (value == 1) {
                return Gender.FEMALE;
            } else {
                return Gender.UNSPECIFIED;
            }
        }
        return null;
    }
}